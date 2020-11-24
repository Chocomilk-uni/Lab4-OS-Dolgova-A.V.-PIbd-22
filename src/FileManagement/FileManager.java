package FileManagement;

public class FileManager {

    private final File rootFile;
    private final HardDisk disk;
    private final EmptyClusters emptyClustersList;

    public FileManager(HardDisk disk) {
        this.disk = disk;
        emptyClustersList = new EmptyClusters(disk);
        rootFile = new File("Root", 1, selectMemory(0));
    }

    public File getRootFile() {
        return rootFile;
    }

    public File addFile(String fileName, int size) {
        MemoryCluster cluster = selectMemory(size);
        if (cluster == null) {
            return null;
        }
        return new File(fileName, size, cluster);
    }

    public File addDirectory(String fileName) {
        MemoryCluster cluster = selectMemory(0);
        if (cluster == null) {
            return null;
        }
        return new File(fileName, 1, cluster);
    }

    public void deleteFile(File file) {
        MemoryCluster previousCluster = null;
        MemoryCluster cluster = file.getCluster();
        while (cluster != null) {
            cluster.setClusterState(1);
            if (previousCluster != null) {
                previousCluster.setNextCluster(null);
            }
            emptyClustersList.addEmptyClusters(cluster.getClusterIndex());
            previousCluster = cluster;
            cluster = cluster.getNextCluster();
        }
    }

    public MemoryCluster selectMemory(int size) {
        size = size / disk.getClusterSize();
        MemoryCluster firstCluster = null;
        if ((size + 1) > emptyClustersList.getClusters().size()) {
            return null;
        }
        MemoryCluster previousCluster = null;
        for (int i = 0; i < size + 1; i++) {
            int index = (int) (Math.random() * emptyClustersList.getClusters().size());
            MemoryCluster buffer = disk.getCluster(emptyClustersList.getClusters().get(index));
            buffer.setClusterState(2);
            emptyClustersList.removeEmptyClusters(index);
            if (previousCluster != null) {
                previousCluster.setNextCluster(buffer);
            } else {
                firstCluster = buffer;
            }
            previousCluster = buffer;
        }
        return firstCluster;
    }

    public void selectFile(MemoryCluster cluster) {
        while (cluster != null) {
            cluster.setClusterState(3);
            cluster = cluster.getNextCluster();
        }
    }

    public void undoSelection() {
        for (int i = 0; i < disk.getClustersArraySize(); i++) {
            if (disk.getCluster(i).getClusterState() == 3) {
                disk.getCluster(i).setClusterState(2);
            }
        }
    }
}