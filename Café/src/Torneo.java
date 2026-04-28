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



    public Torneo(Juego juego, int cuposMaximos, boolean esAmistoso, Turno turno){
        this.juego = juego;
        this.cuposMaximos = cuposMaximos;
        this.esAmistoso = esAmistoso;
        this.cuposFan = (int) Math.ceil(cuposMaximos * 0.20);
        this.inscritos = new ArrayList<>();
        this.cuposUsuario = new HashMap<>();
        this.turno = turno;
    }

    public Juego getJuego(){
        return juego;
    }

    public int getCuposMaximos(){
        return cuposMaximos;
    }

    public List<Usuario> getIncritos(){
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

    public void finalizarTorneo(){
        if(esAmistoso){
            System.out.println("Torneo amistoso finalizado. Se otorgan descuentos.");
        }else{
            System.out.println("Torneo competitivo finalizado. Se otorgan premios.");
        }
    }
}
