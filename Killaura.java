package uz.ozbek.client.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import uz.ozbek.client.OzbekClient;
import uz.ozbek.client.config.FriendManager;

public final class Killaura {
    private Killaura() {}

    public static void tick(MinecraftClient client) {
        if (!OzbekClient.CONFIG.killaura || client.player == null || client.world == null || client.interactionManager == null) return;
        if (client.player.isUsingItem() || client.player.isSpectator()) return;
        if (client.player.getAttackCooldownProgress(0.0f) < 1.0f) return;

        PlayerEntity best = null;
        double bestDistance = OzbekClient.CONFIG.killauraRange * OzbekClient.CONFIG.killauraRange;
        for (PlayerEntity p : client.world.getPlayers()) {
            if (p == client.player || p.isDead() || p.isSpectator()) continue;
            if (FriendManager.isFriend(p.getName().getString())) continue;
            double d = client.player.squaredDistanceTo(p);
            if (d > bestDistance) continue;
            if (!client.player.canSee(p)) continue;
            best = p;
            bestDistance = d;
        }
        if (best != null) {
            client.interactionManager.attackEntity(client.player, best);
            client.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
