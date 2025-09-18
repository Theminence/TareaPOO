import java.util.Scanner;


/**
* Presenta interfaz de usuario en consola para el
* juego Left-Center-Right.
*
* @author (Cecilia Curlango Rosas)
* @version 1.0 (agosto 2025)
*/
public class UILCRGameApplicacion
{
   private LCRGame juego;
   private Scanner scanner;


   public UILCRGameApplicacion() {
       juego = new LCRGame();
       scanner = new Scanner(System.in);
   }


   /**
    * Juega el juego LEFT-CENTER-RIGHT
    */
   public void jugar()
    {
       System.out.println("¡Bienvenido al juego Left-Center-Right!");
       System.out.print("¿Cuántos jugadores van a jugar? (mínimo 3): ");
      
       int cantidadJugadores = scanner.nextInt();
      
       // Validar que haya al menos 3 jugadores
       while (cantidadJugadores < 3) {
           System.out.print("El juego requiere al menos 3 jugadores. Ingrese nuevamente: ");
           cantidadJugadores = scanner.nextInt();
       }
      
       // Consumir el salto de línea que queda después de nextInt()
       scanner.nextLine();
      
       juego.crearJugadores(cantidadJugadores);
      
       // Solicitar nombres de los jugadores
       System.out.println("\nAhora ingresa los nombres de los jugadores:");
       for (int i = 0; i < cantidadJugadores; i++) {
           System.out.print("Nombre del jugador " + (i + 1) + ": ");
           String nombre = scanner.nextLine();
           juego.getJugadores().get(i).setNombre(nombre);
       }
      
       System.out.println("\n¡Jugadores registrados!");
       for (int i = 0; i < juego.getJugadores().size(); i++) {
           System.out.println("- " + juego.getJugadores().get(i).getNombre());
       }
      
       int primerJugador = juego.encontrarPrimerJugador();
       System.out.println("El jugador " + primerJugador + " comenzará el juego.");
      
       System.out.print("\nPresiona Enter para comenzar el juego...");
       scanner.nextLine();


       do {
           // Mostrar quién es el jugador actual
           System.out.println("\n--- Turno de " + juego.getJugadores().get(juego.getTurno() - 1).getNombre() + " ---");
          
           boolean puedeLanzar = juego.lanzarDados();
          
           if (puedeLanzar) {
               juego.procesarResultados();
               mostrarJugada();
           } else {
               // El jugador no tiene fichas, pasa turno
               System.out.println(juego.getJugadores().get(juego.getTurno() - 1).getNombre() +
                                " no tiene fichas, pasa turno.");
           }
          
           // Pausa para continuar al siguiente turno
           if (!juego.esFinDeJuego()) {
               System.out.print("\nPresiona Enter para continuar al siguiente turno...");
               scanner.nextLine();
           }
          
           juego.cambiarTurno();
           juego.establecerJugadores();
       }while(!juego.esFinDeJuego());


       // mostrar ganador
       System.out.println("¡Felicidades! El ganador es: " + juego.getGanador().getNombre());
   }


   /**
    * Muestra los dados, fichas de los jugadores y del centro.
    */
   private void mostrarJugada() {
       // Mostrar solo los dados que se lanzaron
       System.out.print("Dados lanzados: ");
       int dadosLanzados = juego.getDadosLanzados();
      
       switch (dadosLanzados) {
           case 1:
               System.out.println(juego.getDado1().getValor());
               break;
           case 2:
               System.out.print(juego.getDado1().getValor() + " ");
               System.out.println(juego.getDado2().getValor());
               break;
           case 3:
               System.out.print(juego.getDado1().getValor() + " ");
               System.out.print(juego.getDado2().getValor() + " ");
               System.out.println(juego.getDado3().getValor());
               break;
           default:
               System.out.println("Ninguno (no tiene fichas)");
               break;
       }
      
       // Mostrar fichas de todos los jugadores dinámicamente
       System.out.println("Estado actual de fichas:");
       for (int i = 0; i < juego.getJugadores().size(); i++) {
           Jugador jugador = juego.getJugadores().get(i);
           String indicador;
           if (i == juego.getTurno() - 1) {
               indicador = " ← JUGANDO";
           } else {
               indicador = "";
           }
           System.out.println("  " + jugador.getNombre() + ": " + jugador.getFichas() + " fichas" + indicador);
       }
      
       System.out.println("  Centro: " + juego.getCentro().getFichas() + " fichas");
   }
  
   /**
    * Método principal para ejecutar el juego.
    */
   public static void main(String[] args) {
       UILCRGameApplicacion ui = new UILCRGameApplicacion();
       ui.jugar();
   }
}




