package nplayernmove;

import java.util.List;

/**
 * Represents one players strategy space.
 */
public interface Simplex {

    Simplex subdivide();

    List<double[]> getCentroid();

    List<double[][]> getFullyLabeled();
}
