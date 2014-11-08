/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;

public final class ChatUtils {

    public static void sendChatToPlayer(String[] to, String message) {
        message = ChatAllowedCharacters.filerAllowedCharacters(message);
        final ChatComponentText msg = new ChatComponentText(message);
        for (String user : to) {
            final EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(user);
            if (player != null) {
                player.addChatComponentMessage(msg);
            }
        }
    }

    public static void sendChatToPlayer(String to, String message) {
        sendChatToPlayer(new String[]{to}, message);
    }

}