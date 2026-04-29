import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaCafe {
    private GestorPersistencia persistencia;
    private List<Usuario> usuariosSistema;
    private List<InventarioPrestamo> inventarioPrestamo;
    private List<InventarioVenta> inventarioVenta;
    private HistorialVenta historialVentasGlobal;
    private HistorialPrestamo historialPrestamoGlobal;
    private Cafe cafe;
    private int clientesActuales;


    private List<Torneo> torneos;
    private Cliente cliente;
    private Torneo torneo;
    private Juego juego;
    private Turno turno;
    private Empleado empleado;

    public SistemaCafe() {
        this.persistencia = new GestorPersistencia();
        this.usuariosSistema = new ArrayList<Usuario>();
        this.inventarioPrestamo = new ArrayList<InventarioPrestamo>();
        this.inventarioVenta = new ArrayList<InventarioVenta>();
        
        this.historialVentasGlobal = new HistorialVenta();
        this.historialPrestamoGlobal = new HistorialPrestamo();
        this.cafe = new Cafe(50);
        this.clientesActuales = 0;
        this.torneos = new ArrayList<>();
    }

    public void arrancarSistema() {
        try {
            System.out.println("Iniciando y cargando datos :)");
            this.usuariosSistema = persistencia.cargarUsuarios();
            this.inventarioPrestamo = persistencia.cargarInventarioPrestamo();
            this.inventarioVenta = persistencia.cargarInventarioVenta();

            List<Venta> ventasCargadas = persistencia.cargarVentas(this.usuariosSistema);
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

    public Pedido crearPedido(int id, Mesa mesa, int cantidadPersonas) {
        boolean pudoEntrar = intentarIngresarClientes(cantidadPersonas);
        if (!pudoEntrar) {
            System.out.println("No se pudo crear. Supera capacidad máxima de personas");
            return null;
        }

        return new Pedido(id, LocalDate.now(), mesa, cantidadPersonas);
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

    public boolean intentarIngresarClientes(int cantidadPersonas) {
    int capacidadMax = cafe.getCapacidadMax();

        if (clientesActuales + cantidadPersonas > capacidadMax) {
            System.out.println("Se alcanzó la capacidad máxima. No puede ingresar");
            return false;
        }

        clientesActuales += cantidadPersonas;
        System.out.println("Se pudo ingresar. # Clientes actuales: "+ clientesActuales);
        return true;
    }

    public void salirClientes(int cantidadPersonas) {
        clientesActuales -= cantidadPersonas;

        if (clientesActuales < 0) {
            clientesActuales = 0;
        }
        
        System.out.println("Clientes actuales: "+clientesActuales);
    }

        /*
    public static void main(String[] args) {
        SistemaCafe sistema = new SistemaCafe();
        sistema.arrancarSistema();

        System.out.println("Juegos en inventario de préstamo: " + sistema.inventarioPrestamo.size());
        System.out.println("Juegos en inventario de venta: " + sistema.inventarioVenta.size());
        System.out.println("Ventas históricas registradas: " + sistema.historialVentasGlobal.getVentas().size());
        System.out.println("Préstamos históricos registrados: " + sistema.historialPrestamoGlobal.getPrestamos().size());

        sistema.apagarSistema();
    }
    */




    public void crearTorneo(Juego juego, int cupos, boolean esAmistoso, Turno turno){
        Torneo t = new Torneo(juego, cupos, esAmistoso, turno);
        torneos.add(t);
    }
    public List<Torneo> getTorneos(){
        return torneos;
    }
    public Torneo buscarTorneo(Juego juego){
        for(Torneo t : torneos){
            if(t.getJuego().equals(juego)){
                return t;
            }
        }
        return null;
    }
    /* 
    public static void main(String[] args){
        SistemaCafe sistema = new SistemaCafe();
    
        Juego juego = new Juego(1,"Catan", 1995, "Kosmos", "Estrategia",
            3, 4, 10, false, "Disponible", 120000);
    
        Turno turno = new Turno("Lunes", "08:00", "12:00");
        sistema.crearTorneo(juego, 10, true, turno);
        Torneo torneo = sistema.buscarTorneo(juego);
        Cliente c1 = new Cliente(1, "Juan", "juan@mail.com", "123", 0);
        Cliente c2 = new Cliente(2, "Ana", "ana@mail.com", "123", 0);
        torneo.inscribir(c1, 2);
        torneo.inscribir(c2, 1);
    
        System.out.println("Inscritos: "+torneo.getInscritos().size());
        torneo.desinscribir(c1);
        System.out.println("Inscritos después: "+torneo.getInscritos().size());
    }
    */


     /*
    public static void main(String[] args) {
        SistemaCafe sistema = new SistemaCafe();
        Juego juego = new Juego(1, "Catan", 1995, "Kosmos", "Estrategia",
            3, 4, 10, false, "Disponible", 120000);
    
        Turno turno = new Turno("Lunes", "08:00", "12:00");
        sistema.crearTorneo(juego, 10, true, turno);
        Torneo amistoso = sistema.getTorneos().get(0);
    
        Cliente c1 = new Cliente(1, "Juan", "juan@mail.com", "123", 0);
        Cliente c2 = new Cliente(2, "Ana", "ana@mail.com", "123", 0);
        amistoso.inscribir(c1, 2);
        amistoso.inscribir(c2, 1);
    
        System.out.println("----- TORNEO AMISTOSO -----");
        amistoso.finalizarTorneo(c1);
    
        sistema.crearTorneo(juego, 10, false, turno);
        Torneo competitivo = sistema.getTorneos().get(1);
        Cliente c3 = new Cliente(3, "Pedro", "p@mail.com", "123", 0);
        Cliente c4 = new Cliente(4, "Luis", "l@mail.com", "123", 0);
        competitivo.inscribir(c3, 2);
        competitivo.inscribir(c4, 1);
        System.out.println("----- TORNEO COMPETITIVO -----");
        competitivo.finalizarTorneo(c3);
    
        List<String> favoritos = new ArrayList<>();
        favoritos.add("Catan");
        List<String> conocidos = new ArrayList<>();
        conocidos.add("Catan");
        Mesero e = new Mesero(5, "Carlos", "c@mail.com", "123", turno, favoritos, conocidos);
    
        competitivo.inscribir(e, 1);
        System.out.println("----- EMPLEADO GANADOR -----");
        competitivo.finalizarTorneo(e);
    }
    */

    public static void main(String[] args) {
        SistemaCafe sistema = new SistemaCafe();
        sistema.menuPrincipla();
    }

    public void menuPrincipla(){
        juego = new Juego(1, "Catan", 1995, "Kosmos", "Estrategia", 3, 4, 10, false, "Disponible", 120000);
        turno = new Turno("Lunes", "08:00", "12:00");
        torneo = new Torneo(juego, 10, true, turno);
        usuariosSistema.add(new Cliente(1, "Juan", "juan", "123", 0));
        usuariosSistema.add(new Mesero(2, "Carlos", "carlos", "123", turno, new ArrayList<>(), new ArrayList<>()));
        usuariosSistema.add(new Administrador(3, "Mariana", "maraina@gmail.com", "123"));
        Scanner sc = new Scanner(System.in);
    
        System.out.println("1. Inicciar sesion");
        System.out.println("2. Registrarse");
        int opcion = sc.nextInt();
        if(opcion == 1){
            Usuario usuario = login(sc);
            if(usuario == null){
                return;
            }
            if(usuario instanceof Cliente){
                menuCliente(sc, (Cliente) usuario);
            }
            if(usuario instanceof Empleado){
                menuEmpleado(sc, (Empleado) usuario);
            }
            if(usuario instanceof Administrador){
                menuAdmin(sc);
            }
        }
        if (opcion == 2 ){
            registrarCliente(sc);
        }
        
    }

    public void menuCliente(Scanner sc, Cliente cliente){
        System.out.println("==MENU CLIENTE==");
        System.out.println("1. Inscribirse a torneo");
        System.out.println("2. Desinscribirse");
        int opcion = sc.nextInt();
        if(opcion == 1){
            boolean resultado = torneo.inscribir(cliente, 2);
            System.out.println(resultado ? "Inscripción exitosa" : "No se pudo inscribir");
        }
        if(opcion == 2){
            boolean resultado = torneo.desinscribir(cliente);
            System.out.println(resultado ? "Desinscripción exitosa" : "No se pudo desinscribir");
        }
    }

    public void menuAdmin(Scanner sc){
        System.out.println("==MENU ADMINISTRADOR==");
        System.out.println("1. Crear torneo");
        System.out.println("2. Ver torneos");
        System.out.println("3. Buscar Usuarios");
        System.out.println("4. Registrar Empleado");

        int opcion = sc.nextInt();
        if(opcion == 1){
            crearTorneo(juego, 10, true, turno);;
            System.out.println("torneo creado!");
            System.out.println("Cantidad de torneos actuales"+torneos.size());
        }
        if(opcion == 2){
            System.out.println("Cantidad de torneos actuales"+torneos.size());
        }
        if(opcion == 3){
            boolean ecnontrado = false;
            System.out.println("Porfavor ingrese el username de la persona: ");
            String username = sc.next();
            for(Usuario u: usuariosSistema){
                if(username.equals(u.getUsername())){
                    System.out.println("Usuario encontrado: " + u.getUsername());
                    ecnontrado = true;
                }
            }
            if(!ecnontrado){
                System.out.println("El usuario " + username + " no existe. ");   
            }
        }
        if(opcion == 4){
            System.out.println("El empleado que rol tendra: mesero o Cocinero: ");
            String oempleado = sc.next();
            if(oempleado.equalsIgnoreCase("Mesero")){
                System.out.println("Cual es el id del empleado: ");
                Integer id = sc.nextInt();
                System.out.println("Como se llama el empelado ");
                String username = sc.next();
                System.out.println("Como es el email de el empelado ");
                String email = sc.next();
                System.out.println("Cual sera la password de el empelado ");
                String password = sc.next();
                Empleado nEmpleado = new Mesero(id, username, email, password, turno, new ArrayList<>(), new ArrayList<>());
                usuariosSistema.add(nEmpleado);
                System.out.println("Felicidades el empleado " + username + " se ha registrado con exito. ");
            }else if (oempleado.equalsIgnoreCase("Cocinero")){
                System.out.println("Cual es el id del empleado: ");
                Integer id = sc.nextInt();
                System.out.println("Como se llama el empelado ");
                String username = sc.next();
                System.out.println("Como es el email de el empelado ");
                String email = sc.next();
                System.out.println("Cual sera la password de el empelado ");
                String password = sc.next();
                Empleado nEmpleado = new Cocinero(id, username, email, password, turno);
                usuariosSistema.add(nEmpleado);
                System.out.println("Felicidades el empleado " + username + " se ha registrado con exito. ");
            }else{
                System.out.println("Rol invalido. ");

            }
        }
    }

    public void menuEmpleado(Scanner sc, Empleado empleado){
        System.out.println("==MENU EMPLEADO==");
        System.out.println("1. Inscribirse a torneo");
        int opcion = sc.nextInt();
        if(opcion == 1){
            boolean resultado = torneo.inscribir(empleado, 1);
            System.out.println(resultado ? "Empleado inscrito" : "No se pudo inscribir el empleado");
        }
    }

    public Usuario login(Scanner sc){
        System.out.println("Ingrese username:");
        String username = sc.next();
        System.out.println("Ingrese password:");
        String password = sc.next();
        for(Usuario u : usuariosSistema){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                System.out.println("Login exitoso!");
                return u;
            }
        }
        System.out.println("Credenciales incorrectas");
        return null;
    }

    public void registrarCliente(Scanner sc){
        System.out.println("Ingrese username: ");
        String username = sc.next();
        System.out.println("Ingrese email: ");
        String email = sc.next();
        System.out.println("Ingrese password: ");
        String password = sc.next();
        Cliente nCliente = new Cliente(usuariosSistema.size() + 1, username, email, password, 0);
        usuariosSistema.add(nCliente);
        System.out.println("Felicidades " + username + " te has registrado con exito. ");
    }
}