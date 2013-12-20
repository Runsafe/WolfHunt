package no.runsafe.wolfhunt.command;

import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.wolfhunt.Configuration;
import no.runsafe.wolfhunt.Constants;

import java.util.Map;

public class GetConfig extends ExecutableCommand
{
	protected GetConfig(Configuration config)
	{
		super("getconfig", "Gets a configuration value", "wolfhunt.canGetConfig", new RequiredArgument("key"));
		this.config = config;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		String configValue = this.config.getConfigValue(parameters.get("key"));
		if (configValue != null)
			return String.format(Constants.commandInfoGetConfigReturnFormat, parameters.get("key"), configValue);
		else
			return Constants.commandInfoConfigNoExists;
	}

	private final Configuration config;
}
