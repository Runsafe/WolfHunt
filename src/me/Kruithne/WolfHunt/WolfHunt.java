package me.Kruithne.WolfHunt;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfHunt extends JavaPlugin {
	
	Server server;
	Logger log = Logger.getLogger("Minecraft");
	Configuration config = null;
	CommandHandler commandHandler = null;
	WolfHuntPlayerListener playerListener = null;
	
	public void onEnable()
	{
		this.server = this.getServer();
		this.config = new Configuration(this);
		this.playerListener = new WolfHuntPlayerListener(this);
		this.commandHandler = new CommandHandler(this);
		
		this.config.loadConfiguration();
	}
	
	public void trackPlayers(Player player)
	{
		Location playerLocation = player.getLocation();
		Player closestPlayer = getTrackedPlayer(player.getWorld().getPlayers().iterator(), player, playerLocation);
		
		if (closestPlayer == player)
		{
			this.outputToPlayer(Constants.messageNoPlayers, player);
		}
		else if (closestPlayer == null)
		{
			this.outputToPlayer(Constants.messageNearby, player);
		}
		else
		{
			this.outputToPlayer(String.format(Constants.messageDetected, this.getCompassDirection(playerLocation, closestPlayer.getLocation())), player);
		}
	}

	private Player getTrackedPlayer(Iterator<Player> players, Player originPlayer, Location origin)
	{		
		 Hashtable<Double, Player> distances = new Hashtable<Double, Player>();
		 
		 while (players.hasNext())
		 {
			 Player checkPlayer = players.next();
			 
			 if (checkPlayer != originPlayer)
			 {
				 double theDistance = origin.distance(checkPlayer.getLocation());
				 if (theDistance < this.config.trackingRadius)
				 {
					 return null;
				 }
				 distances.put(origin.distance(checkPlayer.getLocation()), checkPlayer);
			 }
		 }
		 
		 if (distances.size() == 0)
		 {
			 return originPlayer;
		 }
		 return distances.get(Collections.min(distances.keySet()));
	}
	
	public String getCompassDirection(Location firstLocation, Location secondLocation)
	{		
		double fX = firstLocation.getX();
		double fZ = firstLocation.getZ();
		
		double sX = secondLocation.getX();
		double sZ = secondLocation.getZ();
		
		String xString = "";
		String zString = "";
		
		if ((sX - fX) > this.config.trackingRadius)
		{
			xString = Constants.directionNorth;
		}
		else if ((sX - fX) < (this.config.trackingRadius - (this.config.trackingRadius * 2)))
		{
			xString = Constants.directionSouth;
		}
		
		if ((sZ - fZ) > this.config.trackingRadius)
		{
			zString = Constants.directionWest;
		}
		else if ((sZ - fZ) < (this.config.trackingRadius - (this.config.trackingRadius * 2)))
		{
			zString = Constants.directionEast;
		}
		
		if (xString != "" && zString != "")
		{
			return xString + "-" + zString;
		}
		else if (xString != "")
		{
			return xString;
		}
		else if (zString != "")
		{
			return zString;
		}
		return Constants.directionUnknown;
	}
	
	public boolean hasPermission(String permKey, Player player)
	{
		if (player.isOp() && this.config.allowOpOverride)
		{
			return true;
		}
		else if (player.hasPermission(String.format(Constants.pluginNodePath, permKey)))
		{
			return true;
		}
		
		return false;
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] arguments)
	{
		return this.commandHandler.handleCommand(sender, command, arguments);
	}
	
	public void outputToConsole(String message, Level outputType)
	{
		this.log.log(outputType, String.format(Constants.outputToConsoleFormat, Constants.outputPluginTag, message));
	}
	
	public void outputToPlayer(String message, Player player)
	{
		player.sendMessage(String.format(Constants.outputToPlayerFormat, ChatColor.DARK_AQUA, message));
	}

}
