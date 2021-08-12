/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salondebelleza.entidadesdenegocio;

import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class Usuario {

    private int id;
    private int idrol;
    private String dui;
    private String nombre;
    private String apellido;
    private String numero;
    private String login;
    private String password;
    private byte estado;
    private LocalDate fechaRegistro;
    private int top_aux;
    private String confirmarPassword_aux;
    private Rol rol;

    public Usuario() {
    }

    public Usuario(int id, int idrol, String dui, String nombre, String apellido, String numero, String login, String password, byte estado, LocalDate fechaRegistro) {
        this.id = id;
        this.idrol = idrol;
        this.dui = dui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.login = login;
        this.password = password;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public String getConfirmarPassword_aux() {
        return confirmarPassword_aux;
    }

    public void setConfirmarPassword_aux(String confirmarPassword_aux) {
        this.confirmarPassword_aux = confirmarPassword_aux;
    }

        public Rol getRol() {
        return rol;
    }
    

    public void setRol(Rol rol) {
        this.rol = rol;
    }


    public class EstadoUsuario {

        public static final byte ACTIVO = 1;
        public static final byte INACTIVO = 2;
    }

}
