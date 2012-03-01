package me.Kruithne.WolfHunt;

import org.bukkit.event.Listener;

public class WolfHuntPlayerListener implements Listener
{
	WolfHunt wolfHuntPlugin = null;
	
	WolfHuntPlayerListener(WolfHunt parentPlugin)
	{
		this.wolfHuntPlugin = parentPlugin;
		this.wolfHuntPlugin.server.getPluginManager().registerEvents(this, this.wolfHuntPlugin);
	}
}
