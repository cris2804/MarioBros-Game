public class Tablero {
    private char[][] mapa;
    private int[] posicionesX;
    private int[] posicionesY;
    private final char VACIO = '#';
    private final char JUGADOR = 'J';


    public Tablero() {
        mapa = new char[20][40]; // 20 filas, 40 columnas
        inicializarTablero();
        posicionesX = new int[10];
        posicionesY = new int[10];
        inicializarPosiciones();
    }

    void inicializarTablero() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                mapa[i][j] = VACIO; // Inicializar con espacios vacíos
            }
        }
    }
    void inicializarPosiciones() {
        for (int i = 0; i < posicionesX.length; ++i) {
            posicionesX[i] = i;
            posicionesY[i] = i;
            mapa[posicionesX[i]][posicionesY[i]] = JUGADOR;
        }
    }
    public void actualizarPosicion(int idJugador, String accion) {
        int lastX = posicionesX[idJugador - 1];
        int lastY = posicionesY[idJugador - 1];
        // Determinar nueva posición
        int newX = lastX;
        int newY = lastY;

        switch (accion) {
            case "ARRIBA":
                if (lastX > 0) newX--;
                break;
            case "ABAJO":
                if (lastX < mapa.length - 1) newX++;
                break;
            case "IZQUIERDA":
                if (lastY > 0) newY--;
                break;
            case "DERECHA":
                if (lastY < mapa[0].length - 1) newY++;
                break;
        }

        // Verificar si la nueva posición está ocupada por otro jugador
        boolean hayJugadorEnNuevaPosicion = false;
        for (int i = 0; i < posicionesX.length; i++) {
            if (i != idJugador - 1 && posicionesX[i] == newX && posicionesY[i] == newY) {
                hayJugadorEnNuevaPosicion = true; // Hay otro jugador en la nueva posición
                break;
            }
        }

        // Si hay un jugador en la nueva posición, simplemente superponemos
        if (!hayJugadorEnNuevaPosicion) {
            // Si no hay jugadores, limpiamos la posición anterior y actualizamos la posición
            mapa[lastX][lastY] = VACIO; // Limpiar la posición anterior
            posicionesX[idJugador - 1] = newX; // Actualizar posición del jugador
            posicionesY[idJugador - 1] = newY; // Actualizar posición del jugador
        }

        // Colocar al jugador en su nueva posición
        // Esto permite que se superpongan sin eliminar a otros jugadores
        mapa[posicionesX[idJugador - 1]][posicionesY[idJugador - 1]] = JUGADOR;

        //System.out.println("Jugador " + idJugador + " hizo: " + accion);
    }
    public String[] obtenerRepresentacion() {
        String[] representacion = new String[mapa.length];
        for (int i = 0; i < mapa.length; i++) {
            representacion[i] = new String(mapa[i]); // Convierte cada fila del mapa en una cadena
        }
        return representacion;
    }

    //@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                sb.append(mapa[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
