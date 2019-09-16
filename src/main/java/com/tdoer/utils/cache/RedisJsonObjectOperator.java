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
package com.tdoer.utils.cache;

import com.tdoer.utils.json.JsonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class RedisJsonObjectOperator extends BasicRedisOperator{
    protected StringRedisTemplate redisTemplate;

    public RedisJsonObjectOperator(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
    }

    // --------------------------------------------------------
    // Key - Value
    // --------------------------------------------------------

    public void setObject(final String key, final Object value) {
        final String json = JsonUtil.toJson(value);
        redisTemplate.opsForValue().set(key, json);
    }

    public void setObject(final String key, final Object value, final int expire) {
        final String json = JsonUtil.toJson(value);
        redisTemplate.opsForValue().set(key, json, expire, TimeUnit.SECONDS);
    }

    public <T> T getObject(final String key, final Class<T> clazz) {
        String json = redisTemplate.opsForValue().get(key);
        return JsonUtil.fromJson(json, clazz);
    }

    // --------------------------------------------------------
    // Set
    // --------------------------------------------------------
    public void setAddObject(final String key, final Object obj){
        final String json = JsonUtil.toJson(obj);
        redisTemplate.opsForSet().add(key, json);
    }

    public Long setRemoveObject(final String key, final Object... objs){
        String[] jsons = new String[objs.length];
        int i=0;
        for(Object obj : objs){
            jsons[i] = JsonUtil.toJson(obj);
            i ++;
        }
        return redisTemplate.opsForSet().remove(key, jsons);
    }

    public <T> Set<T> setGetObjects(final String key, final Class<T> clazz){
        Set<String> jsons = redisTemplate.opsForSet().members(key);
        HashSet<T> set = new HashSet<>();
        if(jsons == null || jsons.size() == 0){
            return set;
        }

        for(String json : jsons){
            set.add(JsonUtil.fromJson(json, clazz));
        }
        return set;
    }

    // --------------------------------------------------------
    // Hash
    // --------------------------------------------------------
    public void hashAddObject(final String key, final String hashKey, final Object obj){
        final String json = JsonUtil.toJson(obj);
        redisTemplate.opsForHash().put(key, hashKey, json);
    }

    public Long hashRemoveObject(final String key, final String... hashKeys){
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public <T> Map<String, T> hashGetObjects(final String key, final Class<T> clazz){
        Map<Object, Object> jsons = redisTemplate.opsForHash().entries(key);

        HashMap<String, T> hashMap = new HashMap();
        if(jsons == null || jsons.size() == 0){
            return hashMap;
        }

        for(Object k : jsons.keySet()){
            hashMap.put(k.toString(), JsonUtil.fromJson(jsons.get(k).toString(), clazz));
        }
        return hashMap;
    }
}
