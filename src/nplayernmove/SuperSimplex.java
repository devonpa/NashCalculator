package nplayernmove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by devonallison on 7/1/18.
 */
public class SuperSimplex implements Simplex{

    private final Simplex[] simplexes;

    public SuperSimplex(final Simplex... simplexes) {
        this.simplexes = simplexes;
    }

    @Override
    public Simplex subdivide() {
        return new SuperSimplex(Arrays.stream(simplexes).map(Simplex::subdivide).toArray(Simplex[]::new));
    }

    @Override
    public List<double[]> getCentroid() {
        //todo is this right
        //return simplexes[0].getCentroid();
        throw new IllegalStateException("Can't find centroid of SuperSimplex");
    }

    @Override
    public List<double[][]> getFullyLabeled() {
        final List<double[][]> ret = new ArrayList<>();
        Arrays.stream(simplexes).forEach(s -> ret.addAll(s.getFullyLabeled()));
        return ret;
    }
}
