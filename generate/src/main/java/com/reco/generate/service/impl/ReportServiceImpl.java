package com.reco.generate.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jcraft.jsch.JSchException;
import com.reco.generate.bo.enumEntity.DateType;
import com.reco.generate.entity.Report;
import com.reco.generate.repository.UserPayValMapper;
import com.reco.generate.repository.logCenter.ReportMapper;
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
import java.util.Date;
import java.util.List;

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
    @Value("${spring.ssh.logCenter.tempFilePath}")
    private String tempFilePath;

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

    @Override
    @SneakyThrows
    public void report(Date startDate, Date endDate, DateType type) {
        Date start = null;
        Date end = null;
        String fileName = null;
        switch (type) {
            case daily:
                start = startDate == null ? new Date() : startDate;
                end = start;
                fileName = outPath + "数据日报 - " + DateUtils.date2Str(startDate, "yyyyMMdd") + ".xlsx";
                break;
            case weekly:
                start = DateUtils.getLastWeekMonday();
                end = DateUtils.getDate(start, 6, DateUtils.AFTER);
                fileName = outPath + "数据周报 - " + DateUtils.getWeekNumStr(start, end) + ".xlsx";
                break;
            case monthly:
                start = DateUtils.getMonthStartOrEnd(true);
                end = DateUtils.getMonthStartOrEnd(false);
                fileName = outPath + "数据月报 - " + DateUtils.date2Str(startDate, "yyyyMM") + ".xlsx";
                break;
            case other:
                start = startDate;
                end = endDate;
                fileName = outPath + "数据报表 - " + DateUtils.date2Str(startDate, "yyyyMMdd") + "-" + DateUtils.date2Str(endDate, "yyyyMMdd") + ".xlsx";
                break;
        }
        List<String> logFiles = downloadLogFile(start, end);
        uploadLogFile(logFiles);
        Boolean result = handleLog(start, end);
        if (!result) {
            System.out.println("==== 日志统计或刷新缓存失败 ====");
            return;
        }
        List<Report> reports = getReports(start, end);
        writeToFile(reports, fileName);
    }

    private List<String> downloadLogFile(Date startDate, Date endDate) throws Exception {
        List<String> logFiles = Lists.newArrayList();
        List<String> dateStrs = DateUtils.getDateStrs(startDate, endDate);
        List<String> existFiles = Lists.newArrayList();
        List<String> notExistFiles = Lists.newArrayList();
        String filePath = null;
        String outPutFile = null;
        File directory = new File(tempFilePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 已下载文件
        for (File file : directory.listFiles()) {
            existFiles.add(file.getName());
            String fileDate = file.getName().split("\\.")[2];
            if (StringUtils.endsWith(file.getName(), "0") && dateStrs.contains(fileDate)) {
                logFiles.add(file.getName());
            }
        }
        // 未下载文件
        String fileName = null;
        for (String dateStr : dateStrs) {
            fileName = prefix + dateStr + suffix;
            if (!existFiles.contains(fileName)) {
                notExistFiles.add(fileName);
            }
        }
        for (String name : notExistFiles) {
            filePath = path + name;
            outPutFile = tempFilePath + name;
            try {
                ssh.downFile(filePath, outPutFile);
                ZArchiverUtils.decompressFile(outPutFile);
                logFiles.add(ZArchiverUtils.getFileName(name));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        ssh.close();
        return logFiles;
    }

    private void uploadLogFile(List<String> logFiles) throws Exception {
        // 2. 上传日志文件到日志中心
        ssh = new JschConnect(lcIp, lcUser, lcPwd, lcPort);
        String localFile = null;
        String remoteFile = null;
        for (String logFile : logFiles) {
            localFile = tempFilePath + logFile;
            remoteFile = lcPath + logFile;
            ssh.transfer(localFile, remoteFile);
        }
    }

    private Boolean handleLog(Date start, Date end) {
        String result = HttpClientUtils.sendGet(logTriggerUrl);
        if (StringUtils.isNotBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
            if (StringUtils.equalsIgnoreCase(json.getString("code"), "1")) {
                // 4. 调用刷新缓存接口
                result = HttpClientUtils.sendPost(syncToDBUrl, getParamJSON(start, end));
                if (StringUtils.isNotBlank(result)) {
                    json = JSONObject.parseObject(result);
                    if (StringUtils.equalsIgnoreCase(json.getString("code"), "1")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<Report> getReports(Date start, Date end) {
        List<String> dateStrs = DateUtils.getDateStrs(start, end, "yyyyMMdd");
        Integer total = userPayValMapper.countBeforeDate(DateUtils.date2Str(start, "yyyy-MM-dd 00:00:00"));
        List<Report> reports = reportMapper.dailyCount(DateUtils.date2Str(start, "yyyyMMdd"), DateUtils.date2Str(end, "yyyyMMdd"));
        List<Report> payReports = userPayValMapper.dailyCount(DateUtils.date2Str(start, "yyyy-MM-dd 00:00:00"), DateUtils.date2Str(end, "yyyy-MM-dd 23:59:59"));
        List<Report> all = Lists.newArrayList();
        Report rpt = null;
        for (String dateStr : dateStrs) {
            rpt = new Report();
            rpt.setDay(dateStr);
            for (Report report : reports) {
                if (StringUtils.equalsIgnoreCase(report.getDay(), dateStr)) {
                    rpt.setPv(report.getPv());
                    rpt.setUv(report.getUv());
                    break;
                }
            }
            for (Report payReport : payReports) {
                if (StringUtils.equalsIgnoreCase(payReport.getDay(), dateStr)) {
                    rpt.setOrderIncrement(payReport.getOrderIncrement());
                    total += payReport.getOrderIncrement();
                    break;
                }
            }
            rpt.setTotal(total);
            all.add(rpt);
        }
        return all;
    }

    private void writeToFile(List<Report> reports, String fileName) throws IllegalAccessException {
        List<String> titleList = this.createDetailTitle(Report.class);
        XSSFWorkbook xssfWorkbook = ExcelUtils.initXSSFWorkbook(0, titleList);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        Field[] fields = Report.class.getDeclaredFields();
        XSSFRow row = null;
        for (int i = 0; i < reports.size(); i++) {
            row = xssfSheet.createRow(i + 1);
            int k = 0;
            for (int j = 0; j < fields.length; j++) {
                XSSFCell cell = row.createCell(j + k);
                fields[j].setAccessible(true);
                cell.setCellValue(fields[j].get(reports.get(i)) == null ? "0" : fields[j].get(reports.get(i)).toString());
            }
        }

        ExcelUtils.writeToFile(xssfWorkbook, new File(fileName));
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
                case "total":
                    titleList.add("截止当日累计订购");
                    break;
                default:
                    break;
            }
        }
        return titleList;
    }
}
