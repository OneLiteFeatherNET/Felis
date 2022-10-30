package net.theevilreaper.felis.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.theevilreaper.felis.util.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The kill command allows a player to kill itself or kill a specific target on the server.<br>
 * Usage of the command is
 * <ul>
 *     <li>/kill</li>
 *     <li>/kill [target]</li>
 * </ul>
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public class KillCommand extends Command {

    private static final String KILL_PERMISSION = "felis.command.kill";
    private static final String KILL_OTHER_PERMISSION = "felis.command.kill.other";

    /**
     * Creates a new instance from the command.
     */
    public KillCommand() {
        super("kill");
        this.setCondition(Conditions::playerOnly);
        var playerArgument = ArgumentType.Entity("player").onlyPlayers(true);

        addSyntax((sender, context) -> this.executeSelf((Player)sender));

        addSyntax((sender, context) -> {
            var finder  = context.get(playerArgument);
            finder.setTargetSelector(EntityFinder.TargetSelector.ALL_PLAYERS);
            this.execute((Player) sender, finder.find(sender));
        }, playerArgument);
    }

    /**
     * Executes the kill logic for a given player.
     * @param player the player who is involved in the execution
     */
    private void executeSelf(@NotNull Player player) {
        if (!player.hasPermission(KILL_PERMISSION)) {
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }
        player.kill();
    }

    /**
     * Executes the command for a given list of targets.
     * @param player the player who executes the command
     * @param entities the entities which are defined in an argument
     */
    private void execute(@NotNull Player player, @NotNull List<Entity> entities) {
        if (!player.hasPermission(KILL_OTHER_PERMISSION)) {
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }

        if (entities.isEmpty()) return;

        for (int i = 0; i < entities.size(); i++) {
            var entity = entities.get(i);
            if (entity.getUuid().equals(player.getUuid())) {
                executeSelf(player);
                continue;
            }
            ((Player)entity).kill();
        }
    }
}
