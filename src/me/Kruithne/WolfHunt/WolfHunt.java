package me.Kruithne.WolfHunt;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfHunt extends JavaPlugin {
	
	public CommandHandler commandHandler = null;
	public WolfHuntPlayerListener playerListener = null;
	
	public void onEnable()
	{
		Configuration config = new Configuration(this);
		Permissions permission = new Permissions(config);
		Output output = new Output(Logger.getLogger("Minecraft"));
	
		this.commandHandler = new CommandHandler(output, permission, config);
		this.playerListener = new WolfHuntPlayerListener(
				new Tracking(config),
				output,
				new VanishHandler(this.getServer(), config),
				permission,
				config
		);
		this.getServer().getPluginManager().registerEvents(this.playerListener, this);
	
		config.loadConfiguration();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] arguments)
	{
		return this.commandHandler.handleCommand(sender, command, arguments);
	}

}
