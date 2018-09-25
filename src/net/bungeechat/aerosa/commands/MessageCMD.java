package net.bungeechat.aerosa.commands;

import com.google.common.collect.ImmutableSet;
import jdk.nashorn.internal.ir.annotations.Immutable;
import net.bungeechat.aerosa.BungeeChat;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MessageCMD extends Command {


    public MessageCMD() {
        super("message", null, "msg", "reply", "r", "m");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
            if (!(sender instanceof ProxiedPlayer)) return;

            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "/" + this.getName() + " (user) (message)");
                return;
            }




            StringBuilder b = new StringBuilder();
            for (int i =1; i<args.length; i++) {
                b.append(args[i]).append(" ");
            }

            String message = b.toString().trim();

            ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);

            if (target != null) {

                if (BungeeChat.getPlugin().getMessageUtils().hasMessageToggle(target.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "This user currently has messages toggled off!");
                    return;
                }

                if (BungeeChat.getPlugin().getMessageUtils().hasIgnoreList(target.getUniqueId())) {
                    if (BungeeChat.getPlugin().getMessageUtils().getIgnored(target.getUniqueId()).contains(player.getUniqueId())) {
                        player.sendMessage(ChatColor.RED + "Fail to send message, reasoning - Ignored!");
                        return;
                    }
                }


                    BungeeChat.getPlugin().getMessageUtils().newRecipient(player, target);
                    BungeeChat.getPlugin().getMessageUtils().newRecipient(target, player);
                    BungeeChat.getPlugin().getMessageUtils().sendMessage(player, message, target);


            }

    }

    public Iterable<String> onTabComplete(CommandSender cs, String[] args) {
        if ((args.length > 2) || (args.length == 0)) {
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
