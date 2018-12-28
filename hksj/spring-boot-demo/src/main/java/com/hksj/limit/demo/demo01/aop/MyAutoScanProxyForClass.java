package com.hksj.limit.demo.demo01.aop;




import com.hksj.limit.matrix.proxy.aop.DefaultAutoScanProxy;
import com.hksj.limit.matrix.proxy.mode.ProxyMode;
import com.hksj.limit.matrix.proxy.mode.ScanMode;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

// 通过全局拦截器实现对类头部注解的扫描和代理
// 该类描述的逻辑是，目标接口或者类头部如果出现了MyAnnotation1注解，那么该接口或者类下面所有的方法都会被执行扫描和代理，代理类为MyInterceptor1
@Component("myAutoScanProxyForClass")
public class MyAutoScanProxyForClass extends DefaultAutoScanProxy {
    private static final long serialVersionUID = -5968030133395182024L;

    // 多个包路径，用“;”分隔
    private static final String SCAN_PACKAGES = "com.hksj.limit.demo.demo01";

    @SuppressWarnings("rawtypes")
    private Class[] commonInterceptorClasses;

    @SuppressWarnings("rawtypes")
    private Class[] classAnnotations;

    public MyAutoScanProxyForClass() {
        super(SCAN_PACKAGES, ProxyMode.BY_CLASS_ANNOTATION_ONLY, ScanMode.FOR_CLASS_ANNOTATION_ONLY);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends MethodInterceptor>[] getCommonInterceptors() {
        if (commonInterceptorClasses == null) {
            commonInterceptorClasses = new Class[] { MyInterceptor1.class };
        }
        return commonInterceptorClasses;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getClassAnnotations() {
        if (classAnnotations == null) {
            classAnnotations = new Class[] { MyAnnotation1.class };
        }
        return classAnnotations;
    }

    @Override
    protected void classAnnotationScanned(Class<?> targetClass, Class<? extends Annotation> classAnnotation) {
        System.out.println("Class annotation scanned, targetClass=" + targetClass + ", classAnnotation=" + classAnnotation);
    }
}