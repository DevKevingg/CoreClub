package me.devkevin.core.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.utils.CC;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ShutdownTask extends BukkitRunnable
{
    private final List<Integer> BROADCAST_TIMES;
    private CorePlugin plugin;
    private int secondsUntilShutdown;

    public void run() {
        if (this.BROADCAST_TIMES.contains(this.secondsUntilShutdown)) {
            this.plugin.getServer().broadcastMessage(CC.LIGHT_PURPLE + "(Siri) " + CC.AQUA + "The server will shutdown in " + CC.YELLOW + this.secondsUntilShutdown + CC.AQUA + " seconds.");
        }
        if (this.secondsUntilShutdown <= 0) {
            this.plugin.getServer().getOnlinePlayers().forEach(player -> player.sendMessage(CC.RED + "The server has shut down."));
            this.plugin.getServer().shutdown();
        }
        --this.secondsUntilShutdown;
    }

    public List<Integer> getBROADCAST_TIMES() {
        return this.BROADCAST_TIMES;
    }

    public CorePlugin getPlugin() {
        return this.plugin;
    }

    public int getSecondsUntilShutdown() {
        return this.secondsUntilShutdown;
    }

    public void setPlugin(final CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void setSecondsUntilShutdown(final int secondsUntilShutdown) {
        this.secondsUntilShutdown = secondsUntilShutdown;
    }

    public ShutdownTask(final CorePlugin plugin, final int secondsUntilShutdown) {
        this.BROADCAST_TIMES = Arrays.asList(3600, 1800, 900, 600, 300, 180, 120, 60, 45, 30, 15, 10, 5, 4, 3, 2, 1);
        this.plugin = plugin;
        this.secondsUntilShutdown = secondsUntilShutdown;
    }
}
