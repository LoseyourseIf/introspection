package xingyu.lu.lab.springcloud.alibaba.svc.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.lab.springcloud.alibaba.config.rest.RestResult;

/**
 * @Description: ProviderController
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-06-03  16:31
 */
@RestController
@Slf4j
public class ProviderController {

    @Value("${server.port}")
    private Integer serverPort;

    @GetMapping("/echo")
    public RestResult<String> echo(@RequestParam String name) {
        // 记录被调用的日志
        log.info("[echo][ provider-({}) 被调用 name({}) ]", serverPort, name);

        return RestResult.success("provider-(" + serverPort + ") 被调用 name(" + name + ")");
    }

}
