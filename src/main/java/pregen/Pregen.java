package pregen;

import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.popcraft.chunky.api.ChunkyAPI;


public class Pregen extends JavaPlugin implements Listener {

    private static final String[] WORLDS = {"world", "world_nether"};
    private static final int WORLD_RADIUS = 2500; // use a much lower number for testing. e.g. 256

    private int worldsIndex = 0;

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        startNextWorld();
        for (World world : getServer().getWorlds()) {
            world.getWorldBorder().setSize(WORLD_RADIUS);
        }
    }

    private void startNextWorld() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ChunkyAPI chunky = getServer().getServicesManager().load(ChunkyAPI.class);
                int index = worldsIndex++;
                chunky.startTask(WORLDS[index], "square", 0, 0, WORLD_RADIUS + 128, WORLD_RADIUS + 128, "concentric");
                chunky.onGenerationComplete(event -> {
                    if (worldsIndex >= WORLDS.length) {
                        getServer().shutdown();
                    } else {
                        startNextWorld();
                    }
                });
            }
        }.runTaskLater(this, 1);
    }
}
