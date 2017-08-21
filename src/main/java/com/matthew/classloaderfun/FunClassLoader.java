package com.matthew.classloaderfun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        return getParent().loadClass(
                name
                    .replace("atomic", "kimota")
                    .replace("java.lang", "kimota")
            );
    }

}
