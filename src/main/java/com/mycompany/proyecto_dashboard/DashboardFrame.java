/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_dashboard;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author Hp
 */
public class DashboardFrame extends JFrame {
    private static final Color HEADER_COLOR = new Color(205, 153, 102);
    private static final Color BUTTON_GREEN = new Color(120, 180, 120);
    private static final Color STATUS_OK = new Color(76, 175, 80);
    private static final Color STATUS_LOW = new Color(255, 152, 0);
    
    private Usuario usuarioActual;
    private DatabaseManager dbManager;
    
    // Campos de productos
    private JTextField codigoField, precioField, nombreField;
    private JRadioButton panRadio, bolleriaRadio, pasteleriaRadio;
    private JCheckBox sinGlutenCheck, ofertaCheck;
    
    // Campos de ventas
    private JTextField clienteField, totalField, facturaField;
    private JComboBox<String> metodoPagoCombo;
    private JCheckBox requiereFacturaCheck, servicioDomicilioCheck;
    
    // Tablas
    private JTable productosTable, ventasDelDiaTable;
    private DefaultTableModel productosModel, ventasModel;
    
    // Gráficos
    private ChartPanel categoriasChart, stockChart;
    
    // Reportes
    private JTextField desdeField, hastaField;
    private JRadioButton ventasRadio, inventarioRadio, masVendidosRadio;
    private JCheckBox graficosCheck, excelCheck, pdfCheck;
    
    // Labels dinámicos
    private JLabel totalDiaLabel;
    
    // Timer para actualización automática
    private Timer autoUpdateTimer;
    
