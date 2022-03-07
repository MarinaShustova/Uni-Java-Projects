import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.Exception;
import java.nio.file.Paths;

import domain.Parse;
import generators.BytecodeGenerator;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import sun.misc.IOUtils;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            args = new String[]{"src/main/mu/test.mu"};
        }

        System.out.println("parsing: " + args[0]);

        ReducedMuLexer lexer = new ReducedMuLexer(new ANTLRFileStream(args[0]));
        ReducedMuParser parser = new ReducedMuParser(new CommonTokenStream(lexer));
        ParseVisitor parseVisitor = new ParseVisitor();
        Parse parse = parser.parse().accept(parseVisitor);
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        byte[] bytecode = bytecodeGenerator.generate(parse);
        String fileName = "MuProg.class";
        OutputStream os = new FileOutputStream(fileName);
        os.write(bytecode);
    }
}