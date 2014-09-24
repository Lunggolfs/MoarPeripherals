/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.api.tile;

import com.theoriginalbit.moarperipherals.client.gui.GuiType;
import net.minecraft.tileentity.TileEntity;

/**
 * Register that your {@link TileEntity} has a GUI
 *
 * @author theoriginalbit
 */
public interface IHasGui {
    /**
     * Returns the ID of the GUI
     */
    public GuiType getGuiId();

}