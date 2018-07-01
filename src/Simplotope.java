import java.util.Arrays;

public class Simplotope {

    public static void main(String[] args) {
        //double[] testCoords = {1,.3333};
        //System.out.println(Arrays.toString(toSimplotope(toSimplex(testCoords))));
        int[][] payoffsP1 = new int[2][2];
        payoffsP1[0][0] = 3;
        payoffsP1[1][0] = 2;
        payoffsP1[0][1] = 2;
        payoffsP1[1][1] = 4;

        int[][] payoffsP2 = new int[2][2];
        payoffsP1[0][0] = 2;
        payoffsP1[1][0] = 1;
        payoffsP1[0][1] = 3;
        payoffsP1[1][1] = 4;
        double[] p1Strat = {1, 0};
        double[] p2Strat = {.4, .6};
        double[] testNash = {0.25, 0.25, 0.25};
        double[] testCoords = {.5, 1};
        //System.out.println(Arrays.toString(toSimplotope(testNash)));
        //System.out.println(Arrays.toString(toSimplex(testCoords)));
        //System.out.println(Arrays.toString(nashFxn(p1Strat,payoffsP1,payoffsP1)));
        //System.out.println(Arrays.toString(doNash(testNash)));

        double[] testCoords3 = new double[] {.5,.75};
        double[] testCoords4 = new double[] {0.3961405464106081, 0.3541309442158207, 0.24972850937357108};
        double[] simplo = toSimplotope(testCoords4);
        //double[] testCoords4 = new double[] {0,0.5,.5};
        //double[] simplo = toSimplotope(testCoords4);
        System.out.println(Arrays.toString(simplo));
        //System.out.println(Arrays.toString(doNash(testCoords4)));
    }

    public static double[] doNash(double[] a){
        //double[] testCoords = {1,.75};
        double[] simplo = toSimplotope(a);
        System.out.println(Arrays.toString(simplo));

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
        System.out.println(Arrays.toString(nashFxn(simplo,payoffsP1,payoffsP2)));

        return toSimplex(nashFxn(toSimplotope(a),payoffsP1,payoffsP2));
    }
    public static double[] toSimplex(double[] coords){
        double x = coords[0]*.5;
        double y = coords[1]*.5;
        double newX = 0;
        double newY = 0;
        if(x == 0){
            newX = 2 * x;
            newY = 2 * y;
        } else {
            double k = y/x;
            double magX = 1/(k+1);
            double xDoubleBar = Math.sqrt(magX*magX + (1-magX)*(1-magX));

            double xSingleBar = 0;
            if(k >= 1){
                double x2 = .5/k;
                double y2 = .5;
                xSingleBar = Math.sqrt((x2)*(x2) + (y2)*(y2));
            } else {
                double x2 = .5;
                double y2 = .5*k;
                xSingleBar = Math.sqrt((x2)*(x2) + (y2)*(y2));
            }
            newX = (xDoubleBar/xSingleBar)*x;

            newY = (xDoubleBar/xSingleBar)*y;
        }
        double[] newCoords = {newX,newY,1-newX-newY};
        return newCoords;
    }

    public static double[] toSimplotope(double[] coords){
        double x = coords[0];
        double y = coords[1];

        double newX = 0;
        double newY = 0;
        if(x == 0){
            newX = x;
            newY = y;
        } else {
            double k = y/x;
            double magX = 1/(k+1);
            double xDoubleBar = Math.sqrt(magX*magX + (1-magX)*(1-magX));

            double xSingleBar = 0;
            if(k >= 1){
                double x2 = 1/k;
                double y2 = 1;
                xSingleBar = Math.sqrt((x2)*(x2) + (y2)*(y2));
            } else {
                double x2 = 1;
                double y2 = 1*k;
                xSingleBar = Math.sqrt((x2)*(x2) + (y2)*(y2));
            }
            newX = (xSingleBar/xDoubleBar)*x;

            newY = (xSingleBar/xDoubleBar)*y;
        }
        double[] newCoords = {newX,newY};
        return newCoords;
    }

    public static double expectedPayoff(double[] stratP1, double[] stratP2, int[][] P1){
        double payoff1 = (stratP1[0]*P1[0][0] + stratP1[1]*P1[0][1])*stratP2[0] +
                (stratP1[0]*P1[1][0] + stratP1[1]*P1[1][1])*stratP2[1];
        //double payoff2 = (stratP1[0]*P2[0][0] + stratP1[1]*P2[0][1])*stratP2[0] +
        //		(stratP1[0]*P2[1][0] + stratP1[1]*P2[1][1])*stratP2[1];
        //double[] payoffs = {payoff1, payoff2};
        //return payoffs;
        return payoff1;
    }

    public static double[] nashFxn(double[] strategies, int[][] P1, int[][] P2){
        double[] pure1 = {1,0};
        double[] pure2 = {0,1};

        double s = strategies[0];
        double s2 = 1 - s;
        double s3 = strategies[1];
        double s4 = 1 - s3;
        double[] p1 = {s,s2};
        double[] p2 = {s3, s4};
        //System.out.println(Arrays.toString(p1) + "p1 strat");
        //System.out.println(Arrays.toString(p2) + "p2 strat");
        double newS = (s + Math.max(0,expectedPayoff(pure1, p2,P1) - expectedPayoff(p1, p2,P1)))/
                (1 + Math.max(0,expectedPayoff(pure1, p2,P1) - expectedPayoff(p1, p2,P1)) +
                        Math.max(0,expectedPayoff(pure2, p2,P1) - expectedPayoff(p1, p2,P1)));

        //System.out.println(Math.max(0,expectedPayoff(pure1, p2,P1) - expectedPayoff(p1, p2,P1)));
        //System.out.println(Math.max(0,expectedPayoff(pure2, p2,P1) - expectedPayoff(p1, p2,P1)));
        //System.out.println(expectedPayoff(pure2, p2,P1) + "expected with pure2");
        //System.out.println(expectedPayoff(p1, p2,P1) + "expected with strat");
        //System.out.println(P1[0][0]);
        //System.out.println(P2[0][0]);
        //System.out.println(expectedPayoff(pure2, pure1,P1) + "expected with what i think it should be");
        double newS3 = (s3 + Math.max(0,expectedPayoff(p1, pure1,P2) - expectedPayoff(p1, p2,P2)))/
                (1 + Math.max(0,expectedPayoff(p1, pure1,P2) - expectedPayoff(p1, p2,P2)) +
                        Math.max(0,expectedPayoff(p1, pure2,P2) - expectedPayoff(p1, p2,P2)));

        double[] newStrat = {newS,newS3};
        return newStrat;
    }

}
