package nplayernmove;

public class Master {

    public static void main(String[] args) {
        final int iterations = 2;

        final PayoffMatrix matrix = null;

        Simplex simplex = createSimplex(matrix);

        for(int i = 0; i < iterations; i++) {
            simplex = simplex.subdivide();
        }

        System.out.println(simplex.getFullyLabeled());
    }

    private static Simplex createSimplex(final PayoffMatrix matrix) {
        //todo
        return null;
    }

}
