package arquitectura;

/**
 *
 * @author Gabriel
 */
public class FilaFinal {
    
    private byte valor;
    private byte repeticion;

    public FilaFinal() {
    }

    public FilaFinal(byte valor, byte repeticion) {
        this.valor = valor;
        this.repeticion = repeticion;
    }

    public byte getValor() {
        return valor;
    }

    public void setValor(byte valor) {
        this.valor = valor;
    }

    public byte getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(byte repeticion) {
        this.repeticion = repeticion;
    }

    @Override
    public String toString() {
        return "FilaFinal{" + "valor=" + valor + ", repeticion=" + repeticion + "}\n";
    }
    
    
    
    
}
