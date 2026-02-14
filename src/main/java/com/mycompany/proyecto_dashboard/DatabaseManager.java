/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_dashboard;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
 * @author Hp
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:panaderia.db";
    private Connection connection;
    
    public DatabaseManager() {
        try {
            // Cargar el driver de SQLite
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            crearTablas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void crearTablas() {
        String sqlProductos = "CREATE TABLE IF NOT EXISTS productos (" +
                "codigo TEXT PRIMARY KEY," +
                "nombre TEXT NOT NULL," +
                "categoria TEXT NOT NULL," +
                "precio REAL NOT NULL," +
                "stock INTEGER NOT NULL," +
                "sinGluten INTEGER DEFAULT 0," +
                "oferta INTEGER DEFAULT 0)";
        
        String sqlVentas = "CREATE TABLE IF NOT EXISTS ventas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numeroFactura TEXT NOT NULL," +
                "cliente TEXT NOT NULL," +
                "fecha TEXT NOT NULL," +
                "hora TEXT NOT NULL," +
                "metodoPago TEXT NOT NULL," +
                "total REAL NOT NULL," +
                "requiereFactura INTEGER DEFAULT 0," +
                "servicioDomicilio INTEGER DEFAULT 0)";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlProductos);
            stmt.execute(sqlVentas);
            insertarProductosPrueba();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertarProductosPrueba() {
        try {
            String checkSql = "SELECT COUNT(*) FROM productos";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(checkSql);
            if (rs.next() && rs.getInt(1) == 0) {
                // Solo insertar si no hay productos
                agregarProducto(new Producto("PAN001", "Pan Integral", "Pan", 2.50, 45, false, false));
                agregarProducto(new Producto("PAN002", "Baguette", "Pan", 1.80, 32, false, false));
                agregarProducto(new Producto("BOL001", "Croissant", "Bollería", 1.20, 28, false, false));
                agregarProducto(new Producto("BOL002", "Napolitana", "Bollería", 1.50, 15, false, false));
                agregarProducto(new Producto("PAS001", "Tarta de Manzana", "Pastelería", 12.50, 8, false, false));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // CRUD Productos
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO productos (codigo, nombre, categoria, precio, stock, sinGluten, oferta) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getNombre());
            pstmt.setString(3, producto.getCategoria());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setInt(5, producto.getStock());
            pstmt.setInt(6, producto.isSinGluten() ? 1 : 0);
            pstmt.setInt(7, producto.isOferta() ? 1 : 0);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY categoria, nombre";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto producto = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getInt("sinGluten") == 1,
                    rs.getInt("oferta") == 1
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
    
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, categoria=?, precio=?, stock=?, sinGluten=?, oferta=? WHERE codigo=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.isSinGluten() ? 1 : 0);
            pstmt.setInt(6, producto.isOferta() ? 1 : 0);
            pstmt.setString(7, producto.getCodigo());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarProducto(String codigo) {
        String sql = "DELETE FROM productos WHERE codigo=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Producto buscarProductoPorCodigo(String codigo) {
        String sql = "SELECT * FROM productos WHERE codigo=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getInt("sinGluten") == 1,
                    rs.getInt("oferta") == 1
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // CRUD Ventas
    public boolean registrarVenta(Venta venta) {
        String sql = "INSERT INTO ventas (numeroFactura, cliente, fecha, hora, metodoPago, total, requiereFactura, servicioDomicilio) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, venta.getNumeroFactura());
            pstmt.setString(2, venta.getCliente());
            pstmt.setString(3, venta.getFecha());
            pstmt.setString(4, venta.getHora());
            pstmt.setString(5, venta.getMetodoPago());
            pstmt.setDouble(6, venta.getTotal());
            pstmt.setInt(7, venta.isRequiereFactura() ? 1 : 0);
            pstmt.setInt(8, venta.isServicioDomicilio() ? 1 : 0);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Venta> obtenerVentasDelDia(String fecha) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE fecha=? ORDER BY hora DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, fecha);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Venta venta = new Venta(
                    rs.getString("numeroFactura"),
                    rs.getString("cliente"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("metodoPago"),
                    rs.getDouble("total"),
                    rs.getInt("requiereFactura") == 1,
                    rs.getInt("servicioDomicilio") == 1
                );
                ventas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }
    
    public double obtenerTotalVentasDia(String fecha) {
        String sql = "SELECT SUM(total) as total FROM ventas WHERE fecha=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, fecha);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}