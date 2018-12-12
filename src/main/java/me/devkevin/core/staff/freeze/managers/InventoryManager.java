package me.devkevin.core.staff.freeze.managers;

import me.devkevin.core.CorePlugin;
import me.devkevin.core.staff.freeze.Manager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager extends Manager
{
    private Inventory frozenInv;
    
    public InventoryManager(final CorePlugin plugin) {
        super(plugin);
        this.initiateFrozenInv();
    }
    
    private void initiateFrozenInv() {
        this.frozenInv = this.Plugin.getServer().createInventory((InventoryHolder)null, 9, "ScreenShare");
        final ItemStack paper = new ItemStack(Material.PAPER);
        final ItemMeta itemMeta = paper.getItemMeta();
        final List<String> lores = new ArrayList<String>();
        lores.add(0, ChatColor.YELLOW + "Don't logout you have 3 minutes.");
        lores.add(1, ChatColor.GOLD + "Press ALT+Tab for decrase your minecraft.");
        itemMeta.setLore((List)lores);
        itemMeta.setDisplayName(ChatColor.YELLOW + "TS: ts.udrop.club");
        paper.setItemMeta(itemMeta);
        this.frozenInv.setItem(4, paper);
    }
    
    public Inventory getFrozenInv() {
        return this.frozenInv;
    }
}
