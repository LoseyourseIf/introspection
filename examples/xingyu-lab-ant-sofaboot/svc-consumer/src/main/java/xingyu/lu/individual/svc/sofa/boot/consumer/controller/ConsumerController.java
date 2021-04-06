package xingyu.lu.individual.svc.sofa.boot.consumer.controller;

import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.individual.svc.sofa.boot.consumer.service.ConsumeService;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXReply;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.GrpcXRequest;
import xingyu.lu.individual.svc.sofa.boot.facade.builder.SofaXServiceTriple;

import javax.annotation.Resource;

/**
 * @author xingyu.lu
 * @create 2020-12-23 13:29
 **/
@RestController
public class ConsumerController {

    @Resource
    private ConsumeService consumeService;

    @RequestMapping("/echoA")
    public String sofaRpcCallA() {

        return consumeService.echoA();
    }

    @RequestMapping("/echoB")
    public String sofaRpcCallB() {
        return consumeService.echoB();
    }

    @RequestMapping("/echoC")
    public String sofaRpcCallC() {
        return consumeService.echoC();
    }

}
