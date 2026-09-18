package uz.ozbek.client.config;

import java.util.LinkedHashSet;
import java.util.Set;

public final class FriendManager {
    private static final Set<String> FRIENDS = new LinkedHashSet<>();
    private FriendManager() {}

    public static void add(String name) { if (name != null && !name.isBlank()) FRIENDS.add(name.toLowerCase()); }
    public static void remove(String name) { if (name != null) FRIENDS.remove(name.toLowerCase()); }
    public static boolean isFriend(String name) { return name != null && FRIENDS.contains(name.toLowerCase()); }
    public static Set<String> all() { return Set.copyOf(FRIENDS); }
    public static void clear() { FRIENDS.clear(); }
    public static void replace(Set<String> names) { FRIENDS.clear(); names.forEach(FriendManager::add); }
}
