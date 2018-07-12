package nplayernmove;

import java.util.List;
import java.util.stream.Collectors;

public class Master {

    public static void main(String[] args) {
        final int iterations = 2;

        final PayoffMatrix matrix = null;

        final Simplotope simplotope = new Simplotope(new int[]{2, 2});

        Simplex simplex = createSimplex(matrix, simplotope.getnDim());

        for(int i = 0; i < iterations; i++) {
            simplex = simplex.subdivide();
        }

        final List<double[]> fullyLabeledCoordinates = simplex.getFullyLabeled();
        final List<double[][]> nashEquilibrium = fullyLabeledCoordinates.stream().map(simplotope::fromSimplex).collect(Collectors.toList());
        System.out.println(nashEquilibrium);
    }

    private static Simplex createSimplex(final PayoffMatrix payoffMatrix, final int nDim) {
        return null;
    }

}
