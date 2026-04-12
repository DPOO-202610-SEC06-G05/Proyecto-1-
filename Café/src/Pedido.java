import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Pedido {
    private int id;
    private LocalDate fecha;
    private Mesa mesa;
    private List<ProductoMenu> productos;
    private boolean terminado;

    public Pedido(int id, LocalDate fecha, Mesa mesa){
        this.id = id;
        this.fecha = fecha;
        this.mesa = mesa;
        this.productos = new ArrayList<>();
        this.terminado = false;
    }

    public List<ProductoMenu> getProductos(){
        return productos;
    }

    public void agregarProducto(ProductoMenu producto){
        productos.add(producto); ///falta restricciones
    }

    public void eliminarProducto(ProductoMenu producto){
        productos.remove(producto);
    }

    public double calcularTotal(){
        double total = 0;
        for (ProductoMenu producto : productos){
            total += producto.getPrecio(); ///faltan impuestos
        }
        return total;
    }

    public void terminarPedido(){
        terminado = true;
    }

    public boolean estaTerminado(){
        return terminado;
    }
}