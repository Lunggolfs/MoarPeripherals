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
package com.theoriginalbit.moarperipherals.api.peripheral.turtle;

import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public abstract class UpgradeTool implements ITurtleUpgrade {
    private final int id;
    private final String name;
    protected final ItemStack craftingStack;

    protected UpgradeTool(int upgradeId, String adjective, ItemStack craftingItemStack) {
        id = upgradeId;
        name = adjective;
        craftingStack = craftingItemStack;
    }

    @Override
    public final int getUpgradeID() {
        return id;
    }

    @Override
    public final String getUnlocalisedAdjective() {
        return name;
    }

    @Override
    public final TurtleUpgradeType getType() {
        return TurtleUpgradeType.Tool;
    }

    @Override
    public final ItemStack getCraftingItem() {
        return craftingStack;
    }

    @Override
    public final IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return null;
    }

    @Override
    public final TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
        switch (verb) {
            case Attack:
                return attack(turtle, direction);
            case Dig:
                return dig(turtle, direction);
        }
        return TurtleCommandResult.failure("Unsupported action");
    }

    @Override
    public final void update(ITurtleAccess turtle, TurtleSide side) {
        // NO-OP
    }

    protected abstract boolean canAttackEntity(Entity entity);

    protected abstract ArrayList<ItemStack> attackEntity(Entity entity);

    protected abstract boolean canAttackBlock(World world, int x, int y, int z, int dir, EntityPlayer turtle);

    protected abstract ArrayList<ItemStack> attackBlock(World world, int x, int y, int z, int dir, EntityPlayer turtle);

    protected abstract boolean canHarvestBlock(World world, int x, int y, int z);

    protected abstract ArrayList<ItemStack> harvestBlock(World world, int x, int y, int z);

    protected String getAttackFailureMessage() {
        return "Nothing to attack";
    }

    protected String getDigFailureMessage() {
        return "Nothing to dig";
    }

    protected final void store(final ITurtleAccess turtle, final ArrayList<ItemStack> list) {
        if (list != null) {
            for (final ItemStack stack : list) {
                store(turtle, stack);
            }
        }
    }

    protected final void store(final ITurtleAccess turtle, final ItemStack stack) {
        if (!storeItemStack(turtle, stack)) {
            ChunkCoordinates coordinates = turtle.getPosition();
            int direction = turtle.getDirection();
            int x = coordinates.posX + Facing.offsetsXForSide[direction];
            int y = coordinates.posY + Facing.offsetsYForSide[direction];
            int z = coordinates.posZ + Facing.offsetsZForSide[direction];
            InventoryUtils.spawnItemStackInWorld(stack, turtle.getWorld(), x, y, z);
        }
    }

    private TurtleCommandResult attack(ITurtleAccess turtle, int dir) {
        final World world = turtle.getWorld();
        final ChunkCoordinates coordinates = turtle.getPosition();
        int x = coordinates.posX + Facing.offsetsXForSide[dir];
        int y = coordinates.posY + Facing.offsetsYForSide[dir];
        int z = coordinates.posZ + Facing.offsetsZForSide[dir];

        final EntityPlayer player = new PlayerTurtle(turtle);
        @SuppressWarnings("unchecked")
        final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(x, y, z, x + 1d, y + 1d, z + 1d));

        for (Entity entity : list) {
            if (canAttackEntity(entity)) {
                store(turtle, attackEntity(entity));
                return TurtleCommandResult.success();
            }
        }

        if (canAttackBlock(world, x, y, z, dir, player)) {
            store(turtle, attackBlock(world, x, y, z, dir, player));
            return TurtleCommandResult.success();
        }

        return TurtleCommandResult.failure(getAttackFailureMessage());
    }

    private TurtleCommandResult dig(ITurtleAccess turtle, int dir) {
        final World world = turtle.getWorld();
        final ChunkCoordinates coordinates = turtle.getPosition();
        int x = coordinates.posX + Facing.offsetsXForSide[dir];
        int y = coordinates.posY + Facing.offsetsYForSide[dir];
        int z = coordinates.posZ + Facing.offsetsZForSide[dir];

        final Block block = world.getBlock(x, y, z);
        if (!world.isAirBlock(x, y, z) && block.getBlockHardness(world, x, y, z) > -1f && canHarvestBlock(world, x, y, z)) {
            final ArrayList<ItemStack> result = harvestBlock(world, x, y, z);
            if (result != null) {
                store(turtle, result);
                world.setBlockToAir(x, y, z);
                world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + world.getBlockMetadata(x, y, z) * 4096);
                return TurtleCommandResult.success();
            }
        }

        return TurtleCommandResult.failure(getDigFailureMessage());
    }

    private boolean storeItemStack(final ITurtleAccess turtle, ItemStack stack) {
        final IInventory inventory = turtle.getInventory();

        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack currentStack = inventory.getStackInSlot(i);
            if (currentStack == null) {
                inventory.setInventorySlotContents(i, stack.copy());
                stack.stackSize = 0;
                return true;
            } else if (currentStack.isStackable() && currentStack.isItemEqual(stack)) {
                int space = currentStack.getMaxStackSize() - currentStack.stackSize;
                if (space >= stack.stackSize) {
                    currentStack.stackSize += stack.stackSize;
                    stack.stackSize = 0;
                    return true;
                } else {
                    currentStack.stackSize = currentStack.getMaxStackSize();
                    stack.stackSize -= space;
                }
            }
        }

        return false;
    }
}