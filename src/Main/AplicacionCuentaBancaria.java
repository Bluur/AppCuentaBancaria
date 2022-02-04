package Main;

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
                    if (numeroControl == 0) {
                        System.out.println("No hay cuentas que gestionar");
                    } else {
                        String nifTemp = leerDatosTeclado.leerString("Dame el NIF de la cuenta");
                        gestionarCuenta(nifTemp, arrayCuentas);
                    }
                }
                case "4" -> {
                    if (numeroControl == 0) {
                        System.out.println("No hay cuentas en el banco");
                    } else {
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

        do {
            titular = leerDatosTeclado.leerString("Deme el nombre del titular de la cuenta");
        } while (!CuentaBancaria.validarTitular(titular));

        do {
            nif = leerDatosTeclado.leerString("Deme el NIF/NIE/CIF del titular");
        } while (!CuentaBancaria.validarId(nif));

        do {
            password = leerDatosTeclado.leerString("Deme la contraseña que quiere para su cuenta, 1 minúscula, 1 mayúscula y 8 carácteres");
        } while (!CuentaBancaria.validarContraseña(password));

        do {
            entidad = leerDatosTeclado.leerString("Deme la entidad bancaria");

            oficina = leerDatosTeclado.leerString("Deme la oficina bancaria");

            DC = leerDatosTeclado.leerString("Deme los digitos de control de su cuenta");

            numCuenta = leerDatosTeclado.leerString("Deme su número de cuenta");

        } while (!CuentaBancaria.comprobarCCC(entidad+oficina+DC+numCuenta));
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
    private static int buscarCuenta(String nif, CuentaBancaria[] cuentas) {
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
     *
     * @param nif NIF/NIE/CIF introducido
     * @param arrayCuentas Array bancario para realizar la busqueda de la cuenta
     */
    private static void gestionarCuenta(String nif, CuentaBancaria arrayCuentas[]) {
        int pos = buscarCuenta(nif, arrayCuentas);
        String decision;
        do {
            decision = menuGestion();
            switch (decision) {
                case "1" ->
                    System.out.println(arrayCuentas[pos].getCCC());
                case "2" ->
                    System.out.println(arrayCuentas[pos].getTitular());
                case "3" ->
                    System.out.println(arrayCuentas[pos].getNif());
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
                    if (arrayCuentas[pos].getSaldo() > cantidad) {
                        arrayCuentas[pos].retirar(cantidad);
                    } else {
                        System.out.println("No hay suficiente saldo para retirar esa cantidad");
                    }

                }
                case "7" ->
                    System.out.println(arrayCuentas[pos].getSaldo());
                case "8" ->
                    System.out.println("Saliendo del gestor de cuentas");
            }
        } while (!decision.equals("8"));
    }

    /**
     * Consulta los depósitos de todas las cuentas bancarias del banco
     *
     * @param arrayCuentas Array con todas las cuentas bancarias
     */
    private static void consultarDepósitos(CuentaBancaria[] arrayCuentas) {
        if (arrayCuentas[0] != null) {
            double sumaDepositos = 0;
            for (int i = 0; arrayCuentas[i + 1] != null && i <= 10; i++) {
                sumaDepositos += arrayCuentas[i].getSaldo();
            }
            System.out.println("La cantidad de dinero presente en el banco es de: " + sumaDepositos);
        }
    }

    /**
     * Elimina la cuenta seleccionada y reordena el array apropiadamente.
     *
     * @param arrayCuentas Array con las cuentas bancarias.
     * @param pos posición que debe eliminar del array.
     */
    private static void eliminarCuenta(CuentaBancaria[] arrayCuentas, int pos) {
        for (int i = pos; i < arrayCuentas.length - 1; i++) {
            if (arrayCuentas[i + 1] != null) {
                arrayCuentas[i] = arrayCuentas[i + 1];
            } else {
                arrayCuentas[i] = null;
            }
        }
    }

    /**
     * Menú del apartado de gestión de la cuenta bancaria
     *
     * @return String con la elección del usuario
     */
    private static String menuGestion() {
        for (int i = 0; i <= 8; i++) {
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
        do {
            decision = leerDatosTeclado.leerString("¿Qué operación desea realizar?(1-8)");
        } while (!validarEleccionMenu2(decision));
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
}
