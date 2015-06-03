/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.tile;

import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.moarperipherals.api.bitnet.BitNetMessage;
import com.moarperipherals.api.bitnet.IBitNetRelay;
import com.moarperipherals.api.bitnet.IBitNetWorld;
import com.moarperipherals.bitnet.BitNetUniverse;
import com.moarperipherals.util.LogUtil;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("bitnet_antenna")
public class TileMiniAntenna extends TileMoarP implements IBitNetRelay {
    private static final String EVENT_BITNET = "bitnet_message";
    private static final float ROTATION_SPEED = 1.0f;
    private static final float BOB_MULTIPLIER = 0.02f;
    private static final float BOB_SPEED = 16.0f;
    @Computers.List
    public ArrayList<IComputerAccess> computers;
    private boolean registered = false;
    private IBitNetWorld network;
    private float rotation = 0.0f;
    private float bob = 0.0f;
    private int tick = 0;

    public float getRotation() {
        return rotation;
    }

    public float getBob() {
        return bob;
    }

    @Override
    public void updateEntity() {
        if (!registered) {
            registerTower();
        }
        rotation = (rotation + ROTATION_SPEED) % 360f;
        bob = BOB_MULTIPLIER * (float) Math.sin(++tick / BOB_SPEED);
    }

    @LuaFunction
    public boolean isOpen(int channel) throws LuaException {
        return network.isChannelOpen(this, channel);
    }

    @LuaFunction
    public boolean open(int channel) throws LuaException {
        return network.openChannel(this, channel);
    }

    @LuaFunction
    public boolean close(int channel) throws LuaException {
        return network.closeChannel(this, channel);
    }

    @LuaFunction
    public boolean transmit(int sendChannel, int replyChannel, Object payload) throws LuaException {
        network.transmit(this, new BitNetMessage(sendChannel, replyChannel, payload));
        return true;
    }

    @Override
    public World getWorld() {
        return worldObj;
    }

    @Override
    public Vec3 getPosition() {
        return Vec3.createVectorHelper(xCoord, yCoord + 1, zCoord);
    }

    @Override
    public RelayType getRelayType() {
        return RelayType.SHORT_RANGE;
    }

    @Override
    public void receive(BitNetMessage payload) {
        if (computers != null && computers.size() > 0) {
            LogUtil.debug(String.format("BitNet Mini Antenna at %d %d %d queueing message.", xCoord, yCoord, zCoord));
            for (IComputerAccess c : computers) {
                c.queueEvent(EVENT_BITNET, payload.getEventData(c));
            }
        }
    }

    @Override
    public void blockBroken(int x, int y, int z) {
        if (!worldObj.isRemote) {
            network.removeRelay(this);
        }
        registered = false;
    }

    private void registerTower() {
        if (!worldObj.isRemote) {
            network = BitNetUniverse.UNIVERSE.getBitNetWorld(worldObj);
            network.addRelay(this);
        }
        registered = true;
    }
}
