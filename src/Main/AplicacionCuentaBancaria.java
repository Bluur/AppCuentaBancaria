package Main;

public class AplicacionCuentaBancaria {
    
    public static void main(String[] args) {
        CuentaBancaria arrayCuentas[] = new CuentaBancaria[10];
        
        int numeroControl = 0;
        
        String entidadBancaria = leerDatosTeclado.leerString("¿A que entidad bancaria perteneces?");
        String mensajeBienvenida = "Bienvenido a %s".formatted(entidadBancaria);
        System.out.println(mensajeBienvenida);

        boolean continuar = true;
        //Bucle
        while(continuar){
            String eleccion = menu();
                    
            switch(eleccion){
                case "1" -> {
                    if(numeroControl < 10){
                        CuentaBancaria cuenta = crearCuenta();
                        arrayCuentas[numeroControl] = cuenta;
                        numeroControl++;
                        System.out.println("Se ha añadido su cuenta correctamente");
                    }else{
                        System.out.println("No se pueden añadir más cuentas");
                    }
                }
                case "2" ->{
                    if(arrayCuentas[0] == null){
                        System.out.println("No hay cuentas que borrar");
                    }else{
                        String nif = leerDatosTeclado.leerString("Deme su dni");
                        int pos = buscarCuenta(nif, arrayCuentas);
                        eliminarCuenta(arrayCuentas, pos);
                    }
                }
                case "3" -> gestionarCuenta();
                case "4" -> consultarDepósitos();
                case "5" -> continuar = false;
            }

        }
    }
    
    /**
     * Esta función imprime un menú, recoge la elección y la valida. 
     * @return String que contiene chars de 1 a 5. 
     */
    public static String menu(){
        //Imprime las opciones
        for(int i = 0; i < 5; i++){
            switch (i){
                case 1 -> System.out.println("1.- Crear cuenta bancaria");
                case 2 -> System.out.println("2.- Eliminar cuenta bancaria");
                case 3 -> System.out.println("3.- Gestionar cuenta bancaria");
                case 4 -> System.out.println("4.- Consultar depósitos");
                case 5 -> System.out.println("5.- Salir del programa");
            }
        }
        //Recoge la elección en un bucle del que no sale hasta que sea una opción valida.
        String eleccion;
        do{
            eleccion = leerDatosTeclado.leerString("¿Qué operación desea realizar?(Introduzca el número de operación)");
        }while(!validarEleccion(eleccion));

        return eleccion;
    }
    
    /**
     * Recibe la String y la valida.
     * @param eleccion
     * @return 
     */
    private static boolean validarEleccion(String eleccion) {
        boolean validez = false;
        switch(eleccion){
            case "1" -> validez = true;
            case "2" -> validez = true;
            case "3" -> validez = true;
            case "4" -> validez = true;
            case "5" -> validez = true;
        }
        
        return validez;
    }

    private static CuentaBancaria crearCuenta() {
        boolean continuar = true;
        CuentaBancaria cuenta;
        do{
            String titular = "Miguel Garcia";
            String nif = "77446461X";
            String password = "170899170899aG";
            String entidad = "1324";
            String oficina = "3452";
            String DC = "63";
            String numCuenta = "8909312432";
            cuenta = new CuentaBancaria(titular, nif, password, entidad, oficina, DC, numCuenta);
            continuar = false;
        }while(continuar);
        return cuenta;
    }

    private static int buscarCuenta(String nif, CuentaBancaria[] cuentas) {
        int pos = 0;
        boolean continuar = true;
        for(int i = 0;continuar && i < 10 || cuentas[i+1] != null; i++){
            String nifPrimero = cuentas[i].getNif();
            if(nifPrimero.equals(nif)){
                pos = i;
                continuar = false;
            }
        }
        return pos;
    }

    private static void gestionarCuenta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void consultarDepósitos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void eliminarCuenta(CuentaBancaria[] arrayCuentas, int pos) {
        for(int i=pos; i < arrayCuentas.length-1; i++){
            if(arrayCuentas[i+1] != null){
                arrayCuentas[i] = arrayCuentas[i+1];
            }else{
                arrayCuentas[i] = null;
            }
        }
        
    }

}
