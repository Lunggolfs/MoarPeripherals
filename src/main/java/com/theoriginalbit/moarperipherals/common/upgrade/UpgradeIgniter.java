/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrade;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.upgrade.abstracts.PlayerTurtle;
import com.theoriginalbit.moarperipherals.common.upgrade.abstracts.UpgradeTool;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public class UpgradeIgniter extends UpgradeTool {
    private final Random rand = new Random();

    public UpgradeIgniter() {
        super(ConfigHandler.upgradeIdIgniter, Constants.UPGRADE.IGNITER, new ItemStack(Items.flint_and_steel));
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return Items.flint_and_steel.getIconFromDamage(0);
    }

    @Override
    protected TurtleCommandResult dig(ITurtleAccess turtle, int dir) {
        return TurtleCommandResult.failure();
    }

    @Override
    protected TurtleCommandResult attack(ITurtleAccess turtle, int dir) {
        final World world = turtle.getWorld();
        final ChunkCoordinates coordinates = turtle.getPosition();
        int x = coordinates.posX + Facing.offsetsXForSide[dir];
        int y = coordinates.posY + Facing.offsetsYForSide[dir];
        int z = coordinates.posZ + Facing.offsetsZForSide[dir];

        final EntityPlayer player = new PlayerTurtle(turtle);
        if (world.isAirBlock(x, y, z) && player.canPlayerEdit(x, y, z, dir, itemStack)) {
            world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            world.setBlock(x, y, z, Blocks.fire);
            return TurtleCommandResult.success();
        }
        if (world.getBlock(x, y, z) == Blocks.portal && player.canPlayerEdit(x, y, z, dir, itemStack)) {
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(Blocks.portal) + world.getBlockMetadata(x, y, z) * 4096);
            world.setBlock(x, y, z, Blocks.air);
            return TurtleCommandResult.success();
        }

        return TurtleCommandResult.failure("Cannot ignite block");
    }

    @Override
    protected boolean canHarvestBlock(World world, int x, int y, int z) {
        return false;
    }

    @Override
    protected ArrayList<ItemStack> harvestBlock(World world, int x, int y, int z) {
        return null;
    }

}
