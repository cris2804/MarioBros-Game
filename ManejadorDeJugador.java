import java.io.*;
import java.net.Socket;

public class ManejadorDeJugador extends Thread { // Extiende Thread para que cada jugador tenga su propio hilo
    private Socket socket;
    private PrintWriter salida;
    private char[][] tablero;
    private int idJugador;

    public ManejadorDeJugador(Socket _socket, char[][] _tablero, int _idJugador) {
        this.socket = _socket;
        this.tablero = _tablero;
        this.idJugador = _idJugador;
        try {
            salida = new PrintWriter(socket.getOutputStream(), true);
            ServidorDeMarioBros.agregarJugador(salida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje;
            // Escucha los comandos del jugador
            salida.println(ServidorDeMarioBros.obtenerEstadoInicialTablero());
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Jugador " + idJugador + ": " + mensaje);
                ServidorDeMarioBros.procesarComando(idJugador, mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ServidorDeMarioBros.removerJugador(salida);
        }
    }

}
