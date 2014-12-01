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
package com.theoriginalbit.moarperipherals.common.upgrade.peripheral;

import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author theoriginalbit
 * @since 25/10/14
 */
@LuaPeripheral("compass")
public class PeripheralCompass {
    private final ITurtleAccess turtle;

    public PeripheralCompass(ITurtleAccess access) {
        turtle = access;
    }

    @LuaFunction
    public String getFacing() {
        return ForgeDirection.getOrientation(turtle.getDirection()).name().toLowerCase();
    }
}
