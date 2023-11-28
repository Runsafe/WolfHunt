package no.runsafe.wolfhunt;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

public class Config implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		nullTrackedPlayerMessage = configuration.getConfigValueAsString("message.nullTrackedPlayer");
		offlineTrackedPlayerMessage = configuration.getConfigValueAsString("message.offlineTrackedPlayer");
		trackedPlayerInWrongWorldMessage = configuration.getConfigValueAsString("message.trackedPlayerInWrongWorld");
		nullLocationMessage = configuration.getConfigValueAsString("message.nullLocation");
		trackedPlayerNearMessage = configuration.getConfigValueAsString("message.trackedPlayerNear");
		trackedPlayerDirectionMessage = configuration.getConfigValueAsString("message.trackedPlayerDirection");
		wolfDrinksBloodMessage = configuration.getConfigValueAsString("message.wolfDrinksBlood");
		wolfSniffsBloodMessage = configuration.getConfigValueAsString("message.wolfSniffsBlood");
		easterEggMessage = configuration.getConfigValueAsString("message.easterEgg");

		trackingRadius = configuration.getConfigValueAsInt("trackingRadius");
		minimumDroppedBlood = configuration.getConfigValueAsInt("minimumDroppedBlood");
		maximumDroppedBlood = configuration.getConfigValueAsInt("maximumAdditionalDroppedBlood");
		chanceOfBloodBeingUsedUp = configuration.getConfigValueAsFloat("chanceOfBloodBeingUsedUp");
		trackingUniverse = configuration.getConfigValueAsString("trackingUniverse");
	}

	public String getNullTrackedPlayerMessage()
	{
		return nullTrackedPlayerMessage;
	}

	public String getOfflineTrackedPlayerMessage()
	{
		return offlineTrackedPlayerMessage;
	}

	public String getTrackedPlayerInWrongWorldMessage()
	{
		return trackedPlayerInWrongWorldMessage;
	}

	public String getNullLocationMessage()
	{
		return nullLocationMessage;
	}

	public String getTrackedPlayerNearMessage()
	{
		return trackedPlayerNearMessage;
	}

	public String getTrackedPlayerDirectionMessage()
	{
		return trackedPlayerDirectionMessage;
	}

	public String getWolfDrinksBloodMessage()
	{
		return wolfDrinksBloodMessage;
	}

	public String getWolfSniffsBloodMessage()
	{
		return wolfSniffsBloodMessage;
	}

	public String getEasterEggMessage()
	{
		return easterEggMessage;
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

	private String nullTrackedPlayerMessage;
	private String offlineTrackedPlayerMessage;
	private String trackedPlayerInWrongWorldMessage;
	private String nullLocationMessage;
	private String trackedPlayerNearMessage;
	private String trackedPlayerDirectionMessage;
	private String wolfDrinksBloodMessage;
	private String wolfSniffsBloodMessage;
	private String easterEggMessage;

	private int trackingRadius;
	private int minimumDroppedBlood;
	private int maximumDroppedBlood;
	private float chanceOfBloodBeingUsedUp;
	private String trackingUniverse;
}
