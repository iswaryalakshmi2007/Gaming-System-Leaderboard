package com.gaming.leaderboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gaming.database.DatabaseConnection;

public class Leaderboard {

    public void displayLeaderboard() {

        String sql =
                "SELECT p.id, p.name, MAX(g.score) AS highest_score " +
                "FROM players p " +
                "JOIN game_scores g ON p.id = g.player_id " +
                "GROUP BY p.id, p.name " +
                "ORDER BY highest_score DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n==========================================");
            System.out.println("              LEADERBOARD");
            System.out.println("==========================================");

            System.out.printf("%-8s %-8s %-20s %-10s%n",
                    "RANK", "ID", "PLAYER", "HIGH SCORE");

            System.out.println("------------------------------------------");

            int rank = 1;
            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.printf("%-8d %-8d %-20s %-10d%n",
                        rank,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("highest_score"));

                rank++;
            }

            if (!found) {
                System.out.println("No game scores available.");
            }

            System.out.println("==========================================");

        } catch (Exception e) {

            System.out.println("Unable to load leaderboard.");
            e.printStackTrace();
        }
    }
}