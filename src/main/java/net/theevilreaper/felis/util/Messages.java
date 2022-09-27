package net.theevilreaper.felis.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Messages {

    public static final Component PREFIX =
            Component.text("Felis >", NamedTextColor.GRAY);
    public static final Component PLAYER_NOT_FOUND =
            PREFIX.append(Component.text("The specific player is not online!", NamedTextColor.RED));
    public static final Component NO_PLAYER =
            PREFIX.append(Component.text("The command works only for players", NamedTextColor.RED));
    public static final Component ABORT_TELEPORT_YOURSELF =
            PREFIX.append(Component.text("Can not teleport to yourself", NamedTextColor.RED));

    private Messages() {}
}
