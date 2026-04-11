import java.util.List;
import java.util.ArrayList;

public class Mesa {
    private int numMesa;
    private int capacidad;
    private int numPersonas;
    private int numNinios;
    private int numJovenes;
    private boolean hayMenores;
    private boolean hayJovenes;
    private boolean ocupada;
    private List<String> juegosEnMesa;
    private List<String> productosEnMesa;

    public Mesa(int capacidad){
        this.capacidad = capacidad;
        this.numMesa = 0;
        this.numPersonas = 0;
        this.numNinios = 0;
        this.numJovenes = 0;
        this.hayMenores = false;
        this.hayJovenes = false;
        this.ocupada = false;
        this.juegosEnMesa = new ArrayList<>();
        this.productosEnMesa = new ArrayList<>();
    }

    public int getNumMesa(){
        return numMesa;
    }

    public int getCapacidad(){
        return capacidad;
    }

    public int getNumPersonas(){
        return numPersonas;
    }

    public int getNumNinios(){
        return numNinios;
    }

    public int getNumJovenes(){
        return numJovenes;
    }

    public boolean getHayMenores(){
        return hayMenores;
    }

    public boolean getHayJovenes(){
        return hayJovenes;
    }

    public boolean getOcupada(){
        return ocupada;
    }

    public List<String> getJuegosEnMesa(){
        return juegosEnMesa;
    }

    public List<String> getProductosEnMesa(){
        return productosEnMesa;
    }

    public void setNumPersonas(int numPersonas){
        this.numPersonas = numPersonas;
    }

    public void setHayMenores(boolean hayMenores){
        this.hayMenores = hayMenores;
    }

    public void setNumNinios(int numNinios){
        this.numNinios = numNinios;
    }

    public void setNumJovenes(int numJovenes){
        this.numJovenes = numJovenes;
    }

    public void setOcupada(boolean ocupada){
        this.ocupada = ocupada;
    }

    public void actualizarJuegos(String juego){
        this.juegosEnMesa.add(juego);
    }

    public boolean juegoAptoParaMesa(Juego juego){
        if(this.numPersonas > this.capacidad){
            return false;
        }else{
            return true;
        }
    }
}
