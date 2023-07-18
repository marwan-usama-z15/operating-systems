import java.util.ArrayList;

public class BestFit {
    ArrayList<partition> partitions;
    ArrayList<process> processes;
    ArrayList<process> unAllocatedProcesses;

    public BestFit(ArrayList<partition> partition, ArrayList<process> processes,
            ArrayList<process> unalloc) {
        this.partitions = partition;
        this.processes = processes;
        this.unAllocatedProcesses = unalloc;
    }

    ArrayList<partition> startBestFit() {

        for (int j = 0; j < processes.size(); j++) {
            process currentProcess = processes.get(j);
            int processSize = currentProcess.getpro_size();
            int bestSize = (int) 1e9;
            int bestIndex = (int) 1e9;
            for (int i = 0; i < partitions.size(); i++) {
                if (processSize <= partitions.get(i).getsize() && partitions.get(i).getsize() < bestSize
                        && partitions.get(i).getstate().equals("empty")) {
                    bestIndex = i;
                    bestSize = partitions.get(i).getsize();
                }
            }

            if (bestIndex == (int) 1e9) {
                unAllocatedProcesses.add(processes.get(j));
                continue;
            }

            partition chosenPartition = partitions.get((int) bestIndex);
            if (chosenPartition.getsize() - currentProcess.getpro_size() != 0)
                partitions.add(bestIndex + 1, new partition("Partition " +
                        Integer.toString(partitions.size()),
                        chosenPartition.getsize() - currentProcess.getpro_size()));
            chosenPartition.setP_size(currentProcess.getpro_size());
            chosenPartition.setstate("Process " + (j + 1));

        }

        for (partition currentPartition : partitions) {
            if (currentPartition.getstate().equals("empty"))
                currentPartition.setstate("External Fragmentation");
            System.out.println(currentPartition.toString());
        }
        if (unAllocatedProcesses.size() != 0) {
            for (process prc : unAllocatedProcesses) {
                System.out.print(prc.getPro_name() + " ");
            }
            System.out.println("cannot be allocated");
        }

        return partitions;
    }

}
