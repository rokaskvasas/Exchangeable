import java.util.Locale;
import java.util.Scanner;

public class Factory {


    public void startWork() {

        Scanner sc = new Scanner(System.in);
        String userInput = "";
        while (!userInput.equals("n")) {
            try {

                System.out.println("Please enter number of available destinations: ");

                int destinations = sc.nextInt();

                System.out.print("Select strategy:\n 1.Randomizer \n 2.Round robin \n ");

                int strategy = sc.nextInt();

                System.out.println("Enter consecutive loads for one destination: ");

                long consecutiveLoads = sc.nextLong();

                System.out.println("Percentage of failure?");

                double failurePercentage = sc.nextDouble();

                System.out.println("Number of loads?");

                long numberOfLoads = sc.nextLong();

                ConvyoerWorker worker = ConvyoerWorker.builder()
                        .destinationList(ConvyoerWorker.buildDestinationList(destinations))
                        .consecutiveLoads(consecutiveLoads)
                        .failurePercentage(failurePercentage)
                        .loads(numberOfLoads)
                        .build();
                worker.doWork(strategy);

                System.out.println(worker.getResult());

                System.out.println("Do you want to work again ? Y/N");
                userInput = sc.next().toLowerCase(Locale.ROOT).trim();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }
        sc.close();
    }
}
