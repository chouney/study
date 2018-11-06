package spring.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/6/21
 */
@Aspect
@Component
public class EAspect {

    @Pointcut("execution(@spring.annotation.AopTest * *(..))")
    public void point(){
    }

    @Around("point()")
    public Object doAop(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("before");
        Object result = joinPoint.proceed(joinPoint.getArgs());
        System.out.println("result:"+result);
        System.out.println("after");
        return result;
    }
}
