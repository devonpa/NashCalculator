package nplayernmove;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Master {

    public static void main(String[] args) {
        final int iterations = 2;

        final PayoffMatrix[] matrices = new PayoffMatrix[2];

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
