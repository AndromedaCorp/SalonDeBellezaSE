package salondebelleza.appdesktop.utils;

// Esta clase se utilizara llenar los datos en los combos 
public class ItemsCombo {

    private int id;
    private String label;
    // getter y setter

    public ItemsCombo() {
    }

    public ItemsCombo(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public String toString() {
        return label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemsCombo other = (ItemsCombo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
