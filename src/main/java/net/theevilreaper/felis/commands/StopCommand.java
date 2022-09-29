package net.theevilreaper.felis.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import net.theevilreaper.felis.util.Messages;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class StopCommand extends Command {

    private static final Component SHUTDOWN = Component.text("Server shutdowns", NamedTextColor.RED);

    public StopCommand() {
        super("stop");
        this.addSyntax(this::onExecute);
    }

    private void onExecute(@NotNull CommandSender sender, @NotNull CommandContext context) {
        if (sender instanceof Player player && !player.hasPermission("test")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return;
        }
        var onlinePlayers = MinecraftServer.getConnectionManager().getOnlinePlayers();
        if (onlinePlayers.isEmpty()) return;
        onlinePlayers.forEach(player -> {
            player.kick(SHUTDOWN);
            MinecraftServer.getConnectionManager().removePlayer(player.getPlayerConnection());
        });
        MinecraftServer.getSchedulerManager().buildTask(MinecraftServer::stopCleanly).delay(Duration.of(1_000, ChronoUnit.MILLIS)).schedule();
    }

}
