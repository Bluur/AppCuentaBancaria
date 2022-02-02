/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Migue
 */
public class Pruebas {

    public static void main(String[] args) {
        String titular = "Miguel Garcia";
        String nif = "77446461X";
        String password = "170899170899aG";
        String entidad = "1324";
        String oficina = "3452";
        String DC = "63";
        String numCuenta = "8909312432";
        char siete = '7';
        
        
        System.out.println(validarDC(entidad, oficina, numCuenta));
        //CuentaBancaria caixa = new CuentaBancaria(titular, nif, password, entidad, oficina, DC, numCuenta);
        //System.out.println(caixa);
    }
    
    public static String validarDC(String entidad, String oficina, String numCuenta) {
        int pesos[] = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
        int sumad1 = 0;
        int sumad2 = 0;
        String cad1 = "00" + entidad + oficina;
        for (int i = 0; i < pesos.length; i++) {
            sumad1 +=  Integer.parseInt(Character.toString(cad1.charAt(i))) * pesos[i];
            System.out.println(sumad1);
            sumad2 +=  Integer.parseInt(Character.toString(numCuenta.charAt(i))) * pesos[i];
        }
        sumad1 = sumad1 % 11;
        sumad2 = sumad2 % 11;
        String DC = "" + sumad1 + sumad2;
        return DC;
    }
}
