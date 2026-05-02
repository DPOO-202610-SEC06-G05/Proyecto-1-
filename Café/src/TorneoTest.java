import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TorneoTest {
    private Juego juego;
    private Turno turno;
    private Torneo torneo;

    @BeforeEach
    public void setUp(){
        juego = new Juego(1, "Catan", 1995, "Kosmos", "Estrategia", 3, 4, 10, false, "Disponible", 120000);
        turno = new Turno("Lunes", "08:00", "12:00");
        torneo = new Torneo(juego, 10, true, turno, true);
    }

    @Test
    public void testInscribirClienteValido(){
        Cliente cliente = new Cliente(1, "Juan", "juan@mail.com", "123", 0, true);
        boolean resultado = torneo.inscribir(cliente, 2);
        assertTrue(resultado);
        assertEquals(1, torneo.getInscritos().size());
    }

    @Test
    public void testNoPermiteMasDeTresCupos(){
        Cliente cliente = new Cliente(1, "Juan", "juan@mail.com", "123", 0, true);
        boolean resultado = torneo.inscribir(cliente, 4);
        assertFalse(resultado);
    }

    @Test
    public void testDesinscribirCliente(){
        Cliente cliente = new Cliente(1, "Juan", "juan@mail.com", "123", 0, true);
        torneo.inscribir(cliente, 2);
        boolean resultado = torneo.desinscribir(cliente);
        assertTrue(resultado);
        assertEquals(0, torneo.getInscritos().size());
    }

    @Test
    public void testEmpleadoEnTurnoNoPuedeInscribirse(){
        List<String> favoritos = new ArrayList<>();
        List<String> conocidos = new ArrayList<>();
        Mesero mesero = new Mesero(1, "Carlos", "c@mail.com", "123", turno, favoritos, conocidos, true);
        boolean resultado = torneo.inscribir(mesero, 1);
        assertFalse(resultado);
    }
}