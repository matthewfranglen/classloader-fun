package com.matthew.classloaderfun;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;

@SpringBootApplication
public class ClassloaderFunApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ClassloaderFunApplication.class, args);
    }

    public void run(String... args) {
        new Thread(this::execute).start();
    }

    private void execute() {
        try {
            Class<?> miracle = new FunClassLoader().loadClass("fatterman.Miracle");
            Object instance = miracle.newInstance();
            Method shazam = miracle.getMethod("shazam");

            fatterman.MagicWord real = fatterman.MagicWord.SHAZAM;
            Object fake = shazam.invoke(instance);

            System.out.println(real.getClass() == fake.getClass());
            System.out.println(real == fake);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
