package me.Kruithne.WolfHunt;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.VanishPlugin;

public class VanishHandler {

	private Server server;
	private VanishPlugin vanish = null;
	private VanishManager vanishManager = null;
	
	VanishHandler(Server server)
	{
		this.server = server;
	}
	
	public boolean playerIsVanished(Player player)
	{
		VanishManager manager = getVanishManager();
		return manager != null && manager.isVanished(player);
	}

	private VanishManager getVanishManager()
	{
		if(vanishManager != null)
			return vanishManager;
	
		if(vanish == null)
			vanish = (VanishPlugin) this.server.getPluginManager().getPlugin("VanishNoPacket");
	
		if(vanish != null && vanish.isEnabled())
			vanishManager = this.vanish.getManager();
	
		return vanishManager;
	}
	
}
