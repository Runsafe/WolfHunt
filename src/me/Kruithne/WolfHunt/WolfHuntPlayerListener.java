package me.Kruithne.WolfHunt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class WolfHuntPlayerListener implements Listener
{
	WolfHunt wolfHuntPlugin = null;
	
	WolfHuntPlayerListener(WolfHunt parentPlugin)
	{
		this.wolfHuntPlugin = parentPlugin;
		this.wolfHuntPlugin.server.getPluginManager().registerEvents(this, this.wolfHuntPlugin);
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		Player eventPlayer = event.getPlayer();
		ItemStack trackingItemStack = new ItemStack(this.wolfHuntPlugin.config.trackingItem);
		
		if (eventPlayer.getItemInHand() == trackingItemStack)
		{
			if (eventPlayer.hasPermission(String.format(Constants.pluginNodePath, "canTrack")))
			{
				this.wolfHuntPlugin.outputToPlayer("Placeholder here. Well, hello there.", eventPlayer);
			}
		}
	}
}
