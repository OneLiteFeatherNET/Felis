package net.theevilreaper.felis.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.theevilreaper.felis.util.Messages.*;

/**
 * The class allows a player to teleport to other players.
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 **/
public class TeleportCommand extends Command {

    public TeleportCommand() {
        super("teleport", "tp");
        this.setCondition(Conditions::playerOnly);

        var playerArgument = ArgumentType.Word("player");

        this.addSyntax(this::onExecuteSelf, playerArgument);
    }

    /**
     * Handles the execution of the command for a player argument.
     * @param sender the involved sender
     * @param context the context from the command
     */
    private void onExecuteSelf(@NotNull CommandSender sender, @NotNull CommandContext context) {
        var player = (Player) sender;
        String playerAsString = context.get("player");
        var targetPlayer = MinecraftServer.getConnectionManager().getPlayer(playerAsString);

        if (targetPlayer == null) {
            player.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        //Abort teleport to yourself
        if (player.getUuid().equals(targetPlayer.getUuid())) {
            player.sendMessage(ABORT_TELEPORT_YOURSELF);
            return;
        }

        if (!player.getInstance().getUniqueId().equals(targetPlayer.getInstance().getUniqueId())) {
            player.setInstance(targetPlayer.getInstance(), targetPlayer.getPosition());
        } else {
            player.teleport(targetPlayer.getPosition());
        }
    }
}
