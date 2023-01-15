package pregen;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.popcraft.chunky.api.ChunkyAPI;


public class Pregen extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                ChunkyAPI chunky = getServer().getServicesManager().load(ChunkyAPI.class);
                chunky.startTask("world", "square", 0, 0, 2624, 2624, "concentric");
                chunky.onGenerationComplete(event -> getServer().shutdown());
            }
        }.runTaskLater(this, 1);
    }
}
