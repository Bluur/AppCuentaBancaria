package Main;

import java.util.Scanner;

public class leerDatosTeclado {

    public static int leerEntero(String mensaje) {
        int numero;
        Scanner input = new Scanner(System.in);
        System.out.println(mensaje);
        while (!input.hasNextInt()) {
            System.out.println("Ese número no me sirve");
            input.next();
        }
        numero = input.nextInt();
        return numero;
    }

    public static int leerEntero(String mensaje, int minimo) {
        int numero;
        Scanner input = new Scanner(System.in);
        System.out.println(mensaje);
        do {
            while (!input.hasNextInt()) {
                System.out.println("Ese número no me sirve");
                input.next();
            }
            numero = input.nextInt();
        } while (numero < minimo);
        return numero;
    }

    public static int LeerEntero(String mensaje, int minimo, int maximo) {
        int numero;
        Scanner input = new Scanner(System.in);
        System.out.println(mensaje);
        do {
            while (!input.hasNextInt()) {
                System.out.println("Ese número no me sirve");
                input.next();
            }
            numero = input.nextInt();
        } while (numero > maximo || numero < minimo);

        return numero;
    }

    public static double leerDouble(String mensaje) {
        double numero;
        Scanner input = new Scanner(System.in);
        System.out.println(mensaje);
        while (!input.hasNextDouble()) {
            System.out.println("Ese número no me sirve");
            input.next();
        }
        numero = input.nextDouble();
        return numero;
    }
    
     public static double leerDouble(String mensaje, double minimo){
        double numero;
        Scanner input = new Scanner(System.in);
        System.out.println(mensaje);
        
        do{
            while(!input.hasNextDouble()){
                System.out.println("Ese número no me sirve");
                input.next();
            }
        }while(input.nextDouble() < minimo);
        
        numero = input.nextDouble();
        return numero;
    }

    public static float leerFloat(String mensaje, float minimo, float maximo) {
        Scanner input = new Scanner(System.in);
        float numero;
        System.out.println(mensaje);
        do {
            while (!input.hasNextFloat()) {
                System.out.println("Ese número no me sirve");
                input.next();
            }
            numero = input.nextFloat();
        } while (!(numero < minimo) && !(numero > maximo));
        return numero;
    }

    public static String leerString(String mensaje, String condicion1, String condicion2) {
        Scanner input = new Scanner(System.in);
        String recu;
        System.out.println(mensaje);
        do {
            recu = input.next().toLowerCase();
        } while (!recu.equals(condicion1) && !recu.equals(condicion2));

        return recu;
    }

    public static String leerString(String mensaje, String condicion1) {
        Scanner input = new Scanner(System.in);
        String recu;
        System.out.println(mensaje);
        do {
            recu = input.next().toLowerCase();
        } while (!recu.equals(condicion1));

        return recu;
    }

    public static String leerString(String mensaje, String condicion1, String condicion2, String condicion3) {
        Scanner input = new Scanner(System.in);
        String recu;
        System.out.println(mensaje);
        do {
            recu = input.next().toLowerCase();
        } while (!recu.equals(condicion1) && !recu.equals(condicion2) && !recu.equals(condicion3));

        return recu;
    }

    public static String leerString(String mensaje) {
        Scanner input = new Scanner(System.in);
        String recu = "";

        System.out.println(mensaje);
        recu = input.nextLine();

        return recu;
    }
}
