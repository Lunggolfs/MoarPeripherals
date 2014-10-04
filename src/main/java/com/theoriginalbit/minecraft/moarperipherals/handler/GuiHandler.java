package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.theoriginalbit.minecraft.moarperipherals.gui.GuiKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.gui.GuiType;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        GuiType gui = GuiType.valueOf(id);
        if (gui != null) {
            switch (gui) {
                case KEYBOARD:
                    TileKeyboard tile = (TileKeyboard) world.getBlockTileEntity(x, y, z);
                    return new GuiKeyboard(tile, player);
                default:
                    return null;
            }
        }
        return null;
    }

}