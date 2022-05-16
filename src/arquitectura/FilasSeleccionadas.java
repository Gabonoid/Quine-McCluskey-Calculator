package arquitectura;

import java.util.Arrays;

/**
 *
 * @author Gabriel
 */
public class FilasSeleccionadas implements Comparable<FilasSeleccionadas>{
    private String nombre;
    private byte [] nombreByte;
    private byte [] fila;
    private byte numUno;
    private boolean seUso = false;

    public FilasSeleccionadas(String nombre, byte[] fila, byte numCeros) {
        this.nombre = nombre;
        this.fila = fila;
        this.numUno = numCeros;
        nombreToByte();
    }
    
    public FilasSeleccionadas(String nombre, byte[] fila, byte numCeros, boolean seUso) {
        this.nombre = nombre;
        this.fila = fila;
        this.numUno = numCeros;
        this.seUso = seUso;
        nombreToByte();
    }
    
    public FilasSeleccionadas(String nombre, byte[] fila) {
        this.nombre = nombre;
        this.fila = fila;
        nombreToByte();
    }
    
    public FilasSeleccionadas(String nombre, byte[] fila, boolean seUso) {
        this.nombre = nombre;
        this.fila = fila;
        this.seUso = seUso;
        nombreToByte();
    }
    
    private void nombreToByte(){
            String [] fila = this.nombre.replace(" ","").split(",");
            byte [] filaNum = new byte [fila.length];
            for (int i = 0; i < filaNum.length; i++) {
                filaNum[i] = Byte.parseByte(fila[i]);
            }
            this.nombreByte = filaNum;
    }

    public boolean isSeUso() {
        return seUso;
    }

    public void setSeUso(boolean seUso) {
        this.seUso = seUso;
    }

    public byte getNumCeros() {
        return numUno;
    }

    public void setNumCeros(byte numCeros) {
        this.numUno = numCeros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getFila() {
        return fila;
    }

    public void setFila(byte[] fila) {
        this.fila = fila;
    }

    public byte[] getNombreByte() {
        return nombreByte;
    }
    
    @Override
    public int compareTo(FilasSeleccionadas o) {
        return this.numUno - o.getNumCeros();
    }

    @Override
    public String toString() {
        String usado = (seUso == false)?" ✓":"";
        return nombre + " -> " + Arrays.toString(fila) + usado;
    }
}
