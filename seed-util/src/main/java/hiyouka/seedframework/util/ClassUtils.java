package hiyouka.seedframework.util;

/**
 * @author hiyouka
 * Date: 2019/1/27
 * @since JDK 1.8
 */
public class ClassUtils {

    /**
     * get thread ClassLoader Object
     * @param
     * @return:java.lang.ClassLoader default ClassLoader
     * @Date: 2019/1/29
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }

    public static Class<?> forName(String className) throws ClassNotFoundException {
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw e;
        }
        return aClass;
    }

    public static Class<?> getClass(String className){
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class can not forName : " + className);
        }
        return aClass;
    }

}