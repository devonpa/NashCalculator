package nplayernmove;

import java.util.Arrays;

/**
 * Collection of math functions.
 *
 * TODO only square 2D arrays allowed in java- make custom data structure
 */
public class MathFunctions {

    public static int labelPoint(final double[] point, final Simplotope strategySpace, final PayoffMatrix[] payoffMatrix) {
        final double[][] strategies = strategySpace.fromSimplex(point);
        final double[][] nashResult = nashFxn(strategies, payoffMatrix);
        final double[] newPoint = strategySpace.fromSimplotope(nashResult);
        int i = 0;
        for(; i < point.length; i++) {
            if(newPoint[i] < point[i]) {
                return i + 1;
            }
        }

        throw new NashFoundException(strategies);
    }

    private static class NashFoundException extends RuntimeException {
        private NashFoundException(final double[][] strategies) {
            super("Found Nash: " + Arrays.deepToString(strategies));
        }
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

    private static double[][] copyArray(final double[][] a) {
        final double[][] duplicate = new double[a.length][a[0].length]; //todo 0 index?
        for(int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, duplicate[i], 0, a[i].length);
        }

        return duplicate;
    }
}
