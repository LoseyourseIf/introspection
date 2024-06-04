package xingyu.lu.lab.springcloud.alibaba.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.*;

/**
 * @Description: Sentinel 配置
 * <p>
 * Sentinel Dashboard 接入
 * -Dcsp.sentinel.dashboard.server=127.0.0.1:18080
 * -Dproject.name=cloud_gateway
 * <p>
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-05-30  16:05
 */
@Configuration
@Slf4j
public class SentinelGatewayConfig {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;


    /**
     * @noinspection SpringJavaInjectionPointsAutowiringInspection
     */
    public SentinelGatewayConfig(
            ObjectProvider<List<ViewResolver>> viewResolversProvider,
            ServerCodecConfigurer serverCodecConfigurer) {

        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;

    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }


    @PostConstruct
    public void initBlockHandlers() {

        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {

            BodyInserter<String, ReactiveHttpOutputMessage> bodyInserter =
                    blockExceptionHandler(throwable);

            log.info("[REQUEST_BLOCKED] REQ = {} , URI = {} ",
                    serverWebExchange.getRequest().getPath(),
                    serverWebExchange.getRequest().getURI());

            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bodyInserter);

        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    /**
     * @description: 熔断
     * <p>
     * TEST
     * curl --location 'http://localhost:18006/httpbin/status/500'
     **/
    @PostConstruct
    private void initDegradeRules() {
        List<DegradeRule> degradeRules = new ArrayList<DegradeRule>();

        DegradeRule rule = new DegradeRule("cloud_svc_provider")
//                .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO) // 设置熔断策略为异常比率
//                .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT) // 设置熔断策略为异常计次
                .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO) // 响应时间模式
                .setCount(0.2) // 1.异常比率阈值 比如 0.5 50% ; 2. 计次则为 几次异常 ; 3. 响应时间 毫秒
                .setTimeWindow(60) // 在 timeWindow 时间内 单位秒，如果触发了熔断条件，则进行熔断。
                .setMinRequestAmount(1); // 设置熔断触发的最小请求数
        degradeRules.add(rule);

        DegradeRule test_rule = new DegradeRule("httpbin_route")
//                .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO) // 设置熔断策略为异常比率
//                .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT) // 设置熔断策略为异常计次
                .setGrade(RuleConstant.DEGRADE_GRADE_RT) // 响应时间模式
                .setCount(300) // 1.异常比率阈值 比如 0.5 50% ; 2. 计次则为 几次异常 ; 3. 响应时间 毫秒
                .setTimeWindow(600) // 在 timeWindow 时间内 单位秒，如果触发了熔断条件，则进行熔断。
                .setMinRequestAmount(2); // 设置熔断触发的最小请求数

        degradeRules.add(test_rule);
        DegradeRuleManager.loadRules(degradeRules);
    }

