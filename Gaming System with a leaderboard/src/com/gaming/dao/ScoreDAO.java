package com.gaming.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gaming.database.DatabaseConnection;

public class ScoreDAO {

    // Save a new game score
    public void addScore(int playerId, int score) {

        String sql = "INSERT INTO game_scores(player_id, score) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playerId);
            ps.setInt(2, score);

            ps.executeUpdate();

            System.out.println("Game score saved successfully!");

        } catch (Exception e) {

            System.out.println("Unable to save game score.");
            e.printStackTrace();
        }
    }


    // View score history of a player
    public void viewPlayerScores(int playerId) {

        String sql = "SELECT score_id, score, played_at " +
                     "FROM game_scores " +
                     "WHERE player_id = ? " +
                     "ORDER BY played_at DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, playerId);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println("\n========== GAME SCORE HISTORY ==========");

                System.out.printf("%-10s %-10s %-25s%n",
                        "SCORE ID", "SCORE", "PLAYED AT");

                System.out.println("-----------------------------------------------");

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.printf("%-10d %-10d %-25s%n",
                            rs.getInt("score_id"),
                            rs.getInt("score"),
                            rs.getTimestamp("played_at"));
                }

                if (!found) {
                    System.out.println("No game scores found.");
                }

                System.out.println("-----------------------------------------------");
            }

        } catch (Exception e) {

            System.out.println("Unable to retrieve score history.");
            e.printStackTrace();
        }
    }
}