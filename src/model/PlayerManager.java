package model;

import utils.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayerManager {

    private ArrayList<Player> players;

    public PlayerManager() {
        this.players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Scanner scanner) {
        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        String email = "";
        boolean emailValido = false;

        while (!emailValido) {
            System.out.print("Email: ");
            email = scanner.nextLine();

            if (emailExists(email)) {
                Menu.showErrorMessage(" ❌ Error: El correo '" + email + "' ya está registrado. Intenta con otro.");
            } else {
                emailValido = true;
            }
        }

        Player newPlayer = new Player(name, email);
        players.add(newPlayer);

        System.out.println(" ✅ ¡Usuario '" + name + "' agregado con éxito!");
    }

    public boolean emailExists(String email) {
        for (Player p : players) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public void showPlayers() {
        if (players.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
        } else {
            for (int i = 0; i < players.size(); i++) {
                System.out.println(i + 1 + ") " + players.get(i));
            }
        }
    }

    public void modificarJugador(Scanner scanner) {
        if (players.isEmpty()) {
            Menu.showErrorMessage("⚠ No se encontraron jugadores");
            return;
        }

        showPlayers();

        int playerId;
        do {
            System.out.print("\nIngresa el ID del jugador: ");
            playerId = scanner.nextInt();
            if (playerId < 0 || playerId > players.size()) {
                Menu.showErrorMessage("ID incorrecto. Intenta de nuevo.");
                continue;
            }
            playerId--;
            break;
        } while (true);

        scanner.nextLine();

        System.out.print("\nNuevo nombre (deja en blanco para no cambiar [" + players.get(playerId).getName() + "]): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            players.get(playerId).setName(newName);
        }

        String newEmail;
        do {
            System.out.print("Nuevo email (deja en blanco para no cambiar [" + players.get(playerId).getEmail() + "]): ");
            newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                if (!newEmail.equalsIgnoreCase(players.get(playerId).getEmail()) && emailExists(newEmail)) {
                    Menu.showErrorMessage(" ❌ Error: El correo '" + newEmail + "' ya pertenece a otro jugador.");
                } else {
                    players.get(playerId).setEmail(newEmail);
                    break;
                }
            }
        } while (!newEmail.isEmpty());
    }

    public void eliminarJugador(Scanner scanner) {
        if (players.isEmpty()) {
            Menu.showErrorMessage("⚠ No se encontraron jugadores");
            return;
        }

        showPlayers();

        int playerId;
        do {
            System.out.print("\nIngresa el ID del jugador: ");
            playerId = scanner.nextInt();
            if (playerId < 0 || playerId > players.size()) {
                Menu.showErrorMessage("ID incorrecto. Intenta de nuevo.");
                continue;
            }

            Player p = players.get(playerId - 1);

            System.out.print("Estas seguro de eliminar al jugador [" + p.getName() + "] (y/n)? ");
            String option = scanner.nextLine();
            if (option.equalsIgnoreCase("y")) {
                players.remove(p);
                System.out.println(" ✅ ¡Jugador '" + p.getName() + "' eliminado con éxito!");
            }
            break;
        } while (true);
    }
}
