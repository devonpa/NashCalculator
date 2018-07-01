

public class Simplex {


    private CoordsAndLabel a;
    private CoordsAndLabel b;
    private CoordsAndLabel c;

    public Simplex(CoordsAndLabel a, CoordsAndLabel b, CoordsAndLabel c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean isFull() {
        return (a.getLabel() != b.getLabel()) && (b.getLabel() != c.getLabel()) && (a.getLabel() != c.getLabel());
    }

    public CoordsAndLabel[] getCoordsAndLabels() {
        return new CoordsAndLabel[]{a, b, c};
    }

    public double[] getCoords() {
        double[] x = a.getCoordinates();
        double[] y = b.getCoordinates();
        double[] z = c.getCoordinates();

        double[] ret = {x[0], x[1], x[2], y[0], y[1], y[2], z[0], z[1], z[2]};
        return ret;
    }

    public int[] getLabels() {

        int[] ret = {a.getLabel(), b.getLabel(), c.getLabel()};
        return ret;
    }

    //calculates the barymetric subdivision
    public Simplex[] BSD() {
        double[] triCoords = this.getCoords();
        double[] bary = getBarycenter(triCoords);
        //double[] baryMap = getBary(bary);
        //double[] baryMap = doNash(bary);
        double[] baryMap = weirdFxn(bary);
        int label = 0;
        if (bary[0] > baryMap[0]) {
            label = 1;
        } else if (bary[1] > baryMap[1]) {
            label = 2;
        } else {
            label = 3;
        }
        CoordsAndLabel baryCoordsAndLabel = new CoordsAndLabel(bary, label);


        double[] x = {triCoords[0], triCoords[1], triCoords[2]};
        double[] y = {triCoords[3], triCoords[4], triCoords[5]};
        double[] z = {triCoords[6], triCoords[7], triCoords[8]};

        double[] mid1 = midPoint(x, y);
        //double[] mid1Map = getBary(mid1);
        //double[] mid1Map = doNash(mid1);
        double[] mid1Map = weirdFxn(mid1);
        if (mid1[0] > mid1Map[0]) {
            label = 1;
        } else if (mid1[1] > mid1Map[1]) {
            label = 2;
        } else {
            label = 3;
        }
        CoordsAndLabel mid1CoordsAndLabel = new CoordsAndLabel(mid1, label);

        double[] mid2 = midPoint(x, z);
        //double[] mid2Map = getBary(mid2);
        //double[] mid2Map = doNash(mid2);
        double[] mid2Map = weirdFxn(mid2);
        if (mid2[0] > mid2Map[0]) {
            label = 1;
        } else if (mid2[1] > mid2Map[1]) {
            label = 2;
        } else {
            label = 3;
        }
        CoordsAndLabel mid2CoordsAndLabel = new CoordsAndLabel(mid2, label);

        double[] mid3 = midPoint(y, z);
        //double[] mid3Map = getBary(mid3);
        //double[] mid3Map = doNash(mid3);
        double[] mid3Map = weirdFxn(mid3);
        if (mid3[0] > mid3Map[0]) {
            label = 1;
        } else if (mid3[1] > mid3Map[1]) {
            label = 2;
        } else {
            label = 3;
        }
        CoordsAndLabel mid3CoordsAndLabel = new CoordsAndLabel(mid3, label);

        Simplex tri1 = new Simplex(a, baryCoordsAndLabel, mid2CoordsAndLabel);
        Simplex tri2 = new Simplex(baryCoordsAndLabel, mid2CoordsAndLabel, c);
        Simplex tri3 = new Simplex(baryCoordsAndLabel, mid3CoordsAndLabel, c);
        Simplex tri4 = new Simplex(baryCoordsAndLabel, mid3CoordsAndLabel, b);
        Simplex tri5 = new Simplex(baryCoordsAndLabel, b, mid1CoordsAndLabel);
        Simplex tri6 = new Simplex(baryCoordsAndLabel, mid1CoordsAndLabel, a);

        Simplex[] ret = {tri1, tri2, tri3, tri4, tri5, tri6};
        //printTri(tri1);
        //printTri(tri2);
        //printTri(tri3);
        //printTri(tri4);
        //printTri(tri5);
        //printTri(tri6);
        return ret;
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

    public double[] getBary(double[] a) {
        double newA = (a[1] * a[1]) / (a[0] * a[0] + a[1] * a[1] + a[2]);
        double newB = a[2] / (a[0] * a[0] + a[1] * a[1] + a[2]);
        double newC = (a[0] * a[0]) / (a[0] * a[0] + a[1] * a[1] + a[2]);
        double[] ret = {newA, newB, newC};
        return ret;
    }

    public double[] weirdFxn(double[] coords) {
        double a = (coords[1] * coords[1]) /
                ((coords[0] * coords[0]) + (coords[1] * coords[1]) +
                        coords[2]);

        double b = (coords[2]) /
                ((coords[0] * coords[0]) + (coords[1] * coords[1]) +
                        coords[2]);

        double c = (coords[0] * coords[0]) /
                ((coords[0] * coords[0]) + (coords[1] * coords[1]) +
                        coords[2]);

        return new double[]{a, b, c};
    }

    public double[] doNash(double[] a) {
        //double[] testCoords = {1,.75};
        double[] simplo = toSimplotope(a);
        //System.out.println(Arrays.toString(simplo));

        int[][] payoffsP1 = new int[2][2];
        payoffsP1[0][0] = 3;
        payoffsP1[1][0] = 2;
        payoffsP1[0][1] = 2;
        payoffsP1[1][1] = 4;

        int[][] payoffsP2 = new int[2][2];
        payoffsP2[0][0] = 2;
        payoffsP2[1][0] = 4;
        payoffsP2[0][1] = 3;
        payoffsP2[1][1] = -3;

        double[] p1Strat = {1, 0};
        double[] p2Strat = {.4, .6};
        //System.out.println(Arrays.toString(nashFxn(simplo,payoffsP1,payoffsP2)));

        return toSimplex(nashFxn(toSimplotope(a), payoffsP1, payoffsP2));
    }

    public double[] toSimplex(double[] coords) {
        double x = coords[0] * .5;
        double y = coords[1] * .5;
        double newX = 0;
        double newY = 0;
        if (x == 0) {
            newX = 2 * x;
            newY = 2 * y;
        } else {
            double k = y / x;
            double magX = 1 / (k + 1);
            double xDoubleBar = Math.sqrt(magX * magX + (1 - magX) * (1 - magX));

            double xSingleBar = 0;
            if (k >= 1) {
                double x2 = .5 / k;
                double y2 = .5;
                xSingleBar = Math.sqrt((x2) * (x2) + (y2) * (y2));
            } else {
                double x2 = .5;
                double y2 = .5 * k;
                xSingleBar = Math.sqrt((x2) * (x2) + (y2) * (y2));
            }
            newX = (xDoubleBar / xSingleBar) * x;

            newY = (xDoubleBar / xSingleBar) * y;
        }
        double[] newCoords = {newX, newY, 1 - newX - newY};
        return newCoords;
    }

    public double[] toSimplotope(double[] coords) {
        double x = coords[0];
        double y = coords[1];

        double newX = 0;
        double newY = 0;
        if (x == 0) {
            newX = x;
            newY = y;
        } else {
            double k = y / x;
            double magX = 1 / (k + 1);
            double xDoubleBar = Math.sqrt(magX * magX + (1 - magX) * (1 - magX));

            double xSingleBar = 0;
            if (k >= 1) {
                double x2 = 1 / k;
                double y2 = 1;
                xSingleBar = Math.sqrt((x2) * (x2) + (y2) * (y2));
            } else {
                double x2 = 1;
                double y2 = 1 * k;
                xSingleBar = Math.sqrt((x2) * (x2) + (y2) * (y2));
            }
            newX = (xSingleBar / xDoubleBar) * x;

            newY = (xSingleBar / xDoubleBar) * y;
        }
        double[] newCoords = {newX, newY};
        return newCoords;
    }

    public double expectedPayoff(double[] stratP1, double[] stratP2, int[][] P1) {
        return (stratP1[0] * P1[0][0] + stratP1[1] * P1[0][1]) * stratP2[0] +
                (stratP1[0] * P1[1][0] + stratP1[1] * P1[1][1]) * stratP2[1];
    }

    public double[] nashFxn(double[] strategies, int[][] P1, int[][] P2) {
        double[] pure1 = {1, 0};
        double[] pure2 = {0, 1};

        double s = strategies[0];
        double s2 = 1 - s;
        double s3 = strategies[1];
        double s4 = 1 - s3;
        double[] p1 = {s, s2};
        double[] p2 = {s3, s4};

        double newS = (s + Math.max(0, expectedPayoff(pure1, p2, P1) - expectedPayoff(p1, p2, P1))) /
                (1 + Math.max(0, expectedPayoff(pure1, p2, P1) - expectedPayoff(p1, p2, P1)) +
                        Math.max(0, expectedPayoff(pure2, p2, P1) - expectedPayoff(p1, p2, P1)));

        double newS3 = (s3 + Math.max(0, expectedPayoff(p1, pure1, P2) - expectedPayoff(p1, p2, P2))) /
                (1 + Math.max(0, expectedPayoff(p1, pure1, P2) - expectedPayoff(p1, p2, P2)) +
                        Math.max(0, expectedPayoff(p1, pure2, P2) - expectedPayoff(p1, p2, P2)));

        double[] newStrat = {newS, newS3};
        return newStrat;
    }
}
