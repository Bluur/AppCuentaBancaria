package Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AplicacionCuentaBancaria {

    public static void main(String[] args) {
        //Crea array vacío para introducir las cuentas y número de control de cuentas
        CuentaBancaria arrayCuentas[] = new CuentaBancaria[10];
        int numeroControl = 0;

        //Mensaje de Bienvenida estático
        String entidadBancaria = leerDatosTeclado.leerString("¿A que entidad bancaria perteneces?");
        String mensajeBienvenida = "Bienvenido a %s".formatted(entidadBancaria);
        System.out.println(mensajeBienvenida);

        //Variable de control de flujo
        boolean continuar = true;

        //Bucle principal que se ejecutará mientras continuar sea verdadero
        while (continuar) {
            //Función del menú principal
            String eleccion = menu();

            //Switch elección para controlar el flujo de decisiones
            switch (eleccion) {
                case "1" -> {
                    if (numeroControl < 10) {
                        CuentaBancaria cuenta = crearCuenta();
                        arrayCuentas[numeroControl] = cuenta;
                        numeroControl++;
                        System.out.println("Se ha añadido su cuenta correctamente");
                    } else {
                        System.out.println("No se pueden añadir más cuentas");
                    }
                }
                case "2" -> {
                    if (arrayCuentas[0] == null) {
                        System.out.println("No hay cuentas que borrar");
                    } else {
                        String nif = leerDatosTeclado.leerString("Deme el NIF de la cuenta");
                        int pos = buscarCuenta(nif, arrayCuentas);
                        eliminarCuenta(arrayCuentas, pos);
                        numeroControl--;
                        System.out.println("Su cuenta se ha borrado apropiadamente");
                    }
                }
                case "3" -> {
                    if(numeroControl == 0){
                        System.out.println("No hay cuentas que gestionar");
                    }else{
                        String nifTemp = leerDatosTeclado.leerString("Dame el NIF de la cuenta");
                        gestionarCuenta(nifTemp, arrayCuentas);
                    }
                }
                case "4" ->{
                    if(numeroControl == 0){
                        System.out.println("No hay cuentas en el banco");
                    }else{
                        consultarDepósitos(arrayCuentas);
                    }
                }
                case "5" ->
                    continuar = false;
            }

        }
    }

    /**
     * Esta función imprime un menú, recoge la elección y la valida.
     *
     * @return String que contiene chars de 1 a 5.
     */
    public static String menu() {
        //Imprime las opciones
        for (int i = 0; i <= 5; i++) {
            switch (i) {
                case 1 ->
                    System.out.println("1.- Crear cuenta bancaria");
                case 2 ->
                    System.out.println("2.- Eliminar cuenta bancaria");
                case 3 ->
                    System.out.println("3.- Gestionar cuenta bancaria");
                case 4 ->
                    System.out.println("4.- Consultar depósitos");
                case 5 ->
                    System.out.println("5.- Salir del programa");
            }
        }
        //Recoge la elección en un bucle del que no sale hasta que sea una opción valida.
        String eleccion;
        do {
            eleccion = leerDatosTeclado.leerString("¿Qué operación desea realizar?(Introduzca el número de operación)");
        } while (!validarEleccionMenu1(eleccion));

        return eleccion;
    }

    /**
     * Recibe la String y la valida.
     *
     * @param eleccion String que contine el número elegido por el usuario
     * @return boolean
     */
    private static boolean validarEleccionMenu1(String eleccion) {
        return eleccion.equals("1") || eleccion.equals("2") || eleccion.equals("3") || eleccion.equals("4") || eleccion.equals("5");
    }

    /**
     * Función que crea una cuentaBancaria, actualmente con datos de prueba
     *
     * @return Objeto CuentaBancaria
     */
    private static CuentaBancaria crearCuenta() {
        CuentaBancaria cuenta;
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
            password = leerDatosTeclado.leerString("Deme la contraseña que quiere para su cuenta, 1 minúscula, 1 mayúscula y 8 carácteres");
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

        cuenta = new CuentaBancaria(titular, nif, password, entidad, oficina, DC, numCuenta);
        return cuenta;
    }

    /**
     * Busca una cuenta que contenga el NIF introducido, pide la contraseña de
     * esa cuenta y en caso de ser correcta, borra la cuenta pasando todas las
     * que hay a la derecha hacia la izquierda.
     *
     * @param nif String con el DNI introducido por el usuario para efectuar la
     * búsqueda
     * @param cuentas Array con todas las cuentas
     * @return int con la posición de la cuenta con el DNI seleccionado.
     */

    private static int  buscarCuenta(String nif, CuentaBancaria[] cuentas) {
        int pos = 0;
        boolean continuar = true;
        for (int i = 0; continuar && i < 10 && cuentas[i + 1] != null; i++) {
            String nifPrimero = cuentas[i].getNif();
            if (nifPrimero.equals(nif)) {
                pos = i;
                continuar = false;
            }
        }
        return pos;
    }
    
    /**
     * Función que gestiona la cuenta bancaria asociada al NIF/NIE/CIF elegido
     * @param nif NIF/NIE/CIF introducido
     * @param arrayCuentas Array bancario para realizar la busqueda de la cuenta
     */
    private static void gestionarCuenta(String nif, CuentaBancaria arrayCuentas[]) {
        int pos = buscarCuenta(nif, arrayCuentas);
        String decision;
        do{
            decision = menuGestion();
            switch(decision){
                case "1" -> System.out.println(arrayCuentas[pos].getCCC());
                case "2" -> System.out.println(arrayCuentas[pos].getTitular());
                case "3" -> System.out.println(arrayCuentas[pos].getNif());
                case "4" -> {
                    System.out.println("Dame la contraseña que quieres usar");
                    String contraseña = leerDatosTeclado.leerString("Debe tener al menos 1 mayus, 1 minus, y 8 carácteres");
                    arrayCuentas[pos].setPassword(contraseña);
                }
                case "5" -> {
                    double cantidad = leerDatosTeclado.leerDouble("Dame la cantidad que quieres ingresar", 0);
                    arrayCuentas[pos].ingresar(cantidad);
                }
                case "6" -> {
                    double cantidad = leerDatosTeclado.leerDouble("Dame la cantidad que quieres retirar", 0);
                    if(arrayCuentas[pos].getSaldo() > cantidad){
                        arrayCuentas[pos].retirar(cantidad);
                    }else{
                        System.out.println("No hay suficiente saldo para retirar esa cantidad");
                    }
                    
                }
                case "7" -> System.out.println(arrayCuentas[pos].getSaldo());
                case "8" -> System.out.println("Saliendo del gestor de cuentas");
            }
        }while(!decision.equals("8"));
    }
    
    private static void consultarDepósitos(CuentaBancaria[] arrayCuentas) {
        if(arrayCuentas[0] != null){
            double sumaDepositos = 0;
            for(int i=0; arrayCuentas[i+1] != null && i <= 10; i++){
                sumaDepositos += arrayCuentas[i].getSaldo();
            }
            System.out.println("La cantidad de dinero presente en el banco es de: "+sumaDepositos);
        }
    }
    
    /**
     * Elimina la cuenta seleccionada y reordena el array apropiadamente.
     * @param arrayCuentas Array con las cuentas bancarias.
     * @param pos posición que debe eliminar del array.
     */
    private static void eliminarCuenta(CuentaBancaria[] arrayCuentas, int pos) {
        for (int i = pos; i < arrayCuentas.length - 1; i++) {
            if (arrayCuentas[i + 1] != null) {
                System.out.println(arrayCuentas);
                arrayCuentas[i] = arrayCuentas[i + 1];
                System.out.println(arrayCuentas);
            } else {
                arrayCuentas[i] = null;
            }
        }
    }
    
    /**
     * Menú del apartado de gestión de la cuenta bancaria
     * @return String con la elección del usuario
     */
    private static String menuGestion() {
        for(int i = 0; i <= 8; i++){
            switch (i) {
                case 1 ->
                    System.out.println("1.- Ver el número de cuenta completo");
                case 2 ->
                    System.out.println("2.- Ver el titular de la cuenta");
                case 3 ->
                    System.out.println("3.- Ver el NIF de la cuenta");
                case 4 ->
                    System.out.println("4.- Modificar la contraseña");
                case 5 ->
                    System.out.println("5.- Realizar un ingreso");
                case 6 -> 
                    System.out.println("6.- Retirar Efectivo");
                case 7 -> 
                    System.out.println("7.- Consultar Saldo");
                case 8 ->
                    System.out.println("8.- Volver al menú principal");
            }
        }
        
        String decision;
        do{
            decision = leerDatosTeclado.leerString("¿Qué operación desea realizar?(1-8)");
        }while(!validarEleccionMenu2(decision));
        return decision;
    }
    
    /**
     * Recibe la String y la valida.
     *
     * @param eleccion String que contine el número elegido por el usuario
     * @return boolean
     */
    private static boolean validarEleccionMenu2(String eleccion) {
        return eleccion.equals("1") || eleccion.equals("2") || eleccion.equals("3") || eleccion.equals("4") || eleccion.equals("5") || eleccion.equals("6") || eleccion.equals("7") || eleccion.equals("8");
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
        }else{
            System.out.println("El NIF/NIE/CIF no es válido");
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
