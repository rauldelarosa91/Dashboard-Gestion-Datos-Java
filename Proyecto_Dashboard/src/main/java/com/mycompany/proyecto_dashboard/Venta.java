/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_dashboard;
import java.io.Serializable;
/**
 *
 * @author Hp
 */
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numeroFactura;
    private String cliente;
    private String fecha;
    private String hora;
    private String metodoPago;
    private double total;
    private boolean requiereFactura;
    private boolean servicioDomicilio;
    
    public Venta() {
    }
    
    public Venta(String numeroFactura, String cliente, String fecha, String hora, 
                 String metodoPago, double total, boolean requiereFactura, boolean servicioDomicilio) {
        this.numeroFactura = numeroFactura;
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
        this.metodoPago = metodoPago;
        this.total = total;
        this.requiereFactura = requiereFactura;
        this.servicioDomicilio = servicioDomicilio;
    }
    
    // Getters y Setters
    public String getNumeroFactura() {
        return numeroFactura;
    }
    
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    
    public String getCliente() {
        return cliente;
    }
    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getHora() {
        return hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public boolean isRequiereFactura() {
        return requiereFactura;
    }
    
    public void setRequiereFactura(boolean requiereFactura) {
        this.requiereFactura = requiereFactura;
    }
    
    public boolean isServicioDomicilio() {
        return servicioDomicilio;
    }
    
    public void setServicioDomicilio(boolean servicioDomicilio) {
        this.servicioDomicilio = servicioDomicilio;
    }
    
    @Override
    public String toString() {
        return "Venta{" +
                "numeroFactura='" + numeroFactura + '\'' +
                ", cliente='" + cliente + '\'' +
                ", total=" + total +
                '}';
    }
}
