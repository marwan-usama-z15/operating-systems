import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class partition {
    private String partition_name;
    private int size;
    private String state = "empty";

    partition(String partition_name, int size) {
        this.partition_name = partition_name;
        this.size = size;
    }

    public String getP_name() {
        return this.partition_name;
    }

    public int getsize() {
        return this.size;
    }

    public String getstate() {
        return this.state;
    }

    public void setP_name(String p_namee) {
        this.partition_name = p_namee;
    }

    public void setP_size(int size) {
        this.size = size;
    }

    public void setstate(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.partition_name + " (" + this.size + ") =>" + this.state;
    }
}

class process {
    private String process_name;
    private int pro_size;

    process(String process_name, int pro_size) {
        this.process_name = process_name;
        this.pro_size = pro_size;

    }

    public String getPro_name() {
        return this.process_name;
    }

    public int getpro_size() {
        return this.pro_size;
    }

    public void setPro_name(String p_namee) {
        this.process_name = p_namee;
    }

    public void setP_size(int size) {
        this.pro_size = size;
    }

}

public class Main {
    static ArrayList<process> notallocate = new ArrayList<>();
    static ArrayList<process> process = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<partition> memory = new ArrayList<partition>();
        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter number of paritions : ");
        int Num = myObj.nextInt();
        for (int i = 0; i < Num; i++) {
            Scanner myObj1 = new Scanner(System.in);
            System.out.println("enter partition name");
            String part1 = myObj1.nextLine();
            System.out.println("enter partition size");
            int s = myObj1.nextInt();
            memory.add(new partition(part1, s));
        }
        ArrayList<partition> copy = new ArrayList<partition>();
        ArrayList<partition> copy2 = new ArrayList<partition>();
        ArrayList<partition> copy3 = new ArrayList<partition>();
        Iterator<partition> it = memory.iterator();
        while (it.hasNext()) {
            partition s = it.next();
            partition newS = new partition(s.getP_name(), s.getsize());
            copy.add(newS);
        }
        Iterator<partition> it1 = memory.iterator();
        while (it1.hasNext()) {
            partition s1 = it1.next();
            partition newS1 = new partition(s1.getP_name(), s1.getsize());
            copy2.add(newS1);
        }
        Iterator<partition> it2 = memory.iterator();
        while (it2.hasNext()) {
            partition s2 = it2.next();
            partition newS2 = new partition(s2.getP_name(), s2.getsize());
            copy3.add(newS2);
        }
        System.out.print("Enter number of processes : ");
        int NUm_P = myObj.nextInt();
        for (int i = 0; i < NUm_P; i++) {
            Scanner myObj2 = new Scanner(System.in);
            System.out.println("enter process name");
            String procn = myObj2.nextLine();
            System.out.println("enter process size");
            int pros = myObj2.nextInt();
            process.add(new process(procn, pros));

        }

        System.out.println("Select the policy you want to apply:\n1. First fit\n2. Worst fit\n3. Best fit\n4. Exit");
        while (true) {
            System.out.print("Select Policy:");
            Scanner myObj3 = new Scanner(System.in);
            Scanner myObj4 = new Scanner(System.in);
            int Policy = myObj3.nextInt();
            int comp;
            switch (Policy) {
                case 1:
                    FirstFit OBJ = new FirstFit();
                    notallocate = new ArrayList<>();
                    ArrayList<partition> F = OBJ.first_fit(process, copy, notallocate);
                    System.out.println("Do you want to compact? 1.yes 2.no");
                    comp = myObj4.nextInt();
                    if (comp == 1) {
                        Compaction C = new Compaction();
                        C.do_Compaction(F, notallocate);
                    }
                    break;
                case 2:
                    notallocate = new ArrayList<>();
                    WorstFit OBJ1 = new WorstFit(copy2, process, notallocate);
                    ArrayList<partition> W = OBJ1.startworstFit();
                    System.out.println("Do you want to compact? 1.yes 2.no");
                    comp = myObj4.nextInt();
                    if (comp == 1) {
                        Compaction C = new Compaction();
                        C.do_Compaction(W, notallocate);
                    }
                    break;
                case 3:
                    notallocate = new ArrayList<>();
                    BestFit OBJ2 = new BestFit(copy3, process, notallocate);
                    ArrayList<partition> B = OBJ2.startBestFit();
                    System.out.println("Do you want to compact? 1.yes 2.no");
                    comp = myObj4.nextInt();
                    if (comp == 1) {
                        Compaction C = new Compaction();
                        C.do_Compaction(B, notallocate);
                    }
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Please enter a right number");
                    break;
            }
        }
    }
}