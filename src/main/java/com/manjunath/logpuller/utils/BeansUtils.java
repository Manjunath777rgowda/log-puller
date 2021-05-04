/* Copyright(C) 2020-21. Nuvepro Pvt. Ltd. All rights reserved. */

package com.manjunath.logpuller.utils;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * This is the Util class used for the opertations related to Objects
 */
public class BeansUtils {

    /**
     * This method will copy one object to another object
     * 
     * @return
     */
    public static Object copyProperties( Object source, Object destination )
    {
        BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
        return destination;
    }

    private static String[] getNullPropertyNames( Object source )
    {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
    }
}
