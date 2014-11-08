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
package com.theoriginalbit.moarperipherals.common.tile;

import com.theoriginalbit.moarperipherals.api.tile.IPairableDevice;
import com.theoriginalbit.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.registry.ModBlocks;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.moarperipherals.common.utils.ComputerUtils;
import com.theoriginalbit.moarperipherals.common.utils.NBTUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileKeyboard extends TileMoarP implements IPairableDevice, IActivateAwareTile {

    private TileEntity targetTile;
    private Integer nbtTargetX, nbtTargetY, nbtTargetZ;

    /**
     * Read the target information from NBT
     */
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        configureTargetFromNbt(tag);
    }

    /**
     * Write the target information to NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (hasConnection()) {
            tag.setInteger("targetX", targetTile.xCoord);
            tag.setInteger("targetY", targetTile.yCoord);
            tag.setInteger("targetZ", targetTile.zCoord);
        }
    }

    @Override
    public void updateEntity() {
        if (nbtTargetX != null && nbtTargetY != null && nbtTargetZ != null) {
            targetTile = worldObj.getTileEntity(nbtTargetX, nbtTargetY, nbtTargetZ);
            nbtTargetX = nbtTargetY = nbtTargetZ = null;
        }
    }

    @Override
    public NBTTagCompound getDescriptionNBT() {
        final NBTTagCompound tag = super.getDescriptionNBT();
        if (targetTile != null) {
            tag.setInteger("targetX", targetTile.xCoord);
            tag.setInteger("targetY", targetTile.yCoord);
            tag.setInteger("targetZ", targetTile.zCoord);
        }
        return tag;
    }

    @Override
    protected void readDescriptionNBT(NBTTagCompound tag) {
        super.readDescriptionNBT(tag);
        configureTargetFromNbt(tag);
    }

    /**
     * When the Keyboard is right-clicked, it shall turn on the target computer if it is not on
     */
    @Override
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ComputerUtils.turnOn(targetTile);
        return true;
    }

    /**
     * Whether the keyboard has a valid connection or not
     */
    public final boolean hasConnection() {
        return targetTile != null && ComputerUtils.isTileComputer(targetTile) && !targetTile.isInvalid();
    }

    /**
     * Used by the renderer to get which texture to display based on the connection status
     */
    public final ResourceLocation getTextureForRender() {
        if (hasConnection() && targetInRange()) {
            return Constants.TEXTURES_MODEL.KEYBOARD_ON.getResourceLocation();
        } else if (targetTile != null || (hasConnection() && !targetInRange())) {
            return Constants.TEXTURES_MODEL.KEYBOARD_LOST.getResourceLocation();
        }
        return Constants.TEXTURES_MODEL.KEYBOARD.getResourceLocation();
    }

    @Override
    public final boolean configureTargetFromNbt(NBTTagCompound tag) {
        final String targetX = Constants.NBT.TARGET_X;
        final String targetY = Constants.NBT.TARGET_Y;
        final String targetZ = Constants.NBT.TARGET_Z;

        if (tag.hasKey(targetX) && tag.hasKey(targetY) && tag.hasKey(targetZ)) {
            nbtTargetX = tag.getInteger(targetX);
            nbtTargetY = tag.getInteger(targetY);
            nbtTargetZ = tag.getInteger(targetZ);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getPairedDrop() {
        ItemStack stack = new ItemStack(ModBlocks.blockKeyboard, 1);
        if (targetTile != null) {
            NBTUtils.setInteger(stack, Constants.NBT.TARGET_X, targetTile.xCoord);
            NBTUtils.setInteger(stack, Constants.NBT.TARGET_Y, targetTile.yCoord);
            NBTUtils.setInteger(stack, Constants.NBT.TARGET_Z, targetTile.zCoord);
        }
        return stack;
    }

    public final void terminateTarget() {
        if (targetInRange()) {
            ComputerUtils.terminate(targetTile);
        }
    }

    public final void rebootTarget() {
        if (targetInRange()) {
            ComputerUtils.reboot(targetTile);
        }
    }

    public final void shutdownTarget() {
        if (targetInRange()) {
            ComputerUtils.shutdown(targetTile);
        }
    }

    public final void queueEventToTarget(String event, Object... args) {
        if (targetInRange()) {
            ComputerUtils.queueEvent(targetTile, event, args);
        }
    }

    private boolean targetInRange() {
        return targetTile != null && MathHelper.sqrt_double(getDistanceFrom(targetTile.xCoord, targetTile.yCoord, targetTile.zCoord)) <= ConfigHandler.keyboardRange;
    }

}