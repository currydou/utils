package com.curry.file.otherutils;

/**
 * @Description: 双击事件处理--ldz
 */
public class NoDoubleClickUtil {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 200) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}