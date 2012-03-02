package me.Kruithne.WolfHunt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CommandHandler {

	private WolfHunt wolfHuntPlugin = null;
	
	private enum WolfHuntOperation
	{
		help,
		spawnwolf,
		getconfig,
		setconfig,
		unknown
	}
	
	CommandHandler (WolfHunt plugin)
	{
		this.wolfHuntPlugin = plugin;
	}
	
	public boolean handleCommand(CommandSender sender, Command command, String[] arguments)
	{
		if (!command.getName().equalsIgnoreCase("wolfhunt") && !command.getName().equalsIgnoreCase("wh"))
			return false;
			
		handleOperation((Player) sender, arguments);

		return true;
	}
	
	private void handleOperation(Player player, String[] arguments)
	{
		if (arguments.length < 1)
		{
			this.wolfHuntPlugin.output.toPlayer(Constants.commandNoParameters, player);
			this.wolfHuntPlugin.output.toPlayer(Constants.commandSeeHelp, player);
			return;
		}

		switch(GetOperation(arguments[0]))
		{
			case help:
				printCommandHelp(player);
				break;
				
			case spawnwolf:
				spawnWolfOperation(player);
				break;
			
			case getconfig:
				getConfigOperation(player, arguments);
				break;
			
			case setconfig:
				setConfigOperation(player, arguments);
				break;

			default:
				this.wolfHuntPlugin.output.toPlayer(Constants.commandUnknown, player);
				break;
		}
	}
	
	private WolfHuntOperation GetOperation(String argument)
	{
		try
		{
			return WolfHuntOperation.valueOf(argument.toLowerCase());
		}
		catch (IllegalArgumentException e)
		{
			return WolfHuntOperation.unknown;
		}
	}
	
	private void spawnWolfOperation(Player player)
	{
		if (this.wolfHuntPlugin.permission.has(player, Permissions.commandSpawnWolf))
		{
			player.getWorld().spawnCreature(player.getLocation(), EntityType.WOLF);
			this.wolfHuntPlugin.output.toPlayer(Constants.commandInfoSpawnWolfDone, player);
		}
		else
		{
			this.wolfHuntPlugin.output.toPlayer(Constants.commandNoPermission, player);
			this.wolfHuntPlugin.output.toPlayer(Constants.commandSeeHelp, player);
		}
	}

	private void getConfigOperation(Player player, String[] arguments)
	{
		if (this.wolfHuntPlugin.permission.has(player, Permissions.commandGetConfig))
		{
			if (arguments.length > 1)
			{
				String configValue = this.wolfHuntPlugin.config.getConfigValue(arguments[1]);
				if (configValue != null)
				{
					this.wolfHuntPlugin.output.toPlayer(
						String.format(
							Constants.commandInfoGetConfigReturnFormat, 
							arguments[1], 
							configValue
						), 
						player
					);
				}
				else
				{
					this.wolfHuntPlugin.output.toPlayer(Constants.commandInfoConfigNoExists, player);
				}
			}
			else
			{
				this.wolfHuntPlugin.output.toPlayer(Constants.commandFormatInvalid, player);
				this.wolfHuntPlugin.output.toPlayer(Constants.commandInfoGetConfig, player);
			}
		}
		else
		{
			this.wolfHuntPlugin.output.toPlayer(Constants.commandNoPermission, player);
			this.wolfHuntPlugin.output.toPlayer(Constants.commandSeeHelp, player);
		}
	}

	private void setConfigOperation(Player player, String[] arguments)
	{
		if (this.wolfHuntPlugin.permission.has(player, Permissions.commandSetConfig))
		{
			if (arguments.length > 2)
			{
				String configValueCheck = this.wolfHuntPlugin.config.getConfigValue(arguments[1]);
				
				if (configValueCheck != null)
				{
					this.wolfHuntPlugin.config.setConfigValue(arguments[1], arguments[2]);
					this.wolfHuntPlugin.config.loadConfiguration();
					this.wolfHuntPlugin.output.toPlayer(
						String.format(
							Constants.commandInfoSetConfigDone, 
							arguments[1], 
							arguments[2]
						), 
						player
					);
				}
				else
				{
					this.wolfHuntPlugin.output.toPlayer(Constants.commandInfoConfigNoExists, player);
				}
		
			}
			else
			{
				this.wolfHuntPlugin.output.toPlayer(Constants.commandFormatInvalid, player);
				this.wolfHuntPlugin.output.toPlayer(Constants.commandInfoSetConfig, player);
			}
		}
		else
		{
			this.wolfHuntPlugin.output.toPlayer(Constants.commandNoPermission, player);
			this.wolfHuntPlugin.output.toPlayer(Constants.commandSeeHelp, player);
		}
	}

	private void printCommandHelp(CommandSender sender)
	{
		Boolean hasCommand = false;
		
		Player player = (Player) sender;
		
		this.wolfHuntPlugin.output.toPlayer(Constants.commandAvailable, player);
		
		if (this.wolfHuntPlugin.permission.has(player, Permissions.commandSpawnWolf))
		{
			this.wolfHuntPlugin.output.toPlayer(Constants.commandInfoSpawnWolf, player);
			hasCommand = true;
		}
		
		if (this.wolfHuntPlugin.permission.has(player, Permissions.commandGetConfig))
		{
			this.wolfHuntPlugin.output.toPlayer(Constants.commandInfoGetConfig, player);
			hasCommand = true;
		}
		
		if (this.wolfHuntPlugin.permission.has(player, Permissions.commandSetConfig))
		{
			this.wolfHuntPlugin.output.toPlayer(Constants.commandInfoSetConfig, player);
			hasCommand = true;
		}
		
		if (!hasCommand)
		{
			this.wolfHuntPlugin.output.toPlayer(Constants.commandNoneAvail, (Player) sender);
		}
	}
}
