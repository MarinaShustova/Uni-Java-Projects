package generators;

import domain.Block;
import domain.Parse;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class BytecodeGenerator {
	public byte[] generate(Parse parse) {
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
		classWriter.visit(V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
				"MuProg", null, "java/lang/Object", null);
		MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",
				"([Ljava/lang/String;)V", null, null);
		mv.visitCode();

		Block block = parse.getBlock();
		BlockGenerator blockGenerator = new BlockGenerator(mv);
		blockGenerator.generate(block);

		mv.visitInsn(RETURN);
		mv.visitMaxs(100, 2);
		mv.visitEnd();
		classWriter.visitEnd();
		return classWriter.toByteArray();
	}
}
