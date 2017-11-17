package com.lyne.common.visitor;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * https://juejin.im/entry/58be2619128fe1006452c8c3
 * @author nn_liu
 * @Created 2017-11-16-18:37
 */

public class MetricClassVisitor extends ClassVisitor {

    private String className;

    public MetricClassVisitor(ClassVisitor cv, String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, final String name, final String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
            // 是否打印耗时,默认不开启
            private boolean inject = false;

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {

                if (Type.getDescriptor(MetricCost.class).equals(desc)){
                    inject = true;
                }

                return super.visitAnnotation(desc, visible);
            }

            @Override protected void onMethodEnter() {
                if(inject){
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn("========start=========");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                            "(Ljava/lang/String;)V", false);

                    mv.visitLdcInsn(name);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
                    mv.visitMethodInsn(INVOKESTATIC, "com/lyne/common/visitor/MetricCostCache",
                            "setStartTime", "(Ljava/lang/String;J)V", false);
                }
            }

            @Override protected void onMethodExit(int opcode) {
                if(inject){
                    mv.visitLdcInsn(name);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
                    mv.visitMethodInsn(INVOKESTATIC, "com/lyne/common/visitor/MetricCostCache",
                            "setEndTime", "(Ljava/lang/String;J)V", false);
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

                    mv.visitLdcInsn(name);
                    mv.visitMethodInsn(INVOKESTATIC, "com/lyne/common/visitor/MetricCostCache",
                            "getCostTime", "(Ljava/lang/String;)Ljava/lang/String;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                            "(Ljava/lang/String;)V", false);
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn("========end=========");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                            "(Ljava/lang/String;)V", false);
                }
            }

        };
        return mv;
    }

}


