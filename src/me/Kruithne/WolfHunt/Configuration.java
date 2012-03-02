package me.Kruithne.WolfHunt;

public class Configuration {
	
	private WolfHunt wolfHuntPlugin = null;
	
	public int trackingItem;
	public int trackingRadius;
	public boolean babyWolvesCanTrack;
	public boolean preventTrackingOps;
	public boolean allowOpOverride;
	public boolean enableVanishNoPacketSupport;
	
	Configuration(WolfHunt plugin)
	{
		this.wolfHuntPlugin = plugin;
	}
	
	public void loadConfiguration()
	{
		this.trackingItem = this.loadInteger("trackingItem", Constants.default_trackingItem);
		this.trackingRadius = this.loadInteger("trackingRadius", Constants.default_trackingRadius);
		this.allowOpOverride = this.loadBoolean("allowOpOverride", Constants.default_allowOpOverride);
		this.preventTrackingOps = this.loadBoolean("preventTrackingOps", Constants.default_preventTrackingOps);
		this.babyWolvesCanTrack = this.loadBoolean("babyWolvesCanTrack", Constants.default_babyWolvesCanTrack);
		this.enableVanishNoPacketSupport = loadBoolean("enableVanishNoPacketSupport", Constants.default_enableVanishNoPacketSupport);
	}

	private Integer loadInteger(String key, String defaultValue)
	{
		return Integer.parseInt(this.getOrAddConfigValue(key, defaultValue));
	}

	private Boolean loadBoolean(String key, String defaultValue)
	{
		return Boolean.parseBoolean(this.getOrAddConfigValue(key, defaultValue));
	}

	public String getOrAddConfigValue(String configKey, String defaultValue)
	{	
		if (this.hasConfigValue(configKey))
		{
			return this.getConfigValue(configKey);
		}
		else
		{
			this.setConfigValue(configKey, defaultValue);
			return defaultValue;
		}
	}
	
	public String getConfigValue(String configKey)
	{
		return this.wolfHuntPlugin.getConfig().getString(String.format(Constants.pluginNodePath, configKey));
	}
	
	public boolean hasConfigValue(String configKey)
	{
		return this.wolfHuntPlugin.getConfig().contains(String.format(Constants.pluginNodePath, configKey));
	}
	
	public void setConfigValue(String configKey, String configValue)
	{
		this.wolfHuntPlugin.getConfig().set(String.format(Constants.pluginNodePath, configKey), configValue);
		this.wolfHuntPlugin.saveConfig();
	}
}
