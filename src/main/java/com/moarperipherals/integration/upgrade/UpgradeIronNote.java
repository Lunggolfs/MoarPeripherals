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
package com.moarperipherals.integration.upgrade;

import com.theoriginalbit.framework.peripheral.turtle.UpgradePeripheral;
import com.theoriginalbit.framework.peripheral.wrapper.WrapperComputer;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.init.ModBlocks;
import com.moarperipherals.Constants;
import com.moarperipherals.tile.TileIronNote;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;

public class UpgradeIronNote extends UpgradePeripheral {
    public UpgradeIronNote() {
        super(ConfigData.upgradeIdIronNote, Constants.UPGRADE.IRONNOTE.getLocalised(), new ItemStack(ModBlocks.blockIronNote));
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return ModBlocks.blockIronNote.getIcon(0, 0);
    }

    @Override
    protected WrapperComputer getPeripheralWrapper(ITurtleAccess access, TurtleSide side) {
        return new WrapperComputer(new TileIronNote());
    }

    @Override
    protected void update(ITurtleAccess turtle, TurtleSide side, WrapperComputer peripheral) {
        final Object instance = peripheral.getInstance();
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
