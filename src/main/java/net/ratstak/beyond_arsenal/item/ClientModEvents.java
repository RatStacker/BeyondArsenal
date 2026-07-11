package net.ratstak.beyond_arsenal.item;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

// Note: bus = Mod.EventBusSubscriber.Bus.MOD
@Mod.EventBusSubscriber(modid = "beyond_arsenal", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    // Creates the keybind. Default is 'R'.
    public static final KeyMapping GUN_SECONDARY_ACTION = new KeyMapping(
            "key.beyond_arsenal.secondary_action", // The translation key for your en_us.json
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R, // The default button (R key)
            "key.categories.beyond_arsenal" // The category name in the controls menu
    );

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        // Tells the game this keybind exists
        event.register(GUN_SECONDARY_ACTION);
    }
}