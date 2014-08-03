package com.theoriginalbit.minecraft.moarperipherals.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

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
public class ModelSonic11 extends ModelBase {

    ModelRenderer emeraldMetalBase, handleEnd, grommet, emeraldBase, emeraldTip,
            handle, grip, base, gripBodyRing1, gripBodyRing2, connectorRing,
            arm1, arm2, arm3, arm4, extender1, extender2, extender3, extender4,
            extenderTip1, extenderTip2, extenderTip3, extenderTip4, gripBody1, gripBody2,
            handleBody1, handleBody2, baseBody1, baseBody2, handleGripRing1, handleGripRing2,
            baseHandleRing1, baseHandleRing2, baseRing1, baseRing2, greenTube, body1, body2, body3, body4;

    public ModelSonic11() {
        textureWidth = 128;
        textureHeight = 64;

        emeraldMetalBase = new ModelRenderer(this, 0, 13);
        emeraldMetalBase.addBox(-2F, -1F, -2F, 4, 2, 4);
        emeraldMetalBase.setRotationPoint(0F, -43F, 0F);
        emeraldMetalBase.setTextureSize(128, 64);
        emeraldMetalBase.mirror = true;
        setRotation(emeraldMetalBase, 0F, 0F, 0F);
        handleEnd = new ModelRenderer(this, 33, 0);
        handleEnd.addBox(-3F, -7F, -3F, 6, 2, 6);
        handleEnd.setRotationPoint(0F, 28F, 0F);
        handleEnd.setTextureSize(128, 64);
        handleEnd.mirror = true;
        setRotation(handleEnd, 0F, 0F, 0F);
        grommet = new ModelRenderer(this, 0, 0);
        grommet.addBox(0F, 0F, 0F, 4, 1, 4);
        grommet.setRotationPoint(-2F, -30.5F, -2F);
        grommet.setTextureSize(128, 64);
        grommet.mirror = true;
        setRotation(grommet, 0F, 0F, 0F);
        emeraldBase = new ModelRenderer(this, 0, 6);
        emeraldBase.addBox(0F, 0F, 0F, 3, 3, 3);
        emeraldBase.setRotationPoint(-1.5F, -47F, -1.5F);
        emeraldBase.setTextureSize(128, 64);
        emeraldBase.mirror = true;
        setRotation(emeraldBase, 0F, 0F, 0F);
        emeraldTip = new ModelRenderer(this, 0, 20);
        emeraldTip.addBox(0F, 0F, 0F, 2, 4, 2);
        emeraldTip.setRotationPoint(-1.333333F, -49.5F, 0F);
        emeraldTip.setTextureSize(128, 64);
        emeraldTip.mirror = true;
        setRotation(emeraldTip, 0F, 0.7853982F, 0F);
        handle = new ModelRenderer(this, 0, 27);
        handle.addBox(0F, 0F, 0F, 6, 13, 6);
        handle.setRotationPoint(-3F, 0F, -3F);
        handle.setTextureSize(128, 64);
        handle.mirror = true;
        setRotation(handle, 0F, 0F, 0F);
        grip = new ModelRenderer(this, 0, 49);
        grip.addBox(0F, 0F, 0F, 6, 9, 6);
        grip.setRotationPoint(-3F, -10F, -3F);
        grip.setTextureSize(128, 64);
        grip.mirror = true;
        setRotation(grip, 0F, 0F, 0F);
        base = new ModelRenderer(this, 33, 9);
        base.addBox(0F, 0F, 0F, 6, 6, 6);
        base.setRotationPoint(-3F, 14F, -3F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        gripBodyRing1 = new ModelRenderer(this, 34, 44);
        gripBodyRing1.addBox(0F, 0F, 0F, 8, 1, 7);
        gripBodyRing1.setRotationPoint(-4F, -11F, -3.5F);
        gripBodyRing1.setTextureSize(128, 64);
        gripBodyRing1.mirror = true;
        setRotation(gripBodyRing1, 0F, 0F, 0F);
        connectorRing = new ModelRenderer(this, 32, 21);
        connectorRing.addBox(0F, 0F, 0F, 6, 2, 6);
        connectorRing.setRotationPoint(-3F, -42F, -3F);
        connectorRing.setTextureSize(128, 64);
        connectorRing.mirror = true;
        setRotation(connectorRing, 0F, 0F, 0F);
        arm1 = new ModelRenderer(this, 25, 0);
        arm1.addBox(0F, 0F, 0F, 2, 21, 1);
        arm1.setRotationPoint(1F, -42F, 3.5F);
        arm1.setTextureSize(128, 64);
        arm1.mirror = true;
        setRotation(arm1, 0F, -3.141593F, 0F);
        arm2 = new ModelRenderer(this, 25, 24);
        arm2.addBox(0F, 0F, 0F, 1, 21, 2);
        arm2.setRotationPoint(2.5F, -42F, -1F);
        arm2.setTextureSize(128, 64);
        arm2.mirror = true;
        setRotation(arm2, 0F, 0F, 0F);
        arm3 = new ModelRenderer(this, 25, 0);
        arm3.addBox(0F, 0F, 0F, 2, 21, 1);
        arm3.setRotationPoint(-1F, -42F, -3.5F);
        arm3.setTextureSize(128, 64);
        arm3.mirror = true;
        setRotation(arm3, 0F, 0F, 0F);
        arm4 = new ModelRenderer(this, 25, 24);
        arm4.addBox(0F, 0F, 0F, 1, 21, 2);
        arm4.setRotationPoint(-2.5F, -42F, 1F);
        arm4.setTextureSize(128, 64);
        arm4.mirror = true;
        setRotation(arm4, 0F, 3.141593F, 0F);
        extender1 = new ModelRenderer(this, 25, 50);
        extender1.addBox(0F, 0F, 2F, 2, 9, 1);
        extender1.setRotationPoint(0.9F, -49F, 2.3F);
        extender1.setTextureSize(128, 64);
        extender1.mirror = true;
        setRotation(extender1, -0.1396263F, 0.7853982F, 0F);
        extender2 = new ModelRenderer(this, 25, 50);
        extender2.addBox(0F, 0F, -2F, 2, 9, 1);
        extender2.setRotationPoint(-3F, -49F, -1.6F);
        extender2.setTextureSize(128, 64);
        extender2.mirror = true;
        setRotation(extender2, 0.1396263F, 0.7853982F, 0F);
        extender3 = new ModelRenderer(this, 25, 50);
        extender3.addBox(0F, 0F, -2F, 2, 9, 1);
        extender3.setRotationPoint(-1.6F, -49F, 3F);
        extender3.setTextureSize(128, 64);
        extender3.mirror = true;
        setRotation(extender3, 0.1396263F, 2.356194F, 0F);
        extender4 = new ModelRenderer(this, 25, 50);
        extender4.addBox(0F, 0F, 2F, 2, 9, 1);
        extender4.setRotationPoint(2.3F, -49F, -0.9F);
        extender4.setTextureSize(128, 64);
        extender4.mirror = true;
        setRotation(extender4, -0.1396263F, 2.356194F, 0F);
        extenderTip1 = new ModelRenderer(this, 11, 22);
        extenderTip1.addBox(-1.7F, -0.1F, -2.2F, 2, 1, 1);
        extenderTip1.setRotationPoint(-1F, -49F, -2F);
        extenderTip1.setTextureSize(128, 64);
        extenderTip1.mirror = true;
        setRotation(extenderTip1, 0.1396263F, 0.7853982F, 0F);
        extenderTip2 = new ModelRenderer(this, 11, 22);
        extenderTip2.addBox(-2.4F, -0.1F, 2F, 2, 1, 1);
        extenderTip2.setRotationPoint(2F, -49F, 0F);
        extenderTip2.setTextureSize(128, 64);
        extenderTip2.mirror = true;
        setRotation(extenderTip2, -0.1396263F, 0.7853982F, 0F);
        extenderTip3 = new ModelRenderer(this, 11, 22);
        extenderTip3.addBox(-0.3F, 0F, -2.2F, 2, 1, 1);
        extenderTip3.setRotationPoint(1F, -49F, -2F);
        extenderTip3.setTextureSize(128, 64);
        extenderTip3.mirror = true;
        setRotation(extenderTip3, 0.1396263F, -0.7853982F, 0F);
        extenderTip4 = new ModelRenderer(this, 11, 22);
        extenderTip4.addBox(-1F, -0.3F, 2F, 2, 1, 1);
        extenderTip4.setRotationPoint(-1F, -49F, 1F);
        extenderTip4.setTextureSize(128, 64);
        extenderTip4.mirror = true;
        setRotation(extenderTip4, -0.1396263F, -0.7853982F, 0F);
        gripBody1 = new ModelRenderer(this, 60, 0);
        gripBody1.addBox(0F, 0F, 0F, 7, 9, 5);
        gripBody1.setRotationPoint(-3.5F, -10F, -2.5F);
        gripBody1.setTextureSize(128, 64);
        gripBody1.mirror = true;
        setRotation(gripBody1, 0F, 0F, 0F);
        gripBody2 = new ModelRenderer(this, 60, 0);
        gripBody2.addBox(0F, 0F, 0F, 7, 9, 5);
        gripBody2.setRotationPoint(-2.5F, -10F, 3.5F);
        gripBody2.setTextureSize(128, 64);
        gripBody2.mirror = true;
        setRotation(gripBody2, 0F, 1.570796F, 0F);
        handleBody1 = new ModelRenderer(this, 60, 15);
        handleBody1.addBox(0F, 0F, 0F, 5, 13, 7);
        handleBody1.setRotationPoint(-2.5F, 0F, -3.5F);
        handleBody1.setTextureSize(128, 64);
        handleBody1.mirror = true;
        setRotation(handleBody1, 0F, 0F, 0F);
        handleBody2 = new ModelRenderer(this, 60, 15);
        handleBody2.addBox(0F, 0F, 0F, 5, 13, 7);
        handleBody2.setRotationPoint(-3.5F, 0F, 2.5F);
        handleBody2.setTextureSize(128, 64);
        handleBody2.mirror = true;
        setRotation(handleBody2, 0F, 1.570796F, 0F);
        baseBody1 = new ModelRenderer(this, 33, 30);
        baseBody1.addBox(0F, 0F, 0F, 5, 6, 7);
        baseBody1.setRotationPoint(-2.5F, 14F, -3.5F);
        baseBody1.setTextureSize(128, 64);
        baseBody1.mirror = true;
        setRotation(baseBody1, 0F, 0F, 0F);
        baseBody2 = new ModelRenderer(this, 33, 30);
        baseBody2.addBox(0F, 0F, 0F, 5, 6, 7);
        baseBody2.setRotationPoint(-3.5F, 14F, 2.5F);
        baseBody2.setTextureSize(128, 64);
        baseBody2.mirror = true;
        setRotation(baseBody2, 0F, 1.570796F, 0F);
        gripBodyRing2 = new ModelRenderer(this, 34, 53);
        gripBodyRing2.addBox(0F, 0F, 0F, 7, 1, 8);
        gripBodyRing2.setRotationPoint(-3.5F, -11F, -4F);
        gripBodyRing2.setTextureSize(128, 64);
        gripBodyRing2.mirror = true;
        setRotation(gripBodyRing2, 0F, 0F, 0F);
        handleGripRing1 = new ModelRenderer(this, 34, 44);
        handleGripRing1.addBox(0F, 0F, 0F, 8, 1, 7);
        handleGripRing1.setRotationPoint(-4F, -1F, -3.5F);
        handleGripRing1.setTextureSize(128, 64);
        handleGripRing1.mirror = true;
        setRotation(handleGripRing1, 0F, 0F, 0F);
        handleGripRing2 = new ModelRenderer(this, 34, 53);
        handleGripRing2.addBox(0F, 0F, 0F, 7, 1, 8);
        handleGripRing2.setRotationPoint(-3.5F, -1F, -4F);
        handleGripRing2.setTextureSize(128, 64);
        handleGripRing2.mirror = true;
        setRotation(handleGripRing2, 0F, 0F, 0F);
        baseHandleRing1 = new ModelRenderer(this, 34, 53);
        baseHandleRing1.addBox(0F, 0F, 0F, 7, 1, 8);
        baseHandleRing1.setRotationPoint(-3.5F, 13F, -4F);
        baseHandleRing1.setTextureSize(128, 64);
        baseHandleRing1.mirror = true;
        setRotation(baseHandleRing1, 0F, 0F, 0F);
        baseHandleRing2 = new ModelRenderer(this, 34, 44);
        baseHandleRing2.addBox(0F, 0F, 0F, 8, 1, 7);
        baseHandleRing2.setRotationPoint(-4F, 13F, -3.5F);
        baseHandleRing2.setTextureSize(128, 64);
        baseHandleRing2.mirror = true;
        setRotation(baseHandleRing2, 0F, 0F, 0F);
        baseRing1 = new ModelRenderer(this, 34, 44);
        baseRing1.addBox(0F, 0F, 0F, 8, 1, 7);
        baseRing1.setRotationPoint(-4F, 20F, -3.5F);
        baseRing1.setTextureSize(128, 64);
        baseRing1.mirror = true;
        setRotation(baseRing1, 0F, 0F, 0F);
        baseRing2 = new ModelRenderer(this, 34, 53);
        baseRing2.addBox(0F, 0F, 0F, 7, 1, 8);
        baseRing2.setRotationPoint(-3.5F, 20F, -4F);
        baseRing2.setTextureSize(128, 64);
        baseRing2.mirror = true;
        setRotation(baseRing2, 0F, 0F, 0F);
        greenTube = new ModelRenderer(this, 85, 0);
        greenTube.addBox(0F, 0F, 0F, 3, 29, 3);
        greenTube.setRotationPoint(-1.5F, -40F, -1.5F);
        greenTube.setTextureSize(128, 64);
        greenTube.mirror = true;
        setRotation(greenTube, 0F, 0F, 0F);
        body1 = new ModelRenderer(this, 16, 0);
        body1.addBox(0F, 0F, 0F, 2, 19, 2);
        body1.setRotationPoint(-3F, -30F, 1F);
        body1.setTextureSize(128, 64);
        body1.mirror = true;
        setRotation(body1, 0F, 0F, 0F);
        body2 = new ModelRenderer(this, 16, 0);
        body2.addBox(0F, 0F, 0F, 2, 19, 2);
        body2.setRotationPoint(1F, -30F, 1F);
        body2.setTextureSize(128, 64);
        body2.mirror = true;
        setRotation(body2, 0F, 0F, 0F);
        body3 = new ModelRenderer(this, 16, 0);
        body3.addBox(0F, 0F, 0F, 2, 19, 2);
        body3.setRotationPoint(1F, -30F, -3F);
        body3.setTextureSize(128, 64);
        body3.mirror = true;
        setRotation(body3, 0F, 0F, 0F);
        body4 = new ModelRenderer(this, 16, 0);
        body4.addBox(0F, 0F, 0F, 2, 19, 2);
        body4.setRotationPoint(-3F, -30F, -3F);
        body4.setTextureSize(128, 64);
        body4.mirror = true;
        setRotation(body4, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        emeraldMetalBase.render(f5);
        handleEnd.render(f5);
        grommet.render(f5);
        emeraldBase.render(f5);
        emeraldTip.render(f5);
        handle.render(f5);
        grip.render(f5);
        base.render(f5);
        gripBodyRing1.render(f5);
        connectorRing.render(f5);
        arm1.render(f5);
        arm2.render(f5);
        arm3.render(f5);
        arm4.render(f5);
        extender1.render(f5);
        extender2.render(f5);
        extender3.render(f5);
        extender4.render(f5);
        extenderTip1.render(f5);
        extenderTip2.render(f5);
        extenderTip3.render(f5);
        extenderTip4.render(f5);
        gripBody1.render(f5);
        gripBody2.render(f5);
        handleBody1.render(f5);
        handleBody2.render(f5);
        baseBody1.render(f5);
        baseBody2.render(f5);
        gripBodyRing2.render(f5);
        handleGripRing1.render(f5);
        handleGripRing2.render(f5);
        baseHandleRing1.render(f5);
        baseHandleRing2.render(f5);
        baseRing1.render(f5);
        baseRing2.render(f5);
        greenTube.render(f5);
        body1.render(f5);
        body2.render(f5);
        body3.render(f5);
        body4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
