
package salondebelleza.appdesktop;

// Importaciones para el funcionamiento de la pantalla de mantenimiento de Servicio
import salondebelleza.appdesktop.utils.*; // importar todas las clases de utilidades de la aplicaciones escritorio
import salondebelleza.accesoadatos.*; // importar todas la clases de la capa de acceso a datos
import salondebelleza.entidadesdenegocio.*; // importar todas la clases de la capa de entidades de negocio
import java.util.ArrayList; // importar el ArrayList para recibir la lista de Roles de la base de datos
import javax.swing.JOptionPane; // importa la clase JOptionPane para mostrar alertas o advertencias a los usuarios

public class FrmServicioEsc extends javax.swing.JFrame {
    
    // <editor-fold defaultstate="collapsed" desc="Codigo para propiedades y metodos locales del formulario FrmRolEsc">
    private FrmServicioLec frmPadre; // Propiedad para almacenar el formulario de FrmServicioLec
    private int opcionForm; // Propiedad para almacenar la opcion a realizar en el formulario puede ser Crear,Modificar,Eliminar, Ver
    private Servicio servicioActual; // Propiedad para almacenar el servicio que se desea crear,modificar,eliminar sus datos

    // metodo para llenar los controles del formulario FrmServicioEsc con los datos que hay en la base de datos del servicio 
    // solicitado desde la pantalla de FrmServicioLec
    private void llenarControles(Servicio pServicio) {
        try {
            servicioActual = ServicioDAL.obtenerPorId(pServicio); // Obtener el Servicio por Id 
            this.txtNombre.setText(servicioActual.getNombre()); // Llenar la caja de texto txtNombre con el nombre del servicio
            this.txtDescripcion.setText(servicioActual.getDescripcion());
            this.txtPrecioServicio.setText(Double.toString(servicioActual.getPrecioServicio()));
        } catch (Exception ex) {
            // Enviar el mensaje al usuario de la pantalla en el caso que suceda un error al obtener los datos de la base de datos
            JOptionPane.showMessageDialog(frmPadre, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }
    
    private void iniciarDatos(Servicio pServicio, int pOpcionForm, FrmServicioLec pFrmPadre) {
        frmPadre = pFrmPadre;
        servicioActual = new Servicio();
        opcionForm = pOpcionForm;
        this.txtNombre.setEditable(true); // colocar txtNombre que se pueda editar
        this.txtDescripcion.setEditable(true);
        this.txtPrecioServicio.setEditable(true);
        switch (pOpcionForm) {
            case FormEscOpcion.CREAR:
                btnOk.setText("Nuevo"); // modificar el texto del boton btnOk a "Nuevo" cuando la pOpcionForm sea CREAR
                this.btnOk.setMnemonic('N'); // modificar la tecla de atajo del boton btnOk a la letra N
                this.setTitle("Crear un nuevo Servicio"); // modificar el titulo de la pantalla de FrmServicioEsc
                break;
            case FormEscOpcion.MODIFICAR:
                btnOk.setText("Modificar"); // modificar el texto del boton btnOk a "Modificar" cuando la pOpcionForm sea MODIFICAR
                this.btnOk.setMnemonic('M'); // modificar la tecla de atajo del boton btnOk a la letra M
                this.setTitle("Modificar el Servicio"); // modificar el titulo de la pantalla de FrmServicioEsc
                llenarControles(pServicio);
                break;
            case FormEscOpcion.ELIMINAR:
                btnOk.setText("Eliminar");// modificar el texto del boton btnOk a "Eliminar" cuando la pOpcionForm sea ELIMINAR
                this.btnOk.setMnemonic('E'); // modificar la tecla de atajo del boton btnOk a la letra E
                this.setTitle("Eliminar el Servicio"); // modificar el titulo de la pantalla de FrmServicioEsc
                this.txtNombre.setEditable(false); // deshabilitar la caja de texto txtNombre
                this.txtDescripcion.setEditable(false);
                this.txtPrecioServicio.setEditable(false);
                llenarControles(pServicio);
                break;
            case FormEscOpcion.VER:
                btnOk.setText("Ver"); // modificar el texto del boton btnOk a "Ver" cuando la pOpcionForm sea VER
                btnOk.setVisible(false); // ocultar el boton btnOk cuando pOpcionForm sea opcion VER
                this.setTitle("Ver el Servicio"); // modificar el titulo de la pantalla de FrmRolEsc
                this.txtNombre.setEditable(false); // deshabilitar la caja de texto txtNombre
                this.txtDescripcion.setEditable(false);
                this.txtPrecioServicio.setEditable(false);
                llenarControles(pServicio);
                break;
            default:
                break;
        }
    }
    
    // validar los datos antes de enviar a la DAL de Servicio
    private boolean validarDatos() {
        boolean result = true; // variable para saber si los datos son validos para su envio a la DAL de Servicio y despues a la base de datos 
        // verificar si la caja de texto txtNombre esta vacia 
        if (this.txtNombre.getText().trim().isEmpty()) {
            result = false; // en el caso que la caja de texto txtNombre este vacia se colocara la variable resul en false
        }
        if (this.txtDescripcion.getText().trim().isEmpty()) {
            result = false; // en el caso que la caja de texto este vacia se colocara la variable resul en false
        }
        if (this.txtPrecioServicio.getText().trim().isEmpty()) {
            result = false; // en el caso que la caja de texto este vacia se colocara la variable resul en false
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
    
    // Metodo para llenar la entidad de servicio con la informacion que esta en la caja de texto del formulario FrmServicioEsc
    private void llenarEntidadConLosDatosDeLosControles() {
        servicioActual.setNombre(this.txtNombre.getText()); // Llenar la propiedad de Nombre de la entidad de Servicio con el valor de la caja de texto txtNombre
        servicioActual.setDescripcion(this.txtDescripcion.getText());
        servicioActual.setPrecioServicio(Double.parseDouble(this.txtPrecioServicio.getText()));
    }
//
    // metodo para cerrar el formulario FrmRolEsc 
    private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario FrmServicioLec
            frmPadre.setVisible(true); // mostrar el formulario FrmServicioLec
        }
        if (pIsEvtClosing == false) { // verificar que no se este cerrando el formulario desde el evento formWindowClosing 
            this.setVisible(false); // Cerrar el formulario FrmServicioEsc
            this.dispose(); // Cerrar todos los procesos abiertos en el formulario FrmServicioEsc
        }
    }
    
    // metodo para enviar los datos a la base de datos segun la opcion de la propiedad opcionForm 
    private void enviarDatos() {
        try {
            if (validarDatos()) { // verificar si todos los datos obligatorios tienen informacion
                // verificar que el usuario de la pantalla presiono el boton YES
                if (obtenerMensajeDeConfirmacion() == JOptionPane.YES_OPTION) {
                    llenarEntidadConLosDatosDeLosControles(); // llenar la entidad de Servicio con los datos de la caja de texto del formulario
                    int resultado = 0;
                    switch (opcionForm) {
                        case FormEscOpcion.CREAR:
                            resultado = ServicioDAL.crear(servicioActual); // si la propiedad opcionForm es CREAR guardar esos datos en la base de datos
                            break;
                        case FormEscOpcion.MODIFICAR:
                            resultado = ServicioDAL.modificar(servicioActual);// si la propiedad opcionForm es MODIFICAR actualizar esos datos en la base de datos
                            break;
                        case FormEscOpcion.ELIMINAR:
                            // si la propiedad opcionForm es ELIMINAR entonces quitamos ese registro de la base de datos
                            resultado = ServicioDAL.eliminar(servicioActual);
                            break;
                        default:
                            break;
                    }
                    if (resultado != 0) {
                        // notificar al usuario que "Los datos fueron correctamente actualizados"
                        JOptionPane.showMessageDialog(this, "Los datos fueron correctamente actualizados");
                        if (frmPadre != null) {
                            // limpiar los datos de la tabla de datos del formulario FrmServicioLec
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
    public FrmServicioEsc(Servicio pServicio, int pOpcionForm, FrmServicioLec pFrmPadre) {
        initComponents();
        iniciarDatos(pServicio, pOpcionForm, pFrmPadre); // Iniciar y obtener los datos de la base de datos para llenar los controles de este formulario
    }
    //</editor-fold>
    
    /**
     * Creates new form FrmServicioEsc
     */
    public FrmServicioEsc() {
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtPrecioServicio = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Servicio");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Descripcion");

        jLabel3.setText("PrecioServicio");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(btnOk)
                        .addGap(61, 61, 61)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecioServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrecioServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancelar))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Eventos de la pantalla FrmServicioEsc
    
    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        this.enviarDatos();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.cerrarFormulario(true);
    }//GEN-LAST:event_formWindowClosing

    //fin de los eventos de la pantalla

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecioServicio;
    // End of variables declaration//GEN-END:variables
}
