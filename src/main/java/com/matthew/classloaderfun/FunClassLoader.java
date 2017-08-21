package com.matthew.classloaderfun;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
            return defineClass(name, data, 0, data.length);
        }
        if (name.equals("java.lang.String")) {
            byte[] data = getClassData("BOOT-INF/classes/kimota/String.class");
            return defineClass(name, data, 0, data.length);
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

}
