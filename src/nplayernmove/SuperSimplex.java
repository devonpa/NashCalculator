package nplayernmove;

import java.util.Arrays;

/**
 * Created by devonallison on 7/1/18.
 */
public class SuperSimplex implements Simplex{

    private final Simplex[] simplexes;

    public SuperSimplex(final Simplex... simplexes) {
        this.simplexes = simplexes;
    }

    public Simplex BSD() {
        return new SuperSimplex(Arrays.stream(simplexes).map(Simplex::BSD).toArray(Simplex[]::new));
    }
}
