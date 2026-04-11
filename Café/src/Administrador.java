import java.util.List;

public class Administrador extends Usuario {

    public Administrador(int id, String username, String email, String password){
        super(id, username, email, password);
    }

    public String getEstadoJuego(Juego juego){
        return juego.getEstado();
    }

    public void setEstadoJuego(Juego juego, String newEstado){
        juego.setEstado(newEstado);
    }

    public void agregarJuegoPrestamo(Juego juego, int cantidad){
        juego.setCantidadPrestamo(juego.getCantidadPrestamo() + cantidad);
        juego.setCantidadPrestamoLibre(juego.getCantidadPrestamoLibre() + cantidad);
    }

    public void agregarJuegoVenta(Juego juego, int cantidad){
        juego.setCantidadVenta(juego.getCantidadVenta() + cantidad);
    }

    public List<Prestamo> verHistorialPrestamo(Juego juego){
        return juego.getHistorialPrestamos();
    }

    public void repararJuego(Juego juegoVenta, Juego juegoPrestamo){
        if(juegoVenta.getCantidadVenta() > 0) {
            juegoVenta.setCantidadVenta(juegoVenta.getCantidadVenta() - 1);
            juegoPrestamo.setCantidadPrestamo(juegoPrestamo.getCantidadPrestamo() + 1);
            juegoPrestamo.setCantidadPrestamoLibre(juegoPrestamo.getCantidadPrestamoLibre() + 1);
            juegoPrestamo.setEstado("Nuevo");
        } else {
            System.out.println("No hay copias en el inventario de venta para reparar este juego.");
        }
    }

    public boolean aprobarSugerenciaPlatillo(String sugerencia){
        return true;
    }

    public List<Venta> ventasDetalladas(List<Venta> historialVentas, String rubroSeleccionado, String fecha){
        return historialVentas;
    }
}