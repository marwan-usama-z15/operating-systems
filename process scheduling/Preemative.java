import java.util.ArrayList;

class preemptive {

    String Name;
    int arrivalTime;
    int exTime;
    int exTime2;
    int priority;
    int endTime = 0;
    int waitingTime = 0;
    int turnAround = 0;

    public preemptive() {
    }

    public preemptive(INPUTS arr[], int num) {
        preemptive_fun(arr, num);
    }

    public static void preemptive_fun(INPUTS arr[], int num) {
        // multi level feed back queues
        ArrayList<ArrayList<preemptive>> queues = new ArrayList<>();
        int numOfProcesses = num;
        ArrayList<preemptive> output = new ArrayList<>();
        int timeSlot = 0;
        int L = 0;
        while (numOfProcesses != 0) {// execute till no processes_P1 left
            if (L < num) {
                preemptive p = new preemptive();
                p.Name = arr[L].process_name;
                p.arrivalTime = arr[L].at;
                p.exTime = arr[L].burst;
                p.exTime2 = p.exTime;
                p.priority = arr[L].priorty - 1;
                while (queues.size() < p.priority + 1) {
                    queues.add(new ArrayList<preemptive>()); // create queues
                }
                queues.get(p.priority).add(p);
                L++;
            }
            int found = 0;
            int deleted = 0;
            for (int i = 0; i < queues.size(); i++) {
                if (found == 1)
                    continue;
                for (int j = 0; j < queues.get(i).size(); j++) {
                    if (found == 1)
                        continue;
                    if (queues.get(i).get(j).arrivalTime <= timeSlot) {
                        found = 1;// not exc more than 1 in same time slot
                        queues.get(i).get(j).exTime--;
                        if (queues.get(i).get(j).exTime == 0) {
                            queues.get(i).get(j).endTime = timeSlot + 1;
                            queues.get(i).get(j).turnAround = queues.get(i).get(j).endTime
                                    - queues.get(i).get(j).arrivalTime;
                            queues.get(i).get(j).waitingTime = queues.get(i).get(j).turnAround
                                    - queues.get(i).get(j).exTime2;
                            output.add(queues.get(i).get(j));
                            queues.get(i).remove(j);
                            numOfProcesses--;
                            deleted = 1;
                        }
                        if (i + 1 < queues.size() && deleted != 1) {
                            queues.get(i + 1).add(queues.get(i).get(j));
                            queues.get(i).remove(queues.get(i).get(j));
                        }

                    }
                }
            }

            timeSlot++;

        }

        System.out.println("processes execution order : ");
        for (int j = 0; j < output.size(); j++) {
            System.out.print(output.get(j).Name + " ");
        }
        System.out.println();
        System.out.println("Waiting Time for each process : ");
        for (int j = 0; j < output.size(); j++) {
            System.out.print(output.get(j).Name + " : " + output.get(j).waitingTime + " ");
        }
        System.out.println();
        System.out.println("Turnaround Time for each process : ");
        for (int j = 0; j < output.size(); j++) {
            System.out.print(output.get(j).Name + " : " + output.get(j).turnAround + " ");
        }
        System.out.println();

        System.out.print("Average Waiting Time : ");
        float total = 0;
        for (int j = 0; j < output.size(); j++) {
            total += output.get(j).waitingTime;
        }

        System.out.println(total / output.size());

        System.out.print("Average Turnaround Time : ");
        float totalTurnAround = 0;
        for (int j = 0; j < output.size(); j++) {
            totalTurnAround += output.get(j).turnAround;
        }
        System.out.println(totalTurnAround / output.size());
    }
}