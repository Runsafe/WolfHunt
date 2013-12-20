package no.runsafe.wolfhunt;

import no.runsafe.framework.api.player.IPlayer;

public class VanishHandler
{
	private Configuration config;

	VanishHandler(Configuration config)
	{
		this.config = config;
	}

	public boolean playerIsVanished(IPlayer player)
	{
		if (!config.enableVanishNoPacketSupport)
			return false;

		return player.isVanished();
	}
}
