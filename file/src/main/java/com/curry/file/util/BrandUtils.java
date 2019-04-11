package com.curry.file.util;

import android.os.Build;

/**
 * @Description: 获取手机品牌--ldz
 */
public class BrandUtils {
    public static boolean getDeviceBrand() {
        String brand = Build.BRAND.toLowerCase();
        return brand.contains("huawei") || brand.contains("hua") || brand.contains("wei");
//        return false;
    }
}
