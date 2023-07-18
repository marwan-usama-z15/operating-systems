
//class for the processes contain process number and the its arrival time and its burst time 
class processes {
    int context_switch = 0;
    int processes_number;
    int arival_time;
    int burst_time;

    public processes(int number_of_process, int burst, int arrival) {
        processes_number = number_of_process;
        burst_time = burst;
        arival_time = arrival;
    }

    void setCONTEXTSWITCH(int c) {
        context_switch = context_switch + c;
    }

    void setNUM(int num) {
        processes_number = num;
    }

    void setBURST(int burs) {
        burst_time = burs;
    }

    void setARRIVAL(int arr) {
        arival_time = arr;
    }
}

public class SJF {
    static void calculate_waiting_time(processes p[], int number_of_processes, int waiting_time[], int context_switch) {
        System.out.println("process    time");
        int condition_for_context_switch = 0;
        int previous = -1;
        int context = 0;
        ////////////////////////////////////////////////////////////////////////
        int running_time[] = new int[number_of_processes];// array will contain burst time of each process
        for (int j = 0; j < number_of_processes; j++) {
            running_time[j] = p[j].burst_time;
        }
        ////////////////////////////////////////////////////////////////////////
        int finish = 0;// to check if all processes completed or not
        int time = 0;// time at which one process finish
        int minmum = Integer.MAX_VALUE;// to store time of minimum process(give minimun huge number at first)
        int shortest_process = 0;// to store index of shortest process form (running_time) array
        int finish_time;
        boolean checking = false;// checking if there is process in the specific time or not
        ////////////////////////////////////////////////////////////////////////
        while (finish != number_of_processes) {

            for (int z = 0; z < number_of_processes; z++) {
                // if condition to choose which process arrives at current time (time)
                // which have the minimum running time and its running time is greater than zer0
                if ((p[z].arival_time <= time) && (running_time[z] < minmum) && (running_time[z] > 0)) {
                    minmum = running_time[z];// store minmum time process in (minmum) at (time#variable#)
                    shortest_process = z;// the shortest process at (shortest) at (time#variable#)
                    checking = true;// true so there is at least one process need to run at specific time
                }

            }
            ////////////////////////////////////////////////////////////////////
            // if condition to checking if no process at a specific time want to run it will
            // increase time by one and then return to the previous loop to chech if there
            //////////////////////////////////////////////////////////////////// is
            // another processes want to run at the next time
            if (checking == false) {
                time++;
                continue;
            }
            if (checking == true)// if condithin in order if there is process checks if there is a context switch
            {
                if ((shortest_process != previous) && (condition_for_context_switch == 1)) {
                    System.out.println("context switch from  " + time + "->" + (time + context_switch));
                    time = time + context_switch;
                    p[0].setCONTEXTSWITCH(context_switch);
                    context++;
                }
                condition_for_context_switch = 1;// variable prevent any context switch occures before first process
                                                 // execution
            }
            previous = shortest_process;
            System.out.println("p" + (shortest_process + 1) + "         " + time + "->" + (time + 1));
            ////////////////////////////////////////////////////////////////////
            running_time[shortest_process]--;// decrease the shortest runnig process by one as it is processing
            ////////////////////////////////////////////////////////////////////
            minmum = running_time[shortest_process];
            if (minmum == 0)// if condition to check if whole process finished or not
            {
                minmum = Integer.MAX_VALUE;// if whole process finished it will give minmum to huge number
                                           // untill the next minmum running time process store its time at it
            }
            ////////////////////////////////////////////////////////////////////
            if (running_time[shortest_process] == 0)// if condition to checking if the process finish its job
            { // or not
                finish++;// increaseing (finish) by one as a process is finished
                checking = false;// return checking to false to see if in next time there will be process to
                                 // to execute or not

                finish_time = time + 1;// as a process fnished it increaseing (finish_time) by one and add (time) of
                                       // whole running processes to it to calculate waiting_time

                waiting_time[shortest_process] = finish_time - p[shortest_process].burst_time
                        - p[shortest_process].arival_time;
                // calculate and store waiting time of the process by getting whole time and
                // subtract
                // the process arival time and burst time from whole time

                if (waiting_time[shortest_process] < 0)// if calculation of waiting time for process is in negative
                { // it will give it a zero value
                    waiting_time[shortest_process] = 0;
                }
            }
            time++;// increaseing time to see if there process need to execute at the next time
        }
        System.out.println("Number of occured context switch is " + context);
        System.out.println("total time of context switch is " + p[0].context_switch);
    }

    ////////////////////////////////////////////////////////////////////////////
    static void calculate_turn_around_time(processes p[], int number_of_processes, int waiting_time[],
            int around_time_total[]) {
        for (int i = 0; i < number_of_processes; i++) {
            around_time_total[i] = p[i].burst_time + waiting_time[i];
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    static void avarage_time(processes p[], int number_of_processes, int context_switch) {
        int[] waiting_time = new int[number_of_processes];
        int[] around_time_array_total = new int[number_of_processes];
        int total_wait_time = 0;
        int total_around_time = 0;

        calculate_waiting_time(p, number_of_processes, waiting_time, context_switch);

        calculate_turn_around_time(p, number_of_processes, waiting_time, around_time_array_total);

        System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");

        for (int i = 0; i < number_of_processes; i++) {
            total_wait_time = total_wait_time + waiting_time[i];
            total_around_time = total_around_time + around_time_array_total[i];
            System.out.println(" " + p[i].processes_number + "\t\t" + p[i].burst_time + "\t\t " + waiting_time[i]
                    + "\t\t" + around_time_array_total[i]);
        }

        System.out.println("Average waiting time = " + (float) total_wait_time / (float) number_of_processes);
        System.out.println("Average turn around time = " + (float) total_around_time / (float) number_of_processes);
    }

    public SJF(INPUTS arr[], int CS, int num) {
        int number_of_processes = num;
        processes p[] = new processes[number_of_processes];
        int context_switch = CS;
        for (int i = 0; i < number_of_processes; i++) {
            int bur = arr[i].burst;
            int at = arr[i].at;
            processes newp = new processes((i + 1), bur, at);
            p[i] = newp;
        }
        avarage_time(p, p.length, context_switch);
    }
}
