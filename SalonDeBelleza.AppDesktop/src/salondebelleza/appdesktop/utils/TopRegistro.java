package salondebelleza.appdesktop.utils;

public class TopRegistro {

    // Llenar de opciones al Combo Box de Top Registro en la pantallas 
    public static void llenarDatos(javax.swing.JComboBox<ItemsCombo> pJComboBox) {
        pJComboBox.addItem(new ItemsCombo(10, "10"));
        pJComboBox.addItem(new ItemsCombo(20, "20"));
        pJComboBox.addItem(new ItemsCombo(50, "50"));
        pJComboBox.addItem(new ItemsCombo(100, "100"));
        pJComboBox.addItem(new ItemsCombo(500, "500"));
        pJComboBox.addItem(new ItemsCombo(1000, "1000"));
        pJComboBox.addItem(new ItemsCombo(5000, "5000"));
        pJComboBox.addItem(new ItemsCombo(10000, "10000"));
        pJComboBox.addItem(new ItemsCombo(Integer.MAX_VALUE, "TODOS"));
    }

    // Obtener valor seleccionado del Combo Box de Top Registro
    public static int obtenerValorSeleccionado(javax.swing.JComboBox<ItemsCombo> pJComboBox) {
        ItemsCombo itemCombo = (ItemsCombo) pJComboBox.getSelectedItem();
        return itemCombo.getId();
    }

    public static void limpiarTopRegistro(javax.swing.JComboBox<ItemsCombo> pJComboBox) {
        pJComboBox.setSelectedItem(new ItemsCombo(10, null));
    }
}
