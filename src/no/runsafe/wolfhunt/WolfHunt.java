package no.runsafe.wolfhunt;

import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Events;
import no.runsafe.wolfhunt.command.GetConfig;
import no.runsafe.wolfhunt.command.SetConfig;
import no.runsafe.wolfhunt.command.SpawnWolf;

import java.util.logging.Logger;

public class WolfHunt extends RunsafePlugin
{
	public WolfHuntPlayerListener playerListener = null;

	@Override
	protected void pluginSetup()
	{
		addComponent(Commands.class);
		addComponent(Events.class);

		// TODO Replace with framework configuration
		addComponent(Configuration.class);
		addComponent(Permissions.class);
		addComponent(Output.class);
		addComponent(Tracking.class);
		// TODO Replace by framework hook calls
		addComponent(VanishHandler.class);

		Command wolfhunt = new Command("wolfhunt", "WolfHunt administrative commands", null);
		wolfhunt.addSubCommand(getInstance(SpawnWolf.class));
		wolfhunt.addSubCommand(getInstance(GetConfig.class));
		wolfhunt.addSubCommand(getInstance(SetConfig.class));
		addComponent(wolfhunt);
	}
}
