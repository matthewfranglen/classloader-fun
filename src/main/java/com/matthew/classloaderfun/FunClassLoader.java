package com.matthew.classloaderfun;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FunClassLoader extends ClassLoader {

    private static final int[] MIRACLE_OFFSETS = {0x62, 0xd0};

    private static final int[] MAGIC_WORD_OFFSETS = {0x51, 0x86, 0xaa, 0xf9, 0x198, 0x1dc};

    private static final int MAGIC_WORD_FIRST_OFFSET = 0x51;
    private static final int MAGIC_WORD_SECOND_OFFSET = 0xaa;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public FunClassLoader() {
        super(FunClassLoader.class.getClassLoader());
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        logger.info("Loading {}", name);

        if (name.equals("fatterman.MagicWord")) {
            return findClass(name);
        } else {
            return super.loadClass(name);
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        logger.info("Finding {}", name);

        if (name.equals("fatterman.Miracle")) {
            byte[] data = getClassData("BOOT-INF/classes/marvelman/Miracle.class");
            byte[] fixed = substitute(data, "marvelman/", "fatterman/", MIRACLE_OFFSETS);

            return defineClass(name, fixed, 0, fixed.length);
        }
        if (name.equals("fatterman.MagicWord")) {
            byte[] data = getClassData("BOOT-INF/classes/marvelman/MagicWord.class");
            byte[] fixed = substitute(data, "marvelman/", "fatterman/", MAGIC_WORD_OFFSETS);

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

    private byte[] substitute(byte[] original, String old, String replacement, int[] offsets) {
        byte[] result = original;
        for (int offset : offsets) {
            result = substituteOnce(result, old, replacement, offset);
        }
        return result;
    }

    private byte[] substituteOnce(byte[] original, String old, String replacement, int offset) {
        byte[] replacementBytes = replacement.getBytes(StandardCharsets.UTF_8);
        int length = old.length();
        int before = offset;
        int after = offset + length;
        int newAfter = offset + replacementBytes.length;
        int afterLength = original.length - after;

        byte[] result = new byte[original.length + replacementBytes.length - length];

        System.arraycopy(original, 0, result, 0, before);
        System.arraycopy(replacementBytes, 0, result, before, replacementBytes.length);
        System.arraycopy(original, after, result, newAfter, afterLength);

        return result;
    }

}
