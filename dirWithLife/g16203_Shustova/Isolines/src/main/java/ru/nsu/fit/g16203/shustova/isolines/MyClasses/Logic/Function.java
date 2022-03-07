package ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic;

public class Function {
    private Double min = -1.0, max = 1.0;
//    private Double min = 0.0, max = 8 * Math.PI*Math.PI;

    public double getValue(double x, double y){
//        return (Math.sin(x*y*y));
        return (Math.sin(x))*(Math.cos(y));
//        return x*x+y*y;
    }
    public double getMin(){
        return min;
    }
    public double getMax(){
        return max;
    }
}
