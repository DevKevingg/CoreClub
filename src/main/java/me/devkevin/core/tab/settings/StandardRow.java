package me.devkevin.core.tab.settings;

import me.devkevin.core.tab.abstraction.EntityPlayerWrapper;
import org.bukkit.scoreboard.Team;


public interface StandardRow {
	
    void setRowTeam(Team team);
    
    String getRowString();
    
    void setRowPlayer(EntityPlayerWrapper entity);
}
