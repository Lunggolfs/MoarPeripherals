/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.common.item.ItemMPBase;
import com.theoriginalbit.moarperipherals.reference.Settings;
import cpw.mods.fml.common.registry.GameRegistry;

public class MaterialRegistry {

    public static ItemMPBase materialKeyboardPart;

    public static void init() {
        if (Settings.enableKeyboard) {
            materialKeyboardPart = new ItemMPBase("keyboardPart");
            GameRegistry.registerItem(materialKeyboardPart, materialKeyboardPart.getUnlocalizedName());
        }
    }

    public static void oreRegistration() {

    }
}
