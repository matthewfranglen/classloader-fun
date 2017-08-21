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

java.time.DayOfWeek
-------------------

It's just all of `java.` really. This code is executed before a class is defined (in java.lang.ClassLoader):

```java
    private ProtectionDomain preDefineClass(String name,
                                            ProtectionDomain pd)
    {
        if (!checkName(name))
            throw new NoClassDefFoundError("IllegalName: " + name);

        // Note:  Checking logic in java.lang.invoke.MemberName.checkForTypeAlias
        // relies on the fact that spoofing is impossible if a class has a name
        // of the form "java.*"
        if ((name != null) && name.startsWith("java.")) {
            throw new SecurityException
                ("Prohibited package name: " +
                 name.substring(0, name.lastIndexOf('.')));
        }
        if (pd == null) {
            pd = defaultDomain;
        }

        if (name != null) checkCerts(name, pd.getCodeSource());

        return pd;
    }
```

This is called by:

```java
    protected final Class<?> defineClass(String name, byte[] b, int off, int len,
                                         ProtectionDomain protectionDomain)
        throws ClassFormatError
    {
        protectionDomain = preDefineClass(name, protectionDomain);
        String source = defineClassSourceLocation(protectionDomain);
        Class<?> c = defineClass1(name, b, off, len, protectionDomain, source);
        postDefineClass(c, protectionDomain);
        return c;
    }
```

The `defineClass1` in that is private and native, so any duplication would have to relax that constraint. Reflection might be able to.
