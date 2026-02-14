/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_dashboard;
import javax.swing.*;
import java.awt.*;
import com.mycompany.proyecto_dashboard.Usuario;
import com.mycompany.proyecto_dashboard.UsuarioManager;
/**
 *
 * @author Hp
 */
public class RegisterFrame extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(205, 153, 102);
    private static final Color BUTTON_COLOR = new Color(120, 180, 120);
    
    private JTextField nombreField;
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private UsuarioManager usuarioManager;
    private LoginFrame loginFrame;
    
    public RegisterFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.usuarioManager = new UsuarioManager();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Panadería - Crear Cuenta");
        setSize(500, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(250, 248, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        // Header mejorado
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(250, 248, 245));
        
        JLabel titleLabel = new JLabel("Crear Cuenta Nueva");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);
        
        headerPanel.add(Box.createVerticalStrut(8));
        
        JLabel subtitleLabel = new JLabel("Únete a nuestro sistema");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitleLabel.setForeground(new Color(120, 120, 120));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(subtitleLabel);
        
        mainPanel.add(headerPanel);
        
        mainPanel.add(Box.createVerticalStrut(35));
        
        // Panel de formulario mejorado
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));
        formPanel.setMaximumSize(new Dimension(380, 550));
        
        // Nombre completo
        addStyledFormField(formPanel, "Nombre Completo", nombreField = new JTextField());
        formPanel.add(Box.createVerticalStrut(18));
        
        // Usuario
        addStyledFormField(formPanel, "Nombre de Usuario", usuarioField = new JTextField());
        formPanel.add(Box.createVerticalStrut(18));
        
        // Email
        addStyledFormField(formPanel, "Correo Electrónico", emailField = new JTextField());
        formPanel.add(Box.createVerticalStrut(18));
        
        // Contraseña
        addStyledFormField(formPanel, "Contraseña", passwordField = new JPasswordField());
        formPanel.add(Box.createVerticalStrut(18));
        
        // Confirmar contraseña
        addStyledFormField(formPanel, "Confirmar Contraseña", confirmPasswordField = new JPasswordField());
        formPanel.add(Box.createVerticalStrut(30));
        
        // Botón Registrarse
        JButton registerButton = new JButton("Crear Cuenta");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(205, 153, 102));
        registerButton.setForeground(new Color(90, 60, 40));
        registerButton.setMaximumSize(new Dimension(380, 50));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(true);
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(180, 130, 90), 2));
        registerButton.setOpaque(true);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> registrarUsuario());
        
        // Efecto hover
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(185, 133, 82));
                registerButton.setForeground(new Color(70, 45, 30));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(205, 153, 102));
                registerButton.setForeground(new Color(90, 60, 40));
            }
        });
        
        formPanel.add(registerButton);
        
        formPanel.add(Box.createVerticalStrut(18));
        
        // Enlace a login
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setMaximumSize(new Dimension(380, 35));
        
        JLabel haveAccountLabel = new JLabel("¿Ya tienes cuenta?");
        haveAccountLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        haveAccountLabel.setForeground(new Color(100, 100, 100));
        loginPanel.add(haveAccountLabel);
        
        JButton loginLink = new JButton("Iniciar Sesión");
        loginLink.setFont(new Font("Arial", Font.BOLD, 13));
        loginLink.setForeground(new Color(139, 92, 71));
        loginLink.setBorderPainted(false);
        loginLink.setContentAreaFilled(false);
        loginLink.setFocusPainted(false);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addActionListener(e -> volverALogin());
        
        // Efecto hover
        loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLink.setForeground(new Color(170, 115, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLink.setForeground(new Color(139, 92, 71));
            }
        });
        
        loginPanel.add(loginLink);
        
        formPanel.add(loginPanel);
        
        mainPanel.add(formPanel);
        
        add(mainPanel);
        
        // Cerrar ventana al cerrar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                volverALogin();
            }
        });
    }
    
    private void addStyledFormField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(new Color(60, 60, 60));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        panel.add(Box.createVerticalStrut(8));
        
        field.setFont(new Font("Arial", Font.PLAIN, 15));
        field.setMaximumSize(new Dimension(380, 45));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        panel.add(field);
    }
    
    private void registrarUsuario() {
        String nombre = nombreField.getText().trim();
        String usuario = usuarioField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validaciones
        if (nombre.isEmpty() || usuario.isEmpty() || email.isEmpty() || 
            password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, complete todos los campos",
                "Campos vacíos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Las contraseñas no coinciden",
                "Error de contraseña",
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "La contraseña debe tener al menos 6 caracteres",
                "Contraseña débil",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese un email válido",
                "Email inválido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Registrar usuario
        if (usuarioManager.registrarUsuario(nombre, usuario, email, password)) {
            JOptionPane.showMessageDialog(this,
                "¡Registro exitoso! Ahora puedes iniciar sesión",
                "Registro completado",
                JOptionPane.INFORMATION_MESSAGE);
            volverALogin();
        } else {
            JOptionPane.showMessageDialog(this,
                "El nombre de usuario ya existe. Por favor, elija otro",
                "Usuario existente",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void volverALogin() {
        loginFrame.setVisible(true);
        dispose();
    }
}
