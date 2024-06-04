package xingyu.lu.lab.springcloud.alibaba.svc.consumer.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xingyu.lu.lab.springcloud.alibaba.common.rest.RestResult;

@FeignClient(name = "cloud-svc-provider", fallbackFactory = CloudSVCProviderFeignFallbackFactory.class)
public interface CloudSVCProviderFeignClient {

    @GetMapping("/echo")
    RestResult<?> echo(@RequestParam("name") String name);

}
