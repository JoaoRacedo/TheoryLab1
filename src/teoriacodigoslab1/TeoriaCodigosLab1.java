/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriacodigoslab1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author sonia
 */
public class TeoriaCodigosLab1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        codificator();
        Scanner sc = new Scanner(System.in);
        int opc;
        do{
            System.out.println("Digite una opcion: ");
            System.out.println("1. Verificador de Codigos ISBN");
            System.out.println("2. Codificador");
            System.out.println("3. Decodificador");
            System.out.println("4. Salir");
            opc = sc.nextInt();
            switch(opc){
                case 1: {
                    String code = "";
                    System.out.println("Digite el codigo ISBN (10 digitos): ");
                    code = sc.next();
                    while(code.length() != 10){
                        System.out.println("Error, Digite de nuevo");
                        code = sc.next();
                    }
                    if(verifISBN(code)){
                        System.out.println("El codigo ISBN es valido");
                    }else{
                        System.out.println("El codigo ISBN no es valido");
                        System.out.println("Posibles:");
                        for (String notValidISBN : notValidISBN(code)) {
                            System.out.println(notValidISBN);
                        }
                    }
                    System.out.println("");
                    break;
                }
                case 2: {
                    System.out.println("Digite el numero (5 digitos): ");
                    int n = sc.nextInt();
                    while(Integer.toString(n).length() != 5){
                        System.out.println("Error, Digite de nuevo");
                        n = sc.nextInt();
                    }
                    System.out.println("Codificacion del numero deseado: " + getCodif(n));
                    System.out.println("");
                    break;
                }
                case 3: {
                    String code = "";
                    System.out.println("Digite el codigo (10 digitos (Se permite usar la X)): ");
                    code = sc.next();
                    while(code.length() != 10){
                    System.out.println("Error, Digite de nuevo");
                    code = sc.next();
                    }
                    int[] codeWord = new int[v1.length];
                    for (int i = 0; i < code.length(); i++) {
                        if(code.substring(i, i+1).equalsIgnoreCase("X")){
                            codeWord[i] = 10;
                        }else{
                            codeWord[i] = Integer.parseInt(code.substring(i, i+1));
                        }
                    }
                    decodificator(codeWord);
                    System.out.println("");
                    break;
                }
                case 4: {
                    System.out.println("Salido");
                    break;
                }
                default: {
                    System.out.println("Opcion invalida");
                    System.out.println("");
                    break;
                }
            }
        }while(opc != 4);
    }
    
    /**
     * Verifica si un ISBN es valido
     * @param code Codigo ISBN a verificar.
     * @return Si el codigo ISBN es valido o no (True or False)
     */
    public static boolean verifISBN(String code){
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (i+1)*Integer.parseInt(code.substring(i, i+1));
        }
        if(code.substring(code.length()-1, code.length()).equalsIgnoreCase("X")){
            sum += 100;
        }else{
            sum += 10*Integer.parseInt(code.substring(code.length()-1, code.length()));
        }
        return sum % 11 == 0;
    }
    
    /**
     * Genera todos los ISBN validos para un ISBN no valido.
     * @param code Codigo ISBN no valido.
     * @return Un arreglo con todos los ISBN validos posibles.
     */
    public static ArrayList<String> notValidISBN(String code){
        ArrayList<String> posCodes = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                if(i != j){
                    if(!code.substring(i, i+1).equalsIgnoreCase("X")){
                        sum += (i+1)*Integer.parseInt(code.substring(i, i+1));
                    }else{
                        sum += 100;
                    }
                }
            }
            int k = 0;
            if(j < 9){
                while((((sum + (j+1)*k) % 11) != 0) && (k < 10)){
                    k++;
                }
                if(k < 10){
                    if(j > 0){
                        posCodes.add(code.substring(0, j) + k + code.substring(j+1, code.length()));
                    }else{
                        posCodes.add(k + code.substring(j+1, code.length()));
                    }
                }
            }else{
                while((((sum + 10*k) % 11) != 0) && (k < 11)){
                    k++;
                }
                if(k < 11){
                    if(k == 10){
                        posCodes.add(code.substring(0, code.length()-1) + "X");
                    }else{
                        posCodes.add(code.substring(0, code.length()-1) + k);
                    }
                }
            }
        }
        return posCodes;
    }
    
    //Elementos delcampo F11
    static int[] elem = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    
    //Elementos de la base k = 5 con longitud n = 10
    static int[] v1 = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0};
    static int[] v2 = {2, 3, 5, 7, 9, 0, 7, 8, 1, 3};
    static int[] v3 = {4, 2, 5, 0, 1, 7, 9, 6, 6, 1};
    static int[] v4 = {5, 7, 9, 5, 8, 6, 4, 1, 2, 3};
    static int[] v5 = {0, 5, 8, 9, 1, 4, 7, 3, 0, 1};
    
    //Todos los codewords del codigo generado
    static ArrayList<int[]> codeWords = new ArrayList<>();
    
    //Los posibles codigos del que puede venir el codeword enviado
    static ArrayList<int[]> posSln;
    
    //Los posibles numeros ya decodificados
    static ArrayList<Integer> posNum;
    
    /**
     * Codifica todos los numeros entre el 0 y 99999 con base en sus respectivas
     * representaciones en F_11
     */
    public static void codificator(){
        int i = 0;
        while(i < elem.length && codeWords.size() < 100000){
            int j = 0;
            while(j < elem.length && codeWords.size() < 100000){
                int k = 0;
                while(k < elem.length && codeWords.size() < 100000){
                    int l = 0;
                    while(l < elem.length && codeWords.size() < 100000){
                        int m = 0;
                        while(m < elem.length && codeWords.size() < 100000){
                            int n = 0;
                            int[] ans = new int[v1.length];
                            while(n < v1.length){
                                ans[n] = (i*v1[n] + j*v2[n] + k*v3[n] + l*v4[n] + m*v5[n]) % 11;
                                n++;
                            }
                            codeWords.add(ans);
                            m++;
                        }
                        l++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }
    }
    
    public static String getCodif(int n){
        int[] codeword = codeWords.get(n);
        String num = "";
        for (int i = 0; i < codeword.length; i++) {
            if(codeword[i] == 10){
                num += "X";
            }else{
                num += codeword[i];
            }    
        }
        return num;
    }
    
    /**
     * Decodifica el codeword enviado, dando como respuesta todos los posibles
     * codewords del cual pudo haber procedido y sus respectivas decodificaciones,
     * en caso de no poder corregir el error. 
     * En caso de corregirlo, muestra el unico codeword posible y su unica 
     * decodificacion.
     * @param codeWord CodeWord enviado.
     */
    public static void decodificator(int[] codeWord){
        posSln = new ArrayList<>();
        posNum = new ArrayList<>();
        int minDist = 11;
        for (int j = 0; j < codeWords.size(); j++) {
            int[] codeWord1 = codeWords.get(j);
            int dist = 0;
            for (int i = 0; i < v1.length; i++) {
                if(codeWord1[i] != codeWord[i]){
                    dist++;
                }
            }
            if(dist == minDist){
                posSln.add(codeWord1);
                posNum.add(j);
                minDist = dist;
            }else if(dist < minDist){
                posSln.clear();
                posNum.clear();
                posSln.add(codeWord1);
                posNum.add(j);
                minDist = dist;
            }
        }
        if(minDist > 2){
            System.out.println("");
            System.out.println("Detectados " + minDist + " errores");
            System.out.println("Posibles codewords:");
            for (int[] posSln1 : posSln) {
                String num = "";
                for (int i = 0; i < posSln1.length; i++) {
                    if(posSln1[i] == 10){
                        num += "X";
                    }else{
                        num += posSln1[i];
                    }
                }
                System.out.println(num);
            }
            System.out.println("");
            System.out.println("Posibles numeros decodificados");
            for (Integer posNum1 : posNum) {
                System.out.println(posNum1);
            }
        }else{
            System.out.println("");
            System.out.println("Corregido " + minDist + " error(es)");
            System.out.println("CodeWord que se quiso digitar:");
            for (int[] posSln1 : posSln) {
                String num = "";
                for (int i = 0; i < posSln1.length; i++) {
                    if(posSln1[i] == 10){
                        num += "X";
                    }else{
                        num += posSln1[i];
                    }
                }
                System.out.println(num);
            }
            System.out.println("");
            System.out.println("Numero decodificado");
            for(Integer posNum1 : posNum) {
                System.out.println(posNum1);
            }
        }
    }
}
