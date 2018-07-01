package nplayernmove;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Test suite for the MathFunctions class.
 */
public class TestMathFunctions {

    public static void main(String[] args) {
        testExpectedPayoff();
        testHInverse();
        testNashFxn();
        testH();
    }

    private static void testExpectedPayoff() {
        final Map<int[], Integer> payoffsP1 = new HashMap<>();
        payoffsP1.put(new int[]{0,0}, 1);
        payoffsP1.put(new int[]{0,1}, 3);
        payoffsP1.put(new int[]{1,0}, 5);
        payoffsP1.put(new int[]{1,1}, 8);
        final PayoffMatrix matrixP1 = new PayoffMatrix(payoffsP1);

        //pure
        assertEquals(1, MathFunctions.expectedPayoff(new double[][]{new double[]{1,0}, new double[]{1,0}}, matrixP1));
        assertEquals(3, MathFunctions.expectedPayoff(new double[][]{new double[]{1,0}, new double[]{0,1}}, matrixP1));
        assertEquals(5, MathFunctions.expectedPayoff(new double[][]{new double[]{0,1}, new double[]{1,0}}, matrixP1));
        assertEquals(8, MathFunctions.expectedPayoff(new double[][]{new double[]{0,1}, new double[]{0,1}}, matrixP1));

        //non-pure
        assertEquals(4.25, MathFunctions.expectedPayoff(new double[][]{new double[]{0.5,0.5}, new double[]{0.5,0.5}}, matrixP1));
        assertEquals(4.6875, MathFunctions.expectedPayoff(new double[][]{new double[]{0.25,0.75}, new double[]{0.75,0.25}}, matrixP1));
    }

    private static void testH() {
        assertEquals(MathFunctions.h(new double[]{0.5, 0.75}), new double[]{0.3, 0.45, 0.25}, 1e-6);
        assertEquals(MathFunctions.h(new double[]{0.5, 0, 1}), new double[]{1.0 / 3.0, 0, 2.0 / 3.0, 0}, 1e-7);
    }

    private static void testHInverse() {
        //input must sum to 1
        assertEquals(MathFunctions.hInverse(new double[]{0.5, 0, 0.5, 0}), new double[]{1, 0, 1});
        assertEquals(MathFunctions.hInverse(new double[]{0.5, 0.5, 0}), new double[]{1, 1});
        assertEquals(MathFunctions.hInverse(new double[]{0.25, 0.75, 0}), new double[]{1.0/3.0, 1});
        assertEquals(MathFunctions.hInverse(new double[]{0, 1, 0}), new double[]{0, 1});
        assertEquals(MathFunctions.hInverse(new double[]{0, 0.5, 0.5}), new double[]{0, 0.5});
    }

