import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Torneo {
    private Juego juego;
    private int cuposMaximos;
    private boolean esAmistoso;
    private List<Usuario> inscritos;
    private int cuposFan;
    private Map<Usuario, Integer> cuposUsuario;
    private Turno turno;
    private double tarifaEntrada;
    private double premioDinero;
    private double bonoDescuento;

    public Torneo(Juego juego, int cuposMaximos, boolean esAmistoso, Turno turno){
        this.juego = juego;
        this.cuposMaximos = cuposMaximos;
        this.esAmistoso = esAmistoso;
        this.cuposFan = (int) Math.ceil(cuposMaximos * 0.20);
        this.inscritos = new ArrayList<>();
        this.cuposUsuario = new HashMap<>();
        this.turno = turno;
        this.tarifaEntrada = 20000;
        this.bonoDescuento = 0.10;
        this.premioDinero = 0;
    }
    public Juego getJuego(){
        return juego;
    }

    public Turno getTurno(){
        return turno;
    }

    public int getCuposMaximos(){
        return cuposMaximos;
    }

    public List<Usuario> getInscritos(){
        return inscritos;
    }
    public boolean isEsAmistoso(){
        return esAmistoso;
    }
    public int getCuposFan(){
        return cuposFan;
    }
    


    private int totalCuposTomados(){
        int total = 0;
        for(int cupos : cuposUsuario.values()){
            total += cupos;
        }
        return total;
    }
    private boolean esFan(Usuario usuario){
        if(usuario instanceof Cliente){
            Cliente c = (Cliente) usuario;
            return c.getJuegosFavoritos().contains(juego.getNombre());
        }
        return false;
    }
    private int totalCuposNoFanTomados(){
        int total = 0;
        for(Usuario u: cuposUsuario.keySet()){
            if(!esFan(u)){
                total += cuposUsuario.get(u);
            }
        }
        return total;
    }
    private double calcularRecaudo(){
        double total = 0;
        for(Usuario u : cuposUsuario.keySet()){
            if(!(u instanceof Empleado)){
                total += cuposUsuario.get(u) * tarifaEntrada;
            }
        }
        return total;
    }

    
    public boolean inscribir(Usuario usuario, int cantidad){
        int max = cuposUsuario.getOrDefault(usuario, 0);
        if(usuario == null){
            System.out.println("Lo sentimos, el Usuario no existe. ");
            return false;
        }
        if(totalCuposTomados() + cantidad > cuposMaximos){
            System.out.println("Lo sentimos, ya no hay cupo diusponble. ");
            return false;
        }
        if(max +cantidad >3){
            System.out.println("Lo sentimos, solo admitimos que estes inscrito a maximo 3 torneos. ");
            return false;
        }
        if(usuario instanceof Empleado){
            Empleado e = (Empleado) usuario;
            if(e.enTurno(turno)){
                System.out.println("El empleado no puede inscribirse porque esta en turno.");
                return false;
            }
        }
        if(!esFan(usuario)){
            int cuposRegulares = cuposMaximos - cuposFan;
            if(totalCuposNoFanTomados()+ cantidad > cuposRegulares){
                System.out.println("Lo sentimos, no hay cupos disponibles para los fans");
                return false;

            }
        }
        inscritos.add(usuario);
        cuposUsuario.put(usuario, cuposUsuario.getOrDefault(usuario, 0) + cantidad);
        return true;
    }




    public boolean desinscribir(Usuario usuario){
        if(usuario == null){
            System.out.println("Lo sentimos, el Usuario no existe. ");
            return false;
        }

        int cupos = cuposUsuario.getOrDefault(usuario, 0);

        if (cupos == 0){
            System.out.println("Lo sentimos usted no tiene inscripciones. ");
            return false;
        }

        inscritos.remove(usuario);
        cuposUsuario.remove(usuario);
        System.out.println("El usuario se ha desincrito satisfactoriamente. ");
        return true;
    }




     public double calcularPremio(){
        if(esAmistoso){
            return bonoDescuento;
        }
        premioDinero = calcularRecaudo() * 0.70;
        return premioDinero;
    }

    public void finalizarTorneo(Usuario ganador){
        if(ganador == null){
            System.out.println("no hat ganador.");
            return;
        }
        if(esAmistoso){
            double bono = calcularPremio();
            System.out.println("Torneo amistoso finalizado. Ganador recibe bono de descuento de "+(bono * 100)+"%.");
            return;
        }
        if(ganador instanceof Empleado){
            System.out.println("Torneo competitivo finalizado. El ganador es empleado, no recibe premio en dinero.");
            return;
        }
    
        double premio = calcularPremio();
        System.out.println("Torneo competitivo finalizado. Premio entregado: "+premio);
    }
}
