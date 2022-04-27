package info.ahaha.customitems.cmd;

import info.ahaha.customitems.ItemData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.isOp()) return true;
        if (args[0].equals("get")) {
            if (args[1] == null) return true;
            ItemData data = ItemData.getItemData(args[1]);
            if (data == null) return true;
            player.getInventory().addItem(data.getItem());
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length <= 1)
            return new ArrayList<>(Collections.singletonList("get"));
        if (args.length <= 2){
                List<String> list = new ArrayList<>();
                for (ItemData data : ItemData.data) {
                    list.add(data.getName());
                }
                return list;
            }
        return null;
    }
}
