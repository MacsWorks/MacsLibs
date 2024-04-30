package eu.macsworks.premium.macslibs.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.macsworks.premium.macslibs.objects.InteractResult;
import eu.macsworks.premium.macslibs.objects.PhisicalInteractResult;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.function.Consumer;

public class RegisteredButtonClickListener implements Listener {

    public static HashMap<String, Consumer<InteractResult>> onClick = new HashMap<>();
    public static HashMap<String, Consumer<PhisicalInteractResult>> onInteract = new HashMap<>();
    public static HashMap<String, Consumer<Player>> onHold = new HashMap<>();

    public void tick(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() != Material.AIR){
                if(new NBTItem(item).hasTag("macsitem_hold")){
                    onHold.get(new NBTItem(item).getString("macsitem_hold")).accept(player);
                }
            }

            ItemStack item1 = player.getInventory().getItemInOffHand();
            if(item1.getType() != Material.AIR){
                if(new NBTItem(item1).hasTag("macsitem_hold")){
                    onHold.get(new NBTItem(item1).getString("macsitem_hold")).accept(player);
                }
            }
        });
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        NBTItem it = new NBTItem(event.getCurrentItem());
        if (!it.hasTag("macsitem")) return;

        event.setCancelled(true);

        if (!onClick.containsKey(it.getString("macsitem"))) return;
        if(it.getString("macsitem").equalsIgnoreCase("nothing")) return;

        onClick.get(it.getString("macsitem")).accept(new InteractResult(it, (Player) event.getWhoClicked(), event));
    }

    public static void addAction(String id, Consumer<InteractResult> onClick){
        RegisteredButtonClickListener.onClick.put(id, onClick);
    }

    public static void addHoldAction(String id, Consumer<Player> onHold){
        RegisteredButtonClickListener.onHold.put(id, onHold);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType() == Material.AIR) return;

        NBTItem it = new NBTItem(event.getItem());
        if (!it.hasTag("macsitem_interact")) return;

        event.setCancelled(true);

        if (!onInteract.containsKey(it.getString("macsitem_interact"))) return;
        if(it.getString("macsitem_interact").equalsIgnoreCase("nothing")) return;

        onInteract.get(it.getString("macsitem_interact")).accept(new PhisicalInteractResult(it, event.getPlayer(), event));
    }

    public static void addInteractAction(String id, Consumer<PhisicalInteractResult> onClick){
        RegisteredButtonClickListener.onInteract.put(id, onClick);
    }

}
