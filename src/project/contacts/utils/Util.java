package project.contacts.utils;

public class Util {

    private Util(){};

    public static void stopTheSystem(String message){
        System.err.println(message);
        System.exit(-1);
    }
}
