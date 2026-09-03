package foodchain.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    
    private static final String LOG_FILE = "log.txt";
    /**
     * This private helper method handles the actual writing to the disk
     * @param message
     */
    private static void writeToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("HH:mm:ss");
            String times = "[" + dateFormater.format(LocalDateTime.now()) + "] ";
            
            writer.write(times + message);
            writer.newLine();
            
            
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Records the configuration parameters at the start of a new game
     * @param era
     * @param totalRounds
     * @param boardSize
     */
    public static void logSetup(String era, int totalRounds, int boardSize) {
        writeToFile("==========================================");
        writeToFile(" NEW GAME SESSION STARTED");
        writeToFile(" Era: " + era);
        writeToFile(" Total Rounds: " + totalRounds);
        writeToFile(" Grid Size: " + boardSize + "x" + boardSize);
        writeToFile("==========================================");
    }
    /**
     * Logs the beginning and termination of each round.
     * @param round
     */
    public static void logStart(int round) {
        writeToFile(">>> Round " + round + " has begun.");
    }
    /**
     * Logs the termination of the current round and appends an empty line to create a visual break in the text file.
     * @param round
     */
    public static void logEnd(int round) {
        writeToFile("<<< Round " + round + " terminated.");
        writeToFile(""); 
    }
    /**
     * Formats movement data into a standardized string
     * @param actorName
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     */
    public static void logMove(String actorName, int startX, int startY, int endX, int endY) {
        writeToFile("[MOVE] " + actorName + " moved from (" + startX + "," + startY + ") to (" + endX + "," + endY + ")");
    }
    /**
     * Records updates to an entity's score.
     * @param actorName
     * @param points
     * @param reason
     */
    public static void logScore(String actorName, int points, String reason) {
        String action = (points >= 0) ? "GAINED" : "LOST";
        writeToFile("[SCORE] " + actorName + " " + action + " " + Math.abs(points) + " points. (Reason: " + reason + ")");
    }
    /**
     * Records the final outcome of the simulation, including the winner's name and score, followed by a closing footer.
     * @param winnerInfo
     */
    public static void logWinner(String winnerInfo) {
        writeToFile("******************************************");
        writeToFile(" WINNER(S): " + winnerInfo);
        writeToFile("******************************************");
    }
    /**
     * Writes a simple "GAME OVER" footer to denote the absolute end of the simulation logic.
     */
    public static void logGameOver() {
        writeToFile("=== GAME OVER ===");
    }
    
    
    /**
     * A public wrapper allowing other classes to write arbitrary debug messages to the log file without worrying about formatting or file handling.
     * @param msg
     */
    public static void log(String msg) {
        writeToFile(msg);
    }
}
