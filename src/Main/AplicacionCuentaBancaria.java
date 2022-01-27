package Main;

public class AplicacionCuentaBancaria {

    public static void main(String[] args) {
        String titular = "Miguel García del Real";
        String contraseña = "170899170899aG";
        String dni = "77446461X";
        /*
        String entidad = "1324";
        String oficina = "3452";
        String DC = "";
        String numCuenta = "8909312432";
        */
        String CCC = "13243452638909312432";
        CuentaBancaria caixaMiguel = new CuentaBancaria(titular, dni, contraseña, CCC);
        
        caixaMiguel.ingresar(500);
        System.out.println(caixaMiguel);
        System.out.println(caixaMiguel.getDC());
    }
    
}
