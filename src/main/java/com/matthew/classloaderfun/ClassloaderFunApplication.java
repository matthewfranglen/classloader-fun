package com.matthew.classloaderfun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;

@SpringBootApplication
public class ClassloaderFunApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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

            logger.info("Comparing {} to {}: {}", real, fake, real == fake);
            logger.info("Comparing {} to {}: {}", real.getClass(), fake.getClass(), real.getClass() == fake.getClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
