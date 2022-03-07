import javassist.*;
import java.io.*;
import java.lang.instrument.Instrumentation;

public class SimpleAgent {
    private static Instrumentation globalInstrumentation;

    public static void premain(String args, Instrumentation inst){
        globalInstrumentation = inst;
        Class[] classes = globalInstrumentation.getAllLoadedClasses();
        for (Class aClass : classes) {
            System.out.println(aClass.getName());
        }
        System.out.println(classes.length);
        customizeTiming();
    }

//    private static void customizeMeasuring(Class cl){
//        ClassPool cp = ClassPool.getDefault();
//        try {
//            CtClass cc = cp.get(cl.getName());
//            CtConstructor[] cm = cc.getConstructors();
//            for (CtConstructor ctConstructor : cm) {
//                ctConstructor.insertAfter("{SimpleAgent.getAndLogObjectSize(this);}");
//            }
//            cc.detach();
//        } catch (NotFoundException | CannotCompileException e) {
//            e.printStackTrace();
//        }
//    }

    private static void customizeTiming(){
        ClassPool pool = ClassPool.getDefault();
        CtClass cc;
        try {
            cc = pool.get("TransactionProcessor");
            CtField f = new CtField(CtClass.longType, "start123", cc);
            cc.addField(f);
            CtMethod m = cc.getDeclaredMethod("processTransaction");
            m.insertBefore("{ start123 = System.currentTimeMillis(); }");
            m.insertAfter("{System.out.println(System.currentTimeMillis() - start123 + \" milliseconds\");}");
            cc.writeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void getAndLogObjectSize(final Object object)
//    {
//        if (globalInstrumentation == null) {
//            throw new IllegalStateException("Agent not initialized.");
//        }
//        long size = globalInstrumentation.getObjectSize(object);
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+object.getClass().getName() + " => " + size+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//    }
//
//    private static BufferedWriter getWriter(){
//        File f = new File("log.txt");
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(f);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        assert fos != null;
//        return new BufferedWriter(new OutputStreamWriter(fos));
//    }

//    private static void log(BufferedWriter bw, Class c){
//        try {
//            bw.write(c.getName() + " => " + getObjectSize(c)+"\n");
//            bw.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
