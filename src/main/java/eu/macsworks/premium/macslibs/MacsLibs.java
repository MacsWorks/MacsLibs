package eu.macsworks.premium.macslibs;

import eu.macsworks.premium.macslibs.licensing.HWID;
import eu.macsworks.premium.macslibs.listeners.RegisteredButtonClickListener;
import eu.macsworks.premium.macslibs.utils.LibLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;

public final class MacsLibs extends JavaPlugin {

    @Getter private static MacsLibs instance = null;

    private static void setInstance(MacsLibs libs) { instance = libs; }

    @Getter private LibLoader libLoader;

    private RegisteredButtonClickListener registeredButtonClickListener;

    @Override
    public void onEnable() {
        setInstance(this);

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

    private void loadListeners(){
        Bukkit.getPluginManager().registerEvents(registeredButtonClickListener, this);
    }

    private void loadTasks(){
        Bukkit.getScheduler().runTaskTimer(this, registeredButtonClickListener::tick, 0L, 20L);
    }

    public static boolean checkLicense(String license){
        return true;
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
