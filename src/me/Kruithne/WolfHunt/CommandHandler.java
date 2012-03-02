package me.Kruithne.WolfHunt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CommandHandler {

	private WolfHunt wolfHuntPlugin = null;
	
	CommandHandler (WolfHunt parentPlugin)
	{
		this.wolfHuntPlugin = parentPlugin;
	}
	
	public boolean handleCommand(CommandSender sender, Command command, String[] arguments)
	{
		Player player = (Player) sender;
		if (command.getName().equalsIgnoreCase("wolfhunt") || command.getName().equalsIgnoreCase("wh"))
		{
			if (arguments.length > 0)
			{
				if (arguments[0].equalsIgnoreCase("help"))
				{
					this.printCommandHelp(sender);
				}
				else if (arguments[0].equalsIgnoreCase("spawnwolf"))
				{
					if (this.wolfHuntPlugin.hasPermission("wolfhunt.commandSpawnWolf", player))
					{
						player.getWorld().spawnCreature(player.getLocation(), EntityType.WOLF);
						this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoSpawnWolfDone, player);
					}
					else
					{
						this.wolfHuntPlugin.outputToPlayer(Constants.commandNoPermission, player);
						this.wolfHuntPlugin.outputToPlayer(Constants.commandSeeHelp, player);
					}
				}
				else if (arguments[0].equalsIgnoreCase("getconfig"))
				{
					if (this.wolfHuntPlugin.hasPermission("wolfhunt.commandGetConfig", player))
					{
						if (arguments.length > 1)
						{
							String configValue = this.wolfHuntPlugin.config.getConfigValue(arguments[1]);
							if (configValue != null)
							{
								this.wolfHuntPlugin.outputToPlayer(String.format(Constants.commandInfoGetConfigReturnFormat, arguments[1], configValue), player);
							}
							else
							{
								this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoConfigNoExists, player);
							}
						}
						else
						{
							this.wolfHuntPlugin.outputToPlayer(Constants.commandFormatInvalid, player);
							this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoGetConfig, player);
						}
					}
					else
					{
						this.wolfHuntPlugin.outputToPlayer(Constants.commandNoPermission, player);
						this.wolfHuntPlugin.outputToPlayer(Constants.commandSeeHelp, player);
					}
				}
				else if (arguments[0].equalsIgnoreCase("setconfig"))
				{
					if (this.wolfHuntPlugin.hasPermission("wolfhunt.commandSetConfig", player))
					{
						if (arguments.length > 2)
						{
							String configValueCheck = this.wolfHuntPlugin.config.getConfigValue(arguments[1]);
							
							if (configValueCheck != null)
							{
								this.wolfHuntPlugin.config.setConfigValue(arguments[1], arguments[2]);
								this.wolfHuntPlugin.outputToPlayer(String.format(Constants.commandInfoSetConfigDone, arguments[1], arguments[2]), player);
							}
							else
							{
								this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoConfigNoExists, player);
							}
					
						}
						else
						{
							this.wolfHuntPlugin.outputToPlayer(Constants.commandFormatInvalid, player);
							this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoSetConfig, player);
						}
					}
					else
					{
						this.wolfHuntPlugin.outputToPlayer(Constants.commandNoPermission, player);
						this.wolfHuntPlugin.outputToPlayer(Constants.commandSeeHelp, player);
					}
				}
				else if (arguments[0].equalsIgnoreCase("reloadConfig"))
				{
					if (this.wolfHuntPlugin.hasPermission("wolfhunt.commandReloadConfig", player))
					{
						this.wolfHuntPlugin.config.loadConfiguration();
					}
					else
					{
						this.wolfHuntPlugin.outputToPlayer(Constants.commandNoPermission, player);
						this.wolfHuntPlugin.outputToPlayer(Constants.commandSeeHelp, player);
					}
				}
				else
				{
					this.wolfHuntPlugin.outputToPlayer(String.format(Constants.commandUnknown, arguments[0]), player);
					this.wolfHuntPlugin.outputToPlayer(Constants.commandSeeHelp, player);
				}
			}
			else
			{
				this.wolfHuntPlugin.outputToPlayer(Constants.commandNoParameters, player);
				this.wolfHuntPlugin.outputToPlayer(Constants.commandSeeHelp, player);
			}
			return true;
		}
		return false;
	}
	
	public void printCommandHelp(CommandSender sender)
	{
		Boolean hasCommand = false;
		
		Player player = (Player) sender;
		
		this.wolfHuntPlugin.outputToPlayer(Constants.commandAvailable, player);
		
		if (this.wolfHuntPlugin.hasPermission("commandSpawnWolf", player))
		{
			this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoSpawnWolf, player);
			hasCommand = true;
		}
		
		if (this.wolfHuntPlugin.hasPermission("commandGetConfig", player))
		{
			this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoGetConfig, player);
			hasCommand = true;
		}
		
		if (this.wolfHuntPlugin.hasPermission("commandSetConfig", player))
		{
			this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoSetConfig, player);
			hasCommand = true;
		}
		
		if (this.wolfHuntPlugin.hasPermission("commandReloadConfig", player))
		{
			this.wolfHuntPlugin.outputToPlayer(Constants.commandInfoReloadConfig, player);
			hasCommand = true;
		}
		
		if (!hasCommand)
		{
			this.wolfHuntPlugin.outputToPlayer(Constants.commandNoneAvail, (Player) sender);
		}
	}
	
}
