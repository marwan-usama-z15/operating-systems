import java.util.ArrayList;
import java.util.Arrays;

public class Compaction {

    void do_Compaction(ArrayList<partition> partition, ArrayList<process> un) {

        int free_size = 0;
        int P_size = partition.size();
        for (int i = 0; i < partition.size(); i++) {
            if (partition.get(i).getstate() == "External Fragmentation") {
                free_size = free_size + partition.get(i).getsize();
                partition.remove(i);
                i--;
            }
        }
        partition.add(new partition("Partition " + Integer.toString(P_size), free_size));
        partition.get(partition.size() - 1).setstate("External Fragmentation");

        if (un.size() != 0) {
            for (int i = 0; i < un.size(); i++) {
                if (un.get(i).getpro_size() <= partition.get(partition.size() - 1).getsize()) {
                    int empty_size = partition.get(partition.size() - 1).getsize();
                    partition.get(partition.size() - 1).setP_size(un.get(i).getpro_size());
                    partition.get(partition.size() - 1).setstate(un.get(i).getPro_name());
                    if (empty_size - un.get(i).getpro_size() != 0) {
                        partition.add(new partition("Partition " + Integer.toString(P_size + 1),
                                empty_size - un.get(i).getpro_size()));
                        un.remove(i);
                        P_size++;
                        partition.get(partition.size() - 1).setstate("External Fragmentation");
                    } else {
                        un.remove(i);
                        break;
                    }
                }
            }
        }
        for (partition currentPartition : partition) {
            System.out.println(currentPartition.toString());
        }
        if (un.size() == 0) {
            System.out.println("There is no processes unallocated");
        } else {
            System.out.println(Arrays.toString(un.toArray()));
        }
    }
}
