import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * ============================================================================
 *  MUSICMANAGERAPP.JAVA (All-in-One File)
 * ----------------------------------------------------------------------------
 *  Combines Song, Playlist, and StatsManager into a single file with an
 *  interactive menu to select between the 3 modules and pick specific songs
 *  or individual statistics.
 * ============================================================================
 */
public class MusicManagerApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Playlist myPlaylist = new Playlist("My Favorites");
        StatsManager statsManager = new StatsManager(myPlaylist);

        // Pre-loading sample songs so you can test immediately
        myPlaylist.addSong(new Song("Believer", "Imagine Dragons", 204, 15));
        myPlaylist.addSong(new Song("Thunder", "Imagine Dragons", 187, 10));
        myPlaylist.addSong(new Song("Blinding Lights", "The Weeknd", 200, 25));
        myPlaylist.addSong(new Song("Starboy", "The Weeknd", 230, 18));
        myPlaylist.addSong(new Song("Shape of You", "Ed Sheeran", 233, 12));

        boolean running = true;
        while (running) {
            System.out.println("\n==========================================");
            System.out.println("   MUSIC PLAYLIST & STATS MANAGER");
            System.out.println("==========================================");
            System.out.println("  Select a Module:");
            System.out.println("   1. SONG Operations (Pick a specific song to view/play)");
            System.out.println("   2. PLAYLIST Operations (View all, Add, Remove, Search, Sort)");
            System.out.println("   3. STATS Manager (Pick a specific song stat or full report)");
            System.out.println("   4. Exit");
            System.out.print("  Enter your choice: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    handleSongMenu(sc, myPlaylist);
                    break;
                case 2:
                    handlePlaylistMenu(sc, myPlaylist);
                    break;
                case 3:
                    handleStatsMenu(sc, statsManager, myPlaylist);
                    break;
                case 0:
                    System.out.println("\n  Exiting Music Manager. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n  Invalid choice! Please select 0, 1, 2, or 3.");
            }
        }
        sc.close();
    }

    // =========================================================================
    //  MODULE 1: SONG MENU (Select which song you want)
    // =========================================================================
    private static void handleSongMenu(Scanner sc, Playlist playlist) {
        if (playlist.getSize() == 0) {
            System.out.println("\n  The playlist is empty! Add songs in the Playlist menu first.");
            return;
        }

        // Show numbered list of songs so the user can pick one
        playlist.viewSongs();
        System.out.print("\n  Enter the Song NO. (1 to " + playlist.getSize() + ") you want to select (or 0 to go back): ");
        int index = readInt(sc) - 1;

        if (index == -1) return;

        Song selectedSong = playlist.getSong(index);
        if (selectedSong == null) {
            System.out.println("  Invalid Song Number!");
            return;
        }

        boolean inSongMenu = true;
        while (inSongMenu) {
            System.out.println("\n  --- SELECTED SONG: " + selectedSong.toString() + " ---");
            System.out.println("   1. Play this song (+1 Play Count)");
            System.out.println("   2. View detailed stats for this song");
            System.out.println("   3. View save-file format string");
            System.out.println("   0. Back to Main Menu");
            System.out.print("  Choose an action: ");

            int action = readInt(sc);
            switch (action) {
                case 1:
                    selectedSong.incrementPlayCount();
                    System.out.println("  Now Playing: " + selectedSong.getTitle() + " | Total Plays: " + selectedSong.getPlayCount());
                    break;
                case 2:
                    int totalListenedSecs = selectedSong.getDurationSeconds() * selectedSong.getPlayCount();
                    int mins = totalListenedSecs / 60;
                    int secs = totalListenedSecs % 60;
                    System.out.println("\n  [Song Details]");
                    System.out.println("   Title               : " + selectedSong.getTitle());
                    System.out.println("   Artist              : " + selectedSong.getArtist());
                    System.out.println("   Duration            : " + selectedSong.getDurationFormatted() + " (" + selectedSong.getDurationSeconds() + "s)");
                    System.out.println("   Times Played        : " + selectedSong.getPlayCount());
                    System.out.println("   Total Time Listened : " + mins + "m " + secs + "s");
                    break;
                case 3:
                    System.out.println("   File String: " + selectedSong.toFileString());
                    break;
                case 0:
                    inSongMenu = false;
                    break;
                default:
                    System.out.println("  Invalid option.");
            }
        }
    }

    // =========================================================================
    //  MODULE 2: PLAYLIST MENU (Manage the collection)
    // =========================================================================
    private static void handlePlaylistMenu(Scanner sc, Playlist playlist) {
        boolean inPlaylistMenu = true;
        while (inPlaylistMenu) {
            System.out.println("\n  --- PLAYLIST MENU (" + playlist.getName() + ") ---");
            System.out.println("   1. View all songs");
            System.out.println("   2. Add a new song");
            System.out.println("   3. Remove a song by title");
            System.out.println("   4. Search songs (by title or artist)");
            System.out.println("   5. Sort songs A-Z by Title");
            System.out.println("   6. Sort songs by Play Count (Highest first)");
            System.out.println("   0. Back to Main Menu");
            System.out.print("  Choose an action: ");

            int action = readInt(sc);
            switch (action) {
                case 1:
                    playlist.viewSongs();
                    break;
                case 2:
                    System.out.print("  Enter Song Title: ");
                    String title = sc.nextLine();
                    System.out.print("  Enter Artist Name: ");
                    String artist = sc.nextLine();
                    System.out.print("  Enter Duration in seconds (e.g. 204): ");
                    int duration = readInt(sc);
                    System.out.print("  Enter Initial Play Count (e.g. 0): ");
                    int plays = readInt(sc);

                    playlist.addSong(new Song(title, artist, duration, plays));
                    System.out.println("  Song added successfully!");
                    break;
                case 3:
                    System.out.print("  Enter Title of the song to remove: ");
                    String removeTitle = sc.nextLine();
                    if (playlist.removeSong(removeTitle)) {
                        System.out.println("  Song removed successfully.");
                    } else {
                        System.out.println("  No song found with that title.");
                    }
                    break;
                case 4:
                    System.out.print("  Enter search keyword (title or artist): ");
                    String query = sc.nextLine();
                    ArrayList<Song> results = playlist.searchSongs(query);
                    if (results.isEmpty()) {
                        System.out.println("  No matching songs found.");
                    } else {
                        System.out.println("\n  Matching Songs:");
                        for (Song s : results) {
                            System.out.println("   - " + s.toString() + " [" + s.getPlayCount() + " plays]");
                        }
                    }
                    break;
                case 5:
                    playlist.sortByTitle();
                    System.out.println("  Playlist sorted A-Z by title!");
                    playlist.viewSongs();
                    break;
                case 6:
                    playlist.sortByPlayCount();
                    System.out.println("  Playlist sorted by most played!");
                    playlist.viewSongs();
                    break;
                case 0:
                    inPlaylistMenu = false;
                    break;
                default:
                    System.out.println("  Invalid option.");
            }
        }
    }

    // =========================================================================
    //  MODULE 3: STATS MENU (Select which stat you want)
    // =========================================================================
    private static void handleStatsMenu(Scanner sc, StatsManager stats, Playlist playlist) {
        boolean inStatsMenu = true;
        while (inStatsMenu) {
            System.out.println("\n  --- LISTENING STATS MENU ---");
            System.out.println("  Which stat would you like to view?");
            System.out.println("   1. Total Plays (across all songs)");
            System.out.println("   2. Total Listening Time (hours & minutes)");
            System.out.println("   3. Favourite Artist");
            System.out.println("   4. Top-N Most Played Songs");
            System.out.println("   5. View Full Stats Report (All of the above)");
            System.out.println("   6. Check stats for a single specific song");
            System.out.println("   0. Back to Main Menu");
            System.out.print("  Choose a stat option: ");

            int action = readInt(sc);
            switch (action) {
                case 1:
                    System.out.println("\n  >> Total Plays (all songs): " + stats.getTotalPlays());
                    break;
                case 2:
                    int secs = stats.getTotalListeningSeconds();
                    int hours = secs / 3600;
                    int mins = (secs % 3600) / 60;
                    System.out.println("\n  >> Total Listening Time: " + hours + " hours " + mins + " minutes (" + secs + " seconds)");
                    break;
                case 3:
                    System.out.println("\n  >> Favourite Artist: " + stats.getFavouriteArtist());
                    break;
                case 4:
                    System.out.print("  How many top songs do you want to see? (e.g. 3 or 5): ");
                    int n = readInt(sc);
                    stats.showTopSongs(n);
                    break;
                case 5:
                    stats.showStats();
                    break;
                case 6:
                    if (playlist.getSize() == 0) {
                        System.out.println("  Playlist is empty!");
                        break;
                    }
                    playlist.viewSongs();
                    System.out.print("  Select Song NO. to view its share of plays: ");
                    int idx = readInt(sc) - 1;
                    Song s = playlist.getSong(idx);
                    if (s != null) {
                        int totalPlays = stats.getTotalPlays();
                        double percentage = (totalPlays > 0) ? (s.getPlayCount() * 100.0 / totalPlays) : 0;
                        System.out.printf("\n  >> '%s' by %s has %d plays (%.1f%% of your total playlist plays).%n",
                                s.getTitle(), s.getArtist(), s.getPlayCount(), percentage);
                    } else {
                        System.out.println("  Invalid Song Number.");
                    }
                    break;
                case 0:
                    inStatsMenu = false;
                    break;
                default:
                    System.out.println("  Invalid option.");
            }
        }
    }

    // Helper method to safely read integer inputs without crashing on text input
    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("  Please enter a valid number: ");
            sc.next();
        }
        int num = sc.nextInt();
        sc.nextLine(); // consume leftover newline
        return num;
    }
}

