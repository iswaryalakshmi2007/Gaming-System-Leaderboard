package com.gaming.main;

import java.util.Scanner;

import com.gaming.dao.PlayerDAO;
import com.gaming.dao.ScoreDAO;
import com.gaming.leaderboard.Leaderboard;
import com.gaming.model.Player;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PlayerDAO playerDAO = new PlayerDAO();
        Leaderboard leaderboard = new Leaderboard();
        ScoreDAO scoreDAO = new ScoreDAO();

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("          GAMING SYSTEM");
            System.out.println("       LEADERBOARD MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. Register Player");
            System.out.println("2. View All Players");
            System.out.println("3. Update Player Score");
            System.out.println("4. View Leaderboard");
            System.out.println("5. Search Player");
            System.out.println("6. Delete Player");
            System.out.println("7. Play Game");
            System.out.println("8. View Score History");
            System.out.println("9. Exit");
            System.out.println("=================================");
            System.out.print("Enter Choice : ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // --------------------------------
                // 1. REGISTER PLAYER
                // --------------------------------
                case 1:

                    System.out.print("Enter Player Name : ");
                    String name = sc.nextLine().trim();

                    if (name.isEmpty()) {
                        System.out.println(
                                "Player name cannot be empty.");
                        break;
                    }

                    Player player = new Player(name, 0);

                    playerDAO.addPlayer(player);

                    break;


                // --------------------------------
                // 2. VIEW ALL PLAYERS
                // --------------------------------
                case 2:

                    playerDAO.viewPlayers();

                    break;


                // --------------------------------
                // 3. UPDATE PLAYER SCORE
                // --------------------------------
                case 3:

                    System.out.print("Enter Player ID : ");
                    int id = sc.nextInt();

                    if (!playerDAO.playerExists(id)) {
                        System.out.println("Player ID not found!");
                        break;
                    }

                    System.out.print("Enter New Score : ");
                    int score = sc.nextInt();

                    if (score < 0) {
                        System.out.println(
                                "Score cannot be negative.");
                        break;
                    }

                    playerDAO.updateScore(id, score);

                    break;


                // --------------------------------
                // 4. VIEW LEADERBOARD
                // --------------------------------
                case 4:

                    leaderboard.displayLeaderboard();

                    break;


                // --------------------------------
                // 5. SEARCH PLAYER
                // --------------------------------
                case 5:

                    System.out.print(
                            "Enter Player Name to Search : ");

                    String searchName = sc.nextLine().trim();

                    if (searchName.isEmpty()) {
                        System.out.println(
                                "Search name cannot be empty.");
                        break;
                    }

                    playerDAO.searchPlayer(searchName);

                    break;


                // --------------------------------
                // 6. DELETE PLAYER
                // --------------------------------
                case 6:

                    System.out.print(
                            "Enter Player ID to Delete : ");

                    int deleteId = sc.nextInt();

                    if (!playerDAO.playerExists(deleteId)) {
                        System.out.println("Player ID not found!");
                        break;
                    }

                    playerDAO.deletePlayer(deleteId);

                    break;


                // --------------------------------
                // 7. PLAY GAME
                // --------------------------------
                case 7:

                    System.out.print("Enter Player ID : ");
                    int playerId = sc.nextInt();

                    // Check player exists
                    if (!playerDAO.playerExists(playerId)) {

                        System.out.println(
                                "Player ID not found!");

                        break;
                    }

                    System.out.print("Enter Game Score : ");
                    int gameScore = sc.nextInt();

                    // Check negative score
                    if (gameScore < 0) {

                        System.out.println(
                                "Score cannot be negative.");

                        break;
                    }

                    // Save game score history
                    scoreDAO.addScore(playerId, gameScore);

                    // Update only if this is the highest score
                    playerDAO.updateHighestScore(
                            playerId, gameScore);

                    break;


                // --------------------------------
                // 8. SCORE HISTORY
                // --------------------------------
                case 8:

                    System.out.print("Enter Player ID : ");

                    int historyPlayerId = sc.nextInt();

                    if (!playerDAO.playerExists(
                            historyPlayerId)) {

                        System.out.println(
                                "Player ID not found!");

                        break;
                    }

                    scoreDAO.viewPlayerScores(
                            historyPlayerId);

                    break;


                // --------------------------------
                // 9. EXIT
                // --------------------------------
                case 9:

                    System.out.println(
                            "\nThank you for using Gaming System!");

                    break;


                // --------------------------------
                // INVALID CHOICE
                // --------------------------------
                default:

                    System.out.println(
                            "Invalid Choice. Please try again.");
            }

        } while (choice != 9);

        sc.close();
    }
}