package de.chloedev.cdnlib.event;

public class Priority {

    public static final byte HIGHEST = 0;
    public static final byte HIGH = 1;
    public static final byte NORMAL = 2;
    public static final byte LOW = 3;
    public static final byte LOWEST = 4;

    public static byte[] values() {
        return new byte[]{
                HIGHEST,
                HIGH,
                NORMAL,
                LOW,
                LOWEST
        };
    }
}
