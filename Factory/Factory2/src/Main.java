import factory.*;
public class Main {
    public static void main(String args[]){
        Informator inf = new Informator();
        inf.getInf();
        AccessoryStorage aS = new AccessoryStorage(inf.accessoryss);
        BodyStorage bS = new BodyStorage(inf.bodyss);
        EngineStorage eS = new EngineStorage(inf.enginess);
        CarStorage cS = new CarStorage(inf.carss);
        EngineProducer ep = new EngineProducer(eS, inf.dpause);
        BodyProducer bp = new BodyProducer(bS, inf.dpause);

        AccessorySupplier[] sups = new AccessorySupplier[inf.suppliers];
        for (int i = 0; i < inf.suppliers; ++i) {
            sups[i] = new AccessorySupplier(aS, inf.dpause);
            sups[i].start();
        }
        ep.start();
        bp.start();

        Worker[] ws = new Worker[inf.workers];
        for (int i = 0; i < inf.workers; ++i) {
            ws[i] = new Worker(aS, bS, eS, cS, inf.cpause);
            ws[i].start();
        }

        Dealer[] ds = new Dealer[inf.dealers];
        for (int i = 0; i < inf.dealers; ++i) {
            ds[i] = new Dealer(cS, inf.dealpause);
            ds[i].start();
        }

        GWindow gw = new GWindow(sups, bp, ep, ws, ds);
        gw.setVisible(true);
    }

}
