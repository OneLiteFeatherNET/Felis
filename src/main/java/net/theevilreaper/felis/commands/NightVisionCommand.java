package net.theevilreaper.felis.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Player;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import net.theevilreaper.felis.util.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 **/
@SuppressWarnings("java:S3252")
public class NightVisionCommand extends Command {

    private final Potion potion;

    public NightVisionCommand() {
        super("nv");
        this.setCondition(Conditions::playerOnly);
        this.potion = new Potion(PotionEffect.NIGHT_VISION, (byte) 1, Integer.MAX_VALUE);
        addSyntax(this::onExecuteSelf);
    }

    private void onExecuteSelf(@NotNull CommandSender sender, @NotNull CommandContext context) {
        var player = (Player) sender;

        if (!player.getActiveEffects().isEmpty()) {
            player.removeEffect(potion.effect());
            player.sendMessage(Messages.NIGHTVISION_DISABLED);
        } else {
            player.addEffect(potion);
            player.sendMessage(Messages.NIGHTVISION_ENABLED);
        }
    }
}
