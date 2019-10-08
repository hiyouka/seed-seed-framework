import seed.framework.test.bean.*;
import seed.framework.test.config.TestConfiguration;
import org.junit.jupiter.api.Test;
import seed.seedframework.beans.metadata.MethodParameter;
import seed.seedframework.context.AnnotationConfigApplicationContext;
import seed.seedframework.context.ApplicationContext;
import seed.seedframework.core.env.Environment;
import seed.seedframework.core.intercept.*;
import seed.seedframework.util.ReflectionUtils;
import seed.seedframework.util.ResolverTypeUtil;

import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
public class  TestClass {

    String test = "123123";


    @Test
    public void testParameterName() throws NoSuchMethodException, NoSuchFieldException {
        Constructor<TestAutowired> declaredConstructor = TestAutowired.class.getDeclaredConstructor(TestBean1.class);
        TypeVariable<Constructor<TestAutowired>>[] typeParameters = declaredConstructor.getTypeParameters();
        Parameter[] parameters = declaredConstructor.getParameters();
        String name = parameters[0].getName();

        MethodParameter methodParameter = new MethodParameter(declaredConstructor,0);
        Method testB = TestAutowired.class.getDeclaredMethod("testB", TestFather1.class);
        MethodParameter methodParameter1 = new MethodParameter(testB, 0);
//        System.out.println(methodParameter.getParameterType());
//        System.out.println(methodParameter1.getParameterType());
        System.out.println(methodParameter.getParameterName());
//        System.out.println(methodParameter1.getParameterName());
        Field test1Test1TestFather1 = TestAutowired.class.getDeclaredField("baseService");
        Type genericType = test1Test1TestFather1.getGenericType();
        System.out.println(ResolverTypeUtil.isAssignableFrom(genericType, TestBean2.class));

    }

    @Test
    public void applicationTest() throws NoSuchFieldException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);
//        Object testAutowired = applicationContext.getBean("testAutowired");
        TestAutowiredBean bean = applicationContext.getBean(TestAutowiredBean.class);
        Environment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("spring.aop.auto");
        List<String> list = new LinkedList<>();
        for(int i=0; i<10; i++){
            list.add(i+"");
        }

//        YmlProperties properties = new YmlProperties();
//        properties.load(getClass().getResourceAsStream("seed.yml"));
//        System.out.println(properties);
//        int i = test.indexOf("123");
//        int i1 = test.indexOf("123", 3);
//        System.out.println(i);
//        System.out.println(i1);

    }

    private String mainTest(String arg){

        System.out.println(">>>>>>>>>>mainTest<<<<<<<<<<" + arg);
        return "123";
    }

    public Object testInter(Joinpoint joinpoint){
        Object process = null;
        try {
            System.out.println("testInter execute before ...... ");
            process = joinpoint.process();
            System.out.println("testInter execute after ...... , previous return value : " + process);
            process = "888";
        } catch (Throwable throwable) {
            System.out.println("testInter execute throw ......");
            throwable.printStackTrace();
        }
        return process;
    }

    @Test
    public void interceptorTest() throws Throwable {
        TestClass testClass = new TestClass();
        Method mainTest = testClass.getClass().getDeclaredMethod("mainTest", String.class);
        int modifiers = mainTest.getModifiers();
        System.out.println(Modifier.toString(modifiers));
        Object[] args = {"123"};
        InterceptorChainMethodInvocation invocation = new InterceptorChainMethodInvocation(mainTest,testClass,args);
        invocation.pushInterceptor(new MethodBeforeAdviceInterceptor((method, args1, target) -> {
            System.out.println("method before interceptor invoke .......... ");
        }));

        invocation.pushInterceptor(new MethodAfterReturnAdviceInterceptor((returnVal, method, args12, target) -> {
            System.out.println("method after interceptor invoke .........");
            returnVal = "444";
            return returnVal;
        }));
        invocation.pushInterceptor(new MethodAroundAdviceInterceptor(new MethodAroundAdvice() {

            private Method adviceMethod = TestClass.class.getDeclaredMethod("testInter", Joinpoint.class);


            @Override
            public Object around(Method method, Object[] args, Object target) throws Throwable {
//                System.out.println("method around interceptor invoke before ........");
//                Object process = invocation.process();
//                process = "555";
//                System.out.println("method around interceptor invoke after ........");
                //
                args = new Object[]{invocation};
                return ReflectionUtils.invokeMethod(this.adviceMethod, invocation.getThis(), args);
            }

        }));

        Object process = invocation.process();

        System.out.println(process);
    }




}