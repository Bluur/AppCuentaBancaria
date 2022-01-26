package Main;

public class AplicacionCuentaBancaria {

    public static void main(String[] args) {
        String titular = "Miguel García del Real";
        String contraseña = "170899170899aG";
        String dni = "77446461X";
        String entidad = "1324";
        String oficina = "3452";
        String DC = "22";
        String numCuenta = "8909312432";
        CuentaBancaria caixaMiguel = new CuentaBancaria(titular, dni, contraseña, entidad, oficina, DC, numCuenta);
        
        caixaMiguel.ingresar(500);
        System.out.println(caixaMiguel);
    }
    
}
