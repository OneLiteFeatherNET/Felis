package net.theevilreaper.felis.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Messages {

    public static final TextColor PREFIX_COLOR = TextColor.fromHexString("#B0FC38");

    public static final Component PREFIX =
            Component.text("Felis").color(PREFIX_COLOR).append(Component.text(" » ", NamedTextColor.GRAY));
    public static final Component PLAYER_NOT_FOUND =
            PREFIX.append(Component.text("The specific player is not online!", NamedTextColor.RED));
    public static final Component NO_PLAYER =
            PREFIX.append(Component.text("The command works only for players", NamedTextColor.RED));
    public static final Component ABORT_TELEPORT_YOURSELF =
            PREFIX.append(Component.text("Can not teleport to yourself", NamedTextColor.RED));

    public static final Component ABORT_FLY_COMMAND =
            PREFIX.append(Component.text("Command can't be executed in CREATIVE mode", NamedTextColor.RED));

    public static final Component NIGHT_VISION_ENABLED =
            PREFIX.append(Component.text("Enabled night vision", NamedTextColor.AQUA));
    public static final Component NIGHT_VISION_DISABLED =
            PREFIX.append(Component.text("Disabled night vision", NamedTextColor.RED));

    public static final Component FLY_ENABLED =
            buildWithPrefix(Component.text("You can fly now"));

    public static final Component FLY_DISABLED =
            buildWithPrefix(Component.text("You can't fly anymore"));

    public static final Component FLY_SPEED_CHANGE =
            buildWithPrefix(Component.text("Changed speed value to", NamedTextColor.GRAY));

    public static final Component VALUE_IS_TO_HIGH =
            buildWithPrefix(Component.text("The given speed is to high. The maximum is 10", NamedTextColor.RED));
    public static final Component RESET_TO_DEFAULT =
            buildWithPrefix(Component.text("Speed has been reset to default", NamedTextColor.GREEN));

    public static final Component NO_PERMISSION =
            buildWithPrefix(Component.text("You can not execute this command!", NamedTextColor.RED));

    public static final Component SHOW_DEBUGS =
            buildWithPrefix(Component.text("Press tab to see some debug information", NamedTextColor.RED));

    public static final Component NO_DEBUGS =
            buildWithPrefix(Component.text("Removed", NamedTextColor.RED));

    private Messages() {}

    @Contract(pure = true)
    public static @NotNull Component buildWithPrefix(@NotNull Component component) {
        return PREFIX.append(component);
    }
}
