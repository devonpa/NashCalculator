package nplayernmove;

import java.util.Arrays;

public class Master {

    public static void main(String[] args) {
        //triangle stuff
        double[] x = { 1, 0, 0 };
        double[] y = { 0, 1, 0 };
        double[] z = { 0, 0, 1 };

        CoordsAndLabel xCoordsAndLabel = new CoordsAndLabel(x, 1);
        CoordsAndLabel yCoordsAndLabel = new CoordsAndLabel(y, 2);
        CoordsAndLabel zCoordsAndLabel = new CoordsAndLabel(z, 3);
        Simplex test = new Simplex(xCoordsAndLabel, yCoordsAndLabel, zCoordsAndLabel);
        Simplex[] testBSD = test.BSD();
        Simplex[] testBSDFull = getFullBSD(testBSD);
        //printTriArray(testBSDFull);

        Simplex[] testBSD0 = testBSD[0].BSD();
        Simplex[] testBSD1 = testBSD[1].BSD();
        Simplex[] testBSD2 = testBSD[2].BSD();
        Simplex[] testBSD3 = testBSD[3].BSD();
        Simplex[] testBSD4 = testBSD[4].BSD();
        Simplex[] testBSD5 = testBSD[5].BSD();

        Simplex[] testBSDBSD = concat(concat(concat(concat(concat(testBSD0, testBSD1),testBSD2),testBSD3),testBSD4),testBSD5);
        Simplex[] finalTest = getFullBSD(testBSDBSD);

        printTriArray(testBSDBSD);

        iterateBSD(test);
    }

    public static void iterateBSD(Simplex tri){
        printTriArray(getFullBSD(iterateBSDAux(tri.BSD(),1,0)));
    }

    public static Simplex[] iterateBSDAux(Simplex[] testBSD, int iter, int curIter){
        if(curIter>=iter){
            return testBSD;
        }

        Simplex[] testBSD0 = testBSD[0].BSD();
        Simplex[] testBSD1 = testBSD[1].BSD();
        Simplex[] testBSD2 = testBSD[2].BSD();
        Simplex[] testBSD3 = testBSD[3].BSD();
        Simplex[] testBSD4 = testBSD[4].BSD();
        Simplex[] testBSD5 = testBSD[5].BSD();

        testBSD0 = iterateBSDAux(testBSD0,iter,curIter+1);
        testBSD1 = iterateBSDAux(testBSD1,iter,curIter+1);
        testBSD2 = iterateBSDAux(testBSD2,iter,curIter+1);
        testBSD3 = iterateBSDAux(testBSD3,iter,curIter+1);
        testBSD4 = iterateBSDAux(testBSD4,iter,curIter+1);
        testBSD5 = iterateBSDAux(testBSD5,iter,curIter+1);

        return concat(concat(concat(concat(concat(testBSD0, testBSD1),testBSD2),testBSD3),testBSD4),testBSD5);
    }

    public static Simplex[] concat(Simplex[] a, Simplex[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Simplex[] c= new Simplex[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public static void printTri(Simplex tris) {
        Simplex cur = tris;
        double[] coords = cur.getCoords();
        System.out.println(Arrays.toString(coords) + cur.getLabels());
    }

    public static void printTriArray(Simplex[] tris) {
        for (int i = 0; i < tris.length; i++) {
            Simplex cur = tris[i];
            if(tris[i] != null){
                double[] coords = cur.getCoords();
                System.out.println(Arrays.toString(coords) + Arrays.toString(cur.getLabels()));
            }

        }
    }

    public static Simplex[] getFullBSD(Simplex[] tris){
        Simplex[] ret = new Simplex[100];
        int j = 0;
        for(int i = 0; i < tris.length; i++){
            if (tris[i].isFull()){
                ret[j] = tris[i];
                j++;
            }
        }
        return ret;


    }

}
