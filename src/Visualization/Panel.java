package Visualization;

import FileManagement.HardDisk;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private HardDisk disk;
    private final int CLUSTER_SIZE = 20;
    private final int START_POSITION = 220;

    public void paint(Graphics g) {
        if (disk != null) {
            super.paint(g);
            int x = START_POSITION;
            int y = START_POSITION / 4;

            for (int i = 0; i < disk.getMemorySize(); i++) {
                if (x + CLUSTER_SIZE >= getWidth()) {
                    x = START_POSITION;
                    y += CLUSTER_SIZE;
                }
                switch (disk.getCluster(i).getClusterState()) {
                    case 1:
                        g.setColor(Color.GRAY);
                        break;
                    case 2:
                        g.setColor(Color.BLUE);
                        break;
                    case 3:
                        g.setColor(Color.RED);
                        break;
                }
                g.fillRect(x, y, CLUSTER_SIZE, CLUSTER_SIZE);
                g.setColor(Color.black);
                g.drawRect(x, y, CLUSTER_SIZE, CLUSTER_SIZE);
                x += CLUSTER_SIZE;
            }
        }
    }

    public void initDisk(HardDisk disk) {
        this.disk = disk;
    }
}