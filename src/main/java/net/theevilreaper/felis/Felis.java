package net.theevilreaper.felis;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import net.theevilreaper.felis.commands.GameModeCommand;
import net.theevilreaper.felis.commands.NightVisionCommand;
import net.theevilreaper.felis.commands.SpeedCommand;

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
    }

    @Override
    public void terminate() {
        // Nothing to do here
    }
}