/**
 * ============================================================================
 *  CLASS 1: SONG (Non-public so it can live in the same file)
 * ============================================================================
 */
class Song {

    private String title;
    private String artist;
    private int durationSeconds;
    private int playCount;

    public Song(String title, String artist, int durationSeconds, int playCount) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.playCount = playCount;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void incrementPlayCount() {
        playCount++;
    }

    public String getDurationFormatted() {
        int minutes = durationSeconds / 60;
        int seconds = durationSeconds % 60;
        return minutes + ":" + String.format("%02d", seconds);
    }

    public String toFileString() {
        return title + "|" + artist + "|" + durationSeconds + "|" + playCount;
    }

    public static Song fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 4) {
            return null;
        }
        String title    = parts[0];
        String artist   = parts[1];
        int seconds     = Integer.parseInt(parts[2].trim());
        int plays       = Integer.parseInt(parts[3].trim());
        return new Song(title, artist, seconds, plays);
    }

    @Override
    public String toString() {
        return title + " - " + artist + " (" + getDurationFormatted() + ")";
    }
}

/**
 * ============================================================================
 *  CLASS 2: PLAYLIST
 * ============================================================================
 */
class Playlist {

    private String name;
    private ArrayList<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public boolean removeSong(String title) {
        for (int i = 0; i < songs.size(); i++) {
            Song current = songs.get(i);
            if (current.getTitle().equalsIgnoreCase(title)) {
                songs.remove(i);
                return true;
            }
        }
        return false;
    }

