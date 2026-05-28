package view;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoginWindow extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final Map<String, String> users = new HashMap<>();

    public LoginWindow() {
        setTitle("Login - BTE System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        loadUsers();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel logoPanel = new JPanel();
        try {
            URL logoUrl = new URL("https://b2b.tn/files/2022/11/BTE.jpg");
            ImageIcon icon = new ImageIcon(logoUrl);
            Image scaled = icon.getImage().getScaledInstance(250, -1, Image.SCALE_SMOOTH);
            logoPanel.add(new JLabel(new ImageIcon(scaled)));
        } catch (Exception e) {
            logoPanel.add(new JLabel("Logo Error", SwingConstants.CENTER));
        }
        panel.add(logoPanel);
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(java.awt.Color.BLACK);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(java.awt.Color.BLACK);

        formPanel.add(userLabel);
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> attemptLogin());

        formPanel.add(new JLabel());
        formPanel.add(loginButton);

        panel.add(formPanel);
        add(panel);
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("database/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load users.txt", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (users.containsKey(username) && users.get(username).equals(password)) {
            saveLoginToExcel(username);
            this.dispose();
            new MainWindow(username).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveLoginToExcel(String username) {
        File file = new File("database/login_log.xlsx");
        Workbook workbook;
        Sheet sheet;

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                sheet.autoSizeColumn(WIDTH);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Login Log");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Username");
                header.createCell(1).setCellValue("Timestamp");
                sheet.autoSizeColumn(WIDTH);
            }
            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);
            row.createCell(0).setCellValue(username);
            row.createCell(1).setCellValue(LocalDateTime.now().toString());

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            workbook.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to write login log to Excel", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
