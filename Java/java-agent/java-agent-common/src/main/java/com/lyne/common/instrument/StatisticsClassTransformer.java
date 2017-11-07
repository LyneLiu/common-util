package com.lyne.common.instrument;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

import static org.objectweb.asm.Opcodes.ACC_PROTECTED;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

/**
 * Java字节码框架ASM
 * 1、ClassReader: 负责读取字节码；
 * 2、CalssVisitor：通过visitor模式访问字节码。
 *
 * http://simpleframework.net/news/view?newsId=f21f7c437a034a358d44e86d4ddce3ab
 * http://www.acyouzi.com/2016/12/09/java-asm/
 * https://my.oschina.net/u/1166271/blog/220011
 *
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
