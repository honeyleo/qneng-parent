package cn.lfyun.wx.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
 
 
@Aspect
@Component
public class CircuitBreakerAspect {
 
 
    @Around("@annotation(cn.lfyun.wx.annotation.CircuitBreaker)")
    public Object circuitBreakerAround(final ProceedingJoinPoint aJoinPoint) throws Throwable {
        String theShortName = aJoinPoint.getSignature().toShortString();
        HystrixCommand.Setter theSetter =
                HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(theShortName));
        theSetter = theSetter.andCommandKey(HystrixCommandKey.Factory.asKey(theShortName));
        
        //线程池模式
        HystrixCommand.Setter setter2 = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(theShortName))
        		.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(52))
                /* 配置依赖超时时间,500毫秒*/  
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(1000)
                        );
        
        //信号量模式
        HystrixCommand.Setter current =HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("wx-dev"))
                .andCommandKey(HystrixCommandKey.Factory.asKey(theShortName))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationThreadTimeoutInMilliseconds(17)
                                .withExecutionIsolationSemaphoreMaxConcurrentRequests(50)
                                .withFallbackIsolationSemaphoreMaxConcurrentRequests(50)
                                .withCircuitBreakerEnabled(false)
//                                .withExecutionTimeoutEnabled(false)
                                .withFallbackEnabled(false)
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE));
        
        HystrixCommand<Object> theCommand = new HystrixCommand<Object>(current) {
            @Override
            protected Object run() throws Exception {
                try {
                    return aJoinPoint.proceed();
                } catch (Exception e) {
                    throw e;
                } catch (Throwable e) {
                    throw new Exception(e);
                }
            }

			@Override
			protected Object getFallback() {
				return "failed";
			}
            
        };
        return theCommand.execute();
    }
}
