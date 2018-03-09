/*
 * Copyright 2018 Fizzed, Inc.
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
package com.fizzed.crux.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author jjlauer
 */
public class Duck {

    static public interface Converter<Object,T> {
        T convert(Object value);
    }
    
    static final private Map<Class<?>,Converter<Object,?>> CONVERTERS = new HashMap<>();
    static {
        CONVERTERS.put(Long.class, v -> {
            if (v == null) {
                return null;
            }
            if (v instanceof Long) {
                return v;
            }
            if (v instanceof Number) {
                return ((Number)v).longValue();
            }
            if (v instanceof CharSequence) {
                try {
                    return Long.decode(v.toString());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
            throw new IllegalArgumentException(buildMessage(Long.class, v));
        });
    }
    
    static private String buildMessage(Class<?> targetClass, Object value) {
        if (value != null) {
            return "Unable to convert class " + value.getClass().getCanonicalName() + " to " + targetClass.getCanonicalName() + " for value " + value;
        } else {
            return "Unable to convert null to " + targetClass.getCanonicalName();
        }
    }

    private final Object value;
    
    public Duck(Object value) {
        this.value = value;
    }
    
    static public Duck of(Object value) {
        return new Duck(value);
    }
    
    public <T> T as(Class<T> type) {
        final Converter<Object,?> converter = CONVERTERS.get(type);
        
        if (converter == null) {
            throw new IllegalArgumentException("No converter registered for class " + type);
        }
        
        return (T)converter.convert(this.value);
    }
    
    public Long asLong() {
        return this.as(Long.class);
    }
    
    public Optional<Long> optLong() {
        return Optional.ofNullable(this.as(Long.class));
    }
    
    public Maybe<Long> maybeLong() {
        return Maybe.of(this.as(Long.class));
    }
    
}