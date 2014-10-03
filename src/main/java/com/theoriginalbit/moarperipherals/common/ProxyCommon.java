/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.client.gui.GuiHandler;
import com.theoriginalbit.moarperipherals.common.handler.ChatHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.ArrayUtils;

public class ProxyCommon {

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(ChatHandler.instance);
        NetworkRegistry.INSTANCE.registerGuiHandler(MoarPeripherals.instance, new GuiHandler());
    }

    public void init() {

    }

    public void postInit() {

    }

    public World getClientWorld(int dimId) {
        return MinecraftServer.getServer().worldServerForDimension(dimId);
    }

    public boolean isClient() {
        return false;
    }

    public void registerRenderInfo() {
    }

    public boolean isOp(EntityPlayer player) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        return ArrayUtils.contains(server.getConfigurationManager().func_152606_n(), player.getDisplayName().toLowerCase());
    }

}