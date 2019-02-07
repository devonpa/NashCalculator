package test.nplayernmove;

import nplayernmove.BaseSimplex;
import nplayernmove.PayoffMatrix;
import nplayernmove.Simplex;
import nplayernmove.Simplotope;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Benchmark for
 */
public class Benchmark {

    /**
     * Test Problem 4 https://www.sciencedirect.com/science/article/pii/S0377042704002444
     * From the GAMBIT benchmark 2x2x2.nfg
     * 3 players, 2 pure strategies. 9 Nash equilibria
     */
    private static BaseSimplex constructProblem4() {
        final PayoffMatrix[] matrices = new PayoffMatrix[3];
        final Map<int[], Integer> payoffsP1 = new HashMap<>();
        payoffsP1.put(new int[]{0,0,0}, 9);
        payoffsP1.put(new int[]{1,0,0}, 0);
        payoffsP1.put(new int[]{0,1,0}, 0);
        payoffsP1.put(new int[]{1,1,0}, 9);
        payoffsP1.put(new int[]{0,0,1}, 0);
        payoffsP1.put(new int[]{1,0,1}, 3);
        payoffsP1.put(new int[]{0,1,1}, 3);
        payoffsP1.put(new int[]{1,1,1}, 0);

        final Map<int[], Integer> payoffsP2 = new HashMap<>();
        payoffsP1.put(new int[]{0,0,0}, 8);
        payoffsP1.put(new int[]{1,0,0}, 0);
        payoffsP1.put(new int[]{0,1,0}, 0);
        payoffsP1.put(new int[]{1,1,0}, 8);
        payoffsP1.put(new int[]{0,0,1}, 0);
        payoffsP1.put(new int[]{1,0,1}, 4);
        payoffsP1.put(new int[]{0,1,1}, 4);
        payoffsP1.put(new int[]{1,1,1}, 0);

        final Map<int[], Integer> payoffsP3 = new HashMap<>();
        payoffsP1.put(new int[]{0,0,0}, 12);
        payoffsP1.put(new int[]{1,0,0}, 0);
        payoffsP1.put(new int[]{0,1,0}, 0);
        payoffsP1.put(new int[]{1,1,0}, 2);
        payoffsP1.put(new int[]{0,0,1}, 0);
        payoffsP1.put(new int[]{1,0,1}, 4);
        payoffsP1.put(new int[]{0,1,1}, 6);
        payoffsP1.put(new int[]{1,1,1}, 0);
        matrices[0] = new PayoffMatrix(payoffsP1);
        matrices[1] = new PayoffMatrix(payoffsP2);
        matrices[2] = new PayoffMatrix(payoffsP3);

        final Simplotope simplotope = new Simplotope(new int[]{2, 2, 2});

        return BaseSimplex.createSimplex(matrices, simplotope, simplotope.getnDim());
    }

    /**
     * Test Problem 6 https://www.sciencedirect.com/science/article/pii/S0377042704002444
     * From the GAMBIT benchmark coord4.nfg
     * 2 players, 4 pure strategies. 15 Nash equilibria
     */
    private static BaseSimplex constructProblem6() {
        final PayoffMatrix[] matrices = new PayoffMatrix[2];
        final Map<int[], Integer> payoffsP1 = new HashMap<>();
        payoffsP1.put(new int[]{0,0}, 3);
        payoffsP1.put(new int[]{1,0}, 0);
        payoffsP1.put(new int[]{2,0}, 0);
        payoffsP1.put(new int[]{3,0}, 0);
        payoffsP1.put(new int[]{0,1}, 0);
        payoffsP1.put(new int[]{1,1}, 2);
        payoffsP1.put(new int[]{2,1}, 0);
        payoffsP1.put(new int[]{3,1}, 0);
        payoffsP1.put(new int[]{0,2}, 0);
        payoffsP1.put(new int[]{1,2}, 0);
        payoffsP1.put(new int[]{2,2}, 1);
        payoffsP1.put(new int[]{3,2}, 0);
        payoffsP1.put(new int[]{0,3}, 0);
        payoffsP1.put(new int[]{1,3}, 0);
        payoffsP1.put(new int[]{2,3}, 0);
        payoffsP1.put(new int[]{3,3}, 4);

        final Map<int[], Integer> payoffsP2 = new HashMap<>();
        payoffsP1.put(new int[]{0,0}, 2);
        payoffsP1.put(new int[]{1,0}, 0);
        payoffsP1.put(new int[]{2,0}, 0);
        payoffsP1.put(new int[]{3,0}, 0);
        payoffsP1.put(new int[]{0,1}, 0);
        payoffsP1.put(new int[]{1,1}, 2);
        payoffsP1.put(new int[]{2,1}, 0);
        payoffsP1.put(new int[]{3,1}, 0);
        payoffsP1.put(new int[]{0,2}, 0);
        payoffsP1.put(new int[]{1,2}, 0);
        payoffsP1.put(new int[]{2,2}, 4);
        payoffsP1.put(new int[]{3,2}, 0);
        payoffsP1.put(new int[]{0,3}, 0);
        payoffsP1.put(new int[]{1,3}, 0);
        payoffsP1.put(new int[]{2,3}, 0);
        payoffsP1.put(new int[]{3,3}, 7);
        matrices[0] = new PayoffMatrix(payoffsP1);
        matrices[1] = new PayoffMatrix(payoffsP2);

        final Simplotope simplotope = new Simplotope(new int[]{4, 4});

        return BaseSimplex.createSimplex(matrices, simplotope, simplotope.getnDim());
    }

    public static void main(String[] args) {
        final int iterations = 1;
        Simplex simplex = constructProblem4();
        final Simplotope simplotope = ((BaseSimplex) simplex).getSimplotope();

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
