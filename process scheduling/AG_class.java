import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;

class scheduling {
    private String p_name;
    private int at;
    private int burst_time;
    private int priorty;
    private int quantam;
    public int burst_time2;
    public int turnaround;

    scheduling() {
    };

    scheduling(String p_name, Integer at, Integer burst_time, Integer priorty, Integer quantam) {
        this.at = at;
        this.p_name = p_name;
        this.burst_time = burst_time;
        this.priorty = priorty;
        burst_time2 = burst_time;
        this.quantam = quantam;
    }

    public String getP_name() {
        return this.p_name;
    }

    public int getat() {
        return this.at;
    }

    public int getbursttime() {
        return this.burst_time;
    }

    public int getPriorty() {
        return this.priorty;
    }

    public int getQuantam() {
        return this.quantam;
    }

    public void setP_name(String p_namee) {
        this.p_name = p_namee;
    }

    public void setAt(int at) {
        this.at = at;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;

    }

    public void setPriorty(int priorty) {
        this.priorty = priorty;
    }

    public void setQuantam(int quantam) {
        this.quantam = quantam;
    }

}

public class AG_class {
    static int nofpro;

    public AG_class(INPUTS[] inp, int no) {
        nofpro = no;
        ArrayList<scheduling> storage = new ArrayList<>();
        for (int i = 0; i < nofpro; i++) {
            storage.add(new scheduling(inp[i].process_name, inp[i].at, inp[i].burst, inp[i].priorty, inp[i].quantam));
        }

        AG(storage, 0);
        System.out.println("Excution order of process");
        System.out.println(Arrays.toString(storage2.toArray()).replace(',', ' '));

    }

    static int avgturn;
    static String previous;
    static int avgwait;
    static int w = 0;
    static double p;
    static int T = 0;
    static int zz = 0;
    static Queue<String> storage2 = new LinkedList<>();

    static boolean isended(ArrayList<scheduling> storage) {
        zz = 0;
        for (int j = 0; j < nofpro; j++) {

            if (storage.get(j).getbursttime() != 0) {
                return false;

            }
        }
        return true;

    }

    static scheduling isprior(ArrayList<scheduling> storage) {
        scheduling x;
        x = storage.get(0);
        zz = 0;
        for (int j = 1; j < nofpro; j++) {

            if (x.getPriorty() > storage.get(j).getPriorty() && storage.get(j).getat() <= T) {
                x = storage.get(j);
                zz = j;

            }
        }
        return x;
    }

    static scheduling isshorter(ArrayList<scheduling> storage) {
        scheduling x = new scheduling();
        zz = 0;
        for (int i = 0; i < nofpro; i++) {
            if (storage.get(i).getbursttime() > 0) {
                x = storage.get(i);
                break;
            }

        }

        for (int j = 1; j < nofpro; j++) {

            if (x.getbursttime() > storage.get(j).getbursttime() && storage.get(j).getat() <= T
                    && storage.get(j).getbursttime() != 0) {
                x = storage.get(j);
                zz = j;
            }
        }
        return x;

    }

    static scheduling isarrived(ArrayList<scheduling> storage) {
        scheduling x;
        x = storage.get(0);
        zz = 0;

        for (int j = 0; j < nofpro; j++) {

            if (storage.get(j).getat() <= T && storage.get(j).getbursttime() != 0) {
                x = storage.get(j);

                zz = j;

                break;
            }
        }
        return x;
    }

