package me.Kruithne.WolfHunt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfHunt extends JavaPlugin {
	
	Server server;
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		this.server = this.getServer();
	}
	
	public void outputConsoleMessage(String message, Level outputType)
	{
		this.log.log(outputType, message);
	}
	
	public void outputMessageToPlayer(String message, Player player)
	{
		player.sendMessage(message);
	}

}
