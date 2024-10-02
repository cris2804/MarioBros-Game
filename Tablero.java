import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Arrays;

public class Tablero extends JPanel {
    private JTextArea textArea;
    private char[][] mapa;

    public Tablero(char[][] mapa) {
        this.mapa = mapa;
        this.textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(16f));
        setLayout(new BorderLayout());
        add(textArea, BorderLayout.CENTER);
        actualizarTexto();
    }
    private void actualizarTexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mapa.length; ++i) {
            for (int j = 0; j < mapa[i].length; ++j) {
                sb.append(mapa[i][j]);
            }
            sb.append("\n");
        }
        textArea.setText(sb.toString());
        textArea.revalidate();
        textArea.repaint();
    }

    public void actualizarPosicion(int idJugador, String accion) {
        // Aquí se manejará la lógica de actualización de posiciones basado en las acciones de los jugadores
        System.out.println("Jugador " + idJugador + " hizo: " + accion);
    }

    public void actualizarMapa(char[][] nuevoMapa) {
        this.mapa = nuevoMapa;
        actualizarTexto();
        System.out.println("Mapa actualizado:");
        /*
        for (char[] fila : mapa) {
            System.out.println(Arrays.toString(fila));
        }
        */
    }
}
