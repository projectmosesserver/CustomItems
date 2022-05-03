package info.ahaha.customitems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ItemData {

    private String name;
    private Material material;
    private List<String> lore;
    private EquipmentSlot slot;
    private List<PotionEffect> effects;
    private final Attribute attribute;
    private final AttributeModifier modifier;
    private ItemStack item;
    public static List<ItemData> data = new ArrayList<>();


    public ItemData(String name, Material material, List<String> lore, EquipmentSlot slot, List<PotionEffect> effects, Attribute attribute,AttributeModifier modifier) {
        this.name = name;
        this.material = material;
        this.lore = lore;
        this.slot = slot;
        this.effects = effects;
        this.attribute = attribute;
        this.modifier = modifier;
        createItem();
        data.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<PotionEffect> effects) {
        this.effects = effects;
    }

    public ItemStack getItem() {
        return item;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeModifier getModifier() {
        return modifier;
    }

    public void createItem() {
        this.item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getName());
        if (!getLore().isEmpty())
            meta.setLore(getLore());
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(CustomItems.plugin.getKey(), PersistentDataType.STRING, getName());
        if (getModifier() != null && getAttribute() != null){
            meta.addAttributeModifier(getAttribute(),getModifier());
        }
        item.setItemMeta(meta);
    }

    public static ItemData getItemData(String name){
        for (ItemData data : ItemData.data){
            if (data.getName().equals(name))return data;
        }
        return null;
    }
}
