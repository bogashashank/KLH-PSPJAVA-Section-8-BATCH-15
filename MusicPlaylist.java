import java.util.Scanner;

public class Main {

    public static int calculateStreakMultiplier(int days) {

        if (days == 0) {
            return 1;
        }

        return days * calculateStreakMultiplier(days - 1);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- Music Playlist & Listening-Stats Manager ---");
        System.out.println("1. Analyze Playlist Play Counts");
        System.out.println("2. Monthly Genre Breakdown Matrix");
        System.out.println("3. Calculate Superfan Streak");
        System.out.println("4. Exit");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        switch (choice) {

            case 1: {
                System.out.print("Enter the number of tracks in the playlist: ");

                int numTracks = sc.nextInt();

                if (numTracks <= 0) {
                    System.out.println("You need at least 1 track to analyze!");
                    break;
                }

                int[] playCounts = new int[numTracks];
                int totalPlays = 0;

                for (int i = 0; i < numTracks; i++) {
                    System.out.print("Enter play count for track " + (i + 1) + ": ");
                    playCounts[i] = sc.nextInt();
                    totalPlays += playCounts[i];
                }

                int maxPlays = playCounts[0];

                for (int i = 1; i < numTracks; i++) {
                    if (playCounts[i] > maxPlays) {
                        maxPlays = playCounts[i];
                    }
                }

                System.out.printf("Total Playlist Streams: %d%n", totalPlays);
                System.out.printf("Most Played Track Count: %d%n", maxPlays);

                break;
            }

            case 2: {
                System.out.println("\n--- Monthly Genre Breakdown Matrix ---");

                // 4 Weeks (rows) x 3 Genres (columns)
                int[][] genreMatrix = new int[4][3];

                System.out.println("Enter the stream counts for 4 weeks across 3 genres:");

                for (int i = 0; i < 4; i++) {

                    System.out.println("Week " + (i + 1) + ":");

                    System.out.print("  Pop streams: ");
                    genreMatrix[i][0] = sc.nextInt();

                    System.out.print("  Rock streams: ");
                    genreMatrix[i][1] = sc.nextInt();

                    System.out.print("  Hip-Hop streams: ");
                    genreMatrix[i][2] = sc.nextInt();
                }

                int totalPop = 0;
                int totalRock = 0;
                int totalHipHop = 0;

                System.out.println("\nStreams per week (Pop | Rock | Hip-Hop):");

                for (int i = 0; i < 4; i++) {

                    System.out.print("Week " + (i + 1) + ": ");

                    for (int j = 0; j < 3; j++) {

                        System.out.print(genreMatrix[i][j] + "   ");

                        if (j == 0) {
                            totalPop += genreMatrix[i][j];
                        } else if (j == 1) {
                            totalRock += genreMatrix[i][j];
                        } else if (j == 2) {
                            totalHipHop += genreMatrix[i][j];
                        }
                    }

                    System.out.println();
                }

                System.out.printf("%nTotal Pop Streams: %d%n", totalPop);
                System.out.printf("Total Rock Streams: %d%n", totalRock);
                System.out.printf("Total Hip-Hop Streams: %d%n", totalHipHop);

                break;
            }

            case 3: {
                System.out.print("Enter consecutive listening streak (days): ");

                int streak = sc.nextInt();
                int multiplier = calculateStreakMultiplier(streak);

                System.out.printf(
                    "Superfan Multiplier for %d days is: %d%n",
                    streak,
                    multiplier
                );

                break;
            }

            case 4: {
                System.out.println("Closing Listening-Stats Manager...");
                break;
            }

            default: {
                System.out.println(
                    "Invalid choice. Please select an option from 1 to 4."
                );

                break;
            }
        }

        System.out.println("\nProgram finished.");

        sc.close();
    }
}
