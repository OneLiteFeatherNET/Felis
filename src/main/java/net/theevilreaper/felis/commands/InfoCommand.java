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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class InfoCommand extends Command {

    private static final String MINESTOM_GITHUB = "https://github.com/Minestom/Minestom";

    public InfoCommand() {
        super("info", "extension", "plugins", "pl");
        this.addSyntax(this::onExecute);
    }

    private void onExecute(@NotNull CommandSender sender, @NotNull CommandContext context) {
        var extensions = MinecraftServer.getExtensionManager().getExtensions();
        var message = Component.text().append(buildMinestomComponent())
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.text("Loaded ", NamedTextColor.GRAY))
                .append(Component.text(extensions.size(), NamedTextColor.GOLD))
                .append(Component.text(" Extension from the folder", NamedTextColor.GRAY))
                .append(Component.newline());

        if (!extensions.isEmpty()) {
            int counter = 0;

            for (Extension extension : extensions) {
                var originExtension = extension.getOrigin();
                var authorComponents = Component.empty();

                if (originExtension.getAuthors().length != 0) {
                    authorComponents =  Component.newline().append(Component.text("Authors: ", NamedTextColor.GRAY)
                            .append(Component.text(Arrays.toString(originExtension.getAuthors()), NamedTextColor.GREEN)));
                }

                message.append(Component.text(originExtension.getName(), NamedTextColor.GREEN)
                        .hoverEvent(HoverEvent.showText(
                                Component.text("Name: ", NamedTextColor.GRAY)
                                        .append(Component.text(originExtension.getName(), NamedTextColor.GREEN))
                                        .append(Component.text("\nVersion: ", NamedTextColor.GRAY))
                                        .append(Component.text(originExtension.getVersion(), NamedTextColor.GREEN))
                                        .append(authorComponents)
                        )));
                counter++;
                if (counter <= extensions.size() -1) {
                    message.append(Component.text(", "));
                }
            }
        }
        sender.sendMessage(message);
    }

    @Contract(pure = true)
    private @NotNull Component buildMinestomComponent() {
        return Component.text("This server is running ", NamedTextColor.GRAY)
                .append(Component.text("Minestom " + MinecraftServer.VERSION_NAME, NamedTextColor.GOLD, TextDecoration.UNDERLINED)
                        .hoverEvent(HoverEvent.showText(Component.text(MINESTOM_GITHUB, NamedTextColor.GRAY)))
                        .clickEvent(ClickEvent.openUrl(MINESTOM_GITHUB)));
    }
}
