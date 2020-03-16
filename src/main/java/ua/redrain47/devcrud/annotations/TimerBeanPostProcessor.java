package ua.redrain47.devcrud.annotations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TimerBeanPostProcessor implements BeanPostProcessor {
    private static Map<String, List<Method>> beanToAnnotatedMethods = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();

        List<Method> annotatedMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Timed.class))
                .collect(Collectors.toList());

        beanToAnnotatedMethods.put(beanName, annotatedMethods);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanToAnnotatedMethods.containsKey(beanName)) {
            Object proxyObj = Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    getInvocationHandler(bean));

            return Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    getInvocationHandler(bean));
        }

        return bean;
    }

    private InvocationHandler getInvocationHandler(Object bean) {
        return (object, method, args) -> {
            if (beanToAnnotatedMethods.get(bean).contains(method)) {
                long start = System.nanoTime();
                Object invoke = method.invoke(bean, args);
                long end = System.nanoTime();

                String profilingMessage = "Profiling: method "
                        + method.getName() + " worked for "
                        + (end - start) + " ns";

                log.debug(profilingMessage);
                System.out.println(profilingMessage);

                return invoke;
            }

            return method.invoke(bean, args);
        };
    }
}
