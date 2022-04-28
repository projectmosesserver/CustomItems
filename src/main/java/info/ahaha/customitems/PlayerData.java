package info.ahaha.customitems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class PlayerData {

    private UUID uuid;
    private Map<EquipmentSlot, List<PotionEffect>> effects = new HashMap<>();
    public static List<PlayerData> data = new ArrayList<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            effects.put(slot, null);
        }
        data.add(this);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void checkEffects(EquipmentSlot slot) {
        Player player = Bukkit.getPlayer(uuid);
        if (!isSlotInItem(slot)) {
            if (effects.get(slot) != null) {
                for (PotionEffect effect : effects.get(slot)) {
                    player.removePotionEffect(effect.getType());
                }
                effects.put(slot, null);
            }
        }else {
            ItemStack item = player.getInventory().getItem(slot);
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String key = container.get(CustomItems.plugin.getKey(), PersistentDataType.STRING);
            ItemData data = ItemData.getItemData(key);
            effects.put(slot, data.getEffects());
        }

    }

    public boolean isSlotInItem(EquipmentSlot slot) {
        if (slot == null) return false;
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return false;
        ItemStack item = player.getInventory().getItem(slot);
        if (item == null)return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(CustomItems.plugin.getKey(), PersistentDataType.STRING)) return false;
        String key = container.get(CustomItems.plugin.getKey(), PersistentDataType.STRING);
        ItemData data = ItemData.getItemData(key);
        if (data == null) return false;
        if (data.getSlot() != slot)return false;
        return true;
    }

    public void addEffects() {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        if (!player.isOnline()) return;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            checkEffects(slot);
            if (effects.get(slot) != null) {
                effects.get(slot).forEach((player::addPotionEffect));
            }
        }
    }

    public static PlayerData getPlayerData(UUID uuid){
        for (PlayerData data : PlayerData.data){
            if (data.getUuid().equals(uuid))return data;
        }
        return null;
    }
}
