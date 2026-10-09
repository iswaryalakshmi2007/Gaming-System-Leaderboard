package com.gaming.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gaming.database.DatabaseConnection;
import com.gaming.model.Player;

public class PlayerDAO {

    // 1. Register Player
    public void addPlayer(Player player) {

        String sql = "INSERT INTO players(name, score) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, player.getName());
            ps.setInt(2, player.getScore());

            ps.executeUpdate();

            System.out.println("Player Registered Successfully!");

        } catch (Exception e) {

            if (e.getMessage() != null &&
                e.getMessage().contains("Duplicate")) {

                System.out.println("Player name already exists!");

            } else {

                System.out.println("Unable to register player.");
                e.printStackTrace();
            }
        }
    }


    // 2. View All Players
    public void viewPlayers() {

        String sql = "SELECT * FROM players ORDER BY id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== PLAYER LIST ==========");

            System.out.printf("%-5s %-20s %-10s%n",
                    "ID", "PLAYER NAME", "SCORE");

            System.out.println("----------------------------------------");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.printf("%-5d %-20s %-10d%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("score"));
            }

            if (!found) {
                System.out.println("No players found.");
            }

            System.out.println("----------------------------------------");

        } catch (Exception e) {

            System.out.println("Unable to retrieve players.");
            e.printStackTrace();
        }
    }


    // 3. Manual Score Update
    public void updateScore(int id, int newScore) {

        String sql = "UPDATE players SET score = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newScore);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Score Updated Successfully!");
            } else {
                System.out.println("Player ID not found.");
            }

        } catch (Exception e) {

            System.out.println("Unable to update score.");
            e.printStackTrace();
        }
    }


    // 4. Search Player
    public void searchPlayer(String name) {

        String sql = "SELECT * FROM players WHERE name LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                System.out.println("\n========== SEARCH RESULT ==========");

                while (rs.next()) {

                    found = true;

                    System.out.println("ID    : " + rs.getInt("id"));
                    System.out.println("Name  : " + rs.getString("name"));
                    System.out.println("Score : " + rs.getInt("score"));
                    System.out.println("--------------------------------");
                }

                if (!found) {
                    System.out.println("Player not found.");
                }
            }

        } catch (Exception e) {

            System.out.println("Unable to search player.");
            e.printStackTrace();
        }
    }


    // 5. Delete Player
    public void deletePlayer(int id) {

        String sql = "DELETE FROM players WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Player Deleted Successfully!");
            } else {
                System.out.println("Player ID not found.");
            }

        } catch (Exception e) {

            System.out.println("Unable to delete player.");
            e.printStackTrace();
        }
    }


    // 6. Check Player ID
    public boolean playerExists(int id) {

        String sql = "SELECT id FROM players WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();
            }

        } catch (Exception e) {

            System.out.println("Unable to check player.");
            e.printStackTrace();

            return false;
        }
    }


    // 7. Update Only Highest Score
    public void updateHighestScore(int id, int newScore) {

        String sql =
                "UPDATE players SET score = ? " +
                "WHERE id = ? AND score < ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newScore);
            ps.setInt(2, id);
            ps.setInt(3, newScore);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("New Highest Score Updated!");
            } else {
                System.out.println(
                    "Previous highest score is greater or equal.");
            }

        } catch (Exception e) {

            System.out.println("Unable to update highest score.");
            e.printStackTrace();
        }
    }
}