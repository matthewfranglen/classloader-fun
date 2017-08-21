package com.matthew.classloaderfun;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FunClassLoader extends ClassLoader {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public FunClassLoader() {
        super(FunClassLoader.class.getClassLoader());
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        logger.info("Loading {}", name);

        if (name.equals("java.lang.String")) {
            return findClass(name);
        } else {
            return super.loadClass(name);
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        logger.info("Finding {}", name);

        if (name.equals("atomic.Miracle")) {
            byte[] data = getClassData("BOOT-INF/classes/kimota/Miracle.class");
            byte[] fixed = substitute(data, "atomic".getBytes(StandardCharsets.UTF_8), 0x74, "kimota/".length());

            return defineClass(name, fixed, 0, fixed.length);
        }
        if (name.equals("java.lang.String")) {
            byte[] data = getClassData("BOOT-INF/classes/kimota/String.class");
            byte[] fixed = substitute(data, "java.lang".getBytes(StandardCharsets.UTF_8), 0x74, "kimota/".length());

            return defineClass(name, fixed, 0, fixed.length);
        }

        throw new ClassNotFoundException(name);
    }

    private byte[] getClassData(String name) throws ClassNotFoundException {
        try {
            return Resources.toByteArray(
                    Resources.getResource(name)
                );
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }
    }

    private byte[] substitute(byte[] original, byte[] replacement, int offset, int length) {
        byte[] result = new byte[original.length + replacement.length - length];
        int before = offset;
        int after = offset + length;
        int newAfter = offset + replacement.length;
        int afterLength = original.length - after;

        System.arraycopy(original, 0, result, 0, before);
        System.arraycopy(replacement, 0, result, before, replacement.length);
        System.arraycopy(original, after, result, newAfter, afterLength);

        return result;
    }

}
