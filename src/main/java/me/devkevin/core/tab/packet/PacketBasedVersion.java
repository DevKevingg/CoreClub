package me.devkevin.core.tab.packet;

import me.devkevin.core.tab.TabIcon;
import me.devkevin.core.tab.abstraction.EntityPlayerVersion;
import me.devkevin.core.tab.abstraction.EntityPlayerWrapper;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;

import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.entity.Player;

import java.util.Collection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class PacketBasedVersion implements PacketUtil {
	
    public EntityPlayer[] getOnlineCraftPlayers() {
        Collection<? extends Player> onlinePlayers = (Collection<? extends Player>)Bukkit.getOnlinePlayers();
        EntityPlayer[] NMSList = new EntityPlayer[onlinePlayers.size()];
        for (int i = 0; i < onlinePlayers.size(); ++i) {
            NMSList[i] = ((CraftPlayer)onlinePlayers.toArray()[i]).getHandle();
        }
        return NMSList;
    }
    
    @Override
    public void setFooterAndHeader(Player player, String header, String footer) {
        if (header != null && footer != null) {
            PacketPlayOutPlayerListHeaderFooter playerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();
            try {
                Field headerField = playerListHeaderFooter.getClass().getDeclaredField("a");
                Field footerField = playerListHeaderFooter.getClass().getDeclaredField("b");
                headerField.setAccessible(true);
                headerField.set(playerListHeaderFooter, new ChatMessage(header, new Object[0]));
                headerField.setAccessible(false);
                footerField.setAccessible(true);
                footerField.set(playerListHeaderFooter, new ChatMessage(footer, new Object[0]));
                footerField.setAccessible(false);
            }
            catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)playerListHeaderFooter);
        }
    }
    
    private EntityPlayer[] entityPlayers(EntityPlayerWrapper[] entityPlayerWrappers) {
        EntityPlayer[] entityPlayers = new EntityPlayer[entityPlayerWrappers.length];
        for (int i = 0; i < entityPlayerWrappers.length; ++i) {
            entityPlayers[i] = ((EntityPlayerVersion)entityPlayerWrappers[i]).getEntityPlayer();
        }
        return entityPlayers;
    }
    
    @Override
    public void clearTab(Player player) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.getOnlineCraftPlayers()));
    }
    
    @Override
    public void clearRows(Player player, EntityPlayerWrapper[] tabRows) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.entityPlayers(tabRows)));
    }
    
    @Override
    public void sendRows(Player player, EntityPlayerWrapper[] tabRows) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.entityPlayers(tabRows)));
    }
    
    @Override
    public EntityPlayerWrapper newRow(String rowString, TabIcon tabIcon) {
        return new EntityPlayerVersion (rowString, tabIcon);
    }
}
