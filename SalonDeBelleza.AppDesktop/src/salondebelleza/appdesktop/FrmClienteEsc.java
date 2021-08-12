package salondebelleza.appdesktop;

import java.util.ArrayList; //Importar el ArrayList para recibir la lista de Roles de la base de datos.
import javax.swing.JOptionPane; //Importar la clase JOptionPane.  
import salondebelleza.accesoadatos.*; //Importar todas las clases de la capa acceso a datos
import salondebelleza.appdesktop.utils.*; //Importar todas las clases de utilerias de las aplicaciones de escritorio.
import salondebelleza.entidadesdenegocio.*; // Importar todas las clases de la capa Entidades de negocio.

public class FrmClienteEsc extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Codigo para propiedades y metodos locales del formulario FrmRolEsc">
    private FrmClienteLec frmPadre; // Propiedad para almacenar el formulario de FrmRolLec
    private int opcionForm; // Propiedad para almacenar la opcion a realizar en el formulario puede ser Crear,Modificar,Eliminar, Ver
    private Cliente clienteActual; // Propiedad para almacenar el rol que se desea crear,modificar,eliminar sus datos

    // metodo para llenar los controles del formulario FrmRolEsc con los datos que hay en la base de datos del rol 
    // solicitado desde la pantalla de FrmRolLec
    private void llenarControles(Cliente pCliente) {
        try {
            clienteActual = ClienteDAL.obtenerPorId(pCliente); // Obtener el Rol por Id 
            this.txtNombre.setText(clienteActual.getNombre()); // Llenar la caja de texto txtNombre con el nombre del rol 
            this.txtApellido.setText(clienteActual.getApellido());
            this.txtDui.setText(clienteActual.getDui());
            //clienteActual.setNumero(Integer.parseInt(this.txtNumero.getText()));
            this.txtNumero.setText(Integer.toString(clienteActual.getNumero()));
        } catch (Exception ex) {
            // Enviar el mensaje al usuario de la pantalla en el caso que suceda un error al obtener los datos de la base de datos
            JOptionPane.showMessageDialog(frmPadre, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    // metodo a utilizar para iniciar los datos al momento de mostrar el formulario FrmRolEsc
    private void iniciarDatos(Cliente pCliente, int pOpcionForm, FrmClienteLec pFrmPadre) {
        frmPadre = pFrmPadre;
        clienteActual = new Cliente();
        opcionForm = pOpcionForm;
        this.txtNombre.setEditable(true); // colocar txtNombre que se pueda editar 
        this.txtApellido.setEditable(true); // colocar txtNombre que se pueda editar 
        this.txtDui.setEditable(true); // colocar txtNombre que se pueda editar 
        this.txtNumero.setEditable(true); // colocar txtNombre que se pueda editar 
        switch (pOpcionForm) {
            case FormEscOpcion.CREAR:
                btnOk.setText("Nuevo"); // modificar el texto del boton btnOk a "Nuevo" cuando la pOpcionForm sea CREAR
                this.btnOk.setMnemonic('N'); // modificar la tecla de atajo del boton btnOk a la letra N
                this.setTitle("Crear un nuevo Cliente"); // modificar el titulo de la pantalla de FrmRolEsc
                break;
            case FormEscOpcion.MODIFICAR:
                btnOk.setText("Modificar"); // modificar el texto del boton btnOk a "Modificar" cuando la pOpcionForm sea MODIFICAR
                this.btnOk.setMnemonic('M'); // modificar la tecla de atajo del boton btnOk a la letra M
                this.setTitle("Modificar el Cliente"); // modificar el titulo de la pantalla de FrmRolEsc
                llenarControles(pCliente);
                break;
            case FormEscOpcion.ELIMINAR:
                btnOk.setText("Eliminar");// modificar el texto del boton btnOk a "Eliminar" cuando la pOpcionForm sea ELIMINAR
                this.btnOk.setMnemonic('E'); // modificar la tecla de atajo del boton btnOk a la letra E
                this.setTitle("Eliminar el Cliente"); // modificar el titulo de la pantalla de FrmRolEsc
                this.txtNombre.setEditable(false); // deshabilitar la caja de texto txtNombre
                this.txtApellido.setEditable(false);
                this.txtDui.setEditable(false);
                this.txtNumero.setEditable(false);
                llenarControles(pCliente);
                break;
            case FormEscOpcion.VER:
                btnOk.setText("Ver"); // modificar el texto del boton btnOk a "Ver" cuando la pOpcionForm sea VER
                btnOk.setVisible(false); // ocultar el boton btnOk cuando pOpcionForm sea opcion VER
                this.setTitle("Ver el Cliente"); // modificar el titulo de la pantalla de FrmRolEsc
                this.txtNombre.setEditable(false); // deshabilitar la caja de texto txtNombre
                this.txtApellido.setEditable(false);
                this.txtDui.setEditable(false);
                this.txtNumero.setEditable(false);
                llenarControles(pCliente);
                break;
            default:
                break;
        }
    }
/////////////////////////////////////////////////////
    // validar los datos antes de enviar a la DAL de Rol

    private boolean validarDatos() {
        boolean result = true; // variable para saber si los datos son validos para su envio a la DAL de Rol y despues a la base de datos 
        // verificar si la caja de texto txtNombre esta vacia 
        if (this.txtNombre.getText().trim().isEmpty()) {
            result = false; // en el caso que la caja de texto txtNombre este vacia se colocara la variable resul en false
        }

        if (this.txtApellido.getText().trim().isEmpty()) {
            result = false; // en el caso que la caja de texto txtNombre este vacia se colocara la variable resul en false
        }

        if (this.txtDui.getText().trim().isEmpty()) {
            result = false; // en el caso que la caja de texto txtNombre este vacia se colocara la variable resul en false
        }

        if (this.txtNumero.getText().trim().isEmpty()) {
            result = false; // en el caso que la caja de texto txtNombre este vacia se colocara la variable resul en false
        }

        if (result == false) {
            // mostrar un mensaje al usuario de la pantalla que los campos son obligatorios en el caso que la variable result sea false
            JOptionPane.showMessageDialog(this, "Los campos con * son obligatorios");
        }
        return result; // retorna la variable result con el valor true o false para saber si los datos son validos o no
    }

    // metodo para obtener el mensaje de confirmacion de envio de informacion a la base de datos
    private int obtenerMensajeDeConfirmacion() {
        String mess = "¿Seguro que desea ";
        switch (opcionForm) {
            case FormEscOpcion.CREAR:
                mess += " Guardar?";
                break;
            case FormEscOpcion.MODIFICAR:
                mess += " Modificar?";
                break;
            case FormEscOpcion.ELIMINAR:
                mess += " Eliminar?";
                break;
            default:
                break;
        }
        // pedir confirmacion al usuario de la pantalla si desea enviar la informacion o no utilizando un showConfirmDialog
        int input = JOptionPane.showConfirmDialog(this, mess, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return input; // retornar la respuesta del usuario de la pantalla al utilizar el showConfirmDialog
    }

    // Metodo para llenar la entidad de rol con la informacion que esta en la caja de texto del formulario FrmRolEsc
    private void llenarEntidadConLosDatosDeLosControles() {
        clienteActual.setNombre(txtNombre.getText());
        clienteActual.setApellido(txtApellido.getText());
        clienteActual.setDui(txtDui.getText());
        //clienteActual.setNumero(txtNumero.getText());
        //clienteActual.setNumero(Integer.parseInt(this.txtNumero.getText()));
        clienteActual.setNumero(Integer.parseInt(this.txtNumero.getText()));
    }

    // metodo para cerrar el formulario FrmRolEsc 
    private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario FrmRolLec
            frmPadre.setVisible(true); // mostrar el formulario FrmRolLec
        }
        if (pIsEvtClosing == false) { // verificar que no se este cerrando el formulario desde el evento formWindowClosing 
            this.setVisible(false); // Cerrar el formulario FrmRolEsc
            this.dispose(); // Cerrar todos los procesos abiertos en el formulario FrmRolEsc
        }
    }

    // metodo para enviar los datos a la base de datos segun la opcion de la propiedad opcionForm 
    private void enviarDatos() {
        try {
            if (validarDatos()) { // verificar si todos los datos obligatorios tienen informacion
                // verificar que el usuario de la pantalla presiono el boton YES
                if (obtenerMensajeDeConfirmacion() == JOptionPane.YES_OPTION) {
                    llenarEntidadConLosDatosDeLosControles(); // llenar la entidad de Rol con los datos de la caja de texto del formulario
                    int resultado = 0;
                    switch (opcionForm) {
                        case FormEscOpcion.CREAR:
                            resultado = ClienteDAL.crear(clienteActual); // si la propiedad opcionForm es CREAR guardar esos datos en la base de datos
                            break;
                        case FormEscOpcion.MODIFICAR:
                            resultado = ClienteDAL.modificar(clienteActual);// si la propiedad opcionForm es MODIFICAR actualizar esos datos en la base de datos
                            break;
                        case FormEscOpcion.ELIMINAR:
                            // si la propiedad opcionForm es ELIMINAR entonces quitamos ese registro de la base de datos
                            resultado = ClienteDAL.eliminar(clienteActual);
                            break;
                        default:
                            break;
                    }
                    if (resultado != 0) {
                        // notificar al usuario que "Los datos fueron correctamente actualizados"
                        JOptionPane.showMessageDialog(this, "Los datos fueron correctamente actualizados");
                        if (frmPadre != null) {
                            // limpiar los datos de la tabla de datos del formulario FrmRolLec
                            frmPadre.iniciarDatosDeLaTabla(new ArrayList());
                        }
                        this.cerrarFormulario(false); // Cerrar el formulario utilizando el metodo "cerrarFormulario"
                    } else {
                        // En el caso que las filas modificadas en el la base de datos sean cero 
                        // mostrar el siguiente mensaje al usuario "Sucedio un error al momento de actualizar los datos"
                        JOptionPane.showMessageDialog(this, "Sucedio un error al momento de actualizar los datos");
                    }
                }
            }
        } catch (Exception ex) {
            // En el caso que suceda un error al ejecutar la consulta en la base de datos 
            // mostrar el siguiente mensaje al usuario "Sucedio un error al momento de actualizar los datos"
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }

    }
    
      // constructor de la clase FrmServicioEsc a utilizar en la clase FrmServicioLec
    // el constructor pide como parametros el Servicio, Opcion , formulario FrmServicioLec
    public FrmClienteEsc(Cliente pCliente, int pOpcionForm, FrmClienteLec pFrmPadre) {
        initComponents();
        iniciarDatos(pCliente, pOpcionForm, pFrmPadre);
    }

    // constructor de la clase FrmRolEsc a utilizar en la clase FrmRolLec
    // el constructor pide como parametros el Rol, Opcion , formulario FrmRolLec
// </editor-fold>
    // constructor de la clase FrmRolEsc a utilizar en la clase FrmRolLec
    // el constructor pide como parametros el Rol, Opcion , formulario FrmRolLec
    public FrmClienteEsc() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDui = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mantenimiento de Cliente");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Nombre *");

        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel2.setText("Apellido *");

        jLabel3.setText("Dui *");

        jLabel4.setText("Telefono *");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(14, 14, 14)
                                .addComponent(txtNumero))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(43, 43, 43)
                                .addComponent(txtDui))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtApellido))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        this.enviarDatos();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.cerrarFormulario(false );
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.cerrarFormulario(true);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDui;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables
}
