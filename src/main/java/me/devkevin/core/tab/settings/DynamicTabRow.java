package me.devkevin.core.tab.settings;

import me.devkevin.core.tab.abstraction.EntityPlayerWrapper;
import org.bukkit.scoreboard.Team;


public class DynamicTabRow implements TabRow, StandardRow {
	
    private EntityPlayerWrapper rowPlayer;
    private String rowString;
    private Team rowTeam;
    
    public DynamicTabRow(String rowString) {
        this.rowString = rowString;
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
    
    public void updateRow() {
        DynamicRow dynamicRow = (DynamicRow)this;
        String prefix = dynamicRow.getPrefix();
        if (prefix != null) {
            this.rowTeam.setPrefix(prefix);
        }
        String suffix = dynamicRow.getSuffix();
        if (suffix != null) {
            this.rowTeam.setSuffix(suffix);
        }
    }
    
    @Override
    public String getRowString() {
        return this.rowString;
    }
}
