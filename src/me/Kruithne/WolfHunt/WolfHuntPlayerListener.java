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
		if (isHoldingTrackingItem(event))
		{
			Entity interactedEntity = event.getRightClicked();
			if (interactedEntity.getType() == EntityType.WOLF)
			{
				Wolf interactedWolf = (Wolf) interactedEntity;
				if (shouldTrack(interactedWolf, eventPlayer) && allowedTrack(eventPlayer))
				{
					this.wolfHuntPlugin.trackPlayers(eventPlayer);
				}
			}
		}
	}

	private boolean isHoldingTrackingItem(PlayerInteractEntityEvent event)
	{
		return event.getPlayer().getItemInHand().getTypeId() == this.wolfHuntPlugin.config.trackingItem;
	}

	private boolean shouldTrack(Wolf wolf, Player player)
	{
		return wolf.isTamed() && wolf.getOwner() == (AnimalTamer)player;
	}

	private boolean allowedTrack(Player player)
	{
		return this.wolfHuntPlugin.hasPermission("canTrack", player);
	}
}
