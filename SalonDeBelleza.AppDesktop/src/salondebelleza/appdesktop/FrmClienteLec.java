package salondebelleza.appdesktop;

//Importaciones para el funcionamiento de la pantalla de mantenimiento Rol
import salondebelleza.appdesktop.utils.*;
import salondebelleza.accesoadatos.*;
import salondebelleza.entidadesdenegocio.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmClienteLec extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Codigo para las clases,propiedades y metodos locales del formulario FrmRolLec">
    private javax.swing.JFrame frmPadre; // Propiedad para almacenar la pantalla de Inicio del sistema

    // Crear la clase anidada ColumnaTabla para saber la posicion de las columnas en la tabla de datos
    public class ColumnaTabla {

        static final int ID = 0; // El campo Id sera  la primera columna en la tabla de datos
        static final int NOMBRE = 1; // El campo Nombre sera  la segunda columna en la tabla de datos
        static final int APELLIDO = 2;
        static final int DUI = 3;
        static final int NUMERO = 4;
    }

    // Metodo para ocultar columnas de nuestra tabla de datos
    private void ocultarColumnasDeLaTabla(int pColumna) {
        this.tbClientes.getColumnModel().getColumn(pColumna).setMaxWidth(0); // le dejamos en el ancho maximo de la tabla en cero en la columna
        this.tbClientes.getColumnModel().getColumn(pColumna).setMinWidth(0);// le dejamos en el ancho minimo de la tabla en cero  en la columna
        // le dejamos en el ancho maximo de la tabla en cero en el header
        this.tbClientes.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        // le dejamos en el ancho minimo de la tabla en cero  en el header
        this.tbClientes.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    // Metodo para iniciar los datos de la tabla a partir de una lista de Roles
    public void iniciarDatosDeLaTabla(ArrayList<Cliente> pClientes) {
        // Iniciamos el DefaultTableModel utilizaremos para crear las columnas y los datos en nuestra tabla    
        DefaultTableModel model = new DefaultTableModel() {
            // aplicamos override al metodo isCellEditable para deshabilitar la edicion en la filas de la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // retornamos false para deshabilitar todas las fila y no puedan ser editables en la tabla de datos
            }
        };
        model.addColumn("Id"); // agregar la columna Id a la tabla de datos 
        model.addColumn("Nombre"); // agregar la columna Nombre a la tabla de Datos 
        model.addColumn("Apellido"); // agregar la columna Nombre a la tabla de Datos 
        model.addColumn("Dui"); // agregar la columna Nombre a la tabla de Datos 
        model.addColumn("Numero"); // agregar la columna Nombre a la tabla de Datos 
        this.tbClientes.setModel(model); // iniciar el DefaultTableModel en nuestra tabla de datos
        Object row[] = null; // crear un array de un objecto para iniciar los datos de la fila
        for (int i = 0; i < pClientes.size(); i++) { // Recorrer el array de Roles
            Cliente cliente = pClientes.get(i);  // Obtener un Rol de array de Roles por su indice
            model.addRow(row); // Iniciar una fila vacia en la tabla de datos
            model.setValueAt(cliente.getId(), i, ColumnaTabla.ID); // agregar el valor de la columna Id en la fila
            model.setValueAt(cliente.getNombre(), i, ColumnaTabla.NOMBRE); // agregar el valor de la columna Nombre en la fila
            model.setValueAt(cliente.getApellido(), i, ColumnaTabla.APELLIDO); // agregar el valor de la columna Nombre en la fila
            model.setValueAt(cliente.getDui(), i, ColumnaTabla.DUI); // agregar el valor de la columna Nombre en la fila
            model.setValueAt(cliente.getNumero(), i, ColumnaTabla.NUMERO); // agregar el valor de la columna Nombre en la fila
        }
        ocultarColumnasDeLaTabla(ColumnaTabla.ID); // Ocultar la columna de Id en la tabla 
    }

    // Metodo para llenar la clase de Rol con los datos que tiene la fila seleccionada de la tabla
    private boolean llenarEntidadConLaFilaSeleccionadaDeLaTabla(Cliente pCliente) {
        int filaSelect; // variable para almacenar el indice de la fila seleccionada
        boolean isSelectRow = false; // variable para saber si esta seleccionada una fila o no
        filaSelect = this.tbClientes.getSelectedRow(); // obtener el indice de la fila seleccionada
        if (filaSelect != -1) { // verificar que se ha seleccionado una fila el cual la variable filaSelect debe ser diferente a -1
            isSelectRow = true; // colocar en true la variable isSelectRow porque si esta seleccionada una fila
            int id = (int) this.tbClientes.getValueAt(filaSelect, ColumnaTabla.ID); // obtener el valor de la fila en la columna Id
            String nombre = (String) this.tbClientes.getValueAt(filaSelect, ColumnaTabla.NOMBRE);// obtener el valor de la fila en la columna Nombre
            String apellido = (String) this.tbClientes.getValueAt(filaSelect, ColumnaTabla.APELLIDO);// obtener el valor de la fila en la columna Nombre
            String dui = (String) this.tbClientes.getValueAt(filaSelect, ColumnaTabla.DUI);// obtener el valor de la fila en la columna Nombre
            int numero = (int) this.tbClientes.getValueAt(filaSelect, ColumnaTabla.NUMERO); // obtener el valor de la fila en la columna Id
            pCliente.setId(id); // Llenar propiedad de Id de Rol con el valor obtenido de la fila de la tabla 
            pCliente.setNombre(nombre); // Llenar propiedad de Nombre de Rol con el valor obtenido de la fila de la tabla 
            pCliente.setApellido(apellido);
            pCliente.setDui(dui);
            pCliente.setNumero(numero);
        }
        return isSelectRow; // devolver el valor de isSelectRow 
    }

    // El metodo abrirFormularioDeEscritura lo utilizaremos para abrir el formulario de FrmRolEsc
    private void abrirFormularioDeEscritura(int pOpcionForm) {
        Cliente cliente = new Cliente(); // Crear una instancia de la clase de Rol
        // Verificar si pOpcionForm es Crear abrimos el formulario de FrmRolEsc
        // en el caso que la pOpcionForm sea diferente a Crear, se va a verificar el metodo de  llenarEntidadConLaFilaSeleccionadaDeLaTabla
        // para llenar la instancia de Rol y verificar que este seleccionada una fila en la tabla de datos
        if (pOpcionForm == FormEscOpcion.CREAR || this.llenarEntidadConLaFilaSeleccionadaDeLaTabla(cliente)) {
            // Abrir el formulario FrmRolEsc utilizando el contructor lleno con los parametros de Rol,OpcionForm y enviando el
            // formulario actual de FrmRolLec
            FrmClienteEsc frmClienteEsc = new FrmClienteEsc(cliente, pOpcionForm, this);
            frmClienteEsc.setVisible(true); // Mostrar el formulario FrmRolEsc
            this.setVisible(false); // ocultar el formulario FrmRolLec
        } else {
            // en el caso que pOpcionForm sea diferente a Crear y el metodo llenarEntidadConLaFilaSeleccionadaDeLaTabla devuelva false
            // se le notificara al usuario del sistema que debe de seleccionar una fila de la tabla 
            JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna fila.");
        }

    }

    // metodo que se utilizara para buscar los datos en la base de datos al dar click en el boton de buscar
    private void buscar() {
        try {
            Cliente clienteSearch = new Cliente();
            // llenar la propiedad de Top de la instancia de Rol con el valor seleccionado en el combobox cbTop
            clienteSearch.setTop_aux(TopRegistro.obtenerValorSeleccionado(cbTop));
            // llenar la propiedad Nombre de la instancia de Rol con el valor de caja de texto txtNombre
            clienteSearch.setNombre(this.txtNombre.getText());
            clienteSearch.setApellido(this.txtApellido.getText());
            clienteSearch.setDui(this.txtDui.getText());
            clienteSearch.setNumero(Integer.parseInt(this.txtNumero.getText()));
            ArrayList<Cliente> clientes = ClienteDAL.buscar(clienteSearch); // buscar el rol en la base de datps
            iniciarDatosDeLaTabla(clientes); // iniciar la tabla con los datos obtenidos en el metodo de buscar de la DAL de Rol
        } catch (Exception ex) {
            // mostrar un error al usuario de pantalla en el caso que suceda al momento de obtener los datos
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    // limpiar los controles que tienen la informacion a enviar a buscar los roles en la base de datos
    private void limpiarControles() {
        this.txtNombre.setText(""); // limpiar la caja de texto txtNombre
        this.txtApellido.setText("");
        this.txtDui.setText("");
        this.txtNumero.setText("0");
        TopRegistro.limpiarTopRegistro(cbTop); // iniciar el combo box de cbTop al valor 10
    }

    // cerrar el formulario de FrmRolLect
    private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario de Inicio
        }
        if (pIsEvtClosing == false) {
            this.setVisible(false); // cerrar el formulario de FrmRolLec
            this.dispose(); // cerrar el formulario de FrmRolLec
        }
    }

    // contructor de la clae FrmRolLec que pide como parametro un JFrame
    // en nuestro caso pedira como parametro el formulario FrmInicio
    public FrmClienteLec(javax.swing.JFrame pFrmPadre) {
        initComponents();
        frmPadre = pFrmPadre;
        pFrmPadre.setEnabled(true); // deshabilitar el formulario FrmInicio
        TopRegistro.llenarDatos(cbTop); // iniciar los datos del combo box cbTop con los valores a enviar en el top registro 
    }
// </editor-fold> 

    /**
     * Creates new form FrmClienteLec
     */
    public FrmClienteLec() {
        initComponents();
        TopRegistro.llenarDatos(cbTop);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bntVer = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbClientes = new javax.swing.JTable();
        txtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        txtDui = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbTop = new javax.swing.JComboBox<>();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar Cliente");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        bntVer.setText("Ver");
        bntVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntVerActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel3.setText("Apellido");

        tbClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbClientes);

        jLabel4.setText("Dui");

        txtNumero.setText("0");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        jLabel5.setText("Telefono");

        jLabel1.setText("Nombre");

        jLabel2.setText("Top");

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel4)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                    .addComponent(txtNombre)
                                    .addComponent(txtApellido)
                                    .addComponent(txtDui, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(cbTop, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bntVer, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(cbTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntVer, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.CREAR);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        this.limpiarControles();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.MODIFICAR);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.ELIMINAR);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void bntVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntVerActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.VER);
    }//GEN-LAST:event_bntVerActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.cerrarFormulario(true);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntVer;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<ItemsCombo> cbTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbClientes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDui;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables
}
