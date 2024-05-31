package xingyu.lu.lab.springcloud.alibaba.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;

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
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: Sentinel 配置
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

            log.info("[REQUEST_BLOCKED] CauseMessage = {} ",
                    ExceptionUtils.getRootCauseMessage(throwable));

            log.info("[REQUEST_BLOCKED] REQ = {} , URI = {} ",
                    serverWebExchange.getRequest().getPath(),
                    serverWebExchange.getRequest().getURI());

            return ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(
                            "{'code': -999, 'message': '服务不可用，请稍后再试！'}"));

        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    /**
     * @description: 熔断
     **/
    @PostConstruct
    private void initDegradeRules() {

        DegradeRule rule = new DegradeRule("aliyun_route")
                .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO) // 设置熔断策略为异常比率
                .setCount(0.5) // 设置异常比率阈值，比如 50%
                .setTimeWindow(10) // 设置熔断时长，比如 10 秒
                .setMinRequestAmount(5); // 设置熔断触发的最小请求数

        DegradeRuleManager.loadRules(Collections.singletonList(rule));
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

        rules.add(new GatewayFlowRule("aliyun_route")
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
                .setCount(2)
                .setIntervalSec(10)
                .setBurst(2)
                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT)
        );

        GatewayRuleManager.loadRules(rules);
    }

    @Bean
    public CorsWebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOriginPattern("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
