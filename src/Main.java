import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> songList = new ArrayList<>();
        songList.add("Gotta_Love_Me.wav");
        songList.add("Style - Kendrick Lamar.wav");
        songList.add("Watch The Party Die  - Kendrick Lamar.wav");
        songList.add("Kendrick Lamar - Teach Me How To Pray.wav");

        boolean running = true;

        while (running) {
            displaySongMenu();
            System.out.print("Selection: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Enter a valid digit!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Goodbye!");
                running = false;
            } else if (choice > 0 && choice <= songList.size()) {
                playSong(songList.get(choice - 1), scanner);
            } else {
                System.out.println("Invalid choice, pick another one!");
            }
        }
    }

    private static void displaySongMenu() {
        System.out.println("Pick a song of your choice:");
        System.out.println("0. Quit");
        System.out.println("1. Gotta love me");
        System.out.println("2. Style");
        System.out.println("3. Watch the party die");
        System.out.println("4. Teach me how to pray");
        System.out.print("Selection: ");
    }

    private static void playSong(String songFile, Scanner scanner) {
        File file = new File(songFile);
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            String response = "";
            while (!response.equals("Q")) {
                System.out.println("\nP = Play | S = Stop | R = Reset | L = Loop | Q = Quit");
                System.out.print("Enter your choice: ");
                response = scanner.next().toUpperCase();

                switch (response) {
                    case "P" -> clip.start();
                    case "S" -> clip.stop();
                    case "R" -> clip.setMicrosecondPosition(0);
                    case "L" -> clip.loop(Clip.LOOP_CONTINUOUSLY);
                    case "Q" -> clip.close();
                    default -> System.out.println("Enter a valid option!");
                }
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing file: " + songFile);
        }
    }
}

