package it.tristana.homes.config;

import java.io.File;

import it.tristana.commons.config.ConfigDatabase;

public class ConfigHomeDatabase extends ConfigDatabase {

	public static final String TABLE_PLAYERS = "table-players";
	
	public ConfigHomeDatabase(File folder) {
		super(new File(folder, "database.yml"));
	}

	@Override
	protected void createDefault() {
		super.createDefault();
		set(TABLE_PLAYERS, "home_players");
	}
}
