package nplayernmove;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Master {

    public static void main(String[] args) {
        final int iterations = 0;

        final PayoffMatrix[] matrices = new PayoffMatrix[2];
        final Map<int[], Integer> payoffsP1 = new HashMap<>();
        payoffsP1.put(new int[]{0,0}, 2);
        payoffsP1.put(new int[]{0,1}, 3);
        payoffsP1.put(new int[]{1,0}, 2);
        payoffsP1.put(new int[]{1,1}, 1);

        final Map<int[], Integer> payoffsP2 = new HashMap<>();
        payoffsP2.put(new int[]{0,0}, 3);
        payoffsP2.put(new int[]{0,1}, 5);
        payoffsP2.put(new int[]{1,0}, 2);
        payoffsP2.put(new int[]{1,1}, 3);
        matrices[0] = new PayoffMatrix(payoffsP1);
        matrices[1] = new PayoffMatrix(payoffsP2);

        final Simplotope simplotope = new Simplotope(new int[]{2, 2});

        Simplex simplex = BaseSimplex.createSimplex(matrices, simplotope, simplotope.getnDim());

        for(int i = 0; i < iterations; i++) {
            simplex = simplex.subdivide();
        }

        final List<double[][]> fullyLabeledCoordinatesList = simplex.getFullyLabeled();
        for(final double[][] fullyLabeledCoordinates : fullyLabeledCoordinatesList) {
            final List<double[][]> nashEquilibrium = Arrays.stream(fullyLabeledCoordinates).map(simplotope::fromSimplex).collect(Collectors.toList());
            print(nashEquilibrium);
        }
    }

    private static void print(final List<double[][]> toPrint) {
        toPrint.forEach(s -> System.out.println(Arrays.deepToString(s)));
    }

}
