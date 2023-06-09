package it.tristana.homes.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import it.tristana.commons.database.DatabaseManager;
import it.tristana.commons.helper.CommonsHelper;
import it.tristana.homes.Main;

public class HomeDatabase extends DatabaseManager<HomeUser> {

	private final Main plugin;
	private final String tableHomes;

	public HomeDatabase(String host, String database, String username, String password, int port, Main plugin, String tableHomes) {
		super(host, database, username, password, port);
		this.plugin = plugin;
		this.tableHomes = tableHomes;
	}

	@Override
	public HomeUser getUser(OfflinePlayer player) {
		HomeUser user = new HomeUser(player);
		executeQueryAsync(String.format("SELECT name, world, pos_x, pos_y, pos_z, yaw, pitch FROM %s WHERE UUID = '%s';", tableHomes, getUuid(player)), resultSet -> {
			loadUser(user, resultSet);
		}, onError -> {
			CommonsHelper.consoleInfo(String.format("&cError while loading %s's (%s) data!", player.getName(), getUuid(player)));
			plugin.writeThrowableOnErrorsFile(onError);
		});
		return user;
	}

	@Override
	public void saveUser(HomeUser user) {
		Player player = user.getOnlinePlayer();
		String uuid = getUuid(player);
		Map<String, Home> homes = user.getHomes();
		new Thread(() -> {
			homes.forEach((name, home) -> {
				try {
					Connection connection = openConnection();
					PreparedStatement statement = connection.prepareStatement("INSERT INTO %s (uuid, name, world, pos_x, pos_y, pos_z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
							+ " ON DUPLICATE KEY UPDATE uuid = ?, name = ?, world = ?, pos_x = ?, pos_y = ?, pos_z = ?, yaw = ?, pitch = ?;");
					setParams(statement, uuid, name, home, 0);
					setParams(statement, uuid, name, home, 8);
					statement.execute();
					statement.close();
				} catch (SQLException e) {
					CommonsHelper.consoleInfo(String.format("&cError while saving %s's (%s) data!", player.getName(), uuid));
					plugin.writeThrowableOnErrorsFile(e);
				}
			});
		}).start();
	}

	@Override
	public void createTables() throws SQLException {
		executeUpdate("CREATE TABLE IF NOT EXISTS " + tableHomes + " ("
				+ " uuid CHAR(36) NOT NULL,"
				+ " name VARCHAR(255) NOT NULL,"
				+ " world VARCHAR(255) NOT NULL,"
				+ " pos_x DECIMAL(8,3) NOT NULL,"
				+ " pos_y DECIMAL(3,3) NOT NULL,"
				+ " pos_z DECIMAL(8,3) NOT NULL,"
				+ " yaw DECIMAL(3,3) NOT NULL,"
				+ " pitch DECIMAL(3,3) NOT NULL,"
				+ " PRIMARY KEY(uuid, name)"
				+ ");");
	}

	private static void loadUser(HomeUser user, ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			String name = resultSet.getString("name");
			String world = resultSet.getString("world");
			double x = resultSet.getDouble("pos_x");
			double y = resultSet.getDouble("pos_y");
			double z = resultSet.getDouble("pos_z");
			float yaw = resultSet.getFloat("yaw");
			float pitch = resultSet.getFloat("pitch");
			user.setHome(name, new Home(world, x, y, z, yaw, pitch));
		}
		user.load();
	}

	private static void setParams(PreparedStatement statement, String uuid, String name, Home home, int offset) throws SQLException {
		statement.setString(offset + 1, uuid);
		statement.setString(offset + 2, name);
		statement.setString(offset + 3, home.world());
		statement.setDouble(offset + 4, home.x());
		statement.setDouble(offset + 5, home.y());
		statement.setDouble(offset + 6, home.z());
		statement.setFloat(offset + 7, home.yaw());
		statement.setFloat(offset + 8, home.pitch());
	}
}
