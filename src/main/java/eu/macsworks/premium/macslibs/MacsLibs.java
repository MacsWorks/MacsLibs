package eu.macsworks.premium.macslibs;

import eu.macsworks.premium.macslibs.advertising.AdvertManager;
import eu.macsworks.premium.macslibs.licensing.LicensingManager;
import eu.macsworks.premium.macslibs.listeners.RegisteredButtonClickListener;
import eu.macsworks.premium.macslibs.utils.LibLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MacsLibs extends JavaPlugin {

    @Getter private static MacsLibs instance = null;

    private static void setInstance(MacsLibs libs) { instance = libs; }

    @Getter private LibLoader libLoader;
    @Getter private AdvertManager advertManager;

    private RegisteredButtonClickListener registeredButtonClickListener;

    @Getter private AtomicBoolean scamProtected = new AtomicBoolean(false);

    @Getter private List<Plugin> requestingPlugins = new ArrayList<>();

    @Override
    public void onEnable() {
        setInstance(this);

        advertManager = new AdvertManager();
        libLoader = new LibLoader();

        registeredButtonClickListener = new RegisteredButtonClickListener();

        loadListeners();
        loadTasks();

        Bukkit.getLogger().info("+-----------------------+");
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("MacsLibs was made by MacsWorks.eu!");
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("+-----------------------+");
    }

    public void setManagedPlugin(Plugin plugin) {
        requestingPlugins.add(plugin);
    }

    private void loadListeners(){
        Bukkit.getPluginManager().registerEvents(registeredButtonClickListener, this);
    }

    private void loadTasks(){
        Bukkit.getScheduler().runTaskTimer(this, registeredButtonClickListener::tick, 0L, 20L);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
           requestingPlugins.forEach(p -> {
               if(scamProtected.get()) return;

               LicensingManager.isLicenseBlocked(p.getName()).thenAcceptAsync(b -> {
                   if(!b) return; //Plugin isn't scam-protected, complete the registration
                   scamProtected.set(true);

                   Bukkit.getScheduler().cancelTasks(p);
                   HandlerList.unregisterAll(p);
               });
           });
        }, 0L, 100L);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("+-----------------------+");
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("MacsLibs was made by MacsWorks.eu!");
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("+-----------------------+");
    }
}
