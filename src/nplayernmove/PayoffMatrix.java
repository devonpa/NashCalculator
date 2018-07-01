package nplayernmove;

import java.util.*;

/**
 * n1xn2xn3xnM... matrix representing the payoff for one player given M players.
 */
public class PayoffMatrix {


    private final Map<Moves, Integer> payoffs = new HashMap<>();

    public PayoffMatrix(final Map<int[], Integer> payoffs) {
        payoffs.entrySet().forEach(a -> this.payoffs.put(new Moves(a.getKey()), a.getValue()));
    }


    public int getPayoff(final int[] moves) {
        return getPayoff(new Moves(moves));
    }

    public int getPayoff(final Moves moves) {
        if(!payoffs.containsKey(moves)) {
            throw new IllegalArgumentException("No payoff exists for given moves " + moves);
        }

        return payoffs.get(moves);
    }

    public Set<Moves> getMoves() {
        return Collections.unmodifiableSet(payoffs.keySet());
    }


    //allows int[] hashmap
    public class Moves {
        private final int[] moves;

        public Moves(int... moves) {
            this.moves = moves;
        }

        public int[] getMoves() {
            return moves;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Moves moves1 = (Moves) o;

            return Arrays.equals(moves, moves1.moves);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(moves);
        }

        @Override
        public String toString() {
            return Arrays.toString(moves);
        }
    }
}
