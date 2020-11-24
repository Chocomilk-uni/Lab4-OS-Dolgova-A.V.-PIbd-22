package FileManagement;

public class MemoryCluster {

    /*
    Первое слово каждого кластера при размещении с использованием связанного списка -
    указатель на следующий кластер файла.
     */
    private MemoryCluster nextCluster;

    /*
    Состояние кластера:
    1 - пустой,
    2 - заполненный, без выделения,
    3 - выделенный.
     */
    private int clusterState;

    private int clusterIndex;

    public MemoryCluster(int clusterState, int index) {
        this.clusterState = clusterState;
        this.clusterIndex = index;
    }

    public int getClusterState() {
        return clusterState;
    }

    public void setClusterState(int clusterState) {
        this.clusterState = clusterState;
    }

    public MemoryCluster getNextCluster() {
        return nextCluster;
    }

    public int getClusterIndex() {
        return clusterIndex;
    }

    public void setNextCluster(MemoryCluster nextCluster) {
        this.nextCluster = nextCluster;
    }
}