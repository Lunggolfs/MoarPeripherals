/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.tile;

import com.theoriginalbit.minecraft.moarperipherals.api.tile.IPairableDevice;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.common.registry.ModBlocks;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.ComputerUtils;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.NBTUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import openperipheral.api.Ignore;

@Ignore
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
            targetTile = worldObj.getBlockTileEntity(nbtTargetX, nbtTargetY, nbtTargetZ);
            nbtTargetX = nbtTargetY = nbtTargetZ = null;
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        if (!hasConnection()) {
            return null;
        }
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound tag = packet.data;
        tag.setInteger("targetX", targetTile.xCoord);
        tag.setInteger("targetY", targetTile.yCoord);
        tag.setInteger("targetZ", targetTile.zCoord);
        return packet;
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        super.onDataPacket(net, packet);
        NBTTagCompound tag = packet.data;
        configureTargetFromNbt(tag);
    }

    /**
     * When the Keybaord is right-clicked, it shall turn on the target computer if it is not on
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