package net.bungeechat.aerosa;

import net.bungeechat.aerosa.commands.*;
import net.bungeechat.aerosa.listeners.PlayerDisconnect;
import net.bungeechat.aerosa.utils.MessageUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeChat extends Plugin
{

    private static BungeeChat  plugin;
    private MessageUtils messageUtils;

    @Override
    public void onEnable() {
        plugin = this;
        getLogger().info("BungeeChat has been loaded successfully!");
        this.messageUtils = new MessageUtils();

        getProxy().getPluginManager().registerCommand(this, new IgnoreCMD());
        getProxy().getPluginManager().registerCommand(this, new MessageCMD());
        getProxy().getPluginManager().registerCommand(this, new ReplyCMD());
        getProxy().getPluginManager().registerCommand(this, new UnIgnoreCMD());
        getProxy().getPluginManager().registerCommand(this, new MessageToggleCMD());

        getProxy().getPluginManager().registerListener(this, new PlayerDisconnect());

    }


    public static BungeeChat getPlugin() {
        return plugin;
    }

    public MessageUtils getMessageUtils() {
        return messageUtils;
    }
}

