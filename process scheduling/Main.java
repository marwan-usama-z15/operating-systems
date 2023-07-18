import java.util.Scanner;

/*mohamed ehab yahia 20205030
 * marwan osama ghareeb 20205044
 * youssef gamal riad 20205028
 * ahmed osama ahmed 20205018
 * omar khaled mohamed 20205015
 */
class INPUTS {
    String process_name = "";
    int at = 0;
    int burst = 0;
    int priorty = 0;
    int quantam = 0;

}

public class Main extends AG_class {
    public Main(INPUTS[] inp, int no) {
        super(inp, no);
    }

    public static void main(String[] args) {
        System.out.print("enter number of process :");
        Scanner IN = new Scanner(System.in);
        int num = IN.nextInt();
        System.out.print("enter context switching time :");
        int CS = IN.nextInt();
        System.out.print("enter round robin quantum time :");
        int RR = IN.nextInt();
        INPUTS Arr[] = new INPUTS[num];
        for (int i = 0; i < num; i++) {
            Arr[i] = new INPUTS();
            System.out.print("Process name : ");
            String name = IN.next();
            Arr[i].process_name = name;
            System.out.print("Arrival time : ");
            int arrive = IN.nextInt();
            Arr[i].at = arrive;
            System.out.print("Burst time : ");
            int bur = IN.nextInt();
            Arr[i].burst = bur;
            System.out.print("Priority : ");
            int prior = IN.nextInt();
            Arr[i].priorty = prior;
            System.out.print("Quantum : ");
            int Q = IN.nextInt();
            Arr[i].quantam = Q;
        }
        IN.close();
        System.out.println("\nAG method");
        AG_class m = new AG_class(Arr, num);
        System.out.println("\n\n\nRound Robin method");
        Round_robin y = new Round_robin(Arr, CS, RR, num);
        System.out.println("\n\n\nShortest job first method");
        SJF o = new SJF(Arr, CS, num);
        System.out.println("\n\n\nPreemptive method");
        preemptive a = new preemptive(Arr, num);
        System.out.println("\n\n\n");
    }

}
