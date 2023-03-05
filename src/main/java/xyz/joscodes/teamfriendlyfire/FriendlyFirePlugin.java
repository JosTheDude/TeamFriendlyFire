package xyz.joscodes.teamfriendlyfire;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class FriendlyFirePlugin extends JavaPlugin implements Listener {
	private boolean friendlyFireEnabled;

	@Override
	public void onEnable() {
		friendlyFireEnabled = true;
		getServer().getPluginManager().registerEvents(this, this);
		Objects.requireNonNull(getCommand("friendlyfire")).setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("friendlyfire") || !(sender instanceof Player player)) return false;

		if (!player.hasPermission("friendlyfire.toggle")) return false;

		friendlyFireEnabled = !friendlyFireEnabled;
		player.sendMessage(friendlyFireEnabled ? ChatColor.GREEN + "Friendly fire is now enabled." : ChatColor.RED + "Friendly fire is now disabled.");
		return true;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player damager) || !(event.getEntity() instanceof Player victim)) return;

		if (friendlyFireEnabled && damager.getScoreboard().getEntryTeam(damager.getName()) == victim.getScoreboard().getEntryTeam(victim.getName())) {
			event.setCancelled(true);
		}
	}
}