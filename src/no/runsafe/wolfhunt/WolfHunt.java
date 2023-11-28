package no.runsafe.wolfhunt;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Events;
import no.runsafe.wolfhunt.commands.GetBlood;

public class WolfHunt extends RunsafeConfigurablePlugin
{
	@Override
	protected void pluginSetup()
	{
		// Framework features
		addComponent(Commands.class);
		addComponent(Events.class);

		// Plugin components
		addComponent(Config.class);
		addComponent(TrackingEngine.class);

		// Commands
		addComponent(GetBlood.class);
	}
}
