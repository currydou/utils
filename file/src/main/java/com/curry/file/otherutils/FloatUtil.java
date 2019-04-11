package com.curry.file.otherutils;

import java.math.BigDecimal;

/**
 * --ldz
 */
public class FloatUtil {

	/***
	 * 
	 * @param price
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static int FloatForInt(float price,float code)throws Exception{
		BigDecimal pricebd = new BigDecimal(Float.toString(price));
		BigDecimal b2 = new BigDecimal(Float.toString(code));
		int priceInt = pricebd.multiply(b2).intValue();
		return priceInt;
	} 
	
	/***
	 * 
	 *@param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商 
	 * @return
	 */
	public static float IntForFloat(float v1,float v2,int scale){
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    } 
	
	/**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(float v,int scale){
        BigDecimal b = new BigDecimal(Float.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    } 
    
    /**
     * 提供精确加法计算的add方法
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1,double value2){
	    BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
	    BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
	    return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static float sub(float value1,float value2){
	    BigDecimal b1 = new BigDecimal(Float.toString(value1));
	    BigDecimal b2 = new BigDecimal(Float.toString(value2));
	    return b1.subtract(b2).floatValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static float mul(float value1,float value2){
	    BigDecimal b1 = new BigDecimal(Float.toString(value1));
	    BigDecimal b2 = new BigDecimal(Float.toString(value2));
	    return b1.multiply(b2).floatValue();
    }
    
//    public static void main(String[] args) {
//    	float v1 = 500.05f;
//    	float v2 = 0.01f;
//    	float result = mul(v1, v2);
//		System.out.println(result);
//	}

    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static float div(float value1,float value2,int scale) throws IllegalAccessException{
	    //如果精确范围小于0，抛出异常信息
	    if(scale<0){
	      throw new IllegalAccessException("精确度不能小于0");
	    }
	    BigDecimal b1 = new BigDecimal(Float.toString(value1));
	    BigDecimal b2 = new BigDecimal(Float.toString(value2));
	    return b1.divide(b2, scale).floatValue();
    }
}
