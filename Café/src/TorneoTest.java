import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TorneoTest {

    @Test
    public void testInscribirClienteValido() {
        Juego juego = new Juego(1, "Catan", 1995, "Kosmos", "Estrategia", 3, 4, 10, false, "Disponible", 120000);
        Turno turno = new Turno("Lunes", "08:00", "12:00");
        Torneo torneo = new Torneo(juego, 10, true, turno);
        Cliente cliente = new Cliente(1, "Juan", "juan@mail.com", "123", 0);
        boolean resultado = torneo.inscribir(cliente, 2);

        assertTrue(resultado);
        assertEquals(1, torneo.getInscritos().size());
    }
}