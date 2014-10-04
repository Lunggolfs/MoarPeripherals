package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.ComputerList;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.entity.player.EntityPlayer;
import openperipheral.api.Ignore;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

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
@Ignore
@LuaPeripheral("player_detector")
public class TilePlayerDetector extends TileMPBase implements IActivateAwareTile {

    private static final String EVENT_PLAYER = "player";

    @ComputerList
    public ArrayList<IComputerAccess> computers;

    @Override
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return false;
        }
        computerQueueEvent(EVENT_PLAYER, player.username);
        return true;
    }

    protected void computerQueueEvent(String event, Object... args) {
        if (computers == null || computers.isEmpty()) {
            return;
        }
        for (IComputerAccess computer : computers) {
            computer.queueEvent(event, ArrayUtils.add(args, 0, computer.getAttachmentName()));
        }
    }

}