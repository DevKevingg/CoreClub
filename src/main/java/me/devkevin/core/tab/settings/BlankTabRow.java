package me.devkevin.core.tab.settings;

import me.devkevin.core.tab.abstraction.EntityPlayerWrapper;
import me.devkevin.core.tab.util.TabUtil;
import org.bukkit.scoreboard.Team;

public class BlankTabRow implements TabRow, StandardRow {
	
    private EntityPlayerWrapper rowPlayer;
    private Team rowTeam;
    
    @Override
    public String getRowString() {
        return new TabUtil ().randomColorString();
    }
    
    @Override
    public void setRowPlayer(EntityPlayerWrapper rowPlayer) {
        this.rowPlayer = rowPlayer;
    }
    
    @Override
    public void setRowTeam(Team rowTeam) {
        this.rowTeam = rowTeam;
    }
    
    @Override
    public EntityPlayerWrapper getRowPlayer() {
        return this.rowPlayer;
    }
    
    public Team getRowTeam() {
		return rowTeam;
	}
}
