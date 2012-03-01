package me.Kruithne.WolfHunt;

public class Configuration {
	
	WolfHunt wolfHuntPlugin = null;
	
	Configuration(WolfHunt parentPlugin)
	{
		this.wolfHuntPlugin = parentPlugin;
	}

	public String getConfigValue(String configKey)
	{	
		if (this.wolfHuntPlugin.getConfig().contains(String.format(Constants.configNodePath, configKey)))
		{
			return this.wolfHuntPlugin.getConfig().getString(String.format(Constants.configNodePath, configKey));
		}
		
		return null;
	}
	
	public void setConfigValue(String configKey, String configValue)
	{
		this.wolfHuntPlugin.getConfig().set(configKey, configValue);
		this.wolfHuntPlugin.saveConfig();
	}
	
}
