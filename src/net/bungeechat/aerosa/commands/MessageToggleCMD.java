package net.bungeechat.aerosa.commands;

import com.google.common.collect.ImmutableSet;
import net.bungeechat.aerosa.BungeeChat;
import net.bungeechat.aerosa.utils.MessageUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashSet;
import java.util.Set;

public class MessageToggleCMD extends Command {


    public MessageToggleCMD() {
        super("mtoggle", null, "togglepm", "pmtoggle", "togglemessage");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player  = (ProxiedPlayer) sender;
            if (BungeeChat.getPlugin().getMessageUtils().hasMessageToggle(player.getUniqueId())) {
                BungeeChat.getPlugin().getMessageUtils().removeToggle(player.getUniqueId());
                player.sendMessage(BungeeChat.getPlugin().getMessageUtils().c("&8[&6S&8] &e&lYou have enabled your messages!"));
            } else {
                BungeeChat.getPlugin().getMessageUtils().setMessageToggle(player.getUniqueId());
                player.sendMessage(BungeeChat.getPlugin().getMessageUtils().c("&8[&6S&8] &e&lYou have disabled your messages!"));
            }
        }
    }
}