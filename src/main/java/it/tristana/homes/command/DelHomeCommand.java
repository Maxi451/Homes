package it.tristana.homes.command;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.database.UsersManager;
import it.tristana.homes.config.SettingsPlugin;
import it.tristana.homes.database.HomeUser;

public class DelHomeCommand extends PluginCommand {

	public DelHomeCommand(UsersManager<HomeUser> usersManager, SettingsPlugin settings) {
		super(usersManager, settings);
	}

	@Override
	protected void onCommand(HomeUser user, String[] args) {
		CommonsHelper.info(user.getOnlinePlayer(), (args.length == 0 ? user.delDefaultHome() : user.delHome(args[0])) ? settings.getHomeDeleted() : settings.getHomeNotFound());
	}
}
