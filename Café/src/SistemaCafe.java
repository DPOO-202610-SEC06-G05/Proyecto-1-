import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaCafe {
    private GestorPersistencia persistencia;
    private List<Usuario> usuariosSistema;
    private List<InventarioPrestamo> inventarioPrestamo;
    private List<InventarioVenta> inventarioVenta;
    private HistorialVenta historialVentasGlobal;
    private HistorialPrestamo historialPrestamoGlobal;
    private Cafe cafe;

    public SistemaCafe() {
        this.persistencia = new GestorPersistencia();
        this.usuariosSistema = new ArrayList<>();
        this.inventarioPrestamo = new ArrayList<>();
        this.inventarioVenta = new ArrayList<>();
        
        this.historialVentasGlobal = new HistorialVenta();
        this.historialPrestamoGlobal = new HistorialPrestamo();
        this.cafe = new Cafe(50);
    }

    public void arrancarSistema() {
        try {
            System.out.println("Iniciando y cargando datos :)");
            this.usuariosSistema = persistencia.cargarUsuarios();
            this.inventarioPrestamo = persistencia.cargarInventarioPrestamo();
            this.inventarioVenta = persistencia.cargarInventarioVenta();

            List<Venta> ventasCargadas = persistencia.cargarVentas();
            for (Venta v : ventasCargadas) {
                this.historialVentasGlobal.registrarVenta(v);
            }

            List<Prestamo> prestamosCargados = persistencia.cargarPrestamos();
            for (Prestamo p : prestamosCargados) {
                this.historialPrestamoGlobal.registrarPrestamo(p);
            }

            System.out.println("Datos cargados correctamente :D");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }
    }

    public void apagarSistema() {
        try {
            System.out.println("Guardando ;)");
            persistencia.guardarUsuarios(this.usuariosSistema);
            persistencia.guardarInventarioPrestamo(this.inventarioPrestamo);
            persistencia.guardarInventarioVenta(this.inventarioVenta);
            persistencia.guardarVentas(this.historialVentasGlobal.getVentas());
            persistencia.guardarPrestamos(this.historialPrestamoGlobal.getPrestamos());

            System.out.println("Hecho :D");
        } catch (IOException e) {
            System.out.println("Error de guardado :( " + e.getMessage());
        }
    }

    public void registrarNuevaVenta(Venta venta) {
        this.historialVentasGlobal.registrarVenta(venta);
    }

    public void registrarNuevoPrestamo(Prestamo prestamo) {
        this.historialPrestamoGlobal.registrarPrestamo(prestamo);
    }

    public Pedido crearPedido(int id, Mesa mesa) {
        return new Pedido(id, LocalDate.now(), mesa);
    }

    public VentaCafeteria cerrarPedido(Pedido pedido, int idVenta) {
        double subtotal = pedido.calcularTotal();
        double impuestoConsumo = subtotal * 0.08;
        double propina = subtotal * 0.10;
        double total = subtotal + impuestoConsumo + propina;

        VentaCafeteria venta = new VentaCafeteria(idVenta, LocalDate.now(), total, impuestoConsumo, propina);
        this.historialVentasGlobal.registrarVenta(venta);
        pedido.terminarPedido();

        return venta;
    }

    public Usuario buscarUsuario(int id) {
        for (Usuario u : usuariosSistema) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public List<Juego> juegosDisponiblesPrestamo() {
        List<Juego> disponibles = new ArrayList<>();

        for (InventarioPrestamo inv : inventarioPrestamo) {
            if (inv.getCantidadDisponible() > 0) {
                disponibles.add(inv.getJuego());
            }
        }

        return disponibles;
    }

    public static void main(String[] args) {
        SistemaCafe sistema = new SistemaCafe();
        sistema.arrancarSistema();

        System.out.println("Juegos en inventario de préstamo: " + sistema.inventarioPrestamo.size());
        System.out.println("Juegos en inventario de venta: " + sistema.inventarioVenta.size());
        System.out.println("Ventas históricas registradas: " + sistema.historialVentasGlobal.getVentas().size());
        System.out.println("Préstamos históricos registrados: " + sistema.historialPrestamoGlobal.getPrestamos().size());

        sistema.apagarSistema();
    }
}