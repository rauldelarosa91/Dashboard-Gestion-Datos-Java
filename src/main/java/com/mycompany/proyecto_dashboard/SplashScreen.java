/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_dashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
/**
 *
 * @author Hp
 */
public class SplashScreen extends JWindow {
    private static final Color PRIMARY_COLOR = new Color(205, 153, 102);
    private static final Color BG_COLOR = new Color(250, 248, 245);
    private JProgressBar progressBar;
    private JLabel statusLabel;
    
    public SplashScreen() {
        initComponents();
    }
    
    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 3),
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        
        // Espacio superior
        mainPanel.add(Box.createVerticalStrut(30));
        
        // Título
        JLabel titleLabel = new JLabel("Panadería Artesanal");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 42));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        
        mainPanel.add(Box.createVerticalStrut(10));
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Sistema de Gestión y Ventas");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(120, 120, 120));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        
        mainPanel.add(Box.createVerticalStrut(50));
        
        // Barra de progreso
        progressBar = new JProgressBar(0, 100);
        progressBar.setMaximumSize(new Dimension(400, 25));
        progressBar.setPreferredSize(new Dimension(400, 25));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setForeground(PRIMARY_COLOR);
        progressBar.setBackground(Color.WHITE);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(progressBar);
        
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Etiqueta de estado
        statusLabel = new JLabel("Iniciando sistema...");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(100, 100, 100));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);
        
        mainPanel.add(Box.createVerticalStrut(30));
        
        // Versión
        JLabel versionLabel = new JLabel("Versión 1.0.0");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        versionLabel.setForeground(new Color(150, 150, 150));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(versionLabel);
        
        add(mainPanel);
        
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        // Hacer la ventana con bordes redondeados (opcional)
        setShape(new RoundRectangle2D.Double(0, 0, 600, 450, 20, 20));
    }
    
    public void mostrarYCargar() {
        setVisible(true);
        
        // Simular proceso de carga
        new Thread(() -> {
            try {
                // Cargando componentes...
                actualizarProgreso(0, "Inicializando componentes...");
                Thread.sleep(500);
                
                actualizarProgreso(20, "Cargando recursos...");
                Thread.sleep(400);
                
                actualizarProgreso(40, "Configurando base de datos...");
                Thread.sleep(500);
                
                actualizarProgreso(60, "Cargando módulos...");
                Thread.sleep(400);
                
                actualizarProgreso(80, "Preparando interfaz...");
                Thread.sleep(500);
                
                actualizarProgreso(100, "¡Listo!");
                Thread.sleep(300);
                
                // Cerrar splash y abrir login
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                });
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void actualizarProgreso(int valor, String mensaje) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(valor);
            statusLabel.setText(mensaje);
        });
    }
}
