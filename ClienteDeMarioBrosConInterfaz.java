import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClienteDeMarioBrosConInterfaz extends JFrame {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private Tablero areaTablero;
    private String nombreJugador;
    private char[][] mapa;

    public ClienteDeMarioBrosConInterfaz(String direccion, int puerto, String nombreJugador) {
        this.nombreJugador = nombreJugador;
        mapa = new char[20][40];
        areaTablero = new Tablero(mapa);
        areaTablero.inicializarMapa();
        setTitle("Mario Bros Multijugador - " + nombreJugador);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(areaTablero, BorderLayout.CENTER);
        conectarServidor(direccion, puerto);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                manejarMovimiento(e);
            }
        });

        setFocusable(true);
        setVisible(true);
    }

    private void conectarServidor(String direccion, int puerto) {
        try {
            // Conectarse al servidor
            socket = new Socket(direccion, puerto);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Iniciar un hilo para escuchar los mensajes del servidor
            new Thread(new EscuchaServidor()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarMovimiento(KeyEvent e) {
        String comando = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                comando = "IZQUIERDA";
                break;
            case KeyEvent.VK_RIGHT:
                comando = "DERECHA";
                break;
            case KeyEvent.VK_UP:
                comando = "SALTAR";
                break;
            case KeyEvent.VK_DOWN:
                comando = "ABAJO";
                break;
        }
        if (comando != null) {
            salida.println(nombreJugador + " " + comando); // Enviar comando al servidor
        }
    }
    private void actualizarTableroConMensaje(String mensaje) {
        System.out.println("Mensaje recibido del servidor: " + mensaje); // Debug: verifica el mensaje recibido
        String[] lineas = mensaje.split("\n");
        for (int i = 0; i < lineas.length && i < mapa.length; ++i) {
            for (int j = 0; j < lineas[i].length() && j < mapa[i].length; ++j) {
                mapa[i][j] = lineas[i].charAt(j);
            }
        }
        SwingUtilities.invokeLater(() -> areaTablero.actualizarMapa(mapa));
    }
    private class EscuchaServidor implements Runnable {
        @Override
        public void run() {
            try {
                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    // Actualizar el tablero en la interfaz gráfica
                    actualizarTableroConMensaje(mensaje);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Iniciar el cliente con interfaz gráfica
        String nombreJugador = JOptionPane.showInputDialog("Ingresa tu nombre de jugador:");
        new ClienteDeMarioBrosConInterfaz("localhost", 8080, nombreJugador);
    }
}
