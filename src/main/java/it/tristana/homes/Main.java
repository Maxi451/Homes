package it.tristana.homes;

import java.io.File;

import org.bukkit.Bukkit;

import it.tristana.commons.arena.Clock;
import it.tristana.commons.database.BasicUsersManager;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.commons.helper.PluginDraft;
import it.tristana.commons.interfaces.DatabaseHolder;
import it.tristana.commons.interfaces.Reloadable;
import it.tristana.commons.interfaces.database.Database;
import it.tristana.commons.interfaces.database.UsersManager;
import it.tristana.commons.listener.LoginQuitListener;
import it.tristana.homes.command.AdminHomeCommand;
import it.tristana.homes.command.DelHomeCommand;
import it.tristana.homes.command.HomeCommand;
import it.tristana.homes.command.HomesCommand;
import it.tristana.homes.command.SetHomeCommand;
import it.tristana.homes.config.ConfigHomeDatabase;
import it.tristana.homes.config.ConfigPlugin;
import it.tristana.homes.config.SettingsPlugin;
import it.tristana.homes.database.HomeDatabase;
import it.tristana.homes.database.HomeUser;

public class Main extends PluginDraft implements Reloadable, DatabaseHolder {

	private static final int AUTOSAVE_INTERVAL = 20 * 60 * 30;

	private File folder;
	private boolean isDisabled;

	private HomeDatabase database;
	private UsersManager<HomeUser> usersManager;
	private Clock autosaver;

	private SettingsPlugin settingsPlugin;

	@Override
	public void onEnable() {
		folder = getFolder();
		try {
			database = createDatabase();
			database.closeConnection(database.openConnection());
		} catch (Exception e) {
			CommonsHelper.consoleInfo("&cCan't open the connection to the database! Check its configuration, plugin disabled");
			writeThrowableOnErrorsFile(e);
			Bukkit.getPluginManager().disablePlugin(this);
			isDisabled = true;
			return;
		}

		setupConfigs();
		setupManagers();
		registerListeners();
		Bukkit.getPluginCommand("home").setExecutor(new HomeCommand(usersManager, settingsPlugin));
		Bukkit.getPluginCommand("homes").setExecutor(new HomesCommand(usersManager, settingsPlugin));
		Bukkit.getPluginCommand("sethome").setExecutor(new SetHomeCommand(usersManager, settingsPlugin));
		Bukkit.getPluginCommand("delhome").setExecutor(new DelHomeCommand(usersManager, settingsPlugin));
		registerCommand(this, AdminHomeCommand.class, "adminhome", ConfigPlugin.FILE_NAME);
	}

	@Override
	public void onDisable() {
		if (isDisabled) {
			return;
		}

		autosaver.cancel();
		usersManager.saveOnlineUsers();
	}

	@Override
	public Database getStorage() {
		return database;
	}

	@Override
	public void reload() {
		settingsPlugin.reload();
	}

	private void setupConfigs() {
		settingsPlugin = new SettingsPlugin(folder);
	}

	private void setupManagers() {
		usersManager = new BasicUsersManager<>(database);
		autosaver = new Clock();
		autosaver.add(usersManager::saveOnlineUsers);
		autosaver.schedule(this, AUTOSAVE_INTERVAL);
	}

	private void registerListeners() {
		register(new LoginQuitListener<>(usersManager, database));
	}

	private HomeDatabase createDatabase() {
		ConfigHomeDatabase config = new ConfigHomeDatabase(folder);
		String host = config.getString(ConfigHomeDatabase.HOST);
		String database = config.getString(ConfigHomeDatabase.DATABASE);
		String username = config.getString(ConfigHomeDatabase.USER);
		String password = config.getString(ConfigHomeDatabase.PASSWORD);
		int port = Integer.parseInt(config.getString(ConfigHomeDatabase.PORT));
		String tablePlayers = config.getString(ConfigHomeDatabase.TABLE_PLAYERS);
		return new HomeDatabase(host, database, username, password, port, this, tablePlayers);
	}
}
