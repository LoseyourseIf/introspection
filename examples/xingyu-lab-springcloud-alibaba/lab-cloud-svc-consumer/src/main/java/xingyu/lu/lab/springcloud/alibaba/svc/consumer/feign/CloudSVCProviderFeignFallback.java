package xingyu.lu.lab.springcloud.alibaba.svc.consumer.feign;

import lombok.Getter;
import lombok.Setter;
import xingyu.lu.lab.springcloud.alibaba.config.rest.RestResult;

/**
 * @Description: 降级处理类
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-06-04  14:33
 */
@Setter
@Getter
public class CloudSVCProviderFeignFallback implements CloudSVCProviderFeignClient {

    private Throwable cause;

    public CloudSVCProviderFeignFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public RestResult<?> echo(String name) {
        return RestResult.failed("provider 暂时无法调用 name(" + name + ")");
    }

}
