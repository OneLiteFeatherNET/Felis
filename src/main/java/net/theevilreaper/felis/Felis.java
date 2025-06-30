package net.theevilreaper.felis;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.extensions.Extension;
import net.theevilreaper.felis.commands.*;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 **/
public class Felis {

    public void initialize() {
        final CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new FlyCommand());
        commandManager.register(new GameModeCommand());
        commandManager.register(new SpeedCommand());
        commandManager.register(new KickCommand());
        commandManager.register(new KillCommand());
        commandManager.register(new NightVisionCommand());
        commandManager.register(new StopCommand());
        commandManager.register(new TeleportCommand());
    }

}
