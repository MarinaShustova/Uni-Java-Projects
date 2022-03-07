package ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;

public class Scene {
    private ArrayList<Figure> figures, axis;
    private double scale;
    public double xMove, yMove, zMove, thetaX, thetaY, thetaZ;
    public Camera camera;
    public Matrix rotationMatrix;

    public Scene(Camera cam) {
        rotationMatrix = new Matrix(4, 4);
        figures = new ArrayList<>();
        axis = new ArrayList<>();
        thetaX = 0;
        thetaY = 0;
        thetaZ = 0;
        Matrix rotateX = new Matrix(4, 4), rotateY = new Matrix(4, 4), rotateZ = new Matrix(4, 4);
        rotateX.setTypeAndFill(Matrix.Type.TURNX, thetaX, 0.0, 0.0, 0.0);
        rotateY.setTypeAndFill(Matrix.Type.TURNY, thetaY, 0.0, 0.0, 0.0);
        rotateZ.setTypeAndFill(Matrix.Type.TURNZ, thetaZ, 0.0, 0.0, 0.0);
        rotationMatrix = rotateZ.mult(rotateY.mult(rotateX));
        camera = cam;
        setAxis();
    }

    private void setAxis() {
        Figure x, y, z;
        x = new Figure();
        y = new Figure();
        z = new Figure();
        x.baseEdges.add(new Pair<>(new Point3(0, 0, 0), new Point3(60, 0, 0)));
        y.baseEdges.add(new Pair<>(new Point3(0, 0, 0), new Point3(0, 60, 0)));
        z.baseEdges.add(new Pair<>(new Point3(0, 0, 0), new Point3(0, 0, 60)));
        x.newEdges.add(new Pair<>(new Point3(0, 0, 0), new Point3(60, 0, 0)));
        y.newEdges.add(new Pair<>(new Point3(0, 0, 0), new Point3(0, 60, 0)));
        z.newEdges.add(new Pair<>(new Point3(0, 0, 0), new Point3(0, 0, 60)));
        x.color = Color.RED;
        y.color = Color.GREEN;
        z.color = Color.BLUE;
        axis.add(x);
        axis.add(y);
        axis.add(z);
    }

    public void setFigures(ArrayList<Figure> figures) {
        this.figures = figures;
        axis = new ArrayList<>();
        for (Figure f : figures) {
            axis.add(f.axeX());
            axis.add(f.axeY());
            axis.add(f.axeZ());
        }
        if (figures.size() > 1)
            setAxis();
    }


    public void recreate() {
        calculateGabariteBox();
        Matrix finalTransform;
        Matrix Mproj = new Matrix(4, 4);
        Mproj.setTypeAndFill(Matrix.Type.PROJ, camera.sw, camera.sh, camera.zf, camera.zn);
        Matrix rotateMatrix = new Matrix(4, 4);
        rotateMatrix.setTypeAndFill(Matrix.Type.ROTATECS, 10.0, 0.0, 0.0, 0.0);
        Matrix moveMatrix = new Matrix(4, 4);
        moveMatrix.setTypeAndFill(Matrix.Type.MOVE, -10.0, 0, 0, 0.0);
        Matrix cameraMatrix = rotateMatrix.mult(moveMatrix);
        Matrix rotateX = new Matrix(4, 4), rotateY = new Matrix(4, 4);
        rotateX.setTypeAndFill(Matrix.Type.TURNY, thetaX, 0.0, 0.0, 0.0);
        rotateY.setTypeAndFill(Matrix.Type.TURNZ, thetaY, 0.0, 0.0, 0.0);
        thetaX = thetaY = 0;
        rotationMatrix = rotateX.mult(rotateY.mult(rotationMatrix));
        Matrix scaleMatrix = new Matrix(4, 4);
        Matrix moveMatrix2 = new Matrix(4, 4);
        moveMatrix2.setTypeAndFill(Matrix.Type.MOVE, xMove, yMove, zMove, 0.0); //Type 0 == MOVE
        scaleMatrix.setTypeAndFill(Matrix.Type.SCALE, 1.0 / scale, 1.0 / scale, 1.0 / scale, 0.0); //Type 2 == SCALE
        finalTransform = Mproj.mult(cameraMatrix.mult(rotationMatrix.mult(scaleMatrix.mult(moveMatrix2))));
        for (Figure f : figures) {
            f.transform(finalTransform);
            f.clip(1.0, 0.0);
        }
        for (Figure f : axis) {
            f.transform(finalTransform);
            f.clip(1.0, 0.0);
        }
    }

    public void moveFigure(int i, double x, double y, double z) {
        Figure toMove = figures.get(i);
        Figure axe1 = axis.get(i * 3), axe2 = axis.get(i * 3 + 1), axe3 = axis.get(i * 3 + 2);
        double cx = toMove.Cx, cy = toMove.Cy, cz = toMove.Cz, dx, dy, dz;
        dx = x - cx;
        dy = y - cy;
        dz = z - cz;
        toMove.Cx = x;
        toMove.Cy = y;
        toMove.Cz = z;
        Matrix moveMatrix = new Matrix(4, 4);
        moveMatrix.setTypeAndFill(Matrix.Type.MOVE, dx, dy, -dz, 0.0);
        toMove.transform(moveMatrix);
        axe1.transform(moveMatrix);
        axe2.transform(moveMatrix);
        axe3.transform(moveMatrix);
        toMove.positionMatrix = moveMatrix.mult(toMove.positionMatrix);
        axe1.positionMatrix = moveMatrix.mult(axe1.positionMatrix);
        axe2.positionMatrix = moveMatrix.mult(axe2.positionMatrix);
        axe3.positionMatrix = moveMatrix.mult(axe3.positionMatrix);
    }

