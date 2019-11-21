package net.projectx.simcity.functions.berufe;
import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Elektriker implements Listener {
    @EventHandler
    public void PlaceKindOfRedstone(BlockPlaceEvent event){
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        Block placed = event.getBlock();

       if(placed.getType().toString().startsWith("REDSTONE")){
           if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
               p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
               event.setCancelled(true);
               return;
           }
       }else{
           if(placed.getType().toString().endsWith("PRESSURE_PLATE")){
               if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                   p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                   event.setCancelled(true);
                   return;
               }
           }else{
               if(placed.getType().toString().endsWith("PISTON")){
                   if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                       p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                       event.setCancelled(true);
                       return;
                   }
               }else{
                   if(placed.getType().equals(Material.HOPPER)){
                       if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                           p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                           event.setCancelled(true);
                           return;
                       }
                   }else{
                       if(placed.getType().toString().endsWith("MINECART")){
                           if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                               p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                               event.setCancelled(true);
                               return;
                           }
                       }else{
                           if(placed.getType().toString().endsWith("RAIL")){
                               if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                   p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                   event.setCancelled(true);
                                   return;
                               }
                           }else{
                               if(placed.getType().equals(Material.COMPARATOR)){
                                   if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                       p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                       event.setCancelled(true);
                                       return;
                                   }
                               }else{
                                   if(placed.getType().equals(Material.REPEATER)){
                                       if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                           p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                           event.setCancelled(true);
                                           return;
                                       }
                                   }else{
                                       if(placed.getType().equals(Material.DROPPER)){
                                           if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                               p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                               event.setCancelled(true);
                                               return;
                                           }
                                       }else{
                                           if(placed.getType().equals(Material.DAYLIGHT_DETECTOR)){
                                               if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                                   p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                                   event.setCancelled(true);
                                                   return;
                                               }
                                           }else{
                                               if(placed.getType().equals(Material.LEVER)){
                                                   if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                                       p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                                       event.setCancelled(true);
                                                       return;
                                                   }
                                               }else{
                                                   if(placed.getType().equals(Material.IRON_TRAPDOOR)){
                                                       if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                                           p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                                           event.setCancelled(true);
                                                           return;
                                                       }
                                                   }else{
                                                       if(placed.getType().equals(Material.IRON_DOOR)){
                                                           if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                                               p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                                               event.setCancelled(true);
                                                               return;
                                                           }
                                                       }else{
                                                           if(placed.getType().equals(Material.DISPENSER)){
                                                               if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                                                   p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                                                   event.setCancelled(true);
                                                                   return;
                                                               }
                                                           }else{
                                                               if(placed.getType().equals(Material.OBSERVER)){
                                                                   if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                                                                       p.sendMessage("§cDu musst Elektriker sein, um diesen Block bauen zu können.");
                                                                       event.setCancelled(true);
                                                                       return;
                                                                   }
                                                               }
                                                           }
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
           }
       }
    }
    @EventHandler
    public void CraftKindOfRedstone(CraftItemEvent event){
        if(event.getInventory().getHolder() instanceof Player){
            Inventory inventory = event.getInventory();
            Player p = (Player) inventory.getHolder();
            UUID uuid = p.getUniqueId();

            if(inventory.getType().equals(InventoryType.WORKBENCH)||inventory.getType().equals(InventoryType.CRAFTING)){
                ItemStack item = event.getRecipe().getResult();

                if(item.getType().toString().startsWith("REDSTONE")){
                    if(!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")){
                        p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                        event.setCancelled(true);
                        return;
                    }
                }else {
                    if (item.getType().toString().endsWith("PRESSURE_PLATE")) {
                        if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                            p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                            event.setCancelled(true);
                            return;
                        }
                    } else {
                        if (item.getType().toString().endsWith("PISTON")) {
                            if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                event.setCancelled(true);
                                return;
                            }
                        } else {
                            if (item.getType().equals(Material.HOPPER)) {
                                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                    p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                    event.setCancelled(true);
                                    return;
                                }
                            } else {
                                if (item.getType().toString().endsWith("MINECART")) {
                                    if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                        p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                        event.setCancelled(true);
                                        return;
                                    }
                                } else {
                                    if (item.getType().toString().endsWith("RAIL")) {
                                        if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                            p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                            event.setCancelled(true);
                                            return;
                                        }
                                    } else {
                                        if (item.getType().equals(Material.COMPARATOR)) {
                                            if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                event.setCancelled(true);
                                                return;
                                            }
                                        } else {
                                            if (item.getType().equals(Material.REPEATER)) {
                                                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                    p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                    event.setCancelled(true);
                                                    return;
                                                }
                                            } else {
                                                if (item.getType().equals(Material.DROPPER)) {
                                                    if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                        p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                        event.setCancelled(true);
                                                        return;
                                                    }
                                                } else {
                                                    if (item.getType().equals(Material.DAYLIGHT_DETECTOR)) {
                                                        if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                            p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                            event.setCancelled(true);
                                                            return;
                                                        }
                                                    } else {
                                                        if (item.getType().equals(Material.LEVER)) {
                                                            if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                                p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                                event.setCancelled(true);
                                                                return;
                                                            }
                                                        } else {
                                                            if (item.getType().equals(Material.IRON_TRAPDOOR)) {
                                                                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                                    p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                                    event.setCancelled(true);
                                                                    return;
                                                                }
                                                            } else {
                                                                if (item.getType().equals(Material.IRON_DOOR)) {
                                                                    if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                                        p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                                        event.setCancelled(true);
                                                                        return;
                                                                    }
                                                                } else {
                                                                    if (item.getType().equals(Material.DISPENSER)) {
                                                                        if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                                            p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                                            event.setCancelled(true);
                                                                            return;
                                                                        }
                                                                    } else {
                                                                        if (item.getType().equals(Material.OBSERVER)) {
                                                                            if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Elektriker")) {
                                                                                p.sendMessage("§cDu musst Elektriker sein, um diesen Block craften zu können.");
                                                                                event.setCancelled(true);
                                                                                return;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
