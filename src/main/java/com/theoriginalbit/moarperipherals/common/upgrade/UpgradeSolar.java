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

import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeToolIcon;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.registry.ModItems;
import com.theoriginalbit.moarperipherals.common.upgrade.abstracts.UpgradePeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 25/10/14
 */
public class UpgradeSolar extends UpgradePeripheral implements IUpgradeToolIcon {
    private static final int TICK_ADD_FUEL = 100; // 5 seconds
    private int tick = 0;
    private IIcon icon;

    public UpgradeSolar() {
        super(ConfigHandler.upgradeIdSolar, Constants.UPGRADE.SOLAR, new ItemStack(ModItems.itemUpgradeSolar), null);
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return icon;
    }

    @Override
    protected void update(ITurtleAccess turtle, TurtleSide side, IPeripheral peripheral) {
        final World world = turtle.getWorld();
        // if the world has a sky, this code is being run on a server, the turtle needs fuel, and 20 ticks have passed
        if (!world.provider.hasNoSky && !world.isRemote && turtle.isFuelNeeded() && turtle.getFuelLevel() < turtle.getFuelLimit() && ++tick > TICK_ADD_FUEL) {
            final ChunkCoordinates coordinates = turtle.getPosition();
            if (!world.canBlockSeeTheSky(coordinates.posX, coordinates.posY, coordinates.posZ)) {
                return;
            }
            int light = world.getSavedLightValue(EnumSkyBlock.Sky, coordinates.posX, coordinates.posY, coordinates.posZ) - world.skylightSubtracted;
            float angle = world.getCelestialAngleRadians(1f);

            if (angle < (float) Math.PI) {
                angle += (0f - angle) * 0.2f;
            } else {
                angle += (((float) Math.PI * 2f) - angle) * 0.2f;
            }

            light = Math.round((float) light * MathHelper.cos(angle));

            // if the turtle can see sky above it
            if (light > 7) {
                turtle.addFuel(1);
            }
            tick = 0;
        }
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon(ModInfo.RESOURCE_DOMAIN + ":upgradeSolar");
    }
}
