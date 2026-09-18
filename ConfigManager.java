package uz.ozbek.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path DIR = FabricLoader.getInstance().getConfigDir().resolve("ozbekclient").resolve("cfg");
    private ConfigManager() {}

    public static void save(String name, ClientConfig cfg) throws IOException {
        Files.createDirectories(DIR);
        String safe = safeName(name);
        cfg.friends.clear(); cfg.friends.addAll(FriendManager.all());
        Files.writeString(DIR.resolve(safe + ".cfg"), GSON.toJson(cfg), StandardCharsets.UTF_8);
    }

    public static ClientConfig load(String name) throws IOException {
        String safe = safeName(name);
        Path file = DIR.resolve(safe + ".cfg");
        ClientConfig cfg = GSON.fromJson(Files.readString(file, StandardCharsets.UTF_8), ClientConfig.class);
        if (cfg == null) throw new IOException("Konfiguratsiya bo'sh");
        FriendManager.replace(cfg.friends == null ? java.util.Set.of() : cfg.friends);
        return cfg;
    }

    private static String safeName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nom kerak");
        String s = name.trim();
        if (s.endsWith(".cfg")) s = s.substring(0, s.length() - 4);
        return s.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}
