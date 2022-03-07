package ru.nsu.fit.g16203.shustova.raytracing.MyClasses.Logic;

public class Matrix {
    public enum Type{
        MOVE,
        VECTOR,
        SCALE,
        ROTATECS,
        TURNX,
        TURNY,
        TURNZ,
        PROJ,
        ROTATEX,
        ROTATEY,
        ROTATEZ,
        I,
        J,
        K,
        E
    }
    public double[][] body;
    private int m, n;

    public Matrix(int m, int n) {
        body = new double[m][n];
        this.m = m;
        this.n = n;
    }

    public void setTypeAndFill(Type type, double a, double b, double c, double d) {
        if (type == Type.MOVE) {
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i == j)
                        body[i][j] = 1;
                    else if (j == n - 1) {
                        if (i == 0)
                            body[i][j] = a;
                        else if (i == 1)
                            body[i][j] = b;
                        else
                            body[i][j] = c;
                    } else
                        body[i][j] = 0;
                }
            }
        }
        else if (type == Type.VECTOR) {
            body[0][0] = a;
            body[1][0] = b;
            body[2][0] = c;
            body[3][0] = 1;
        }
        else if (type == Type.SCALE) {
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i == j) {
                        if (i == 0)
                            body[i][j] = a;
                        else if (i == 1)
                            body[i][j] = b;
                        else if (i == 2)
                            body[i][j] = c;
                        else if (i == 3)
                            body[i][j] = 1;
                    } else
                        body[i][j] = 0;
                }
            }
        }
        else if (type == Type.ROTATECS) {
            body[0][0] = 0.0;
            body[0][1] = 0.0;
            body[0][2] = 1.0;
            body[0][3] = 0.0;
            body[1][0] = 0.0;
            body[1][1] = -1.0;
            body[1][2] = 0.0;
            body[1][3] = 0.0;
            body[2][0] = -1.0;
            body[2][1] = 0.0;
            body[2][2] = 0.0;
            body[2][3] = 0.0;
            body[3][0] = 0.0;
            body[3][1] = 0.0;
            body[3][2] = 0.0;
            body[3][3] = 1.0;
        }
        else if (type == Type.TURNX) {
            body[0][0] = 1.0;
            body[0][1] = 0.0;
            body[0][2] = 0.0;
            body[0][3] = 0.0;
            body[1][0] = 0.0;
            body[1][1] = Math.cos(a);
            body[1][2] = -1*Math.sin(a);
            body[1][3] = 0.0;
            body[2][0] = 0.0;
            body[2][1] = Math.sin(a);
            body[2][2] = Math.cos(a);
            body[2][3] = 0.0;
            body[3][0] = 0.0;
            body[3][1] = 0.0;
            body[3][2] = 0.0;
            body[3][3] = 1.0;
        }
        else if (type == Type.TURNY){
            body[0][0] = Math.cos(a);
            body[0][1] = 0.0;
            body[0][2] = Math.sin(a);
            body[0][3] = 0.0;
            body[1][0] = 0.0;
            body[1][1] = 1.0;
            body[1][2] = 0.0;
            body[1][3] = 0.0;
            body[2][0] = -1*Math.sin(a);
            body[2][1] = 0.0;
            body[2][2] = Math.cos(a);
            body[2][3] = 0.0;
            body[3][0] = 0.0;
            body[3][1] = 0.0;
            body[3][2] = 0.0;
            body[3][3] = 1.0;
        }
        else if (type == Type.TURNZ) {
            body[0][0] = Math.cos(a);
            body[0][1] = -1*Math.sin(a);
            body[0][2] = 0.0;
            body[0][3] = 0.0;
            body[1][0] = Math.sin(a);
            body[1][1] = Math.cos(a);
            body[1][2] = 0.0;
            body[1][3] = 0.0;
            body[2][0] = 0.0;
            body[2][1] = 0.0;
            body[2][2] = 1.0;
            body[2][3] = 0.0;
            body[3][0] = 0.0;
            body[3][1] = 0.0;
            body[3][2] = 0.0;
            body[3][3] = 1.0;
        }
        else if (type == Type.PROJ){ //sw, sh, zf, zn
//            System.out.println(a+" "+b+" "+c+" "+d);
            body[0][0] = 2*d/a;
            body[0][1] = 0.0;
            body[0][2] = 0.0;
            body[0][3] = 0.0;
            body[1][0] = 0.0;
            body[1][1] = 2*d/b;
            body[1][2] = 0.0;
            body[1][3] = 0.0;
            body[2][0] = 0.0;
            body[2][1] = 0.0;
            body[2][2] = c/(c-d);
            body[2][3] = -1*c*d/(c-d);
            body[3][0] = 0.0;
            body[3][1] = 0.0;
            body[3][2] = 1.0;
            body[3][3] = 0.0;
        }
        else if (type == Type.ROTATEX){
            Matrix v = new Matrix(3,1), vT = new Matrix(1,3), vDual = new Matrix(3,3);
            Matrix R, Ematrix = new Matrix(3,3);
            Ematrix.setTypeAndFill(Type.E,0,0,0,0);
            v.setTypeAndFill(Type.I, 0,0,0,0);
            vT.transp(v);
            vDual.dual(v);
            R = ((v.mult(vT)).add(Ematrix.sub(v.mult(vT)).mult(Math.cos(a)))).add(vDual.mult(Math.sin(a)));
            copyAndAddW(R);
        }
        else if (type == Type.ROTATEY){
            Matrix v = new Matrix(3,1), vT = new Matrix(1,3), vDual = new Matrix(3,3);
            Matrix R, Ematrix = new Matrix(3,3);
            Ematrix.setTypeAndFill(Type.E,0,0,0,0);
            v.setTypeAndFill(Type.J, 0,0,0,0);
            vT.transp(v);
            vDual.dual(v);
            R = ((v.mult(vT)).add(Ematrix.sub(v.mult(vT)).mult(Math.cos(a)))).add(vDual.mult(Math.sin(a)));
            copyAndAddW(R);
        }
        else if (type == Type.ROTATEZ){
            Matrix v = new Matrix(3,1), vT = new Matrix(1,3), vDual = new Matrix(3,3);
            Matrix R, Ematrix = new Matrix(3,3);
            Ematrix.setTypeAndFill(Type.E,0,0,0,0);
            v.setTypeAndFill(Type.K, 0,0,0,0);
            vT.transp(v);
            vDual.dual(v);
            R = ((v.mult(vT)).add(Ematrix.sub(v.mult(vT)).mult(Math.cos(a)))).add(vDual.mult(Math.sin(a)));
            copyAndAddW(R);
        }
        else if (type == Type.I){
            body[0][0] = 1;
            body[1][0] = 0;
            body[2][0] = 0;
        }
        else if (type == Type.J){
            body[0][0] = 0;
            body[1][0] = 1;
            body[2][0] = 0;
        }
        else if (type == Type.K){
            body[0][0] = 0;
            body[1][0] = 0;
            body[2][0] = 1;
        }
        else if (type == Type.E){
            for (int i = 0; i < m; ++i)
                for (int j =0; j < n; ++j){
                    if (i == j)
                        body[i][j] = 1.0;
                    else
                        body[i][j] = 0.0;
                }
        }
    }

    private void copyAndAddW(Matrix matrix){
        for (int i = 0; i < matrix.getM(); ++i)
            for (int j =0; j < matrix.getN(); ++j){
                body[i][j] = matrix.body[i][j];
            }
        for (int i = 0; i < m; ++i){
            body[i][n-1] = 0.0;
        }
        for (int i = 0; i < n; ++i){
            body[m-1][i] = 0.0;
        }
        body[m-1][n-1] = 1.0;
    }

    private void transp(Matrix matr){
        for (int i = 0; i < m; ++i)
            for (int j =0; j < n; ++j){
                body[i][j] = matr.body[j][i];
            }
    }

    private void dual(Matrix matr){
        body[0][0] = 0.0;
        body[0][1] = -1*matr.body[2][0];
        body[0][2] = matr.body[1][0];
        body[1][0] = matr.body[2][0];
        body[1][1] = 0.0;
        body[1][2] = -1*matr.body[0][0];
        body[2][0] = -1*matr.body[1][0];
        body[2][1] = matr.body[0][0];
        body[2][2] = 0.0;
    }

    public Matrix mult(Matrix matrix) {
        Matrix res = new Matrix(m, matrix.getN());
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < matrix.getN(); j++) {
                res.body[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    res.body[i][j] += body[i][k] * matrix.body[k][j];
                }
            }
        }
        return res;
    }

    public Matrix mult(double a) {
        Matrix res = new Matrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res.body[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    res.body[i][j] = body[i][k] * a;
                }
            }
        }
        return res;
    }

    public Matrix add(Matrix matrix){
        Matrix res = new Matrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res.body[i][j] = body[i][j] + matrix.body[i][j];
            }
        }
        return res;
    }

    public Matrix sub(Matrix matrix){
        Matrix res = new Matrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res.body[i][j] = body[i][j] - matrix.body[i][j];
            }
        }
        return res;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }
}
