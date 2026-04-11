import java.time.LocalDate;
import java.util.List;

public abstract class Venta {
    protected int id;
    protected LocalDate fecha;
    protected double subtotal; 
    protected Usuario comprador; 
    protected double total; 

    public Venta(int id, LocalDate fecha, double subtotal, Usuario comprador){
        this.id = id;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.comprador = comprador;
    }

    public int getId(){
         return id; 
    }

    public LocalDate getFecha(){ 
        return fecha; 
    }

    public double getSubtotal(){ 
        return subtotal;
    }

    public double getTotal(){ 
        return total; 
    }

    public Usuario getComprador(){
        return comprador; 
    }

    public void registrarVenta(List<Venta> historialCompras){
        historialCompras.add(this);
    }
}