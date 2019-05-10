package com.reco.generate.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 从文件中获取歌曲id
     *
     * @param fileName
     * @return
     */
    public static String findSongIds(String fileName) {
        String songIds = null;
        File file = new File(getFileTempPath(fileName));
        boolean isEnd = false;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String str = null;
        if (file.exists()) {
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                while (!isEnd && (str = bufferedReader.readLine()) != null) {
                    if (StringUtils.containsIgnoreCase(str, "int[] ids") && StringUtils.containsIgnoreCase(str, "=")) {
                        int start = str.lastIndexOf("{");
                        int end = str.lastIndexOf("}");
                        songIds = str.substring(start + 1, end);
                        isEnd = true;
                    }
                }
                logger.info("========= songIds: " + songIds + " ==========");
            } catch (FileNotFoundException e) {
                logger.error("未找到文件, " + e.getMessage());
            } catch (IOException e) {
                logger.error("I/O 异常, " + e.getMessage());
            } finally {
                try {
                    if (null != bufferedReader) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    logger.error("关闭缓冲读取流异常, " + e.getMessage());
                }
                try {
                    if(null != fileReader) {
                        fileReader.close();
                    }
                } catch (IOException e) {
                    logger.error("关闭文件读取流异常异常, " + e.getMessage());
                }
            }
        }
        return songIds;
    }

    /**
     * 获得文件本地存放地址
     *
     * @param fileName
     * @return
     */
    public static String getFileTempPath(String fileName) {
        return Constant.getTempPath() + fileName;
    }
}
