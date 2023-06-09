package it.tristana.homes.command;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.database.UsersManager;
import it.tristana.homes.config.SettingsPlugin;
import it.tristana.homes.database.HomeUser;

public class HomesCommand extends PluginCommand {

	public HomesCommand(UsersManager<HomeUser> usersManager, SettingsPlugin settings) {
		super(usersManager, settings);
	}

	@Override
	protected void onCommand(HomeUser user, String[] args) {
		CommonsHelper.info(
				user.getOnlinePlayer(),
				CommonsHelper.replaceAll(
						settings.getHomesList(),
						"{homes}",
						CommonsHelper.playerListToString(
								user.getHomes().keySet(),
								settings.getNoneWord(),
								settings.getAndWord()
						)
				)
		);
	}
}
