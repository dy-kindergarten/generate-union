package com.reco.generate.utils;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/10/16 10:14
 * @Description: 压缩/解压工具类
 */
public class ZArchiverUtils {

    /**
     * 解压gz
     *
     * @param inFileName
     * @return
     */
    @SneakyThrows
    public static void decompressFile(String inFileName) {
        @Cleanup FileInputStream fis = new FileInputStream(inFileName);
        @Cleanup GZIPInputStream gzis = new GZIPInputStream(fis);
        String outFileName = getFileName(inFileName);
        @Cleanup FileOutputStream fos = new FileOutputStream(outFileName);
        byte[] buf = new byte[1024];
        int len;
        while ((len = gzis.read(buf)) > 0) {
            fos.write(buf, 0, len);
        }
    }

    /**
     * 获取文件名
     *
     * @param inFileName
     * @return
     */
    public static String getFileName(String inFileName) {
        String fileName = "";
        int i = inFileName.lastIndexOf('.');
        if (i > 0 && i < inFileName.length() - 1) {
            fileName = inFileName.substring(0, i);
        }
        return fileName;
    }
}
