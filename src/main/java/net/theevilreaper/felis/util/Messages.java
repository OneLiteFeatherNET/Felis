package net.theevilreaper.felis.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public final class Messages {

    public static final Component PREFIX =
            Component.text("Felis >", NamedTextColor.GRAY);
    public static final Component PLAYER_NOT_FOUND =
            PREFIX.append(Component.text("The specific player is not online!", NamedTextColor.RED));
    public static final Component NO_PLAYER =
            PREFIX.append(Component.text("The command works only for players", NamedTextColor.RED));
    public static final Component ABORT_TELEPORT_YOURSELF =
            PREFIX.append(Component.text("Can not teleport to yourself", NamedTextColor.RED));

    public static final Component ABORT_FLY_COMMAND =
            PREFIX.append(Component.text("Command can't be executed in CREATIVE mode", NamedTextColor.RED));

    public static final Component NIGHTVISION_ENABLED =
            PREFIX.append(Component.text("Enabled nightvision", NamedTextColor.AQUA));
    public static final Component NIGHTVISION_DISABLED =
            PREFIX.append(Component.text("Disabled nightvision", NamedTextColor.RED));

    public static final Component FLY_ENABLED =
            buildWithPrefix(Component.text("You can fly now"));

    public static final Component FLY_DISABLED =
            buildWithPrefix(Component.text("You can't fly anymore"));

    public static final Component NO_PERMISSION =
            buildWithPrefix(Component.text("You can not execute this command!", NamedTextColor.RED));
    private Messages() {}

    public static Component buildWithPrefix(@NotNull Component component) {
        return PREFIX.append(component);
    }
}
