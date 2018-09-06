package com.clouddo.commons.common.util.http;

import com.clouddo.commons.common.service.ApplicationContextRegister;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * rest工具类
 * @author zhongming
 * @since 3.0
 * 2018/6/5下午2:02
 */
public class RestUtil {


    /**
     * 发送post请求
     * @param url
     * @param headers
     * @param parameters
     * @return
     */
    public static ResponseEntity restPost(String url, Map<String, String> headers, MultiValueMap<String, Object> parameters) {

        RestTemplate restTemplate = ApplicationContextRegister.getBean(RestTemplate.class);
        //设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(parameters, httpHeaders);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
        return response;
    }

    /**
     * 发送get请求
     * @param url 请求地址
     * @param headers 请求头
     * @param parameters 请求参数
     * @return
     */
    public static ResponseEntity restGet(String url, Map<String, String> headers, MultiValueMap<String, Object> parameters) {
        RestTemplate restTemplate = ApplicationContextRegister.getBean(RestTemplate.class);
        //设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(parameters, httpHeaders);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Object.class);
        return response;
    }


    /**
     * 发送post请求
     * @param url
     * @param headers
     * @param parameters
     * @return
     */
    public static ResponseEntity restPost(String url, Map<String, String> headers, String parameters) {

        RestTemplate restTemplate = ApplicationContextRegister.getBean(RestTemplate.class);
        //设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }

        HttpEntity<String> requestEntity = new HttpEntity<String>(parameters, httpHeaders);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
        return response;
    }
}
