import java.io.*;
import java.net.Socket;

public class ManejadorDeJugador extends Thread { // Extiende Thread para que cada jugador tenga su propio hilo
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private Tablero tablero;
    private ServidorDeMarioBros servidor;
    private int idJugador;

    public ManejadorDeJugador(Socket socket, Tablero tablero, int idJugador, ServidorDeMarioBros servidor) {
        this.socket = socket;
        this.tablero = tablero;
        this.idJugador = idJugador;
        this.servidor = servidor;
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
                //System.out.println("Jugador " + idJugador + ": " + mensaje);
                procesarComando(mensaje); // Procesa los comandos del jugador, como movimiento

                // Actualiza y envía el tablero a este jugador
                servidor.enviarTableroATodos();
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

    private void procesarComando(String mensaje) {
        String[] partes = mensaje.split(" ");
        if (partes.length == 2) {
            String accion = partes[1];
            tablero.actualizarPosicion(idJugador, accion);
        }
    }

    public void enviarTablero() {
        //System.out.println("Enviando tablero al jugador " + idJugador + ":");
        System.out.println(tablero.toString());
        // Enviar el estado del tablero al cliente
        //salida.println(tablero.toString());
        // Obtener la representación del tablero
        String[] lineasTablero = tablero.obtenerRepresentacion();

        for (String linea : lineasTablero) {
            salida.println(linea); // Envía cada línea del tablero
        }
        salida.println("FIN_TABLERO"); // Indica el final del tablero
    }
}
