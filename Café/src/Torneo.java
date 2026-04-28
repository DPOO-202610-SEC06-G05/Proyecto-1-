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
    Turno turno;

    public Torneo(Juego juego, int cuposMaximos, boolean esAmistoso, int cuposFan, Turno turno){
        this.juego = juego;
        this.cuposMaximos = cuposMaximos;
        this.esAmistoso = esAmistoso;
        this.cuposFan = (int) Math.ceil(cuposMaximos * 0.20);
        this.inscritos = new ArrayList<>();
        this.cuposUsuario = new HashMap<>();
        this.turno = turno;
    }

    public boolean inscribir(Usuario usuario, int cantidad){
        int fanActuales = 0;
        int max = cuposUsuario.getOrDefault(usuario, 0);
        if(usuario == null){
            System.out.println("Lo sentimos, el Usuario no existe. ");
            return false;
        }
        if(inscritos.size() + cantidad > cuposMaximos){
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
                System.out.println("bro...estas en turno. ");
                return false;
            }
        }
        for(Usuario u: inscritos){
            if(u instanceof Cliente){
                fanActuales++;
            }
        }
        if (usuario instanceof Cliente){
            if(fanActuales + cantidad > cuposFan){
                System.out.println("Lo sentimos,no hay ams cupos para fans. ");
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
}
