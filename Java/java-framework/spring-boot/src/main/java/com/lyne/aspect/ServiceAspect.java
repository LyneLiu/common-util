package com.lyne.aspect;

import com.lyne.metric.BizMetric;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * 参考Hibernate-Validation
 * 通过@Order注解设置Aspect的优先级，可以制定一个int型的value属性，该属性值越小，则优先级越高.
 *
 * Created by nn_liu on 2017/6/26.
 */

@Aspect
@Component
@Order(1)
public class ServiceAspect {

    @Autowired
    private BizMetric bizMetric;

    private Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

    @Around("execution(public * com.lyne.service.*.*(..))")
    public Object aspect(ProceedingJoinPoint point) throws Throwable {
        logger.info("executing request around aspect!");
        try {
            this.validator(point.getArgs());

            Object result = point.proceed();
            logger.info("complete executing request around aspect!" + result);
            return null;
        } catch (Throwable e) {

            return null;
        }

    }

    @Before("execution(public * com.lyne.controller.*.*(..))")
    public void metric(JoinPoint point) throws Throwable {
        logger.info("executing request before aspect!");
        try {
            /*https://my.oschina.net/itblog/blog/211693*/
            bizMetric.metricCount(point.getSignature().getDeclaringTypeName() +
                    "." + point.getSignature().getName());
        } catch (Throwable e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }

    }


    private void validator(Object[] args) {
        if (args != null && args.length > 0) {
            StringBuffer buffer = new StringBuffer(64);// 用于存储验证后的错误信息
            for (Object object : args) {

                Validator validator = Validation.byProvider(HibernateValidator.class)
                        .configure()
                        //添加fail-fast机制
                        //.addProperty( "hibernate.validator.fail_fast", "true" )
                        .failFast(true)
                        .buildValidatorFactory()
                        .getValidator();

                Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);// 验证某个对象,，其实也可以只验证其中的某一个属性的

                Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
                while (iter.hasNext()) {
                    ConstraintViolation<Object> constraintViolation = iter.next();
                    String message = constraintViolation.getPropertyPath()+" " + constraintViolation.getMessage() + ",";
                    buffer.append(message);
                }
            }
            String message = buffer.toString();
            if (message != null && !"".equals(message)) {
                logger.error(message.substring(0, message.length() - 1));
            }
        }

    }


    @AfterReturning("execution(public * com.lyne.service.*.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        logger.info("Completed:"+joinPoint);
    }

}
