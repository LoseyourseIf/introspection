package xingyu.lu.review.tools.http;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author xingyu.lu
 * @create 2018-06-14 16:03
 **/
public class SpringRestTemplateUsage {

    private static RestTemplate httpsRestTemplate;
    private static RestTemplate httpRestTemplate;


    static {
        httpsRestTemplate = new RestTemplate(new HttpsClientRequestFactory());
        httpRestTemplate = new RestTemplate();
    }

    public static void main(String[] args) {
        httpsRestTemplate.getForObject("url",Object.class,"","","");
    }
}
