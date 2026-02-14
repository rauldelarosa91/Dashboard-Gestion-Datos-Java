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
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private boolean sinGluten;
    private boolean oferta;
    
    public Producto() {
    }
    
    public Producto(String codigo, String nombre, String categoria, double precio, int stock, boolean sinGluten, boolean oferta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.sinGluten = sinGluten;
        this.oferta = oferta;
    }
    
    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public boolean isSinGluten() {
        return sinGluten;
    }
    
    public void setSinGluten(boolean sinGluten) {
        this.sinGluten = sinGluten;
    }
    
    public boolean isOferta() {
        return oferta;
    }
    
    public void setOferta(boolean oferta) {
        this.oferta = oferta;
    }
    
    public String getEstado() {
        return stock > 20 ? "OK" : "BAJO";
    }
    
    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
