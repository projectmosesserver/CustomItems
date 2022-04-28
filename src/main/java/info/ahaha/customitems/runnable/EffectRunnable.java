package info.ahaha.customitems.runnable;

import info.ahaha.customitems.CustomItems;
import info.ahaha.customitems.ItemData;
import info.ahaha.customitems.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EffectRunnable implements Runnable {
    Map<Player, List<PotionEffect>> effects = new HashMap<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData data = PlayerData.getPlayerData(player.getUniqueId());
            if (data == null){
                data = new PlayerData(player.getUniqueId());
            }
            data.addEffects();
        }
    }
}
