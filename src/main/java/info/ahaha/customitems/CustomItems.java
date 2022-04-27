package info.ahaha.customitems;

import com.sun.org.apache.xml.internal.utils.NameSpace;
import info.ahaha.customitems.cmd.Cmd;
import info.ahaha.customitems.runnable.EffectRunnable;
import info.ahaha.customitems.util.CreateItemData;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomItems extends JavaPlugin {

    public static CustomItems plugin;
    private DataManager manager;
    private NamespacedKey key;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.manager = new DataManager(this);
        this.key = new NamespacedKey(this, "customItems");
        CreateItemData.createItemData(manager);

        getServer().getScheduler().runTaskTimer(this, new EffectRunnable(), 0, 1);

        getCommand("ci").setExecutor(new Cmd());
        getCommand("ci").setTabCompleter(new Cmd());
    }

    public DataManager getManager() {
        return manager;
    }

    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
