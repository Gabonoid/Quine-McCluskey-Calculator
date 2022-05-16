package arquitectura;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author Gabriel
 */
public class Helpers {

    public static List<Byte> leerNumeros() {

        Scanner sc = new Scanner(System.in);
        List<Byte> numerosSeleccionados = new ArrayList<>();
        boolean comprobador = true;
        while (comprobador) {
            System.out.println("Ingrese un numero del 0 al 15:");
            System.out.println("Oprima cualquier letra para aceptar");
            try {
                byte num = sc.nextByte();
                if (num <= 15 || num >= 0) {
                    numerosSeleccionados.add(num);
                } else {
                    System.out.println("Número invalido");
                }
            } catch (Exception e) {
                comprobador = false;
            }
        }

        numerosSeleccionados = numerosSeleccionados.stream().distinct().collect(Collectors.toList());

        return numerosSeleccionados;
    }

    public static byte[] seleccionarFilas() {
        
        List<Byte> numerosSeleccionados = leerNumeros();
        
        byte[] matriz = new byte[numerosSeleccionados.size()];

        for (int i = 0; i < numerosSeleccionados.size(); i++) {
            matriz[i] = numerosSeleccionados.get(i);
        }
        return matriz;
    }
    
    
    public static byte[] listaPredeterminada() {
        byte[] matriz = {0,2,3,4,6,10,12,13,14,15};
        return matriz;
    }
    
    public static byte[] listaPredeterminada2() {
        byte[] matriz = {0,1,2,8,10,11,14,15};
        return matriz;
    }

}
