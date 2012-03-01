package me.Kruithne.WolfHunt;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class WolfHuntPlayerListener implements Listener
{
	private WolfHunt wolfHuntPlugin = null;
	
	WolfHuntPlayerListener(WolfHunt parentPlugin)
	{
		this.wolfHuntPlugin = parentPlugin;
		this.wolfHuntPlugin.server.getPluginManager().registerEvents(this, this.wolfHuntPlugin);
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		Player eventPlayer = event.getPlayer();
		
		if (eventPlayer.getItemInHand().getTypeId() == this.wolfHuntPlugin.config.trackingItem)
		{
			Entity interactedEntity = event.getRightClicked();
			
			if (interactedEntity.getType() == EntityType.WOLF)
			{
				Wolf interactedWolf = (Wolf) interactedEntity;
				
				if (interactedWolf.isTamed() && interactedWolf.getOwner() == (AnimalTamer) eventPlayer)
				{
					if (this.wolfHuntPlugin.hasPermission("canTrack", eventPlayer))
					{
						this.wolfHuntPlugin.trackPlayers(eventPlayer);
					}
				}
			}
		}
	}
}
