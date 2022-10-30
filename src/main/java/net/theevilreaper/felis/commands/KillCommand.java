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

public class KillCommand extends Command {

    private static final String KILL_PERMISSION = "felis.command.kill";
    private static final String KILL_OTHER_PERMISSION = "felis.command.kill.other";

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

    private void executeSelf(@NotNull Player player) {
        if (player.hasPermission(KILL_PERMISSION)) {
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }
        player.kill();
    }

    private void execute(@NotNull Player player, @NotNull List<Entity> entities) {
        if (!player.hasPermission(KILL_OTHER_PERMISSION)) {
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }

        if (entities.isEmpty()) {
            return;
        }

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
