package net.theevilreaper.felis.commands;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerDeathEvent;
import net.minestom.server.event.server.ServerTickMonitorEvent;
import net.minestom.server.monitoring.BenchmarkManager;
import net.minestom.server.monitoring.TickMonitor;
import net.minestom.server.utils.MathUtils;
import net.minestom.server.utils.time.TimeUnit;
import net.theevilreaper.felis.util.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class DebugCommand extends Command {

    private static final AtomicReference<TickMonitor> LAST_TICK = new AtomicReference<>();
    private final EventNode<Event> debugNode;
    private final Set<Player> viewers;

    public DebugCommand() {
        super("debug", "d");
        this.debugNode = EventNode.all("debugNode");
        this.viewers = new HashSet<>();
        this.addSyntax(this::executeCommand);
        this.initListener(debugNode);
        MinecraftServer.getGlobalEventHandler().addChild(debugNode);
    }

    private void executeCommand(@NotNull CommandSender sender, @NotNull CommandContext context) {
        var player = (Player) sender;

        if (!player.hasPermission("")) {
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }

        if (this.viewers.add(player)) {
            player.sendMessage(Messages.SHOW_DEBUGS);
        } else {
            this.viewers.remove(player);
            player.sendMessage(Messages.NO_DEBUGS);
            player.sendPlayerListHeaderAndFooter(Component.empty(), Component.empty());
        }
    }

    private void initListener(@NotNull EventNode<Event> eventNode) {
        eventNode.addListener(ServerTickMonitorEvent.class, event -> LAST_TICK.set(event.getTickMonitor()));
        eventNode.addListener(PlayerDeathEvent.class, event -> viewers.remove(event.getPlayer()));
        BenchmarkManager benchmarkManager = MinecraftServer.getBenchmarkManager();
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            if (viewers.isEmpty())
                return;

            long ramUsage = benchmarkManager.getUsedMemory();
            ramUsage /= 1e6; // bytes to MB

            TickMonitor tickMonitor = LAST_TICK.get();
            final Component header = Component.text("RAM USAGE: " + ramUsage + " MB")
                    .append(Component.newline())
                    .append(Component.text("TICK TIME: " + MathUtils.round(tickMonitor.getTickTime(), 2) + "ms"))
                    .append(Component.newline())
                    .append(Component.text("ACQ TIME: " + MathUtils.round(tickMonitor.getAcquisitionTime(), 2) + "ms"));
            final Component footer = benchmarkManager.getCpuMonitoringMessage();
            sendMessage(header, footer);
        }).repeat(10, TimeUnit.SERVER_TICK).schedule();
    }

    private void sendMessage(@NotNull Component header, @NotNull Component footer) {
        for (Player viewer : this.viewers) {
            viewer.sendPlayerListHeaderAndFooter(header, footer);
        }
    }
}
