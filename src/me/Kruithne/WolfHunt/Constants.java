package me.Kruithne.WolfHunt;

import org.bukkit.Material;

public class Constants {
	
	public static boolean enableVanishNoPacketSupport = false;
	public static boolean trackOP = true;
	
	public static String messageNoPlayers = "The wolf's tail wags as it looks up at you.";
	public static String messageDetected = "The wolf barks in the %s direction.";
	public static String messageNearby = "The wolf growls loudly.";
	
	public String directionNorth = "north";
	public String directionEast = "east";
	public String directionSouth = "south";
	public String directionWest = "west";
	public String directionUnknown = "skyward";
	
	public static String outputPluginTag = "[WolfHunt]";
	public static String outputToConsoleFormat = "%s %s";
	public static String outputToPlayerFormat = "%s%s";
	
	public static String pluginNodePath = "wolfhunt.%s";
	
	public static String configDirectory = "plugins/WolfHunt";
	public static String configFailed = "Failed to create the configuration file, using built-in configuration.";
	
	public static String default_trackingItem = Integer.toString(Material.STRING.getId());
	public static String default_allowOpOverride = "true";
	
}
