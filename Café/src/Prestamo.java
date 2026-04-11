import java.time.LocalDate;

public class Prestamo {
    private int id;
    private LocalDate fecha;
    private String estado;
    private LocalDate fechaDevolucion;
    private Juego juego;
    private Usuario prestatario;

    public Prestamo(int id, LocalDate fecha, String estado, LocalDate fechaDevolucion, Usuario prestatario, Juego juego){
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.fechaDevolucion = fechaDevolucion;
        this.prestatario = prestatario;
        this.juego = juego;
    }

    public int getId(){
        return id; }

    public LocalDate getFecha(){ 
        return fecha; }

    public String getEstado(){ 
        return estado; }

    public LocalDate getFechaDevolucion(){ 
        return fechaDevolucion; }

    public Usuario getPrestatario() { 
        return prestatario; }
        
    public Juego getJuego() { 
        return juego; }

    public void registrarPrestamo(){
        this.estado = "activo";
        this.fecha = LocalDate.now();
    }

    public void finalizarPrestamo(){
        this.estado = "finalizado";
        this.fechaDevolucion = LocalDate.now();
    }
}