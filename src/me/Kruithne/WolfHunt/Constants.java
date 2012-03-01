package me.Kruithne.WolfHunt;

public class Constants {
	
	public static int trackingRadius = 200;
	public static int trackingItem = 287;
	
	public static boolean enableBreeding = true;
	public static boolean enabledDebug = false;
	public static boolean enableVanishNoPacketSupport = false;
	
	public static boolean trackOP = true;
	
	public static String messageNoPlayers = "The wolf's tail wags as it looks up at you.";
	public static String messageDetected = "The wolf barks in the %s direction.";
	public static String messageNearby = "The wolf growls loudly.";
	public static String messageBreed = "Your wolf has found a nearby mate and birthed a baby wolf.";
	
	public static String directionNorth = "north";
	public static String directionEast = "east";
	public static String directionSouth = "south";
	public static String directionWest = "west";
	public static String directionUnknown = "skyward";
	
	public static int breedChanceRawPorkchop = 5;
	public static int breedChanceCookedPorkchop = 5;
	public static int breedChanceRawChicken = 5;
	public static int breedChanceCookedChicken = 5;
	public static int breedChanceRawBeef = 5;
	public static int breedChanceSteak = 5;
	
	public static double breedDistanceX = 10.0;
	public static double breedDistanceY = 2.0;
	public static double breedDistanceZ = 10.0;
	
	public static String outputPluginTag = "[WolfHunt]";
	
	public static String configDirectory = "plugins/WolfHunt";
	public static String configFileName = "wolfhunt.config";
	public static String configDescription = "Configuration file for WolfHunt";
	public static String configFailed = "Failed to create the configuration file, using built-in config.";
	
}
