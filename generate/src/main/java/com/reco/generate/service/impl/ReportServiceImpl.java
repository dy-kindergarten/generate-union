package com.reco.generate.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jcraft.jsch.JSchException;
import com.reco.generate.entity.Report;
import com.reco.generate.repository.logCenter.ReportMapper;
import com.reco.generate.repository.UserPayValMapper;
import com.reco.generate.service.ReportService;
import com.reco.generate.utils.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/10/16 9:42
 * @Description: 报表服务实现类
 */
@Service(value = "reportService")
public class ReportServiceImpl implements ReportService {

    private JschConnect ssh;

    @Value("${spring.ssh.ipAddress}")
    private String ip;
    @Value("${spring.ssh.username}")
    private String user;
    @Value("${spring.ssh.password}")
    private String pwd;
    @Value("${spring.ssh.port}")
    private Integer port;
    @Value("${spring.ssh.file.path}")
    private String path;
    @Value("${spring.ssh.file.prefix}")
    private String prefix;
    @Value("${spring.ssh.file.suffix}")
    private String suffix;
    @Value("${spring.ssh.logCenter.ipAddress}")
    private String lcIp;
    @Value("${spring.ssh.logCenter.username}")
    private String lcUser;
    @Value("${spring.ssh.logCenter.password}")
    private String lcPwd;
    @Value("${spring.ssh.logCenter.port}")
    private Integer lcPort;
    @Value("${spring.ssh.logCenter.filePath}")
    private String lcPath;
    @Value("${spring.ssh.logCenter.logTrigger}")
    private String logTriggerUrl;
    @Value("${spring.ssh.logCenter.syncToDB}")
    private String syncToDBUrl;
    @Value("${spring.ssh.logCenter.outPath}")
    private String outPath;

    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private UserPayValMapper userPayValMapper;

