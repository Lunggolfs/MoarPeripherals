/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile.abstracts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileMoarP extends TileEntity {

    private String owner = "[NONE]";

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        setOwnerFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("owner", owner);
    }

    @Override
    public Packet getDescriptionPacket() {
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, getDescriptionNbt());
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        setOwnerFromNBT(packet.func_148857_g());
    }

    protected NBTTagCompound getDescriptionNbt() {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setString("owner", owner);
        return tag;
    }

    public final void setOwner(String username) {
        owner = username;
    }

    public final String getOwner() {
        return owner;
    }

    public boolean canPlayerAccess(EntityPlayer player) {
        return player != null;
    }

    private void setOwnerFromNBT(NBTTagCompound tag) {
        owner = tag.getString("owner");
    }

}
