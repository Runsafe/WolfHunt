package no.runsafe.wolfhunt;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.player.IPlayerDeathEvent;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.extension.player.RunsafePlayer;
import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.animals.RunsafeWolf;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerDeathEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.List;
import java.util.UUID;

public class TrackingEngine implements IPlayerInteractEntityEvent, IPlayerDeathEvent
{
	public TrackingEngine(Config config, IServer server)
	{
		this.config = config;
		this.server = server;
	}

	private String trackPlayer(IPlayer tracker, IPlayer trackedPlayer)
	{
		if (trackedPlayer == null)
			return "&cThe wolf drinks the blood and looks around confused.";

		if (!trackedPlayer.isOnline())
			return "&cThe wolf drinks the blood but seems to do nothing.";

		IWorld playerWorld = trackedPlayer.getWorld();
		IWorld trackerWorld = trackedPlayer.getWorld();

		if (playerWorld == null || trackerWorld == null || !playerWorld.isWorld(trackerWorld))
			return "&cThe wolf drinks the blood and looks to the sky.";

		ILocation playerLocation = trackedPlayer.getLocation();
		ILocation trackerLocation = tracker.getLocation();

		if (playerLocation == null || trackerLocation == null)
			return "&cThe wolf drinks the blood and peers at you confused.";

		short east_west = -1;
		short north_south = -1;

		int trackingRadius = config.getTrackingRadius();

		double xDistance = trackerLocation.getX() - playerLocation.getX();
		double zDistance = trackerLocation.getZ() - playerLocation.getZ();

		if (xDistance < -trackingRadius)
			east_west = 1;
		else if (xDistance > trackingRadius)
			east_west = 2;


		if (zDistance < -trackingRadius)
			north_south = 2;
		else if (zDistance > trackingRadius)
			north_south = 1;

		if (east_west == -1 && north_south == -1)
			return "&2The wolf snarls and growls. Its target is near!";

		String direction = "skyward";

		if (east_west == 1)
			direction = "east";
		else if (east_west == 2)
			direction = "west";

		if (north_south == 1)
			direction = "north-" + direction;
		else if (north_south == 2)
			direction = "south-" + direction;

		return "&2The wolf howls in a " + direction + " direction";
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		RunsafeEntity entity = event.getRightClicked();

		// Make sure we are right-clicking on a wolf.
		if (entity.getEntityType() != LivingEntity.Wolf)
			return;

		IWorld world = entity.getWorld();
		if (world == null)
			return;

		// Make sure we are right-clicking on a wolf.
		if (entity.getEntityType() != LivingEntity.Wolf)
			return;

		RunsafeWolf wolf = (RunsafeWolf) ObjectWrapper.convert(entity.getRaw());
		IPlayer player = event.getPlayer();

		if (wolf == null || !wolf.isTamed())
			return;

		// Check the player owns the wolf.
		if (!wolf.getOwner().equals(player))
			return;

		RunsafeMeta item = player.getItemInHand();

		// Check the player is holding a potion.
		if (item == null || !item.is(Item.Brewing.Potion))
			return;

		String displayName = item.getDisplayName();

		// Make sure the potion is a vial of blood.
		if (displayName == null || !displayName.equals("§3Vial of Blood"))
			return;

		List<String> lore = item.getLore();
		if (lore == null)
			return;

		for (String loreString : lore)
		{
			//Check if bottle is one of the newer ones that stores the player's UUID.
			if (loreString.startsWith("§0 "))
			{
				String[] parts = loreString.split(" "); // Get the player data
				IPlayer trackedPlayer = server.getPlayer(UUID.fromString(parts[1])); // Get the tracked player
				player.removeExactItem(item, 1); // Remove one vial.
				player.sendColouredMessage(trackPlayer(player, trackedPlayer)); // Run the track
				return;
			}
			//Check if the bottle only stores the player's username.
			if (loreString.startsWith("§7Track: "))
			{
				String[] parts = loreString.split(" ");//Get the player data
				IPlayer trackedPlayer = server.getPlayerExact(parts[1]); // Get the tracked player
				player.removeExactItem(item, 1); // Remove one vial.
				player.sendColouredMessage(trackPlayer(player, trackedPlayer)); // Run the track
				return;
			}
		}
	}

	@Override
	public void OnPlayerDeathEvent(RunsafePlayerDeathEvent event)
	{
		IPlayer player = event.getEntity();

		IEntity killer = player.getKiller();
		if (killer == null || !(killer instanceof RunsafePlayer))
			return;

		IWorld world = player.getWorld();
		ILocation location = player.getLocation();

		if (world != null && location != null && world.isUniverse("survival"))
		{
			RunsafeMeta vial = Item.Brewing.Potion.getItem();
			vial.setDurability((short) 8261);
			vial.setDisplayName("§3Vial of Blood");
			vial.addLore("§0 " + player.getUniqueId());
			vial.addLore("§CTrack: " + player.getName());

			world.dropItem(location, vial);
		}
	}

	private final Config config;
	private final IServer server;
}
