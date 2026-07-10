package net.ratstak.beyond_arsenal.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "beyond_arsenal", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Modinputevents {

    @SubscribeEvent
    public static void onLeftClickBlock(LeftClickBlock event) {
        ItemStack heldItem = event.getItemStack();














        if (heldItem.is(Moditems.CHUD.get())) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onLeftClickEntity(AttackEntityEvent event) {
        ItemStack heldItem = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND);

        if (heldItem.is(Moditems.CHUD.get())){
            event.setCanceled(true);
        }
    }
}
