

import java.util.ArrayList;

public class Playlist {

    // ---------------- FIELDS ----------------
    private String name;                        // playlist name
    private ArrayList<Song> songs;              // the actual list of Song objects

    // ---------------- CONSTRUCTOR ----------------
    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();         // start with an empty list
    }

    public String getName() {
        return name;
    }

    // ---------------- BASIC LIST OPERATIONS ----------------

    // Add one song at the end of the playlist.
    public void addSong(Song song) {
        songs.add(song);
    }

    // Remove the FIRST song whose title matches (ignoring capital letters).
    // Returns true if something was removed, false if no match was found.
    public boolean removeSong(String title) {
        for (int i = 0; i < songs.size(); i++) {
            Song current = songs.get(i);
            if (current.getTitle().equalsIgnoreCase(title)) {
                songs.remove(i);
                return true;                    // found and removed -> stop here
            }
        }
        return false;                           // no song with that title
    }

    // Returns the song stored at a given position (0-based index).
    public Song getSong(int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        }
        return null;                            // invalid position
    }

    // How many songs are in the playlist?
    public int getSize() {
        return songs.size();
    }

    // Gives the full list to other classes (e.g. StatsManager needs it).
    public ArrayList<Song> getAllSongs() {
        return songs;
    }

    // ---------------- DISPLAY ----------------

    // Prints every song as a neatly aligned table using printf.
    // %-3d  -> left-aligned number in 3 spaces
    // %-26s -> left-aligned text in 26 spaces (keeps columns straight)
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

    // ---------------- SEARCH ----------------

    // Returns a NEW list containing every song whose title OR artist
    // contains the search text (case-insensitive).
    public ArrayList<Song> searchSongs(String query) {
        ArrayList<Song> results = new ArrayList<>();
        String q = query.toLowerCase();         // search is not case-sensitive
        for (Song s : songs) {
            if (s.getTitle().toLowerCase().contains(q)
                    || s.getArtist().toLowerCase().contains(q)) {
                results.add(s);
            }
        }
        return results;
    }

    // ---------------- SORTING ----------------

    // Sort A-Z by title. compareToIgnoreCase is Java's built-in
    // alphabetical comparison that ignores CAPITAL/small letters.
    public void sortByTitle() {
        songs.sort((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
    }

    // Sort by play count, HIGHEST first (most played at the top).
    // b - a (instead of a - b) gives us the reverse (descending) order.
    public void sortByPlayCount() {
        songs.sort((a, b) -> b.getPlayCount() - a.getPlayCount());
    }
}
