package it.tristana.homes.config;

import java.io.File;

import it.tristana.commons.config.Config;

public class ConfigPlugin extends Config {

	public static final String FILE_NAME = "plugin.yml";
	
	public static final String HOME_NOT_FOUND = "home-not-found";
	public static final String HOME_DELETED = "home-deleted";
	public static final String HOME_CREATED = "home-created";
	public static final String HOME_TELEPORTED_TO = "home-created";
	public static final String HOMES_LIST = "homes-list";
	public static final String AND_WORD = "and-word";
	public static final String NONE_WORD = "none-word";

	public ConfigPlugin(File folder) {
		super(new File(folder, FILE_NAME));
	}

	@Override
	protected void createDefault() {
		set(HOME_NOT_FOUND, "&cHome not found");
		set(HOME_DELETED, "Home &cdeleted");
		set(HOME_CREATED, "Home &acreated");
		set(HOME_TELEPORTED_TO, "&aWhoosh!");
		set(HOMES_LIST, "Homes: {homes}");
		set(AND_WORD, "and");
		set(NONE_WORD, "none");
	}
}
