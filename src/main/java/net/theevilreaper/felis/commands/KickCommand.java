package net.theevilreaper.felis.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.theevilreaper.felis.util.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KickCommand extends Command {

    private static final Component DEFAULT_KICK_MESSAGE =
            Component.text("You have been kicked from the server", NamedTextColor.RED);

    private static final String KICK_BYPASS = "felis.commands.kick.bypass";

    //Syntax: /kick <player> <reason>
    public KickCommand() {
        super("kick", "k");
        setCondition(Conditions::playerOnly);

        var playerArgument = ArgumentType.Entity("player").onlyPlayers(true);
        var kickMessage = ArgumentType.String("kickMessage");
        addSyntax((sender, context) -> {
            var finder = context.get(playerArgument);
            finder.setTargetSelector(EntityFinder.TargetSelector.ALL_PLAYERS);
            onPlayerExecute((Player) sender, context.get("kickMessage"), finder.find(sender));
        }, kickMessage);
    }

    private void onPlayerExecute(@NotNull Player sender, @NotNull String message, @NotNull List<Entity> targets) {
        var kickComponent = message.trim().isEmpty() ? DEFAULT_KICK_MESSAGE : PlainTextComponentSerializer.plainText().deserialize(message).color(NamedTextColor.RED);
        if (targets.isEmpty()) {
            sender.sendMessage(Messages.NO_PLAYER);
            return;
        }

        for (Entity target : targets) {
            if (target.getUuid().equals(sender.getUuid()) || target.hasPermission(KICK_BYPASS)) continue;
            ((Player) target).kick(kickComponent);
        }
    }
}