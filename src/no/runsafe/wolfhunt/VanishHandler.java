package no.runsafe.wolfhunt;

import no.runsafe.framework.api.player.IPlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.VanishPlugin;

public class VanishHandler {

	private Server server;
	private Configuration config;
	private VanishPlugin vanish = null;
	private VanishManager vanishManager = null;
	
	private boolean initiated = false;
	
	VanishHandler(Server server, Configuration config)
	{
		this.server = server;
		this.config = config;
	}
	
	public boolean playerIsVanished(IPlayer player)
	{
		if(!config.enableVanishNoPacketSupport)
			return false;

		VanishManager manager = getVanishManager();
		return manager != null && manager.isVanished(player);
	}

	private VanishManager getVanishManager()
	{
		if(vanishManager != null)
			return vanishManager;
			
		if(vanish == null && !this.initiated)
		{
			vanish = (VanishPlugin) this.server.getPluginManager().getPlugin("VanishNoPacket");
			this.initiated = true;
		}
	
		if(vanish != null && vanish.isEnabled())
			vanishManager = this.vanish.getManager();
	
		return vanishManager;
	}
	
}
