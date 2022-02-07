import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ConvyoerWorker {

    private List<Destination> destinationList;

    private long consecutiveLoads;

    private double failurePercentage;

    private long loads;

    public static List<Destination> buildDestinationList(int count) {
        List<Destination> list = new ArrayList<>();

        for (int i = 0; i < count + 1; i++) {
            list.add(Destination.builder()
                    .position(i)
                    .build());
        }
        return list;
    }

    public void doWork(int strategy) {
        switch (strategy){
            case 1 -> routeRandom();
            case 2 -> routeRobin();
            default -> throw new IllegalArgumentException("Invalid strategy input");
        }
    }

    private void routeRandom() {

        for (int i = 1; i < loads; i += consecutiveLoads) {
            Destination destination = destinationList.get(getRandom(destinationList.size()));
            destinationRoute(destination);
        }
    }

    private void routeRobin() {

        int destinationPosition = 1;
        for (int i = 1; i < loads; i += consecutiveLoads) {
            Destination destination = destinationList.get(destinationPosition);
            destinationRoute(destination);
            destinationPosition = destinationPosition == destinationList.size() - 1 ? 1 : destinationPosition + 1;

        }
    }


    private void destinationRoute(Destination destination) {
        Destination exitDestination = destinationList.get(0);
        for (int j = 0; j < consecutiveLoads; j++) {
            destination.setAssignedPackages(destination.getAssignedPackages() + 1);
            if (Math.random() > failurePercentage / 100) {
                destination.setSuccessfulPackages(destination.getSuccessfulPackages() + 1);
            } else {
                exitDestination.setAssignedPackages(exitDestination.getAssignedPackages() + 1);
                exitDestination.setSuccessfulPackages(exitDestination.getSuccessfulPackages() + 1);
            }
        }
    }


    public String getResult() {
        StringBuilder sb = new StringBuilder();
        destinationList.forEach(destination -> {
            sb.append(destination.toString()).append("\n");
            long percent = getPercentOfSuccess(destination);
            sb.append(String.format("Percentage of successfully reached loads: %s%% \n", percent));
        });
        return sb.toString();
    }

    private long getPercentOfSuccess(Destination destination) {
        long percent;
        if (destination.getAssignedPackages() == 0) {
            percent = 0;
        } else {
            percent = (destination.getSuccessfulPackages() * 100) / destination.getAssignedPackages();
        }
        return percent;
    }

    private int getRandom(int max) {
        return ((int) (Math.random() * (max - 1))) + 1;
    }
}
