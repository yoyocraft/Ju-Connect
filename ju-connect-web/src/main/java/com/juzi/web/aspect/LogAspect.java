package com.juzi.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 使用AOP环绕通知记录日志
 *
 * @author codejuzi
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 切点
     * todo 修改成具体路径
     */
    @Pointcut("execution(* com.juzi.web.controller.*.*(..))")
    public void pointCut() {
    }

    /**
     * 执行Controller层日志记录，在方法执行前后，环绕通知的方式
     *
     * @param joinPoint join point
     * @return 方法执行结果
     */
    @Around("pointCut()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String requestURI = request.getRequestURI();

        // 生成唯一的请求ID
        String requestId = UUID.randomUUID().toString();

        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        String reqParams = String.format("[%s]", StringUtils.join(args, ","));

        // 获取请求IP
        String remoteHost = request.getRemoteHost();

        // 输出请求日志
        log.info("=========> request start, id: {}, path: {}, ip: {}, params: {}",
                requestId, requestURI, remoteHost, reqParams);

        // 执行原方法
        Object result = joinPoint.proceed(args);

        // 停止计时
        stopWatch.stop();
        // 输出响应日志
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("<======== request stop, id: {}, cost: {}ms", requestId, totalTimeMillis);

        // 返回执行结果
        return result;
    }
}