    public void rotateFigure(int i, double theta, int axe) {
        Figure toMove = figures.get(i);
        Figure axe1 = axis.get(i * 3), axe2 = axis.get(i * 3 + 1), axe3 = axis.get(i * 3 + 2);
        Matrix moveMatrix = new Matrix(4, 4);
        moveMatrix.setTypeAndFill(Matrix.Type.MOVE, -toMove.Cx, -toMove.Cy, toMove.Cz, 0.0);
        toMove.positionMatrix = moveMatrix.mult(toMove.positionMatrix);
        axe1.positionMatrix = moveMatrix.mult(axe1.positionMatrix);
        axe2.positionMatrix = moveMatrix.mult(axe2.positionMatrix);
        axe3.positionMatrix = moveMatrix.mult(axe3.positionMatrix);
        toMove.transform(moveMatrix);
        axe1.transform(moveMatrix);
        axe2.transform(moveMatrix);
        axe3.transform(moveMatrix);
        double x = toMove.rx, y = toMove.ry, z = toMove.rz, dx, dy, dz;
        Matrix rot = new Matrix(4, 4);
        if (axe == 0) {
            dx = (theta - x) / 100;
            toMove.rx = (int) theta;
            rot.setTypeAndFill(Matrix.Type.TURNX, dx, 0, 0, 0.0);
        } else if (axe == 1) {
            dy = (theta - y) / 100;
            toMove.ry = (int) theta;
            rot.setTypeAndFill(Matrix.Type.TURNY, dy, 0, 0, 0.0);
        } else if (axe == 2) {
            dz = (theta - z) / 100;
            toMove.rz = (int) theta;
            rot.setTypeAndFill(Matrix.Type.TURNZ, dz, 0, 0, 0.0);
        }
        toMove.transform(rot);
        axe1.transform(rot);
        axe2.transform(rot);
        axe3.transform(rot);
        axe1.positionMatrix = rot.mult(axe1.positionMatrix);
        axe2.positionMatrix = rot.mult(axe2.positionMatrix);
        axe3.positionMatrix = rot.mult(axe3.positionMatrix);
        moveMatrix.setTypeAndFill(Matrix.Type.MOVE, toMove.Cx, toMove.Cy, -toMove.Cz, 0.0);
        toMove.transform(moveMatrix);
        axe1.transform(moveMatrix);
        axe2.transform(moveMatrix);
        axe3.transform(moveMatrix);
        axe1.positionMatrix = moveMatrix.mult(axe1.positionMatrix);
        axe2.positionMatrix = moveMatrix.mult(axe2.positionMatrix);
        axe3.positionMatrix = moveMatrix.mult(axe3.positionMatrix);
        toMove.positionMatrix = (rot).mult(toMove.positionMatrix);
        toMove.positionMatrix = moveMatrix.mult(toMove.positionMatrix);
    }

    private void calculateGabariteBox() {
        double xMax = 0, xMin = 0, yMax = 0, yMin = 0, zMax = 0, zMin = 0;
        boolean first = true;
        for (Figure figure : figures) {
            for (Pair<Point3, Point3> pair : figure.baseEdges) {
                if (first) {
                    xMax = pair.getKey().x;
                    xMin = pair.getKey().x;
                    yMax = yMin = pair.getKey().y;
                    zMax = zMin = pair.getKey().z;
                    first = false;
                } else if (pair.getKey().x < xMin)
                    xMin = pair.getKey().x;
                else if (pair.getKey().x > xMax)
                    xMax = pair.getKey().x;
                else if (pair.getKey().y < yMin)
                    yMin = pair.getKey().y;
                else if (pair.getKey().y > yMax)
                    yMax = pair.getKey().y;
                else if (pair.getKey().z < zMin)
                    zMin = pair.getKey().z;
                else if (pair.getKey().z < zMax)
                    zMax = pair.getKey().z;
            }
        }
        double xScale = (xMax - xMin) / 2.0;
        double yScale = (yMax - yMin) / 2.0;
        double zScale = (zMax - zMin) / 2.0;
        scale = Math.max(Math.max(xScale, yScale), zScale);
        xMove = -1 * (int) Math.round((xMax + xMin) / 2.0);
        yMove = -1 * (int) Math.round((yMax + yMin) / 2.0);
        zMove = -1 * (int) Math.round((zMax + zMin) / 2.0);
    }

    public void resetScene() {
        for (Figure f : figures) {
            f.reset();
        }
        for (Figure f : axis) {
            f.reset();
        }
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public ArrayList<Figure> getAxis() {
        return axis;
    }
}