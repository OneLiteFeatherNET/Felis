package net.theevilreaper.felis.commands;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import static net.theevilreaper.felis.util.Messages.*;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 **/
public class GameModeCommand extends Command {

    private static final String GAME_MODE = "gamemode";

    private static final Component USAGE = PREFIX
            .append(Component.text("Usage: /gamemode <gm> [targets]", NamedTextColor.RED));

    public GameModeCommand() {
        super(GAME_MODE, "gm");
        setCondition(Conditions::playerOnly);
        ArgumentEnum<GameMode> gamemode = ArgumentType.Enum(GAME_MODE, GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);
        ArgumentEntity playerArgument = ArgumentType.Entity("targets").onlyPlayers(true);

        setDefaultExecutor((sender, context) -> sender.sendMessage(USAGE));

        //Command Syntax for /gamemode <gamemode>
        addSyntax(this::executeSelf);

        //Command Syntax for /gamemode <gamemode> [targets]
        addSyntax((sender, context) -> {
            EntityFinder finder = context.get(playerArgument);
            GameMode mode = context.get(gamemode);

            //Set the gamemode for the targets
            executeOthers(sender, mode, finder.find(sender));
        }, gamemode, playerArgument);
    }

    /**
     * Sets the gamemode for the specified entities, and
     * notifies them (and the sender) in the chat.
     */
    private void executeOthers(@NotNull CommandSender sender, @NotNull GameMode mode, @NotNull List<Entity> entities) {
        if (entities.isEmpty()) {
            sender.sendMessage(PLAYER_NOT_FOUND);
        } else for (Entity entity : entities) {
            if (entity instanceof Player player) {
                if (player == sender) {
                    executeSelf((Player) sender, mode);
                } else {
                    player.setGameMode(mode);

                    String gamemodeString = "gameMode." + mode.name().toLowerCase(Locale.ROOT);
                    Component gamemodeComponent = Component.translatable(gamemodeString);
                    Component playerName = player.getDisplayName() == null ? player.getName() : player.getDisplayName();

                    //Send a message to the changed player and the sender
                    player.sendMessage(PREFIX.append(Component.text("Changed gamemode to ", NamedTextColor.GRAY).append(gamemodeComponent)));
                    sender.sendMessage(Component.translatable("commands.gamemode.success.other", playerName, gamemodeComponent), MessageType.SYSTEM);
                }
            }
        }
    }

    private void executeSelf(@NotNull CommandSender sender, @NotNull CommandContext context) {
        var player = (Player) sender;

        if (!player.hasPermission("")) {
            return;
        }

        var gameMode = GameMode.valueOf(context.get(GAME_MODE));
        player.setGameMode(gameMode);
        player.sendMessage(buildWithPrefix(
                Component.text("Changed gamemode to", NamedTextColor.GRAY)
                        .append(Component.text(gameMode.name(), NamedTextColor.GREEN))));
    }
}
