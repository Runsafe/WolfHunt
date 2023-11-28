package no.runsafe.wolfhunt;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

public class Config implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		trackingRadius = configuration.getConfigValueAsInt("trackingRadius");
		minimumDroppedBlood = configuration.getConfigValueAsInt("minimumDroppedBlood");
		maximumDroppedBlood = configuration.getConfigValueAsInt("maximumAdditionalDroppedBlood");
		chanceOfBloodBeingUsedUp = configuration.getConfigValueAsFloat("chanceOfBloodBeingUsedUp");
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

	private int trackingRadius;
	private int minimumDroppedBlood;
	private int maximumDroppedBlood;
	private float chanceOfBloodBeingUsedUp;
}
