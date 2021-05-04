/* Copyright(C) 2020-21. Nuvepro Pvt. Ltd. All rights reserved. */

package com.manjunath.logpuller.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This is util class is used for the null check for different data types
 */
public class NullEmptyUtils {

    /**
     * This method is used for String null check
     */
    public static boolean isNullOrEmpty( String val )
    {
        return isNull(val) || val.trim().isEmpty() || val.equals("null");
    }

    /**
     * This method is used for String List check
     */
    public static boolean isNullOrEmpty( List<?> val )
    {
        return isNull(val) || val.isEmpty();
    }

    /**
     * This method is used for Collection null check
     */
    public static boolean isNullOrEmpty( Collection<?> val )
    {
        return isNull(val) || val.isEmpty();
    }

    /**
     * This method is used for Number null check
     */
    public static boolean isNullOrEmpty( Number val )
    {
        return isNull(val) || val.doubleValue() == 0.0D || val.intValue() == 0;
    }

    /**
     * This method is used for Map null check
     */
    public static boolean isNullOrEmpty( Map<?, ?> val )
    {
        return isNull(val) || val.isEmpty();
    }

    /**
     * This method is used for Object null check
     */
    public static boolean isNull( Object val )
    {
        return val == null;
    }
}
