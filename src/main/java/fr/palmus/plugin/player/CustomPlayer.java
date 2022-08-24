package fr.palmus.plugin.player;

import fr.palmus.plugin.EvoPlugin;
import fr.palmus.plugin.mysql.DBConnection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class CustomPlayer {
    private EvoPlugin main;

    private HashMap<Player, PlayerPeriod> plmList;

    private HashMap<UUID, String> playerCache;

    public CustomPlayer(EvoPlugin main){
        this.main = main;
        this.plmList = new HashMap<>();
        this.playerCache = new HashMap<>();
    }

    public void initPlayer(Player pl) throws IOException, SQLException {
        if(main.cfg.get(pl.getUniqueId() + ".period") == null) {
            updateCache(pl, null);
        }
        this.plmList.put(pl, new PlayerPeriod(pl, main.cfg.getInt(pl.getUniqueId() + ".exp")));
        final DBConnection database = main.getDatabaseManager().getDatabase();
        try {
            final Connection connection = database.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, period, exp, doubler, limiter, rank FROM player_data WHERE uuid = ?");

            preparedStatement.setString(1, pl.getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                updateCache(pl, resultSet);
            }else{
                createUserData(connection, pl);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public PlayerPeriod get(Player pl){
        return plmList.get(pl);
    }

    private void createUserData(Connection connection, Player pl){
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player_data VALUES ?, ?, ?, ?, ?, ?, ?, ?");
            final long time = System.currentTimeMillis();
            preparedStatement.setString(1, pl.getUniqueId().toString());
            preparedStatement.setInt(2, main.cfg.getInt(pl.getUniqueId() + ".period"));
            preparedStatement.setString(3, String.valueOf(main.cfg.getInt(pl.getUniqueId() + ".exp")));
            preparedStatement.setInt(4, main.cfg.getInt(pl.getUniqueId() + ".doubler"));
            preparedStatement.setInt(5, main.cfg.getInt(pl.getUniqueId() + ".limiter"));
            preparedStatement.setInt(6, main.cfg.getInt(pl.getUniqueId() + ".rank"));
            preparedStatement.setTimestamp(7, new Timestamp(time));
            preparedStatement.setTimestamp(8, new Timestamp(time));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<UUID, String> getPlayerCache() {
        return playerCache;
    }

    public void updateCache(Player pl, ResultSet set) throws IOException, SQLException {
        if(set == null) {
            main.cfg.set(pl.getUniqueId() + ".period", 0);
            main.cfg.set(pl.getUniqueId() + ".exp", 0);
            main.cfg.set(pl.getUniqueId() + ".doubler", 0);
            main.cfg.set(pl.getUniqueId() + ".limiter", 0);
            main.cfg.set(pl.getUniqueId() + ".rank", 1);
        }else{
            main.cfg.set(pl.getUniqueId() + ".period", set.getInt("period"));
            main.cfg.set(pl.getUniqueId() + ".exp", Integer.parseInt(set.getString("exp")));
            main.cfg.set(pl.getUniqueId() + ".doubler", set.getInt("doubler"));
            main.cfg.set(pl.getUniqueId() + ".limiter", set.getInt("limiter"));
            main.cfg.set(pl.getUniqueId() + ".rank", set.getInt("rank"));
        }
        main.cfg.save(main.file);
    }

    public void saveData(Player pl, Connection connection){
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player_data VALUES ?, ?, ?, ?, ?, ?, ?, ?");
            final long time = System.currentTimeMillis();
            preparedStatement.setInt(2, main.cfg.getInt(pl.getUniqueId() + ".period"));
            preparedStatement.setString(3, String.valueOf(main.cfg.getInt(pl.getUniqueId() + ".exp")));
            preparedStatement.setInt(4, main.cfg.getInt(pl.getUniqueId() + ".doubler"));
            preparedStatement.setInt(5, main.cfg.getInt(pl.getUniqueId() + ".limiter"));
            preparedStatement.setInt(6, main.cfg.getInt(pl.getUniqueId() + ".rank"));
            preparedStatement.setTimestamp(8, new Timestamp(time));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
