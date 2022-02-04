package Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CuentaBancaria {

    //Atributos
    private String titular;
    private String nif;
    private String password;
    private double saldo;
    private String entidad;
    private String oficina;
    private String DC;
    private String numCuenta;

    /**
     * Constructor con parámetros individuales, valida los datos, los añade a
     * sus respectivos campos, creando el objeto. Para que se pueda crear el 
     * objeto, los datos han de ser correctos, en caso de que no encajen los
     * dígitos de control saltará una excepción, por lo que es recomendado 
     * comprobar los datos antes de usar la clase.
     *
     * @param titular String con el nombre y apellidos del titular. Min 10 máx
     * 100.
     * @param nif String con el DNI. Debe cumplir los parámetros de un nif, en 
     * caso contrario, saltará excepción.
     * @param password String con la contraseña. Min 2 números, 2 letras, 1 
     * mayúscula y mínimo 8 carácteres, máximo 20.
     * @param entidad Número de la entidad bancaria.
     * @param oficina Número de la oficina bancaria.
     * @param DC Dígitos de control que se calculan con una fórmula, en caso de 
     * no ser válidos, saltará una excepción.
     * @param numCuenta Número de cuenta del usuario.
     */
    public CuentaBancaria(String titular, String nif, String password, String entidad, String oficina, String DC, String numCuenta) {
        if (!validarTitular(titular)){
            throw new IllegalArgumentException("El titular no es válido");
        }
        
        if (!validarId(nif)){
            throw new IllegalArgumentException("El nif no es válido");
        }
        
        if(!validarContraseña(password)){
            throw new IllegalArgumentException("La contraseña no es válida");
        }
        
        if(!comprobarCCC(entidad + oficina + DC + numCuenta)){
            throw new IllegalArgumentException("Los dígitos de control no son válidos");
        }
        
        this.titular = titular;
        this.nif = nif;
        this.password = password;
        this.entidad = entidad;
        this.oficina = oficina;
        this.DC = DC;
        this.numCuenta = numCuenta;
    }

    /**
     * Construye un objeto de la clase con parámetros compuestos, es decir,
     * recibe el numero de cuenta entero, valida el número validando los dígitos
     * de control y en caso de ser un nº de cuenta correcto valida y añade el
     * resto de campos.
     *
     * @param titular
     * @param nif
     * @param password
     * @param CCC
     */
    public CuentaBancaria(String titular, String nif, String password, String CCC) {
        this(titular, nif, password, CCC.substring(0, 4), CCC.substring(4, 8), CCC.substring(8, 10), CCC.substring(10));
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

    public void retirar(double cantidad) {
        if (cantidad > this.saldo || cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no es válida");
        }
        this.saldo -= cantidad;
    }

    public void ingresar(double cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no es válida");
        }
        this.saldo += cantidad;
    }

    public String getTitular() {
        return titular;
    }

    public String getNif() {
        return nif;
    }

    public String getPassword() {
        return password;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getEntidad() {
        return entidad;
    }

    public String getOficina() {
        return oficina;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public String getDC() {
        return DC;
    }

    public String getCCC() {
        return this.entidad + this.oficina + this.DC + this.numCuenta;
    }

    public void setTitular(String titular) {
        if (validarTitular(titular)) {
            this.titular = titular;
        }
    }

    public void setPassword(String password) {
        if (validarContraseña(password)) {
            this.password = password;
        }
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" + "titular = " + titular + ", nif = " + nif + ", password = " + password + ", saldo = " + saldo + ", CCC = " + entidad + " " + oficina + " " + DC + " " + numCuenta + '}';
    }

    //Métodos Privados
    /**
     * Utiliza una regex con positive lookahead, 1 minúscula, 1 mayúscula y
     * mínimo 10 carácteres, máximo 100.
     *
     * @param titular
     * @return boolean
     */
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
