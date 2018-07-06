package nplayernmove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lowest level of simplex, has no inner simplices
 */
public class BaseSimplex implements Simplex{


    private final CoordsAndLabel[] points;
    private final PayoffMatrix payoffMatrix;

    public BaseSimplex(final PayoffMatrix payoffMatrix, final CoordsAndLabel... points) {
        this.payoffMatrix = payoffMatrix;
        this.points = points;
    }

    public boolean isFull() {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = 0; j < points.length; j++) {
                if(i != j && points[i].getLabel() == points[j].getLabel()) {
                    return false;
                }
            }
        }

        return true;
    }

    public double[]  getCoords() {
//        double[] x = a.getCoordinates();
//        double[] y = b.getCoordinates();
//        double[] z = c.getCoordinates();
//
//        double[] ret = {x[0], x[1], x[2], y[0], y[1], y[2], z[0], z[1], z[2]};
//        return ret;

        return null;
    }

    public int[] getLabels() {
        int[] ret = new int[points.length];

        for(int i = 0; i < ret.length; i++) {
            ret[i] = points[i].getLabel();
        }

        return ret;
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
        final List<Object> centroidAndLine = faces[0].getCentroid();
        final double[] newPointCoordinates = (double[]) centroidAndLine.get(0);
        final CoordsAndLabel newCoordsAndLabel = new CoordsAndLabel(newPointCoordinates, MathFunctions.labelPoint(newPointCoordinates, payoffMatrix));
        final CoordsAndLabel[] onLine = (CoordsAndLabel []) centroidAndLine.get(1);

        //the points on the line are the "split", one goes on each side
        final CoordsAndLabel[] newCoords = new CoordsAndLabel[points.length];
        int j = 0;
        for(final CoordsAndLabel coordsAndLabel : points) {
            //todo probably just need ==
            if(!coordsAndLabel.equals(onLine[0]) && !coordsAndLabel.equals(onLine[1])) {
                newCoords[j++] = coordsAndLabel;
            }
        }

        newCoords[j] = newCoordsAndLabel;
        newCoords[newCoords.length - 1] = onLine[0];
        final Simplex left = new BaseSimplex(payoffMatrix, newCoords).subdivide(i - 1);

        final CoordsAndLabel[] coordsAndLabelsRight = new CoordsAndLabel[newCoords.length];
        System.arraycopy(newCoords, 0, coordsAndLabelsRight, 0, newCoords.length - 1);
        coordsAndLabelsRight[newCoords.length - 1] = onLine[1];
        final Simplex right = new BaseSimplex(payoffMatrix, coordsAndLabelsRight).subdivide(i - 1);

        return new SuperSimplex(left, right);
    }

    public Simplex subdivideBaseCase() {
        final List<Object> centroidAndLine = getCentroid();
        final double[] newPoint = (double[]) centroidAndLine.get(0);
        final CoordsAndLabel[] onLine = (CoordsAndLabel[]) centroidAndLine.get(1);

        final CoordsAndLabel newCoordsAndLabel = new CoordsAndLabel(newPoint, MathFunctions.labelPoint(newPoint, payoffMatrix));

        return new SuperSimplex(new BaseSimplex(payoffMatrix, newCoordsAndLabel, onLine[0]),
                new BaseSimplex(payoffMatrix, newCoordsAndLabel, onLine[1]));
    }

    public Simplex[] makeFaces() {
        final Simplex[] faces = new Simplex[points.length];

        for(int i = 0; i < points.length; i++) {
            final CoordsAndLabel[] coords = new CoordsAndLabel[points.length - 1];

            for(int j = 0; j < points.length; j++) {
                if(i != j) {
                    coords[j] = points[j];
                }
            }
            faces[i] = new BaseSimplex(payoffMatrix, coords);
        }

        return faces;
    }

    public List<Object> getCentroid() {
        if(points.length == 2) {
            final double[] coordinates0 = points[0].getCoordinates();
            final double[] coordinates1 = points[1].getCoordinates();
            final double[] newPointCoordinates = new double[]{(coordinates0[0] + coordinates1[0]) / 2d, (coordinates0[1] + coordinates1[1]) / 2d};
            final CoordsAndLabel[] onLine = new CoordsAndLabel[]{points[0], points[1]};
            return Arrays.asList(newPointCoordinates, onLine);
        }

        return makeFaces()[0].getCentroid();
    }

    @Override
    public List<double[]> getFullyLabeled() {
        if(!isFull()) {
            throw new IllegalStateException("BaseSimplex not full!");
        }

        final List<double[]> list = new ArrayList<>();
        Arrays.stream(points).forEach(p -> list.add(p.getCoordinates()));
        return list;
    }
}
