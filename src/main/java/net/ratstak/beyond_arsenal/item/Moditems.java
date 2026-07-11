package net.ratstak.beyond_arsenal.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.ratstak.beyond_arsenal.BeyondArsenal;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BeyondArsenal.MOD_ID);
    public static final RegistryObject<Item> CHUD = ITEMS.register("chud",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_CHUD = ITEMS.register("raw_chud",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHUDSIXER = ITEMS.register("chudsixer",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
