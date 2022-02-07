package Main;

/**
 * Esta clase crea un objeto del tipo Cuenta Bancaria, tiene atributos para identificar al dueño de esta,
 * como (String) Titular, (String) NIF [Puede ser un NIF/NIE/CIF] y (String) password.
 *
 * Tiene Atributos para identificar la cuenta de dicho usuario como (String) entidad, (String) Oficina
 * (String) números de control y (String) número de cuenta.
 *
 * A la hora de crear la clase se dispone de dos constructores, uno con los datos descompuestos, pero tambíen tiene
 * uno que acepta el CCC compuesto, que simplemente lo descompone y se lo manda al otro constructor.
 *
 * Todos los datos son validados y comprobados antes de ser asignados, se han usado Expresiones regulares para validar
 * la estructura y en el caso del DNI/NIE/CIF y del CCC, se separan y validan los números de control individualmente.
 *
 * @author Miguel García del Real Ortiz
 */
public class CuentaBancaria {

    //Atributos
    private String titular;
    private final String nif;
    private String password;
    private double saldo;
    private final String entidad;
    private final String oficina;
    private final String DC;
    private final String numCuenta;

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
        if (!funcionesValidadoras.validarTitular(titular)){
            throw new IllegalArgumentException("El titular no es válido");
        }
        
        if (funcionesValidadoras.validarId(nif)){
            throw new IllegalArgumentException("El nif no es válido");
        }
        
        if(!funcionesValidadoras.validarPassword(password)){
            throw new IllegalArgumentException("La contraseña no es válida");
        }
        
        if(!funcionesValidadoras.comprobarCCC(entidad + oficina + DC + numCuenta)){
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
     * @param titular Titular de la cuenta
     * @param nif NIF/NIE/CIF del titular
     * @param password Contraseña del titular
     * @param CCC Cuenta compuesta del titular
     */
    public CuentaBancaria(String titular, String nif, String password, String CCC) {
        this(titular, nif, password, CCC.substring(0, 4), CCC.substring(4, 8), CCC.substring(8, 10), CCC.substring(10));
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
        if (funcionesValidadoras.validarTitular(titular)) {
            this.titular = titular;
        }
    }

    public void setPassword(String password) {
        if (funcionesValidadoras.validarPassword(password)) {
            this.password = password;
        }
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" + "titular = " + titular + ", nif = " + nif + ", password = " + password + ", saldo = " + saldo + ", CCC = " + entidad + " " + oficina + " " + DC + " " + numCuenta + '}';
    }
}
