package jpp.qrcode;

public class MaskApplier {

    public static void applyTo(boolean[][] data, MaskFunction mask, ReservedModulesMask reservedModulesMask) {
        if (data.length != reservedModulesMask.size()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (!reservedModulesMask.isReserved(i, j)) {
                    if (mask.mask(i, j)) {
                        if (data[i][j]) {
                            data[i][j] = false;
                        } else data[i][j] = true;
                    }
                }
            }
        }
    }
}
