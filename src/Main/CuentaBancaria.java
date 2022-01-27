
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
     * Crea un objeto de la clase cuenta bancaria
     * @param titular
     * @param nif
     * @param password
     * @param entidad
     * @param oficina
     * @param DC
     * @param numCuenta 
     */
    //Constructor con parámetros individuales
    public CuentaBancaria(String titular, String nif, String password, String entidad, String oficina, String DC, String numCuenta){
        if(validarTitular(titular)){
            this.titular = titular;
        }else{
            throw new IllegalArgumentException("El titular no es válido");
        }
        
        if(validarNif(nif)){
            this.nif = nif;
        }else{
            throw new IllegalArgumentException("El nif no es válido");
        }
        
        if(validarContraseña(password)){
            this.password = password;
        }else{
            throw new IllegalArgumentException("La contraseña no es válida");
        }
        
        this.entidad = entidad;
        this.oficina = oficina;
        this.numCuenta = numCuenta;
        this.DC = obtenerDigitosControl(this.entidad, this.oficina, this.numCuenta);
    }
    
    /**
     * Construye una Cuenta bancaria a partir de un CCC, es decir, un número completo.
     * Primero valida el CCC comprobando los dígitos de control y si es válido crea el objeto.
     * @param titular
     * @param nif
     * @param password
     * @param CCC 
     */
    //Constructor con parámetros compuestos (CCC)
    public CuentaBancaria(String titular, String nif, String password, String CCC){
       if(validarCCC(CCC)){
           this.titular = titular; 
           this.nif = nif;
           this.password = password; 
           this.entidad = CCC.substring(0, 4); 
           this.oficina = CCC.substring(4, 8); 
           this.numCuenta = CCC.substring(10);
           this.DC = CCC.substring(8, 10);
       }else{
           throw new IllegalArgumentException("Los dígitos de control no son correctos");
       }
    }
    
    //Métodos Públicos
    /**
     * Se le pasan la entidad, oficina y numCuenta, para que 
     * calcule el numero de control.
     * @param entidad
     * @param oficina
     * @param numCuenta
     * @return DC
     */
    public static String obtenerDigitosControl(String entidad, String oficina, String numCuenta){
        int pesos[] = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
        int sumad1 = 0;
        int sumad2 = 0;
        String cad1 = "00" + entidad + oficina;
        for(int i=0; i < pesos.length; i++){
            sumad1 += (int)cad1.charAt(i) * pesos[i];
            sumad2 += (int)numCuenta.charAt(i) * pesos[i];
        }
        sumad1 = sumad1 % 11;
        sumad2 = sumad2 % 11;
        String DC = ""+sumad1+sumad2;
        return DC;
    }
    /**
     * Recibe un CCC de cuenta, y llama a la función obtenerDigitosControl,
     * esta devuelve esos digitos calculados, y los compara con los de la cuenta.
     * @param CCC
     * @return boolean
     */
    public static boolean validarCCC(String CCC){
        String DC = obtenerDigitosControl(CCC.substring(0, 4), CCC.substring(4, 8), CCC.substring(10));
        return DC.equals(CCC.substring(8, 10));
    }
    
    public void retirar(double cantidad){
        if(cantidad > this.saldo || cantidad < 0){
            throw new IllegalArgumentException("La cantidad no es válida");
        }
        this.saldo -= cantidad;
    }
    
    public void ingresar(double cantidad){
        if(cantidad < 0){
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
    
    public String getDC(){
        return DC;
    }
    
    public String getCCC(){
        return this.entidad+this.oficina+this.DC+this.numCuenta;
    }

    public void setTitular(String titular) {
        if(validarTitular(titular)){
            this.titular = titular;
        }
    }

    public void setPassword(String password) {
        if(validarContraseña(password)){
            this.password = password;
        }
    }

    @Override
    public String toString() {
        return "CuentaBancaria{"+"titular = "+titular+", nif = "+nif+", password = "+password+", saldo = "+saldo +", CCC = " + entidad+" "+oficina+" "+DC+" "+numCuenta+'}';
    }

    //Métodos Privados
    
    /**
    * Utiliza una regex con positive lookahead, 1 minúscula,
    * 1 mayúscula y mínimo 10 carácteres, máximo 100.
    * @param titular
    * @return boolean 
    */
    private boolean validarTitular(String titular){
        Pattern nombre = Pattern.compile("^(?=.+[a-z])(?=.+[A-Z]).{10,100}$");
        Matcher m = nombre.matcher(titular);
        return m.matches();
    }
    
    /**
     * Utiliza una regex, X-Y inicial opcional, 9 dígitos y una letra.
     * @param nif
     * @return boolean
     */
    private boolean validarNif(String nif){
        Pattern dni = Pattern.compile("([XY]?)([0-9]{1,9})([A-Za-z])");
        Matcher d = dni.matcher(nif);
        return d.matches();
    }
    
    /**
     * Utiliza una regex con positive LookAhead, mínimo 2 dígitos, 1 minúscula
     * 1 mayúscula, 8 carácteres y máximo 20 carácteres. No acepta espacios.
     * @param contraseña
     * @return boolean
     */
    private boolean validarContraseña(String contraseña){
        Pattern pass = Pattern.compile("^(?=.+[0-9]{2,})(?=.+[a-z])(?=.+[A-Z])(?=\\S+$).{8,20}$");
        Matcher p = pass.matcher(contraseña);
        return p.matches();
    }
}
