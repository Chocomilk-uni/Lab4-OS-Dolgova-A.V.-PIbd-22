package Visualization;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class MoveOptions extends JDialog {

    private final JTree tree;
    private DefaultMutableTreeNode currentNode;
    private boolean applyChanges;

    public DefaultMutableTreeNode getNode() {
        if (currentNode != null && currentNode.getAllowsChildren() && applyChanges) {
            return currentNode;
        }
        return null;
    }

    public MoveOptions(JFrame owner, DefaultMutableTreeNode root) {
        super(owner, true);
        setLayout(null);
        tree = new JTree(root);
        setBounds(0, 0, 300, 500);
        setResizable(false);
        setName("Выберите каталог для перемещения");
        tree.setBounds(0, 0, 300, 300);
        tree.addTreeSelectionListener(e1 -> currentNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
        add(tree);

        JButton acceptButton = new JButton("Принять изменения");
        acceptButton.setBounds(0, 320, 300, 30);
        acceptButton.addActionListener(e -> {
            applyChanges = true;
            dispose();
        });
        add(acceptButton);

        JButton cancelButton = new JButton("Закрыть");
        cancelButton.setBounds(0, 360, 300, 30);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
        tree.updateUI();
        repaint();
        setVisible(true);
    }
}