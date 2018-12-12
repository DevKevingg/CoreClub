package me.devkevin.core.tab.abstraction;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

import me.devkevin.core.tab.TabIcon;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class EntityPlayerVersion extends EntityPlayerWrapper {
	
    private EntityPlayer entityPlayer;
    private MinecraftServer minecraftServer;
    private WorldServer worldServer;
    private PlayerInteractManager playerInteractManager;
    
    @Override
    public String getName() {
        return this.entityPlayer.getName();
    }
    
    public EntityPlayerVersion(String rowString, TabIcon tabIcon) {
        this.minecraftServer = ((CraftServer)Bukkit.getServer()).getServer();
        this.worldServer = this.minecraftServer.getWorldServer(0);
        this.playerInteractManager = new PlayerInteractManager((World)this.worldServer);
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), rowString);
        if (tabIcon != null) {
            gameProfile.getProperties().put("textures", tabIcon.getProperty());
        }
        EntityPlayer entityPlayer = new EntityPlayer(this.minecraftServer, this.worldServer, gameProfile, this.playerInteractManager);
        this.entityPlayer = entityPlayer;
    }
    
    public EntityPlayer getEntityPlayer() {
        return this.entityPlayer;
    }
}
