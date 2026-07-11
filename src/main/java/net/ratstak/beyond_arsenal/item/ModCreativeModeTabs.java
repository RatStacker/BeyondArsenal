package net.ratstak.beyond_arsenal.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.ratstak.beyond_arsenal.BeyondArsenal;
import net.ratstak.beyond_arsenal.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeyondArsenal.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CHUD_TAB = CREATIVE_MODE_TABS.register("chud_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.CHUD.get()))
                    .title(Component.translatable("creativetab.chud_tab"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(Moditems.CHUD.get());
                        pOutput.accept(Moditems.RAW_CHUD.get());
                        pOutput.accept(Moditems.CHUDSIXER.get());

                        pOutput.accept(ModBlocks.CHUD_BLOCK.get());
                        pOutput.accept(ModBlocks.RAW_CHUD_BLOCK.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
