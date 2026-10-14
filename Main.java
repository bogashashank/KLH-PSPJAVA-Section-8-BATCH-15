/**
 * ============================================================================
 *  MAIN.JAVA
 * ----------------------------------------------------------------------------
 *  The ENTRY POINT of the "Music Playlist & Listening Stats Manager".
 *
 *  What this class does:
 *    1. Loads saved songs from the text file "music_data.txt"
 *       (if the file does not exist yet, it loads 7 sample songs).
 *    2. Shows a menu again and again until the user chooses Exit.
 *    3. Calls the right method for every menu choice.
 *    4. Auto-saves the playlist to the text file whenever data changes,
 *       and once more before exiting.
 *
 *  HOW TO RUN (after compiling):
 *        java Main
 * ============================================================================
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // ---------------- GLOBAL OBJECTS USED BY THE WHOLE PROGRAM ----------------
    static final String DATA_FILE = "music_data.txt";   // name of the save file

    static Playlist playlist = new Playlist("My Playlist");   // holds the songs
    static StatsManager stats = new StatsManager(playlist);   // calculates statistics
    static Scanner scanner = new Scanner(System.in);          // reads keyboard input

    // =========================================================================
    //  MAIN METHOD - program starts here
    // =========================================================================
    public static void main(String[] args) {

        loadSongsFromFile();      // Step 1: bring back yesterday's data
        printWelcome();           // Step 2: hello message

        boolean running = true;   // Step 3: keep showing the menu until Exit

        while (running) {
            printMenu();
            int choice = readInt("  Enter your choice (1-8): ");

            switch (choice) {
                case 1: playlist.viewSongs();                    break;
                case 2: addSong();                               break;
                case 3: removeSong();                            break;
                case 4: playSong();                              break;
                case 5: searchSongsMenu();                       break;
                case 6: sortPlaylistMenu();                      break;
                case 7: stats.showStats();                       break;
                case 8: running = false;                         break;
                default:
                    System.out.println("\n  Invalid choice! Please enter a number from 1 to 8.");
            }
        }

        // Step 4: user chose Exit -> save everything one last time.
        saveSongsToFile();
        System.out.println("\n  All data saved to \"" + DATA_FILE + "\". Goodbye!");
    }

    // =========================================================================
    //  MENU OPTIONS (one method per menu choice - keeps main() tidy)
    // =========================================================================

    // ---- OPTION 2: Add a new song ----
    public static void addSong() {
        System.out.println("\n  ---------- ADD A NEW SONG ----------");
        String title  = readText("  Song title   : ");
        String artist = readText("  Artist name  : ");
        int minutes   = readInt  ("  Length - minutes part (e.g. 3) : ");
        int seconds   = readInt  ("  Length - seconds part (e.g. 53): ");

        // 3 minutes 53 seconds = 3*60 + 53 = 233 seconds
        int totalSeconds = (minutes * 60) + seconds;

        playlist.addSong(new Song(title, artist, totalSeconds, 0));
        saveSongsToFile();   // auto-save so no data is ever lost

        System.out.println("\n  >> \"" + title + "\" added to the playlist and saved!");
    }

    // ---- OPTION 3: Remove a song ----
    public static void removeSong() {
        playlist.viewSongs();   // show the list first so the user knows the names
        if (playlist.getSize() == 0) return;

        String title = readText("\n  Enter the TITLE of the song to remove: ");
        boolean removed = playlist.removeSong(title);

        if (removed) {
            saveSongsToFile();
            System.out.println("\n  >> \"" + title + "\" removed from the playlist.");
        } else {
            System.out.println("\n  >> No song with that title was found.");
        }
    }

    // ---- OPTION 4: Play a song (increases its play count) ----
    public static void playSong() {
        playlist.viewSongs();   // show songs so the user can pick a number
        if (playlist.getSize() == 0) return;

        int number = readInt("\n  Enter the song NUMBER to play: ");
        Song song = playlist.getSong(number - 1);   // menu numbers start at 1, indexes at 0

        if (song == null) {
            System.out.println("\n  >> Invalid song number!");
            return;
        }

        song.incrementPlayCount();
        saveSongsToFile();   // auto-save the new play count

        System.out.println("\n  *************************************");
        System.out.println("   NOW PLAYING : " + song.toString());
        System.out.println("  *************************************");
        System.out.println("  (Play count for \"" + song.getTitle() + "\" is now "
                + song.getPlayCount() + ")");
    }

    // ---- OPTION 5: Search ----
    public static void searchSongsMenu() {
        String query = readText("\n  Enter a song or artist name to search: ");
        ArrayList<Song> results = playlist.searchSongs(query);

        if (results.isEmpty()) {
            System.out.println("\n  >> No songs matched \"" + query + "\".");
        } else {
            System.out.println("\n  Found " + results.size() + " matching song(s):");
            for (Song s : results) {
                System.out.println("   * " + s.toString()
                        + "  [" + s.getPlayCount() + " plays]");
            }
        }
    }

    // ---- OPTION 6: Sort ----
    public static void sortPlaylistMenu() {
        System.out.println("\n  Sort the playlist by:");
        System.out.println("   1. Title (A to Z)");
        System.out.println("   2. Most played first");
        int option = readInt("  Choose 1 or 2: ");

        if (option == 1) {
            playlist.sortByTitle();
            System.out.println("\n  >> Sorted by title (A-Z).");
        } else if (option == 2) {
            playlist.sortByPlayCount();
            System.out.println("\n  >> Sorted by play count (most played first).");
        } else {
            System.out.println("\n  >> Invalid option, playlist not changed.");
            return;
        }
        playlist.viewSongs();   // show the sorted result
    }

    // =========================================================================
    //  FILE HANDLING - saving and loading the playlist
    // =========================================================================

    // Load songs from the text file at program start.
    public static void loadSongsFromFile() {
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            // First ever run -> no save file yet -> load sample songs.
            loadSampleSongs();
            saveSongsToFile();
            System.out.println("  First run detected: loaded 7 sample songs into your playlist.");
            return;
        }

        try {
            Scanner fileReader = new Scanner(file);
            int loaded = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (line.isEmpty()) continue;          // skip empty lines
                Song s = Song.fromFileString(line);
                if (s != null) {
                    playlist.addSong(s);
                    loaded++;
                }
            }
            fileReader.close();
            System.out.println("  Loaded " + loaded + " song(s) from \"" + DATA_FILE + "\".");
        } catch (IOException e) {
            System.out.println("  Could not read the save file: " + e.getMessage());
        }
    }

    // Write every song to the text file (one line per song).
    // Called automatically after every change = simple "auto-save".
    public static void saveSongsToFile() {
        try {
            FileWriter writer = new FileWriter(DATA_FILE);   // false = overwrite file
            for (Song s : playlist.getAllSongs()) {
                writer.write(s.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("  Could not save the file: " + e.getMessage());
        }
    }

    // Sample data so the program looks alive on the very first run.
    public static void loadSampleSongs() {
        playlist.addSong(new Song("Shape of You",     "Ed Sheeran",      234, 42));
        playlist.addSong(new Song("Blinding Lights",  "The Weeknd",      200, 35));
        playlist.addSong(new Song("Levitating",       "Dua Lipa",        203, 28));
        playlist.addSong(new Song("Believer",         "Imagine Dragons", 204, 51));
        playlist.addSong(new Song("Perfect",          "Ed Sheeran",      263, 19));
        playlist.addSong(new Song("Counting Stars",   "OneRepublic",     257, 23));
        playlist.addSong(new Song("Someone Like You", "Adele",           285, 15));
    }

    // =========================================================================
    //  SMALL HELPER METHODS - safe keyboard input
    // =========================================================================

    public static void printWelcome() {
        System.out.println("\n  ======================================================================");
        System.out.println("        WELCOME TO THE MUSIC PLAYLIST & LISTENING STATS MANAGER");
        System.out.println("  ======================================================================");
    }

    public static void printMenu() {
        System.out.println("\n  ======================= MAIN MENU =======================");
        System.out.println("   1. View All Songs");
        System.out.println("   2. Add a New Song");
        System.out.println("   3. Remove a Song");
        System.out.println("   4. Play a Song (updates play count)");
        System.out.println("   5. Search Songs");
        System.out.println("   6. Sort Playlist");
        System.out.println("   7. View Listening Stats");
        System.out.println("   8. Exit (auto-saves)");
        System.out.println("  ==========================================================");
    }

    // Keeps asking until the user types a whole number.
    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  Please type a valid whole number!");
            }
        }
    }

    // Reads a line of text and makes sure it is not empty.
    public static String readText(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("  This field cannot be empty!");
        }
    }
}