    private static void testNashFxn() {
        final Map<int[], Integer> payoffsP1 = new HashMap<>();
        payoffsP1.put(new int[]{0,0}, 2);
        payoffsP1.put(new int[]{0,1}, 3);
        payoffsP1.put(new int[]{1,0}, 2);
        payoffsP1.put(new int[]{1,1}, 1);

        final Map<int[], Integer> payoffsP2 = new HashMap<>();
        payoffsP2.put(new int[]{0,0}, 3);
        payoffsP2.put(new int[]{0,1}, 5);
        payoffsP2.put(new int[]{1,0}, 2);
        payoffsP2.put(new int[]{1,1}, 3);

        final PayoffMatrix[] payoffMatrices = new PayoffMatrix[]{new PayoffMatrix(payoffsP1), new PayoffMatrix(payoffsP2)};

        assertEquals(MathFunctions.nashFxn(new double[][]{new double[]{1,0}, new double[]{1,0}}, payoffMatrices),
                new double[][]{new double[]{1,0}, new double[]{1.0/3.0, 2.0/3.0}});




        final Map<int[], Integer> payoffsP1_2 = new HashMap<>();
        payoffsP1_2.put(new int[]{0,0}, 3);
        payoffsP1_2.put(new int[]{0,1}, 2);
        payoffsP1_2.put(new int[]{1,0}, 2);
        payoffsP1_2.put(new int[]{1,1}, 4);

        final Map<int[], Integer> payoffsP2_2 = new HashMap<>();
        payoffsP2_2.put(new int[]{0,0}, 2);
        payoffsP2_2.put(new int[]{0,1}, 4);
        payoffsP2_2.put(new int[]{1,0}, 3);
        payoffsP2_2.put(new int[]{1,1}, -3);

        final PayoffMatrix[] payoffMatrices_2 = new PayoffMatrix[]{new PayoffMatrix(payoffsP1_2), new PayoffMatrix(payoffsP2_2)};

        assertEquals(MathFunctions.nashFxn(new double[][]{new double[]{1,0}, new double[]{1,0}}, payoffMatrices_2),
                new double[][]{new double[]{1,0}, new double[]{1.0/3.0, 2.0/3.0}});




        final Map<int[], Integer> payoffsP1_3 = new HashMap<>();
        payoffsP1_3.put(new int[]{0,0,0}, 2);
        payoffsP1_3.put(new int[]{1,0,0}, 2);
        payoffsP1_3.put(new int[]{0,1,0}, 1);
        payoffsP1_3.put(new int[]{0,0,1}, 1);
        payoffsP1_3.put(new int[]{1,1,0}, 2);
        payoffsP1_3.put(new int[]{1,0,1}, 2);
        payoffsP1_3.put(new int[]{0,1,1}, 2);
        payoffsP1_3.put(new int[]{1,1,1}, 1);

        final Map<int[], Integer> payoffsP2_3 = new HashMap<>();
        payoffsP2_3.put(new int[]{0,0,0}, 1);
        payoffsP2_3.put(new int[]{1,0,0}, 1);
        payoffsP2_3.put(new int[]{0,1,0}, 2);
        payoffsP2_3.put(new int[]{0,0,1}, 2);
        payoffsP2_3.put(new int[]{1,1,0}, 2);
        payoffsP2_3.put(new int[]{1,0,1}, 2);
        payoffsP2_3.put(new int[]{0,1,1}, 2);
        payoffsP2_3.put(new int[]{1,1,1}, 1);

        final Map<int[], Integer> payoffsP3_3 = new HashMap<>();
        payoffsP3_3.put(new int[]{0,0,0}, 1);
        payoffsP3_3.put(new int[]{1,0,0}, 2);
        payoffsP3_3.put(new int[]{0,1,0}, 2);
        payoffsP3_3.put(new int[]{0,0,1}, 2);
        payoffsP3_3.put(new int[]{1,1,0}, 1);
        payoffsP3_3.put(new int[]{1,0,1}, 1);
        payoffsP3_3.put(new int[]{0,1,1}, 1);
        payoffsP3_3.put(new int[]{1,1,1}, 2);

        final PayoffMatrix[] payoffMatrices_3 = new PayoffMatrix[]{new PayoffMatrix(payoffsP1_3), new PayoffMatrix(payoffsP2_3), new PayoffMatrix(payoffsP3_3)};

        assertEquals(MathFunctions.nashFxn(new double[][]{new double[]{1,0}, new double[]{0,1}, new double[]{1,0}}, payoffMatrices_3),
                new double[][]{new double[]{0.5,0.5}, new double[]{0,1}, new double[]{1,0}});
    }

    private static void assertEquals(double expected, double actual) {
        if(expected != actual) {
            throw new RuntimeException("Expected: " + expected + ", was " + actual);
        }
    }

    private static void assertEquals(double expected, double actual, double epsilon) {
        if(expected - epsilon > actual || expected + epsilon < actual) {
            throw new RuntimeException("Expected: " + expected + ", was " + actual);
        }
    }

    private static void assertEquals(double[] expected, double[] actual) {
        if(!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Expected: " + Arrays.toString(expected) + ", was " + Arrays.toString(actual));
        }
    }

    private static void assertEquals(double[] expected, double[] actual, double epsilon) {
        if(expected.length != actual.length) {
            throw new RuntimeException("Expected: " + Arrays.toString(expected) + ", was " + Arrays.toString(actual));
        }
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], epsilon);
        }
    }

    private static void assertEquals(double[][] expected, double[][] actual) {
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
