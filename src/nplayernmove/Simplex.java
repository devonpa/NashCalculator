package nplayernmove;

import java.util.List;

public interface Simplex {

    Simplex subdivide();

    double[] getCentroid();

    List<double[]> getFullyLabeled();
}
