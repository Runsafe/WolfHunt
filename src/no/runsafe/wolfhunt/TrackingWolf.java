package no.runsafe.wolfhunt;

import net.minecraft.server.v1_7_R2.*;
import org.bukkit.craftbukkit.v1_7_R2.util.UnsafeList;

import java.lang.reflect.Field;

public class TrackingWolf extends EntityWolf
{
	public TrackingWolf(World world)
	{
		super(world);

		try
		{
			Field gsa = PathfinderGoalSelector.class.getDeclaredField("b");
			gsa.setAccessible(true);
			gsa.set(this.goalSelector, new UnsafeList());
			gsa.set(this.targetSelector, new UnsafeList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.goalSelector.a(1, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
		this.goalSelector.a(1, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, this.bp);
		this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 1.0D, 10.0F, 2.0F));
		this.goalSelector.a(6, new PathfinderGoalBreed(this, 1.0D));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
		this.goalSelector.a(8, new PathfinderGoalBeg(this, 8.0F));
		this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityOcelot.class, 8.0F));
		this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityVillager.class, 8.0F));
		this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHorse.class, 8.0F));
		this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
		this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
		this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
		this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true));
		this.targetSelector.a(4, new PathfinderGoalRandomTargetNonTamed(this, EntitySheep.class, 200, false));
	}
}
