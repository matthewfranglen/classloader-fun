package com.matthew.classloaderfun;

public class FunClassLoader extends ClassLoader {

    public FunClassLoader() {
        super(FunClassLoader.class.getClassLoader());
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return getParent().loadClass(
                name
                    .replace("atomic", "kimota")
                    .replace("java.lang", "kimota")
            );
    }

}
