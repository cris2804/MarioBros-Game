public class Tablero {
    private char[][] mapa;

    public Tablero() {
        mapa = new char[20][40]; // 20 filas, 40 columnas
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                mapa[i][j] = '-'; // Inicializar con espacios vacíos
            }
        }
    }

    public void actualizarPosicion(int idJugador, String accion) {
        // Aquí se manejará la lógica de actualización de posiciones basado en las acciones de los jugadores
        System.out.println("Jugador " + idJugador + " hizo: " + accion);
    }

    @Override
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
