package nplayernmove;

/**
 * Lowest level of simplex, has no inner simplices
 */
public class BaseSimplex implements Simplex{


    private final CoordsAndLabel[] points;

    public BaseSimplex(final CoordsAndLabel... points) {
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

    //calculates the barymetric subdivision
    public Simplex BSD() {
//        double[] triCoords = this.getCoords();
//        double[] bary = getBarycenter(triCoords);
//        //double[] baryMap = getBary(bary);
//        //double[] baryMap = doNash(bary);
//        double[] baryMap = continuousFunction(bary);
//        int label = 0;
//        if (bary[0] > baryMap[0]) {
//            label = 1;
//        } else if (bary[1] > baryMap[1]) {
//            label = 2;
//        } else {
//            label = 3;
//        }
//        CoordsAndLabel baryCoordsAndLabel = new CoordsAndLabel(bary, label);
//
//
//        double[] x = {triCoords[0], triCoords[1], triCoords[2]};
//        double[] y = {triCoords[3], triCoords[4], triCoords[5]};
//        double[] z = {triCoords[6], triCoords[7], triCoords[8]};
//
//        double[] mid1 = midPoint(x, y);
//        //double[] mid1Map = getBary(mid1);
//        //double[] mid1Map = doNash(mid1);
//        double[] mid1Map = continuousFunction(mid1);
//        if (mid1[0] > mid1Map[0]) {
//            label = 1;
//        } else if (mid1[1] > mid1Map[1]) {
//            label = 2;
//        } else {
//            label = 3;
//        }
//        CoordsAndLabel mid1CoordsAndLabel = new CoordsAndLabel(mid1, label);
//
//        double[] mid2 = midPoint(x, z);
//        //double[] mid2Map = getBary(mid2);
//        //double[] mid2Map = doNash(mid2);
//        double[] mid2Map = continuousFunction(mid2);
//        if (mid2[0] > mid2Map[0]) {
//            label = 1;
//        } else if (mid2[1] > mid2Map[1]) {
//            label = 2;
//        } else {
//            label = 3;
//        }
//        CoordsAndLabel mid2CoordsAndLabel = new CoordsAndLabel(mid2, label);
//
//        double[] mid3 = midPoint(y, z);
//        //double[] mid3Map = getBary(mid3);
//        //double[] mid3Map = doNash(mid3);
//        double[] mid3Map = continuousFunction(mid3);
//        if (mid3[0] > mid3Map[0]) {
//            label = 1;
//        } else if (mid3[1] > mid3Map[1]) {
//            label = 2;
//        } else {
//            label = 3;
//        }
//        CoordsAndLabel mid3CoordsAndLabel = new CoordsAndLabel(mid3, label);
//
//        Simplex tri1 = new Simplex(a, baryCoordsAndLabel, mid2CoordsAndLabel);
//        Simplex tri2 = new Simplex(baryCoordsAndLabel, mid2CoordsAndLabel, c);
//        Simplex tri3 = new Simplex(baryCoordsAndLabel, mid3CoordsAndLabel, c);
//        Simplex tri4 = new Simplex(baryCoordsAndLabel, mid3CoordsAndLabel, b);
//        Simplex tri5 = new Simplex(baryCoordsAndLabel, b, mid1CoordsAndLabel);
//        Simplex tri6 = new Simplex(baryCoordsAndLabel, mid1CoordsAndLabel, a);
//
//        Simplex[] ret = {tri1, tri2, tri3, tri4, tri5, tri6};
//
//        return ret;


        return null;
    }


    public double[] midPoint(double[] a, double[] b) {
        double x = (a[0] + b[0]) / 2;
        double y = (a[1] + b[1]) / 2;
        double z = (a[2] + b[2]) / 2;
        double[] ret = {x, y, z};
        return ret;
    }

    public double[] getBarycenter(double[] a) {
        double newA = (a[0] + a[3] + a[6]) / 3;
        double newB = (a[1] + a[4] + a[7]) / 3;
        double newC = (a[2] + a[5] + a[8]) / 3;
        double[] ret = {newA, newB, newC};
        return ret;
    }
}
