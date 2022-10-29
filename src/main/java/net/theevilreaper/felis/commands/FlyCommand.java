package net.theevilreaper.felis.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.theevilreaper.felis.util.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The FlyCommand allows player to change if they can fly or not.
 * @author theEvilReaper
 * @since 1.0.0
 * @version 1.0.0
 */
public class FlyCommand extends Command {

    private static final String FLY_OTHER_PERMISSION = "felis.command.fly.other";

    public FlyCommand() {
        super("fly");
        this.setCondition(Conditions::playerOnly);
        this.addSyntax(this::onExecuteSelf);
        var playerArgument = ArgumentType.Entity("player").onlyPlayers(true);

        this.addSyntax((sender, context) -> {
            var finder = context.get(playerArgument);
            finder.setTargetSelector(EntityFinder.TargetSelector.ALL_PLAYERS);
            executeOther((Player)sender, finder.find(sender));
        }, playerArgument);
    }

    private void executeOther(@NotNull Player sender, @NotNull List<Entity> players) {
        if (players.isEmpty()) return;

        if (!sender.hasPermission(FLY_OTHER_PERMISSION)) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return;
        }

        if (players.size() == 1) {
            var target = ((Player)players.get(0));
            updateFlyStatus(target);
            return;
        }

        for (int i = 0; i < players.size(); i++) {
            var target = players.get(i);
            if (target.getUuid().equals(sender.getUuid())) continue;
            updateFlyStatus((Player) target);
        }
    }

    /**
     * Executes the logic for a single player.
     * @param sender the sender who is involved into the command
     * @param context the given context from the command
     */
    private void onExecuteSelf(@NotNull CommandSender sender, @NotNull CommandContext context) {
        var player = (Player) sender;
        //Abort the execution of the command when the involved player is in creative mode
        if (player.getGameMode() == GameMode.CREATIVE) {
            player.sendMessage(Messages.ABORT_FLY_COMMAND);
            return;
        }
        updateFlyStatus(player);
    }

    /**
     * Updates the fly status from a given {@link Player}.
     * @param player the player to change the status
     */
    private void updateFlyStatus(@NotNull Player player) {
        if (player.isAllowFlying()) {
            player.setAllowFlying(false);
            player.setFlying(false);
            player.sendMessage(Messages.FLY_DISABLED);
        } else {
            player.setAllowFlying(true);
            player.setFlying(true);
            player.sendMessage(Messages.FLY_ENABLED);
        }
    }
}
