package net.projectx.simcity.functions.berufe;

import net.projectx.simcity.functions.mysql.MySQL_User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftInventoryCrafting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Schmied implements Listener {
    @EventHandler
    public void Schmieden(CraftItemEvent event) {
        if (event.getInventory().getHolder() instanceof Player) {

            Inventory inventory = event.getInventory();
            Player p = (Player) inventory.getHolder();
            UUID uuid = p.getUniqueId();


            if (inventory.getType().equals(InventoryType.WORKBENCH)) {

                ItemStack item = event.getRecipe().getResult();

                if (!item.getType().toString().startsWith("WOODEN_")) {
                    if (!item.getType().toString().startsWith("LEATHER_")) {
                        if (!item.getType().toString().startsWith("TURTLE_")) {
                            if (!item.getType().toString().startsWith("CHAINMAIL_")) {
                                if (!item.getType().toString().startsWith("STONE_")) {


                                    if (item.getType().toString().endsWith("_SWORD")) {
                                        if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                            event.setCancelled(true);
                                            p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                            return;
                                        }
                                    } else {

                                        if (item.getType().toString().endsWith("_PICKAXE")) {
                                            if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                event.setCancelled(true);
                                                p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                return;

                                            }
                                        } else {
                                            if (item.getType().toString().endsWith("_AXE")) {
                                                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                    event.setCancelled(true);
                                                    p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                    return;

                                                }
                                            } else {
                                                if (item.getType().toString().endsWith("_SHOVEL")) {
                                                    if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                        event.setCancelled(true);
                                                        p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                        return;

                                                    }
                                                } else {
                                                    if (item.getType().toString().endsWith("_HOE")) {
                                                        if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                            event.setCancelled(true);
                                                            p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                            return;

                                                        }
                                                    } else {
                                                        if (item.getType().toString().endsWith("_HELMET")) {
                                                            if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                                event.setCancelled(true);
                                                                p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                                return;
                                                            }
                                                        } else {
                                                            if (item.getType().toString().endsWith("_CHESTPLATE")) {
                                                                if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                                    event.setCancelled(true);
                                                                    p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                                    return;
                                                                }
                                                            } else {
                                                                if (item.getType().toString().endsWith("_LEGGINGS")) {
                                                                    if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                                        event.setCancelled(true);
                                                                        p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                                        return;
                                                                    }
                                                                } else {
                                                                    if (item.getType().toString().endsWith("_BOOTS")) {
                                                                        if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                                            event.setCancelled(true);
                                                                            p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
                                                                            return;
                                                                        }
                                                                    } else {
                                                                        if (item.getType().toString().endsWith("_HORSE_ARMOR")) {
                                                                            if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                                                                                event.setCancelled(true);
                                                                                p.sendMessage("§cDu musst ein Schmied sein, um dieses Item craften zu können.");
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


    @EventHandler
    public void Amboss(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        UUID uuid = p.getUniqueId();
        if (event.hasBlock()) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Block Amboss = event.getClickedBlock();
                if (Amboss.getType().toString().endsWith("ANVIL")) {
                    if (!MySQL_User.getJob(uuid).equalsIgnoreCase("Schmied")) {
                        event.setCancelled(true);
                        p.sendMessage("§cDu musst ein Schmied sein, um Ambosse nutzen zu können.");
                        return;
                    }
                }
            }
        }
    }
}
