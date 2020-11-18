package jpp.qrcode.encode;

import jpp.qrcode.DataBlock;
import jpp.qrcode.ErrorCorrection;
import jpp.qrcode.ErrorCorrectionGroup;
import jpp.qrcode.ErrorCorrectionInformation;
import jpp.qrcode.reedsolomon.ReedSolomon;

import java.util.ArrayList;
import java.util.List;

public class DataStructurer {

    public static DataBlock[] split(byte[] data, ErrorCorrectionInformation errorCorrectionInformation) {

        //int bytezahl=errorCorrectionInformation.totalByteCount();
        //int datenbytezKl=errorCorrectionInformation.lowerDataByteCount();
        ErrorCorrectionGroup[] korrGr = errorCorrectionInformation.correctionGroups();
        int correction = errorCorrectionInformation.correctionBytesPerBlock();
        int blockAnzahl = errorCorrectionInformation.totalBlockCount();
        int langBlock = data.length / blockAnzahl;

        DataBlock[] result = new DataBlock[blockAnzahl];

        int pos = 0;
        int i = 0;
        for (ErrorCorrectionGroup ec : korrGr) {
            int bloecke = ec.blockCount();
            int laenge = ec.dataByteCount();
            while (bloecke > 0) {
                byte[] block = new byte[laenge];
                for (int j = 0; j < block.length; j++) {
                    block[j] = data[pos];
                    pos++;
                }
                byte[] korrSingle = ReedSolomon.calculateCorrectionBytes(block, correction);
                DataBlock teil = new DataBlock(block, korrSingle);
                result[i] = teil;
                i++;
                bloecke--;
            }
        }


        return result;
    }


    public static byte[] interleave(DataBlock[] blocks, ErrorCorrectionInformation ecBlocks) {

        byte[] result = new byte[ecBlocks.totalByteCount()];
        int korr = ecBlocks.correctionBytesPerBlock();
        int anzBlock = ecBlocks.totalBlockCount();
        int korrGes = anzBlock * korr;
        ErrorCorrectionGroup[] group = ecBlocks.correctionGroups();


        int pos = 0;
        int bla = 0;
        while (pos < result.length - korrGes) {
            for (DataBlock db : blocks) {
                byte[] dataBl = db.dataBytes();
                if (bla < dataBl.length) {
                    result[pos] = dataBl[bla];
                    pos++;
                }
            }
            bla++;
        }
        bla = 0;
        while (pos < result.length) {
            for (DataBlock db : blocks) {
                byte[] dataBl = db.correctionBytes();
                result[pos] = dataBl[bla];
                pos++;
            }
            bla++;
        }
        return result;
    }

    public static byte[] structure(byte[] data, ErrorCorrectionInformation ecBlocks) {

        if (ecBlocks.totalDataByteCount() != data.length) {
            throw new IllegalArgumentException();
        }
        DataBlock[] dataBl = split(data, ecBlocks);
        byte[] result = interleave(dataBl, ecBlocks);

        return result;
    }
}
