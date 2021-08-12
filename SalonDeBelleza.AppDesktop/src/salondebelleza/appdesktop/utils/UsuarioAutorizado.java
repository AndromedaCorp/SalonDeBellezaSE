/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salondebelleza.appdesktop.utils;

/**
 *
 * @author hecto
 */
public class UsuarioAutorizado {
    private static int id;
    private static String login;
    
    public static int getId() {
        return id;
    }
    
    public static void setId(int id) {
        UsuarioAutorizado.id = id;
    }
    
     public static String getLogin() {
        return login;
    }
     
    public static void setLogin(String login) {
        UsuarioAutorizado.login = login; 
    }
    
}
