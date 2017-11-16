package com.lyne.common.instrument;

import com.lyne.common.visitor.MetricClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 *
 * 1、记录方法执行时间；
 * 2、通过javassist修改class 字节码。
 *
 * @author nn_liu
 * @Created 2017-11-16-16:51
 */

public class MetricClassTransformer implements ClassFileTransformer {

    @Override public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        //简化测试demo，直接写待修改的类(com/blueware/agent/TestTime)
        if (className != null) {
            //读取类的字节码流
            ClassReader reader = new ClassReader(classfileBuffer);
            //创建操作字节流值对象，ClassWriter.COMPUTE_MAXS:表示自动计算栈大小
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            //接受一个ClassVisitor子类进行字节码修改
            reader.accept(new MetricClassVisitor(writer, className), 8);
            //返回修改后的字节码流
            return writer.toByteArray();
        }
        return null;
    }

}
