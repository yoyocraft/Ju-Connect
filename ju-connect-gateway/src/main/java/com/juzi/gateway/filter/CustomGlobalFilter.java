package com.juzi.gateway.filter;

import com.juzi.sdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author codejuzi
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList(
            "127.0.0.1",
            "192.168.0.101",
            "0:0:0:0:0:0:0:1"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求日志处理
        ServerHttpRequest request = exchange.getRequest();
        doReqLog(request);
        // 黑白名单
        ServerHttpResponse response = exchange.getResponse();
        String sourceAddr = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        if (!IP_WHITE_LIST.contains(sourceAddr)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        // 用户鉴权
        try {
            doUserAuth(request);
        } catch (Exception e) {
            return handleNoAuth(response);
        }

        // 请求的模拟接口是否存在
        // TODO: 2023/7/2 去数据库查询

        // 请求转发，调用模拟接口
        return handleResponse(exchange, chain);
    }

    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            // 初始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓冲区工厂，拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();

            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            // 对象是响应式的
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // TODO: 2023/7/2 调用成功，扣减用户调用次数
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                // 释放内存
                                DataBufferUtils.release(dataBuffer);
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                sb2.append("<--- {} {} \n");
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);
                                sb2.append(data);
                                log.info(sb2.toString(), rspArgs.toArray());
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 调用失败，返回错误的验证码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };

                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 降级处理返回数据
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private void doReqLog(ServerHttpRequest request) {
        log.info("请求唯一标识：{}", request.getId());
        log.info("请求路径：{}", request.getPath().value());
        log.info("请求方法类型：{}", request.getMethod());
        log.info("请求参数：{}", request.getQueryParams());
        log.info("请求IP：{}", request.getRemoteAddress());
    }

    private void doUserAuth(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String body = URLDecoder.decode(Objects.requireNonNull(headers.getFirst("body")), StandardCharsets.UTF_8);
        String sign = headers.getFirst("sign");

        // 实际上是根据ak去数据库查询是否分配给用户
        if (!"aaa".equals(accessKey)) {
            throw new RuntimeException("无权限");
        }

        // 校验随机数，实际上还要查看服务器端是否有这个随机数
        assert nonce != null;
        if (nonce.length() != 20) {
            throw new RuntimeException("无权限");
        }

        // 校验时间戳，和当前时间相差5分钟以内
        if (!validateTimestamp(timestamp)) {
            throw new RuntimeException("无权限");
        }

        // 校验sign， 实际上的sk是从数据库中查出来的
        String serverSign = SignUtils.genSign(body, "bbb");
        if (!serverSign.equals(sign)) {
            throw new RuntimeException("无权限");
        }
    }

    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }


    private boolean validateTimestamp(String timestamp) {
        long currentTime = Instant.now().getEpochSecond();
        long inputTime = Long.parseLong(timestamp);

        long timeDifference = currentTime - inputTime;
        long timeDifferenceInMinutes = Math.abs(timeDifference) / 60;

        return timeDifferenceInMinutes <= 5;
    }
}
