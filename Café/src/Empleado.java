import java.time.LocalDate;

public abstract class Empleado extends Usuario {
    protected Turno turno;

    public Empleado(int id, String username, String email, String password, Turno turno){
        super(id, username, email, password);
        this.turno = turno;
    }

    public Turno getTurno(){
        return turno;
    }

    public void setTurno(Turno newTurno){
        this.turno = newTurno;
    }

    public boolean enTurno(Turno turno){
        return this.turno != null && this.turno.equals(turno);
    }

    public void solicitarCambioTurno(Turno newTurno, Turno actualTurno, int idEmpleado){
        System.out.println("El empleado " + this.getUsername() + " (ID: " + idEmpleado + ") ha solicitado cambiar del turno " + 
                           actualTurno.getDia() + " al turno " + newTurno.getDia() + ".");
        System.out.println("Esta solicitud queda pendiente de aprobación por el Administrador.");
    }

    public VentaJuego comprarJuego(Juego juego, String codigoDescuento, int idVenta){
        if (juego.getCantidadVenta() > 0) {
            double porcentajeDescuento = codigoDescuento.equals("EMP20") ? 0.20 : 0.10; 
            double valorDescuento = juego.getPrecio() * porcentajeDescuento;

            VentaJuego venta = new VentaJuego(idVenta, LocalDate.now(), juego.getPrecio(), valorDescuento, this);
            
            juego.setCantidadVenta(juego.getCantidadVenta() - 1);
            
            System.out.println("Compra de juego exitosa. Descuento aplicado: " + (porcentajeDescuento * 100) + "%");
            return venta;
        } 
        
        else {
            System.out.println("No hay copias disponibles para venta del juego: " + juego.getNombre());
            return null;
        }
    }

    public VentaCafeteria comprarProducto(ProductoMenu producto, String codigoDescuento, int idVenta){
        double porcentajeDescuento = codigoDescuento.equals("EMP20") ? 0.20 : 0.10;
        double precioConDescuento = producto.getPrecio() * (1 - porcentajeDescuento);
        VentaCafeteria venta = new VentaCafeteria(idVenta, LocalDate.now(), precioConDescuento, this);
        System.out.println("Pedido de cafetería exitoso con descuento del " + (porcentajeDescuento * 100) + "%.");
        return venta;
    }

    public void sugerirPlatillo(String newPlatillo){
        System.out.println("El empleado " + this.getUsername() + " ha sugerido incluir el platillo: '" + newPlatillo + "' en el menú.");
        System.out.println("Sugerencia enviada al Administrador.");
    }
}