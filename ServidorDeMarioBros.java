import java.io.*;
import java.net.*;
import java.util.*;
// El servidor ya está implementado con hilos. Puedes utilizar el código anterior.

public class ServidorDeMarioBros {
    private static final int PUERTO = 8080;
    private static char[][] tablero = new char[20][40];
    private static Set<PrintWriter> jugadores = new HashSet<>();
    private static int contadorJugadores = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)){
            System.out.println("Servidor Iniciado ... ");
            inicializarTablero();
            while (true) {
                new ManejadorDeJugador(serverSocket.accept(), tablero, ++contadorJugadores).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void inicializarTablero() {
        for (int i = 0; i < 20; ++i) {
            Arrays.fill(tablero[i], '#');
        }
    }

    static void enviarEstadoTablero() {
        StringBuilder sb = new StringBuilder();
        for (char[] fila : tablero) {
            sb.append(fila).append("\n");
        }
        synchronized (jugadores) {
            for (PrintWriter jugador : jugadores) {
                jugador.println(sb.toString());
            }
        }
    }
    static String obtenerEstadoInicialTablero() {
        StringBuilder sb = new StringBuilder();
        for (char[] fila : tablero) {
            sb.append(fila).append("\n");
        }
        return sb.toString();
    }
    static void agregarJugador(PrintWriter salida) {
        synchronized (jugadores) {
            jugadores.add(salida);
        }
    }

    static void removerJugador(PrintWriter salida) {
        synchronized (jugadores) {
            jugadores.remove(salida);
        }
    }
    static void procesarComando(int idJugador, String comando) {
        int x = 0;
        int y = 0;
        switch (comando) {
            case "IZQUIERDA":
                if (x > 0) x--; // Mover a la izquierda
                break;
            case "DERECHA":
                if (x < tablero[0].length - 1) x++; // Mover a la derecha
                break;
            case "SALTAR":
                // Lógica para saltar (puedes implementar esto más adelante)
                break;
            case "ABAJO":
                // Lógica para mover hacia abajo (puedes implementar esto más adelante)
                break;
        }
        tablero[y][x] = 'J';
        enviarEstadoTablero(); // Enviar el tablero actualizado a todos los jugadores
    }
}
