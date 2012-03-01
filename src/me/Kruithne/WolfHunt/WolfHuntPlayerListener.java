package me.Kruithne.WolfHunt;

//import org.bukkit.Material;
//import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class WolfHuntPlayerListener implements Listener
{
	WolfHunt wolfHuntPlugin = null;
	
	WolfHuntPlayerListener(WolfHunt parentPlugin)
	{
		this.wolfHuntPlugin = parentPlugin;
		this.wolfHuntPlugin.server.getPluginManager().registerEvents(this, this.wolfHuntPlugin);
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		//Player eventPlayer = event.getPlayer();
		//if (eventPlayer.getItemInHand() == (ItemStack) Material.getMaterial(Constants.trackingItem))
	}
}
