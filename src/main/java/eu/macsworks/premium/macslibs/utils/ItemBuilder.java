package eu.macsworks.premium.macslibs.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.macsworks.premium.macslibs.MacsLibs;
import eu.macsworks.premium.macslibs.listeners.RegisteredButtonClickListener;
import eu.macsworks.premium.macslibs.objects.InteractResult;
import eu.macsworks.premium.macslibs.objects.PhisicalInteractResult;
import eu.macsworks.premium.macslibs.utils.versionpopulators.FoodPopulator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.ToolComponent;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemBuilder {

    private ItemStack item;

    public static ItemBuilder builder(){
        if(MacsLibs.getInstance().getScamProtected().get()){
            Bukkit.getLogger().severe("The MacsLibs instance is scam-protected. No API method will work until the stolen plugin will be paid for.");
            return null;
        }

        return new ItemBuilder();
    }

    public ItemBuilder item(ItemStack item){
        this.item = item;
        return this;
    }

    public ItemBuilder material(Material mat){
        checkItem();

        item.setType(mat);
        return this;
    }

    public ItemBuilder customModelData(int cmd){
        checkItem();
        ItemMeta meta = getMeta();
        meta.setCustomModelData(cmd);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder name(String name){
        checkItem();
        ItemMeta meta = getMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String name){
        checkItem();
        ItemMeta meta = getMeta();
        meta.setLore(Arrays.asList(name.split("\n")));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int lvl){
        checkItem();
        ItemMeta meta = getMeta();
        meta.addEnchant(enchantment, lvl, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable){
        checkItem();
        ItemMeta meta = getMeta();
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder interactive(Consumer<InteractResult> onInteract){
        checkItem();
        String id = UUID.randomUUID().toString();
        NBTItem nbt = new NBTItem(item);
        nbt.setString("macsitem", id);
        item = nbt.getItem();
        RegisteredButtonClickListener.addAction(id, onInteract);
        return this;
    }

    public ItemBuilder interactable(Consumer<PhisicalInteractResult> onInteract){
        checkItem();
        String id = UUID.randomUUID().toString();
        NBTItem nbt = new NBTItem(item);
        nbt.setString("macsitem_interact", id);
        item = nbt.getItem();
        RegisteredButtonClickListener.addInteractAction(id, onInteract);
        return this;
    }

    public ItemBuilder onHold(Consumer<Player> onHold){
        checkItem();
        String id = UUID.randomUUID().toString();
        NBTItem nbt = new NBTItem(item);
        nbt.setString("macsitem_hold", id);
        item = nbt.getItem();
        RegisteredButtonClickListener.addHoldAction(id, onHold);
        return this;
    }

    public ItemBuilder interactive(Consumer<InteractResult> onInteract, String id){
        checkItem();
        NBTItem nbt = new NBTItem(item);
        nbt.setString("macsitem", id);
        item = nbt.getItem();
        RegisteredButtonClickListener.addAction(id, onInteract);
        return this;
    }

    public ItemBuilder interactable(Consumer<PhisicalInteractResult> onInteract, String id){
        checkItem();
        NBTItem nbt = new NBTItem(item);
        nbt.setString("macsitem_interact", id);
        item = nbt.getItem();
        RegisteredButtonClickListener.addInteractAction(id, onInteract);
        return this;
    }

    public ItemBuilder onHold(Consumer<Player> onHold, String id){
        checkItem();
        NBTItem nbt = new NBTItem(item);
        nbt.setString("macsitem_hold", id);
        item = nbt.getItem();
        RegisteredButtonClickListener.addHoldAction(id, onHold);
        return this;
    }

    public ItemBuilder makeStatic(){
        checkItem();
        NBTItem nbt = new NBTItem(item);
        nbt.setString("macsitem", "nothing");
        item = nbt.getItem();
        return this;
    }

    public ItemBuilder skullOf(UUID uuid){
        checkItem();
        item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) getMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder skullOf(String name){
        checkItem();
        item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) getMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder skullOf(OfflinePlayer player){
        checkItem();
        item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) getMeta();
        meta.setOwnerProfile(player.getPlayerProfile());
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder food(int nutrition, float saturation, float eatSeconds){
        checkItem();
        ItemMeta meta = item.getItemMeta();
        new FoodPopulator().populate(meta, new Object[]{nutrition, saturation, eatSeconds});
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder unbreakable(boolean b){
        checkItem();
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(b);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder fireResistant(boolean b){
        checkItem();
        ItemMeta meta = item.getItemMeta();
        meta.setFireResistant(b);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder nbt(String tag, String value){
        checkItem();
        NBTItem it = new NBTItem(item);
        it.setString(tag, value);
        item = it.getItem();
        return this;
    }

    public ItemBuilder nbt(String tag, int value){
        checkItem();
        NBTItem it = new NBTItem(item);
        it.setInteger(tag, value);
        item = it.getItem();
        return this;
    }

    public ItemBuilder nbt(String tag, boolean value){
        checkItem();
        NBTItem it = new NBTItem(item);
        it.setBoolean(tag, value);
        item = it.getItem();
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag){
        checkItem();
        ItemMeta meta = getMeta();
        meta.addItemFlags(flag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build(){
        checkItem();
        return item.clone();
    }

    private void checkItem(){
        if(item == null){
            item = new ItemStack(Material.STICK, 1);
        }
    }

    private ItemMeta getMeta(){
        return item.getItemMeta();
    }

}
