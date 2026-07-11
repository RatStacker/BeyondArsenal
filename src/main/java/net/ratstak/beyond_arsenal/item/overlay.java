package net.ratstak.beyond_arsenal.item;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ratstak.beyond_arsenal.util.ModTags;
import org.checkerframework.checker.units.qual.A;

@Mod.EventBusSubscriber(modid = "beyond_arsenal", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class overlay {

    private static final ResourceLocation WHITE_DOT = new ResourceLocation("beyond_arsenal", "textures/gui/dot.png");
    public static final IGuiOverlay HUD_ELEMENT = (gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) {
            return;
        }

        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.is(ModTags.Items.GUNS)) {

            int boxWidth = 50;
            int boxHeight = 20;
            int AmmoX = width - boxWidth - 10;
            int AmmoY = height - boxHeight - 10;

            guiGraphics.fill(AmmoX, AmmoY, AmmoX + boxWidth, AmmoY + boxHeight, 0x90000000);

            guiGraphics.drawString(
                    mc.font,
                    "AMMO: 6",
                    AmmoX + 5,  // 5 pixels inside our box
                    AmmoY + 6,  // 6 pixels down inside our box
                    0xFFFFFF
            );
        }
    };
    @SubscribeEvent
    public static void onRenderCrosshair(net.minecraftforge.client.event.RenderGuiOverlayEvent.Pre event) {
        // Check if the game is about to draw the vanilla crosshair
        if (event.getOverlay() == net.minecraftforge.client.gui.overlay.VanillaGuiOverlay.CROSSHAIR.type()) {

            Player player = Minecraft.getInstance().player;
            if (player != null && player.getMainHandItem().is(ModTags.Items.GUNS)) {
                // Cancel the event so the vanilla crosshair disappears
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public static void onBlockHighlight(RenderHighlightEvent.Block event) {
        Player player = Minecraft.getInstance().player;

        if (player != null) {
            ItemStack heldItem = player.getMainHandItem();

            if (heldItem.is(ModTags.Items.GUNS)) {
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public static void onRawMouseInput(net.minecraftforge.client.event.InputEvent.MouseButton.Pre event) {
        Minecraft mc = Minecraft.getInstance();

        // Do nothing if the player is in a menu, inventory, or paused
        if (mc.player == null || mc.screen != null) return;

        // Check if the hardware input was Left Click (0) or Right Click (1)
        if (event.getButton() == org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT ||
                event.getButton() == org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT) {

            if (mc.player.getMainHandItem().is(ModTags.Items.GUNS)) {
                // This completely eats the hardware click. Minecraft will have no idea you clicked.
                // This guarantees ZERO arm swinging, block breaking, or interacting!
                event.setCanceled(true);

                // IMPORTANT: Because you are eating the left click, if you want your gun
                // to shoot when you left-click, you must trigger it right here!
                if (event.getButton() == org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT && event.getAction() == org.lwjgl.glfw.GLFW.GLFW_PRESS) {
                    // Send your custom "Shoot" network packet to the server right here!
                }
            }
        }
    }
    @SubscribeEvent
    public static void onClientTick(net.minecraftforge.event.TickEvent.ClientTickEvent event) {
        // We only want to execute logic once per tick, so we check for the END phase
        if (event.phase == net.minecraftforge.event.TickEvent.Phase.END) {

            // .consumeClick() returns true if the key was pressed down, and then resets it.
            // A while loop is standard here so it registers every single click if pressed rapidly.
            while (net.ratstak.beyond_arsenal.item.ClientModEvents.GUN_SECONDARY_ACTION.consumeClick()) {

                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.player.getMainHandItem().is(ModTags.Items.GUNS)) {

                    // --- DO YOUR CUSTOM ACTION HERE ---

                    // For testing, this will just print a message above your hotbar:
                    mc.player.displayClientMessage(
                            net.minecraft.network.chat.Component.literal("Secondary Action Pressed!"),
                            true // true displays it on the action bar above your ammo counter
                    );

                    // If you want to open a screen, you would do:
                    // mc.setScreen(new YourCustomScreen());
                }
            }
        }
    }

}
