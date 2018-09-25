package net.bungeechat.aerosa.listeners;

import net.bungeechat.aerosa.BungeeChat;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnect implements Listener
{

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        ProxiedPlayer player = e.getPlayer();

        BungeeChat.getPlugin().getMessageUtils().removeRecipient(player);
    }
}
