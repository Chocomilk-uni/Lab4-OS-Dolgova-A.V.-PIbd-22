package Visualization;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FileOptions extends JDialog {
    private String fileName;
    private int fileSize;

    public FileOptions(JFrame owner, boolean isDirectory) {
        super(owner, true);
        setLayout(null);
        setBounds(0, 0, 400, 300);
        setResizable(false);
        setName("Укажите атрибуты файла");

        JLabel labelFileName = new JLabel("Введите имя файла:");
        JLabel labelFileSize = new JLabel("Введите размер файла:");
        labelFileName.setBounds(20, 30, 150, 20);
        labelFileSize.setBounds(20, 80, 160, 20);

        JTextField fieldName = new JTextField("default_name");
        JTextField fieldSize = new JTextField("1");
        fieldName.setBounds(180, 30, 150, 20);
        fieldSize.setBounds(190, 80, 40, 20);

        add(labelFileName);
        add(fieldName);

        if (!isDirectory) {
            add(labelFileSize);
            add(fieldSize);
        }

        JButton applyChanges = new JButton("Принять изменения");
        applyChanges.setBounds(120, 200, 150, 40);
        add(applyChanges);

        applyChanges.addActionListener(e -> {
            fileName = fieldName.getText();
            fileSize = Integer.parseInt(fieldSize.getText());
            dispose();
        });
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                fileName = fieldName.getText();
                fileSize = Integer.parseInt(fieldSize.getText());
            }
        });
    }

    public String getFileName() {
        return fileName;
    }

    public int getFileSize() {
        return fileSize;
    }
}