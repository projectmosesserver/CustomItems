package info.ahaha.customitems.runnable;

import info.ahaha.customitems.CustomItems;
import info.ahaha.customitems.ItemData;
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
            ItemStack slotItem = null;
            ItemMeta slotMeta = null;
            String container1 = null, container2 = null;
            ItemData itemData = null;

            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null) continue;
                ItemMeta meta = item.getItemMeta();
                if (meta == null) continue;
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (!container.has(CustomItems.plugin.getKey(), PersistentDataType.STRING)) continue;
                ItemData data = ItemData.getItemData(container.get(CustomItems.plugin.getKey(), PersistentDataType.STRING));
                if (data == null) continue;
                if (data.getSlot() == null) continue;
                slotItem = player.getInventory().getItem(data.getSlot());
                if (slotItem == null) continue;
                slotMeta = slotItem.getItemMeta();
                if (slotMeta == null) continue;
                PersistentDataContainer slotContainer = slotMeta.getPersistentDataContainer();
                if (!slotContainer.has(CustomItems.plugin.getKey(), PersistentDataType.STRING)) continue;
                if (!container.get(CustomItems.plugin.getKey(), PersistentDataType.STRING).equalsIgnoreCase(slotContainer.get(CustomItems.plugin.getKey(), PersistentDataType.STRING)))
                    continue;
                container1 = container.get(CustomItems.plugin.getKey(), PersistentDataType.STRING);
                container2 = slotContainer.get(CustomItems.plugin.getKey(), PersistentDataType.STRING);
                itemData = data;

                break;
            }
            if (itemData != null && container1 != null && container2 != null) {
                boolean check = container1.equals(container2);
                if (check) {
                    effects.put(player, itemData.getEffects());
                    if (!player.getActivePotionEffects().isEmpty()){
                        for (PotionEffect effect : player.getActivePotionEffects())
                            player.removePotionEffect(effect.getType());
                    }
                    for (PotionEffect effect : effects.get(player)) {
                        if (!player.hasPotionEffect(effect.getType())) {
                            player.addPotionEffect(effect);
                        }
                    }
                }
            } else {
                if (effects.containsKey(player)) {
                    for (PotionEffect effect : player.getActivePotionEffects())
                        player.removePotionEffect(effect.getType());
                    effects.remove(player);
                }
            }
        }
    }
}
