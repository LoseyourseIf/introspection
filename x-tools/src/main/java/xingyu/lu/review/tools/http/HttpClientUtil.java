package xingyu.lu.review.tools.http;/**
 * Created by lxy on 17/7/12.
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description  Http请求工具类
 * @author  xingyu.lu
 * @date    18/5/7 11:10
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    // 连接超时时间
    private static final int CONNECTION_TIMEOUT = 3000;//3秒
    // 接收超时时间
    private static final int READ_DATA_TIMEOUT = 6000;//6秒

    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpclient = null;

    static {
        connManager = new PoolingHttpClientConnectionManager();
        httpclient = HttpClients.custom().setConnectionManager(connManager).build();
    }

    /**
     * 创建 HTTPS 请求 Client
     * Https sslClient
     *
     * @return
     */
    public static CloseableHttpClient createSSLClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * Post请求（默认超时时间）
     *
     * @param url
     * @param data
     * @param encoding
     * @return
     */
    public static String post(String url, Map<String, Object> data, String encoding) throws IOException {
        return post(url, CONNECTION_TIMEOUT, READ_DATA_TIMEOUT, data, encoding);
    }

    /**
     * @Describe 设置超时时间的post
     * @Auther xingyu.lu
     * @Date 17/7/17 16:29
     */
    public static String post(String url, int timeout, Map<String, Object> data, String encoding) throws IOException {
        return post(url, timeout, timeout, data, encoding);
    }

    /**
     * @Describe 带 Authorization 头的 post 请求
     * @Auther xingyu.lu
     * @Date 17/7/14 15:52
     */
    public static String post(String url, Map<String, Object> data, String encoding, String auth) throws IOException {
        return postWithAuth(url, CONNECTION_TIMEOUT, READ_DATA_TIMEOUT, data, encoding, auth);
    }

    /**
     * @Describe Post body
     * @Param
     * @Return
     * @Auther xingyu.lu
     * @Date 17/7/17 16:32
     */
    public static String postBody(String url, String body, String encoding, int connectTimeout, int readTimeout) throws IOException {
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(readTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout)
                .setExpectContinueEnabled(false).build();
        post.setConfig(requestConfig);

        if (StringUtils.isNotBlank(body)) {
            StringEntity formEntity = new StringEntity(body, encoding);
            post.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        if (url.startsWith("https")) {//https
            response = createSSLClient().execute(post);
        } else {
            response = httpclient.execute(post);
        }

        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                String str = EntityUtils.toString(entity, encoding);
                return str;
            }
        } finally {
            if (entity != null) {
                entity.getContent().close();
            }
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * @Describe Post JSON
     * @Param
     * @Return
     * @Auther xingyu.lu
     * @Date 17/7/17 14:37
     */
    public static String postJson(String url, String json, String encoding) throws IOException {
        HttpPost post = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(READ_DATA_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
                .setExpectContinueEnabled(false).build();
        post.setConfig(requestConfig);


        post.addHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("Accept", "application/json");

        if (StringUtils.isNotBlank(json)) {
            StringEntity formEntity = new StringEntity(json, encoding);
            post.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;

        if (url.startsWith("https")) {//https
            response = createSSLClient().execute(post);
        } else {
            response = httpclient.execute(post);
        }

        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                String str = EntityUtils.toString(entity, encoding);
                return str;
            }
        } finally {
            if (entity != null) {
                entity.getContent().close();
            }
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


    /**
     * 带auth 头的post重载
     *
     * @param url
     * @param connectTimeout
     * @param readTimeout
     * @param data
     * @param encoding
     * @param auth
     * @return
     * @throws IOException
     */
    public static String postWithAuth(String url, int connectTimeout, int readTimeout, Map<String, Object> data, String encoding, String auth) throws IOException {
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(readTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout)
                .setExpectContinueEnabled(false).build();
        post.setConfig(requestConfig);

        if (null != data && !data.isEmpty()) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : data.keySet()) {
                formparams.add(new BasicNameValuePair(key, data.get(key).toString()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, encoding);
            post.setEntity(formEntity);
        }
        post.addHeader("Content-type", "application/x-www-form-urlencoded");
        post.addHeader("Authorization", auth);
        CloseableHttpResponse response = null;
        if (url.startsWith("https")) {//https
            response = createSSLClient().execute(post);
        } else {
            response = httpclient.execute(post);
        }
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                String str = EntityUtils.toString(entity, encoding);
                return str;
            }
        } finally {
            if (entity != null) {
                entity.getContent().close();
            }
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * Post请求
     *
     * @param url
     * @param connectTimeout
     * @param readTimeout
     * @param data
     * @param encoding
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static String post(String url, int connectTimeout, int readTimeout, Map<String, Object> data, String encoding) throws IOException {
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(readTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout)
                .setExpectContinueEnabled(false).build();
        post.setConfig(requestConfig);

        if (null != data && !data.isEmpty()) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : data.keySet()) {
                formparams.add(new BasicNameValuePair(key, data.get(key).toString()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, encoding);
            post.setEntity(formEntity);
        }
        CloseableHttpResponse response = null;
        if (url.startsWith("https")) {//https
            response = createSSLClient().execute(post);
        } else {
            response = httpclient.execute(post);
        }

        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                String str = EntityUtils.toString(entity, encoding);
                return str;
            }
        } finally {
            if (entity != null) {
                entity.getContent().close();
            }
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * 如果失败尝试3次
     *
     * @param url
     * @param encoding
     * @return
     */
    public static String tryGet(String url, String encoding) {
        String resultStr = "";
        for (int i = 0; i < 3; i++) {
            try {
                resultStr = get(url, encoding);
                break;
            } catch (Exception e) {
                logger.error("请求异常count:{} ", i, e);
            }
        }
        return resultStr;
    }

    /**
     * GET 请求（默认超时时间）
     *
     * @param url
     * @param encoding
     * @return
     */
    public static String get(String url, String encoding) throws IOException {
        return get(url, null, CONNECTION_TIMEOUT, READ_DATA_TIMEOUT, encoding);
    }

    public static String get(String url, Map<String, String> cookies, String encoding) throws IOException {
        return get(url, cookies, CONNECTION_TIMEOUT, READ_DATA_TIMEOUT, encoding);
    }

    public static String get(String url, Map<String, String> cookies, int timeout, String encoding) throws IOException {
        return get(url, cookies, timeout, timeout, encoding);
    }

    public static String get(String url, Map<String, String> cookies, int connectTimeout, int readTimeout, String encoding) throws IOException {
        HttpGet get = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(readTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout)
                .setExpectContinueEnabled(false).build();
        get.setConfig(requestConfig);
        if (cookies != null && !cookies.isEmpty()) {
            StringBuilder buffer = new StringBuilder(128);
            for (String cookieKey : cookies.keySet()) {
                buffer.append(cookieKey).append("=").append(cookies.get(cookieKey)).append("; ");
            }
            // 设置cookie内容
            get.setHeader(new BasicHeader("Cookie", buffer.toString()));
        }
        CloseableHttpResponse response = null;
        if (url.startsWith("https")) {//https
            response = createSSLClient().execute(get);
        } else {
            response = httpclient.execute(get);
        }
        HttpEntity entity = response.getEntity();
        try {
            if (entity != null) {
                String str = EntityUtils.toString(entity, encoding);
                return str;
            }
        } finally {
            if (entity != null) {
                entity.getContent().close();
            }
            if (response != null) {
                response.close();
            }
        }

        return null;
    }

    /**
     * map转成queryStr
     *
     * @param paramMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String mapToQueryStr(Map<String, String> paramMap) {
        StringBuffer strBuff = new StringBuffer();
        for (String key : paramMap.keySet()) {
            strBuff.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        return strBuff.substring(0, strBuff.length() - 1);
    }


}
