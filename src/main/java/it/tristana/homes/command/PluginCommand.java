package it.tristana.homes.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import it.tristana.commons.interfaces.database.UsersManager;
import it.tristana.homes.config.SettingsPlugin;
import it.tristana.homes.database.HomeUser;

public abstract class PluginCommand implements TabExecutor {

	protected UsersManager<HomeUser> usersManager;
	protected SettingsPlugin settings;

	public PluginCommand(UsersManager<HomeUser> usersManager, SettingsPlugin settings) {
		this.usersManager = usersManager;
		this.settings = settings;
	}

	@Override
	public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player player)) {
			return true;
		}

		onCommand(usersManager.getUser(player), args);
		return true;
	}

	@Override
	public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player player)) {
			return new ArrayList<>();
		}

		return args.length == 0 ? null : getHomesFor(usersManager.getUser(player), args[0]);
	}

	protected List<String> getHomesFor(HomeUser user, String start) {
		return StringUtil.copyPartialMatches(start, user.getHomes().keySet(), new ArrayList<>());
	}

	protected abstract void onCommand(HomeUser user, String[] args);
}
