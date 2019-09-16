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
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {
	public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
	    @Override
	    @SuppressWarnings("unchecked")
	    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
	        return (HibernateProxy.class.isAssignableFrom(type.getRawType())
	                ? (TypeAdapter<T>) new HibernateProxyTypeAdapter((TypeAdapter<Object>) gson.getAdapter(TypeToken.get(type.getRawType().getSuperclass()))) 
	     : null);
	    }
	};
	private final TypeAdapter<Object> delegate;

	private HibernateProxyTypeAdapter(TypeAdapter<Object> delegate) {
	    this.delegate = delegate;
	}

	@Override
	public void write(JsonWriter out, HibernateProxy value) throws IOException {
	    out.nullValue();
	}

	@Override
	public HibernateProxy read(JsonReader arg0) throws IOException {
		throw new UnsupportedOperationException();
	}
}
