package com.theoriginalbit.minecraft.moarperipherals.render;

import com.theoriginalbit.minecraft.moarperipherals.item.ItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.model.ModelItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.lookup.ModelTextures;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class RendererItemInkCartridge extends CustomItemRenderer {

    private static final ResourceLocation[] textures;
    private static final ModelBase modelCartridgeEmpty = new ModelItemInkCartridge(true);
    private static final ModelBase modelCartridgeFilled = new ModelItemInkCartridge(false);

    public RendererItemInkCartridge() {
        super(modelCartridgeEmpty);
    }

    @Override
    protected ResourceLocation getTexture(ItemStack stack) {
        int inkColor = ItemInkCartridge.getInkColor(stack);
        return textures[inkColor];
    }

    @Override
    protected void manipulateEntityRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.24f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, -0.5f, 0f);
    }

    @Override
    protected void manipulateInventoryRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.6f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
    }

    @Override
    protected void manipulateThirdPersonRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.3f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(120, 1, 0, 0);
        GL11.glRotatef(-55, 0, 0, 1);
        GL11.glRotatef(-40, 0, 1, 0);
        GL11.glTranslatef(-3.2f, 2.1f, -1.7f);
    }

    @Override
    protected void manipulateFirstPersonRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.5f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(170, 0, 0, 1);
        GL11.glRotatef(40, 0, 1, 0);
        GL11.glRotatef(-60, 1, 0, 0);
        GL11.glTranslatef(-2.2f, -1f, -1.5f);
    }

    private void selectModel(ItemStack stack) {
        modelItem = ItemInkCartridge.isCartridgeEmpty(stack) ? modelCartridgeEmpty : modelCartridgeFilled;
    }

    static {
        final String texturePath = ModelTextures.INK_CARTRIDGE.getPath();
        textures = new ResourceLocation[17];
        for (int i = 0; i < textures.length - 1; ++i) {
            textures[i] = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, String.format(texturePath, i));
        }
        textures[textures.length - 1] = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, String.format(texturePath, "Empty"));
    }

}