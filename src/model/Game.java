package model;

import dao.PlayersDAO;
import dao.WordsDAO;
import utils.Menu;

import java.io.File;
import java.util.*;

public class Game {
    public void startGame(Scanner scanner, PlayerManager playerManager, PlayersDAO playersDAO) {
        if (playerManager.getPlayers().isEmpty() || playerManager.getPlayers().size() == 1) {
            do {
                System.out.print("No hay jugaderes suficientes.\nQuieres registrar (y/n)? ");
                String option = scanner.nextLine();
                if (option.equalsIgnoreCase("y")) {
                    do {
                        playerManager.addPlayer(scanner);
                        System.out.print("Quieres agregar un nuevo jugador (y/n)? ");
                        option = scanner.nextLine();
                    } while (option.equalsIgnoreCase("y"));
                    System.out.println("💾 Guardando datos...");
                    playersDAO.savePlayers(playerManager.getPlayers());
                } else {
                    return;
                }
            } while (playerManager.getPlayers().size() <= 1);
        }

        String category = showAndSelectCategory(scanner);

        WordsDAO wordsDAO = new WordsDAO(category);
        List<String> words = wordsDAO.loadWords();
        if (words.isEmpty()) {
            Menu.showErrorMessage("No hay palabras disponibles. Intenta de nuevo.");
            return;
        }

        boolean running = true;
        boolean invalidOption;
        while (running) {
            if (words.isEmpty()) {
                Menu.showErrorMessage("No quedan mas palabras disponibles. Elige otra categoria.");
                return;
            }
            String word = getRandomWord(words);
            List<List<String>> playersList = setWordsToPlayers(word, playerManager.getPlayers());

            /// test block
            System.out.println("ASIGNANDO PALABRAS ALEATORIO...");
            System.out.println("OBTENIENDO AL IMPOSTOR...");
            System.out.println("--------------------------------");
            for (List<String> strings : playersList) {
                for (String s : strings) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
            ///

            do {
                invalidOption = false;
                Menu.showGameMenu();
                int option;
                option = scanner.nextInt(); // add exception
                scanner.nextLine();
                switch (option) {
                    case 1 -> words.remove(word);
                    case 2 -> running = false;
                    default -> invalidOption = true;
                }
                if (invalidOption) {
                    System.out.println("Opcion Incorrecta. Intenta de nuevo");
                }
            } while (invalidOption);
        }

    }

    private String showAndSelectCategory(Scanner scanner) {
        final String DIR = "data";
        File dir = new File(DIR);
        ArrayList<String> files = new ArrayList<>();

        String[] filesList = dir.list();

        System.out.println("\nSelecciona una categoria:");
        int count = 0;
        for (String f : filesList) {
            if (f.endsWith(".csv")) {
                String[] split = f.split("\\.");
                String s = split[0];
                String properCaseFile = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                if (s.equalsIgnoreCase("paises_ciudades")) {
                    properCaseFile = "Paises y Ciudades";
                }
                count++;
                System.out.println(count + ") " + properCaseFile);
                files.add(s);
            }
        }

        if (files.isEmpty()) {
            Menu.showErrorMessage("No se encontraron categorias disponibles.");
            return null;
        }

        System.out.print(">: ");
        int option;
        while (!scanner.hasNextInt()) {
            Menu.showErrorMessage("Opcion incorrecta. Intenta de nuevo.");
            scanner.next();
        }
        do {
            option = scanner.nextInt();
            scanner.nextLine();
            if (option <= 0 || option > files.size()) {
                Menu.showErrorMessage("Opcion incorrecta. Intenta de nuevo.");
                continue;
            }
            break;
        } while (true);

        return files.get(option - 1);
    }

    private String getRandomWord(List<String> words) {
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }

    private int getImpostor(ArrayList<Player> players) {
        Random random = new Random();
        return random.nextInt(players.size());
    }

    private List<List<String>> setWordsToPlayers(String word, ArrayList<Player> players) {
        int impostorIndex = getImpostor(players);

        List<List<String>> playersList = new ArrayList<>();

        for (int i = 0; i < players.size(); i++){
            List<String> playersInfo = new ArrayList<>();
            playersInfo.add(players.get(i).getName());
            playersInfo.add(players.get(i).getEmail());

            if (i == impostorIndex) {
                playersInfo.add("IMPOSTOR");
            } else {
                playersInfo.add(word.trim());
            }

            playersList.add(playersInfo);
        }
        return playersList;
    }
}