    static void AG(ArrayList<scheduling> storage, int i) {
        scheduling x;
        System.out.println("Quantum update\n" + "process" + "\t" + "quantum");
        while (true) {

            int z = storage.get(i).getQuantam();
            if (storage.get(i).getbursttime() == 0) {
                storage.get(i).turnaround = T;
                storage.get(i).setQuantam(0);
                System.out.println(storage.get(i).getP_name() + "\t" + storage.get(i).getQuantam());
                storage.add(storage.remove(i));
                isarrived(storage);
                i = zz;
            }
            System.out.println(storage.get(i).getP_name() + "\t" + storage.get(i).getQuantam());

            if (isended(storage)) {
                System.out.println("time = " + T);
                System.out.println("                ");
                System.out.println("                ");
                for (int k = 0; k < nofpro; k++) {
                    System.out.println(storage.get(k).getP_name());
                    storage.get(k).turnaround = storage.get(k).turnaround - storage.get(k).getat();
                    System.out.println("Turnaround=" + storage.get(k).turnaround);
                    avgturn += storage.get(k).turnaround;
                    System.out.println("waiting time=" + (storage.get(k).turnaround - storage.get(k).burst_time2));
                    avgwait += storage.get(k).turnaround - storage.get(k).burst_time2;
                }
                System.out.println("Average turnaround time = " + (double) avgturn / nofpro);
                System.out.println("Average waiting time = " + (double) avgwait / nofpro);
                break;
            }

            if (storage.get(i).getQuantam() == 0 && storage.get(i).getbursttime() != 0) {

                storage.get(i).setQuantam(2 + w);
                System.out.println(storage.get(i).getP_name() + "\t" + storage.get(i).getQuantam());
                x = storage.remove(i);
                storage.add(x);
                isarrived(storage);
                i = zz;
                continue;
            }
            w = z;
            if (storage.get(i).getbursttime() < (int) Math.ceil(0.25 * storage.get(i).getQuantam())) {
                T += storage.get(i).getbursttime();
                storage.get(i).setBurst_time(0);
            } else {
                storage.get(i).setBurst_time(
                        storage.get(i).getbursttime() - (int) Math.ceil(0.25 * storage.get(i).getQuantam()));

                T += (int) Math.ceil(0.25 * storage.get(i).getQuantam());
                storage.get(i)
                        .setQuantam(storage.get(i).getQuantam() - (int) Math.ceil(0.25 * storage.get(i).getQuantam()));
            }

            System.out.println(storage.get(i).getP_name() + "\t" + storage.get(i).getQuantam());
            if (storage.get(i).getP_name() == previous) {
            } else {
                storage2.add(storage.get(i).getP_name());
            }
            previous = storage.get(i).getP_name();
            if (storage.get(i).getbursttime() == 0) {

                continue;
            }

            if (isprior(storage).getP_name() != storage.get(i).getP_name() && isprior(storage).getbursttime() != 0) {
                storage.get(i).setQuantam(z + (int) Math.ceil((double) storage.get(i).getQuantam() / 2));
                System.out.println(storage.get(i).getP_name() + "\t" + storage.get(i).getQuantam());
                storage.add(storage.remove(i));
                if (zz == 0) {
                    i = zz;
                } else {
                    i = zz - 1;

                }
            } else {
                T += (int) Math.ceil(0.25 * storage.get(i).getQuantam());
                storage.get(i).setBurst_time(
                        storage.get(i).getbursttime() - (int) Math.ceil(0.25 * storage.get(i).getQuantam()));
                if (storage.get(i).getbursttime() == 0) {
                    continue;
                } else {
                    storage.get(i).setQuantam(
                            storage.get(i).getQuantam() - (int) Math.ceil(0.25 * storage.get(i).getQuantam()));
                }
                System.out.println(storage.get(i).getP_name() + "\t" + storage.get(i).getQuantam());
                if (storage.get(i).getQuantam() == 0) {
                    continue;
                }
                if (isshorter(storage).getP_name() != storage.get(i).getP_name()) {
                    storage.get(i).setQuantam(z + storage.get(i).getQuantam());
                    storage.add(storage.remove(i));
                    if (zz == 0) {
                        i = zz;
                    } else {
                        i = zz - 1;

                    }

                }

                else {
                    while (true) {

                        storage.get(i).setBurst_time(storage.get(i).getbursttime() - 1);
                        storage.get(i).setQuantam(storage.get(i).getQuantam() - 1);
                        System.out.println(storage.get(i).getP_name() + "\t" + storage.get(i).getQuantam());
                        T += 1;
                        if (storage.get(i).getbursttime() == 0) {

                            break;

                        }
                        if (storage.get(i).getQuantam() == 0) {
                            break;
                        }

                        else if (isshorter(storage).getP_name() != storage.get(i).getP_name()) {

                            storage.get(i).setQuantam(z + storage.get(i).getQuantam());
                            storage.add(storage.remove(i));
                            System.out.println(storage.get(i).getP_name() + "\t" + storage.get(i).getQuantam());

                            if (zz == 0) {
                                i = zz;

                            } else {
                                i = zz - 1;

                            }
                            break;
                        }
                    }

                }
            }

        }
    }
}