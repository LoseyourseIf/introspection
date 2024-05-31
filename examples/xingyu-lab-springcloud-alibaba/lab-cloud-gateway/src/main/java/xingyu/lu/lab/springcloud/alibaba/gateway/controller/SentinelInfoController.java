package xingyu.lu.lab.springcloud.alibaba.gateway.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

/**
 * @Description: GatewayRulesController
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-05-31  09:48
 */

@RestController
public class SentinelInfoController {

    @GetMapping("/SentinelRules")
    public Mono<Set<GatewayFlowRule>> apiGateway() {
        return Mono.just(GatewayRuleManager.getRules());
    }

    @GetMapping("/SentinelFlows")
    public Mono<List<FlowRule>> apiFlow() {
        return Mono.just(FlowRuleManager.getRules());
    }

}
