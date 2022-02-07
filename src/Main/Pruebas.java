
package Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pruebas {

    public static void main(String[] args) {
        String titular;
        String nif;
        String password;
        String entidad;
        String oficina;
        String DC;
        String numCuenta;        
        
        do{
            titular = leerDatosTeclado.leerString("Deme el nombre del titular de la cuenta");
        }while(!validarTitular(titular));

        do{
            nif = leerDatosTeclado.leerString("Deme el NIF/NIE/CIF del titular");
        }while(!validarId(nif));

        do{
            password = leerDatosTeclado.leerString("Deme la contraseña que quiere para su cuenta");
        }while(!validarContraseña(password));

        do{
            do{
                entidad = leerDatosTeclado.leerString("Deme la entidad bancaria");
            }while(entidad.length() != 4);

            do{
                oficina = leerDatosTeclado.leerString("Deme la oficina bancaria");
            }while(oficina.length() != 4);

            do{
                DC = leerDatosTeclado.leerString("Deme los digitos de control de su cuenta");
            }while(DC.length() != 2);

            do{
                numCuenta = leerDatosTeclado.leerString("Deme su número de cuenta");
            }while(numCuenta.length() != 10);

        }while(!comprobarCCC(entidad+oficina+DC+numCuenta));

        CuentaBancaria caixa = new CuentaBancaria(titular, nif, password, entidad+oficina+DC+numCuenta);
        System.out.println(caixa);
    }

    //Métodos Públicos
    /**
     * Se le pasan la entidad, oficina y numCuenta, para que calcule el numero
     * de control.
     *
     * @param entidad
     * @param oficina
     * @param numCuenta
     * @return DC
     */
    public static String obtenerDigitosControl(String entidad, String oficina, String numCuenta) {
        int pesos[] = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
        int sumad1 = 0;
        int sumad2 = 0;
        String cad1 = "00" + entidad + oficina;
        for (int i = 0; i < pesos.length; i++) {
            sumad1 +=  Integer.parseInt(Character.toString(cad1.charAt(i))) * pesos[i];
            sumad2 +=  Integer.parseInt(Character.toString(numCuenta.charAt(i))) * pesos[i];
        }
        sumad1 = sumad1 % 11;
        sumad2 = sumad2 % 11;
        String DC = "" + sumad1 + sumad2;
        return DC;
    }

    /**
     * Recibe un CCC de cuenta, y llama a la función obtenerDigitosControl, esta
     * devuelve esos digitos calculados, y los compara con los de la cuenta.
     *
     * @param CCC
     * @return boolean
     */
    public static boolean comprobarCCC(String CCC) {
        String DC = obtenerDigitosControl(CCC.substring(0, 4), CCC.substring(4, 8), CCC.substring(10));
        return DC.equals(CCC.substring(8, 10));
    }

    private static boolean validarTitular(String titular) {
        Pattern nombre = Pattern.compile("^(?=.+[a-z])(?=.+[A-Z]).{10,100}$");
        Matcher m = nombre.matcher(titular);
        return m.matches();
    }

    /**
     * Utiliza una regex, X-Y inicial opcional, 9 dígitos y una letra.
     *
     * @param nif
     * @return boolean
     */
    private static boolean validarId(String nif) {
        boolean validez = false;
        char inicio = nif.charAt(0);
        //comprobar si es nif o nie
        if(Character.isDigit(nif.charAt(0))){
            validez = validarNif(nif);
        }else if(Character.isAlphabetic(nif.charAt(0)) && Character.isAlphabetic(nif.charAt(8)) && inicio == 'Y' || inicio == 'X' || inicio == 'Z'){
            String nie;
            if(nif.startsWith("X")){
                nie = "0" + nif.substring(1);
            }else if(nif.startsWith("y")){
                nie = "1" + nif.substring(1);
            }else{
                nie = "2" + nif.substring(1);
            }
            validez = validarNif(nie);
        }else if(Character.isAlphabetic(inicio) && inicio != 'Y' && inicio != 'X' && inicio != 'Z'){
            validez = validarCif(nif);
        }
        return validez;
    }
    
    /**
     * Valida el NIF y el NIE, con un patrón para la estructura y con un
     * algoritmo para la letra de control.
     * @param nif NIF/NIE de entrada
     * @return boolean
     */
    private static boolean validarNif(String nif){
        Pattern dni = Pattern.compile("([XYZ]?)([0-9]{1,9})([A-Za-z])");
        Matcher d = dni.matcher(nif);

        String cadenaValidadora = "TRWAGMYFPDXBNJZSQVHLCKE";
        boolean valido = false;

        int numero = Integer.parseInt(nif.substring(0, 8)) % 23;
        
        if(nif.endsWith(Character.toString(cadenaValidadora.charAt(numero)))){
            valido = true;
        }

        return d.matches() && valido;
    }
    
    /**
     * Valida el CIF de una empresa algorítmicamente, tanto con letra al final
     * como sin letra al final.
     * @param cif CIF a validar
     * @return boolean
     */
    private static boolean validarCif(String cif){
        //Variables
        String caracteres = "ABCDEFGHIJ";
        int sumaPares = 0;
        int sumaImpares = 0;
        boolean validez;
        char charFinal = cif.charAt(8);
        
        /**
         * Bucle que valida un CIF, separa los pares de los impares, los pares los suma en una variable,
         * los impares los multiplica por 2 y si son de 2 digitos, suma esos digitos y el resultado lo
         * suma a una variable.
         */
        for(int i=1; i < 8; i++){
            if(i%2 == 0){
                sumaPares += (Integer.parseInt(Character.toString(cif.charAt(i))));
            }else{
                if(Integer.parseInt(Character.toString(cif.charAt(i))) * 2 >= 10){
                    int digito1 = (Integer.parseInt(Character.toString(cif.charAt(i))) * 2) / 10;
                    int digito2 = (Integer.parseInt(Character.toString(cif.charAt(i))) * 2) % 10;
                    sumaImpares += digito1 + digito2;
                    System.out.println(sumaImpares);
                }else{
                    sumaImpares += Integer.parseInt(Character.toString(cif.charAt(i)));
                }
            }
        }
        
        String suma = "" + sumaPares + sumaImpares;
        int digito = Integer.parseInt(Character.toString(suma.charAt(suma.length()-1)));
        digito = 10 - digito;

        /**
         * Un CIF puede acabar en número o caracter, si acaba en caracter, el número final ha de coincidir
         * con la letra correcta del String [caracteres], y si es un digito, el cálculo ha de coincidir
         * con el digito final del CIF.
         */
        if(Character.isAlphabetic(charFinal)){
            validez = Character.toString(caracteres.charAt(digito)).equals(Character.toString(charFinal));
        }else{
            String digitoFinal = ""+digito;
            validez = Character.toString(charFinal).equals(digitoFinal);
        }
        return validez;
    }

    /**
     * Utiliza una regex con positive LookAhead, mínimo 2 dígitos, 1 minúscula 1
     * mayúscula, 8 carácteres y máximo 20 carácteres. No acepta espacios.
     *
     * @param contraseña
     * @return boolean
     */
    private static boolean validarContraseña(String contraseña) {
        Pattern pass = Pattern.compile("^(?=.+[0-9]{2,})(?=.+[a-z])(?=.+[A-Z])(?=\\S+$).{8,20}$");
        Matcher p = pass.matcher(contraseña);
        return p.matches();
    }
}
