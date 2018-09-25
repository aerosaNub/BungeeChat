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

public class UnIgnoreCMD extends Command {


    public UnIgnoreCMD() {
        super("unignore", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {

            if (args.length == 0) {
                ((ProxiedPlayer) sender).sendMessage(ChatColor.RED + "/unignore <player>");
                ((ProxiedPlayer) sender).sendMessage(ChatColor.RED + "/unignore all");
                return;
            }

            ProxiedPlayer player  = (ProxiedPlayer) sender;

            if (args[0].equalsIgnoreCase("all")) {
                if (BungeeChat.getPlugin().getMessageUtils().hasIgnoreList(player.getUniqueId())) {

                    BungeeChat.getPlugin().getMessageUtils().removeAllUser(player.getUniqueId());
                    player.sendMessage(BungeeChat.getPlugin().getMessageUtils().c("&8[&6S&8] &e&lYou cleared your whole ignore list!"));
                    return;
                } else {
                    player.sendMessage(BungeeChat.getPlugin().getMessageUtils().c("&8[&6S&8] &e&lYou have no-one ignored!"));
                }
            }

            ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);

            if (target != null) {
              if (BungeeChat.getPlugin().getMessageUtils().hasIgnoreList(player.getUniqueId())) {
                  if (BungeeChat.getPlugin().getMessageUtils().getIgnored(player.getUniqueId()).contains(target.getUniqueId())) {
                      BungeeChat.getPlugin().getMessageUtils().removeUSERSIgnore(player.getUniqueId(), target.getUniqueId());
                      player.sendMessage(BungeeChat.getPlugin().getMessageUtils().c("&8[&6S&8] &e&lYou have stopped ignoring " + target.getName() + "!"));
                  }
              }
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
