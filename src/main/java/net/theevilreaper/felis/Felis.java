package net.theevilreaper.felis;

import net.minestom.server.MinecraftServer;
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
        commandManager.register(new InfoCommand());
        commandManager.register(new SpeedCommand());
        commandManager.register(new KickCommand());
        commandManager.register(new KillCommand());
        commandManager.register(new NightVisionCommand());
        commandManager.register(new SpeedCommand());
        commandManager.register(new StopCommand());
        commandManager.register(new TeleportCommand());
    }

    @Override
    public void terminate() {
        debugCommand.unregisterNode();
    }
}
