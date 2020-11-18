package jpp.qrcode.decode;

import jpp.qrcode.DataPositions;
import jpp.qrcode.ReservedModulesMask;

public class DataExtractor {

    public static byte[] extract(boolean[][] data, ReservedModulesMask mask, int byteCount) {
        if (mask.size() != data.length) {
            throw new IllegalArgumentException();
        }
        DataPositions dPos = new DataPositions(mask);
        byte[] by = new byte[byteCount];

        byte start = 0;
        for (int i = 0; i < by.length; i++) {
            for (int j = 7; j >= 0; j--) {
                if (data[dPos.i()][dPos.j()]) {
                    start |= (1 << j);
                }
                dPos.next();
            }
            by[i]=start;
            start=0;
        }


        return by;
    }
}