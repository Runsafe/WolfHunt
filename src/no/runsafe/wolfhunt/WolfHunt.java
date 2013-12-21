package no.runsafe.wolfhunt;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.features.Events;

public class WolfHunt extends RunsafeConfigurablePlugin
{
	@Override
	protected void pluginSetup()
	{
		// Framework features
		addComponent(Events.class);

		// Plugin components
		addComponent(Tracking.class);
		addComponent(WolfHuntPlayerListener.class);
	}
}
