package no.runsafe.wolfhunt;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.player.IPlayerDeathEvent;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.animals.RunsafeWolf;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerDeathEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.List;

public class TrackingEngine implements IPlayerInteractEntityEvent, IPlayerDeathEvent
{
	public TrackingEngine(Config config, IServer server)
	{
		this.config = config;
		this.server = server;
	}

	private String trackPlayer(IPlayer tracker, String playerName)
	{
		IPlayer player = server.getPlayerExact(playerName);
		if (player == null)
			return "&cThe wolf drinks the blood and looks around confused.";

		if (!player.isOnline())
			return "&cThe wolf drinks the blood but seems to do nothing.";

		IWorld playerWorld = player.getWorld();
		IWorld trackerWorld = player.getWorld();

		if (playerWorld == null || trackerWorld == null || !playerWorld.isWorld(trackerWorld))
			return "&cThe wolf drinks the blood and looks to the sky.";

		ILocation playerLocation = player.getLocation();
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
			return "&2The wolf snarls and growls. It's target is near!";

		String direction = "skyward";

		if (east_west == 1) direction = "east"; else if (east_west == 2) direction = "west";
		if (north_south == 1) direction = "north-" + direction; else if (north_south == 2) direction = "south-" + direction;

		return "&2The wolf howls in a " + direction + " direction";
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		RunsafeEntity entity = event.getRightClicked();

		// Make sure we are right-clicking on a wolf.
		if (entity.getEntityType() == LivingEntity.Wolf)
		{
			IPlayer player = event.getPlayer();
			RunsafeWolf wolf = (RunsafeWolf) entity;

			// Check the player owns the wolf.
			if (wolf.getOwner().getName().equalsIgnoreCase(player.getName()))
			{
				RunsafeMeta item = player.getItemInHand();

				// Check the player is holding a potion.
				if (item != null && item.is(Item.Brewing.Potion))
				{
					String displayName = item.getDisplayName();

					// Make sure the potion is a vial of blood.
					if (displayName != null && displayName.equals("§3Vial of Blood"))
					{
						List<String> lore = item.getLore();
						if (lore == null)
							return;

						for (String loreString : lore)
						{
							if (loreString.startsWith("§7Track: "))
							{
								String[] parts = loreString.split(" ");
								player.removeExactItem(item, 1); // Remove one vial.
								player.sendColouredMessage(trackPlayer(player, parts[1])); // Run the track
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void OnPlayerDeathEvent(RunsafePlayerDeathEvent event)
	{
		IPlayer player = event.getEntity();

		IWorld world = player.getWorld();
		ILocation location = player.getLocation();

		if (world != null && location != null)
		{
			RunsafeMeta vial = Item.Brewing.Potion.getItem();
			vial.setDurability((short) 8261);
			vial.setDisplayName("&3Vial of Blood");
			vial.addLore("&7" + player.getName());

			world.dropItem(location, vial);
		}
	}

	private final Config config;
	private final IServer server;
}
