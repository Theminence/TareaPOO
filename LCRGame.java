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


   /**
    * Lanza los dados del juego. La cantidad
    * de dados que se lanza depende de la cantidad
    * de fichas que tiene el jugador actual.
    * @return true si el jugador pudo lanzar dados, false si no tiene fichas
    */
   public boolean lanzarDados() {
       int fichas = jugadorActual.getFichas();
       switch (fichas) {
           case 0:
               dadosLanzados = 0;
               return false; // No puede lanzar dados, no tiene fichas
           case 1:
               lanzar1Dado();
               dadosLanzados = 1;
               return true;
           case 2:
               lanzar2Dados();
               dadosLanzados = 2;
               return true;
           default:
               lanzar3Dados();
               dadosLanzados = 3;
               return true;
       }
   }


  


   /**
    * Crea los jugadores con nombres predeterminados.
    * @param cantidadJugadores número de jugadores a crear
    */
   public void crearJugadores(int cantidadJugadores) {
       jugadores.clear(); // Limpiar lista existente
      
       for (int i = 1; i <= cantidadJugadores; i++) {
           Jugador nuevoJugador = new Jugador();
           nuevoJugador.setNombre("Jugador " + i);
           jugadores.add(nuevoJugador);
       }
   }
  


   /**
    * Determina por suerte cuál de los jugadores
    * iniciará el juego. Lanza los tres dados
    * para cada jugador y el que tenga la mayor
    * cantidad de asteriscos (*) es el que empieza el juego.
    * @return el número del jugador que iniciará (1-n).
    */
   public int encontrarPrimerJugador() {
       int primerJugador = 1;
       int maxPuntos = -1; // Inicializar con -1 para asegurar que el primer jugador se registre
      
       System.out.println("=== Determinando quién inicia el juego ===");
      
       for (int i = 0; i < jugadores.size(); i++) {
           jugadorActual = jugadores.get(i);
           lanzar3Dados();
           int puntosActuales = contarPuntos();
          
           // Mostrar qué le cayó a cada jugador
           System.out.print(jugadorActual.getNombre() + " lanzó: ");
           System.out.print(dado1.getValor() + " ");
           System.out.print(dado2.getValor() + " ");
           System.out.print(dado3.getValor() + " ");
           System.out.println("-> " + puntosActuales + " puntos (*)");
          
           // El jugador con más asteriscos es el que inicia
           if (puntosActuales > maxPuntos) {
               maxPuntos = puntosActuales;
               primerJugador = i + 1;
           }
       }
      
       System.out.println("El jugador con más puntos: " + jugadores.get(primerJugador - 1).getNombre());
       System.out.println("================================");
      
       turno = primerJugador;
       establecerJugadores();
       return primerJugador;
   }


   /**
    * Cuenta cuantos dados caen en punto.
    * @return cantidad de dados que tienen un punto.
    */
   private int contarPuntos() {
       int contador = 0;
       if (dado1.getValor() == '*') {
           contador++;
       }
       if (dado2.getValor() == '*') {
           contador++;
       }
       if (dado3.getValor() == '*') {
           contador++;
       }
       return contador;
   }


   /**
    * Lanza los 1 dado.
    */
   private void lanzar1Dado() {
       dado1.lanzar();
   }


   /**
    * Lanza los 2 dados.
    */
   private void lanzar2Dados() {
       dado1.lanzar();
       dado2.lanzar();
   }


   /**
    * Lanza los 3 dados.
    */
   private void lanzar3Dados() {
       dado1.lanzar();
       dado2.lanzar();
       dado3.lanzar();
   }
   /**
    * Regresa la lista de jugadores.
    * @return ArrayList de jugadores
    */
   public ArrayList<Jugador> getJugadores() {
       return jugadores;
   }
  
   /**
    * Regresa el primer dado.
    * @return primer dado del juego
    */
   public DadoLCR getDado1() {
       return dado1;
   }
   /**
    * Regresa el segundo dado.
    * @return segundo dado del juego
    */
   public DadoLCR getDado2() {
       return dado2;
   }
   /**
    * Regresa el tercer dado.
    * @return tercer dado del juego
    */
   public DadoLCR getDado3() {
       return dado3;
   }
   /**
    * Regresa el centro del juego.
    * @return centro del juego
    */
   public Centro getCentro() {
       return centro;
   }
  
   /**
    * Regresa el número del turno actual.
    * @return número del turno actual
    */
   public int getTurno() {
       return turno;
   }
  
   /**
    * Regresa la cantidad de dados lanzados en el turno actual.
    * @return cantidad de dados lanzados
    */
   public int getDadosLanzados() {
       return dadosLanzados;
   }
  
}


