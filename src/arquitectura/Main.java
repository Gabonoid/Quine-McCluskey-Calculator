package arquitectura;

/**
 *
 * @author Gabriel
 */
public class Main {

    public static void main(String[] args) {
        //Creamos un arreglo para guardar las filas que seleccione el usuario
        // byte [] matriz = Helpers.seleccionarFilas();
        
        
        byte [] matriz = Helpers.listaPredeterminada();
        
        //Creamos la clase matrizPrueba para almacenar los datos
        Matriz matrizPrueba = new Matriz(matriz);  
        
        //Damos inicio al algoritmo de Tabulado
        matrizPrueba.aplicarTabulado();
        
        System.out.println("\n=============Grupo A=============\n");
        Matriz.mostrarGrupos(matrizPrueba.getGrupoA());
        System.out.println("\n=============Grupo B=============\n");
        Matriz.mostrarGrupos(matrizPrueba.getGrupoB());
        System.out.println("\n=============Grupo C=============\n");
        Matriz.mostrarGrupos(matrizPrueba.getGrupoC());
        
        matrizPrueba.mostrarNoUsadas();
        
    }
    
}
