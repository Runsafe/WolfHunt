package me.Kruithne.WolfHunt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfHunt extends JavaPlugin {
	
	Server server;
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		this.server = this.getServer();
		this.loadConfiguration();
	}
	
	public String getConfigValue(String configKey)
	{	
		if (this.getConfig().contains(String.format(Constants.configNodePath, configKey)))
		{
			return this.getConfig().getString(String.format(Constants.configNodePath, configKey));
		}
		
		return null;
	}
	
	public void setConfigValue(String configKey, String configValue)
	{
		this.getConfig().set(configKey, configValue);
		this.saveConfig();
	}
	
	public void loadConfiguration()
	{
		//Coming soon.
	}
	
	public void outputToConsole(String message, Level outputType)
	{
		this.log.log(outputType, String.format(Constants.outputToConsoleFormat, Constants.outputPluginTag, message));
	}
	
	public void outputToPlayer(String message, Player player)
	{
		player.sendMessage(String.format(Constants.outputToPlayerFormat, ChatColor.DARK_AQUA, Constants.outputPluginTag, message));
	}

}
