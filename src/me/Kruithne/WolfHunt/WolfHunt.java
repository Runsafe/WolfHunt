package me.Kruithne.WolfHunt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WolfHunt extends JavaPlugin {
	
	Server server;
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		this.server = this.getServer();
	}
	
	public void loadConfiguration()
	{
		File configFile = new File(Constants.configDirectory + File.separator + Constants.configFileName);
    	Properties settingsFile = new Properties();
    		new File(Constants.configDirectory).mkdir();
    		
    	if (!configFile.exists())
       	{
       		try {
       			configFile.createNewFile();
       			FileOutputStream out = new FileOutputStream(configFile);
       			
       			settingsFile.put("trackingRadius", Integer.toString(Constants.trackingRadius));
              	settingsFile.put("trackingItem", Integer.toString(Constants.trackingItem));
              	
              	settingsFile.put("enableBreeding", Boolean.toString(Constants.enableBreeding));
         		settingsFile.put("enableDebug", Boolean.toString(Constants.enabledDebug));
             	settingsFile.put("enableVanishNoPacketSupport", Boolean.toString(Constants.enableVanishNoPacketSupport));
              		
             	settingsFile.put("messageNoPlayers", Constants.messageNoPlayers);
             	settingsFile.put("messageDetected", Constants.messageDetected);
              	settingsFile.put("messageNearby", Constants.messageNearby);
               	settingsFile.put("messageBreed", Constants.messageBreed);
               	
               	settingsFile.put("directionNorth", Constants.directionNorth);
               	settingsFile.put("directionEast", Constants.directionEast);
               	settingsFile.put("directionSouth", Constants.directionSouth);
               	settingsFile.put("directionWest", Constants.directionWest);
               	settingsFile.put("directionUnknown", Constants.directionUnknown);
               	
               	settingsFile.put("breedChanceRawPorkchop", Integer.toString(Constants.breedChanceRawPorkchop));
               	settingsFile.put("breedChanceCookedPorkchop", Integer.toString(Constants.breedChanceCookedPorkchop));
               	settingsFile.put("breedChanceRawChicken", Integer.toString(Constants.breedChanceRawChicken));
               	settingsFile.put("breedChanceCookedChicken", Integer.toString(Constants.breedChanceCookedChicken));
               	settingsFile.put("breedChanceRawBeef", Integer.toString(Constants.breedChanceRawBeef));
               	settingsFile.put("breedChanceSteak", Integer.toString(Constants.breedChanceSteak));
               	
               	settingsFile.put("breedDistanceX", Double.toString(Constants.breedDistanceX));
               	settingsFile.put("breedDistanceY", Double.toString(Constants.breedDistanceY));
               	settingsFile.put("breedDistanceZ", Double.toString(Constants.breedDistanceZ));
               	
               	settingsFile.store(out, Constants.configDescription);
           	}
            catch (IOException ex)
            {
            	this.outputConsoleMessage(Constants.configFailed, Level.WARNING);
            }
       	}
       	else
       	{
       		try {
       			FileInputStream in = new FileInputStream(configFile);
    	        try {
    	        	settingsFile.load(in);
    	          			
    	        	directionNorth = settingsFile.getProperty("directionNorth");
    	          	directionEast = settingsFile.getProperty("directionEast");
    	          	directionSouth = settingsFile.getProperty("directionSouth");
    	          	directionWest = settingsFile.getProperty("directionWest");
    	           	directionUnknown = settingsFile.getProperty("directionUnknown");
    	           	
    	           	trackingRadius = Integer.parseInt(settingsFile.getProperty("trackingRadius"));
    	           	trackingItem = Integer.parseInt(settingsFile.getProperty("trackingItem"));
    	           	enableCowLevel = Boolean.parseBoolean(settingsFile.getProperty("enableCowLevel"));
    	           	enableBreeding = Boolean.parseBoolean(settingsFile.getProperty("enableBreeding"));
    	           	enableDebug = Boolean.parseBoolean(settingsFile.getProperty("enableDebug"));
    	           	enableVanishNoPacketSupport = Boolean.parseBoolean(settingsFile.getProperty("enableVanishNoPacketSupport"));
    	           	
    	           	messageNoPlayers = settingsFile.getProperty("messageNoPlayers");
    	           	messageDetected = settingsFile.getProperty("messageDetected");
    	           	messageNearby = settingsFile.getProperty("messageNearby");
    	           	messageCowLevel = settingsFile.getProperty("messageCowlLevel");
    	           	messageBreed = settingsFile.getProperty("messageBreed");
    	           	
    	           	breedChanceRawPorkchop = Integer.parseInt(settingsFile.getProperty("breedChanceRawPorkchop"));
    	           	breedChanceCookedPorkchop = Integer.parseInt(settingsFile.getProperty("breedChanceCookedPorkchop"));
    	           	breedChanceRawChicken = Integer.parseInt(settingsFile.getProperty("breedChanceRawChicken"));
    	           	breedChanceCookedChicken = Integer.parseInt(settingsFile.getProperty("breedChanceCookedChicken"));
    	           	breedChanceRawBeef = Integer.parseInt(settingsFile.getProperty("breedChanceRawBeef"));
    	           	breedChanceSteak = Integer.parseInt(settingsFile.getProperty("breedChanceSteak"));
    	           		
    	           	breedDistanceX = Double.parseDouble(settingsFile.getProperty("breedDistanceX"));
    	           	breedDistanceY = Double.parseDouble(settingsFile.getProperty("breedDistanceY"));
    	           	breedDistanceZ = Double.parseDouble(settingsFile.getProperty("breedDistanceZ"));
    	        }
    	        catch (IOException ex)
    	        {
    	           	this.output("Failed to load the configuration file");
    	        }
    	    }
    	    catch (FileNotFoundException ex)
    	    {
    	      	this.output("Failed to load the configuration file");
    	    }
       	}
	}
	
	public void outputConsoleMessage(String message, Level outputType)
	{
		this.log.log(outputType, String.format("%s: %s", Constants.outputPluginTag, message));
	}
	
	public void outputMessageToPlayer(String message, Player player)
	{
		player.sendMessage(message);
	}

}
