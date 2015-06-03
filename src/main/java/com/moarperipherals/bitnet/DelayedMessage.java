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
package com.moarperipherals.bitnet;

import com.moarperipherals.api.bitnet.BitNetMessage;
import com.moarperipherals.api.bitnet.IBitNetRelay;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.util.LogUtil;

/**
 * A container class for a {@link BitNetMessage}, tracking how
 * many ticks remain before the message payload should be delivered to the receiving tower
 *
 * @author theoriginalbit
 */
final class DelayedMessage {

    private final IBitNetRelay receiver;
    private final BitNetMessage payload;
    private int sendDelay;

    public DelayedMessage(IBitNetRelay tower, BitNetMessage message, double distance) {
        receiver = tower;
        payload = message;
        // calculate the cost to send this message
        sendDelay = (int) (Math.ceil(distance / 100) * ConfigData.bitNetMessageDelay);
        LogUtil.debug(String.format("Created delayed message, delay=%d payload=%s", sendDelay, payload));
    }

    /**
     * Invoked once per server-tick so the delay tracker can count down and determine when the message
     * should be sent
     *
     * @return whether the message was dispatched
     */
    public boolean tick() {
        if (--sendDelay <= 0) {
            LogUtil.debug(String.format("Delay expired, sending message %s", payload));
            receiver.receive(payload);
            return true;
        }
        return false;
    }

}
