package utils;

public class Menu {
    public static void showMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("1) Iniciar Juego");
        System.out.println("2) Administrar Jugadores");
        System.out.println("3) Salir");
        System.out.print(">: ");
    }

    public static void showErrorMessage(String message) {
        System.out.println("\n*" + message + "*\n");
    }

    public static void showGameMenu() {
        System.out.println("\n-----------------------------------------");
        System.out.println("1) Elegir nueva palabra");
        System.out.println("2) Regresar menu anterior");
        System.out.print(">: ");
    }
}
