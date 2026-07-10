package net.ratstak.beyond_arsenal.item;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.common.Mod;
import net.ratstak.beyond_arsenal.item.Moditems;

public class overlay {
    public static final IGuiOverlay HUD_ELEMENT = (gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) {
            return;
        }

        ItemStack mainHandItem = player.getMainHandItem();

        if (mainHandItem.is(Moditems.CHUD.get())) {
            int boxWidth = 70;
            int boxHeight = 20;

            int x = width - boxWidth - 10;
            int y = height - boxHeight - 10;

            guiGraphics.fill(x, y, x + boxWidth, y + boxHeight, 0x90000000);

            guiGraphics.drawString(
                mc.font,
                "AMMO: 6",
                x + 5,  // 5 pixels inside our box
                y + 6,  // 6 pixels down inside our box
                0xFFFFFF
            );
        }
    };
}
