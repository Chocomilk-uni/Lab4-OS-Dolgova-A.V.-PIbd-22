package FileManagement;

public class File {

    private final int size;
    private final String name;
    /*
    Для получения списка кластеров, принадлежащих файлу, достаточно хранить только адрес 1-го элемента св. списка
     */
    private final MemoryCluster cluster;

    public File(String name, int size, MemoryCluster cluster) {
        this.name = name;
        this.size = size;
        this.cluster = cluster;
    }

    public int getSize() {
        return size;
    }

    public MemoryCluster getCluster() {
        return cluster;
    }

    public String toString() {
        return name;
    }
}