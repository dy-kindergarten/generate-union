package com.reco.generate.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/10/16 10:51
 * @Description: http请求工具类
 */
public class HttpClientUtils {

    /**
     * 执行get请求
     *
     * @param url
     * @return
     */
    @SneakyThrows
    public static String sendGet(String url) {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        client.executeMethod(getMethod);
        return readHttpResult(getMethod);
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param jsonObject
     * @return
     */
    @SneakyThrows
    public static String sendPost(String url, JSONObject jsonObject) {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/json");
        RequestEntity entity = new StringRequestEntity(jsonObject.toString(), "application/json", null);
        postMethod.setRequestEntity(entity);
        client.executeMethod(postMethod);
        return readHttpResult(postMethod);
    }

    /**
     * 获取请求的返回
     *
     * @param httpMethod
     * @return
     * @throws Exception
     */
    private static String readHttpResult(HttpMethodBase httpMethod) throws Exception {
        @Cleanup InputStream is = httpMethod.getResponseBodyAsStream();
        @Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        byte[] datas = os.toByteArray();
        return new String(datas, "UTF-8");
    }
}
