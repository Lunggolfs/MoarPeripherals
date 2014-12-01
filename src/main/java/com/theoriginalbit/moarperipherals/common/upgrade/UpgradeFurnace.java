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
package com.theoriginalbit.moarperipherals.common.upgrade;

import com.theoriginalbit.moarperipherals.api.peripheral.wrapper.WrapperComputer;
import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeToolIcon;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.api.peripheral.turtle.UpgradePeripheral;
import com.theoriginalbit.moarperipherals.common.upgrade.peripheral.PeripheralFurnace;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

/**
 * @author theoriginalbit
 * @since 27/10/14
 */
public class UpgradeFurnace extends UpgradePeripheral implements IUpgradeToolIcon {
    private Icon icon;

    public UpgradeFurnace() {
        super(ConfigHandler.upgradeIdFurnace, Constants.UPGRADE.FURNACE.getLocalised(), new ItemStack(Block.furnaceIdle));
    }

    @Override
    protected WrapperComputer getPeripheralWrapper(ITurtleAccess access, TurtleSide side) {
        return new WrapperComputer(new PeripheralFurnace(access, side));
    }

    @Override
    protected void update(ITurtleAccess turtle, TurtleSide side, WrapperComputer peripheral) {
        // NO-OP
    }

    @Override
    public Icon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return icon;
    }

    @Override
    public void registerIcons(IconRegister register) {
        icon = register.registerIcon("furnace_front_off");
    }
}
