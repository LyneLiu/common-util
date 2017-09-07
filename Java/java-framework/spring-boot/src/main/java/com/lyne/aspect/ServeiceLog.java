package com.lyne.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author nn_liu
 * @Created 2017-09-07-18:06
 */
@Aspect
@Component
public class ServeiceLog {

    private Logger logger = LoggerFactory.getLogger(ServeiceLog.class);

    @Around("execution(public * com.lyne.service.*.*(..))")
    public Object aspect(ProceedingJoinPoint point) throws Throwable {
        logger.info("executing another request aspect!");
        try {

            point.proceed();
            logger.info("complete executing another request aspect!");

            return null;
        } catch (Throwable e) {

            return null;
        }

    }

}
