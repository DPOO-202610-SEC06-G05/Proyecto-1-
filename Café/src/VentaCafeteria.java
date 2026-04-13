import java.time.LocalDate;

public class VentaCafeteria extends Venta {
    private double impuestoConsumo; 
    private double propina; 

    public VentaCafeteria(int id, LocalDate fecha, double subtotal, Usuario comprador){
        super(id, fecha, subtotal, comprador);
        this.impuestoConsumo = 0.08;
        this.propina = subtotal * 0.10;
        this.total = subtotal + (subtotal * this.impuestoConsumo) + this.propina;
    }
    
    public VentaCafeteria(int id, LocalDate fecha, double total, double impuestoConsumo, double propina){
        super(id, fecha, total, null);
        this.impuestoConsumo = impuestoConsumo;
        this.propina = propina;
        this.total = total;
    }
    
    public double getImpuestoConsumo(){
        return impuestoConsumo; 
    }
    
    public double getPropina(){ 
        return propina;
    }
}