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
package com.moarperipherals.integration.upgrade;

import com.theoriginalbit.framework.peripheral.turtle.UpgradePeripheral;
import com.theoriginalbit.framework.peripheral.wrapper.WrapperComputer;
import com.moarperipherals.client.render.IUpgradeToolIcon;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.init.ModItems;
import com.moarperipherals.integration.peripheral.PeripheralSolar;
import com.moarperipherals.Constants;
import com.moarperipherals.ModInfo;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * @author theoriginalbit
 * @since 25/10/14
 */
public class UpgradeSolar extends UpgradePeripheral implements IUpgradeToolIcon {
    private IIcon icon;

    public UpgradeSolar() {
        super(ConfigData.upgradeIdSolar, Constants.UPGRADE.SOLAR.getLocalised(), new ItemStack(ModItems.itemUpgradeSolar));
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return icon;
    }

    @Override
    protected WrapperComputer getPeripheralWrapper(ITurtleAccess access, TurtleSide side) {
        return new WrapperComputer(new PeripheralSolar(access));
    }

    @Override
    protected void update(ITurtleAccess turtle, TurtleSide side, WrapperComputer peripheral) {
        ((PeripheralSolar) peripheral.getInstance()).update();
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon(ModInfo.RESOURCE_DOMAIN + ":upgradeSolar");
    }
}
