import javax.swing.*;
import java.awt.*;

public class Tablero extends JPanel {
    private char[][] mapa;

    public Tablero(char[][] mapa) {
        this.mapa = mapa;
        setPreferredSize(new Dimension(800, 600)); // Ajusta el tamaño según tus necesidades
    }
    public void inicializarMapa() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                mapa[i][j] = '#'; // Puedes elegir el carácter que desees para las celdas vacías
            }
        }
        repaint(); // Repaint para asegurarte de que se muestra el mapa inicial
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarMapa(g);
    }

    private void dibujarMapa(Graphics g) {
        int cellSize = 30; // Tamaño de cada celda de la grilla
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                // Dibujar el fondo de la celda
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);

                // Dibujar el carácter en la celda
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(mapa[i][j]), j * cellSize + cellSize / 3, i * cellSize + 20);

                // Dibujar el borde de la celda
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    public void actualizarMapa(char[][] nuevoMapa) {
        this.mapa = nuevoMapa;
        repaint(); // Llama a repaint para actualizar la visualización
    }
}
