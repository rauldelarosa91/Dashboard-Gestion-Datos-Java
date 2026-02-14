/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_dashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mycompany.proyecto_dashboard.Usuario;
import com.mycompany.proyecto_dashboard.UsuarioManager;
/**
 *
 * @author Hp
 */
public class LoginFrame extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(205, 153, 102);
    private static final Color BUTTON_COLOR = new Color(120, 180, 120);
    
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private UsuarioManager usuarioManager;
    
    public LoginFrame() {
        usuarioManager = new UsuarioManager();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Panadería - Iniciar Sesión");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(250, 248, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        
        // Logo/Título con diseño mejorado
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(250, 248, 245));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Panadería Artesanal");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);
        
        headerPanel.add(Box.createVerticalStrut(10));
        
        JLabel subtitleLabel = new JLabel("Sistema de Gestión y Ventas");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(120, 120, 120));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(subtitleLabel);
        
        mainPanel.add(headerPanel);
        
        mainPanel.add(Box.createVerticalStrut(45));
        
        // Panel de formulario con sombra
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(35, 40, 35, 40)
        ));
        formPanel.setMaximumSize(new Dimension(380, 400));
        
        // Usuario
        JLabel usuarioLabel = new JLabel("Nombre de usuario");
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 13));
        usuarioLabel.setForeground(new Color(60, 60, 60));
        usuarioLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(usuarioLabel);
        
        formPanel.add(Box.createVerticalStrut(8));
        
        usuarioField = new JTextField();
        usuarioField.setFont(new Font("Arial", Font.PLAIN, 15));
        usuarioField.setMaximumSize(new Dimension(380, 45));
        usuarioField.setBackground(Color.WHITE);
        usuarioField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        formPanel.add(usuarioField);
        
        formPanel.add(Box.createVerticalStrut(25));
        
        // Contraseña
        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passwordLabel.setForeground(new Color(60, 60, 60));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordLabel);
        
        formPanel.add(Box.createVerticalStrut(8));
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setMaximumSize(new Dimension(380, 45));
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        formPanel.add(passwordField);
        
        formPanel.add(Box.createVerticalStrut(35));
        
        // Botón Iniciar Sesión
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(205, 153, 102));
        loginButton.setForeground(new Color(90, 60, 40));
        loginButton.setMaximumSize(new Dimension(380, 50));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(true);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(180, 130, 90), 2));
        loginButton.setOpaque(true);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> iniciarSesion());
        
        // Efecto hover
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(185, 133, 82));
                loginButton.setForeground(new Color(70, 45, 30));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(205, 153, 102));
                loginButton.setForeground(new Color(90, 60, 40));
            }
        });
        
        formPanel.add(loginButton);
        
        formPanel.add(Box.createVerticalStrut(20));
        
        // Enlace a registro
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setMaximumSize(new Dimension(380, 35));
        
        JLabel noAccountLabel = new JLabel("¿No tienes cuenta?");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        noAccountLabel.setForeground(new Color(100, 100, 100));
        registerPanel.add(noAccountLabel);
        
        JButton registerLink = new JButton("Crear una cuenta");
        registerLink.setFont(new Font("Arial", Font.BOLD, 13));
        registerLink.setForeground(new Color(139, 92, 71));
        registerLink.setBackground(new Color(250, 248, 245));
        registerLink.setBorderPainted(false);
        registerLink.setContentAreaFilled(true);
        registerLink.setFocusPainted(false);
        registerLink.setOpaque(true);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.addActionListener(e -> abrirRegistro());
        
        // Efecto hover para el enlace
        registerLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerLink.setForeground(new Color(170, 115, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerLink.setForeground(new Color(139, 92, 71));
            }
        });
        
        registerPanel.add(registerLink);
        
        formPanel.add(registerPanel);
        
        mainPanel.add(formPanel);
        
        // Enter para login
        passwordField.addActionListener(e -> iniciarSesion());
        
        add(mainPanel);
    }
    
    private void iniciarSesion() {
        String usuario = usuarioField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, complete todos los campos",
                "Campos vacíos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usuario user = usuarioManager.validarUsuario(usuario, password);
        
        if (user != null) {
            // Login exitoso
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido " + user.getNombre() + "!",
                "Inicio de sesión exitoso",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir dashboard
            SwingUtilities.invokeLater(() -> {
                DashboardFrame dashboard = new DashboardFrame(user);
                dashboard.setVisible(true);
                dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos",
                "Error de autenticación",
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
    
    private void abrirRegistro() {
        RegisterFrame registerFrame = new RegisterFrame(this);
        registerFrame.setVisible(true);
        setVisible(false);
    }
}
