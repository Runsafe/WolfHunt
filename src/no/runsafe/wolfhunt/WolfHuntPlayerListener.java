package no.runsafe.wolfhunt;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.LegacyMaterial;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

public class WolfHuntPlayerListener implements IPlayerInteractEntityEvent, IConfigurationChanged
{
	private Tracking tracking = null;

	WolfHuntPlayerListener(Tracking tracking)
	{
		this.tracking = tracking;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		babyWolvesCanTrack = configuration.getConfigValueAsBoolean("babyWolvesCanTrack");
		trackingItem = configuration.getConfigValueAsInt("trackingItem");
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		if (shouldTrackPlayers(event))
		{
			IPlayer player = event.getPlayer();
			player.sendColouredMessage(tracking.trackPlayersRelativeTo(player));
			event.cancel();
		}
	}

	private boolean shouldTrackPlayers(RunsafePlayerInteractEntityEvent event)
	{
		IPlayer eventPlayer = event.getPlayer();
		RunsafeEntity target = event.getRightClicked();

		if (this.isNotHoldingTrackingItem(event))
			return false;

		if (this.isNotWolf(target))
			return false;

		if (eventPlayer.isVanished())
			return false;

		Wolf wolf = (Wolf) target;

		return this.isAdult(wolf) && this.isTrackingWolf(wolf, eventPlayer) && this.allowedTrack(eventPlayer);
	}

	private boolean isNotWolf(RunsafeEntity entity)
	{
		return entity.getEntityType().getRaw() != EntityType.WOLF;
	}

	private boolean isAdult(Wolf wolf)
	{
		return babyWolvesCanTrack || wolf.getAge() >= 0;
	}

	private boolean isNotHoldingTrackingItem(RunsafePlayerInteractEntityEvent event)
	{
		RunsafeMeta item = event.getPlayer().getItemInHand();
		return item == null || item.getItemType().getType() != LegacyMaterial.getById(trackingItem);
	}

	private boolean isTrackingWolf(Wolf wolf, IPlayer player)
	{
		return wolf.isTamed() && wolf.getOwner() == player;
	}

	private boolean allowedTrack(IPlayer player)
	{
		return player.hasPermission("wolfhunt.canTrack");
	}

	private boolean babyWolvesCanTrack;
	private int trackingItem;
}
