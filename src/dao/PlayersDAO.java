package dao;

import model.Player;

import java.io.*;
import java.util.ArrayList;

public class PlayersDAO {
    private final String DIR = "data";
    private final String FILE_NAME = DIR + "/" + "players.dat";

    public ArrayList<Player> players = new ArrayList<>();

    public PlayersDAO() {
        this.players = loadPlayers();
    }

    public ArrayList<Player> loadPlayers() {
        File dir = new File(DIR);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.err.println("❌ Error: No se pudo crear el directorio de datos: " + DIR);
                return null;
            }
        }
        ArrayList<Player> players = new ArrayList<>();
        try (
                FileInputStream fileIn = new FileInputStream(FILE_NAME);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        ) {
            players = (ArrayList<Player>) objectIn.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Archivo de datos no encontrado. Se inicia con lista vacía de citas.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error al cargar los jugadores: " + e.getMessage());
            e.printStackTrace();
        }
        return players;
    }

    public boolean savePlayers(ArrayList<Player> players) {
        File dir = new File(DIR);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.err.println("❌ Error: No se pudo crear el directorio de datos: " + DIR);
                return false;
            }
        }
        try (
                FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        ) {
            objectOut.writeObject(players);
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar los jugadores: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
