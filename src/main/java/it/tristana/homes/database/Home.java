package it.tristana.homes.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public record Home(String world, double x, double y, double z, float yaw, float pitch) {

	public Home(Location location) {
		this(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	public Location toLocation() {
		World world = Bukkit.getWorld(this.world);
		if (world == null) {
			return null;
		}

		return new Location(world, x, y, z, yaw, pitch);
	}
}
