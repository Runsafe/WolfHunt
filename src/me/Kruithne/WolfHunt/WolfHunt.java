package me.Kruithne.WolfHunt;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.VanishPlugin;

public class WolfHunt extends JavaPlugin {
	
	public Server server;
	public Logger log = Logger.getLogger("Minecraft");
	public Configuration config = null;
	public CommandHandler commandHandler = null;
	public WolfHuntPlayerListener playerListener = null;
	public Tracking tracking = null;
	private VanishPlugin vanish = null;
	private VanishManager vanishManager = null;
	
	public void onEnable()
	{
		this.server = this.getServer();
		this.config = new Configuration(this);
		this.playerListener = new WolfHuntPlayerListener(this);
		this.commandHandler = new CommandHandler(this);
		this.tracking = new Tracking(this);		
		this.config.loadConfiguration();
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
	
	public boolean playerIsVanished(Player player)
	{
		VanishManager manager = getVanishManager();
		return manager != null && manager.isVanished(player);
	}

	public VanishManager getVanishManager()
	{
		if(vanishManager != null)
			return vanishManager;
	
		if(vanish == null)
			vanish = (VanishPlugin) this.server.getPluginManager().getPlugin("VanishNoPacket");
	
		if(vanish != null && vanish.isEnabled())
			vanishManager = this.vanish.getManager();
	
		return vanishManager;
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
