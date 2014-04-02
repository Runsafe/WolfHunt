package no.runsafe.wolfhunt;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.Item;

public class Config implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		trackingItem = Item.get(configuration.getConfigValueAsString("trackingItem"));
		trackingRadius = configuration.getConfigValueAsInt("trackingRadius");
	}

	public Item getTrackingItem()
	{
		return trackingItem;
	}

	public int getTrackingRadius()
	{
		return trackingRadius;
	}

	private Item trackingItem;
	private int trackingRadius;
}
