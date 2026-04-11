import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class GestorPersistencia {
    private final String RUTA_DATOS = "datos_aplicacion/";

    public GestorPersistencia() {
        File folder = new File(RUTA_DATOS);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void guardarUsuarios(List<Usuario> usuarios) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA_DATOS + "usuarios.csv"))) {
            for (Usuario u : usuarios) {
                String tipo = u.getClass().getSimpleName();
                String base = tipo + ";" + u.getId() + ";" + u.getUsername() + ";" + u.getEmail() + ";" + u.getPassword();
                
                if (u instanceof Cliente) {
                    base += ";" + ((Cliente) u).getPuntos() + ";N/A";
                } else if (u instanceof Mesero) {
                    List<String> conocidos = ((Mesero) u).getJuegosConocidos();
                    String juegosStr = conocidos != null && !conocidos.isEmpty() ? String.join(",", conocidos) : "Ninguno";
                    base += ";0;" + juegosStr;
                } else {
                    base += ";0;N/A";
                }
                writer.println(base);
            }
        }
    }

    public List<Usuario> cargarUsuarios() throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(RUTA_DATOS + "usuarios.csv");
        if (!file.exists()) return usuarios;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] p = linea.split(";");
                String tipo = p[0];
                int id = Integer.parseInt(p[1]);
                String user = p[2];
                String email = p[3];
                String pass = p[4];

                if (tipo.equals("Administrador")) {
                    usuarios.add(new Administrador(id, user, email, pass));
                } else if (tipo.equals("Cliente")) {
                    int puntos = Integer.parseInt(p[5]);
                    usuarios.add(new Cliente(id, user, email, pass, puntos));
                } else if (tipo.equals("Mesero")) {

                    List<String> juegosConocidos = new ArrayList<>();
                    if (p.length > 6 && !p[6].equals("Ninguno") && !p[6].equals("N/A")) {
                        juegosConocidos.addAll(Arrays.asList(p[6].split(",")));
                    }
                    usuarios.add(new Mesero(id, user, email, pass, null, new ArrayList<>(), juegosConocidos));
                } else if (tipo.equals("Cocinero")) {
                    usuarios.add(new Cocinero(id, user, email, pass, null));
                }
            }
        }
        return usuarios;
    }

    private void guardarInventario(List<Juego> juegos, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA_DATOS + archivo))) {
            for (Juego j : juegos) {
                writer.println(j.getId() + ";" + j.getNombre() + ";" + j.getAnioPublicacion() + ";" + 
                               j.getEmpresa() + ";" + j.getCategoria() + ";" + j.getMinJugadores() + ";" + 
                               j.getMaxJugadores() + ";" + j.getEdadMinima() + ";" + j.isEsDificil() + ";" + 
                               j.getCantidadTotal() + ";" + j.getCantidadVenta() + ";" + j.getCantidadPrestamo() + ";" + 
                               j.getCantidadPrestamoLibre() + ";" + j.getEstado() + ";" + j.getPrecio());
            }
        }
    }

    public void guardarInventarioVenta(List<Juego> juegos) throws IOException {
        guardarInventario(juegos, "juegos_venta.csv");
    }

    public void guardarInventarioPrestamo(List<Juego> juegos) throws IOException {
        guardarInventario(juegos, "juegos_prestamo.csv");
    }

    private List<Juego> cargarInventario(String archivo) throws IOException {
        List<Juego> juegos = new ArrayList<>();
        File file = new File(RUTA_DATOS + archivo);
        if (!file.exists()) return juegos;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] p = linea.split(";");
                
                Juego j = new Juego(
                    Integer.parseInt(p[0]), p[1], Integer.parseInt(p[2]), p[3], p[4], 
                    Integer.parseInt(p[5]), Integer.parseInt(p[6]), Integer.parseInt(p[7]), 
                    Boolean.parseBoolean(p[8]), Integer.parseInt(p[9]), Integer.parseInt(p[10]), 
                    Integer.parseInt(p[11]), Integer.parseInt(p[12]), p[13], Double.parseDouble(p[14])
                );
                
                juegos.add(j);
            }
        }
        return juegos;
    }

    public List<Juego> cargarInventarioVenta() throws IOException {
        return cargarInventario("juegos_venta.csv");
    }

    public List<Juego> cargarInventarioPrestamo() throws IOException {
        return cargarInventario("juegos_prestamo.csv");
    }


    public void guardarVentas(List<Venta> ventas) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA_DATOS + "ventas.csv"))) {
            for (Venta v : ventas) {
                String tipo = v.getClass().getSimpleName();
                writer.println(v.getId() + ";" + v.getFecha() + ";" + tipo + ";" + 
                               v.getSubtotal() + ";" + v.getTotal() + ";" + v.getComprador().getId());
            }
        }
    }

    public List<Venta> cargarVentas(List<Usuario> usuariosGlobales) throws IOException {
        List<Venta> ventas = new ArrayList<>();
        File file = new File(RUTA_DATOS + "ventas.csv");
        if (!file.exists()) return ventas;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] p = linea.split(";");
                int id = Integer.parseInt(p[0]);
                LocalDate fecha = LocalDate.parse(p[1]);
                String tipo = p[2];
                double subtotal = Double.parseDouble(p[3]);
                int idComprador = Integer.parseInt(p[5]);

                Usuario comprador = null;
                for (Usuario u : usuariosGlobales) {
                    if (u.getId() == idComprador) {
                        comprador = u;
                        break;
                    }
                }

                if (tipo.equals("VentaJuego")) {
                    ventas.add(new VentaJuego(id, fecha, subtotal, 0.0, comprador));
                } else if (tipo.equals("VentaCafeteria")) {
                    ventas.add(new VentaCafeteria(id, fecha, subtotal, comprador));
                }
            }
        }
        return ventas;
    }
}