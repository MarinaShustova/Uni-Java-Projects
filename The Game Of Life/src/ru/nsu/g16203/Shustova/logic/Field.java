package ru.nsu.g16203.Shustova.logic;

import javafx.util.Pair;
import ru.nsu.g16203.Shustova.Settings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

public class Field {
    private LifeSettings settings = new LifeSettings();
    public ArrayList<HexaCell> field;
    private int mym, myn;
    public int AA;
    private Pair<Integer, Integer>[][] firstOffsets = new Pair[2][6], secondOffsets = new Pair[2][6];

    public void setSettings(LifeSettings l) {
        settings = l;
    }

    public Field(int n, int m, int size) {
        AA = 0;
        mym = m;
        myn = n;
        double width = Math.sqrt(3.0) * size;
        double height = 2 * size;
        field = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (i % 2 == 0) {
                for (int j = 0; j < m; ++j)
                    field.add(new HexaCell(i, j, (int) Math.round(width / 2 + j * width), (int) Math.round(height / 2 + 3 * i * (height / 4)), 0.0));
            } else
                for (int j = 0; j < m - 1; ++j)
                    field.add(new HexaCell(i, j, (int) Math.round(width * (j + 1)), (int) Math.round(height / 2 + 3 * i * (height / 4)), 0.0));
        }
        firstOffsets[0][0] = new Pair<>(0, 1);
        firstOffsets[0][1] = new Pair<>(-1, 0);
        firstOffsets[0][2] = new Pair<>(-1, -1);
        firstOffsets[0][3] = new Pair<>(0, -1);
        firstOffsets[0][4] = new Pair<>(1, -1);
        firstOffsets[0][5] = new Pair<>(1, 0);
        firstOffsets[1][0] = new Pair<>(0, 1);
        firstOffsets[1][1] = new Pair<>(-1, 1);
        firstOffsets[1][2] = new Pair<>(-1, 0);
        firstOffsets[1][3] = new Pair<>(0, -1);
        firstOffsets[1][4] = new Pair<>(1, 0);
        firstOffsets[1][5] = new Pair<>(1, 1);
        secondOffsets[0][0] = new Pair<>(-2, 0);
        secondOffsets[0][1] = new Pair<>(-1, -2);
        secondOffsets[0][2] = new Pair<>(1, -2);
        secondOffsets[0][3] = new Pair<>(2, 0);
        secondOffsets[0][4] = new Pair<>(1, 1);
        secondOffsets[0][5] = new Pair<>(-1, 1);
        secondOffsets[1][0] = new Pair<>(-2, 0);
        secondOffsets[1][1] = new Pair<>(-1, -1);
        secondOffsets[1][2] = new Pair<>(1, -1);
        secondOffsets[1][3] = new Pair<>(2, 0);
        secondOffsets[1][4] = new Pair<>(1, 2);
        secondOffsets[1][5] = new Pair<>(-1, 2);
    }

    public void changeState(HexaCell hexaCell) {
        AA += (hexaCell.isAlive()) ? (-1) : 1;
        hexaCell.changeState();
        calcImpact(hexaCell);
    }

    public HexaCell getSecondNeigbour(int i, int j, int dir) {
        Pair direction = secondOffsets[i % 2][dir];
        return getCell(i + (int) direction.getKey(), j + (int) direction.getValue(), field, mym, myn);
    }

    public HexaCell getFirstNeighbour(int i, int j, int dir) {
        Pair direction = firstOffsets[i % 2][dir];
        return getCell(i + (int) direction.getKey(), j + (int) direction.getValue(), field, mym, myn);
    }

    public HexaCell getCell(int row, int col, ArrayList<HexaCell> f, int m, int n) {
        if ((row < 0) || (row > n - 1) || (col < 0) || ((row % 2 == 0) && (col > m - 1)) || ((row % 2 != 0) && (col > m - 2)))
            throw new ArrayIndexOutOfBoundsException();
        int index = 0;
        for (int i = 0; i < row; ++i) {
            if (i % 2 == 0)
                index += m;
            else
                index += (m - 1);
        }
        index += col;
        return f.get(index);
    }

    public void step() {
        ArrayList<HexaCell> previousCondition = field.stream().map(HexaCell::new).collect(toCollection(ArrayList::new));
        boolean previousState;
        double imp;
        for (HexaCell hc : field) {
            imp = hc.getNewImpact();
            BigDecimal bd = new BigDecimal(imp);
            bd = bd.setScale(1, RoundingMode.HALF_UP);
            if (hc.isAlive()) {
                if ((bd.doubleValue() < settings.getLIVE_BEGIN()) || (bd.doubleValue() > settings.getLIVE_END()))
                    hc.changeState();
            } else {
                if ((bd.doubleValue() >= settings.getBIRTH_BEGIN()) && (bd.doubleValue() <= settings.getBIRTH_END()))
                    hc.changeState();
            }
        }
        for (HexaCell h : field) {
            previousState = getCell(h.getRow(), h.getCol(), previousCondition, mym, myn).isAlive();
            if (previousState != h.isAlive()) {
                AA += (previousState) ? (-1) : 1;
                calcImpact(h);
            }
        }
    }

    private void calcImpact(HexaCell hexaCell) {
        HexaCell curr;
        int impM = hexaCell.isAlive() ? 1 : -1;
        for (int i = 0; i < 6; ++i) {
            try {
                curr = getFirstNeighbour(hexaCell.getRow(), hexaCell.getCol(), i);
                curr.setNewImpact(curr.getNewImpact() + settings.getIMP1() * impM);
            } catch (ArrayIndexOutOfBoundsException e) { /*System.out.println("I'm fine!");*/ }
            try {
                curr = getSecondNeigbour(hexaCell.getRow(), hexaCell.getCol(), i);
                curr.setNewImpact(curr.getNewImpact() + settings.getIMP2() * impM);
            } catch (ArrayIndexOutOfBoundsException ex) { /*System.out.println("I'm ok!");*/ }
        }
    }

    public void resize(Settings oldS, Settings newS) {
        ArrayList<HexaCell> newField = new ArrayList<>();
//        m = newS.m; n = newS.n;
        double width = Math.sqrt(3.0) * newS.k;
        double height = 2 * newS.k;
        for (int i = 0; i < newS.n; ++i) {
            if (i % 2 == 0) {
                for (int j = 0; j < newS.m; ++j) {
                    newField.add(new HexaCell(i, j, (int) Math.round(width / 2 + j * width), (int) Math.round(height / 2 + 3 * i * (height / 4)), 0.0));
                    if ((i < oldS.n) && (j < oldS.m)) {
                        try {
                            getCell(i, j, newField, newS.m, newS.n).setState(getCell(i, j, field, mym, myn).isAlive());
                            getCell(i, j, newField, newS.m, newS.n).setOldImpact(getCell(i, j, field, mym, myn).getOldImpact());
                            getCell(i, j, newField, newS.m, newS.n).setNewImpact(getCell(i, j, field, mym, myn).getNewImpact());
                        }catch (ArrayIndexOutOfBoundsException e){/**/}
                    }
                }
            } else {
                for (int j = 0; j < newS.m - 1; ++j) {
                    newField.add(new HexaCell(i, j, (int) Math.round(width * (j + 1)), (int) Math.round(height / 2 + 3 * i * (height / 4)), 0.0));
                    if ((i < oldS.n) && (j < oldS.m - 1)) {
                        try {
                            getCell(i, j, newField, newS.m, newS.n).setState(getCell(i, j, field, mym, myn).isAlive());
                            getCell(i, j, newField, newS.m, newS.n).setOldImpact(getCell(i, j, field, mym, myn).getOldImpact());
                            getCell(i, j, newField, newS.m, newS.n).setNewImpact(getCell(i, j, field, mym, myn).getNewImpact());
                        }catch (ArrayIndexOutOfBoundsException e){/**/}
                    }
                }
            }
        }
        field = newField;
        mym = newS.m;
        myn = newS.n;
    }

//    public void reset() {
//        for (HexaCell hc : field)
//            hc.changeState();
//    }
}
