import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Round_robin {
    public Round_robin(INPUTS arr[], int CS, int RR, int num) {
        int P_num = num;
        int RR_Quantum = RR;
        int Context_Switching = CS;
        String p_name[] = new String[P_num];
        int arrival[] = new int[P_num];
        int burst[] = new int[P_num];
        for (int i = 0; i < P_num; i++) {
            p_name[i] = arr[i].process_name;
            arrival[i] = arr[i].at;
            burst[i] = arr[i].burst;
        }
        int processes[] = new int[P_num];
        for (int i = 0; i < P_num; i++) {
            processes[i] = arrival[i];
        }
        int first_process = arrival[0];
        for (int i = 0; i < P_num; i++) {
            if (first_process > arrival[i]) {
                first_process = i;
            }
        }
        processes[first_process] = -1;
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(first_process);
        int Remaining[] = new int[P_num];
        for (int i = 0; i < P_num; i++) {
            Remaining[i] = burst[i];
        }
        Queue<String> P_Sequence = new LinkedList<>();
        int Turnaround[] = new int[P_num];
        int time = 0;
        int next = 0;
        while (!queue.isEmpty()) {
            int index = queue.remove(0);
            P_Sequence.add(p_name[index]);
            if (Remaining[index] <= RR_Quantum) {
                time += (Remaining[index] + Context_Switching);
                Remaining[index] = 0;
                Turnaround[index] = time;
            }
            if (Remaining[index] > RR_Quantum) {
                time += (RR_Quantum + Context_Switching);
                Remaining[index] -= RR_Quantum;
            }

            for (int i = 0; i < P_num; i++) {
                if (processes[i] <= time && processes[i] != -1) {
                    next = i;
                    queue.add(next);
                    processes[i] = -1;
                }
            }

            if (Remaining[index] > 0) {
                queue.add(index);
            }

        }
        float Turnaround_num = 0;
        float Wait_num = 0;
        for (int i = 0; i < P_num; i++) {
            Turnaround[i] -= arrival[i];
            Turnaround_num += Turnaround[i];
        }
        int wait[] = new int[P_num];
        for (int i = 0; i < P_num; i++) {
            wait[i] = Turnaround[i] - burst[i];
            Wait_num += wait[i];
        }
        System.out.println();
        System.out.println("sequence of execution of processes:" + Arrays.toString(P_Sequence.toArray()));
        System.out.println("Waiting time of processes:" + Arrays.toString(wait));
        System.out.println("Turnaround time of processes:" + Arrays.toString(Turnaround));
        System.out.print("Average waiting time of processes:");
        System.out.printf("%.2f", Wait_num / P_num);
        System.out.println();
        System.out.print("Average turnaround time of processes:");
        System.out.printf("%.2f", Turnaround_num / P_num);
    }

}
