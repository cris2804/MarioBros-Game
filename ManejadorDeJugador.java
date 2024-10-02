import java.io.*;
import java.net.Socket;

public class ManejadorDeJugador extends Thread { // Extiende Thread para que cada jugador tenga su propio hilo
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private Tablero tablero;
    private int idJugador;

    public ManejadorDeJugador(Socket socket, Tablero tablero, int idJugador, ServidorDeMarioBros servidorDeMarioBros) {
        this.socket = socket;
        this.tablero = tablero;
        this.idJugador = idJugador;
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String mensaje;
            // Escucha los comandos del jugador
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Jugador " + idJugador + ": " + mensaje);
                procesarComando(mensaje); // Procesa los comandos del jugador, como movimiento

                // Actualiza y envía el tablero a este jugador
                enviarTablero();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void procesarComando(String comando) {
        // Lógica para manejar los comandos del jugador y actualizar el tablero
        tablero.actualizarPosicion(idJugador, comando);
    }

    void enviarTablero() {
        // Enviar el estado del tablero al cliente
        salida.println(tablero.toString());
    }
}
