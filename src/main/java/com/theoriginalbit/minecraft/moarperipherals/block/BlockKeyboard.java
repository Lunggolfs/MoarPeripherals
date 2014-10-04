package com.theoriginalbit.minecraft.moarperipherals.block;

import buildcraft.api.tools.IToolWrench;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.block.base.BlockPairable;
import com.theoriginalbit.minecraft.moarperipherals.gui.GuiType;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.utils.BlockNotifyFlags;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class BlockKeyboard extends BlockPairable {

    private final ForgeDirection[] validDirections = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST};

    public BlockKeyboard() {
        super(Settings.blockIdKeyboard, Material.iron, "keyboard");
        setStepSound(soundMetalFootstep);
        setRotationMode(RotationMode.FOUR);
        setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileKeyboard();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public final boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        return isOnTopOfSolidBlock(world, x, y, z, ForgeDirection.getOrientation(side).getOpposite());
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        // check if the item was a wrench first
        ItemStack equipped = player.getCurrentEquippedItem();
        if (equipped != null && equipped.getItem() instanceof IToolWrench) {
            return true;
        }

        // open the GUI if there is a connection
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile instanceof TileKeyboard) {
            TileKeyboard keyboard = (TileKeyboard) tile;
            if (keyboard.hasConnection()) {
                player.openGui(MoarPeripherals.instance, GuiType.KEYBOARD.ordinal(), world, x, y, z);
            } else if (!world.isRemote) {
                String message = EnumChatFormatting.RED + Constants.CHAT.CHAT_NOT_PAIRED.getLocalised();
                player.sendChatToPlayer(new ChatMessageComponent().addText(message));
            }
            return ((IActivateAwareTile) tile).onActivated(player, side, hitX, hitY, hitZ);
        }

        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        setBlockBounds(0f, 0f, 0f, 0.8f, 0.1f, 0.8f);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
    }

    @Override
    public ForgeDirection[] getValidRotations(World world, int x, int y, int z) {
        return validDirections;
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection direction) {
        switch (direction) {
            case UP: return false;
            case DOWN: return false;
            default:
                return world.getBlockMetadata(x, y, z) == direction.ordinal() || world.setBlockMetadataWithNotify(x, y, z, direction.ordinal(), BlockNotifyFlags.SEND_TO_CLIENTS);
        }
    }

    private boolean isOnTopOfSolidBlock(World world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.DOWN && isNeighborBlockSolid(world, x, y, z, ForgeDirection.DOWN);
    }

    private static boolean isNeighborBlockSolid(World world, int x, int y, int z, ForgeDirection side) {
        x += side.offsetX;
        y += side.offsetY;
        z += side.offsetZ;
        return world.isBlockSolidOnSide(x, y, z, side.getOpposite());
    }

}