    public Song getSong(int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        }
        return null;
    }

    public int getSize() {
        return songs.size();
    }

    public ArrayList<Song> getAllSongs() {
        return songs;
    }

    public void viewSongs() {
        if (songs.isEmpty()) {
            System.out.println("\n  The playlist is empty. Add some songs first!");
            return;
        }
        System.out.println("\n  --------------------------------------------------------------------------");
        System.out.printf("   %-4s %-26s %-20s %-10s %-6s%n",
                "NO.", "TITLE", "ARTIST", "LENGTH", "PLAYS");
        System.out.println("  --------------------------------------------------------------------------");
        for (int i = 0; i < songs.size(); i++) {
            Song s = songs.get(i);
            System.out.printf("   %-4d %-26s %-20s %-10s %-6d%n",
                    (i + 1), s.getTitle(), s.getArtist(),
                    s.getDurationFormatted(), s.getPlayCount());
        }
        System.out.println("  --------------------------------------------------------------------------");
        System.out.println("  Total songs in playlist: " + songs.size());
    }

    public ArrayList<Song> searchSongs(String query) {
        ArrayList<Song> results = new ArrayList<>();
        String q = query.toLowerCase();
        for (Song s : songs) {
            if (s.getTitle().toLowerCase().contains(q)
                    || s.getArtist().toLowerCase().contains(q)) {
                results.add(s);
            }
        }
        return results;
    }

    public void sortByTitle() {
        songs.sort((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
    }

    public void sortByPlayCount() {
        songs.sort((a, b) -> b.getPlayCount() - a.getPlayCount());
    }
}

/**
 * ============================================================================
 *  CLASS 3: STATSMANAGER
 * ============================================================================
 */
class StatsManager {

    private Playlist playlist;

    public StatsManager(Playlist playlist) {
        this.playlist = playlist;
    }

    public int getTotalPlays() {
        int total = 0;
        for (Song s : playlist.getAllSongs()) {
            total = total + s.getPlayCount();
        }
        return total;
    }

    public int getTotalListeningSeconds() {
        int total = 0;
        for (Song s : playlist.getAllSongs()) {
            total = total + (s.getDurationSeconds() * s.getPlayCount());
        }
        return total;
    }

    public String getFavouriteArtist() {
        if (playlist.getSize() == 0) {
            return "N/A";
        }

        HashMap<String, Integer> artistPlays = new HashMap<>();
        for (Song s : playlist.getAllSongs()) {
            String artist = s.getArtist();
            int plays     = s.getPlayCount();

            if (artistPlays.containsKey(artist)) {
                artistPlays.put(artist, artistPlays.get(artist) + plays);
            } else {
                artistPlays.put(artist, plays);
            }
        }

        String bestArtist  = "N/A";
        int    bestPlays   = -1;
        for (String artist : artistPlays.keySet()) {
            if (artistPlays.get(artist) > bestPlays) {
                bestPlays  = artistPlays.get(artist);
                bestArtist = artist;
            }
        }
        return bestArtist;
    }

    public void showStats() {
        int songCount   = playlist.getSize();
        int totalPlays  = getTotalPlays();
        int listenSecs  = getTotalListeningSeconds();

        int hours   = listenSecs / 3600;
        int minutes = (listenSecs % 3600) / 60;

        System.out.println("\n  ======================================================================");
        System.out.println("                            LISTENING STATS REPORT");
        System.out.println("  ======================================================================");
        System.out.println("   Total songs in playlist : " + songCount);
        System.out.println("   Total plays (all songs) : " + totalPlays);
        System.out.println("   Total listening time    : " + hours + " hours " + minutes + " minutes");
        System.out.println("   Favourite artist        : " + getFavouriteArtist());
        System.out.println("  ======================================================================");

        showTopSongs(5);
    }

    public void showTopSongs(int n) {
        ArrayList<Song> copy = new ArrayList<>(playlist.getAllSongs());
        copy.sort((a, b) -> b.getPlayCount() - a.getPlayCount());

        System.out.println("\n   TOP " + n + " MOST PLAYED SONGS");
        System.out.println("   -------------------------------------------------------");

        if (copy.isEmpty()) {
            System.out.println("   (no songs yet)");
            return;
        }

        int limit = Math.min(n, copy.size());
        for (int i = 0; i < limit; i++) {
            Song s = copy.get(i);
            System.out.printf("   %2d. %-26s %-18s %3d plays%n",
                    (i + 1), s.getTitle(), s.getArtist(), s.getPlayCount());
        }
        System.out.println("   -------------------------------------------------------");
    }
}