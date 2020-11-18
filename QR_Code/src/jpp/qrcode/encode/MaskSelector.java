package jpp.qrcode.encode;

import jpp.qrcode.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class MaskSelector {

    public static void placeFormatInformation(boolean[][] res, int formatInformation) {

        String s = Integer.toBinaryString(formatInformation);
        int pos = s.length() - 1;
        for (int i = 0; i < 9; i++) {
            if (i != 6) {
                if (pos >= 0) {
                    if (s.charAt(pos) == '1') {
                        res[i][8] = true;
                    }else res[i][8]=false;
                    pos--;
                }else res[i][8]=false;
            }
        }
        for (int i = 7; i >= 0; i--) {
            if (i != 6) {
                if (pos >= 0) {
                    if (s.charAt(pos) == '1') {
                        res[8][i] = true;
                    }else res[8][i]=false;
                    pos--;
                }else res[8][i]=false;
            }
        }
        pos = s.length() - 1;
        for (int i = res.length - 1; i > res.length - 9; i--) {
            if (pos >= 0) {
                if (s.charAt(pos) == '1') {
                    res[8][i] = true;
                }else res[8][i]=false;
                pos--;
            }else res[8][i]=false;
        }
        for (int i = res.length - 7; i < res.length; i++) {
            if (pos >= 0) {
                if (s.charAt(pos) == '1') {
                    res[i][8] = true;
                }else res[i][8]=false;
                pos--;
            }else res[i][8]=false;
        }
    }

    public static int calculatePenaltySameColored(boolean[][] data) {
        int counter = 1;
        int punkte = 0;
        for (int i = 0; i < data.length; i++) {
            counter = 1;
            for (int j = 0; j < data.length - 1; j++) {
                if (data[i][j] == data[i][j + 1]) {
                    counter++;
                    if (counter == 5) {
                        punkte += 3;
                    } else if (counter > 5) {
                        punkte++;
                    }
                } else counter = 1;

            }
        }
        counter = 1;
        for (int i = 0; i < data.length; i++) {
            counter = 1;
            for (int j = 0; j < data.length - 1; j++) {
                if (data[j][i] == data[j + 1][i]) {
                    counter++;
                    if (counter == 5) {
                        punkte += 3;
                    } else if (counter > 5) {
                        punkte++;
                    }
                } else counter = 1;

            }
        }
        return punkte;
    }


    public static int calculatePenalty2x2(boolean[][] arr) {
        int punkte = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[i][j] == arr[i + 1][j] && arr[i][j] == arr[i][j + 1] &&
                        arr[i][j] == arr[i + 1][j + 1]) {
                    punkte += 3;
                }

            }
        }

        return punkte;
    }

    public static int calculatePenaltyBlackWhite(boolean[][] arr) {
        int black = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j]) {
                    black++;
                }
            }
        }
        int bla = 10 * (abs(2 * black - (arr.length * arr.length)) * 10 / (arr.length * arr.length));
        return bla;
    }

    public static int calculatePenaltyPattern(boolean[][] array) {
        int punkte = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 10; j++) {
                if (!array[i][j] & !array[i][j + 1] & !array[i][j + 2] & !array[i][j + 3]) {

                    if (array[i][j + 4] & !array[i][j + 5] & array[i][j + 6] & array[i][j + 7] & array[i][j + 8]
                            & !array[i][j + 9] & array[i][j + 10]) {
                        punkte += 40;
                        j = j + 5;
                    }

                } else if (array[i][j] & !array[i][j + 1] & array[i][j + 2] & array[i][j + 3] & array[i][j + 4]
                        & !array[i][j + 5] & array[i][j + 6]) {
                    if (!array[i][j + 7] & !array[i][j + 8] & !array[i][j + 9] & !array[i][j + 10])
                        punkte += 40;

                }
            }
        }


        for (int a = 0; a < array.length; a++) {
            for (int b = 0; b < array.length - 10; b++) {
                if (!array[b][a] & !array[b + 1][a] & !array[b + 2][a] & !array[b + 3][a]) {
                    if (array[b + 4][a] & !array[b + 5][a] & array[b + 6][a] & array[b + 7][a] & array[b + 8][a]
                            & !array[b + 9][a] & array[b + 10][a]) {
                        punkte += 40;
                        b = b + 5;
                    }

                } else if (array[b][a] & !array[b+1][a] & array[b+2][a] & array[b+3][a] & array[b+4][a]
                        & !array[b + 5][a] & array[b + 6][a]) {
                    if (!array[b + 7][a] & !array[b + 8][a] & !array[b + 9][a] & !array[b + 10][a])
                        punkte += 40;

                }
            }
        }

        return punkte;
    }

    public static int calculatePenaltyFor(boolean[][] data) {
        int summe = calculatePenaltySameColored(data) + calculatePenalty2x2(data) +
                calculatePenaltyBlackWhite(data) + calculatePenaltyPattern(data);
        return summe;
    }

    public static MaskPattern maskWithBestMask(boolean[][] data, ErrorCorrection correction, ReservedModulesMask modulesMask) {
        if (data.length!=modulesMask.size()){
            throw new IllegalArgumentException();
        }
        List<MaskPattern> li = new ArrayList<>();
        li.add(MaskPattern.MASK000);
        li.add(MaskPattern.MASK001);
        li.add(MaskPattern.MASK010);
        li.add(MaskPattern.MASK011);
        li.add(MaskPattern.MASK100);
        li.add(MaskPattern.MASK101);
        li.add(MaskPattern.MASK111);
        li.add(MaskPattern.MASK110);

        int start=Integer.MAX_VALUE;
        MaskPattern map = MaskPattern.MASK000;
        for (MaskPattern ma : li){
            int fi = FormatInformation.get(correction,ma).formatInfo();
            MaskApplier.applyTo(data,ma.maskFunction(),modulesMask);
            placeFormatInformation(data,fi);
            int calc = calculatePenaltyFor(data);
            MaskApplier.applyTo(data,ma.maskFunction(),modulesMask);
            if (calc<start){
                start=calc;
                map=ma;
            }

        }

        int info = FormatInformation.get(correction,map).formatInfo();
        MaskApplier.applyTo(data,map.maskFunction(),modulesMask);
        placeFormatInformation(data,info);
        return map;


    }
}
