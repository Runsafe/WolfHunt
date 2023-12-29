package no.runsafe.wolfhunt.commands;

import no.runsafe.framework.api.command.argument.BooleanArgument;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.item.meta.RunsafePotion;
import no.runsafe.wolfhunt.Config;

public class GetBlood extends PlayerCommand
{
	public GetBlood()
	{
		super(
			"blood", "Gets a player's blood.", "runsafe.wolfhunt.blood",
			new Player().require(), new BooleanArgument("1.8+").withDefault(true)
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		IPlayer donor = parameters.getRequired("player");
		boolean uuidBottle = parameters.getRequired("1.8+");

		RunsafeMeta vial = Item.Brewing.Potion.getItem();
		((RunsafePotion) vial).giveCustomEffect(Buff.Healing.Instant);
		vial.setDisplayName("§3Vial of Blood");

		if (uuidBottle)
		{
			vial.addLore("§0 " + donor.getUniqueId());
			vial.addLore("§CTrack: " + donor.getName());
		}
		else vial.addLore("§7Track: " + donor.getName());

		executor.give(vial);

		return String.format(Config.Message.getCommandBloodObtained(), donor.getPrettyName());
	}
}
