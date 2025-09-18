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
    }