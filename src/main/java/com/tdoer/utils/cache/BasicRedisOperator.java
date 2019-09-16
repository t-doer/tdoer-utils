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

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class BasicRedisOperator {
    private StringRedisTemplate redisTemplate;

    public BasicRedisOperator(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public StringRedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }


    public long decrement(String key, long value) {
        return redisTemplate.opsForValue().increment(key, -value);
    }

    public long decrement(String key) {
        return redisTemplate.opsForValue().increment(key, -1);
    }


    public long increment(String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }


    public long increment(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }


    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    public Integer getInt(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return Integer.valueOf(value);
    }


    public Float getFloat(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return Float.valueOf(value);
    }

    public Long getLong(String key){
        String value = redisTemplate.opsForValue().get(key);
        return Long.valueOf(value);
    }

    public Double getDouble(String key){
        String value = redisTemplate.opsForValue().get(key);
        return Double.valueOf(value);
    }

    public Byte getByte(String key){
        String value = redisTemplate.opsForValue().get(key);
        return Byte.valueOf(value);
    }

    public void multiSet(final Map<String, String> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    public List<String> multiGet(final Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public List<String> multiGet(final String[] keys) {
        List<String> listKeys = new ArrayList<>();
        Collections.addAll(listKeys, keys);
        return redisTemplate.opsForValue().multiGet(listKeys);
    }

    public List<String> multiGetByKeyPattern(final String keyPattern) {
        Set<String> keys = redisTemplate.keys(keyPattern);
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public Map<String, String> multiGetKV(Collection<String> keys) {
        Map<String, String> retMap = new HashMap<>();
        List<String> listKeys = new ArrayList<>(keys);
        if (keys != null) {
            List<String> retList = multiGet(keys);
            int retSize = retList.size();
            for (int i = 0; i < retSize; i++) {
                String key = listKeys.get(i);
                String value = retList.get(i);
                retMap.put(key, value);
            }
        }
        return retMap;
    }

    public Map<String, String> multiGetKV(final String[] keys) {
        List<String> listKeys = new ArrayList<>();
        Collections.addAll(listKeys, keys);
        return multiGetKV(listKeys);
    }

    public Map<String, String> multiGetKVByKeyPattern(String keyPattern) {
        Set<String> keys = redisTemplate.keys(keyPattern);
        return multiGetKV(keys);
    }

    public void deleteByKeyPattern(String keyPattern) {
        Set<String> keys = redisTemplate.keys(keyPattern);
        delete(keys);
    }

    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(final String key, final String value, final int expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }


    public boolean expireAt(final String key, final Date expire) {
        return redisTemplate.expireAt(key, expire);
    }


    public boolean expire(final String key, final int expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }


    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}