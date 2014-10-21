package no.runsafe.wolfhunt;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

public class Config implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		trackingRadius = configuration.getConfigValueAsInt("trackingRadius");
	}

	public int getTrackingRadius()
	{
		return trackingRadius;
	}

	private int trackingRadius;
}
