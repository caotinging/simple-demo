package com.caotinging.wsdemo.https;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultClientConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

/**
 * http 连接池
 * @author caoting
 * @date 2018年11月17日
 */
@Component
@Slf4j
public class RestTemplateFactory {

    /**
     * 连接池最大连接数
     */
	private static Integer maxTotal = 100;

    /**
     * 最多转发次数
     */
	private static Integer maxPerRoute = 20;
	
    private static RestTemplate restTemplate;

    /**
     * 忽略Http证书验证方式
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * HTTP/HTTPS连接池初始化
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * 
     * @author caoting
     * @date 2018年11月19日
     */
    public static void init() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //region HttpClient构造
        //region 连接池httpClientConnectionManager
        //采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext, new NoopHostnameVerifier()))
                .build();
        
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);

        //endregion

        //region 重试机制 retryHandler
        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(2, true);
        //endregion

        HttpClient httpClient = HttpClientBuilder.create()
                .setRetryHandler(retryHandler)
                .setConnectionManager(httpClientConnectionManager)
                //默认客户端连接重用策略
                .setConnectionReuseStrategy(new DefaultClientConnectionReuseStrategy())
                //保持长连接配置，需要在头添加Keep-Alive
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setConnectionManagerShared(true)
                .build();
        //endregion
        //region http连接工厂
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setHttpClient(httpClient);
        //连接超时
        httpRequestFactory.setConnectTimeout(2000);
        // 读写超时
        httpRequestFactory.setReadTimeout(30000);
        //endregion
        restTemplate = new RestTemplate(httpRequestFactory);
        /*restTemplate.getInterceptors().add(new RestTemplateTraceInterceptor());
        //异常处理逻辑
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());*/
    }

    /**
     * 根据返回的Class进行请求
     *
     * @param url
     * @param headers
     * @param responseType
     * @param body
     * @param args
     * @return org.springframework.http.ResponseEntity<T>
     */
    public static <T> ResponseEntity<T> postForEntity(String url, HttpHeaders headers, Object body, Class<T> responseType, Object... args) {
        return exchange(url, HttpMethod.POST, headers, body, responseType, args);
    }

    /**
     * 根据包装的类型进行请求
     *
     * @param url
     * @param httpHeaders
     * @param body
     * @param typeReference
     * @param args
     * @return org.springframework.http.ResponseEntity<T>
     * @see ParameterizedTypeReference
     */
    public static <T> ResponseEntity<T> postForObject(String url, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> typeReference, Object... args) {
        return exchange(url, HttpMethod.POST, httpHeaders, body, typeReference, args);
    }

    /**
     * 根据 ParameterizedTypeReference 类型获取信息
     *
     * @param url
     * @param httpHeaders
     * @param typeReference
     * @param args
     * @return <T> ResponseEntity<T>
     */
    public static <T> ResponseEntity<T> getForEntity(String url, HttpHeaders httpHeaders, ParameterizedTypeReference<T> typeReference, Object... args) {
        return exchange(url, HttpMethod.GET, httpHeaders, null, typeReference, args);
    }

    /**
     * 根据Class类型获取返回的信息
     *
     * @param url
     * @param headers
     * @param responseType
     * @param args
     * @return org.springframework.http.ResponseEntity<T>
     */
    public static <T> ResponseEntity<T> getForEntity(String url, HttpHeaders headers, Class<T> responseType, Object... args) {
        return exchange(url, HttpMethod.GET, headers, null, responseType, args);
    }


    private static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> responseType, Object... args) {
        headers = resolveHttpHeaders(headers);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, args);
        return responseEntity;
    }

    @SuppressWarnings("unchecked")
    private static <T> ResponseEntity<T> exchange(String url, HttpMethod httpMethod, HttpHeaders headers, Object body, ParameterizedTypeReference typeReference, Object... args) {
        headers = resolveHttpHeaders(headers);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body, headers);
        ResponseEntity<T> responseEntity = (ResponseEntity<T>) restTemplate.exchange(url, httpMethod, httpEntity, typeReference, args);
        return responseEntity;
    }

    /**
     * Http GET请求Url里可以带参数的请求
     * @param url             请求URL
     * @param respType        返回值类型Class
     * @param pathVariables   参数
     * @param <T>             返回类型
     * @return
     */
    public static <T> T httpGetPathVariable(String url, Class<T> respType, Object... pathVariables){
        return restTemplate.getForObject(url, respType, pathVariables);
    }

    /**
     * Http GET请求Url里不带参数的请求
     * @param url             请求URL
     * @param respType        返回类型Class
     * @param urlVariableMap  参数
     * @param <T>             返回类型
     * @return
     */
    public static <T> T httpGetUrlVariable(String url, Class<T> respType, Map<String, Object> urlVariableMap){
        String urlParams = "";

        if(urlVariableMap!=null && urlVariableMap.size()>0){

            for(String m : urlVariableMap.keySet()){
                if(!StringUtils.isEmpty(urlParams)) urlParams += "&";
                urlParams += m + "=" + JSON.toJSONString(urlVariableMap.get(m));
            }
        }

        log.debug("请求格式：" + url + "?" + urlParams);
        return restTemplate.getForObject(url + "?" + urlParams, respType, urlVariableMap);
    }

    /**
     * Http GET请求Url里不带参数的请求
     * @param url             请求URL
     * @param respType        返回类型Class
     * @param urlVariableMap  参数
     * @param <T>             返回类型
     * @return
     */
    public static <T> T httpGetUrlVariableWithParamOrder(String url, Class<T> respType, Map<String, Object> urlVariableMap,String orderParam){
        String urlParams = "";
        if(!StringUtils.isEmpty(orderParam)){
            urlParams += orderParam;
        }

        if(urlVariableMap!=null && urlVariableMap.size()>0){

            for(String m : urlVariableMap.keySet()){
                if(!StringUtils.isEmpty(urlParams)) urlParams += "&";
                urlParams += m + "={" + m + "}";
            }
        }
        log.debug("请求格式：" + urlParams);
        return restTemplate.getForObject(url + "?" + urlParams, respType, urlVariableMap);
    }

    /**
     * Http Post请求   MediaType  application/json
     * @param url       请求类型
     * @param respType  返回类型Class
     * @param obj       请求对象信息
     * @param <T>       返回类型
     * @return
     */
    public static <T> T httpPostMediaTypeJson(String url, Class<T> respType, Object obj){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(obj), headers);
        log.debug("请求格式：" + formEntity);
        return restTemplate.postForObject(url, formEntity, respType);
    }

    /**
     * Http Post请求   MediaType  application/x-www-form-urlencoded
     * @param url       请求类型
     * @param respType  返回类型Class
     * @param obj       请求对象信息
     * @param <T>       返回类型
     * @return
     */
    public static <T> T httpPostMediaTypeFromData(String url, Class<T> respType, Map<String, ?> obj){
        LinkedMultiValueMap multMap = new LinkedMultiValueMap();
        if(obj!=null && obj.size()>0){
            for(String m : obj.keySet()){
                multMap.add(m, obj.get(m));
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
//        headers.add("Accept", MediaType.TEXT_PLAIN_VALUE);
//        headers.add("Accept", MediaType.ALL_VALUE);
        HttpEntity<LinkedMultiValueMap> req = new HttpEntity<LinkedMultiValueMap>(multMap, headers);
        log.debug("请求格式：" + req);

        return restTemplate.postForObject(url, req ,respType);
    }

    /**
     * 没有就设置默认的 HttpHeaders
     *
     * @see HttpHeaders#setContentType(MediaType)
     * @see MediaType#APPLICATION_JSON
     * @see MediaType#APPLICATION_JSON_UTF8 (部分版本不支持)
     */
    private static HttpHeaders resolveHttpHeaders(HttpHeaders headers) {
        if (headers == null) {
            headers = new HttpHeaders();
            headers.set("Content-Type", "application/json;charset=UTF-8");
            headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        }
        return headers;
    }

    public static void testHttps() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RestTemplateFactory.init();
        String url = "https://www.baidu.com";
        String resp = httpGetPathVariable(url,String.class);
        log.info("百度连接测试结果:");
        log.info(resp);
    }
}
