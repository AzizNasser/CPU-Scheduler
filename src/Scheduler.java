
public class Scheduler {
    private Pcb[] queue = new Pcb[30]; // ready queue
    private Pcb[] wqueue = new Pcb[30]; // waiting queue
    int numj; // number of jobs
    int wq; // number of waiting queue
    int tbt = 0; // total burst time
    int memory = 0; // memory used
    double wt = 0; // total waiting time
    double ta = 0; // total turnaround time

    // this method return whether meomry is full or not
    public boolean memoryChick(Pcb p) {
        int temp = memory;
        temp += p.MB;
        if (temp <= 8192)
            memory += p.MB;
        if (memory <= 8192)
            return true;
        else
            return false;
    }

    // this method add jobs to the ready queue if its full add to waiting queue
    private void addQ(Pcb[] array, int size) {
        for (int i = 0; i < size; i++) {
            if (memoryChick(array[i])) {
                array[i].PS = 2;
                tbt += array[i].BT;
                queue[numj++] = array[i];
            } else {
                array[i].PS = 4;
                wqueue[wq++] = array[i];
            }
        }

    }

    // this method add jobs from the waiting queue to the ready queue (by changing
    // the state only)
    private void wQCheck() {
        for (int j = 0; j < wq; j++) {
            if (memoryChick(wqueue[j]) && wqueue[j].PS == 4) {
                wqueue[j].PS = 2;
                tbt += wqueue[j].BT;
                queue[numj++] = wqueue[j];
            } else
                break;
        }
    }

    // new = 1, ready = 2, running = 3, waiting = 4, terminated = 5
    // this method processes the jobs in First Come First Serve
    public void fcfs(Pcb[] array, int size) {
        addQ(array, size);
        int count = 0;
        for (int i = 0; i < numj; i++) {
            wQCheck();
            queue[i].PS = 3;
            System.out.println("job" + queue[i].PID + " start at time " + count);
            while (queue[i].BT != 0) {
                System.out.print("job " + queue[i].PID + ", ");
                queue[i].BT--;
                for (int j = 0 + i; j < numj; j++)
                    if (queue[j].PS != 3)
                        wt++;
                count++;

            }
            System.out.println();

            queue[i].PS = 5;
            memory -= queue[i].MB;
            System.out.println("job" + queue[i].PID + " end at time " + count);
        }
        // display average waiting time
        double Avgwt = wt / numj;
        System.out.println("Avarage waiting time is: " + Avgwt);
        // display average turnarround time
        ta = (tbt + wt) / numj;
        System.out.println("Avarage completion time is: " + ta);

    }

    public void rr(Pcb[] array, int size, int q) {
        addQ(array, size);
        int count = 0;
        int i = 0;
        int temp = tbt;
        while (temp != 0) {
            wQCheck();

            int qt = 0;
            if (queue[i].PS == 2) {
                queue[i].PS = 3;
                System.out.println("job" + queue[i].PID + " start at time " + count);
            }

            while (queue[i].BT != 0 && qt < q) {
                System.out.print("job " + queue[i].PID + ", ");
                queue[i].BT--;
                temp--;
                for (int j = 0; j < numj; j++)
                    if (queue[j].PS == 2)
                        wt++;

                qt++;
                count++;

            }
            if (queue[i].BT == 0 && queue[i].PS == 3) {
                queue[i].PS = 5;
                memory -= queue[i].MB;
                System.out.println();
                System.out.println("job" + queue[i].PID + " finished at time " + count);
            } else if (queue[i].PS == 3) {
                queue[i].PS = 2;
                System.out.println();
                System.out.println("job" + queue[i].PID + " end at time " + count);
            }

            i = (i + 1) % numj;

        }

        // display average waiting time
        double Avgwt = wt / numj;
        System.out.println("Avarage waiting time is: " + Avgwt);
        // display average turnarround time
        ta = (tbt + wt) / numj;
        System.out.println("Avarage completion time is: " + ta);

    }

    // sort the jobs bsaed on burst time and then will work like fcfs
    public void SJF(Pcb[] array, int size) {

        for (int i = 0; i < size; i++) {
            // Inner nested loop pointing 1 index ahead
            for (int j = i + 1; j < size; j++) {
                // Checking elements
                Pcb temp;
                if (array[j].BT < array[i].BT) {
                    // Swapping
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        fcfs(array, size);

    }

}