    /**
     * @description: GatewayFlowRule 限流
     * <p>
     * resource (String)
     * 资源名称，用于标识限流的对象。可以是网关中的 route 名称或者用户自定义的 API 分组名称。
     * <p>
     * resourceMode (int)
     * 规则是针对 API Gateway 的 route（RESOURCE_MODE_ROUTE_ID）
     * 用户在 Sentinel 中定义的 API 分组（RESOURCE_MODE_CUSTOM_API_NAME），默认是 route。
     * <p>
     * grade (int)
     * 限流指标维度，通常分为两种：
     * RuleConstant.FLOW_GRADE_QPS：QPS（每秒请求数）模式，表示每秒钟的请求数量。
     * RuleConstant.FLOW_GRADE_THREAD：线程数模式，表示并发执行的线程数量。
     * <p>
     * count (int)
     * 限流阈值，即在指定的统计时间窗口内，允许通过的最大请求数量。
     * <p>
     * intervalSec (int)
     * 统计时间窗口，单位是秒，默认是 1 秒。例如，如果设置为 1，那么 count 表示每秒钟的限流阈值。
     * <p>
     * controlBehavior (int)
     * 流量整形的控制效果，目前支持两种模式：
     * RuleConstant.CONTROL_BEHAVIOR_DEFAULT：快速失败模式，默认模式，超过限流阈值的请求将直接被限流。
     * RuleConstant.CONTROL_BEHAVIOR_WARM_UP：预热模式，允许系统逐渐增加流量，直到达到稳定状态。
     * <p>
     * burst (int)
     * 应对突发请求时额外允许的请求数目。当突发流量到来时，系统可以暂时处理更多的请求，超过正常阈值
     * 但不超过 count + burst 的请求将被允许通过。
     * <p>
     * maxQueueingTimeoutMs (int)
     * 匀速排队模式下的最长排队时间，单位是毫秒，仅在匀速排队模式下生效。如果请求在该时间内未得到处理，则会被丢弃。
     * <p>
     * paramItem (GatewayParamFlowItem)
     * 参数限流配置。如果不提供，则代表不针对参数进行限流，该网关规则将会被转换成普通流控规则；否则会转换成热点规则。
     * GatewayParamFlowItem 包含以下属性：
     * parseStrategy：从请求中提取参数的策略，支持来源 IP、Host、任意 Header 和任意 URL 参数。
     * fieldName：若提取策略选择 Header 模式或 URL 参数模式，则需要指定对应的 header 名称或 URL 参数名称。
     * pattern：参数值的匹配模式，只有匹配该模式的请求属性值会纳入统计和流控。
     * matchStrategy：参数值的匹配策略，支持精确匹配、子串匹配和正则匹配。
     **/
    @PostConstruct
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();

        rules.add(new GatewayFlowRule("cloud_svc_provider")
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
                .setCount(2)
                .setIntervalSec(10)
                .setBurst(2)
                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
                )

        );

        rules.add(new GatewayFlowRule("httpbin_route")
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
                .setCount(10)
                .setIntervalSec(10)
                .setBurst(5)
                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT)
        );

        GatewayRuleManager.loadRules(rules);
    }


    /*
     * @description: 不同类型 BlockException 分开处理
     * @author: LuXingYu
     * @date: 2024/5/31 16:20
     * @param: [throwable]
     * @return: org.springframework.web.reactive.function.BodyInserter<java.lang.String,org.springframework.http.ReactiveHttpOutputMessage>
     **/
    private BodyInserter<String, ReactiveHttpOutputMessage> blockExceptionHandler(Throwable throwable) {

        if (throwable instanceof ParamFlowException) {

            ParamFlowException FlowException = (ParamFlowException) throwable;
            log.info("[REQUEST_BLOCKED] Current Limiting Protection RootCause = {} ",
                    ExceptionUtils.getRootCauseMessage(FlowException));
            // 限流保护
            return BodyInserters.fromValue(
                    "{'code': -999, 'message': '服务短时间请求次数达到上限，请稍后再试！'}");

        } else if (throwable instanceof DegradeException) {
            DegradeException degradeException = (DegradeException) throwable;
            log.info("[REQUEST_BLOCKED] Fuse Protection RootCause = {} ",
                    ExceptionUtils.getRootCauseMessage(degradeException));
            // 熔断保护
            return BodyInserters.fromValue(
                    "{'code': -998, 'message': '服务异常，请稍后再试！'}");

        } else if (throwable instanceof SystemBlockException) {
            SystemBlockException systemBlockException = (SystemBlockException) throwable;
            log.info("[REQUEST_BLOCKED] System Protection RootCause = {} ",
                    ExceptionUtils.getRootCauseMessage(systemBlockException));
            // 系统保护
            return BodyInserters.fromValue(
                    "{'code': -990, 'message': '服务繁忙，请稍后再试！'}");
        } else if (throwable instanceof BlockException) {

            BlockException blockException = (BlockException) throwable;
            log.info("[REQUEST_BLOCKED] BlockException RootCause = {} ",
                    ExceptionUtils.getRootCauseMessage(blockException));
            return BodyInserters.fromValue(
                    "{'code': -990, 'message': '服务繁忙，请稍后再试！'}");
        } else {
            log.info("[REQUEST_BLOCKED] Exception RootCause = {} ",
                    ExceptionUtils.getRootCauseMessage(throwable));
            return BodyInserters.fromValue(
                    "{'code': -990, 'message': '服务繁忙，请稍后再试！'}");
        }

    }
}
