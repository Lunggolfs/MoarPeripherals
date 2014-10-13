/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrades;

import com.theoriginalbit.framework.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.registry.ModBlocks;
import com.theoriginalbit.moarperipherals.common.tile.TileIronNote;
import com.theoriginalbit.moarperipherals.common.upgrades.abstracts.UpgradePeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class UpgradeIronNote extends UpgradePeripheral {

    public UpgradeIronNote() {
        super(ConfigHandler.upgradeIdIronNote, Constants.UPGRADE.IRONNOTE, new ItemStack(ModBlocks.blockIronNote), TileIronNote.class);
    }

    @Override
    protected void updateLocation(ITurtleAccess turtle, IPeripheral peripheral) {
        if (peripheral instanceof PeripheralWrapper) {
            Object instance = ((PeripheralWrapper) peripheral).getInstance();
            if (instance instanceof TileEntity) {
                TileEntity tile = (TileEntity) instance;
                ChunkCoordinates coords = turtle.getPosition();
                tile.setWorldObj(turtle.getWorld());
                tile.xCoord = coords.posX;
                tile.yCoord = coords.posY;
                tile.zCoord = coords.posZ;
                tile.updateEntity();
            }
        }
    }

}
