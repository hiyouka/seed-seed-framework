package seed.seedframework.core.intercept;

import seed.seedframework.util.Assert;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class AroundAdviceInterceptor implements MethodInterceptor{

    private final MethodAroundAdvice advice;

    public AroundAdviceInterceptor(MethodAroundAdvice advice) {
        Assert.notNull(advice,"advice must not null");
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return this.advice.around(invocation);
    }



}