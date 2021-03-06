/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.framework.peripheral.converter;

import com.google.common.collect.Maps;
import com.theoriginalbit.framework.peripheral.LuaType;
import dan200.computercraft.api.lua.LuaException;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Converts a {@link java.util.Map} to/from Lua
 *
 * @author theoriginalbit
 */
public class ConverterMap implements ITypeConverter {
    @Override
    public Object fromLua(Object obj, Class<?> expected) throws LuaException {
        if (obj instanceof Map && expected == Map.class) {
            return obj;
        }
        return null;
    }

    @Override
    public Object toLua(Object obj) throws LuaException {
        if (obj instanceof Map) {
            Map<Object, Object> map = Maps.newHashMap();
            for (Entry<?, ?> e : ((Map<?, ?>) obj).entrySet()) {
                Object k = LuaType.toLua(e.getKey());
                Object v = LuaType.toLua(e.getValue());
                map.put(k, v);
            }
            return map;
        }
        return null;
    }
}