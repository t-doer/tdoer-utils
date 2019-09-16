/*
 * Copyright 2017-2019 T-Doer (tdoer.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tdoer.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import java.sql.Timestamp;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class JsonUtil {
    private static Gson gson = null;

    static {
        GsonBuilder b = new GsonBuilder();
        b.setDateFormat("yyyy-MM-dd hh:mm:ss");
        b.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter());
        b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        b.registerTypeAdapterFactory(DateTypeAdapter.FACTORY);
        gson = b.create();
    }


    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }


    public static String toJson(Object object) {
        String json = null;
        json =  gson.toJson(object);
        return json;
    }

}
