package marvelman;

public class Miracle {

    public Object shazam() throws Exception {
        return getClass().getClassLoader().loadClass("java.lang.String").newInstance();
    }

}
