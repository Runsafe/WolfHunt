package no.runsafe.wolfhunt;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;

import java.util.ArrayList;
import java.util.List;

public class Config implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		Message.nullTrackedPlayer = configuration.getConfigValueAsString("message.nullTrackedPlayer");
		Message.offlineTrackedPlayer = configuration.getConfigValueAsString("message.offlineTrackedPlayer");
		Message.trackedPlayerInWrongWorld = configuration.getConfigValueAsString("message.trackedPlayerInWrongWorld");
		Message.nullLocation = configuration.getConfigValueAsString("message.nullLocation");
		Message.trackedPlayerNear = configuration.getConfigValueAsString("message.trackedPlayerNear");
		Message.trackedPlayerDirection = configuration.getConfigValueAsString("message.trackedPlayerDirection");
		Message.wolfDrinksBlood = configuration.getConfigValueAsString("message.wolfDrinksBlood");
		Message.wolfSniffsBlood = configuration.getConfigValueAsString("message.wolfSniffsBlood");
		Message.easterEgg = configuration.getConfigValueAsString("message.easterEgg");
		Message.commandBloodObtained = configuration.getConfigValueAsString("message.commandBloodObtained");

		trackingRadius = configuration.getConfigValueAsInt("trackingRadius");
		minimumDroppedBlood = configuration.getConfigValueAsInt("minimumDroppedBlood");
		maximumDroppedBlood = configuration.getConfigValueAsInt("maximumDroppedBlood");
		chanceOfBloodBeingUsedUp = configuration.getConfigValueAsFloat("chanceOfBloodBeingUsedUp");
		trackingUniverse = configuration.getConfigValueAsString("trackingUniverse");

		easterEggPlayers.clear();
		easterEggPlayers.addAll(configuration.getConfigValueAsList("easterEggPlayers"));
	}

	public static final class Message
	{
		public static String getNullTrackedPlayer()
		{
			return nullTrackedPlayer;
		}

		public static String getOfflineTrackedPlayer()
		{
			return offlineTrackedPlayer;
		}

		public static String getTrackedPlayerInWrongWorld()
		{
			return trackedPlayerInWrongWorld;
		}

		public static String getNullLocation()
		{
			return nullLocation;
		}

		public static String getTrackedPlayerNear()
		{
			return trackedPlayerNear;
		}

		public static String getTrackedPlayerDirection()
		{
			return trackedPlayerDirection;
		}

		public static String getWolfDrinksBlood()
		{
			return wolfDrinksBlood;
		}

		public static String getWolfSniffsBlood()
		{
			return wolfSniffsBlood;
		}

		public static String getEasterEgg()
		{
			return easterEgg;
		}

		public static String getCommandBloodObtained()
		{
			return commandBloodObtained;
		}

		private static String nullTrackedPlayer;
		private static String offlineTrackedPlayer;
		private static String trackedPlayerInWrongWorld;
		private static String nullLocation;
		private static String trackedPlayerNear;
		private static String trackedPlayerDirection;
		private static String wolfDrinksBlood;
		private static String wolfSniffsBlood;
		private static String easterEgg;
		private static String commandBloodObtained;
	}

	public int getTrackingRadius()
	{
		return trackingRadius;
	}

	public int getMinimumDroppedBlood()
	{
		return minimumDroppedBlood;
	}

	public int getMaximumDroppedBlood()
	{
		return maximumDroppedBlood;
	}

	public float getChanceOfBloodBeingUsedUp()
	{
		return chanceOfBloodBeingUsedUp;
	}

	public String getTrackingUniverse()
	{
		return trackingUniverse;
	}

	public boolean isEasterEggPlayer(IPlayer player)
	{
		if (player == null)
			return false;

		return easterEggPlayers.contains(player.getName());
	}

	private int trackingRadius;
	private int minimumDroppedBlood;
	private int maximumDroppedBlood;
	private float chanceOfBloodBeingUsedUp;
	private String trackingUniverse;
	private final List<String> easterEggPlayers = new ArrayList<>(0);
}
