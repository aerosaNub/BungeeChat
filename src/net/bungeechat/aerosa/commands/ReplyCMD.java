package net.bungeechat.aerosa.commands;

import com.google.common.collect.ImmutableSet;
import net.bungeechat.aerosa.BungeeChat;
import net.bungeechat.aerosa.utils.MessageUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ReplyCMD extends Command {


    public ReplyCMD() {
        super("reply", null, "r");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "/" + this.getName() + " (message)");
            return;
        }




        StringBuilder b = new StringBuilder();
        for (int i = 0; i<args.length; i++) {
            b.append(args[i]).append(" ");
        }

        String message = b.toString().trim();

        ProxiedPlayer target = BungeeCord.getInstance().getPlayer(BungeeChat.getPlugin().getMessageUtils().getRecipient(player));

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
}