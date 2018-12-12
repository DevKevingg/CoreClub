package me.devkevin.core.tab.packet;

import me.devkevin.core.tab.TabIcon;
import me.devkevin.core.tab.abstraction.EntityPlayerWrapper;
import org.bukkit.entity.Player;

public interface PacketUtil {
	
    EntityPlayerWrapper newRow(String value , TabIcon icon);
    
    void sendRows(Player player , EntityPlayerWrapper[] entity);
    
    void clearRows(Player player , EntityPlayerWrapper[] entity);
    
    void clearTab(Player player);
    
    void setFooterAndHeader(Player player , String footer , String header);
}
