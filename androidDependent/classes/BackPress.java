package xcalibur.androidDependent.classes;

public final class BackPress {

    private static boolean state = true;

    public static void disable(){
        state = false;
    }

    public static void enable(){
        state = true;
    }

    public static boolean state(){
        return state;
    }
}
