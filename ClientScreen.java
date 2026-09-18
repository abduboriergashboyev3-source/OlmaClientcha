package uz.ozbek.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import uz.ozbek.client.OzbekClient;

public class ClientScreen extends Screen {
    private int left, top;
    public ClientScreen() { super(Text.literal("O'ZBEK CLIENT")); }
    @Override protected void init() {
        left = width / 2 - 300; top = height / 2 - 150;
        int colW = 180, gap = 14, row = 30;
        String[][] items = {
            {"Killaura", "killaura"}, {"Uzoq urish", "reach"}, {"Kritik zarba", "criticals"},
            {"Avto bosish", "autoclicker"}, {"Tezlikka qarshi", "velocity"}, {"Sekinlashmaslik", "noslow"},
            {"Yorqinlik", "fullbright"}, {"Kattalashtirish", "zoom"}, {"Erkin qarash", "freelook"},
            {"Avto yugurish", "togglesprint"}, {"Nuqtalar", "waypoints"}, {"HUD", "hud"},
            {"Tebranish animatsiyasi", "swingAnimations"}, {"Ko‘rinishlar", "visuals"}, {"O‘yinchi belgisi", "esp"},
            {"Parvoz", "fly"}, {"Elytra parvozi", "elytraFly"}, {"Avto marvarid", "autoPearl"},
            {"Avto qazish", "autoMine"}, {"Avto duel", "autoDuel"}, {"Avto TP qabul", "autoTpAccept"},
            {"Avto duel qabul", "autoDuelAccept"}, {"Avto autentifikatsiya", "autoAuth"}, {"Avto totem", "autoTotem"},
            {"Blok qo‘yish", "scaffold"}, {"Yiqilishdan himoya", "noFall"}, {"Qadam", "step"},
            {"Tezlik", "speed"}, {"Inventar harakati", "inventoryMove"}, {"Sandıq talash", "chestStealer"},
            {"Avto asbob", "autoTool"}, {"Ism belgisi", "nametags"}, {"Chiziqlar", "tracers"}, {"Nishon HUD", "targetHud"}
        };
        for (int i=0;i<items.length;i++) {
            int col=i/6, r=i%6, x=left+col*(colW+gap), y=top+45+r*row;
            String label=items[i][0], key=items[i][1];
            addDrawableChild(ButtonWidget.builder(label(key), b -> { toggle(key); b.setMessage(label(key)); })
                .dimensions(x,y,colW,24).build());
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("Yopish"), b -> close())
            .dimensions(width/2-90, top+235,180,26).build());
    }
    private void toggle(String key) {
        try {
            var f = OzbekClient.CONFIG.getClass().getField(key);
            if (f.getType() == boolean.class) f.setBoolean(OzbekClient.CONFIG, !f.getBoolean(OzbekClient.CONFIG));
        } catch (Exception ignored) {}
    }
    private Text label(String key) {
        boolean on=false;
        try { on=OzbekClient.CONFIG.getClass().getField(key).getBoolean(OzbekClient.CONFIG); } catch(Exception ignored) {}
        String name = switch(key) {
            case "killaura" -> "Killaura"; case "reach" -> "Uzoq urish"; case "criticals" -> "Kritik zarba";
            case "autoclicker" -> "Avto bosish"; case "velocity" -> "Tezlikka qarshi"; case "noslow" -> "Sekinlashmaslik";
            case "fullbright" -> "Yorqinlik"; case "zoom" -> "Kattalashtirish"; case "freelook" -> "Erkin qarash";
            case "togglesprint" -> "Avto yugurish"; case "waypoints" -> "Nuqtalar"; case "hud" -> "HUD";
            case "swingAnimations" -> "Tebranish animatsiyasi"; case "visuals" -> "Ko‘rinishlar"; case "esp" -> "O‘yinchi belgisi";
            case "fly" -> "Parvoz"; case "elytraFly" -> "Elytra parvozi"; case "autoPearl" -> "Avto marvarid";
            case "autoMine" -> "Avto qazish"; case "autoDuel" -> "Avto duel"; case "autoTpAccept" -> "Avto TP qabul";
            case "autoDuelAccept" -> "Avto duel qabul"; case "autoAuth" -> "Avto autentifikatsiya"; case "autoTotem" -> "Avto totem";
            case "scaffold" -> "Blok qo‘yish"; case "noFall" -> "Yiqilishdan himoya"; case "step" -> "Qadam";
            case "speed" -> "Tezlik"; case "inventoryMove" -> "Inventar harakati"; case "chestStealer" -> "Sandıq talash";
            case "autoTool" -> "Avto asbob"; case "nametags" -> "Ism belgisi"; case "tracers" -> "Chiziqlar";
            case "targetHud" -> "Nishon HUD"; default -> key;
        };
        return Text.literal(name + "  [" + (on ? "YOQ" : "O‘CHIQ") + "]");
    }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, "O'ZBEK CLIENT", width/2, top+12, 0x55AAFF);
        ctx.drawCenteredTextWithShadow(textRenderer, "Barcha modullar o'zbekcha", width/2, top+28, 0xFFFFFF);
        super.render(ctx, mouseX, mouseY, delta);
    }
}
