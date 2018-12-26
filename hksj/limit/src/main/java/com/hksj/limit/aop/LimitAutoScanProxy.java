package com.hksj.limit.aop;

import com.hksj.limit.annotation.Limit;
import com.hksj.limit.matrix.proxy.aop.DefaultAutoScanProxy;
import com.hksj.limit.matrix.proxy.mode.ProxyMode;
import com.hksj.limit.matrix.proxy.mode.ScanMode;

import java.lang.annotation.Annotation;

public class LimitAutoScanProxy extends DefaultAutoScanProxy {
    private static final long serialVersionUID = -6456216398492047529L;

    private String[] commonInterceptorNames;

    @SuppressWarnings("rawtypes")
    private Class[] methodAnnotations;

    public LimitAutoScanProxy(String scanPackages) {
        super(scanPackages, ProxyMode.BY_METHOD_ANNOTATION_ONLY, ScanMode.FOR_METHOD_ANNOTATION_ONLY);
    }

    @Override
    protected String[] getCommonInterceptorNames() {
        if (commonInterceptorNames == null) {
            commonInterceptorNames = new String[] { "limitInterceptor" };
        }

        return commonInterceptorNames;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getMethodAnnotations() {
        if (methodAnnotations == null) {
            methodAnnotations = new Class[] { Limit.class };
        }

        return methodAnnotations;
    }
}
