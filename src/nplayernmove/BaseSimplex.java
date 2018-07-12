package nplayernmove;

import java.util.*;

/**
 * Lowest level of simplex, has no inner simplices
 */
public class BaseSimplex implements Simplex{


    private final double[][] points;
    private final PayoffMatrix[] payoffMatrices;
    private final Simplotope simplotope;

    public BaseSimplex(final PayoffMatrix[] payoffMatrices, final Simplotope simplotope, final double[]... points) {
        this.payoffMatrices = payoffMatrices;
        this.simplotope = simplotope;
        this.points = points;
    }

    public static BaseSimplex createSimplex(final PayoffMatrix[] payoffMatrices, final Simplotope simplotope, final int ndim) {
        //give all corners, which will be of the form (0,0,..,1,0,..0)
        final double[][] points = new double[ndim][ndim];
        for(int i = 0; i < ndim; i++) {
            points[i][i] = 1;
        }

        return new BaseSimplex(payoffMatrices, simplotope, points);
    }

    @Override
    public Simplex subdivide() {
        return subdivide(2);
    }

    public Simplex subdivide(int i) {
        if(points.length == 2) {
            return subdivideBaseCase();
        }

        if(i <= 0) {
            return this;
        }

        final Simplex[] faces = makeFaces();

        //simplex i in faces will not have point i
        final List<double[]> centroidAndLine = faces[0].getCentroid();
        final double[] newPointCoordinates = centroidAndLine.get(0);
        final double[] onLine1 = centroidAndLine.get(1);
        final double[] onLine2 = centroidAndLine.get(2);

        //the points on the line are the "split", one goes on each side
        final double[][] newCoords = new double[points.length][points[0].length];
        int j = 0;
        for(final double[] coordsAndLabel : points) {
            if(!Arrays.equals(coordsAndLabel, onLine1) && !Arrays.equals(coordsAndLabel, onLine2)) {
                newCoords[j++] = coordsAndLabel;
            }
        }

        newCoords[j] = newPointCoordinates;
        newCoords[newCoords.length - 1] = onLine1;
        final Simplex left = new BaseSimplex(payoffMatrices, simplotope, newCoords).subdivide(i - 1);

        final double[][] coordsAndLabelsRight = new double[points.length][points[0].length];
        System.arraycopy(newCoords, 0, coordsAndLabelsRight, 0, newCoords.length - 1);
        coordsAndLabelsRight[newCoords.length - 1] = onLine2;
        final Simplex right = new BaseSimplex(payoffMatrices, simplotope, coordsAndLabelsRight).subdivide(i - 1);

        return new SuperSimplex(left, right);
    }

    public Simplex subdivideBaseCase() {
        final List<double[]> centroidAndLine = getCentroid();
        final double[] newPoint = centroidAndLine.get(0);

        return new SuperSimplex(new BaseSimplex(payoffMatrices, simplotope, newPoint, centroidAndLine.get(1)),
                new BaseSimplex(payoffMatrices, simplotope, newPoint, centroidAndLine.get(2)));
    }

    public Simplex[] makeFaces() {
        final Simplex[] faces = new Simplex[points.length];

        for(int i = 0; i < points.length; i++) {
            //each face has one less coordinate, but same dimension
            final double[][] coords = new double[points.length - 1][points[0].length];
            int k = 0;
            for(int j = 0; j < points.length; j++) {
                if(i != j) {
                    coords[k++] = points[j];
                }
            }
            faces[i] = new BaseSimplex(payoffMatrices, simplotope, coords);
        }

        return faces;
    }

    public List<double[]> getCentroid() {
        if(points.length == 2) {
            final double[] coordinates0 = points[0];
            final double[] coordinates1 = points[1];
            final double[] newPointCoordinates = new double[]{(coordinates0[0] + coordinates1[0]) / 2d, (coordinates0[1] + coordinates1[1]) / 2d};
            return Arrays.asList(newPointCoordinates, points[0], points[1]);
        }

        return makeFaces()[0].getCentroid();
    }

    @Override
    public List<double[][]> getFullyLabeled() {
        final Set<Integer> labels = new HashSet<>();

        for(final double[] coords : points) {
            if(labels.add(MathFunctions.labelPoint(coords, simplotope, payoffMatrices))) {
                final List<double[][]> list = Collections.singletonList(points);
                return Collections.unmodifiableList(list);
            }
        }

        return Collections.emptyList();
    }
}
