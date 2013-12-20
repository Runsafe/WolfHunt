package no.runsafe.wolfhunt;

import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.LegacyMaterial;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

public class WolfHuntPlayerListener implements IPlayerInteractEntityEvent
{
	private Tracking tracking = null;
	private Output output = null;
	private VanishHandler vanish = null;
	private Permissions permission = null;
	private Configuration config = null;

	WolfHuntPlayerListener(Tracking tracking, VanishHandler vanish, Permissions permission, Output output, Configuration config)
	{
		this.tracking = tracking;
		this.output = output;
		this.vanish = vanish;
		this.permission = permission;
		this.config = config;
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		if (shouldTrackPlayers(event))
		{
			IPlayer player = event.getPlayer();
			this.output.toPlayer(this.tracking.trackPlayersRelativeTo(player), player);
			event.cancel();
		}
	}

	private boolean shouldTrackPlayers(RunsafePlayerInteractEntityEvent event)
	{
		IPlayer eventPlayer = event.getPlayer();
		RunsafeEntity target = event.getRightClicked();

		if (!this.isHoldingTrackingItem(event))
			return false;

		if (!this.isWolf(target))
			return false;

		if (this.playerIsVanished(eventPlayer))
			return false;

		Wolf wolf = (Wolf) target;

		if (this.isBaby(wolf))
			return false;

		if (!this.isPlayersWolf(wolf, eventPlayer))
			return false;

		return this.allowedTrack(eventPlayer);
	}

	private boolean isWolf(RunsafeEntity entity)
	{
		return entity.getEntityType().getRaw() == EntityType.WOLF;
	}

	private boolean playerIsVanished(IPlayer player)
	{
		return this.vanish.playerIsVanished(player);
	}

	private boolean isBaby(Wolf wolf)
	{
		return !this.config.babyWolvesCanTrack && wolf.getAge() < 0;
	}

	private boolean isHoldingTrackingItem(RunsafePlayerInteractEntityEvent event)
	{
		return event.getPlayer().getItemInHand().getItemType().getType() == LegacyMaterial.getById(this.config.trackingItem);
	}

	private boolean isPlayersWolf(Wolf wolf, IPlayer player)
	{
		return wolf.isTamed() && wolf.getOwner() == (AnimalTamer) player;
	}

	private boolean allowedTrack(IPlayer player)
	{
		return this.permission.has(player, Permissions.canTrack);
	}
}
