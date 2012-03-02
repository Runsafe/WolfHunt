package me.Kruithne.WolfHunt;

import org.bukkit.entity.Player;

public class Permissions {

	public static String permissionFormat = "wolfhunt.%s";
	public static String canTrack = "canTrack";
	public static String commandSpawnWolf = "canSpawnWolf";
	public static String commandGetConfig = "canGetConfig";
	public static String commandSetConfig = "canSetConfig";
	
	private Configuration config = null; 
	
	public Permissions(Configuration config) 
	{
		this.config = config;
	}
	
	public boolean has(Player player, String permission) 
	{ 
		if (player.isOp() && this.config.allowOpOverride) 
			return true; 
		else if (player.hasPermission(String.format(Permissions.permissionFormat, permission))) 
			return true; 
		
		return false;
	}
	
}
