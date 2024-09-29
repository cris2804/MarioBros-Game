import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
// El servidor ya está implementado con hilos. Puedes utilizar el código anterior.

public class ServidorDeMarioBros {
    private static final int PUERTO = 8080;
    private List<ManejadorDeJugador> jugadores;
    private Tablero tablero;

    public ServidorDeMarioBros() {
        jugadores = new ArrayList<>();
        tablero = new Tablero(); // Inicializa el tablero de juego
    }

    public void iniciar() {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);
            while (true) {
                Socket socket = servidor.accept();
                ManejadorDeJugador nuevoJugador = new ManejadorDeJugador(socket, tablero, jugadores.size() + 1);
                jugadores.add(nuevoJugador);
                nuevoJugador.start();
                System.out.println("Nuevo jugador conectado. Total de jugadores: " + jugadores.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServidorDeMarioBros().iniciar();
    }
}
