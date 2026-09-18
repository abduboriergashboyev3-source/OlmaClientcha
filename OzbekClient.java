package uz.ozbek.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import uz.ozbek.client.config.ClientConfig;
import uz.ozbek.client.config.ConfigManager;
import uz.ozbek.client.config.FriendManager;
import uz.ozbek.client.feature.Killaura;
import uz.ozbek.client.feature.Modules;
import uz.ozbek.client.gui.ClientScreen;

import java.io.IOException;

public class OzbekClient implements ClientModInitializer {
    public static final ClientConfig CONFIG = new ClientConfig();
    private static KeyBinding menuKey;
    private static KeyBinding auraKey;

    @Override public void onInitializeClient() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("O'zbek Client menyusi", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "O'zbek Client"));
        auraKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("Jangni yoqish/o'chirish", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "O'zbek Client"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) client.setScreen(new ClientScreen());
            while (auraKey.wasPressed()) CONFIG.killaura = !CONFIG.killaura;
            Killaura.tick(client);
            Modules.tick(client);
        });
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient c = MinecraftClient.getInstance();
            if (!CONFIG.hud || c.player == null) return;
            int x = 8, y = 8;
            drawContext.drawTextWithShadow(c.textRenderer, "O'ZBEK CLIENT", x, y, 0x55AAFF);
            drawContext.drawTextWithShadow(c.textRenderer, "Jang: " + (CONFIG.killaura ? "YOQ" : "O'CHIQ"), x, y + 12, 0xFFFFFF);
            drawContext.drawTextWithShadow(c.textRenderer, "Do'stlar: " + FriendManager.all().size(), x, y + 24, 0xFFFFFF);
            drawContext.drawTextWithShadow(c.textRenderer, "FPS: " + c.getCurrentFps(), x, y + 36, 0xFFFFFF);
        });
    }

    public static void command(String input) {
        String[] a = input.trim().split("\\s+");
        MinecraftClient c = MinecraftClient.getInstance();
        if (a.length == 0) return;
        try {
            if (a[0].equalsIgnoreCase(".friend")) {
                if (a.length >= 3 && a[1].equalsIgnoreCase("add")) { FriendManager.add(a[2]); msg("Do'st qo'shildi: " + a[2]); return; }
                if (a.length >= 3 && a[1].equalsIgnoreCase("remove")) { FriendManager.remove(a[2]); msg("Do'st olib tashlandi: " + a[2]); return; }
                if (a.length >= 2 && a[1].equalsIgnoreCase("list")) { msg("Do'stlar: " + String.join(", ", FriendManager.all())); return; }
                msg("Foydalanish: .friend add|remove|list <ism>");
            } else if (a[0].equalsIgnoreCase(".cfg")) {
                if (a.length >= 3 && a[1].equalsIgnoreCase("save")) { ConfigManager.save(a[2], CONFIG); msg("CFG saqlandi: " + a[2] + ".cfg"); return; }
                if (a.length >= 3 && a[1].equalsIgnoreCase("load")) {
                    ClientConfig loaded = ConfigManager.load(a[2]);
                    copy(loaded, CONFIG);
                    msg("CFG yuklandi: " + a[2] + ".cfg"); return;
                }
                msg("Foydalanish: .cfg save <nom> yoki .cfg load <nom>");
            }
        } catch (Exception e) { msg("Xato: " + e.getMessage()); }
    }

    private static void copy(ClientConfig from, ClientConfig to) {
        to.killaura = from.killaura; to.reach = from.reach; to.criticals = from.criticals; to.velocity = from.velocity;
        to.swingAnimations=from.swingAnimations; to.visuals=from.visuals; to.esp=from.esp; to.fly=from.fly; to.elytraFly=from.elytraFly;
        to.autoPearl=from.autoPearl; to.autoMine=from.autoMine; to.autoDuel=from.autoDuel; to.autoTpAccept=from.autoTpAccept;
        to.autoDuelAccept=from.autoDuelAccept; to.autoAuth=from.autoAuth; to.autoTotem=from.autoTotem; to.scaffold=from.scaffold;
        to.noFall=from.noFall; to.step=from.step; to.speed=from.speed; to.inventoryMove=from.inventoryMove; to.chestStealer=from.chestStealer;
        to.autoTool=from.autoTool; to.nametags=from.nametags; to.tracers=from.tracers; to.targetHud=from.targetHud;
        to.noslow = from.noslow; to.autoclicker = from.autoclicker; to.fullbright = from.fullbright; to.zoom = from.zoom;
        to.freelook = from.freelook; to.togglesprint = from.togglesprint; to.waypoints = from.waypoints; to.hud = from.hud;
        to.killauraRange = from.killauraRange; to.killauraDelayTicks = from.killauraDelayTicks; to.theme = from.theme;
    }
    private static void msg(String s) { MinecraftClient c = MinecraftClient.getInstance(); if (c.player != null) c.player.sendMessage(Text.literal("[O'zbek Client] " + s), false); }
}
