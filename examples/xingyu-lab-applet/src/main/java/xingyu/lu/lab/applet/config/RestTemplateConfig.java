package xingyu.lu.lab.applet.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xingyu.lu
 * @create 2021-05-18 10:00
 **/
@Configuration
public class RestTemplateConfig {

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;

    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 10;

    private static final int DEFAULT_MAX_TIME_OUT = 3000;

    private static final int DEFAULT_TIME_OUT = 3000;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        converters.add(fastJsonHttpMessageConverter);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RequestConfig requestConfig = RequestConfig.custom()
                // 连接超时
                .setConnectTimeout(DEFAULT_TIME_OUT)
                // 排队超时
                .setConnectionRequestTimeout(DEFAULT_TIME_OUT)
                // 数据读取超时
                .setSocketTimeout(DEFAULT_TIME_OUT)
                .build();
        requestFactory.setConnectTimeout(DEFAULT_TIME_OUT);
        requestFactory.setReadTimeout(DEFAULT_TIME_OUT);
        SocketConfig socketConfig = SocketConfig
                .custom()
                .setSoKeepAlive(false)
                .setTcpNoDelay(true)
                .build();
        PoolingHttpClientConnectionManager pm = new PoolingHttpClientConnectionManager();
        pm.setDefaultSocketConfig(socketConfig);
        pm.closeIdleConnections(DEFAULT_MAX_TIME_OUT, TimeUnit.MICROSECONDS);
        pm.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        pm.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(pm)
                .build();
        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }
}
