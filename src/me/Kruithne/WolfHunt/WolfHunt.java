package me.Kruithne.WolfHunt;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfHunt extends JavaPlugin {
	
	public Configuration config = null;
	public CommandHandler commandHandler = null;
	public WolfHuntPlayerListener playerListener = null;
	public Tracking tracking = null;
	public VanishHandler vanisHandler = null;
	public Permissions permission = null;
	public VanishHandler vanishHandler = null;
	public Output output = null;
	
	public void onEnable()
	{
		this.config = new Configuration(this);
		this.tracking = new Tracking(this.config);		
		this.permission = new Permissions(this.config);
		this.vanishHandler = new VanishHandler(this.getServer(), this.config);
		this.output = new Output(Logger.getLogger("Minecraft"));
		this.commandHandler = new CommandHandler(this.output, this.permission, this.config);
		this.playerListener = new WolfHuntPlayerListener(this.tracking, this.output, this.vanishHandler, this.permission, this.config);
		this.config.loadConfiguration();
		
		this.getServer().getPluginManager().registerEvents(this.playerListener, this);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] arguments)
	{
		return this.commandHandler.handleCommand(sender, command, arguments);
	}

}
