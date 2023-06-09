package it.tristana.homes.command;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.interfaces.database.UsersManager;
import it.tristana.homes.config.SettingsPlugin;
import it.tristana.homes.database.HomeUser;

public class HomeCommand extends PluginCommand {

	public HomeCommand(UsersManager<HomeUser> usersManager, SettingsPlugin settings) {
		super(usersManager, settings);
	}

	@Override
	protected void onCommand(HomeUser user, String[] args) {
		Location home = args.length == 0 ? user.getDefaultHome() : user.getHome(args[0]);
		Player player = user.getOnlinePlayer();
		if (home == null) {
			CommonsHelper.info(user.getOnlinePlayer(), settings.getHomeNotFound());
			return;
		}

		player.teleport(home);
		CommonsHelper.info(player, settings.getHomeTeleportedTo());
	}
}
