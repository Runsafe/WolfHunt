package no.runsafe.wolfhunt;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.player.IPlayer;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;

public class Tracking implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		preventTrackingOps = configuration.getConfigValueAsBoolean("preventTrackingOps");
		trackingRadius = configuration.getConfigValueAsDouble("trackingRadius");
	}

	public String trackPlayersRelativeTo(IPlayer player)
	{
		ILocation origin = player.getLocation();
		IWorld world = origin.getWorld();
		Iterator<IPlayer> players = world.getPlayers().iterator();
		return getTrackerResult(players, origin, player);
	}

	private String getTrackerResult(Iterator<IPlayer> players, ILocation origin, IPlayer originPlayer)
	{
		Hashtable<Double, IPlayer> distances = new Hashtable<Double, IPlayer>();

		while (players.hasNext())
		{
			IPlayer checkPlayer = players.next();
			if (canTrackPlayer(originPlayer, checkPlayer))
			{
				double theDistance = origin.distance(checkPlayer.getLocation());
				if (theDistance < trackingRadius)
					return Constants.messageNearby;

				distances.put(origin.distance(checkPlayer.getLocation()), checkPlayer);
			}
		}

		if (distances.size() == 0)
			return Constants.messageNoPlayers;

		return String.format(Constants.messageDetected, this.getCompassDirection(origin, distances.get(Collections.min(distances.keySet())).getLocation()));
	}

	private boolean canTrackPlayer(IPlayer tracker, IPlayer tracked)
	{
		if (tracker == tracked)
			return false;
		else if (preventTrackingOps && tracked.isOP())
			return false;
		return true;
	}

	private String getCompassDirection(ILocation a, ILocation b)
	{
		double v = a.getX() - b.getX();
		double h = a.getZ() - b.getZ();

		String hDir = h < 0 ? Constants.directionWest : Constants.directionEast;
		String vDir = v < 0 ? Constants.directionSouth : Constants.directionNorth;

		double angle = Math.asin(Math.abs(v) / a.distance(b));

		if (angle <= 0.3927)
			return hDir;
		else if (angle >= 1.1781)
			return vDir;
		else
			return vDir + "-" + hDir;
	}

	private boolean preventTrackingOps;
	private double trackingRadius;
}
