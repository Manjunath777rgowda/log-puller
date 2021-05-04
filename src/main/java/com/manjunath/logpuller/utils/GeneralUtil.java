/* Copyright(C) 2020-21. Nuvepro Pvt. Ltd. All rights reserved */

package com.manjunath.logpuller.utils;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneralUtil {

    public static String getUUID()
    {
        return UUID.randomUUID().toString();
    }

}
