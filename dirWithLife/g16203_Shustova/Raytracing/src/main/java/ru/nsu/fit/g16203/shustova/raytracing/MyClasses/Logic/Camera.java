package ru.nsu.fit.g16203.shustova.raytracing.MyClasses.Logic;

public class Camera {
    public Point3 Pcam, Pview, Vup;
    public double zn = 1, zf = 11, sw = 1, sh = 1;
    public Camera(){
        Pcam = new Point3(-10.0, 0.0, 0.0);
        Pview = new Point3(10.0, 0.0, 0.0);
        Vup = new Point3(0.0, 1.0, 0.0);
    }
}
