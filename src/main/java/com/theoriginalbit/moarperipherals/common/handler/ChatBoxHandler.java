/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.theoriginalbit.moarperipherals.api.listener.IChatListener;
import com.theoriginalbit.moarperipherals.api.listener.ICommandListener;
import com.theoriginalbit.moarperipherals.api.listener.IDeathListener;
import com.theoriginalbit.moarperipherals.api.listener.IPlayerEventListener;
import com.theoriginalbit.moarperipherals.common.reference.Mods;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public final class ChatBoxHandler {

    public static final ChatBoxHandler instance = new ChatBoxHandler();

    public static void init() {
        if (Loader.isModLoaded(Mods.OPENPERIPHERALADDON)) {
            LogUtils.info("Detected OpenPeripheral-Addons installed. Registering the terminal glasses command as a ChatBox command so it is ignored by ChatBoxes.");
            try {
                instance.addCommandListener(new ICommandListener() {
                    private static final String OPENPCOMMAND = "$$";

                    @Override
                    public String getToken() {
                        return OPENPCOMMAND;
                    }

                    @Override
                    public void onServerChatEvent(String message, EntityPlayer player) {
                    }
                });
            } catch (Exception e) {
                LogUtils.debug("Failed to register OpenPeripheral-Addon ChatBox command listener");
                e.printStackTrace();
            }
        }
    }

    private final ArrayList<IChatListener> chatListeners = Lists.newArrayList();
    private final ArrayList<IDeathListener> deathListeners = Lists.newArrayList();
    private final ArrayList<IPlayerEventListener> playerListeners = Lists.newArrayList();
    private final HashMap<String, ArrayList<ICommandListener>> commandListeners = Maps.newHashMap();

    public void addChatListener(IChatListener listener) {
        synchronized (chatListeners) {
            if (!chatListeners.contains(listener)) {
                chatListeners.add(listener);
            }
        }
    }

    public void removeChatListener(IChatListener listener) {
        synchronized (chatListeners) {
            if (chatListeners.contains(listener)) {
                chatListeners.remove(listener);
            }
        }
    }

    public void addDeathListener(IDeathListener listener) {
        synchronized (deathListeners) {
            if (!deathListeners.contains(listener)) {
                deathListeners.add(listener);
            }
        }
    }

    public void removeDeathListener(IDeathListener listener) {
        synchronized (deathListeners) {
            if (deathListeners.contains(listener)) {
                deathListeners.remove(listener);
            }
        }
    }

    public void addPlayerEventListener(IPlayerEventListener listener) {
        synchronized (playerListeners) {
            if (!playerListeners.contains(listener)) {
                playerListeners.add(listener);
            }
        }
    }

    public void removePlayerEventListener(IPlayerEventListener listener) {
        synchronized (playerListeners) {
            if (playerListeners.contains(listener)) {
                playerListeners.remove(listener);
            }
        }
    }

    public void addCommandListener(ICommandListener listener) throws Exception {
        final String token = listener.getToken();

        synchronized (commandListeners) {
            if (!commandListeners.containsKey(token)) {
                commandListeners.put(token, new ArrayList<ICommandListener>());
            }
            commandListeners.get(token).add(listener);
        }
    }

    public void removeCommandListener(ICommandListener listener) {
        final String token = listener.getToken();

        synchronized (commandListeners) {
            if (commandListeners.containsKey(token)) {
                commandListeners.get(token).remove(listener);
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onServerChatEvent(ServerChatEvent event) {
        // lets just ignore canceled events
        if (event.isCanceled()) {
            return;
        }

        // check if it was a command first, if it is, chat listeners shouldn't get this!
        synchronized (commandListeners) {
            for (Entry<String, ArrayList<ICommandListener>> entry : commandListeners.entrySet()) {
                final String token = entry.getKey();
                if (event.message.startsWith(token)) {
                    for (ICommandListener listener : entry.getValue()) {
                        listener.onServerChatEvent(event.message.substring(token.length()).trim(), event.player);
                        event.setCanceled(true);
                    }
                }
            }
        }

        // the event was once valid, but a ICommandListener used the event
        if (event.isCanceled()) {
            return;
        }

        // it wasn't a command, IChatListeners can have it now
        synchronized (chatListeners) {
            for (IChatListener listener : chatListeners) {
                listener.onServerChatEvent(event);
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onLivingDeathEvent(LivingDeathEvent event) {
        synchronized (deathListeners) {
            for (IDeathListener listener : deathListeners) {
                listener.onDeathEvent(event);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        synchronized (playerListeners) {
            for (IPlayerEventListener listener : playerListeners) {
                listener.onPlayerJoin(event.player.getDisplayName());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerLoggedOutEvent event) {
        synchronized (playerListeners) {
            for (IPlayerEventListener listener : playerListeners) {
                listener.onPlayerLeave(event.player.getDisplayName());
            }
        }
    }

}