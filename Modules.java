package uz.ozbek.client.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import uz.ozbek.client.OzbekClient;

public final class Modules {
    private Modules() {}
    private static double oldGamma = -1;
    private static double oldFov = -1;

    public static void tick(MinecraftClient c) {
        if (c.player == null) return;
        if (OzbekClient.CONFIG.fullbright) {
            if (oldGamma < 0) oldGamma = c.options.getGamma().getValue();
            c.options.getGamma().setValue(16.0);
        } else if (oldGamma >= 0) {
            c.options.getGamma().setValue(oldGamma); oldGamma = -1;
        }
        if (OzbekClient.CONFIG.togglesprint && !c.player.isSprinting()) {
            c.player.setSprinting(true);
        }
        if (OzbekClient.CONFIG.zoom) {
            if (oldFov < 0) oldFov = c.options.getFov().getValue();
            c.options.getFov().setValue(30);
        } else if (oldFov >= 0) {
            c.options.getFov().setValue(oldFov); oldFov = -1;
        }
        if (OzbekClient.CONFIG.autoclicker && c.currentScreen == null && c.options.attackKey.isPressed()) {
            if (c.interactionManager != null && c.player.getAttackCooldownProgress(0.0f) >= 1.0f) {
                c.doAttack();
            }
        }
    }

    public static void reset(MinecraftClient c) {
        if (oldGamma >= 0) { c.options.getGamma().setValue(oldGamma); oldGamma = -1; }
        if (oldFov >= 0) { c.options.getFov().setValue(oldFov); oldFov = -1; }
    }
}
