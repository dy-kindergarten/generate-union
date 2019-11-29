package com.reco.generate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.reco.generate.bo.enumEntity.DateType;
import com.reco.generate.service.ReportService;
import com.reco.generate.utils.Constant;
import com.reco.generate.utils.DateUtils;
import com.reco.generate.utils.ExcelUtils;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/6/14 10:49
 * @Description: todo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionTest {

    @Autowired
    private ReportService reportService;

    @Test
    @SneakyThrows
    public void mergeSong() {
        List<String> list = Lists.newArrayList();
        List<String> songNames1 = Lists.newArrayList("双鱼", "我要的飞翔", "不说再见", "给你给我", "那些花儿", "叶子", "望", "突然好想你", "桃花诺 ", "千古", "凉凉", "画心", "祝你生日快乐", "共产儿童团歌", "校园多美好", "我们从小讲礼貌", "稍息立正站好", "大手牵小手", "小猪小猪肥嘟嘟", "调皮的小羊羊", "哈巴狗", "弟弟不听话", "小马过河", "快乐兔小贝", "刻不容缓", "天长地久", "进行式", "心的发现", "如果我爱你", "星月", "爱是永恒", "最初的梦想", "爱情字典", "爱的面包", "情歌", "爱的运气曲奇", "对数歌", "七色光", "愿望", "祖国和我", "读书郎", "小白兔", "流浪小猫眯", "凤凰于飞", "顺其自然", "大海", "叶子", "爱上你不如爱上海", "神话", "快乐你我他", "我是一只小小鸟", "最美的图画", "星星的海洋", "萌宝过大年", "御龙归字谣", "做你的心上人", "小鸡小鸡", "卜卦", "小苹果", "最炫民族风", "撞", "隔壁泰山", "打字机", "天生一对", "两个人一个人", "light", "第三者", "孤单", "下完这场雨", "逞强", "一个人过");
        XSSFWorkbook xssfWorkbook = ExcelUtils.openExcel("C:\\Users\\dell\\Desktop\\陕西广电 - 未播放统计.xlsx");
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            String songName = row.getCell(2).getStringCellValue();
            if (StringUtils.isNotBlank(songName) && !songNames1.contains(songName)) {
                list.add(songName);
            }
        }
        System.out.println(list.size());
    }

    @Test
    public void valueTest() {
        System.out.println(Constant.getSongTestTempFile());
    }

    @Test
    @SneakyThrows
    public void testList() {
        // 数据库导出
        Map<String, String> dbData = this.getDataMap("C:\\Users\\hasee\\Desktop\\1.xlsx");
        Map<String, String> netData = this.getDataMap("C:\\Users\\hasee\\Desktop\\2.xlsx");
        Set<String> dbKeySet = dbData.keySet();
        Set<String> netKeySet = netData.keySet();
        dbKeySet.removeAll(netKeySet);
        List<String> titles = Lists.newArrayList("iptv_code", "name");
        XSSFWorkbook output = ExcelUtils.initXSSFWorkbook(0, titles);
        XSSFSheet xssfSheet = output.getSheetAt(0);
        int i = 0;
        for (String key : dbKeySet) {
            XSSFRow row = xssfSheet.createRow(i + 1);
            XSSFCell cell0 = row.createCell(0);
            XSSFCell cell1 = row.createCell(1);
            cell0.setCellValue(key);
            cell1.setCellValue(dbData.get(key));
            System.out.println("====" + key + "==" + dbData.get(key) + "====");
            i++;
        }
        File outputFile = new File("C:\\Users\\hasee\\Desktop\\output.xlsx");
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        outputFile.setWritable(true);
        ExcelUtils.writeToFile(output, outputFile);
    }

    @SneakyThrows
    private Map<String, String> getDataMap(String filePath) {
        Map<String, String> dataMap = Maps.newHashMap();
        XSSFWorkbook xssfWorkbook = ExcelUtils.openExcel(filePath);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            String iptvCode = row.getCell(0).getRawValue();
            String songName = row.getCell(1).getStringCellValue();
            dataMap.put(iptvCode, songName);
        }
        return dataMap;
    }

    @Test
    public void reportTest() {
        Date startDate = DateUtils.str2Date("20191101", "yyyyMMdd");
        Date endDate = DateUtils.str2Date("20191128", "yyyyMMdd");
        reportService.report(startDate, endDate, DateType.other);
    }

    @Test
    @SneakyThrows
    public void textEditTest() {
        File file = new File("C:\\Users\\dell\\Desktop\\test.txt");
        @Cleanup FileReader fr = new FileReader(file);
        @Cleanup BufferedReader bf = new BufferedReader(fr);
        String line = null;
        XSSFWorkbook xssfWorkbook = null;
        xssfWorkbook = ExcelUtils.initXSSFWorkbook(0, Lists.newArrayList());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        int i = 0;
        while ((line = bf.readLine()) != null) {
            line = new String(line.getBytes(), "UTF-8");
            String[] strs = line.split("\\|");
            XSSFRow row = xssfSheet.createRow(i + 1);
            for (int j = 0; j < strs.length; j++) {
                XSSFCell cell = row.createCell(j);
                if (j == 4) {
                    String verticalImg = "http://125.72.108.108/iptv_hd/images/HD/photos/artist/c_" + strs[4].trim().replaceAll("jpg", "png");
                    cell.setCellValue(verticalImg);
                } else if (j == 5) {
                    cell.setCellValue(StringUtils.equalsIgnoreCase("5", strs[5].trim()) ? 0 : 1);
                } else {
                    cell.setCellValue(strs[j].trim());
                }
            }
            i++;
        }
        ExcelUtils.writeToFile(xssfWorkbook, new File("C:\\Users\\dell\\Desktop\\test.xlsx"));
    }
}
