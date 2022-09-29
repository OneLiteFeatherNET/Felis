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

    @Override
    public void initialize() {
        var commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new GameModeCommand());
        commandManager.register(new SpeedCommand());
        commandManager.register(new NightVisionCommand());
        commandManager.register(new FlyCommand());
        commandManager.register(new InfoCommand());
        commandManager.register(new StopCommand());
    }

    @Override
    public void terminate() {
        // Nothing to do here
    }
}
