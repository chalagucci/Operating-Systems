import java.io.File;
import java.util.Scanner;

public class BankerAlgorithm {
    protected int numOfProcess;
    protected int[] resources;
    protected int[][] max;
    protected int[][] currentAllocation;

    public BankerAlgorithm() {
    }

    public BankerAlgorithm(int numOfProcess, int[] resources, int[][] max, int[][] currentAllocation) {
        this.numOfProcess = numOfProcess;
        this.resources = resources;
        this.max = max;
        this.currentAllocation = currentAllocation;
    }

    public boolean request(int p, int r, int amount) {
        if (resources[r] < amount)
            return false;
        if (max[p][r] < amount)
            return false;
        resources[r] -= amount;
        currentAllocation[p][r] += amount;
        return true;
    }

    public void release(int p, int r, int amount) {
        resources[r] += amount;

        currentAllocation[p][r] -= amount;
    }

    public boolean isDeadlocked() {
        int count = 0;
        boolean detected;
        for (int i = 0; i < numOfProcess; i++) {
            detected = false;
            for (int j = 0; !detected && j < resources.length; j++) {
                if (max[i][j] - currentAllocation[i][j] > resources[j])
                    detected = true;
            }
            if (detected)
                count++;
        }
        return count == numOfProcess;
    }

    public static void initializeState(BankerAlgorithm algorithm) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter number of processes: ");
        int p = input.nextInt();
        System.out.println("Enter number of resources: ");
        int r = input.nextInt();
        int[] resources = new int[r];
        System.out.println("Enter number of each resource, separated by white space: ");
        int i, j;
        for (i = 0; i < r; i++) {
            resources[i] = input.nextInt();
        }

        System.out.println();
        System.out.println("Enter claim matrix: ");

        int[][] claimMatrix = new int[p][r];
        for (i = 0; i < p; i++) {
            System.out.println("Enter maximum claim of process" + i + "for each resource");
            System.out.println("Separated by white space");
            for (j = 0; j < r; j++)
                claimMatrix[i][j] = input.nextInt();
        }

        algorithm.numOfProcess = p;
        algorithm.resources = resources;
        algorithm.max = claimMatrix;
        algorithm.currentAllocation = new int[p][r];
    }

    public String toString() {
        String result = "Claim Matrix: \n";
        int i, j;
        for (i = 0; i < numOfProcess; i++) {
            for (j = 0; j < resources.length; j++)
                result = result + "\t" + max[i][j];
            result += "\n";
        }

        result += "\nAllocation Matrix: \n";
        for (i = 0; i < numOfProcess; i++) {
            for (j = 0; j < resources.length; j++)
                result = result + "\t" + currentAllocation[i][j];
            result += "\n";
        }
        result += "\nAvailable Resources:\n";
        for (i = 0; i < resources.length; i++)
            result += "\t" + resources[i];
        return result;
    }

        public static void main(String[] args){
            Scanner input = new Scanner(System.in);

            BankerAlgorithm  algorithm = new BankerAlgorithm();
            initializeState(algorithm);
            System.out.println("System initial state is: \n" + algorithm);

            while (!algorithm.isDeadlocked()) {
                System.out.println("Enter your next command in form request(i, j, k) or release(i, j, k): ");
                String command[] = input.nextLine().split("\\(|, |\\)");
                int i = Integer.parseInt(command[1].trim());
                int j = Integer.parseInt(command[2].trim());
                int k = Integer.parseInt(command[3].trim());
                if (command[0].trim().equals("request")) {
                    if (!algorithm.request(i, j, k))
                        System.out.println("The request rejected");
                    else
                        System.out.println("The request granted");
                } else if (command[0].trim().equals("release")) {
                    algorithm.release(i, j, k);
                } else {
                    System.out.println("Wrong command format");
                }
                System.out.println("System current state is: \n" + algorithm);
            }
            System.out.println("Now the system is deadlocked");

          }
        }


