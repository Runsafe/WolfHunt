package me.Kruithne.WolfHunt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler {

	private WolfHunt wolfHuntPlugin = null;
	
	CommandHandler (WolfHunt parentPlugin)
	{
		this.wolfHuntPlugin = parentPlugin;
	}
	
	public boolean handleCommand(CommandSender sender, Command command, String[] arguments)
	{
		if (command.getName().equalsIgnoreCase("wolfhunt") || command.getName().equalsIgnoreCase("wh"))
		{
			if (arguments.length > 0)
			{
				
			}
			else
			{
				this.wolfHuntPlugin.outputToPlayer(Constants.commandNoParameters, (Player) sender);
				this.wolfHuntPlugin.outputToPlayer(Constants.commandSeeHelp, (Player) sender);
			}
			return true;
		}
		return false;
	}
	
	public void printCommandHelp(CommandSender sender)
	{
		Boolean hasCommand = false;
		
		this.wolfHuntPlugin.outputToPlayer(Constants.commandAvailable, (Player) sender);
		
		if (this.wolfHuntPlugin.hasPermission("commandSpawnWolf", (Player) sender))
		{
			this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoSpawnWolf,(Player) sender);
			hasCommand = true;
		}
		
		if (!hasCommand)
		{
			this.wolfHuntPlugin.outputToPlayer(Constants.commandNoneAvail, (Player) sender);
		}
	}
	
}
