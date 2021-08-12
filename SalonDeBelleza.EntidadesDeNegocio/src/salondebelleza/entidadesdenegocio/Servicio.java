package salondebelleza.entidadesdenegocio;

public class Servicio {
    private int id;
    private String nombre;
    private String descripcion;
    private double precioServicio;
    private int top_aux;

    public Servicio() {
    }

    public Servicio(int id, String nombre, String descripcion, double precioServicio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioServicio = precioServicio;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(double precioServicio) {
        this.precioServicio = precioServicio;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
    
    
}
