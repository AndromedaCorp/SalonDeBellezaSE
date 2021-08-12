/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salondebelleza.appdesktop;

// Importaciones para el funcionamiento de la pantalla de mantenimiento de Usuario
import salondebelleza.appdesktop.utils.*; // importar todas las clases de utilidades de la aplicaciones escritorio
import salondebelleza.accesoadatos.*; // importar todas la clases de la capa de acceso a datos
import salondebelleza.entidadesdenegocio.*; // importar todas la clases de la capa de entidades de negocio
import java.util.ArrayList; // importar el ArrayList para recibir la lista de Usuarios de la base de datos
import javax.swing.JOptionPane; // importa la clase JOptionPane para mostrar alertas o advertencias a los usuarios
import javax.swing.table.DefaultTableModel; // importar la clase DefaultTableModel para llenar los datos de la tabla


public class FrmUsuarioLec extends javax.swing.JFrame {

 // <editor-fold defaultstate="collapsed" desc="Codigo para las clases,propiedades y metodos locales del formulario FrmUsuarioLec">
    private javax.swing.JFrame frmPadre; // Propiedad para almacenar la pantalla de Inicio del sistema

    // Crear la clase anidada ColumnaTabla para saber la posicion de las columnas en la tabla de datos
    public class ColumnaTabla {

        static final int ID = 0;
        static final int IDROL = 1;
        static final int DUI = 2;
        static final int ESTADO = 3;
        static final int LOGIN = 4;
        static final int NOMBRE = 5;
        static final int APELLIDO = 6;
        static final int NUMERO = 7;
        static final int ROL = 8;
        static final int ESTATUSSTR = 9;
        static final int FECHAREGISTRO = 10;
    }

    // Metodo para ocultar columnas de nuestra tabla de datos
    private void ocultarColumnasDeLaTabla(int pColumna) {
        this.tbUsuarios.getColumnModel().getColumn(pColumna).setMaxWidth(0); // le dejamos en el ancho maximo de la tabla en cero en la columna
        this.tbUsuarios.getColumnModel().getColumn(pColumna).setMinWidth(0);// le dejamos en el ancho minimo de la tabla en cero  en la columna
        // le dejamos en el ancho maximo de la tabla en cero en el header
        this.tbUsuarios.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        // le dejamos en el ancho minimo de la tabla en cero  en el header
        this.tbUsuarios.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    // Metodo para iniciar los datos de la tabla a partir de una lista de Usuarios
    public void iniciarDatosDeLaTabla(ArrayList<Usuario> pUsuarios) {
        // Iniciamos el DefaultTableModel utilizaremos para crear las columnas y los datos en nuestra tabla    
        DefaultTableModel model = new DefaultTableModel() {
            // aplicamos override al metodo isCellEditable para deshabilitar la edicion en la filas de la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // retornamos false para deshabilitar todas las fila y no puedan ser editables en la tabla de datos
            }
        };
        model.addColumn("Id"); // agregar la columna Id a la tabla de datos
        model.addColumn("IdRol"); // agregar la columna IdRol a la tabla de datos
         model.addColumn("Dui");
        model.addColumn("EstatusEnt"); // agregar la columna EstatusEnt a la tabla de datos
        model.addColumn("Login"); // agregar la columna Login a la tabla de datos
        model.addColumn("Nombre"); // agregar la columna Nombre a la tabla de datos
        model.addColumn("Apellido"); // agregar la columna Apellido a la tabla de datos
           model.addColumn("Numero");
        model.addColumn("Rol"); // agregar la columna Rol a la tabla de datos
        model.addColumn("Estado"); // agregar la columna Estatus a la tabla de datos
        model.addColumn("Fecha Registro"); // agregar la columna Fecha Registro a la tabla de datos
        this.tbUsuarios.setModel(model);
        Object row[] = null;
        for (int i = 0; i < pUsuarios.size(); i++) {
            Usuario usuario = pUsuarios.get(i);
            model.addRow(row);
            model.setValueAt(usuario.getId(), i, ColumnaTabla.ID); // agregar el valor de la columna Id en la fila
            model.setValueAt(usuario.getEstado(), i, ColumnaTabla.ESTADO);
            model.setValueAt(usuario.getIdrol(), i, ColumnaTabla.IDROL);
             model.setValueAt(usuario.getDui(), i, ColumnaTabla.DUI);
            model.setValueAt(usuario.getLogin(), i, ColumnaTabla.LOGIN);
            model.setValueAt(usuario.getNombre(), i, ColumnaTabla.NOMBRE);
            model.setValueAt(usuario.getApellido(), i, ColumnaTabla.APELLIDO);
             model.setValueAt(usuario.getNumero(), i, ColumnaTabla.NUMERO);
            model.setValueAt(usuario.getRol().getNombre(), i, ColumnaTabla.ROL);
            if (usuario.getEstado()== Usuario.EstadoUsuario.ACTIVO) {
                model.setValueAt("ACTIVO", i, ColumnaTabla.ESTATUSSTR);
            } else if (usuario.getEstado()== Usuario.EstadoUsuario.INACTIVO) {
                model.setValueAt("INACTIVO", i, ColumnaTabla.ESTATUSSTR);
            }
            model.setValueAt(usuario.getFechaRegistro().toString(), i, ColumnaTabla.FECHAREGISTRO);
        }
        ocultarColumnasDeLaTabla(ColumnaTabla.ID); // Ocultar la columna de Id en la tabla 
        ocultarColumnasDeLaTabla(ColumnaTabla.ESTADO);
        ocultarColumnasDeLaTabla(ColumnaTabla.IDROL);
    }

