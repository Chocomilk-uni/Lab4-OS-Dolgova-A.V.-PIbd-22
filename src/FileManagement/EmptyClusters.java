package FileManagement;

import java.util.ArrayList;

public class EmptyClusters {
    /*
    Список пустых кластеров.
     */
    private final ArrayList<Integer> clusters;

    public EmptyClusters(HardDisk disk) {
        this.clusters = new ArrayList<>();
        for (int i = 0; i < disk.getClustersArraySize(); i++) {
            if (disk.getCluster(i).getClusterState() == 1) {
                clusters.add(i);
            }
        }
    }

    public ArrayList<Integer> getClusters() {
        return clusters;
    }

    public void addEmptyClusters(int index) {
        clusters.add(index);
    }

    /*
    При создании файла выделенные под него кластеры удаляются из списка пустых кластеров.
     */
    public void removeEmptyClusters(int index) {
        clusters.remove(index);
    }
}