    @Autowired
    public void initSsh() {
        try {
            ssh = new JschConnect(ip, user, pwd, port);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void weeklyReport() {
        /*
         *  获取上周的pv/uv
         */
        Date lastWeekStart = DateUtils.getThisWeekMonday();
        Date lastWeekEnd = DateUtils.getDate(lastWeekStart, 6, DateUtils.AFTER);
        List<String> dateStrs = DateUtils.getDateStrs(lastWeekStart, lastWeekEnd);
        // 1. 下载服务器上日志文件并解压
        String weekNumStr = DateUtils.getWeekNumStr(lastWeekEnd);
        String outputDir = "D:\\工作\\陕西广电\\访问日志\\" + weekNumStr;
        File directory = new File(outputDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = null;
        String outPutFile = null;
        String fileName = null;
        List<String> logFiles = Lists.newArrayList();
        for (String dateStr : dateStrs) {
            fileName = prefix + dateStr + suffix;
            filePath = path + fileName;
            outPutFile = outputDir + "\\" + fileName;
            ssh.downFile(filePath, outPutFile);
            ZArchiverUtils.decompressFile(outPutFile);
            logFiles.add(ZArchiverUtils.getFileName(fileName));
        }
        ssh.close();
        // 2. 上传日志文件到日志中心
        ssh = new JschConnect(lcIp, lcUser, lcPwd, lcPort);
        String localFile = null;
        String remoteFile = null;
        for (String logFile : logFiles) {
            localFile = outputDir + "\\" + logFile;
            remoteFile = lcPath + logFile;
            ssh.transfer(localFile, remoteFile);
        }
        // 3. 调用日志统计接口
        String result = HttpClientUtils.sendGet(logTriggerUrl);
        if (StringUtils.isNotBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
            if (StringUtils.equalsIgnoreCase(json.getString("code"), "1")) {
                // 4. 调用刷新缓存接口
                result = HttpClientUtils.sendPost(syncToDBUrl, getParamJSON(lastWeekStart, lastWeekEnd));
                if (StringUtils.isNotBlank(result)) {
                    json = JSONObject.parseObject(result);
                    if (StringUtils.equalsIgnoreCase(json.getString("code"), "1")) {
                        // 5. 数据库查询数据
                        Report report = reportMapper.countVolume(DateUtils.date2Str(lastWeekStart, "yyyyMMdd"), DateUtils.date2Str(lastWeekEnd, "yyyyMMdd"));
                        Integer count = userPayValMapper.countByDate(DateUtils.date2Str(lastWeekStart, "yyyy-MM-dd 00:00:00"), DateUtils.date2Str(lastWeekEnd, "yyyy-MM-dd 23:59:59"));
                        report.setOrderIncrement(count);

                        // 6. 生成表格
                        List<String> titleList = this.createCountTitle(Report.class);
                        XSSFWorkbook xssfWorkbook = ExcelUtils.initXSSFWorkbook(0, titleList);
                        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                        Field[] fields = report.getClass().getDeclaredFields();
                        XSSFRow row = xssfSheet.createRow(1);
                        int j = 0;
                        for (int i = 0; i < fields.length; i++) {
                            if (StringUtils.equalsIgnoreCase(fields[i].getName(), "day")) {
                                j--;
                            } else {
                                XSSFCell cell = row.createCell(i + j);
                                fields[i].setAccessible(true);
                                cell.setCellValue(fields[i].get(report) == null ? "" : fields[i].get(report).toString());
                            }
                        }
                        String pathName = outPath + weekNumStr + ".xlsx";
                        ExcelUtils.writeToFile(xssfWorkbook, new File(pathName));
                    }
                }
            }
        }
    }

    @Override
    @SneakyThrows
    public void dailyReport(Date startDate, Date endDate) {
        List<Report> dailyViewCounts = reportMapper.dailyCount(DateUtils.date2Str(startDate, "yyyyMMdd"), DateUtils.date2Str(endDate, "yyyyMMdd"));
        List<Report> dailyPayCounts = userPayValMapper.dailyCount(DateUtils.date2Str(startDate, "yyyy-MM-dd 00:00:00"), DateUtils.date2Str(endDate, "yyyy-MM-dd 23:59:59"));
        for (Report view : dailyViewCounts) {
            for (Report pay : dailyPayCounts) {
                if (StringUtils.equalsIgnoreCase(view.getDay(), pay.getDay())) {
                    view.setOrderIncrement(pay.getOrderIncrement());
                }
            }
        }

        // 生成表格
        List<String> titleList = this.createDetailTitle(Report.class);
        XSSFWorkbook xssfWorkbook = ExcelUtils.initXSSFWorkbook(0, titleList);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        Field[] fields = Report.class.getDeclaredFields();
        XSSFRow row = null;
        XSSFCell cell = null;
        for (int i = 0; i < dailyViewCounts.size(); i++) {
            row = xssfSheet.createRow(i + 1);
            for (int j = 0; j < fields.length; j++) {
                cell = row.createCell(j);
                fields[j].setAccessible(true);
                Object value = fields[j].get(dailyViewCounts.get(i));
                cell.setCellValue(value == null ? "0" : value.toString());
            }
        }

        String pathName = outPath + DateUtils.date2Str(startDate, "MM月") + ".xlsx";
        ExcelUtils.writeToFile(xssfWorkbook, new File(pathName));
    }

    private JSONObject getParamJSON(Date lastWeekStart, Date lastWeekEnd) {
        JSONObject param = new JSONObject();
        StringBuffer value = new StringBuffer();
        List<String> dateStrs = DateUtils.getDateStrs(lastWeekStart, lastWeekEnd, "yyyyMMdd");
        for (int i = 0; i < dateStrs.size(); i++) {
            if (i != 0) {
                value.append("&");
            }
            value.append(dateStrs.get(i));
        }
        param.put("syncDate", value);
        return param;
    }

    private List<String> createCountTitle(Class clazz) {
        List<String> titleList = Lists.newArrayList();
        for (Field field : clazz.getDeclaredFields()) {
            switch (field.getName()) {
                case "pv":
                    titleList.add("周pv");
                    break;
                case "uv":
                    titleList.add("周uv");
                    break;
                case "orderIncrement":
                    titleList.add("周订购");
                    break;
                default:
                    break;
            }
        }
        return titleList;
    }

    private List<String> createDetailTitle(Class clazz) {
        List<String> titleList = Lists.newArrayList();
        for (Field field : clazz.getDeclaredFields()) {
            switch (field.getName()) {
                case "day":
                    titleList.add("日期");
                    break;
                case "pv":
                    titleList.add("pv");
                    break;
                case "uv":
                    titleList.add("uv");
                    break;
                case "orderIncrement":
                    titleList.add("新增订购");
                    break;
                case "orderTotal":
                    titleList.add("累计总订购");
                    break;
                default:
                    break;
            }
        }
        return titleList;
    }
}
