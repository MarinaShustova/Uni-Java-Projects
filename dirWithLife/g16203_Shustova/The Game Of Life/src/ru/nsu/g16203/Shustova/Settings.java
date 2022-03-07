package ru.nsu.g16203.Shustova;

import ru.nsu.g16203.Shustova.logic.LifeSettings;

public class Settings {
    public int m, n, w, k;
    public boolean isXOR;
    public boolean isImpacts = false;
    public LifeSettings lifeSettings = new LifeSettings();

    public Settings(int a, int b, int c, int d, boolean e) {
        m = a;
        n = b;
        w = c;
        k = d;
        isXOR = e;
    }

    public void setLifeSettings(LifeSettings l) {
        lifeSettings = l;
    }
}
