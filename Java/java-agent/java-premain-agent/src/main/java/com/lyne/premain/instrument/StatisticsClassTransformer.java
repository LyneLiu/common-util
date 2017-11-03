package com.lyne.premain.instrument;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.List;

import static org.objectweb.asm.Opcodes.ACC_PROTECTED;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

/**
 * @author nn_liu
 * @Created 2017-11-03-9:41
 */

public class StatisticsClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {

        System.out.println("Processing class... : " + className);

        ClassReader classReader = null;
        try {
            classReader = new ClassReader(className);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.SKIP_DEBUG);

        @SuppressWarnings("unchecked")
        List<MethodNode> allMethods = classNode.methods;
        for (MethodNode methodNode : allMethods){
            // print the public/protected/static method
            if ((methodNode.access & ACC_PUBLIC) == 1 || (methodNode.access & ACC_PROTECTED) == 1 || (methodNode.access & ACC_STATIC) == 1){
                System.out.println(methodNode.name);
            }
        }

        System.out.println("End processing class... : " + className);
        System.out.println();
        return classfileBuffer;
    }
}
