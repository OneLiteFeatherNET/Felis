package net.theevilreaper.felis.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Messages {

    public static final Component PREFIX =
            LegacyComponentSerializer.legacyAmpersand().deserialize(
                    "§7[§eEditor§7] "
            );

    public static final Component PLAYER_NOT_FOUND =
            PREFIX.append(Component.text(
                    "The specific player is not online!",
                    NamedTextColor.RED
            ));

    public static final Component NO_PLAYER =
            PREFIX.append(Component.text("The command works only for players", NamedTextColor.RED));

    public static final Component NO_INSTANCE_OWNER =
            PREFIX.append(Component.text(
                    "You can not execute this command because you are not the owner of this instance!",
                    NamedTextColor.RED
            ));

    private Messages() {}
}
