package it.tristana.homes.command;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.database.UsersManager;
import it.tristana.homes.config.SettingsPlugin;
import it.tristana.homes.database.HomeUser;

public class SetHomeCommand extends PluginCommand {

	public SetHomeCommand(UsersManager<HomeUser> usersManager, SettingsPlugin settings) {
		super(usersManager, settings);
	}

	@Override
	protected void onCommand(HomeUser user, String[] args) {
		Player player = user.getOnlinePlayer();
		Location pos = player.getLocation();
		if (args.length == 0) {
			user.setDefaultHome(pos);
		} else {
			user.setHome(args[0], pos);
		}
		CommonsHelper.info(player, settings.getHomeCreated());
	}
}
