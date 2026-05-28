package view;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TypeCreanceWindow extends JFrame {
    public TypeCreanceWindow() {
        setTitle("Les Types de Créances");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Side panel with logo and SIEGE label
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBounds(0, 0, 120, 768); // vertical side bar
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        JLabel logoSideLabel = new JLabel();
        logoSideLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon icon = new ImageIcon(new java.net.URL("https://b2b.tn/files/2022/11/BTE.jpg"));
            Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            logoSideLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            logoSideLabel.setText("BTE");
            logoSideLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JLabel siegeLabel = new JLabel("SIÈGE");
        siegeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        siegeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        siegeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(logoSideLabel);
        sidePanel.add(siegeLabel);
        add(sidePanel);

        // Title
        JLabel title = new JLabel("Les Types de Créances", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(120, 10, 904, 40); // adjust width after side panel
        add(title);

        // Date label
        JLabel dateLabel = new JLabel();
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setBounds(870, 20, 150, 20);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateLabel.setText("Date: " + LocalDate.now().format(formatter));
        add(dateLabel);

        // Table
        JTable table = new JTable(new DefaultTableModel(new Object[]{"Code produit", "Libellé", "Devise"}, 0));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 70, 760, 180);
        add(scrollPane);

        try (FileInputStream fis = new FileInputStream("database/TYPES_CREDITS_MCR.xlsx")) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    model.addRow(new Object[]{
                            row.getCell(0),
                            row.getCell(1),
                            row.getCell(2)
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement Excel", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        final var downloadButton = getJButton(table);
        add(downloadButton);

        JPanel interetsPanel = new JPanel();
        interetsPanel.setBorder(BorderFactory.createTitledBorder("Prélèvement intérêts"));
        interetsPanel.setBounds(140, 270, 220, 70);
        interetsPanel.setLayout(new GridLayout(2, 1));
        JRadioButton source = new JRadioButton("A la Source", true);
        JRadioButton echeance = new JRadioButton("A l'échéance");
        ButtonGroup prelevGroup = new ButtonGroup();
        prelevGroup.add(source);
        prelevGroup.add(echeance);
        interetsPanel.add(source);
        interetsPanel.add(echeance);
        add(interetsPanel);

        JPanel materPanel = new JPanel();
        materPanel.setBorder(BorderFactory.createTitledBorder("Matérialisation"));
        materPanel.setBounds(380, 270, 180, 70);
        materPanel.setLayout(new GridLayout(2, 1));
        JRadioButton credit = new JRadioButton("Titre de Crédit");
        JRadioButton billet = new JRadioButton("Billet à Ordre", true);
        ButtonGroup matGroup = new ButtonGroup();
        matGroup.add(credit);
        matGroup.add(billet);
        materPanel.add(credit);
        materPanel.add(billet);
        add(materPanel);

        JCheckBox perequation = new JCheckBox("Péréquation de change");
        perequation.setBounds(580, 270, 200, 30);
        add(perequation);

        JCheckBox dateFin = new JCheckBox("Date Fin non obligatoire");
        dateFin.setBounds(580, 310, 220, 30);
        add(dateFin);

        JPanel tauxPanel = new JPanel(null);
        tauxPanel.setBorder(BorderFactory.createTitledBorder("Taux Intérêt"));
        tauxPanel.setBounds(140, 360, 660, 90);

        JLabel typeLbl = new JLabel("Type:");
        typeLbl.setBounds(10, 20, 50, 20);
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Taux Indexé"});
        typeBox.setBounds(60, 20, 120, 25);

        JLabel refLbl = new JLabel("Indice de Référence:");
        refLbl.setBounds(190, 20, 130, 20);
        JComboBox<String> refBox = new JComboBox<>(new String[]{"TMM"});
        refBox.setBounds(320, 20, 100, 25);

        JLabel margeLbl = new JLabel("Marge:");
        margeLbl.setBounds(430, 20, 60, 20);
        JTextField margeField = new JTextField("4.000000%");
        margeField.setBounds(490, 20, 80, 25);

        JLabel minLbl = new JLabel("Marge Min:");
        minLbl.setBounds(10, 55, 100, 20);
        JTextField minField = new JTextField("0.250000%");
        minField.setBounds(100, 55, 80, 25);

        JLabel maxLbl = new JLabel("Marge Max:");
        maxLbl.setBounds(190, 55, 100, 20);
        JTextField maxField = new JTextField("5.000000%");
        maxField.setBounds(280, 55, 80, 25);

        JLabel avLbl = new JLabel("Marge (Av. sur Facture):");
        avLbl.setBounds(370, 55, 180, 20);
        JTextField avField = new JTextField();
        avField.setBounds(550, 55, 80, 25);

        tauxPanel.add(typeLbl);
        tauxPanel.add(typeBox);
        tauxPanel.add(refLbl);
        tauxPanel.add(refBox);
        tauxPanel.add(margeLbl);
        tauxPanel.add(margeField);
        tauxPanel.add(minLbl);
        tauxPanel.add(minField);
        tauxPanel.add(maxLbl);
        tauxPanel.add(maxField);
        tauxPanel.add(avLbl);
        tauxPanel.add(avField);
        add(tauxPanel);

        JPanel retardPanel = new JPanel(null);
        retardPanel.setBorder(BorderFactory.createTitledBorder("Intérêt de Retard"));
        retardPanel.setBounds(820, 360, 180, 120);
        JRadioButton margePlus = new JRadioButton("Marge en plus", true);
        margePlus.setBounds(10, 20, 150, 25);
        JRadioButton fixe = new JRadioButton("Fixe indépendant");
        fixe.setBounds(10, 45, 150, 25);
        JTextField retardField = new JTextField("2.000000%");
        retardField.setBounds(10, 70, 100, 25);
        ButtonGroup retardGroup = new ButtonGroup();
        retardGroup.add(margePlus);
        retardGroup.add(fixe);
        retardPanel.add(margePlus);
        retardPanel.add(fixe);
        retardPanel.add(retardField);
        add(retardPanel);

        JPanel amortPanel = new JPanel(new GridLayout(2, 1));
        amortPanel.setBorder(BorderFactory.createTitledBorder("Nature Amortissement"));
        amortPanel.setBounds(140, 470, 220, 70);
        JRadioButton actuel = new JRadioButton("Actuariel", true);
        JRadioButton prop = new JRadioButton("Proportionnel");
        ButtonGroup amortGroup = new ButtonGroup();
        amortGroup.add(actuel);
        amortGroup.add(prop);
        amortPanel.add(actuel);
        amortPanel.add(prop);
        add(amortPanel);

        JPanel joursPanel = new JPanel(null);
        joursPanel.setBorder(BorderFactory.createTitledBorder("Nombre de jours"));
        joursPanel.setBounds(380, 470, 400, 70);

        JLabel moisLbl = new JLabel("Par mois:");
        moisLbl.setBounds(10, 20, 200, 25);

        JButton moisBtn = new JButton("Calend...");
        moisBtn.setBounds(90, 20, 80, 25);
        moisBtn.addActionListener(e -> showMonthYearPicker(moisBtn));

        JLabel anLbl = new JLabel("Par année:");
        anLbl.setBounds(180, 20, 80, 25);

        JButton anBtn = new JButton("360");
        anBtn.setBounds(250, 20, 60, 25);
        anBtn.addActionListener(e -> showYearPicker(anBtn));

        joursPanel.add(moisLbl);
        joursPanel.add(moisBtn);
        joursPanel.add(anLbl);
        joursPanel.add(anBtn);
        add(joursPanel);
    }

    private JButton getJButton(JTable table) {
        JButton downloadButton = new JButton("Exporter");
        downloadButton.setBounds(910, 250, 90, 25);
        downloadButton.addActionListener(e -> {
            try (FileOutputStream fos = new FileOutputStream("exported_table.xlsx")) {
                Workbook exportWb = new XSSFWorkbook();
                Sheet sheet = exportWb.createSheet("Créances");
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                Row header = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    header.createCell(i).setCellValue(model.getColumnName(i));
                }
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object val = model.getValueAt(i, j);
                        row.createCell(j).setCellValue(val != null ? val.toString() : "");
                    }
                }
                exportWb.write(fos);
                exportWb.close();
                JOptionPane.showMessageDialog(this, "Exportation réussie dans exported_table.xlsx");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur exportation: " + ex.getMessage());
            }
        });
        return downloadButton;
    }

    private void showMonthYearPicker(JButton targetButton) {
        JDialog dialog = new JDialog(this, "Sélectionner Mois/Année", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        Calendar calendar = GregorianCalendar.getInstance();

        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(
                calendar.get(Calendar.YEAR), 1900, 2100, 1
        ));

        JComboBox<String> monthCombo = new JComboBox<>(new String[]{
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        });
        monthCombo.setSelectedIndex(calendar.get(Calendar.MONTH));

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Année:"));
        controlPanel.add(yearSpinner);
        controlPanel.add(new JLabel("Mois:"));
        controlPanel.add(monthCombo);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int year = (Integer) yearSpinner.getValue();
            int month = monthCombo.getSelectedIndex() + 1;
            targetButton.setText(String.format("%02d/%d", month, year));
            dialog.dispose();
        });

        dialog.add(controlPanel, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showYearPicker(JButton targetButton) {
        JDialog dialog = new JDialog(this, "Sélectionner Année", true);
        dialog.setSize(200, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        Calendar calendar = GregorianCalendar.getInstance();

        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(
                calendar.get(Calendar.YEAR), 1900, 2100, 1
        ));

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Année:"));
        controlPanel.add(yearSpinner);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int year = (Integer) yearSpinner.getValue();
            targetButton.setText(String.valueOf(year));
            dialog.dispose();
        });

        dialog.add(controlPanel, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TypeCreanceWindow window = new TypeCreanceWindow();
            window.setVisible(true);
        });
    }
}
