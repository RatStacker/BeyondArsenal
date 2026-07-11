package net.ratstak.beyond_arsenal.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.ratstak.beyond_arsenal.BeyondArsenal;

public class ModTags {
    public static class Blocks{

    }

    public static class Items {
        public static final TagKey<Item> GUNS = tag("guns");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(BeyondArsenal.MOD_ID, name));
        }
    }
}
