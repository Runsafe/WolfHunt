package no.runsafe.wolfhunt.command;

import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.wolfhunt.Configuration;
import no.runsafe.wolfhunt.Constants;

import java.util.Map;

public class SetConfig extends ExecutableCommand
{
	protected SetConfig(Configuration config)
	{
		super("setconfig", "Change a configuration value", "wolfhunt.canSetConfig", new RequiredArgument("key"), new RequiredArgument("value"));
		this.config = config;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		String key = parameters.get("key");
		String value = parameters.get("value");
		String configValueCheck = this.config.getConfigValue(key);

		if (configValueCheck != null)
		{
			this.config.setConfigValue(key, value);
			this.config.loadConfiguration();
			return Constants.commandInfoSetConfigDone;
		}
		return Constants.commandInfoConfigNoExists;
	}

	private final Configuration config;
}
