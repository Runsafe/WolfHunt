package me.Kruithne.WolfHunt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Output {

	private Logger log = null;
	
	Output (Logger log)
	{
		this.log = log;
	}

	public void toConsole(String message, Level outputType)
	{
		this.log.log(outputType, String.format(Constants.outputToConsoleFormat, Constants.outputPluginTag, message));
	}
	
	public void toPlayer(String message, Player player)
	{
		player.sendMessage(String.format(Constants.outputToPlayerFormat, ChatColor.DARK_AQUA, message));
	}
	
}
