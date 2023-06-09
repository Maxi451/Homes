package it.tristana.homes.config;

import java.io.File;

import it.tristana.commons.config.Settings;

public class SettingsPlugin extends Settings<ConfigPlugin> {

	private String homeNotFound;
	private String homeDeleted;
	private String homeCreated;
	private String homeTeleportedTo;
	private String homesList;
	private String andWord;
	private String noneWord;

	public SettingsPlugin(File folder) {
		super(folder, ConfigPlugin.class);
	}

	@Override
	protected void reload(ConfigPlugin config) {
		homeNotFound = config.getString(ConfigPlugin.HOME_NOT_FOUND);
		homeDeleted = config.getString(ConfigPlugin.HOME_DELETED);
		homeCreated = config.getString(ConfigPlugin.HOME_CREATED);
		homeTeleportedTo = config.getString(ConfigPlugin.HOME_TELEPORTED_TO);
		homesList = config.getString(ConfigPlugin.HOMES_LIST);
		andWord = config.getString(ConfigPlugin.AND_WORD);
		noneWord = config.getString(ConfigPlugin.NONE_WORD);
	}

	public String getHomesList() {
		return homesList;
	}

	public String getAndWord() {
		return andWord;
	}

	public String getNoneWord() {
		return noneWord;
	}

	public String getHomeNotFound() {
		return homeNotFound;
	}

	public String getHomeDeleted() {
		return homeDeleted;
	}

	public String getHomeCreated() {
		return homeCreated;
	}

	public String getHomeTeleportedTo() {
		return homeTeleportedTo;
	}
}
