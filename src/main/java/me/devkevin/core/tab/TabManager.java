package me.devkevin.core.tab;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TabManager implements Listener {
	
    private HashMap<Player, Tab> tabList;
    
    public TabManager() {
        this.tabList = new HashMap<Player, Tab>();
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (this.hasTab(e.getPlayer())) {
            this.tabList.remove(e.getPlayer());
        }
    }
    
    public void updateTabs() {
        for (Tab tab : this.tabList.values()) {
            tab.updateTab();
        }
    }
    
    public Tab getTab(Player player) {
        return this.tabList.get(player);
    }
    
    private boolean hasTab(Player player) {
        return this.tabList.containsKey(player);
    }
    
    public Tab newTab(Player player, String tabTitle, String tabFooter) {
        Tab tab = new Tab(player, tabTitle, tabFooter);
        this.tabList.put(player, tab);
        return tab;
    }
    
    public void onDisable() {
        for (Tab tab : this.tabList.values()) {
            tab.clearTab();
        }
    }
    
    public HashMap<Player, Tab> getTabs() {
        return this.tabList;
    }
}
