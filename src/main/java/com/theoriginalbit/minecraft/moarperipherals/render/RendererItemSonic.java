package com.theoriginalbit.minecraft.moarperipherals.render;

import com.theoriginalbit.minecraft.moarperipherals.model.ModelSonic;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.render.base.CustomItemRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 * <p/>
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class RendererItemSonic extends CustomItemRenderer {

    private static final ModelBase model = new ModelSonic();

    public RendererItemSonic() {
        super(model);
    }

    @Override
    protected ResourceLocation getTexture(ItemStack stack) {
        return Constants.TEXTURES_MODEL.SONIC.getResourceLocation();
    }

    @Override
    protected void manipulateEntityRender(ItemStack stack) {
        float scale = 0.145f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, -1f, 0f);
    }

    @Override
    protected void manipulateInventoryRender(ItemStack stack) {
        float scale = 0.3f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, 1f, 0f);
    }

    @Override
    protected void manipulateThirdPersonRender(ItemStack stack) {
        float scale = 0.225f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(175, 1, 0, 0);
        GL11.glTranslatef(0f, -3.5f, -4.5f);
    }

    @Override
    protected void manipulateFirstPersonRender(ItemStack stack) {
        float scale = 0.3f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(140, 1, 0, 0);
        GL11.glRotatef(-60, 0, 1, 0);
        GL11.glRotatef(-20, 0, 0, 1);
        GL11.glTranslatef(-2f, -0.5f, -3.8f);
    }
}
