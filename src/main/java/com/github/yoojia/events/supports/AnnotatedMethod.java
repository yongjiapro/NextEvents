package com.github.yoojia.events.supports;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Yoojia Chen (yoojiachen@gmail.com)
 * @since 1.2
 */
public class AnnotatedMethod extends Annotated<Method> {

    public AnnotatedMethod(final Class<? extends Annotation> type) {
        super();
        addResourceFilter(new Filter<Method>() {
            @Override
            public boolean accept(Method method) {
                return !method.isBridge()
                        && !method.isSynthetic()
                        && method.isAnnotationPresent(type);
            }
        });
    }

    @Override
    protected Method[] getResource(Class<?> type) {
        return type.getDeclaredMethods();
    }

}