    // Metodo para llenar la clase de Rol con los datos que tiene la fila seleccionada de la tabla
    private boolean llenarEntidadConLaFilaSeleccionadaDeLaTabla(Usuario pUsuario) {
        int filaSelect; // variable para almacenar el indice de la fila seleccionada
        boolean isSelectRow = false; // variable para saber si esta seleccionada una fila o no
        filaSelect = this.tbUsuarios.getSelectedRow(); // obtener el indice de la fila seleccionada
        if (filaSelect != -1) { // verificar que se ha seleccionado una fila el cual la variable filaSelect debe ser diferente a -1
            isSelectRow = true; // colocar en true la variable isSelectRow porque si esta seleccionada una fila
            pUsuario.setId((int) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.ID)); // agregar el valor de la columna Id a la propiedad Id
            pUsuario.setIdrol((int) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.IDROL));
            pUsuario.setDui((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.DUI));
            pUsuario.setNombre((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.NOMBRE));
            pUsuario.setApellido((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.APELLIDO));
            pUsuario.setNumero((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.NUMERO));
            pUsuario.setEstado((byte) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.ESTADO));
            pUsuario.setLogin((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.LOGIN));
            pUsuario.setRol(new Rol());
            pUsuario.getRol().setNombre((String) this.tbUsuarios.getValueAt(filaSelect, ColumnaTabla.ROL));
        }
        return isSelectRow; // devolver el valor de isSelectRow 
    }

    // El metodo abrirFormularioDeEscritura lo utilizaremos para abrir el formulario de FrmRolEsc
    private void abrirFormularioDeEscritura(int pOpcionForm) {
        Usuario usuario = new Usuario(); // Crear una instancia de la clase de Rol
        // Verificar si pOpcionForm es Crear abrimos el formulario de FrmUsuarioEsc
        // en el caso que la pOpcionForm sea diferente a Crear, se va a verificar el metodo de  llenarEntidadConLaFilaSeleccionadaDeLaTabla
        // para llenar la instancia de Usuario y verificar que este seleccionada una fila en la tabla de datos
        if (pOpcionForm == FormEscOpcion.CREAR || this.llenarEntidadConLaFilaSeleccionadaDeLaTabla(usuario)) {
            // Abrir el formulario FrmUsuarioEsc utilizando el contructor lleno con los parametros de Usuario,OpcionForm y enviando el
            // formulario actual de FrmUsuarioEsc
            FrmUsuarioEsc frmUsuarioEsc = new FrmUsuarioEsc(usuario, pOpcionForm, this);
            frmUsuarioEsc.setVisible(true); // Mostrar el formulario FrmUsuarioEsc
            this.setVisible(false); // ocultar el formulario FrmUsuarioLec
        } else {
            // en el caso que pOpcionForm sea diferente a Crear y el metodo llenarEntidadConLaFilaSeleccionadaDeLaTabla devuelva false
            // se le notificara al usuario del sistema que debe de seleccionar una fila de la tabla 
            JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna fila.");
        }

    }

    // metodo que se utilizara para buscar los datos en la base de datos al dar click en el boton de buscar
    private void buscar() {
        try {
            Usuario usuarioSearch = new Usuario();
            usuarioSearch.setTop_aux(TopRegistro.obtenerValorSeleccionado(cbTop));
             usuarioSearch.setDui(this.txtDUI.getText());
            usuarioSearch.setLogin(this.txtLogin.getText());
            usuarioSearch.setNombre(this.txtNombre.getText());
            usuarioSearch.setApellido(this.txtApellido.getText());
            usuarioSearch.setNumero(this.txtNumeroTelefono.getText());
            ItemsCombo itemsCbEstado = (ItemsCombo) cbEstado.getSelectedItem();
            usuarioSearch.setEstado((byte) itemsCbEstado.getId());
            ItemsCombo itemsCbRoles = (ItemsCombo) cbRol.getSelectedItem();
            usuarioSearch.setIdrol(itemsCbRoles.getId());
            ArrayList<Usuario> usuarios = UsuarioDAL.buscarIncluirRol(usuarioSearch); // buscar el usuario  en la base de datos
            iniciarDatosDeLaTabla(usuarios); // iniciar la tabla con los datos obtenidos en el metodo de buscar de la DAL de Usuario
        } catch (Exception ex) {
            // mostrar un error al usuario de pantalla en el caso que suceda al momento de obtener los datos
            JOptionPane.showMessageDialog(this, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    // limpiar los controles que tienen la informacion a enviar a buscar los Usuarios en la base de datos
    private void limpiarControles() {
           this.txtNumeroTelefono.setText(""); // limpiar la caja de texto 
           this.txtDUI.setText(""); // limpiar la caja de texto 
        this.txtNombre.setText(""); // limpiar la caja de texto 
        TopRegistro.limpiarTopRegistro(cbTop); // iniciar el combo box de cbTop al valor 10
        this.txtApellido.setText(""); // limpiar la caja de texto 
        this.txtLogin.setText("");  // limpiar la caja de texto  
        this.cbRol.setSelectedItem(new ItemsCombo(0, null));  // iniciar  el combo box Rol al valor 0  "SELECCIONAR"
        this.cbEstado.setSelectedItem(new ItemsCombo(0, null)); // iniciar  el combo box Estatus al valor 0  "SELECCIONAR"
    }

    // cerrar el formulario de FrmUsuarioLec
    private void cerrarFormulario(boolean pIsEvtClosing) {
        if (frmPadre != null) {
            frmPadre.setEnabled(true); // habilitar el formulario de Inicio
        }
        if (pIsEvtClosing == false) {
            this.setVisible(false); // cerrar el formulario de FrmUsuarioLec
            this.dispose(); // cerrar el formulario de FrmUsuarioLec
        }
    }

    // Metodo para agregar los valor al combo box de Estatus
    public void iniciarComboEstatus(javax.swing.JComboBox<ItemsCombo> pJComboBox) {
        pJComboBox.addItem(new ItemsCombo(0, "SELECCIONAR"));
        pJComboBox.addItem(new ItemsCombo(Usuario.EstadoUsuario.ACTIVO, "ACTIVO"));
        pJComboBox.addItem(new ItemsCombo(Usuario.EstadoUsuario.INACTIVO, "INACTIVO"));
    }
    // Metodo para agregar los valor al combo box de Rol obteniendo los datos desde la base de datos

    public void iniciarComboRol(javax.swing.JComboBox<ItemsCombo> pJComboBox, javax.swing.JFrame pFrame) {
        pJComboBox.addItem(new ItemsCombo(0, "SELECCIONAR"));
        try {
            ArrayList<Rol> roles = RolDAL.obtenerTodos();
            for (int i = 0; i < roles.size(); i++) {
                Rol rol = roles.get(i);
                pJComboBox.addItem(new ItemsCombo(rol.getId(), rol.getNombre()));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pFrame, "Sucedio el siguiente error: " + ex.getMessage());
        }
    }

    private void iniciarDatos(javax.swing.JFrame pFrmPadre) {
        frmPadre = pFrmPadre;
        pFrmPadre.setEnabled(true); // deshabilitar el formulario FrmInicio
        TopRegistro.llenarDatos(cbTop); // iniciar los datos del combo box cbTop con los valores a enviar en el top registro 
        iniciarComboEstatus(this.cbEstado); // iniciar del combo box estatus
        iniciarComboRol(this.cbRol, this.frmPadre);  // iniciar del combo box Rol
    }

    // contructor de la clae FrmRolLec que pide como parametro un JFrame
    // en nuestro caso pedira como parametro el formulario FrmInicio
    public FrmUsuarioLec(javax.swing.JFrame pFrmPadre) {
        initComponents();
        iniciarDatos(pFrmPadre); // llamar el metodo IniciarDatos para cargar los combo box y llenar propiedades iniciales del formulario
    }
// </editor-fold> 

    /**
     * Creates new form FrmUsuarioLec
     */
    public FrmUsuarioLec() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        cbRol = new javax.swing.JComboBox<>();
        cbTop = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsuarios = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        cbEstado = new javax.swing.JComboBox<>();
        txtNumeroTelefono = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDUI = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar Usuario");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Numero de telefono");

        jLabel3.setText("Login");

        jLabel4.setText("Apellido");

        jLabel5.setText("DUI");

        jLabel6.setText("Rol");

        jLabel7.setText("Estado");

        jLabel8.setText("Top");

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

        tbUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbUsuarios);

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

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(btnNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVer)
                        .addGap(28, 28, 28)
                        .addComponent(btnCerrar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(196, 196, 196)
                                .addComponent(btnBuscar))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(132, 132, 132)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                            .addComponent(txtNumeroTelefono)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(70, 70, 70)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel7)))))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel8))
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cbTop, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(cbRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(txtDUI))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtApellido)))
                                .addGap(7, 7, 7))
                            .addComponent(btnLimpiar))))
                .addGap(0, 119, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNumeroTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel3)))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(41, 41, 41)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDUI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(cbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnVer)
                    .addComponent(btnCerrar))
                .addGap(120, 120, 120))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
           // TODO add your handling code here:
        this.buscar(); // llamar el metodo de buscar
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
      this.limpiarControles();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
      this.abrirFormularioDeEscritura(FormEscOpcion.CREAR);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
         this.abrirFormularioDeEscritura(FormEscOpcion.MODIFICAR);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.abrirFormularioDeEscritura(FormEscOpcion.ELIMINAR);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
       this.abrirFormularioDeEscritura(FormEscOpcion.VER);
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
     this.cerrarFormulario(false);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
           this.cerrarFormulario(true);
    }//GEN-LAST:event_formWindowClosing



    // fin de los eventos de la pantalla de usuario
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVer;
    private javax.swing.JComboBox<ItemsCombo> cbEstado;
    private javax.swing.JComboBox<ItemsCombo> cbRol;
    private javax.swing.JComboBox<ItemsCombo> cbTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbUsuarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDUI;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumeroTelefono;
    // End of variables declaration//GEN-END:variables
}
