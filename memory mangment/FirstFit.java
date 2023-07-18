import java.util.ArrayList;
import java.util.Collections;

public class FirstFit {
    static int index;
    static int newsiz;

    ArrayList<partition> first_fit(ArrayList<process> process, ArrayList<partition> copy,
            ArrayList<process> notallocate) {
        for (int j = 0; j < process.size(); j++) {
            index = -1;
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i).getstate() == "empty" && copy.get(i).getsize() >= process.get(j).getpro_size()) {
                    {
                        index = i;

                        break;
                    }

                }
            }
            if (index == -1) {
                System.out.println(process.get(j).getPro_name() + " can't be allocated ");
                notallocate.add(process.get(j));
                continue;
            }
            copy.get(index).setstate(process.get(j).getPro_name());

            if (process.get(j).getpro_size() < copy.get(index).getsize()) {
                newsiz = copy.get(index).getsize() - process.get(j).getpro_size();
                copy.add(index + 1, new partition("partition" +
                        String.valueOf(copy.size()), newsiz));
            }
            copy.get(index).setP_size(process.get(j).getpro_size());

        }
        for (int y = 0; y < copy.size(); y++) {
            if (copy.get(y).getstate() == "empty") {
                copy.get(y).setstate("External Fragmentation");
            }
        }

        for (partition currentPartition : copy) {
            System.out.println(currentPartition.toString());
        }
        return copy;
    }
}
