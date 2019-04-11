package com.curry.file.otherutils;

import java.math.BigDecimal;

/**
 * @Description: 价格计算工具类--ldz
 */
public class PriceUtil {

    /**
     * 元 转 分
     *
     * @param price
     * @return
     */
    public static int YtoF(String price) {
        try {
            return new BigDecimal(price).multiply(new BigDecimal(100)).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 分 转 元
     *
     * @param price
     * @return
     */
    public static double FtoY(String price) {
        try {
            return new BigDecimal(price).multiply(new BigDecimal(0.01)).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
