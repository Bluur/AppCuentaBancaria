
package Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pruebas {

    public static void main(String[] args) {
        String titular = "Miguel Garcia";
        String nif = "A58818501";
        String password = "170899170899aG";
        String entidad = "1324";
        String oficina = "3452";
        String DC = "63";
        String numCuenta = "8909312432";
        char siete = '7';
        
        
        System.out.println(validarId(nif));
        //CuentaBancaria caixa = new CuentaBancaria(titular, nif, password, entidad, oficina, DC, numCuenta);
        //System.out.println(caixa);
    }

    public static boolean validarId(String nif) {
        boolean validez = false;
        char inicio = Character.isAlphabetic(nif.charAt(0)) ? nif.charAt(0): ' ';
        //comprobar si es nif o nie
        if(Character.isDigit(nif.charAt(0))){
            validez = validarNif(nif);
        }else if(Character.isAlphabetic(nif.charAt(0)) && Character.isAlphabetic(nif.charAt(8))){
            String nie;
            if(nif.startsWith("X")){
                nie = "0" + nif.substring(1);
            }else if(nif.startsWith("y")){
                nie = "1" + nif.substring(1);
            }else{
                nie = "2" + nif.substring(1);
            }
            validez = validarNif(nie);
        }else if(inicio != 'Y' && inicio != 'X' && inicio != 'Z' && inicio != ' '){
            validez = validarCif(nif);
        }
        return validez;
    }
    
    public static boolean validarNif(String nif){
        boolean validez = false;
        Pattern dni = Pattern.compile("([0-9]{1,9})([A-Za-z])");
        Matcher d = dni.matcher(nif);

        String cadenaValidadora = "TRWAGMYFPDXBNJZSQVHLCKE";
        boolean valido = false;

        int numero = Integer.parseInt(nif.substring(0, 8)) % 23;
        
        if(nif.endsWith(Character.toString(cadenaValidadora.charAt(numero)))){
            valido = true;
        }

        return d.matches() && valido;
    }
    
    public static boolean validarCif(String cif){
        //Variables
        String caracteres = "ABCDEFGHIJ";
        int sumaPares = 0;
        int sumaImpares = 0;
        boolean validez = false;
        char charFinal = cif.charAt(8);
        
        //Cálculos necesarios para la validación
        for(int i=1; i < 8; i++){
            if(i%2 == 0){
                sumaPares += (Integer.parseInt(Character.toString(cif.charAt(i))));
            }else{
                int digito1 = (Integer.parseInt(Character.toString(cif.charAt(i))) * 2)  / 10;
                int digito2 = (Integer.parseInt(Character.toString(cif.charAt(i))) * 2) % 10;
                sumaImpares += digito1 + digito2;
            }
        }
        int sumaGlobal = sumaPares + sumaImpares;
        
        //Comprobación de la validación
        if(sumaGlobal/10 > 0){
            String suma = "" + sumaGlobal;
            int digito = Integer.parseInt(Character.toString(suma.charAt(suma.length()-1)));
            digito = 10 - digito;
            
            if(Character.isAlphabetic(charFinal)){
                validez = Character.toString(caracteres.charAt(digito)).equals(charFinal) ? true : false;
                System.out.println("hola");
            }else{
                String digitoFinal = ""+digito;
                validez = Character.toString(charFinal).equals(digito);
            }
            
        }else{
            int numeroFinal = 10 - sumaGlobal;
            String num = "" + numeroFinal;
            if(Character.isAlphabetic(charFinal)){
                validez = false;
            }else{
                validez = false;
            }
        }
        return validez;
    }
}
