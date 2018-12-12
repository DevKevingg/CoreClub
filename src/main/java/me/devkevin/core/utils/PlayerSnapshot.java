package me.devkevin.core.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class PlayerSnapshot
{
    private ItemStack[] mainContent;
    private ItemStack[] armorContent;
    private Collection<PotionEffect> potionEffects;
    private float walkSpeed;

    public PlayerSnapshot(final Player player) {
        this.mainContent = player.getInventory().getContents();
        this.armorContent = player.getInventory().getArmorContents();
        this.potionEffects = (Collection<PotionEffect>)player.getActivePotionEffects();
        this.walkSpeed = player.getWalkSpeed();
    }

    public ItemStack[] getMainContent() {
        return this.mainContent;
    }

    public ItemStack[] getArmorContent() {
        return this.armorContent;
    }

    public Collection<PotionEffect> getPotionEffects() {
        return this.potionEffects;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }
}

