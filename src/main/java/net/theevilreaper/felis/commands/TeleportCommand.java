package net.theevilreaper.felis.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

        var playerArgument = ArgumentType.Entity("player").onlyPlayers(true);

        addSyntax((sender, context) -> {
            var finder = context.get(playerArgument);
            finder.setTargetSelector(EntityFinder.TargetSelector.MINESTOM_USERNAME);
            this.onExecuteSelf(sender, finder.find(sender));
        }, playerArgument);
    }

    /**
     * Handles the execution of the command for a player argument.
     * @param sender the involved sender
     */
    private void onExecuteSelf(@NotNull CommandSender sender, @Nullable List<Entity> targets) {
        var player = (Player) sender;

        if (targets == null || targets.isEmpty()) {
            player.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        var target = targets.get(0);

        //Abort teleport to yourself
        if (player.getUuid().equals(target.getUuid())) {
            player.sendMessage(ABORT_TELEPORT_YOURSELF);
            return;
        }

        if (!player.getInstance().getUniqueId().equals(target.getInstance().getUniqueId())) {
            player.setInstance(target.getInstance(), target.getPosition());
        } else {
            player.teleport(target.getPosition());
        }
    }
}
