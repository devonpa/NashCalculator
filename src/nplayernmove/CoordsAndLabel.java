package nplayernmove;

import java.util.Arrays;

public class CoordsAndLabel {


    private double[] coordinates;
    private int label;

    public CoordsAndLabel(double[]tri, int label){
        this.coordinates = tri;
        this.label = label;
    }

    public double[] getCoordinates(){
        return coordinates;
    }

    public boolean equals(CoordsAndLabel other){
        return this == other || (Arrays.equals(coordinates, other.getCoordinates()) && label == other.getLabel());
    }

    public int getLabel(){
        return label;
    }

    public void setLabel(int newLabel){
        label = newLabel;
    }
}
