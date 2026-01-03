import dao.PlayersDAO;
import dao.WordsDAO;
import model.Game;
import model.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.PlayerManager;
import utils.Menu;
import utils.SubMenus;

public class Main {
    public static void main(String[] args) {

        PlayersDAO playersDAO = new PlayersDAO();
        ArrayList<Player> players = playersDAO.players;

        PlayerManager playerManager = new PlayerManager();
        playerManager.setPlayers(players);

        Game game = new Game();

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            Menu.showMenu();
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> game.startGame(scanner, playerManager, playersDAO);
                case 2 -> SubMenus.ejecutarMenuJugador(scanner, playerManager, playersDAO);
                case 3 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion Incorrecta");
            }
        } while (option != 3);
    }

}