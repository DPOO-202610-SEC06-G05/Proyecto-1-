import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SistemaCafe {
    private GestorPersistencia persistencia;
    private List<Usuario> usuariosSistema;
    private List<Juego> inventarioPrestamo;
    private List<Juego> inventarioVenta;
    
    private List<Venta> historialVentasGlobal; 

    public SistemaCafe() {
        this.persistencia = new GestorPersistencia();
        this.usuariosSistema = new ArrayList<>();
        this.inventarioPrestamo = new ArrayList<>();
        this.inventarioVenta = new ArrayList<>();
        
        this.historialVentasGlobal = new ArrayList<>(); 
    }

    public void arrancarSistema() {
        try {
            System.out.println("Iniciando sistema y cargando datos...");
            this.usuariosSistema = persistencia.cargarUsuarios();
            this.inventarioPrestamo = persistencia.cargarInventarioPrestamo();
            this.inventarioVenta = persistencia.cargarInventarioVenta();
            
            this.historialVentasGlobal = persistencia.cargarVentas(this.usuariosSistema);
            
            System.out.println("¡Datos cargados correctamente!");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }
    }

    public void apagarSistema() {
        try {
            System.out.println("Guardando el estado del sistema...");
            persistencia.guardarUsuarios(this.usuariosSistema);
            persistencia.guardarInventarioPrestamo(this.inventarioPrestamo);
            persistencia.guardarInventarioVenta(this.inventarioVenta);
            persistencia.guardarVentas(this.historialVentasGlobal);
            
            System.out.println("¡Datos guardados exitosamente. Hasta pronto!");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public void registrarNuevaVenta(Venta venta) {
        this.historialVentasGlobal.add(venta);
    }

    public static void main(String[] args) {
        SistemaCafe cafe = new SistemaCafe();
        cafe.arrancarSistema();

        System.out.println("Juegos cargados: " + cafe.inventarioPrestamo.size());
        System.out.println("Ventas históricas registradas: " + cafe.historialVentasGlobal.size());
        
        cafe.apagarSistema();
    }
}