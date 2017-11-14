package com.lyne.common;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

/**
 * @author nn_liu
 * @Created 2017-11-13-18:04
 */

public class TestClassVisitor extends ClassVisitor {

    public TestClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5,classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature,
            String superName, String[] interfaces){
        if (cv != null){
            cv.visit(version, access, name, signature, superName, interfaces);
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){

        if ("newFunc".equals(name)){
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            return new TestMethodVisitor(mv);
        }

        if (cv != null) {
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }

        return null;
    }

}

class TestMethodVisitor extends MethodVisitor {

    public TestMethodVisitor(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    @Override
    public void visitCode() {
        //方法体内开始时调用
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("========start=========");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        //每执行一个指令都会调用
        if (opcode == Opcodes.RETURN) {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("========end=========");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
        super.visitInsn(opcode);
    }
}
