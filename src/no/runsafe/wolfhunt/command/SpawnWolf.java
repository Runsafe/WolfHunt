package no.runsafe.wolfhunt.command;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.wolfhunt.Constants;

import java.util.Map;

public class SpawnWolf extends PlayerCommand
{
	protected SpawnWolf()
	{
		super("spawn", "Spawns a wolf at your location", "wolfhunt.canSpawnWolf");
	}

	@Override
	public String OnExecute(IPlayer executor, Map<String, String> parameters)
	{
		IWorld world = executor.getWorld();
		if (world == null)
			return "You do not appear to be in a world!";
		world.spawnCreature(executor.getLocation(), "WOLF");
		return Constants.commandInfoSpawnWolfDone;
	}
}
