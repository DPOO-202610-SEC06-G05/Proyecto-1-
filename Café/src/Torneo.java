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

    public Torneo(Juego juego, int cuposMaximos, boolean esAmistoso, int cuposFan){
        this.juego = juego;
        this.cuposMaximos = cuposMaximos;
        this.esAmistoso = esAmistoso;
        this.cuposFan = (int) Math.ceil(cuposMaximos * 0.20);
        this.inscritos = new ArrayList<>();
        this.cuposUsuario = new HashMap<>();
    }

    public inscribir(){

    }

    public desinscribir(){
        
    }
}
