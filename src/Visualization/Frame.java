package Visualization;

import FileManagement.File;
import FileManagement.FileManager;
import FileManagement.HardDisk;
import FileManagement.MemoryCluster;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class Frame {
    private JFrame frame;
    private JButton btnCreateFile;
    private JButton btnCreateDirectory;
    private JButton btnDelete;
    private JButton btnCopy;
    private JButton btnPaste;
    private JButton btnMove;
    private JButton btnRun;
    private JTextField fieldDiskSize;
    private JTextField fieldClusterSize;

    private Panel panel;

    private int memorySize;
    private int clusterSize;

    private HardDisk disk;
    private JTree fileTree;
    private FileManager fileManager;
    private DefaultMutableTreeNode currentNode;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode buffer;

    public Frame() {
        initialization();
    }

    public void initialization() {
        panel = new Panel();
        panel.setBounds(0, 0, 900, 430);
        frame = new JFrame("Имитация файловой системы");
        frame.setBounds(0, 0, 1100, 580);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

        btnRun = new JButton("Запуск");
        btnRun.setBounds(550, 450, 100, 50);
        btnRun.addActionListener(e -> {
            btnCreateFile.setEnabled(true);
            btnCreateDirectory.setEnabled(true);
            btnDelete.setEnabled(true);
            btnCopy.setEnabled(true);
            btnPaste.setEnabled(true);
            btnMove.setEnabled(true);
            memorySize = Integer.parseInt(fieldDiskSize.getText());
            clusterSize = Integer.parseInt(fieldClusterSize.getText());
            disk = new HardDisk(memorySize, clusterSize);
            fileManager = new FileManager(disk);
            btnRun.setEnabled(false);
            fieldDiskSize.setEnabled(false);
            fieldClusterSize.setEnabled(false);
            panel.initDisk(disk);
            panel.setLayout(null);
            root = new DefaultMutableTreeNode(fileManager.getRootFile(), true);
            fileTree = new JTree(root);
            fileTree.updateUI();
            fileTree.setBounds(0, 50, 170, 320);
            fileTree.addTreeSelectionListener(e1 -> {
                currentNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
                fileManager.undoSelection();
                selectFile(currentNode);
            });
            panel.add(fileTree);
            frame.repaint();
        });

        btnCreateFile = new JButton("Создать файл");
        btnCreateFile.setBounds(900, 50, 160, 30);
        btnCreateFile.addActionListener(e -> {
            if (currentNode == null || !currentNode.getAllowsChildren()) {
                JOptionPane.showMessageDialog(frame, "Перед созданием файла необходимо указать каталог, в котором он будет создан.");
                return;
            }
            FileOptions fileOptions = new FileOptions(frame, false);
            String fileName = fileOptions.getFileName();
            int fileSize = fileOptions.getFileSize();
            if (!currentNode.getAllowsChildren()) {
                JOptionPane.showMessageDialog(frame, "Действие неккоректно: добавить новые файлы можно только в каталог.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            File file = fileManager.addFile(fileName, fileSize);
            if (file != null) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(file, false);
                currentNode.add(node);
                fileTree.updateUI();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Невозможно выполнить действие: на устройстве хранения недостаточно свободного места. Удалите существующие файлы и попробуйте снова.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCreateDirectory = new JButton("Создать каталог");
        btnCreateDirectory.setBounds(900, 130, 160, 30);
        btnCreateDirectory.addActionListener(e -> {
            if (currentNode == null) {
                JOptionPane.showMessageDialog(frame, "Перед созданием каталога необходимо указать каталог, в котором он будет создан.");
                return;
            }
            else if (!currentNode.getAllowsChildren()) {
                JOptionPane.showMessageDialog(frame, "Действие неккоректно: добавить новые файлы можно только в каталог.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            FileOptions fileOptions = new FileOptions(frame, true);
            String fileName = fileOptions.getFileName();
            File folder = fileManager.addDirectory(fileName);
            if (folder != null) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(folder, true);
                currentNode.add(node);
                fileTree.updateUI();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Невозможно выполнить действие: на устройстве хранения недостаточно свободного места. Удалите существующие файлы и попробуйте снова.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete = new JButton("Удалить");
        btnDelete.setBounds(900, 210, 160, 30);
        btnDelete.addActionListener(e -> {
            deleteFile(currentNode);
            frame.repaint();
        });

        btnPaste = new JButton("Вставить");
        btnPaste.setBounds(900, 370, 160, 30);
        btnPaste.addActionListener(e -> {
            pasteFile(currentNode);
            frame.repaint();
        });

        btnCopy = new JButton("Скопировать");
        btnCopy.setBounds(900, 290, 160, 30);
        btnCopy.addActionListener(e -> {
            copyFile(currentNode);
            frame.repaint();
        });

        btnMove = new JButton("Переместить");
        btnMove.setBounds(900, 450, 160, 30);
        btnMove.addActionListener(e -> {
            if (currentNode != null && currentNode.getParent() != null) {
                DefaultMutableTreeNode buffer = currentNode;
                ((DefaultMutableTreeNode) currentNode.getParent()).remove(currentNode);
                MoveOptions moveSelectionWindow = new MoveOptions(frame, root);
                DefaultMutableTreeNode parent = moveSelectionWindow.getNode();
                if (parent != null) {
                    parent.add(buffer);
                }
            }
            fileTree.updateUI();
            frame.repaint();
        });

        JLabel labelDiskSize = new JLabel("Объем памяти на устройстве хранения:");
        labelDiskSize.setBounds(10, 450, 240, 20);
        fieldDiskSize = new JTextField("9248");
        fieldDiskSize.setBounds(260, 450, 70, 20);

        JLabel labelClusterSize = new JLabel("Размер кластера:");
        labelClusterSize.setBounds(10, 480, 110, 20);
        fieldClusterSize = new JTextField("16");
        fieldClusterSize.setBounds(120, 480, 70, 20);

        btnCreateFile.setEnabled(false);
        btnCreateDirectory.setEnabled(false);
        btnDelete.setEnabled(false);
        btnCopy.setEnabled(false);
        btnPaste.setEnabled(false);
        btnMove.setEnabled(false);

        frame.getContentPane().add(panel);
        frame.getContentPane().add(btnCreateFile);
        frame.getContentPane().add(btnCreateDirectory);
        frame.getContentPane().add(btnDelete);
        frame.getContentPane().add(btnCopy);
        frame.getContentPane().add(btnPaste);
        frame.getContentPane().add(btnMove);
        frame.getContentPane().add(labelDiskSize);
        frame.getContentPane().add(fieldDiskSize);
        frame.getContentPane().add(fieldClusterSize);
        frame.getContentPane().add(labelClusterSize);
        frame.getContentPane().add(btnRun);
        frame.repaint();
    }

    private void selectFile(DefaultMutableTreeNode node) {
        if (node.getUserObject() != null) {
            File file = (File) node.getUserObject();
            MemoryCluster cluster = file.getCluster();
            if (cluster != null) {
                fileManager.selectFile(cluster);
                frame.repaint();
            }
        }
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getChildCount(); i++) {
                selectFile((DefaultMutableTreeNode) node.getChildAt(i));
            }
        }
    }

    private void deleteFile(DefaultMutableTreeNode node) {
        while (!node.isLeaf()) {
            deleteFile((DefaultMutableTreeNode) node.getChildAt(0));
        }
        if (node.getUserObject() != null) {
            File file = (File) node.getUserObject();
            fileManager.deleteFile(file);
            ((DefaultMutableTreeNode) node.getParent()).remove(node);
            fileTree.updateUI();
            frame.repaint();
        }
    }

    private void copyFile(DefaultMutableTreeNode node) {
        if (node != null && node.getParent() != null) {
            buffer = node;
        }
    }

    private void pasteFile(DefaultMutableTreeNode parent) {
        if (parent.getAllowsChildren() && buffer != null) {
            parent.add(cloneFile(buffer));
            fileTree.updateUI();
        }
    }

    private DefaultMutableTreeNode cloneFile(DefaultMutableTreeNode node) {
        File file = (File) node.getUserObject();
        MemoryCluster cluster = fileManager.selectMemory(file.getSize());
        DefaultMutableTreeNode newNode;
        if (cluster != null) {
            newNode = new DefaultMutableTreeNode(new File(node.toString(), file.getSize(), cluster), node.getAllowsChildren());
            for (int i = 0; i < node.getChildCount(); i++) {
                newNode.add(cloneFile((DefaultMutableTreeNode) node.getChildAt(i)));
            }
            return newNode;
        } else {
            JOptionPane.showMessageDialog(frame, "Невозможно выполнить действие: на устройстве хранения недостаточно свободного места. Удалите существующие файлы и попробуйте снова.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}