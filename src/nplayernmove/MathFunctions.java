package nplayernmove;

import java.util.Arrays;

/**
 * Collection of math functions.
 *
 * TODO only square 2D arrays allowed in java- make custom data structure
 */
public class MathFunctions {

    public static int labelPoint(final double[] point, final PayoffMatrix payoffMatrix) {
        //todo
        return -1;
    }

    public static double expectedPayoff(final double[][] strategies, final PayoffMatrix payoffMatrix){
        double expectedPayoff = 0;

        for(final PayoffMatrix.Moves moveSet : payoffMatrix.getMoves()) {
            final double payoff = payoffMatrix.getPayoff(moveSet);
            final int[] moves = moveSet.getMoves();
            double probability = 1;
            for(int i = 0; i < strategies.length; i++) {
                probability *= strategies[i][moves[i]];
            }

            expectedPayoff += probability * payoff;
        }

        return expectedPayoff;
    }

    public static double[][] nashFxn(final double[][] strategies, final PayoffMatrix[] payoffMatrices) {
        final int numPlayers = strategies.length;
        final int numMoves = strategies[0].length; //todo 2D arrays?

        final double[][] functionResult = new double[numPlayers][numMoves];

        for(int player = 0; player < numPlayers; player++) {
            final double[] functionResultPlayer = new double[numMoves];
            final double[] cachedDeltaFxnResult = new double[numMoves];

            for(int move = 0; move < numMoves; move++) {
                cachedDeltaFxnResult[move] = deltaFxn(strategies, player, move, payoffMatrices[player]);
            }

            final double sumOfDeltaFxnResults = Arrays.stream(cachedDeltaFxnResult).sum();

            for(int move = 0; move < numMoves; move++) {
                functionResultPlayer[move] = (strategies[player][move] + cachedDeltaFxnResult[move]) / (1 + sumOfDeltaFxnResults);
            }

            functionResult[player] = functionResultPlayer;
        }

        return functionResult;
    }

    private static double deltaFxn(final double[][] strategies, final int player, final int pureMove, final PayoffMatrix payoffMatrix) {
        final double expectedWithCurrentStrategy = expectedPayoff(strategies, payoffMatrix);

        final double[][] strategiesDuplicated = copyArray(strategies); //can't modify strategies
        final double[] pureStrategy = new double[strategies[player].length];
        pureStrategy[pureMove] = 1;
        strategiesDuplicated[player] = pureStrategy;

        final double expectedWithPureStrategy = expectedPayoff(strategiesDuplicated, payoffMatrix);

        return Math.max(0, expectedWithPureStrategy - expectedWithCurrentStrategy);
    }

    public static double[] hInverse(final double[] coordinates){
        final double[] embeddedCoordinates = new double[coordinates.length - 1];
        System.arraycopy(coordinates, 0, embeddedCoordinates, 0, coordinates.length - 1);

        return gInverse(embeddedCoordinates);
    }

    //radial projection from origin, through coordinate, to box
    private static double[] gInverse(final double[] coordinates) {
        final double t = t(coordinates);
        final double sum = Arrays.stream(coordinates).sum();
        final double t2 = sum == 0 ? 0 : 1.0 / sum;
        final double stretchFactor = t / t2;

        return Arrays.stream(coordinates).map(d -> d * stretchFactor).toArray();
    }

    private static double max(final double[] a) {
        double max = Double.NEGATIVE_INFINITY;

        for(final double d : a) {
            max = Math.max(max, d);
        }

        return max;
    }

    private static double[][] copyArray(final double[][] a) {
        final double[][] duplicate = new double[a.length][a[0].length]; //todo 0 index?
        for(int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, duplicate[i], 0, a[i].length);
        }

        return duplicate;
    }

    public static double[] h(final double[] coordinates) {
        final double t = t(coordinates);
        final double sum = Arrays.stream(coordinates).sum();
        final double t2 = 1.0 / sum; //sum can't be 0 or t would be
        final double stretchFactor = t2 / t;

        final double[] temp = Arrays.stream(coordinates).map(d -> d * stretchFactor).toArray();
        final double[] ret = new double[temp.length + 1];
        System.arraycopy(temp, 0, ret, 0, temp.length);
        ret[ret.length - 1] = 1 - (stretchFactor * sum);

        return ret;
    }

    private static double t(double[] a) {
        //highest coordinate will hit unit cube first
        final double max = max(a);

        //linear => scalar projection will be same for each coordinate
        if(max == 0) {
            return 0.0;
        } else {
            return 1.0 / max;
        }
    }
}
