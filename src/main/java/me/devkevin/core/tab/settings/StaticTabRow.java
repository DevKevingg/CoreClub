package me.devkevin.core.tab.settings;

import me.devkevin.core.tab.abstraction.EntityPlayerWrapper;
import org.bukkit.scoreboard.Team;

public class StaticTabRow implements TabRow, StandardRow {
	
    private String rowString;
    private EntityPlayerWrapper rowPlayer;
    private Team rowTeam;
    
    public StaticTabRow(final String rowString) {
        this.rowString = rowString;
    }
    
    @Override
    public void setRowPlayer(final EntityPlayerWrapper rowPlayer) {
        this.rowPlayer = rowPlayer;
    }
    
    @Override
    public String getRowString() {
        return this.rowString;
    }
    
    @Override
    public void setRowTeam(final Team rowTeam) {
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
