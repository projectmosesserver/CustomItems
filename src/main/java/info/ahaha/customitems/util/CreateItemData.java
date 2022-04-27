package info.ahaha.customitems.util;

import info.ahaha.customitems.DataManager;
import info.ahaha.customitems.ItemData;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateItemData {

    public static void createItemData(DataManager manager) {

        for (String item : manager.getConfig().getStringList("Items")) {

            String name = manager.getConfig().getString(item + ".Name");
            Material material = Material.valueOf(manager.getConfig().getString(item + ".Material"));
            List<String> lore = manager.getConfig().getStringList(item + ".Lore");
            List<PotionEffect> effects = new ArrayList<>();
            for (String potion : manager.getConfig().getStringList(item + ".Effects")) {
                PotionEffect effect = new PotionEffect(PotionEffectType.getByName(potion), 99999, manager.getConfig().getInt(item + "." + potion), true, true, true);
                effects.add(effect);
            }
            EquipmentSlot slot = EquipmentSlot.valueOf(manager.getConfig().getString(item + ".Slot"));
            Attribute attribute = null;
            for (Attribute attribute1 : Attribute.values()) {
                if (attribute1.name().equalsIgnoreCase(manager.getConfig().getString(item + ".Attribute.Name"))) {
                    attribute = attribute1;
                    break;
                }
            }
            double amount = manager.getConfig().getDouble(item + ".Attribute.Amount");

            AttributeModifier modifier = null;
            if (attribute != null) {
                modifier = new AttributeModifier(UUID.randomUUID(), attribute.name(), amount, AttributeModifier.Operation.ADD_NUMBER, slot);
            }

            new ItemData(name, material, lore, slot, effects, attribute, modifier);

        }
    }
}
