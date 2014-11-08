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
package com.theoriginalbit.moarperipherals.common.item.abstracts;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.api.ITooltipInformer;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.utils.KeyboardUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemMoarP extends Item {

    private final String name;

    public ItemMoarP(String itemName) {
        super();
        name = itemName;

        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + ":" + name);
        setCreativeTab(MoarPeripherals.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        itemIcon = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":" + name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        Item item = stack.getItem();
        if (item instanceof ITooltipInformer) {
            if (KeyboardUtils.isShiftKeyDown()) {
                ((ITooltipInformer) item).addInformativeTooltip(stack, player, list, bool);
            } else {
                list.add(Constants.TOOLTIPS.SHIFT_INFO.getLocalised());
            }
        }
    }

}