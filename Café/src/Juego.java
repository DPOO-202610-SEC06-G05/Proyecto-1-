import java.util.List;
import java.util.ArrayList;

public class Juego {
    private int id;
    private String nombre;
    private int anioPublicacion;
    private String empresa;
    private String categoria;
    private int minJugadores;
    private int maxJugadores;
    private int edadMinima;
    private String estado;
    private boolean esDificil;
    private double precio; 
    private List<Prestamo> historialPrestamos;
    private int cantidadTotal;
    private int cantidadVenta;
    private int cantidadPrestamo;
    private int cantidadPrestamoLibre;

    public Juego(int id, String nombre, int anioPublicacion, String empresa, String categoria, int minJugadores, int maxJugadores, int edadMinima, boolean esDificil, int cantidadTotal, double precio){
        this.id = id;
        this.nombre = nombre;
        this.anioPublicacion = anioPublicacion;
        this.empresa = empresa;
        this.categoria = categoria;
        this.minJugadores = minJugadores;
        this.maxJugadores = maxJugadores;
        this.edadMinima = edadMinima;
        this.estado = "Nuevo";
        this.esDificil = esDificil;
        this.precio = precio; 
        this.historialPrestamos = new ArrayList<>();
        this.cantidadTotal = cantidadTotal;
        this.cantidadVenta = 0;
        this.cantidadPrestamo = 0;
        this.cantidadPrestamoLibre = cantidadTotal;
    }
    
    public Juego(int id, String nombre, int anioPublicacion, String empresa, String categoria, int minJugadores, int maxJugadores, int edadMinima, boolean esDificil, int cantidadTotal, int cantidadVenta, int cantidadPrestamo, int cantidadPrestamoLibre, String estado, double precio){
        this.id = id;
        this.nombre = nombre;
        this.anioPublicacion = anioPublicacion;
        this.empresa = empresa;
        this.categoria = categoria;
        this.minJugadores = minJugadores;
        this.maxJugadores = maxJugadores;
        this.edadMinima = edadMinima;
        this.esDificil = esDificil;
        this.historialPrestamos = new ArrayList<>();
        this.cantidadTotal = cantidadTotal;
        this.cantidadVenta = cantidadVenta;
        this.cantidadPrestamo = cantidadPrestamo;
        this.cantidadPrestamoLibre = cantidadPrestamoLibre;
        this.estado = estado;
        this.precio = precio;
    }

    public int getId() { 
        return id; 
    }

    public String getNombre(){ 
        return nombre; 
    }

    public int getAnioPublicacion(){
        return anioPublicacion; 
    }

    public String getEmpresa(){ 
        return empresa; 
    }

    public String getCategoria(){ 
        return categoria;
    }

    public int getMinJugadores(){ 
        return minJugadores; 
    }

    public int getMaxJugadores(){ 
        return maxJugadores; 
    }
    
    public int getEdadMinima(){ 
        return edadMinima; 
    }

    public String getEstado(){ 
        return estado; 
    }

    public boolean isEsDificil(){ 
        return esDificil; 
    }

    public double getPrecio() { 
        return precio; 
    }
    
    public List<Prestamo> getHistorialPrestamos(){ 
        return historialPrestamos; 
    }

    public int getCantidadTotal(){ 
        return cantidadTotal; 
    }

    public int getCantidadVenta(){ 
        return cantidadVenta; 
    }

    public int getCantidadPrestamo(){ 
        return cantidadPrestamo; 
    }

    public int getCantidadPrestamoLibre(){ 
        return cantidadPrestamoLibre; 
    }

    public void setPrecio(double precio) { 
        this.precio = precio; 
    }

    public boolean esAptoJugadores(Mesa mesa){
        int personas = mesa.getNumPersonas();
        return personas >= minJugadores && personas <= maxJugadores;
    }

    public boolean esAptoEdad(Mesa mesa){
        if(mesa.getHayMenores()){
            return edadMinima <= 17;
        }
        return true;
    }

    public void setEstado(String newEstado){ 
        this.estado = newEstado; 
    }

    public void setCantidadTotal(int newInt){ 
        this.cantidadTotal = newInt; 
    }

    public void setCantidadVenta(int newInt){ 
        this.cantidadVenta = newInt; 
    }

    public void setCantidadPrestamo(int newInt){ 
        this.cantidadPrestamo = newInt; 
    }

    public void setCantidadPrestamoLibre(int newInt){ 
        this.cantidadPrestamoLibre = newInt; 
    }
}