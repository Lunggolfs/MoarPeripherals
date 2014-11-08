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
package com.theoriginalbit.moarperipherals.common.handler;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author theoriginalbit
 * @since 15/10/2014
 */
public class TickHandler {

    public static final TickHandler INSTANCE = new TickHandler();
    private static final ConcurrentLinkedQueue<FutureTask> callbacks = new ConcurrentLinkedQueue<FutureTask>();

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.type == TickEvent.Type.SERVER) {
            FutureTask callback;
            while ((callback = callbacks.poll()) != null) {
                callback.run();
            }
        }
    }

    public static <T> Future<T> addTickCallback(Callable<T> callback) {
        FutureTask<T> task = new FutureTask<T>(callback) {
            @Override
            protected void done() {
                try {
                    if (!isCancelled()) get();
                } catch (Throwable t) {
                    LogUtils.warn("Exception while executing callback! " + this);
                    t.printStackTrace();
                }
            }
        };
        callbacks.add(task);
        return task;
    }

    /*
     * Enabled when anything that uses it is needed.
     */
    public static boolean shouldRegister() {
        return ConfigHandler.enableFireworkLauncher;
    }
}
