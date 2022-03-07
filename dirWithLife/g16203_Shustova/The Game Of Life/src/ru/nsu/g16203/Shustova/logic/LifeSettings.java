package ru.nsu.g16203.Shustova.logic;

public class LifeSettings {
    private double IMP1, IMP2, LIVE_BEGIN, LIVE_END, BIRTH_BEGIN, BIRTH_END;

    public LifeSettings() {
        IMP1 = 1.0;
        IMP2 = 0.3;
        LIVE_BEGIN = 2.0;
        LIVE_END = 3.3;
        BIRTH_BEGIN = 2.3;
        BIRTH_END = 2.9;
    }

    public void setIMP1(double n) {
        IMP1 = n;
    }

    public void setIMP2(double n) {
        IMP2 = n;
    }

    public void setLIVE_BEGIN(double n) {
        LIVE_BEGIN = n;
    }

    public void setLIVE_END(double n) {
        LIVE_END = n;
    }

    public void setBIRTH_BEGIN(double n) {
        BIRTH_BEGIN = n;
    }

    public void setBIRTH_END(double n) {
        BIRTH_END = n;
    }

    public double getIMP1() {
        return IMP1;
    }

    public double getIMP2() {
        return IMP2;
    }

    public double getLIVE_BEGIN() {
        return LIVE_BEGIN;
    }

    public double getLIVE_END() {
        return LIVE_END;
    }

    public double getBIRTH_BEGIN() {
        return BIRTH_BEGIN;
    }

    public double getBIRTH_END() {
        return BIRTH_END;
    }

}
