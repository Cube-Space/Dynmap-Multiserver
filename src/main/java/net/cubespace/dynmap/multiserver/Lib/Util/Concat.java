package net.cubespace.dynmap.multiserver.Lib.Util;

import net.cubespace.dynmap.multiserver.GSON.Component;
import net.cubespace.dynmap.multiserver.GSON.DynmapWorld;
import net.cubespace.dynmap.multiserver.GSON.Player;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Concat {
    public static Player[] concat(Player[] A, Player[] B) {
        int aLen = A.length;
        int bLen = B.length;
        Player[] C= new Player[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    public static Component[] concat(Component[] A, Component[] B) {
        int aLen = A.length;
        int bLen = B.length;
        Component[] C= new Component[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    public static DynmapWorld[] concat(DynmapWorld[] A, DynmapWorld[] B) {
        int aLen = A.length;
        int bLen = B.length;
        DynmapWorld[] C= new DynmapWorld[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }
}
