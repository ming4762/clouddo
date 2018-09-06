package com.clouddo.commons.common.util.http;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http工具类
 * @author zhongming
 * @since 3.0
 * 2018/6/5下午3:59
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 发送post请求
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param parameters 请求参数
     * @return 请求结果
     * @throws IOException
     */
    public static HttpEntity httpPost(String url, Map<String, String> headers, Map<String, Object> parameters) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost(url);
        //设置请求头
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            post.setHeader(entry.getKey(), entry.getValue());
        }

        //设置参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //设置参数
        for(Map.Entry<String, Object> entry : parameters.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
        HttpResponse response = httpClient.execute(post);
        //获取状态码
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return response.getEntity();
        }
        logger.warn("发送POST请求失败，URL：{}，请求头：{}，请求参数：{}，错误码：{}", url, headers, parameters, response.getStatusLine().getStatusCode());
        return null;

    }


    /**
     * 发送post请求
     * @param url 请求的URL
     * @param headers 请求头信息
     * @param parameters 请求参数
     * @return 请求结果
     * @throws IOException
     */
    public static HttpResponse httpGet(String url, Map<String, String> headers, Map<String, Object> parameters) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBuilder stringBuffer = new StringBuilder(url);
        if (parameters != null) {
            //设置参数
            int i = 0;
            for(Map.Entry<String, Object> entry : parameters.entrySet()) {
                if(i == 0) {
                    stringBuffer.append("?").append(entry.getKey()).append("=").append(entry.getValue());
                } else {
                    stringBuffer.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
                i++;
            }
        }

        HttpGet get = new HttpGet(stringBuffer.toString());
        if (headers != null) {
            //设置请求头
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }

        HttpResponse response = httpClient.execute(get);
        //获取状态码
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            logger.warn("发送GET请求失败，URL：{}，请求头：{}，请求参数：{}，错误码：{}", url, headers, parameters, response.getStatusLine().getStatusCode());
        }
        return response;

    }

    public static InputStream downFile(String src) throws IOException {
        return downFile(URI.create(src));
    }

    /**
     * 从网络上下载文件
     *
     * @param uri
     * @return
     * @throws IOException
     */
    public static InputStream downFile(URI uri) throws IOException {
        HttpResponse httpResponse;
        try {
            Request request = Request.Get(uri);
            HttpHost httpHost = URIUtils.extractHost(uri);
            if (!StringUtils.isEmpty(httpHost.getHostName())) {
                request.setHeader("Host", httpHost.getHostName());
            }
            request.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            httpResponse = request.execute().returnResponse();
        } catch (Exception e) {
            logger.error("远程请求失败，url=" + uri, e);
            throw new FileNotFoundException();
        }

        int code = httpResponse.getStatusLine().getStatusCode();
        if (code != 200) {
            throw new FileNotFoundException();
        }

        return httpResponse.getEntity().getContent();
    }
}
