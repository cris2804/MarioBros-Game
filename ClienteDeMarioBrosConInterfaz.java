import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClienteDeMarioBrosConInterfaz extends JFrame {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private JTextArea areaTablero;
    private String nombreJugador;

    public ClienteDeMarioBrosConInterfaz(String direccion, int puerto, String nombreJugador) {
        this.nombreJugador = nombreJugador;

        // Configuración de la interfaz gráfica con GridBagLayout
        setTitle("Mario Bros Multijugador - " + nombreJugador);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel para la IP, puerto y botón de conexión
        JPanel panelConexion = new JPanel(new GridBagLayout());
        GridBagConstraints gbcc = new GridBagConstraints();

        JLabel labelIp = new JLabel("IP");
        JTextField campoIp = new JTextField(direccion, 10);
        JLabel labelPuerto = new JLabel("Port");
        JTextField campoPuerto = new JTextField(String.valueOf(puerto), 5);
        JButton botonConectar = new JButton("Connect");

        gbcc.insets = new Insets(5, 5, 5, 5); // Espaciado interno

        // Añadiendo componentes a panelConexion
        gbcc.gridx = 0;
        gbcc.gridy = 0;
        panelConexion.add(labelIp, gbcc);

        gbcc.gridx = 1;
        gbcc.gridy = 0;
        panelConexion.add(campoIp, gbcc);

        gbcc.gridx = 2;
        gbcc.gridy = 0;
        panelConexion.add(labelPuerto, gbcc);

        gbcc.gridx = 3;
        gbcc.gridy = 0;
        panelConexion.add(campoPuerto, gbcc);

        gbcc.gridx = 4;
        gbcc.gridy = 0;
        panelConexion.add(botonConectar, gbcc);

        // Añadir panel de conexión al JFrame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Hacer que el panel ocupe dos columnas
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(panelConexion, gbc);

        // Panel para el tablero (mapa)
        areaTablero = new JTextArea(20, 40); // Ajusta el tamaño si es necesario
        areaTablero.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Fuente monoespaciada para el mapa
        areaTablero.setEditable(false); // No permitir que el usuario edite el área de texto
        JScrollPane scrollPane = new JScrollPane(areaTablero);

        // Añadir el área del tablero al JFrame
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Volver a una columna
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(scrollPane, gbc);

        // Panel para los botones de control (movimiento)
        JPanel panelControl = new JPanel(new GridBagLayout());
        GridBagConstraints gbccControl = new GridBagConstraints();

        JButton btnIzquierda = new JButton("<");
        JButton btnDerecha = new JButton(">");
        JButton btnArriba = new JButton("^");
        JButton btnAbajo = new JButton("V");

        gbccControl.insets = new Insets(5, 5, 5, 5); // Espaciado interno

        gbccControl.gridx = 1;
        gbccControl.gridy = 0;
        panelControl.add(btnArriba, gbccControl);

        gbccControl.gridx = 0;
        gbccControl.gridy = 1;
        panelControl.add(btnIzquierda, gbccControl);

        gbccControl.gridx = 1;
        gbccControl.gridy = 1;
        panelControl.add(new JLabel(""), gbccControl); // Espacio vacío

        gbccControl.gridx = 2;
        gbccControl.gridy = 1;
        panelControl.add(btnDerecha, gbccControl);

        gbccControl.gridx = 1;
        gbccControl.gridy = 2;
        panelControl.add(btnAbajo, gbccControl);

        // Añadir panel de control de botones al JFrame
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panelControl, gbc);

        // Conectar al servidor al hacer clic en el botón de conexión
        botonConectar.addActionListener(e -> conectarServidor(campoIp.getText(), Integer.parseInt(campoPuerto.getText())));

        // Manejo de los botones presionados para el movimiento
        btnIzquierda.addActionListener(e -> manejarMovimiento("IZQUIERDA"));
        btnDerecha.addActionListener(e -> manejarMovimiento("DERECHA"));
        btnArriba.addActionListener(e -> manejarMovimiento("ARRIBA"));
        btnAbajo.addActionListener(e -> manejarMovimiento("ABAJO"));

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

    private void manejarMovimiento(String comando) {
        if (comando != null) {
            salida.println(nombreJugador + " " + comando); // Envía el comando al servidor
        }
    }

    private class EscuchaServidor implements Runnable {
        @Override
        public void run() {
            try {
                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    final String mensajeFinal = mensaje;
                    //System.out.println("Tablero recibido: \n" + mensaje);  // Debug: Mostrar el tablero recibido
                    SwingUtilities.invokeLater(() -> areaTablero.setText(mensajeFinal));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Iniciar el cliente con interfaz gráfica
        String nombreJugador = JOptionPane.showInputDialog("Ingresa tu nombre de jugador:");
        new ClienteDeMarioBrosConInterfaz("127.0.0.1", 8080, nombreJugador);  // Cambia la IP y puerto según tu configuración
    }
}
