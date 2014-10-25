/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile.firework;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
public class QueueBuffer {
    private final int maxSize;
    private final String invName;
    private final ArrayList<ItemStackWrapper> inventory;

    public QueueBuffer(String name, int size) {
        inventory = Lists.newArrayListWithCapacity(size);
        invName = name;
        maxSize = size;
    }

    public void readFromNBT(NBTTagCompound tag) {
        final NBTTagList list = tag.getTagList("BufferInv" + invName, 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            final NBTTagCompound itemTag = list.getCompoundTagAt(i);
            addItemStack(ItemStack.loadItemStackFromNBT(itemTag));
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        final NBTTagList list = new NBTTagList();
        for (final ItemStackWrapper wrapper : inventory) {
            final NBTTagCompound itemTag = wrapper.getItemStack().writeToNBT(new NBTTagCompound());
            list.appendTag(itemTag);
        }
        tag.setTag("BufferInv" + invName, list);
    }

    public int getSizeInventory() {
        return maxSize;
    }

    public int getCurrentSize() {
        return inventory.size();
    }

    public void clear() {
        inventory.clear();
    }

    public long addItemStack(ItemStack stack) {
        if (inventory.size() < maxSize) {
            final ItemStackWrapper wrapper = new ItemStackWrapper(stack.splitStack(1));
            inventory.add(wrapper);
            return wrapper.getId();
        }
        return -1;
    }

    public ArrayList<Integer> getWrapperIds() {
        final ArrayList<Integer> result = Lists.newArrayList();
        for (final ItemStackWrapper wrapper : inventory) {
            result.add(wrapper.getId());
        }
        return result;
    }

    public ItemStack getNextItemStack() {
        return inventory.size() > 0 ? inventory.remove(0).getItemStack() : null;
    }

    public boolean containsItemStackWithId(int id) {
        for (final ItemStackWrapper wrapper : inventory) {
            if (wrapper != null && wrapper.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public ItemStack peekItemStackWithId(int id) {
        for (final ItemStackWrapper wrapper : inventory) {
            if (wrapper.getId() == id) {
                return wrapper.getItemStack();
            }
        }
        return null;
    }

    public ItemStack getItemStackWithId(int id) {
        // loop the inventory
        for (int i = 0; i < inventory.size(); ++i) {
            // get the wrapper object
            final ItemStackWrapper wrapper = inventory.get(i);
            // if the wrapper object is the one we're after
            if (wrapper != null && wrapper.getId() == id) {
                // remove it
                inventory.remove(i);
                // return it's ItemStack
                return wrapper.getItemStack();
            }
        }
        return null;
    }

    public boolean hasFreeSpace() {
        return getCurrentSize() < getSizeInventory();
    }

    public void insertOrExplode(IInventory inv, World world, int x, int y, int z, int id) {
        // get the rocket with the supplied id
        insertOrExplodeInternal(getItemStackWithId(id), inv, world, x, y, z);
    }

    public void explodeBuffer(World world, int x, int y, int z) {
        for (final ItemStackWrapper item : inventory) {
            InventoryUtils.spawnInWorld(item.getItemStack(), world, x, y, z);
        }
    }

    private void insertOrExplodeInternal(ItemStack stack, IInventory inv, World world, int x, int y, int z) {
        if (stack != null) {
            // see if it can do into the inventory
            final int slot = InventoryUtils.findSlotFor(inv, stack);
            // add to the inventory, or spawn in the world if it wont fit in the inventory
            if (slot == -1) {
                InventoryUtils.spawnInWorld(stack, world, x, y, z);
            } else {
                inv.getStackInSlot(slot).stackSize += stack.stackSize;
            }
            InventoryUtils.spawnInWorld(getNextItemStack(), world, x, y, z);
        }
    }

}
