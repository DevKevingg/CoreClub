package me.devkevin.core.tab;

import me.devkevin.core.CorePlugin;
import me.devkevin.core.tab.abstraction.EntityPlayerWrapper;
import me.devkevin.core.tab.settings.DynamicTabRow;
import me.devkevin.core.tab.settings.StandardRow;
import me.devkevin.core.tab.settings.StaticTabRow;
import me.devkevin.core.tab.settings.TabRow;
import me.devkevin.core.tab.util.TabUtil;
import org.bukkit.scoreboard.Team;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import org.bukkit.entity.Player;

public class Tab {
	
    private Player player;
    private String tabTitle;
    private String tabFooter;
    private List<TabRow> tabRows;
    private Random random;
    
    public Tab(Player player, String tabTitle, String tabFooter) {
        this.tabRows = new ArrayList<TabRow>();
        this.random = new Random();
        this.player = player;
        this.tabTitle = tabTitle;
        this.tabFooter = tabFooter;
    }
    
    public void updateTab() {
        for (TabRow tabRow : this.tabRows) {
            if (tabRow instanceof DynamicTabRow) {
                ((DynamicTabRow )tabRow).updateRow();
            }
        }
    }
    
    private EntityPlayerWrapper[] tabRowList7() {
        EntityPlayerWrapper[] tabRowList = new EntityPlayerWrapper[this.tabRows.size()];
        for (int i = 0; i < this.tabRows.size() / 3; ++i) {
            tabRowList[i * 3] = this.tabRows.get(0 + i).getRowPlayer();
            tabRowList[i * 3 + 1] = this.tabRows.get(20 + i).getRowPlayer();
            tabRowList[i * 3 + 2] = this.tabRows.get(40 + i).getRowPlayer();
        }
        return tabRowList;
    }
    
    private EntityPlayerWrapper[] tabRowList() {
        EntityPlayerWrapper[] tabRowList = new EntityPlayerWrapper[this.tabRows.size()];
        for (int i = 0; i < this.tabRows.size(); ++i) {
            tabRowList[i] = this.tabRows.get(i).getRowPlayer();
        }
        return tabRowList;
    }
    
    public void clearTab() {
        CorePlugin.getPlugin().getPacketUtil().clearRows(this.player, this.tabRowList());
        CorePlugin.getPlugin().getPacketUtil().setFooterAndHeader(this.player, "", "");
    }
    
    public void sendTab7() {
        CorePlugin.getPlugin().getPacketUtil().clearTab(this.player);
        CorePlugin.getPlugin().getPacketUtil().setFooterAndHeader(this.player, this.tabTitle, this.tabFooter);
        CorePlugin.getPlugin().getPacketUtil().sendRows(this.player, this.tabRowList7());
    }
    
    public void sendTab() {
        CorePlugin.getPlugin().getPacketUtil().clearTab(this.player);
        CorePlugin.getPlugin().getPacketUtil().setFooterAndHeader(this.player, this.tabTitle, this.tabFooter);
        CorePlugin.getPlugin().getPacketUtil().sendRows(this.player, this.tabRowList());
    }
    
    public List<TabRow> getTabRows() {
        return this.tabRows;
    }
    
    private Team getTeam(Player player, int index) {
        String alphabeticalTeamName = new TabUtil().getAlphabeticalString(index) + this.random.nextInt(1000000);
        Team toReturn;
        if (player.getScoreboard().getTeam(alphabeticalTeamName) == null) {
            toReturn = player.getScoreboard().registerNewTeam(alphabeticalTeamName);
        }
        else {
            toReturn = player.getScoreboard().getTeam(alphabeticalTeamName);
        }
        return toReturn;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void addTabRow(StandardRow standardRow, TabIcon tabIcon) {
        int index = this.tabRows.size();
        if (standardRow instanceof StaticTabRow) {
            String[] rowString = new TabUtil ().splitString(standardRow.getRowString());
            EntityPlayerWrapper rowPlayer = CorePlugin.getPlugin().getPacketUtil().newRow(new TabUtil().fillEndString(rowString[1]), tabIcon);
            standardRow.setRowPlayer(rowPlayer);
            Team rowTeam = this.getTeam(this.player, index);
            rowTeam.addEntry(rowPlayer.getName());
            if (rowString[0] != null) {
                rowTeam.setPrefix(rowString[0]);
            }
            if (rowString[2] != null) {
                rowTeam.setSuffix(rowString[2]);
            }
            standardRow.setRowTeam(rowTeam);
        }
        else {
            EntityPlayerWrapper rowPlayer2 = CorePlugin.getPlugin().getPacketUtil().newRow(new TabUtil().fillEndString(standardRow.getRowString()), tabIcon);
            standardRow.setRowPlayer(rowPlayer2);
            Team rowTeam2 = this.getTeam(this.player, index);
            rowTeam2.addEntry(rowPlayer2.getName());
            standardRow.setRowTeam(rowTeam2);
        }
        this.tabRows.add((TabRow)standardRow);
    }
    
    public void addTabRow(StandardRow standardRow) {
        this.addTabRow(standardRow, TabIcon.GREY);
    }
}
