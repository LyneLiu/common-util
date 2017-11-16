package com.lyne;

import com.lyne.common.visitor.MetricCostCache;
import org.objectweb.asm.*;

import java.io.IOException;

/**
 * @author nn_liu
 * @Created 2017-11-03-13:27
 */

public class ClassReaderTest {

    public static void main(String[] args) throws IOException {
        ClassReader classReader = new ClassReader("java.lang.String");
        classReader.accept(new MyClassVisitor(), 0);
    }

    private static class MyClassVisitor extends ClassVisitor {

        public MyClassVisitor() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visit(int version, int access, String name,
                String signature, String superName, String[] interfaces) {
            System.out.println("class name:" + name);
            System.out.println("super class name:" + superName);
            System.out.println("class version:" + version);
            System.out.println("class access:" + access);
            System.out.println("class signature:" + signature);

            if ((interfaces != null) && (interfaces.length > 0)) {
                for (String str : interfaces) {
                    System.out.println("implemented interface name:" + str);
                }
            }
        }
    }

}
