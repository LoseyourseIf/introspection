package xingyu.lu.lab.springcloud.alibaba.svc.consumer.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xingyu.lu.lab.springcloud.alibaba.config.rest.RestResult;

@FeignClient(name = "cloud-svc-provider", contextId = "cloudSVCProviderFeignClient")
public interface CloudSVCProviderFeignClient {

    @GetMapping("/echo")
    RestResult<String> echo(@RequestParam("name") String name);

}
