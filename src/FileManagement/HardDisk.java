package FileManagement;

import java.util.ArrayList;

public class HardDisk {
    /*
    Для ОС данные на устройстве хранения расположены в виде набора физических блоков фиксированного размера - кластеров.
     */
    private final int SIZE_OF_CLUSTER;
    private final int SIZE_OF_MEMORY;
    private final ArrayList<MemoryCluster> clustersArray;

    public HardDisk(int diskSize, int clusterSize) {
        this.SIZE_OF_CLUSTER = clusterSize;
        SIZE_OF_MEMORY = diskSize / clusterSize;
        clustersArray = new ArrayList<>();
        fillClusters();
    }

    public void fillClusters() {
        for (int i = 0; i < SIZE_OF_MEMORY; i++) {
            clustersArray.add(new MemoryCluster(1, i));
        }
    }

    public int getMemorySize() {
        return SIZE_OF_MEMORY;
    }

    public MemoryCluster getCluster(int index) {
        return clustersArray.get(index);
    }

    public int getClustersArraySize() {
        return clustersArray.size();
    }

    public int getClusterSize() {
        return SIZE_OF_CLUSTER;
    }
}