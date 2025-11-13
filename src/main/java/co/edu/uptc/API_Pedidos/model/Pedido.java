package co.edu.uptc.API_Pedidos.model;

public class Pedido {
    private String nombre;
    private String tamaño;
    private String estadoPedido;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTamaño() {
        return tamaño;
    }
    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }
    public String getEstadoPedido() {
        return estadoPedido;
    }
    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }
}