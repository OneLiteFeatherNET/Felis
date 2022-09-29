package net.theevilreaper.felis.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class InfoCommand extends Command {

    private static final String MINESTOM_GITHUB = "https://github.com/Minestom/Minestom";

    public InfoCommand() {
        super("info", "extension", "plugins", "pl");
    }

    private void onExecute(@NotNull CommandSender sender, @NotNull CommandContext context) {
        var extensionManager = MinecraftServer.getExtensionManager();
        var message = Component.text()
                .append(Component.text("This server is running ", NamedTextColor.GRAY))
                        .append(Component.text("Minestom" + MinecraftServer.getBrandName(), NamedTextColor.GOLD, TextDecoration.UNDERLINED)
                                .hoverEvent(HoverEvent.showText(Component.text(MINESTOM_GITHUB, NamedTextColor.GRAY)))
                                .clickEvent(ClickEvent.openUrl(MINESTOM_GITHUB))
                .append(Component.text("\n\n  → " +  extensionManager.getExtensions().size() + "extensions:\n", NamedTextColor.LIGHT_PURPLE)));

        if (!extensionManager.getExtensions().isEmpty()) {
            int counter = 0;

            for (Extension extension : extensionManager.getExtensions()) {
                var originExtension = extension.getOrigin();
                message.append(Component.text(originExtension.getName(), NamedTextColor.GREEN)
                        .hoverEvent(HoverEvent.showText(
                                Component.text()
                                        .append(Component.text("Name: ", NamedTextColor.GRAY))
                                        .append(Component.text(originExtension.getName(), NamedTextColor.GREEN))
                                        .append(Component.text("\nVersion: ", NamedTextColor.GRAY))
                                        .append(Component.text(originExtension.getVersion(), NamedTextColor.GREEN))
                        )));
                if (originExtension.getAuthors().length != 0) {
                    message.append(Component.text("\nAuthors", NamedTextColor.GRAY));
                    message.append(Component.text(Arrays.toString(originExtension.getAuthors()), NamedTextColor.GRAY));
                }
            }
        }

        sender.sendMessage(message);
    }
}
