package net.theevilreaper.felis.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand extends Command {

    public FlyCommand() {
        super("fly");
        this.setCondition(Conditions::playerOnly);

        this.addSyntax(this::onExecuteSelf);
    }

    private void onExecuteSelf(@NotNull CommandSender sender, @NotNull CommandContext context) {
        var player = (Player) sender;

        //Abort the execution of the command when the involved player is in creative mode
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (player.isAllowFlying()) {
            player.setAllowFlying(false);
            player.setFlying(false);
        } else {
            player.setAllowFlying(true);
            player.setFlying(true);
        }
    }
}
