Class Loader Fun
================

This is just a custom class loader that attempts to load a java class in a way to produce two instances of the same class that are different.

java.lang.String
----------------

This is prohibited!

```
Exception in thread "Thread-3" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
        at com.matthew.classloaderfun.ClassloaderFunApplication.execute(ClassloaderFunApplication.java:31)
        at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.reflect.InvocationTargetException
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.matthew.classloaderfun.ClassloaderFunApplication.execute(ClassloaderFunApplication.java:27)
        ... 1 more
Caused by: java.lang.SecurityException: Prohibited package name: java.lang
        at java.lang.ClassLoader.preDefineClass(ClassLoader.java:662)
        at java.lang.ClassLoader.defineClass(ClassLoader.java:761)
        at java.lang.ClassLoader.defineClass(ClassLoader.java:642)
        at com.matthew.classloaderfun.FunClassLoader.findClass(FunClassLoader.java:47)
        at com.matthew.classloaderfun.FunClassLoader.loadClass(FunClassLoader.java:28)
        at fatterman.Miracle.shazam(Miracle.java:6)
        ... 6 more
```
