
import java.util.ArrayList;

/**
 * Juego Left-Center-Write.
 *
 * Esta versión se desarrolló en el salón de clase.
 * 
 * @author (Cecilia Curlango Rosas)
 * @version (1.0 agosto 2025)
 */
public class LCRGame
{
    private DadoLCR dado1, dado2, dado3;
    private ArrayList<Jugador> jugadores;
    private Jugador jugadorActual, jugadorIzquierda, jugadorDerecha;
    private Jugador jugadorGanador;
    private int turno;
    private int dadosLanzados; // Cantidad de dados lanzados en el turno actual
    private Centro centro;

    /**
     * Configura el juego a su estado inicial.
     * Crea los dados y los jugadores.
     */
    public LCRGame() {
        dado1 = new DadoLCR();
        dado2 = new DadoLCR();
        dado3 = new DadoLCR();
        centro = new Centro();
        jugadores = new ArrayList<Jugador>();
    }

    /**
     * Determina si todos los jugadores excepto uno se
     * quedaron sin fichas. En ese caso, hay 
     * ganador. El jugador ganador se asigna.
     * @return true si sólo queda un jugador con fichas.
     */
    public boolean esFinDeJuego() {
        int jugadoresEnCero = 0;
        boolean seTermino = false;
        
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            if (jugador.getFichas() == 0) {
                jugadoresEnCero++;
            } else {
                jugadorGanador = jugador;
            }
        }
        
        if (jugadoresEnCero == jugadores.size() - 1) {
            seTermino = true;
        }
        return seTermino;
    }
    /**
     * Regresa al jugador que ganó la partida.
     * @return jugador ganador; si no se ha terminado
     * el juego, regresa null.
     */
    public Jugador getGanador() {
        if (!esFinDeJuego()) {
            return null;
        }
        return jugadorGanador;
    }
    /**
     * Según los valores de los dados que lanzó
     * el jugador actual, reparte las fichas.
     */
    public void procesarResultados() {
        int cuantosDados = jugadorActual.getFichas();
        char cara;
        switch(cuantosDados) {
            case 0:
                break;
            case 1:
                cara = dado1.getValor();
                procesarCara(cara);
                break;
            case 2:
                cara = dado1.getValor();
                procesarCara(cara);
                cara = dado2.getValor();
                procesarCara(cara);
                break;
            default:
                cara = dado1.getValor();
                procesarCara(cara);
                cara = dado2.getValor();
                procesarCara(cara);
                cara = dado3.getValor();
                procesarCara(cara);
                break;    
        }
    }

    /**
     * Quita una ficha al jugador actual y 
     * se la entrega al centro, al de la izquierda
     * o al de la derecha, según lo que valga la cara.
     * @param cara del dado.
     */
    private void procesarCara(char cara){
        switch(cara) {
            case 'C':
                jugadorActual.retirarFichas(1);
                centro.agregarFichas(1);
                break;
            case 'L':
                jugadorActual.retirarFichas(1);
                jugadorIzquierda.agregarFichas(1);
                break;
            case 'R':
                jugadorActual.retirarFichas(1);
                jugadorDerecha.agregarFichas(1);
                break;
        }
    }

    /**
     * Cambia el número del jugador en turno.
     */
    public void cambiarTurno() {
        turno++;
        if (turno > jugadores.size()) {
            turno = 1;
        }
    }

    /**
     * Según quien sea el jugador en turno,
     * asigna al jugador actual, al de la izquierda y
     * al de la derecha.
     */
    public void establecerJugadores() {
        if (jugadores.size() >= 3) {
            jugadorActual = jugadores.get(turno - 1);
            
            // Jugador de la izquierda (anterior en el círculo)
            int indiceIzquierda = (turno - 2 + jugadores.size()) % jugadores.size();
            jugadorIzquierda = jugadores.get(indiceIzquierda);
            
            // Jugador de la derecha (siguiente en el círculo)
            int indiceDerecha = turno % jugadores.size();
            jugadorDerecha = jugadores.get(indiceDerecha);
        }
    }

    
}