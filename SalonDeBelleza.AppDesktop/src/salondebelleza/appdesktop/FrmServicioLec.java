package salondebelleza.appdesktop;

// Importaciones para el funcionamiento de la pantalla de mantenimiento de Servicio
import salondebelleza.appdesktop.utils.*; // importar todas las clases de utilidades de la aplicaciones escritorio
import salondebelleza.accesoadatos.*; // importar todas la clases de la capa de acceso a datos
import salondebelleza.entidadesdenegocio.*; // importar todas la clases de la capa de entidades de negocio
import java.util.ArrayList; // importar el ArrayList para recibir la lista de Roles de la base de datos
import javax.swing.JOptionPane; // importa la clase JOptionPane para mostrar alertas o advertencias a los usuarios
import javax.swing.table.DefaultTableModel; // importar la clase DefaultTableModel para llenar los datos de la tabla

public class FrmServicioLec extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Codigo para las clases,propiedades y metodos locales del formulario FrmRolLec">
    private javax.swing.JFrame frmPadre; // Propiedad para almacenar la pantalla de Inicio del sistema

    // Crear la clase anidada ColumnaTabla para saber la posicion de las columnas en la tabla de datos
    public class ColumnaTabla {

        static final int ID = 0; // El campo Id sera  la primera columna en la tabla de datos
        static final int NOMBRE = 1; // El campo Nombre sera  la segunda columna en la tabla de datos
        static final int DESCRIPCION = 2;
        static final int PRECIOSERVICIO = 3;
    }
    
    // Metodo para ocultar columnas de nuestra tabla de datos
    private void ocultarColumnasDeLaTabla(int pColumna) {
        this.tbServicio.getColumnModel().getColumn(pColumna).setMaxWidth(0); // le dejamos en el ancho maximo de la tabla en cero en la columna
        this.tbServicio.getColumnModel().getColumn(pColumna).setMinWidth(0);// le dejamos en el ancho minimo de la tabla en cero  en la columna
        // le dejamos en el ancho maximo de la tabla en cero en el header
        this.tbServicio.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        // le dejamos en el ancho minimo de la tabla en cero  en el header
        this.tbServicio.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }
    
    // Metodo para iniciar los datos de la tabla a partir de una lista de Servicios
    public void iniciarDatosDeLaTabla(ArrayList<Servicio> pServicios) {
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
        model.addColumn("Descripcion");
        model.addColumn("PrecioServicio");
        this.tbServicio.setModel(model); // iniciar el DefaultTableModel en nuestra tabla de datos
        Object row[] = null; // crear un array de un objecto para iniciar los datos de la fila
        for (int i = 0; i < pServicios.size(); i++) { // Recorrer el array de Servicios
            Servicio servicio = pServicios.get(i);  // Obtener un Servicio de array de Servicios por su indice
            model.addRow(row); // Iniciar una fila vacia en la tabla de datos
            model.setValueAt(servicio.getId(), i, ColumnaTabla.ID); // agregar el valor de la columna Id en la fila
            model.setValueAt(servicio.getNombre(), i, ColumnaTabla.NOMBRE); // agregar el valor de la columna Nombre en la fila
            model.setValueAt(servicio.getDescripcion(), i, ColumnaTabla.DESCRIPCION);
            model.setValueAt(servicio.getPrecioServicio(), i, ColumnaTabla.PRECIOSERVICIO);
        }
        ocultarColumnasDeLaTabla(ColumnaTabla.ID); // Ocultar la columna de Id en la tabla 
    }
    
    // Metodo para llenar la clase de Servicio con los datos que tiene la fila seleccionada de la tabla
    private boolean llenarEntidadConLaFilaSeleccionadaDeLaTabla(Servicio pServicio) {
        int filaSelect; // variable para almacenar el indice de la fila seleccionada
        boolean isSelectRow = false; // variable para saber si esta seleccionada una fila o no
        filaSelect = this.tbServicio.getSelectedRow(); // obtener el indice de la fila seleccionada
        if (filaSelect != -1) { // verificar que se ha seleccionado una fila el cual la variable filaSelect debe ser diferente a -1
            isSelectRow = true; // colocar en true la variable isSelectRow porque si esta seleccionada una fila
            int id = (int) this.tbServicio.getValueAt(filaSelect, ColumnaTabla.ID); // obtener el valor de la fila en la columna Id
            String nombre = (String) this.tbServicio.getValueAt(filaSelect, ColumnaTabla.NOMBRE);// obtener el valor de la fila en la columna Nombre
            String descripcion = (String) this.tbServicio.getValueAt(filaSelect, ColumnaTabla.DESCRIPCION);
            double precioservicio = (double) this.tbServicio.getValueAt(filaSelect, ColumnaTabla.PRECIOSERVICIO);
            pServicio.setId(id); // Llenar propiedad de Id de Servicio con el valor obtenido de la fila de la tabla 
            pServicio.setNombre(nombre); // Llenar propiedad de Nombre de Servicio con el valor obtenido de la fila de la tabla 
            pServicio.setDescripcion(descripcion);
            pServicio.setPrecioServicio(precioservicio);
        }
        return isSelectRow; // devolver el valor de isSelectRow 
    }
    
    private void abrirFormularioDeEscritura(int pOpcionForm) {
        Servicio servicio = new Servicio(); // Crear una instancia de la clase de Servicio
        // Verificar si pOpcionForm es Crear abrimos el formulario de FrmServicioEsc
        // en el caso que la pOpcionForm sea diferente a Crear, se va a verificar el metodo de  llenarEntidadConLaFilaSeleccionadaDeLaTabla
        // para llenar la instancia de Servicio y verificar que este seleccionada una fila en la tabla de datos
        if (pOpcionForm == FormEscOpcion.CREAR || this.llenarEntidadConLaFilaSeleccionadaDeLaTabla(servicio)) {
            // Abrir el formulario FrmServicioEsc utilizando el contructor lleno con los parametros de Servicio,OpcionForm y enviando el
            // formulario actual de FrmServicioLec
            FrmServicioEsc frmServicioEsc = new FrmServicioEsc(servicio, pOpcionForm, this);
            frmServicioEsc.setVisible(true); // Mostrar el formulario FrmServicioEsc
            this.setVisible(false); // ocultar el formulario FrmServicioLec
        } else {
            // en el caso que pOpcionForm sea diferente a Crear y el metodo llenarEntidadConLaFilaSeleccionadaDeLaTabla devuelva false
            // se le notificara al usuario del sistema que debe de seleccionar una fila de la tabla 
            JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna fila.");
        }

    }
    
    // metodo que se utilizara para buscar los datos en la base de datos al dar click en el boton de buscar
    private void buscar() {
        try {
            Servicio servicioSearch = new Servicio();
            // llenar la propiedad de Top de la instancia de Servicio con el valor seleccionado en el combobox cbTop
            servicioSearch.setTop_aux(TopRegistro.obtenerValorSeleccionado(cbTop));
            // llenar la propiedad Nombre de la instancia de Servicio con el valor de caja de texto txtNombre
            servicioSearch.setNombre(this.txtNombre.getText());
            servicioSearch.setDescripcion(this.txtDescripcion.getText());
            servicioSearch.setPrecioServicio(Double.parseDouble(this.txtPrecioServicio.getText()));
            ArrayList<Servicio> servicios = ServicioDAL.buscar(servicioSearch); // buscar el Servicio en la base de datps
            iniciarDatosDeLaTabla(servicios); // iniciar la tabla con los datos obtenidos en el metodo de buscar de la DAL de Servicio
        } catch (Exception ex) {
            // mostrar un error al usuario de pantalla en el caso que suceda al momento de obtener los datos
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }
    
    // limpiar los controles que tienen la informacion a enviar a buscar los roles en la base de datos
    private void limpiarControles() {
        this.txtNombre.setText(""); // limpiar la caja de texto txtNombre
        this.txtDescripcion.setText("");
        this.txtPrecioServicio.setText("0");
        TopRegistro.limpiarTopRegistro(cbTop); // iniciar el combo box de cbTop al valor 10
    }
    
    // cerrar el formulario de FrmServicioLect
    private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario de Inicio
        }
        if (pIsEvtClosing == false) {
            this.setVisible(false); // cerrar el formulario de FrmServicioLec
            this.dispose(); // cerrar el formulario de FrmServicioLec
        }
    }
    
    // contructor de la clae FrmServicioLec que pide como parametro un JFrame
    // en nuestro caso pedira como parametro el formulario FrmInicio
    public FrmServicioLec(javax.swing.JFrame pFrmPadre) {
        initComponents();
        frmPadre = pFrmPadre;
        pFrmPadre.setEnabled(true); // deshabilitar el formulario FrmInicio
        TopRegistro.llenarDatos(cbTop); // iniciar los datos del combo box cbTop con los valores a enviar en el top registro 
    }
    
    // </editor-fold>
    
    public FrmServicioLec() {
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

        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPrecioServicio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbTop = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbServicio = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

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

        txtPrecioServicio.setText("0");

        jLabel4.setText("Top");

        cbTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTopActionPerformed(evt);
            }
        });

        tbServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbServicio);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnVer.setText("Ver");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(198, 198, 198)
                                .addComponent(btnBuscar)
                                .addGap(66, 66, 66)
                                .addComponent(btnLimpiar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(btnNuevo)
                                .addGap(51, 51, 51)
                                .addComponent(btnModificar)
                                .addGap(59, 59, 59)
                                .addComponent(btnEliminar)
                                .addGap(48, 48, 48)
                                .addComponent(btnVer)
                                .addGap(44, 44, 44)
                                .addComponent(btnCerrar)))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addGap(45, 45, 45))
            .addGroup(layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrecioServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cbTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnModificar)
                    .addComponent(btnCerrar)
                    .addComponent(btnVer)
                    .addComponent(btnEliminar))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTopActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        this.limpiarControles();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.CREAR);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.MODIFICAR);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.ELIMINAR);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        // TODO add your handling code here:
        this.abrirFormularioDeEscritura(FormEscOpcion.VER);
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.cerrarFormulario(true);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVer;
    private javax.swing.JComboBox<ItemsCombo> cbTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbServicio;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecioServicio;
    // End of variables declaration//GEN-END:variables
}
