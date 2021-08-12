
package salondebelleza.entidadesdenegocio;

import java.util.ArrayList;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String dui;
    private int numero;
    private int top_aux;
    private ArrayList<Usuario> usuarios;

    public Cliente() {
    }

    public Cliente(int id, String nombre, String apellido, String dui, int numero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dui = dui;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    //
}
