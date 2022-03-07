package ru.nsu.g16203.Shustova.logic;


public class HexaCell {
    private int row, col;
    private boolean isAlive;
    private Double oldImpact, newImpact;
    private int xcenter, ycenter;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof HexaCell) {
            HexaCell that = (HexaCell) o;
            return (row == that.row) && (col == that.col);
        }
        return false;
    }

    public HexaCell(int i, int j, int a, int b, double imp) {
        row = i;
        col = j;
        xcenter = a;
        ycenter = b;
        isAlive = false;
        newImpact = imp;
        oldImpact = imp;
    }

    public HexaCell(HexaCell h) {
        row = h.row;
        col = h.col;
        xcenter = h.xcenter;
        ycenter = h.ycenter;
        isAlive = h.isAlive;
        newImpact = h.newImpact;
        oldImpact = h.oldImpact;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getXcenter() {
        return xcenter;
    }

    public int getYcenter() {
        return ycenter;
    }

    public Double getOldImpact() {
        return oldImpact;
    }

    public Double getNewImpact() {
        return newImpact;
    }

    public void setNewImpact(double imp) {
        newImpact = imp;
    }

    public void setOldImpact() {
        oldImpact = newImpact;
    }

    public void setOldImpact(double imp) {
        oldImpact = imp;
    }

    public void setState(boolean st) {
        isAlive = st;
    }

    public void changeState() {
        isAlive = !isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
