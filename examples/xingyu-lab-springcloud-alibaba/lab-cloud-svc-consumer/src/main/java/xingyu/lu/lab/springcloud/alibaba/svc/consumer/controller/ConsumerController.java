package xingyu.lu.lab.springcloud.alibaba.svc.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.lab.springcloud.alibaba.config.rest.RestResult;
import xingyu.lu.lab.springcloud.alibaba.svc.consumer.feign.CloudSVCProviderFeignClient;

/**
 * @Description: ConsumerController
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-06-03  17:19
 */

@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    private CloudSVCProviderFeignClient feignClient;

    @GetMapping("/call")
    public RestResult<?> call(@RequestParam String name) {
        log.info("[call][ consumer 调用 cloud-svc-provider ({}) ]", name);
        return feignClient.echo(name);

    }
}
