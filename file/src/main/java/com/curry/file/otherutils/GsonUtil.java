package com.curry.file.otherutils;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GsonUtil是Gson解析工具类--ldz
 *
 */
public class GsonUtil {


    /**
     * 使用Gson解析数据成Object
     *
     * @param json 需要解析的Json数据
     * @param cls  类名
     * @return T
     */
    public static <T> T getObject(String json, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 使用Gson解析数据成List<Object>
     *
     * @param json 需要解析的Json数据
     * @param cls  类名
     * @return List<T>
     */
    public static <T> List<T> getList(String json, Class<T> cls) {


        List<T> list = new ArrayList<T>();
        try {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement element : array) {
                list.add(new Gson().fromJson(element, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 将json数据转成List<Map<String, Object>>
     *
     * @param json 需要解析的Json数据
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getListMaps(String json) {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Map<String, Object>>>() {
            }.getType();
            maps = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maps;
    }
}
