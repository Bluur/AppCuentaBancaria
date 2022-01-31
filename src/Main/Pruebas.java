/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

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
        
        CuentaBancaria caixa = new CuentaBancaria(titular, nif, password, entidad, oficina, DC, numCuenta);
        System.out.println(caixa);
    }
    
}
