package com.lyne.premain.instrument;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author nn_liu
 * @Created 2017-11-02-18:57
 */

public class SimpleClassTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        // just print the class name
        System.out.println("loading..." + className);
        return classfileBuffer;
    }

}
