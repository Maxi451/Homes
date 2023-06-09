package it.tristana.homes.database;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import it.tristana.commons.database.BasicUser;

public class HomeUser extends BasicUser {

	private static final String DEFAULT_HOME = "home";
	
	private final Map<String, Home> homes;
	private volatile boolean isLoaded;
	
	public HomeUser(OfflinePlayer player) {
		super(player);
		this.homes = new HashMap<>();
	}
	
	public void load() {
		isLoaded = true;
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	public Location getDefaultHome() {
		return getHome(DEFAULT_HOME);
	}
	
	public void setDefaultHome(Location home) {
		setHome(DEFAULT_HOME, home);
	}
	
	public Location getHome(String name) {
		return homes.get(name.toLowerCase()).toLocation();
	}
	
	public void setHome(String name, Location home) {
		setHome(name, new Home(home));
	}
	
	void setHome(String name, Home home) {
		homes.put(name.toLowerCase(), home);
	}
	
	public boolean delDefaultHome() {
		return delHome(DEFAULT_HOME);
	}
	
	public boolean delHome(String name) {
		return homes.remove(name) != null;
	}
	
	public Map<String, Home> getHomes() {
		return new HashMap<>(homes);
	}
}