package net.bungeechat.aerosa.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class MessageUtils {

    private HashMap<UUID, List<UUID>> ignored = new HashMap<>();
    private ArrayList<UUID> messageToggle = new ArrayList<>();
    private HashMap<UUID, UUID> recipients = new HashMap<>();

    public String c (String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void sendMessage(ProxiedPlayer p, String pMsg, ProxiedPlayer t) {
        TextComponent message = new TextComponent(c("&8&l(&7From &6&l" + p.getName() + "&8&l) &b&l"  + pMsg));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(c("&6This user is currently on &a&l[" + p.getServer().getInfo().getName() + "]")).create()));
        t.sendMessage(message);

        TextComponent message1 = new TextComponent(c("&8&l(&7To &6&l" + t.getName() + "&8&l) &b&l"  + pMsg));
        message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(c("&6This user is currently on &a&l[" + t.getServer().getInfo().getName() + "]")).create()));
        p.sendMessage(message1);
    }

    public UUID getRecipient(ProxiedPlayer player) {
        return this.recipients.get(player.getUniqueId());
    }

    public void newRecipient(ProxiedPlayer player, ProxiedPlayer target) {
        this.recipients.put(player.getUniqueId(), target.getUniqueId());
    }

    public void removeRecipient(ProxiedPlayer player) {
        this.recipients.remove(player.getUniqueId());
    }


    public List<UUID> getIgnored(UUID uuid) {
        return this.ignored.get(uuid);
    }



    public void addToUSERSIgnore(UUID uuid, UUID uuid1) {

        if (hasIgnoreList(uuid)) {

            List<UUID> ignoredList = this.getIgnored(uuid);

            ignoredList.add(uuid1);
            if (!this.ignored.get(uuid).contains(uuid1)) {
                this.ignored.put(uuid, ignoredList);
            }
        } else {
            List<UUID> newList = new ArrayList<>();

            newList.add(uuid1);

            ignored.put(uuid, newList);
        }
    }

    public void removeUSERSIgnore(UUID uuid, UUID uuid1) {
        List<UUID> ignoredList = this.getIgnored(uuid);

        ignoredList.remove(uuid1);
        if (this.ignored.get(uuid).contains(uuid1)) {
            this.ignored.put(uuid, ignoredList);
        }
    }

    public void removeAllUser(UUID uuid) {
        this.ignored.remove(uuid, this.ignored.get(uuid));
    }

    public boolean hasIgnoreList(UUID uuid) {
        return this.ignored.containsKey(uuid);
    }

    public void setMessageToggle(UUID uuid) {
        this.messageToggle.add(uuid);
    }

    public boolean hasMessageToggle(UUID u) {
        return this.messageToggle.contains(u);
    }

    public void removeToggle(UUID uuid) {
        this.messageToggle.remove(uuid);
    }

}
