package net.theevilreaper.felis;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extensions.Extension;
import net.theevilreaper.felis.commands.*;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 **/
public class Felis extends Extension {

    private DebugCommand debugCommand;

    @Override
    public void initialize() {
        var commandManager = MinecraftServer.getCommandManager();
        debugCommand = new DebugCommand();
        commandManager.register(this.debugCommand);
        commandManager.register(new FlyCommand());
        commandManager.register(new GameModeCommand());
        commandManager.register(new SpeedCommand());
        commandManager.register(new KickCommand());
        commandManager.register(new KillCommand());
        commandManager.register(new NightVisionCommand());
        commandManager.register(new SpeedCommand());
        commandManager.register(new StopCommand());
        commandManager.register(new TeleportCommand());

        var instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        MinecraftServer.getInstanceManager().registerInstance(instance);

        MinecraftServer.getGlobalEventHandler().addListener(
                PlayerLoginEvent.class, playerLoginEvent -> playerLoginEvent.setSpawningInstance(instance)
        );

        var pos = new Pos(0, 100, 0);

        instance.loadChunk(pos);

        MinecraftServer.getGlobalEventHandler().addListener(
                PlayerSpawnEvent.class, playerSpawnEvent ->
                        playerSpawnEvent.getPlayer().teleport(pos));
    }

    @Override
    public void terminate() {
        debugCommand.unregisterNode();
    }
}
