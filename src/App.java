import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class App {
    public static void main(String[] args) throws Exception {

        Scanner kb = new Scanner(System.in);
        int d = 0;
        do {
            int i = 0;
            Pcb[] jobs = new Pcb[30];
            try {
                File myObj = new File(
                        "testdata2.txt");
                Scanner myReader = new Scanner(myObj);

                while (myReader.hasNextLine()) {
                    myReader.nextLine();
                    String[] line = myReader.nextLine().trim().split(",");
                    int id = Integer.parseInt(line[0].trim());
                    int bt = Integer.parseInt(line[1].trim());
                    int mb = Integer.parseInt(line[2].trim());
                    jobs[i++] = new Pcb(id, 1, bt, mb);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            Scheduler s = new Scheduler();
            System.out.print("\nChoose an option\n"
                    + "--------------------------------------------------------------------------\n"
                    + "1) First Come First Serve\n"
                    + "2) Shortest job first\n"
                    + "3) Round Robin quantum = 3\n"
                    + "4) Round Robin quantum = 5\n"
                    + "5) Quit\n"
                    + "--------------------------------------------------------------------------\n"
                    + "Enter your option :> ");
            d = kb.nextInt();
            if (d == 1)
                s.fcfs(jobs, i);
            else if (d == 2)
                s.SJF(jobs, i);
            else if (d == 3)
                s.rr(jobs, i, 3);
            else if (d == 4)
                s.rr(jobs, i, 5);
            else if (d != 5)
                System.out.println("please enter valid input!!");

        } while (d != 5);
        System.out.println("Thanks Goodbye !");
        kb.close();
    }
}
