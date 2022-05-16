package arquitectura;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class Matriz {

    private byte[][] matrizPrincipal;
    private byte[] filasSelect;
    private String formulaFinal;
    private List<List<FilasSeleccionadas>> grupoA = new ArrayList<>();
    private List<List<FilasSeleccionadas>> grupoB = new ArrayList<>();
    private List<List<FilasSeleccionadas>> grupoC = new ArrayList<>();
    private List<FilasSeleccionadas> filasNoUsadas = new ArrayList<>();

    private static final byte[][] tablaVerdad = new byte[][]{
        {0, 0, 0, 0}, //0
        {0, 0, 0, 1}, //1 
        {0, 0, 1, 0}, //2
        {0, 0, 1, 1}, //3
        {0, 1, 0, 0}, //4
        {0, 1, 0, 1}, //5
        {0, 1, 1, 0}, //6
        {0, 1, 1, 1}, //7
        {1, 0, 0, 0}, //8
        {1, 0, 0, 1}, //9
        {1, 0, 1, 0}, //10
        {1, 0, 1, 1}, //11
        {1, 1, 0, 0}, //12
        {1, 1, 0, 1}, //13
        {1, 1, 1, 0}, //14
        {1, 1, 1, 1}, //15
    };

    public Matriz(byte[] filasSelect) {
        this.filasSelect = filasSelect;
    }

    /**
     * Metodo que inicia y da orden al proceso de Tabulado
     */
    public void aplicarTabulado() {
        llenaMatrizPrincipal();
        crearFilasSelec();
        diferenciador();
        listarNoUsadas();
        contarNumerosRepetidos();
    }

    /**
     * LLenado de nuestra matriz principal
     */
    public void llenaMatrizPrincipal() {
        matrizPrincipal = new byte[filasSelect.length][4];
        for (int i = 0; i < filasSelect.length; i++) {
            matrizPrincipal[i] = tablaVerdad[filasSelect[i]];
        }
    }

    /**
     * Crea una lista con las filas seleccionadas
     *
     */
    public void crearFilasSelec() {

        List<FilasSeleccionadas> seleccionada = new ArrayList<>();
        for (int i = 0; i < matrizPrincipal.length; i++) {
            seleccionada.add(
                    new FilasSeleccionadas(
                            filasSelect[i] + "",
                            matrizPrincipal[i],
                            contadorUno(matrizPrincipal[i]),
                            true));
        }

        Collections.sort(seleccionada);

        seleccionada.forEach(filasSeleccionadas -> {
            System.out.println(filasSeleccionadas);
        });

        System.out.println("Tamaño seleccionadas: " + seleccionada.size());

        for (int i = 0; i < 5; i++) {
            List<FilasSeleccionadas> fila = new ArrayList<>();
            for (int j = 0; j < seleccionada.size(); j++) {
                if (seleccionada.get(j).getNumCeros() == i) {
                    fila.add(seleccionada.get(j));
                }

            }
            if (fila != null) {
                grupoA.add(fila);
            }
        }
    }

    /**
     * Cuenta cuantos "1" se encuentra en nuestra fila
     *
     * @param fila
     * @return Conteo de "1" dentro de la fila
     */
    public byte contadorUno(byte[] fila) {
        byte numUnos = 0;
        for (int i = 0; i < 4; i++) {
            if (fila[i] == 1) {
                numUnos++;
            }
        }
        return numUnos;
    }

    /**
     * Genera los subGrupos resultado de la multiplicacion de matrices
     *
     * @param grupo - Grupo al cual queremos separar
     * @param i - Posicion a la cual queremos acceder
     * @param selector - Selector para definir que grupo pasara
     * @return
     */
    public List<FilasSeleccionadas> generarSubGrupo(List<List<FilasSeleccionadas>> grupo, int i, int selector) {

        List<FilasSeleccionadas> nuevoSubGrupo = new ArrayList<>();
        for (int j = 0; j < grupo.get(i - 1).size(); j++) {
            List<FilasSeleccionadas> subGrupo = grupo.get(i - 1);
            byte[] fila = subGrupo.get(j).getFila();

            for (int k = 0; k < grupo.get(i).size(); k++) {
                byte[] nuevaFila = new byte[4];
                List<FilasSeleccionadas> subGrupo2 = grupo.get(i);
                byte[] fila2 = subGrupo2.get(k).getFila();
                byte numeroCabios = 0;

                for (int l = 0; l < fila2.length; l++) {
                    if (fila[l] != fila2[l]) {
                        numeroCabios++;
                    }
                }

                if (numeroCabios == 1) {
                    for (int l = 0; l < fila2.length; l++) {
                        if (fila[l] != fila2[l]) {
                            nuevaFila[l] = -1;
                        } else {
                            nuevaFila[l] = fila2[l];
                        }
                    }
                    nuevoSubGrupo.add(
                            new FilasSeleccionadas(
                                    subGrupo.get(j).getNombre() + ", " + subGrupo2.get(k).getNombre(),
                                    nuevaFila, false));
                    if (selector == 1) {
                        grupoB.get(i - 1).get(j).setSeUso(true);
                        grupoB.get(i).get(k).setSeUso(true);
                    }

                }
            }
        }
        return nuevoSubGrupo;
    }

    /**
     * Hace la multiplicacion de las matrices de los grupos
     */
    public void diferenciador() {

        // Diferenciar Grupo A
        for (int i = 1; i < this.grupoA.size(); i++) {
            List<FilasSeleccionadas> nuevoSubGrupo = generarSubGrupo(grupoA, i, 0);
            grupoB.add(nuevoSubGrupo);
        }

        // Diferenciar Grupo B
        for (int i = 1; i < grupoB.size(); i++) {
            List<FilasSeleccionadas> nuevoSubGrupo = generarSubGrupo(grupoB, i, 1);
            grupoC.add(nuevoSubGrupo);
        }

    }

    /**
     * Muestra a consola un grupo
     *
     * @param grupo
     */
    public static void mostrarGrupos(List<List<FilasSeleccionadas>> grupo) {
        System.out.println("-------------------");
        for (int i = 0; i < grupo.size(); i++) {
            grupo.get(i).forEach(System.out::println);
            System.out.println("-------------------");
        }

        System.out.println("Numero de grupos: " + grupo.size());
    }

    /**
     * Recorre nuestros grupos para encontrar aquellas filas que no se
     * utilizaron
     */
    public void listarNoUsadas() {

        for (int i = 0; i < grupoB.size(); i++) {
            for (int j = 0; j < grupoB.get(i).size(); j++) {
                if (grupoB.get(i).get(j).isSeUso() == false) {
                    filasNoUsadas.add(grupoB.get(i).get(j));
                }
            }
        }

        for (int i = 0; i < grupoC.size(); i++) {
            for (int j = 0; j < grupoC.get(i).size(); j++) {
                if (grupoC.get(i).get(j).isSeUso() == false) {
                    filasNoUsadas.add(grupoC.get(i).get(j));
                }
            }
        }

        for (int i = 0; i < filasNoUsadas.size(); i++) {
            byte[] uno = filasNoUsadas.get(i).getFila();

            for (int j = 1 + i; j < filasNoUsadas.size();) {
                byte[] dos = filasNoUsadas.get(i).getFila();
                if (uno.equals(dos)) {
                    filasNoUsadas.remove(j);
                    break;
                }
            }
        }
    }

    /**
     * Muestra aquellas filas de cualquier grupo las cuales nunca se pudieron
     * utilizar
     */
    public void mostrarNoUsadas() {
        System.out.println("\n============= Filas No usadas =============\n");
        for (int i = 0; i < filasNoUsadas.size(); i++) {
            System.out.println(filasNoUsadas.get(i).getNombre() + " -> " + Arrays.toString(filasNoUsadas.get(i).getFila()));
        }
    }

    public List<List<FilasSeleccionadas>> getGrupoA() {
        return grupoA;
    }

    public List<List<FilasSeleccionadas>> getGrupoB() {
        return grupoB;
    }

    public List<List<FilasSeleccionadas>> getGrupoC() {
        return grupoC;
    }

    public String getFormulaFinal() {
        return formulaFinal;
    }

    public void contarNumerosRepetidos() {
        List<FilaFinal> conteo = new ArrayList<>();

        for (int i = 0; i < filasSelect.length; i++) {
            byte contador = 0;
            for (int j = 0; j < filasNoUsadas.size(); j++) {
                byte[] h = filasNoUsadas.get(j).getNombreByte();

                for (int k = 0; k < h.length; k++) {
                    if (h[k] == filasSelect[i]) {
                        contador++;
                    }
                }
            }
            conteo.add(new FilaFinal(filasSelect[i], contador));
        }

        List<FilasSeleccionadas> formula = new ArrayList<>();

        for (int i = 0; i < conteo.size(); i++) {
            if (conteo.get(i).getRepeticion() == 1) {

                for (int j = 0; j < filasNoUsadas.size(); j++) {
                    byte[] h = filasNoUsadas.get(j).getNombreByte();
                    for (int k = 0; k < h.length; k++) {
                        if (h[k] == conteo.get(i).getValor()) {
                            formula.add(filasNoUsadas.get(j));
                        }
                    }
                }
            }
        }

        //eliminamos repetidos
        HashMap<String, FilasSeleccionadas> limpiar = new HashMap<String, FilasSeleccionadas>();
        for (FilasSeleccionadas filasSeleccionadas : formula) {
            limpiar.put(filasSeleccionadas.getNombre(), filasSeleccionadas);
        }

        /*
        System.out.println("\n============= Filas Final =============\n");
        limpiar.entrySet().forEach(valor -> {
            System.out.println(valor.getValue());
        });

        System.out.println("\n============= Lista Conteo =============\n");
        conteo.forEach(numero -> {
            System.out.println(numero.getValor());
        });
        */
        generarVariable(limpiar);

    }

    public void generarVariable(HashMap<String, FilasSeleccionadas> limpiar) {
        String FormulaFinal = "";
        List<FilasSeleccionadas> values = new ArrayList<FilasSeleccionadas>(limpiar.values());

        for (int i = 0; i < values.size(); i++) {
            byte[] fila = values.get(i).getFila();
            String a = asignarValor("A", fila[0]);
            String b = asignarValor("B", fila[1]);
            String c = asignarValor("C", fila[2]);
            String d = asignarValor("D", fila[3]);
            if (i == values.size()-1) {
                FormulaFinal += (FormulaFinal != null)? (a + b + c + d):("");
            }else{
                FormulaFinal += (FormulaFinal != null)? (a + b + c + d + " + "):("");
            }
            
        }
        formulaFinal = FormulaFinal;

    }

    public String asignarValor(String letra, byte valor) {
        String letraCom = "";

        if (valor == -1) {
            letraCom = "";
        } else if (valor == 0) {
            letraCom = letra + "'";
        } else if (valor == 1) {
            letraCom = letra;
        }

        return letraCom;
    }

}
