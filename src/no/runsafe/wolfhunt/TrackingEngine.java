package no.runsafe.wolfhunt;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.animals.IWolf;
import no.runsafe.framework.api.event.player.IPlayerDeathEvent;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerDeathEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.item.meta.RunsafePotion;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TrackingEngine implements IPlayerInteractEntityEvent, IPlayerDeathEvent
{
	public TrackingEngine(Config config, IServer server)
	{
		this.config = config;
		this.server = server;
	}

	private String trackPlayer(IPlayer tracker, IPlayer trackedPlayer, RunsafeMeta vial)
	{
		if (trackedPlayer == null)
			return Config.Message.getWolfSniffsBlood() + "\n" + Config.Message.getNullTrackedPlayer();

		if (!trackedPlayer.isOnline())
			return Config.Message.getWolfSniffsBlood() + "\n" + Config.Message.getOfflineTrackedPlayer();

		IWorld playerWorld = trackedPlayer.getWorld();
		IWorld trackerWorld = trackedPlayer.getWorld();

		if (playerWorld == null || trackerWorld == null || !playerWorld.isWorld(trackerWorld))
			return Config.Message.getWolfSniffsBlood() + "\n" + Config.Message.getTrackedPlayerInWrongWorld();

		ILocation playerLocation = trackedPlayer.getLocation();
		ILocation trackerLocation = tracker.getLocation();

		if (playerLocation == null || trackerLocation == null)
			return Config.Message.getWolfSniffsBlood() + "\n" + Config.Message.getNullLocation();

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
			return Config.Message.getTrackedPlayerNear();

		String direction = "skyward";

		if (east_west == 1)
			direction = "east";
		else if (east_west == 2)
			direction = "west";

		if (north_south == 1)
			direction = "north-" + direction;
		else if (north_south == 2)
			direction = "south-" + direction;

		String returnMessage = String.format(Config.Message.getTrackedPlayerDirection(), direction);

		if (!(random.nextFloat() < (config.getChanceOfBloodBeingUsedUp() / 100)))
			return Config.Message.getWolfSniffsBlood() + "\n" + returnMessage;

		tracker.removeExactItem(vial, 1); // Remove one vial.
		return Config.Message.getWolfDrinksBlood() + "\n" + returnMessage;
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		IEntity entity = event.getRightClicked();

		// Make sure we are right-clicking on a wolf.
		if (!(entity instanceof IWolf))
			return;

		IWolf wolf = (IWolf) entity;
		IPlayer player = event.getPlayer();

		if (!wolf.isTamed())
			return;

		// Check the player owns the wolf.
		if (!wolf.getOwner().equals(player))
			return;

		RunsafeMeta item = player.getItemInMainHand();

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
			// Check if bottle is one of the newer ones that stores the player's UUID.
			String[] parts = loreString.split(" "); // Get the player data
			IPlayer trackedPlayer;

			// Grab player information if exists.
			if (loreString.startsWith("§0 "))
				trackedPlayer = server.getPlayer(UUID.fromString(parts[1])); // Get the tracked player
			// Check if the bottle only stores the player's username.
			else if (loreString.startsWith("§7Track: "))
				trackedPlayer = server.getPlayerExact(parts[1]); // Get the tracked player
			else continue;

			if (config.isEasterEggPlayer(trackedPlayer))
			{
				player.sendColouredMessage(Config.Message.getWolfDrinksBlood());
				player.removeExactItem(item, 1); // Remove one vial.
				runEasterEgg(player);
				return;
			}

			player.sendColouredMessage(trackPlayer(player, trackedPlayer, item)); // Run the track
			return;
		}
	}

	private void runEasterEgg(IPlayer victim)
	{
		ILocation location = victim.getLocation();
		if (location == null)
			return;
		victim.sendColouredMessage(Config.Message.getEasterEgg());

		location.getWorld().spawnCreature(location, "evocation_fangs");

		victim.addBuff(Buff.Combat.Blindness.duration(15));
		victim.addBuff(Buff.Disease.Hunger.duration(15));
		victim.addBuff(Buff.Utility.Unluck.duration(99999));
		victim.addBuff(Buff.Utility.Movement.DecreaseSpeed.duration(15));
		victim.addBuff(Buff.Combat.Damage.Wither.duration(5));
	}

	@Override
	public void OnPlayerDeathEvent(RunsafePlayerDeathEvent event)
	{
		IPlayer player = event.getEntity();

		if (player.getKiller() == null)
			return;

		IWorld world = player.getWorld();
		ILocation location = player.getLocation();

		if (world == null || location == null || !world.isUniverse(config.getTrackingUniverse()))
			return;

		int amount = config.getMinimumDroppedBlood();
		int bound = config.getMaximumDroppedBlood() - config.getMinimumDroppedBlood() + 1;
		if (bound > 1)
			amount += random.nextInt(bound);

		if (amount < 1)
			return;

		RunsafeMeta vial = Item.Brewing.Potion.getItem();
		((RunsafePotion) vial).giveCustomEffect(Buff.Healing.Instant);
		vial.setDisplayName("§3Vial of Blood");
		vial.addLore("§0 " + player.getUniqueId());
		vial.addLore("§CTrack: " + player.getName());

		for (int i = 0; i < amount; i++)
			world.dropItem(location, vial);
	}

	private final Config config;
	private final IServer server;
	private static final Random random = new Random();
}
