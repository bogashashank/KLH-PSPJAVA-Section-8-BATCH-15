/**
 * ============================================================================
 *  STATSMANAGER.JAVA
 * ----------------------------------------------------------------------------
 *  Calculates all LISTENING STATISTICS from the playlist:
 *
 *    - total number of plays
 *    - total listening time (hours + minutes)
 *    - favourite artist (artist whose songs have the most plays combined)
 *    - Top-5 most played songs
 *
 *  This class does the MATH, and Main.java simply calls its methods.
 * ============================================================================
 */
import java.util.ArrayList;
import java.util.HashMap;

public class StatsManager {

    // The StatsManager works ON a playlist, so it keeps a reference to it.
    private Playlist playlist;

    // ---------------- CONSTRUCTOR ----------------
    public StatsManager(Playlist playlist) {
        this.playlist = playlist;
    }

    // ---------------- CALCULATION HELPERS ----------------

    // Adds up the play counts of every song.
    public int getTotalPlays() {
        int total = 0;
        for (Song s : playlist.getAllSongs()) {
            total = total + s.getPlayCount();
        }
        return total;
    }

    // Adds up (song length x times played) for every song.
    // Example: a 3-minute song played 4 times = 12 minutes of listening.
    public int getTotalListeningSeconds() {
        int total = 0;
        for (Song s : playlist.getAllSongs()) {
            total = total + (s.getDurationSeconds() * s.getPlayCount());
        }
        return total;
    }

    // Finds the artist with the highest TOTAL number of plays.
    //
    // We use a HashMap, which works like a dictionary:
    //   KEY   = artist name        VALUE = total plays of that artist
    // Every time we see an artist, we add their plays to their total.
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
                artistPlays.put(artist, plays);   // first time we see this artist
            }
        }

        // Now find which artist has the biggest total.
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

    // ---------------- THE FULL STATS REPORT ----------------
    public void showStats() {
        int songCount   = playlist.getSize();
        int totalPlays  = getTotalPlays();
        int listenSecs  = getTotalListeningSeconds();

        // Convert total seconds into "X hours Y minutes".
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

    // Prints the Top-N most played songs.
    // We sort a COPY of the list so the playlist order is not changed.
    public void showTopSongs(int n) {
        ArrayList<Song> copy = new ArrayList<>(playlist.getAllSongs());
        copy.sort((a, b) -> b.getPlayCount() - a.getPlayCount());   // highest first

        System.out.println("\n   TOP " + n + " MOST PLAYED SONGS");
        System.out.println("   -------------------------------------------------------");

        if (copy.isEmpty()) {
            System.out.println("   (no songs yet)");
            return;
        }

        // Print at most n songs (if the playlist is smaller, print all).
        int limit = Math.min(n, copy.size());
        for (int i = 0; i < limit; i++) {
            Song s = copy.get(i);
            // printf: %2d = rank, %-26s = title, %-18s = artist, %3d = plays
            System.out.printf("   %2d. %-26s %-18s %3d plays%n",
                    (i + 1), s.getTitle(), s.getArtist(), s.getPlayCount());
        }
        System.out.println("   -------------------------------------------------------");
    }
}
