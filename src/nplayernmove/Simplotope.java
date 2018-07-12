package nplayernmove;

import java.util.Arrays;

/**
 * Represents the entire strategy space for the game.
 *
 * The constructor takes an int[] with the number of moves per player.
 * The simplotope is the product of each players simplified strategy space (number of moves -1)
 * This allows us to represent the simplotope as the intersection of the inequalities 0 <= sum moves <= 1
 */
public class Simplotope {

    private final int[] numMoves;
    private final int nDim;

    public Simplotope(final int[] numMoves) {
        //todo this is required to be equal from using [][]
        for(int i = 0; i < numMoves.length - 1; i++) {
            if(numMoves[i] != numMoves[i+1]) {
                throw new IllegalArgumentException("Each player must have the same number of moves");
            }
        }

        this.numMoves = numMoves;
        this.nDim = Arrays.stream(numMoves).sum() - numMoves.length; //subtract one move per player
    }

    public double[][] fromSimplex(double[] coordinates) {
        coordinates = toProjectedSimplexCoordinates(coordinates);
        double tSimplexIntercept = findSimplexIntercept(coordinates);

        final double[] moddedSimpletopeCoords;
        if(tSimplexIntercept != 0d) {
            double minTSimplotopeIntercept = findSimplotopeIntercept(coordinates);

            //multiply by the magnitude of the vector given when letting t = minTSimplotopeIntercept
            //divided by t = tSimplexIntercept
            //this is just minTSimplotopeIntercept/tSimplexIntercept
            final double stretchFactor = minTSimplotopeIntercept / tSimplexIntercept;
            moddedSimpletopeCoords = Arrays.stream(coordinates).map(d -> d * stretchFactor).toArray();
        } else {
            moddedSimpletopeCoords = coordinates;
        }

        return fromModifiedSimplotopeToStrategies(moddedSimpletopeCoords);
    }

    public double[] fromSimplotope(double[][] strategies) {
        final double[] coordinates = toModifiedSimplotopeCoordinates(strategies);

        final double tSimplexIntercept = findSimplexIntercept(coordinates);
        if(tSimplexIntercept == 0d) {
            return coordinates; //this will happen at the origin- no stretching required
        }

        final double minTSimplotopeIntercept = findSimplotopeIntercept(coordinates);

        final double stretchFactor = tSimplexIntercept / minTSimplotopeIntercept;

        return fromProjectedSimplexCoordinates(Arrays.stream(coordinates).map(d -> d * stretchFactor).toArray());
    }

    public int getnDim() {
        return nDim;
    }

    //we remove the last coordinate of the simplex to project it down a dimension
    //this makes the projected simplex contain the origin
    private double[] toProjectedSimplexCoordinates(final double[] simplexCoordinates) {
        final double[] projectedCoordinates = new double[simplexCoordinates.length - 1];
        System.arraycopy(simplexCoordinates, 0, projectedCoordinates, 0, projectedCoordinates.length);
        return projectedCoordinates;
    }

    private double[] fromProjectedSimplexCoordinates(final double[] modSimplexCoordinates) {
        final double[] coordinates = new double[modSimplexCoordinates.length + 1];
        System.arraycopy(modSimplexCoordinates, 0, coordinates, 0, modSimplexCoordinates.length);
        coordinates[coordinates.length - 1] = 1d - Arrays.stream(modSimplexCoordinates).sum();
        return coordinates;
    }

    //strategies contain all probabilities of all players
    //change these double[][] to match our modification
    private double[] toModifiedSimplotopeCoordinates(final double[][] strategies) {
        final double[] coordinates = new double[nDim];
        int count = 0;
        for (double[] strategy : strategies) {
            for (int j = 0; j < strategy.length - 1; j++) { //remove last probability from each strategy
                coordinates[count++] = strategy[j];
            }
        }

        return coordinates;
    }

    private double[][] fromModifiedSimplotopeToStrategies(final double[] modifiedCoordinates) {
        //calculate the probability of the final move for each player
        final double[][] unModdifiedCoords = new double[numMoves.length][numMoves[0]];
        int count = 0;
        int numMovesMinus1SoFar = 0;
        for (int numMove : numMoves) {
            final double[] playerStrategy = new double[numMove];
            double probability = 1;
            for(int i = 0; i < numMove - 1; i++) {
                double x = modifiedCoordinates[numMovesMinus1SoFar + i];
                playerStrategy[i] = x;
                probability -= x;
            }
            playerStrategy[numMove - 1] = probability;
            unModdifiedCoords[count] = playerStrategy;
            count++;
            numMovesMinus1SoFar += numMove - 1;
        }

        return unModdifiedCoords;
    }

    private double findSimplexIntercept(final double[] coordinates) {
        //line function will be given by x1 = c[0]t, x2 = c[1]t etc
        //this intersects the simplex 1 = x1 + x2 + ...
        //at t = 1 / sum(coordinates)
        final double sum = Arrays.stream(coordinates).sum();
        if(sum == 0d) { //special case at origin
            return 0;
        }
        return 1d / sum;
    }

    private double findSimplotopeIntercept(final double[] coordinates) {
        //this simplotope is the intersection of planes given by similar inequalities 1 = x1 + x2 + ...
        //the edge of the simplotope will be one of these planes- the one
        //that whose t value hits first
        double minTSimplotopeIntercept = Double.MAX_VALUE;
        int numMovesSoFar = 0;
        for (int numMove : numMoves) {
            final int numMoveMinusone = numMove - 1;
            final double[] relatedCoords = new double[numMoveMinusone];
            System.arraycopy(coordinates, numMovesSoFar, relatedCoords, 0, numMoveMinusone);
            final double sum = Arrays.stream(relatedCoords).sum();
            final double t;
            if(sum == 0d) {
                numMovesSoFar += numMoveMinusone;
                continue; //todo think about- this shouldn't happen every time
            } else {
                t = 1d / sum;
            }
            minTSimplotopeIntercept = Math.min(minTSimplotopeIntercept, t);

            numMovesSoFar += numMoveMinusone;
        }

        return minTSimplotopeIntercept;
    }
}