    public DashboardFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        this.dbManager = new DatabaseManager();
        initComponents();
        cargarDatos();
        iniciarActualizacionAutomatica();
    }
    
    private void initComponents() {
        setTitle("Sistema de Punto de Venta - " + usuarioActual.getNombre());
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        createMenuBar();
        createToolBar();
        
        // Panel principal con 3 columnas
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(createLeftPanel());
        mainPanel.add(createCenterPanel());
        mainPanel.add(createRightPanel());
        
        add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem cerrarSesionItem = new JMenuItem("Cerrar Sesión");
        cerrarSesionItem.addActionListener(e -> cerrarSesion());
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));
        archivoMenu.add(cerrarSesionItem);
        archivoMenu.addSeparator();
        archivoMenu.add(salirItem);
        menuBar.add(archivoMenu);
        
        // Menú Productos
        JMenu productosMenu = new JMenu("Productos");
        JMenuItem nuevoProductoItem = new JMenuItem("Nuevo Producto");
        nuevoProductoItem.addActionListener(e -> limpiarFormularioProducto());
        JMenuItem listarProductosItem = new JMenuItem("Listar Productos");
        listarProductosItem.addActionListener(e -> cargarProductos());
        productosMenu.add(nuevoProductoItem);
        productosMenu.add(listarProductosItem);
        menuBar.add(productosMenu);
        
        // Menú Ventas
        JMenu ventasMenu = new JMenu("Ventas");
        JMenuItem nuevaVentaItem = new JMenuItem("Nueva Venta");
        nuevaVentaItem.addActionListener(e -> generarNumeroFactura());
        JMenuItem listarVentasItem = new JMenuItem("Listar Ventas");
        listarVentasItem.addActionListener(e -> cargarVentas());
        ventasMenu.add(nuevaVentaItem);
        ventasMenu.add(listarVentasItem);
        menuBar.add(ventasMenu);
        
        // Menú Inventario
        JMenu inventarioMenu = new JMenu("Inventario");
        JMenuItem actualizarStockItem = new JMenuItem("Actualizar Stock");
        actualizarStockItem.addActionListener(e -> mostrarDialogoStock());
        inventarioMenu.add(actualizarStockItem);
        menuBar.add(inventarioMenu);
        
        // Menú Reportes
        JMenu reportesMenu = new JMenu("Reportes");
        JMenuItem reporteVentasItem = new JMenuItem("Reporte de Ventas");
        reporteVentasItem.addActionListener(e -> ventasRadio.setSelected(true));
        JMenuItem reporteInventarioItem = new JMenuItem("Reporte de Inventario");
        reporteInventarioItem.addActionListener(e -> inventarioRadio.setSelected(true));
        reportesMenu.add(reporteVentasItem);
        reportesMenu.add(reporteInventarioItem);
        menuBar.add(reportesMenu);
        
        // Menú Configuración
        JMenu configMenu = new JMenu("Configuración");
        JMenuItem preferenciasItem = new JMenuItem("Preferencias");
        configMenu.add(preferenciasItem);
        menuBar.add(configMenu);
        
        // Menú Ayuda
        JMenu ayudaMenu = new JMenu("Ayuda");
        JMenuItem acercaDeItem = new JMenuItem("Acerca de");
        acercaDeItem.addActionListener(e -> mostrarAcercaDe());
        ayudaMenu.add(acercaDeItem);
        menuBar.add(ayudaMenu);
        
        // Panel de usuario
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel(" " + usuarioActual.getNombre());
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userPanel.add(userLabel);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(userPanel);
        
        setJMenuBar(menuBar);
    }
    
    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(Color.LIGHT_GRAY);
        
        // Botón Nuevo
        JButton nuevoBtn = new JButton("Nuevo");
        nuevoBtn.setFocusable(false);
        nuevoBtn.addActionListener(e -> limpiarFormularioProducto());
        toolBar.add(nuevoBtn);
        toolBar.addSeparator();
        
        // Botón Guardar
        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.setFocusable(false);
        guardarBtn.addActionListener(e -> agregarProducto());
        toolBar.add(guardarBtn);
        toolBar.addSeparator();
        
        // Botón Eliminar
        JButton eliminarBtn = new JButton("Eliminar");
        eliminarBtn.setFocusable(false);
        eliminarBtn.addActionListener(e -> eliminarProductoSeleccionado());
        toolBar.add(eliminarBtn);
        toolBar.addSeparator();
        
        // Botón Imprimir
        JButton imprimirBtn = new JButton("Imprimir");
        imprimirBtn.setFocusable(false);
        imprimirBtn.addActionListener(e -> imprimirReporte());
        toolBar.add(imprimirBtn);
        toolBar.addSeparator();
        
        // Botón Exportar
        JButton exportarBtn = new JButton("Exportar");
        exportarBtn.setFocusable(false);
        exportarBtn.addActionListener(e -> exportarDatos());
        toolBar.add(exportarBtn);
        toolBar.addSeparator();
        
        // Botón Actualizar
        JButton actualizarBtn = new JButton("Actualizar");
        actualizarBtn.setFocusable(false);
        actualizarBtn.addActionListener(e -> cargarDatos());
        toolBar.add(actualizarBtn);
        toolBar.addSeparator();
        
        // Botón Buscar
        JButton buscarBtn = new JButton("Buscar");
        buscarBtn.setFocusable(false);
        buscarBtn.addActionListener(e -> buscarProducto());
        toolBar.add(buscarBtn);
        toolBar.addSeparator();
        
        // Botón Ventas
        JButton ventasBtn = new JButton("Ventas");
        ventasBtn.setFocusable(false);
        ventasBtn.addActionListener(e -> generarNumeroFactura());
        toolBar.add(ventasBtn);
        toolBar.addSeparator();
        
        // Botón Productos
        JButton productosBtn = new JButton("Productos");
        productosBtn.setFocusable(false);
        productosBtn.addActionListener(e -> cargarProductos());
        toolBar.add(productosBtn);
        
        add(toolBar, BorderLayout.NORTH);
    }
    
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        leftPanel.add(createProductDataPanel());
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createCategoryChartPanel());
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(createStockChartPanel());
        
        return leftPanel;
    }
    
    private JPanel createProductDataPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_COLOR, 2),
            "Datos del Producto",
            0, 0, new Font("Arial", Font.BOLD, 14),
            HEADER_COLOR
        ));
        
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        form.add(new JLabel("Código:"));
        codigoField = new JTextField();
        form.add(codigoField);
        
        form.add(new JLabel("Precio €:"));
        precioField = new JTextField("0.00");
        form.add(precioField);
        
        form.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        form.add(nombreField);
        
        panel.add(form);
        
        // Tipo de producto
        JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoPanel.add(new JLabel("Tipo:"));
        ButtonGroup tipoGroup = new ButtonGroup();
        panRadio = new JRadioButton("Pan", true);
        bolleriaRadio = new JRadioButton("Bollería");
        pasteleriaRadio = new JRadioButton("Pastelería");
        tipoGroup.add(panRadio);
        tipoGroup.add(bolleriaRadio);
        tipoGroup.add(pasteleriaRadio);
        tipoPanel.add(panRadio);
        tipoPanel.add(bolleriaRadio);
        tipoPanel.add(pasteleriaRadio);
        panel.add(tipoPanel);
        
        // Opciones
        JPanel opcionesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sinGlutenCheck = new JCheckBox("SinGluten");
        ofertaCheck = new JCheckBox("Oferta");
        opcionesPanel.add(sinGlutenCheck);
        opcionesPanel.add(ofertaCheck);
        panel.add(opcionesPanel);
        
        // Botones
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton agregarBtn = new JButton("Agregar");
        agregarBtn.setBackground(BUTTON_GREEN);
        agregarBtn.setForeground(Color.BLACK);
        agregarBtn.addActionListener(e -> agregarProducto());
        JButton limpiarBtn = new JButton("Limpiar");
        limpiarBtn.setBackground(BUTTON_GREEN);
        limpiarBtn.addActionListener(e -> limpiarFormularioProducto());
        buttonsPanel.add(agregarBtn);
        buttonsPanel.add(limpiarBtn);
        panel.add(buttonsPanel);
        
        return panel;
    }
    
    private JPanel createCategoryChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_COLOR, 2),
            "Ventas por Categoría",
            0, 0, new Font("Arial", Font.BOLD, 14),
            HEADER_COLOR
        ));
        
        categoriasChart = new ChartPanel(crearGraficoVentasPorCategoria());
        categoriasChart.setPreferredSize(new Dimension(300, 200));
        panel.add(categoriasChart, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JFreeChart crearGraficoVentasPorCategoria() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        List<Producto> productos = dbManager.obtenerProductos();
        int pan = 0, bolleria = 0, pasteleria = 0;
        
        for (Producto p : productos) {
            if (p.getCategoria().equals("Pan")) pan += p.getStock();
            else if (p.getCategoria().equals("Bollería")) bolleria += p.getStock();
            else if (p.getCategoria().equals("Pastelería")) pasteleria += p.getStock();
        }
        
        dataset.addValue(pan, "Stock", "Pan");
        dataset.addValue(bolleria, "Stock", "Bollería");
        dataset.addValue(pasteleria, "Stock", "Pastelería");
        
        JFreeChart chart = ChartFactory.createBarChart(
            null, null, null, dataset,
            PlotOrientation.VERTICAL, false, true, false
        );
        
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRenderer(new org.jfree.chart.renderer.category.BarRenderer() {{
            setSeriesPaint(0, HEADER_COLOR);
        }});
        
        return chart;
    }
    
    private JPanel createStockChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_COLOR, 2),
            "Distribución de Stock",
            0, 0, new Font("Arial", Font.BOLD, 14),
            HEADER_COLOR
        ));
        
        stockChart = new ChartPanel(crearGraficoDistribucionStock());
        stockChart.setPreferredSize(new Dimension(300, 250));
        panel.add(stockChart, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JFreeChart crearGraficoDistribucionStock() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        List<Producto> productos = dbManager.obtenerProductos();
        int totalStock = 0;
        int stockPan = 0, stockBolleria = 0, stockPasteleria = 0;
        
        for (Producto p : productos) {
            totalStock += p.getStock();
            if (p.getCategoria().equals("Pan")) stockPan += p.getStock();
            else if (p.getCategoria().equals("Bollería")) stockBolleria += p.getStock();
            else if (p.getCategoria().equals("Pastelería")) stockPasteleria += p.getStock();
        }
        
        if (totalStock > 0) {
            dataset.setValue("Pan " + (stockPan * 100 / totalStock) + "%", stockPan);
            dataset.setValue("Bollería " + (stockBolleria * 100 / totalStock) + "%", stockBolleria);
            dataset.setValue("Pastelería " + (stockPasteleria * 100 / totalStock) + "%", stockPasteleria);
        }
        
        JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        
        return chart;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        centerPanel.add(createProductListPanel());
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(createSalesPanel());
        
        return centerPanel;
    }
    
    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_COLOR, 2),
            "Listado de Productos",
            0, 0, new Font("Arial", Font.BOLD, 14),
            HEADER_COLOR
        ));
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField("Buscar producto...");
        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"Todas", "Pan", "Bollería", "Pastelería"});
        JButton searchBtn = new JButton("🔍");
        searchBtn.addActionListener(e -> filtrarProductos(searchField.getText(), (String)filterCombo.getSelectedItem()));
        searchPanel.add(searchField, BorderLayout.CENTER);
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.add(filterCombo);
        filterPanel.add(searchBtn);
        searchPanel.add(filterPanel, BorderLayout.EAST);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        String[] columns = {"Código", "Nombre", "Categoría", "Precio", "Stock", "Estado"};
        productosModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        productosTable = new JTable(productosModel);
        productosTable.setRowHeight(25);
        productosTable.getTableHeader().setBackground(HEADER_COLOR);
        productosTable.getTableHeader().setForeground(Color.BLACK);
        
        productosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    cargarProductoSeleccionado();
                }
            }
        });
        
        productosTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && value.equals("OK")) {
                    c.setBackground(STATUS_OK);
                    c.setForeground(Color.WHITE);
                } else if (value != null && value.equals("BAJO")) {
                    c.setBackground(STATUS_LOW);
                    c.setForeground(Color.WHITE);
                }
                if (!isSelected) setHorizontalAlignment(CENTER);
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(productosTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSalesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_COLOR, 2),
            "Ventas del Día",
            0, 0, new Font("Arial", Font.BOLD, 14),
            HEADER_COLOR
        ));
        
        String[] columns = {"Factura", "Cliente", "Hora", "Total"};
        ventasModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        ventasDelDiaTable = new JTable(ventasModel);
        ventasDelDiaTable.setRowHeight(25);
        ventasDelDiaTable.getTableHeader().setBackground(HEADER_COLOR);
        ventasDelDiaTable.getTableHeader().setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(ventasDelDiaTable);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel totalLabel = new JLabel("Total del Día:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalDiaLabel = new JLabel("€0.00");
        totalDiaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalDiaLabel.setForeground(new Color(0, 128, 0));
        totalPanel.add(totalLabel);
        totalPanel.add(totalDiaLabel);
        
        panel.add(totalPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        rightPanel.add(createNewSalePanel());
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(createReportsPanel());
        
        return rightPanel;
    }
    
    private JPanel createNewSalePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_COLOR, 2),
            "Nueva Venta",
            0, 0, new Font("Arial", Font.BOLD, 14),
            HEADER_COLOR
        ));
        
        JPanel form = new JPanel(new GridLayout(7, 2, 5, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        form.add(new JLabel("N° Factura:"));
        facturaField = new JTextField();
        generarNumeroFactura();
        form.add(facturaField);
        
        form.add(new JLabel("Cliente:"));
        clienteField = new JTextField();
        form.add(clienteField);
        
        form.add(new JLabel("Fecha:"));
        JTextField fechaField = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        fechaField.setEditable(false);
        form.add(fechaField);
        
        form.add(new JLabel("Método de Pago:"));
        metodoPagoCombo = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia"});
        form.add(metodoPagoCombo);
        
        form.add(new JLabel(""));
        requiereFacturaCheck = new JCheckBox("Requiere Factura");
        form.add(requiereFacturaCheck);
        
        form.add(new JLabel(""));
        servicioDomicilioCheck = new JCheckBox("Servicio a Domicilio");
        form.add(servicioDomicilioCheck);
        
        form.add(new JLabel("Total (€):"));
        totalField = new JTextField("0.00");
        form.add(totalField);
        
        panel.add(form);
        
        JButton registrarBtn = new JButton("Registrar Venta");
        registrarBtn.setBackground(BUTTON_GREEN);
        registrarBtn.setForeground(Color.BLACK);
        registrarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrarBtn.setMaximumSize(new Dimension(300, 40));
        registrarBtn.addActionListener(e -> registrarVenta());
        panel.add(Box.createVerticalStrut(10));
        panel.add(registrarBtn);
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_COLOR, 2),
            "Reportes",
            0, 0, new Font("Arial", Font.BOLD, 14),
            HEADER_COLOR
        ));
        
        JPanel datePanel = new JPanel(new GridLayout(2, 2, 5, 10));
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        datePanel.add(new JLabel("Desde:"));
        datePanel.add(new JLabel("Hasta:"));
        desdeField = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hastaField = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        datePanel.add(desdeField);
        datePanel.add(hastaField);
        panel.add(datePanel);
        
        // Panel combinado para Tipo y Opciones (lado a lado)
        JPanel tipoOpcionesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        tipoOpcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel Tipo
        JPanel tipoPanel = new JPanel();
        tipoPanel.setLayout(new BoxLayout(tipoPanel, BoxLayout.Y_AXIS));
        tipoPanel.setBorder(BorderFactory.createTitledBorder("Tipo"));
        
        ButtonGroup tipoGroup = new ButtonGroup();
        ventasRadio = new JRadioButton("Ventas", true);
        inventarioRadio = new JRadioButton("Inventario");
        masVendidosRadio = new JRadioButton("Más Vendidos");
        
        ventasRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        inventarioRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        masVendidosRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        tipoGroup.add(ventasRadio);
        tipoGroup.add(inventarioRadio);
        tipoGroup.add(masVendidosRadio);
        
        tipoPanel.add(ventasRadio);
        tipoPanel.add(inventarioRadio);
        tipoPanel.add(masVendidosRadio);
        
        // Panel Opciones
        JPanel opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));
        opcionesPanel.setBorder(BorderFactory.createTitledBorder("Opciones"));
        
        graficosCheck = new JCheckBox("Incluir Gráficos");
        excelCheck = new JCheckBox("Exportar Excel");
        pdfCheck = new JCheckBox("Exportar PDF");
        
        graficosCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        excelCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        pdfCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        opcionesPanel.add(graficosCheck);
        opcionesPanel.add(excelCheck);
        opcionesPanel.add(pdfCheck);
        
        tipoOpcionesPanel.add(tipoPanel);
        tipoOpcionesPanel.add(opcionesPanel);
        panel.add(tipoOpcionesPanel);
        
        JButton generarBtn = new JButton("Generar Reporte");
        generarBtn.setBackground(BUTTON_GREEN);
        generarBtn.setForeground(Color.BLACK);
        generarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        generarBtn.setMaximumSize(new Dimension(300, 40));
        generarBtn.addActionListener(e -> generarReporte());
        panel.add(Box.createVerticalStrut(10));
        panel.add(generarBtn);
        
        return panel;
    }
    
    // MÉTODOS FUNCIONALES
    
    private void cargarDatos() {
        cargarProductos();
        cargarVentas();
        actualizarGraficos();
    }
    
    private void cargarProductos() {
        productosModel.setRowCount(0);
        List<Producto> productos = dbManager.obtenerProductos();
        for (Producto p : productos) {
            productosModel.addRow(new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getCategoria(),
                String.format("€%.2f", p.getPrecio()),
                p.getStock(),
                p.getEstado()
            });
        }
    }
    
    private void cargarVentas() {
        ventasModel.setRowCount(0);
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Venta> ventas = dbManager.obtenerVentasDelDia(fecha);
        
        for (Venta v : ventas) {
            ventasModel.addRow(new Object[]{
                v.getNumeroFactura(),
                v.getCliente(),
                v.getHora(),
                String.format("€%.2f", v.getTotal())
            });
        }
        
        double total = dbManager.obtenerTotalVentasDia(fecha);
        totalDiaLabel.setText(String.format("€%.2f", total));
    }
    
    private void agregarProducto() {
        try {
            String codigo = codigoField.getText().trim();
            String nombre = nombreField.getText().trim();
            double precio = Double.parseDouble(precioField.getText().trim());
            
            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String categoria = panRadio.isSelected() ? "Pan" : 
                             bolleriaRadio.isSelected() ? "Bollería" : "Pastelería";
            
            // Pedir stock
            String stockStr = JOptionPane.showInputDialog(this, "Ingrese el stock inicial:", "Stock", JOptionPane.QUESTION_MESSAGE);
            if (stockStr == null || stockStr.trim().isEmpty()) return;
            
            int stock = Integer.parseInt(stockStr.trim());
            
            Producto producto = new Producto(codigo, nombre, categoria, precio, stock, 
                                            sinGlutenCheck.isSelected(), ofertaCheck.isSelected());
            
            if (dbManager.agregarProducto(producto)) {
                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioProducto();
                cargarDatos();
                actualizarGraficosEnTiempoReal(); // ← ACTUALIZACIÓN EN TIEMPO REAL
            } else {
                JOptionPane.showMessageDialog(this, "Error: El código ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormularioProducto() {
        codigoField.setText("");
        nombreField.setText("");
        precioField.setText("0.00");
        panRadio.setSelected(true);
        sinGlutenCheck.setSelected(false);
        ofertaCheck.setSelected(false);
    }
    
    private void eliminarProductoSeleccionado() {
        int selectedRow = productosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String codigo = (String) productosModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea eliminar este producto?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dbManager.eliminarProducto(codigo)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                actualizarGraficosEnTiempoReal(); // ← ACTUALIZACIÓN EN TIEMPO REAL
            }
        }
    }
    
    private void cargarProductoSeleccionado() {
        int selectedRow = productosTable.getSelectedRow();
        if (selectedRow == -1) return;
        
        String codigo = (String) productosModel.getValueAt(selectedRow, 0);
        Producto producto = dbManager.buscarProductoPorCodigo(codigo);
        
        if (producto != null) {
            codigoField.setText(producto.getCodigo());
            nombreField.setText(producto.getNombre());
            precioField.setText(String.valueOf(producto.getPrecio()));
            
            if (producto.getCategoria().equals("Pan")) panRadio.setSelected(true);
            else if (producto.getCategoria().equals("Bollería")) bolleriaRadio.setSelected(true);
            else pasteleriaRadio.setSelected(true);
            
            sinGlutenCheck.setSelected(producto.isSinGluten());
            ofertaCheck.setSelected(producto.isOferta());
        }
    }
    
    private void buscarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del producto:", "Buscar Producto", JOptionPane.QUESTION_MESSAGE);
        
        if (codigo != null && !codigo.trim().isEmpty()) {
            Producto producto = dbManager.buscarProductoPorCodigo(codigo.trim());
            
            if (producto != null) {
                codigoField.setText(producto.getCodigo());
                nombreField.setText(producto.getNombre());
                precioField.setText(String.valueOf(producto.getPrecio()));
                
                if (producto.getCategoria().equals("Pan")) panRadio.setSelected(true);
                else if (producto.getCategoria().equals("Bollería")) bolleriaRadio.setSelected(true);
                else pasteleriaRadio.setSelected(true);
                
                sinGlutenCheck.setSelected(producto.isSinGluten());
                ofertaCheck.setSelected(producto.isOferta());
                
                JOptionPane.showMessageDialog(this, "Producto encontrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void filtrarProductos(String busqueda, String categoria) {
        productosModel.setRowCount(0);
        List<Producto> productos = dbManager.obtenerProductos();
        
        for (Producto p : productos) {
            boolean coincideBusqueda = busqueda.isEmpty() || busqueda.equals("Buscar producto...") ||
                                      p.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
                                      p.getCodigo().toLowerCase().contains(busqueda.toLowerCase());
            
            boolean coincideCategoria = categoria.equals("Todas") || p.getCategoria().equals(categoria);
            
            if (coincideBusqueda && coincideCategoria) {
                productosModel.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    String.format("€%.2f", p.getPrecio()),
                    p.getStock(),
                    p.getEstado()
                });
            }
        }
    }
    
    private void generarNumeroFactura() {
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        int numero = (int)(Math.random() * 9999) + 1;
        facturaField.setText(String.format("F-%s-%04d", fecha, numero));
    }
    
    private void registrarVenta() {
        try {
            String numeroFactura = facturaField.getText().trim();
            String cliente = clienteField.getText().trim();
            double total = Double.parseDouble(totalField.getText().trim());
            
            if (numeroFactura.isEmpty() || cliente.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            String metodoPago = (String) metodoPagoCombo.getSelectedItem();
            
            Venta venta = new Venta(numeroFactura, cliente, fecha, hora, metodoPago, total,
                                   requiereFacturaCheck.isSelected(), servicioDomicilioCheck.isSelected());
            
            if (dbManager.registrarVenta(venta)) {
                JOptionPane.showMessageDialog(this, "Venta registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioVenta();
                cargarVentas();
                actualizarGraficosEnTiempoReal(); // ← ACTUALIZACIÓN EN TIEMPO REAL
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un total válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormularioVenta() {
        generarNumeroFactura();
        clienteField.setText("");
        totalField.setText("0.00");
        metodoPagoCombo.setSelectedIndex(0);
        requiereFacturaCheck.setSelected(false);
        servicioDomicilioCheck.setSelected(false);
    }
    
    private void mostrarDialogoStock() {
        int selectedRow = productosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String codigo = (String) productosModel.getValueAt(selectedRow, 0);
        Producto producto = dbManager.buscarProductoPorCodigo(codigo);
        
        if (producto != null) {
            String nuevoStockStr = JOptionPane.showInputDialog(this, 
                "Stock actual: " + producto.getStock() + "\nIngrese el nuevo stock:", 
                "Actualizar Stock", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (nuevoStockStr != null && !nuevoStockStr.trim().isEmpty()) {
                try {
                    int nuevoStock = Integer.parseInt(nuevoStockStr.trim());
                    producto.setStock(nuevoStock);
                    
                    if (dbManager.actualizarProducto(producto)) {
                        JOptionPane.showMessageDialog(this, "Stock actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarDatos();
                        actualizarGraficosEnTiempoReal(); // ← ACTUALIZACIÓN EN TIEMPO REAL
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void actualizarGraficos() {
        // Actualizar gráfico de ventas por categoría
        categoriasChart.setChart(crearGraficoVentasPorCategoria());
        
        // Actualizar gráfico de distribución de stock
        stockChart.setChart(crearGraficoDistribucionStock());
    }
    
    // Método para actualización en tiempo real con animación
    private void actualizarGraficosEnTiempoReal() {
        SwingUtilities.invokeLater(() -> {
            // Actualizar gráfico de ventas por categoría
            JFreeChart nuevoGraficoCategorias = crearGraficoVentasPorCategoria();
            categoriasChart.setChart(nuevoGraficoCategorias);
            categoriasChart.repaint();
            
            // Actualizar gráfico de distribución de stock
            JFreeChart nuevoGraficoStock = crearGraficoDistribucionStock();
            stockChart.setChart(nuevoGraficoStock);
            stockChart.repaint();
            
            // Efecto visual de actualización
            categoriasChart.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            stockChart.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            
            // Restaurar borde normal después de 500ms
            Timer timer = new Timer(500, e -> {
                categoriasChart.setBorder(BorderFactory.createEmptyBorder());
                stockChart.setBorder(BorderFactory.createEmptyBorder());
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
    
    private void generarReporte() {
        String tipoReporte = ventasRadio.isSelected() ? "Ventas" :
                           inventarioRadio.isSelected() ? "Inventario" : "Más Vendidos";
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE ").append(tipoReporte.toUpperCase()).append(" ===\n\n");
        reporte.append("Fecha: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        reporte.append("Desde: ").append(desdeField.getText()).append("\n");
        reporte.append("Hasta: ").append(hastaField.getText()).append("\n\n");
        
        if (ventasRadio.isSelected()) {
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<Venta> ventas = dbManager.obtenerVentasDelDia(fecha);
            reporte.append("Total de ventas: ").append(ventas.size()).append("\n");
            reporte.append("Total recaudado: €").append(String.format("%.2f", dbManager.obtenerTotalVentasDia(fecha))).append("\n\n");
            
            for (Venta v : ventas) {
                reporte.append(v.getNumeroFactura()).append(" - ")
                       .append(v.getCliente()).append(" - ")
                       .append(v.getHora()).append(" - €")
                       .append(String.format("%.2f", v.getTotal())).append("\n");
            }
        } else if (inventarioRadio.isSelected()) {
            List<Producto> productos = dbManager.obtenerProductos();
            reporte.append("Total de productos: ").append(productos.size()).append("\n\n");
            
            for (Producto p : productos) {
                reporte.append(p.getCodigo()).append(" - ")
                       .append(p.getNombre()).append(" - ")
                       .append(p.getCategoria()).append(" - ")
                       .append("Stock: ").append(p.getStock())
                       .append(" - Estado: ").append(p.getEstado()).append("\n");
            }
        }
        
        if (graficosCheck.isSelected()) {
            reporte.append("\n[Gráficos incluidos]\n");
        }
        if (excelCheck.isSelected()) {
            reporte.append("[Exportado a Excel]\n");
        }
        if (pdfCheck.isSelected()) {
            reporte.append("[Exportado a PDF]\n");
        }
        
        JTextArea textArea = new JTextArea(reporte.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Reporte - " + tipoReporte, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void imprimirReporte() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad de impresión disponible.\nSe enviaría el reporte a la impresora predeterminada.", 
            "Imprimir", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportarDatos() {
        String[] opciones = {"Excel", "PDF", "CSV"};
        int seleccion = JOptionPane.showOptionDialog(this, 
            "Seleccione el formato de exportación:", 
            "Exportar Datos", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, opciones, opciones[0]);
        
        if (seleccion >= 0) {
            JOptionPane.showMessageDialog(this, 
                "Datos exportados exitosamente a formato " + opciones[seleccion], 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void mostrarAcercaDe() {
        String mensaje = "Sistema de Punto de Venta\n" +
                        "Panadería Artesanal\n\n" +
                        "Versión 1.0.0\n" +
                        "© 2025 Todos los derechos reservados\n\n" +
                        "Desarrollado para gestión de productos,\n" +
                        "ventas e inventario.";
        
        JOptionPane.showMessageDialog(this, mensaje, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            // Detener timer de actualización automática
            if (autoUpdateTimer != null) {
                autoUpdateTimer.stop();
            }
            dbManager.cerrarConexion();
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                dispose();
            });
        }
    }
    
    // Método para iniciar actualización automática de gráficos
    private void iniciarActualizacionAutomatica() {
        // Actualizar cada 30 segundos (30000 ms)
        autoUpdateTimer = new Timer(30000, e -> {
            actualizarGraficosEnTiempoReal();
            System.out.println("Gráficos actualizados automáticamente: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        });
        autoUpdateTimer.start();
    }
}