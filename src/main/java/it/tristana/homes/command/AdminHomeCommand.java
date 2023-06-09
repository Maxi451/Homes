package it.tristana.homes.command;

import it.tristana.commons.command.MainCommand;
import it.tristana.commons.config.SettingsDefaultCommands;
import it.tristana.homes.Main;

public class AdminHomeCommand extends MainCommand<Main> {

	public AdminHomeCommand(Main plugin, SettingsDefaultCommands settings, String command) {
		super(plugin, settings, command);
	}

	@Override
	protected String getAdminPerms() {
		return "homes.admin";
	}
}
