package utils;

import dao.PlayersDAO;
import model.PlayerManager;

import java.util.Scanner;

public class SubMenus {
    public static void mostrarMenuJugadores() {
        System.out.println("\n--- Menú Jugadores ---");
        System.out.println("1. Agregar jugadores");
        System.out.println("2. Modificar jugadores");
        System.out.println("3. Eliminar jugadores");
        System.out.println("4. Mostrar jugadores");
        System.out.println("5. Regresar al menú anterior");
        System.out.print(">: ");
    }

    public static void ejecutarMenuJugador(Scanner scanner, PlayerManager playerManager, PlayersDAO playersDAO) {
        int option;
        boolean datosModificados = false;

        do {
            mostrarMenuJugadores();

            while (!scanner.hasNextInt()) {
                System.out.println("Opcion Invalida");
                scanner.next(); // limpia buffer
            }
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("--- Agregar Jugador ---");
                    playerManager.addPlayer(scanner);
                    datosModificados = true;
                    break;
                case 2:
                    System.out.println("--- Modificar Jugador ---");
                    playerManager.modificarJugador(scanner);
                    datosModificados = true;
                    break;
                case 3:
                    System.out.println("--- Eliminar Jugador ---");
                    playerManager.eliminarJugador(scanner);
                    datosModificados = true;
                    break;
                case 4:
                    System.out.println("--- Mostrar lista de jugadores ---");
                    playerManager.showPlayers();
                    break;
                case 5:
                    if (datosModificados) {
                        System.out.println("💾 Guardando datos...");
                        playersDAO.savePlayers(playerManager.getPlayers());
                    }
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
            System.out.println();

        } while (option != 5);
    }
}
