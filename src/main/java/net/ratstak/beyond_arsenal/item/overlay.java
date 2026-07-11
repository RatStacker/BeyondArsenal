package net.ratstak.beyond_arsenal.item;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ratstak.beyond_arsenal.util.ModTags;

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
        if (mc.player == null || mc.screen != null) return;

        if (mc.player.getMainHandItem().is(ModTags.Items.GUNS)) {
            // Cancel left and right physical mouse clicks completely
            if (event.getButton() == org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT ||
                    event.getButton() == org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(net.minecraftforge.event.TickEvent.ClientTickEvent event) {
        // Run at START so it feeds into vanilla input processing perfectly before the tick happens
        if (event.phase == net.minecraftforge.event.TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player != null && mc.player.getMainHandItem().is(ModTags.Items.GUNS)) {

                // --- SIMULATE VANILLA RIGHT CLICK ---
                // Tell Minecraft the Right-Click button is being pressed if your custom key is pressed
                boolean isCustomKeyDown = net.ratstak.beyond_arsenal.item.ClientModEvents.GUN_SECONDARY_ACTION.isDown();
                mc.options.keyUse.setDown(isCustomKeyDown);

                // Transfer any rapid individual clicks over
                while (net.ratstak.beyond_arsenal.item.ClientModEvents.GUN_SECONDARY_ACTION.consumeClick()) {
                    net.minecraft.client.KeyMapping.click(mc.options.keyUse.getKey());
                }

                // --- KILL THE SWING ANIMATION ---
                // By forcing these variables to 0 every single frame, the arm visually locks in place.
                // Vanilla Minecraft will successfully open the door, but your gun will never move!
                mc.player.swinging = false;
                mc.player.swingTime = 0;
                mc.player.attackAnim = 0.0f;
                mc.player.oAttackAnim = 0.0f;
            }
        }
    }

}
