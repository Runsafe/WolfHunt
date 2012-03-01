package me.Kruithne.WolfHunt;

public class Configuration {
	
	WolfHunt wolfHuntPlugin = null;
	
	public int trackingItem;
	
	Configuration(WolfHunt parentPlugin)
	{
		this.wolfHuntPlugin = parentPlugin;
	}

	public String getConfigValue(String configKey, String defaultValue)
	{	
		if (this.wolfHuntPlugin.getConfig().contains(String.format(Constants.pluginNodePath, configKey)))
		{
			return this.wolfHuntPlugin.getConfig().getString(String.format(Constants.pluginNodePath, configKey));
		}
		
		return defaultValue;
	}
	
	public void setConfigValue(String configKey, String configValue)
	{
		this.wolfHuntPlugin.getConfig().set(configKey, configValue);
		this.wolfHuntPlugin.saveConfig();
	}
	
	public void loadConfiguration()
	{
		this.trackingItem = Integer.parseInt(this.getConfigValue("trackingItem", Constants.default_trackingItem));
	}
	
}
