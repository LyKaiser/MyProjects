package hIndex;

import java.util.Arrays;


public class HirschIndex {
    public static int hirschIndex(int[] citations) {
        Arrays.sort(citations);
        int[] neu = new int[citations.length];
        int j = 0;
        for (int i = 1; i < citations.length + 1; i++) {
            if (i < citations.length && citations[i] == citations[i - 1]) {
                neu[j]++;
            } else {
                neu[j]++;
                j++;
            }
        }

        int result = 1;
        int helper = 0;
        for (int x = 0; x < neu.length; x++) {
            int comp = neu[x];
            for (int y = 0; y < neu.length; y++) {
                if (neu[y] >= comp) {
                    helper++;
                }
            }
            if (helper >= comp && neu[x] > result){
                result = neu[x];
            }
            helper=0;
        }
        return result;
    }

}

