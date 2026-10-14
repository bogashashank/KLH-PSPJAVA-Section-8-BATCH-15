/**
 * ============================================================================
 *  SONG.JAVA
 * ----------------------------------------------------------------------------
 *  Represents ONE song in the playlist.
 *
 *  This class follows the "Encapsulation" idea of OOP:
 *    - The data (fields) is kept PRIVATE so nobody can change it directly.
 *    - Access is given only through PUBLIC getter methods.
 *
 *  A Song object knows:
 *    - its title            (e.g. "Believer")
 *    - its artist           (e.g. "Imagine Dragons")
 *    - its length in seconds (e.g. 204)
 *    - how many times it was played (playCount)
 * ============================================================================
 */
public class Song {

    // ---------------- FIELDS (the data every song has) ----------------
    private String title;           // name of the song
    private String artist;          // singer / band name
    private int durationSeconds;    // song length in seconds (e.g. 204 = 3:24)
    private int playCount;          // how many times this song was played

    // ---------------- CONSTRUCTOR ----------------
    // Called whenever we create a new Song object, e.g.:
    //   Song s = new Song("Believer", "Imagine Dragons", 204, 0);
    public Song(String title, String artist, int durationSeconds, int playCount) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.playCount = playCount;
    }

    // ---------------- GETTERS (safe read-only access) ----------------
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

    // ---------------- METHODS (things a song can do) ----------------

    // Called when the user "plays" this song -> play count goes up by 1.
    public void incrementPlayCount() {
        playCount++;
    }

    // Converts seconds into a nice "minutes:seconds" format.
    // Example: 204 seconds  ->  "3:24"
    public String getDurationFormatted() {
        int minutes = durationSeconds / 60;               // whole minutes
        int seconds = durationSeconds % 60;               // leftover seconds
        return minutes + ":" + String.format("%02d", seconds);
    }

    // ---------------- FILE HELPER METHODS ----------------
    // We save each song as ONE line in the text file, like this:
    //        Believer|Imagine Dragons|204|51
    // We use "|" as the separator (instead of a comma) because some
    // song titles contain commas, and that would confuse the splitting.

    // Converts this song into one save-file line.
    public String toFileString() {
        return title + "|" + artist + "|" + durationSeconds + "|" + playCount;
    }

    // Reads one save-file line and turns it back into a Song object.
    // "static" means we can call it without any Song object:
    //        Song s = Song.fromFileString(line);
    public static Song fromFileString(String line) {
        String[] parts = line.split("\\|");   // "\\" escapes the special meaning of "|"
        if (parts.length < 4) {
            return null;   // line was broken/incomplete -> skip it safely
        }
        String title    = parts[0];
        String artist   = parts[1];
        int seconds     = Integer.parseInt(parts[2].trim());
        int plays       = Integer.parseInt(parts[3].trim());
        return new Song(title, artist, seconds, plays);
    }

    // ---------------- PRINTING ----------------
    // toString() decides how a Song looks when we print it.
    // Example:  Believer - Imagine Dragons (3:24)
    @Override
    public String toString() {
        return title + " - " + artist + " (" + getDurationFormatted() + ")";
    }
}
