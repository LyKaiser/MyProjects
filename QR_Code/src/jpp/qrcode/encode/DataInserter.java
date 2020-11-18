package jpp.qrcode.encode;

import jpp.qrcode.DataPositions;
import jpp.qrcode.ReservedModulesMask;

public class DataInserter {
    public static void insert(boolean[][] target, ReservedModulesMask mask, byte[] data) {

        DataPositions po = new DataPositions(mask);
        for (byte b :data){
            for (int a = 7; a >= 0; a--) {
                if ((b & (1 << a)) != 0) {
                    target[po.i()][po.j()] = true;
                }
                po.next();
            }
        }

    }
}
