/*
 * Copyright 2019 T-Doer (tdoer.com).
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
package com.tdoer.utils.id;

import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2019-11-21
 */
public class GUID {

    private static String delimiter = "-";

    public static String generateTenantGUID(){
        return generateUUID();
    }

    public static String generateClientGUID(Long tenantID){
        return tenantID + delimiter + generateUUID();
    }

    public static String generateUserGUID(Long tenantId){
        return tenantId + delimiter + generateUUID();
    }

    public static String generateContextInstanceGUID(Long tenantId, Long contextType){
        return tenantId + delimiter + contextType + delimiter + generateUUID();
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static Long parseTenantIdFromClientGUID(String uuid){
        return Long.parseLong(StringUtils.split(uuid, delimiter)[0]);
    }

    public static Long parseTenantIdFromUserGUID(String uuid){
        return Long.parseLong(StringUtils.split(uuid, delimiter)[0]);
    }

    public static Long parseTenantIdFromContextInstanceGUID(String uuid){
        return Long.parseLong(StringUtils.split(uuid, delimiter)[0]);
    }

    public static Long parseContextTypeFromContextInstanceGUID(String uuid){
        return Long.parseLong(StringUtils.split(uuid, delimiter)[1]);
    }
}
