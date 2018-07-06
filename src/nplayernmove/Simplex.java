package nplayernmove;

import java.util.List;

public interface Simplex {

    Simplex subdivide();

    List<Object> getCentroid();

    List<double[]> getFullyLabeled();
}
