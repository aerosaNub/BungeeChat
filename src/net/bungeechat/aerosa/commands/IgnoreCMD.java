package net.bungeechat.aerosa.commands;

import com.google.common.collect.ImmutableSet;
import net.bungeechat.aerosa.BungeeChat;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IgnoreCMD extends Command {


    public IgnoreCMD() {
        super("ignore", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {

            if (args.length == 0) {
                ((ProxiedPlayer) sender).sendMessage(ChatColor.RED + "/ignore <player>");
                ((ProxiedPlayer) sender).sendMessage(ChatColor.RED + "/ignore list");
                return;
            }

            ProxiedPlayer player  = (ProxiedPlayer) sender;

            if (args[0].equalsIgnoreCase("list")) {

                if (BungeeChat.getPlugin().getMessageUtils().hasIgnoreList(player.getUniqueId())) {
                    player.sendMessage(BungeeChat.getPlugin().getMessageUtils().c("&8[&6S&8] &e&lYour List of ignored players&7: &a"));
                    for (UUID list : BungeeChat.getPlugin().getMessageUtils().getIgnored(player.getUniqueId())) {
                        player.sendMessage(ChatColor.GREEN + " - " + BungeeCord.getInstance().getPlayer(list).getName());
                    }
                    return;
                } else {
                    player.sendMessage(BungeeChat.getPlugin().getMessageUtils().c("&8[&6S&8] &e&lYour List of ignored players&7: &cEmpty"));
                    return;
                }
            }

            ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);

            if (target != null) {
                BungeeChat.getPlugin().getMessageUtils().addToUSERSIgnore(player.getUniqueId(), target.getUniqueId());
                player.sendMessage(BungeeChat.getPlugin().getMessageUtils().c("&8[&6S&8] &e&lYou are now ignoring " + target.getName() + "!"));
            }
        }
    }

    public Iterable<String> onTabComplete(CommandSender cs, String[] args) {
        if ((args.length > 1) || (args.length == 0)) {
            return ImmutableSet.of();
        }

        Set<String> match = new HashSet<>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            for (ProxiedPlayer player : BungeeCord.getInstance().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(search)) {
                    match.add(player.getName());
                }
            }
        }
        return match;
    }
}
