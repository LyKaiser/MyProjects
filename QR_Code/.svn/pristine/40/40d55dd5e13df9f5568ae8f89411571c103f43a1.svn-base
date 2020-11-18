package jpp.qrcode;

public enum Encoding {
    NUMERIC, ALPHANUMERIC, BYTE, KANJI, ECI, INVALID;

    public static Encoding fromBits(int i) {

        if (i == 0b1) {
            return NUMERIC;
        } else if (i == 0b10) {
            return ALPHANUMERIC;
        } else if (i == 0b100) {
            return BYTE;
        } else if (i == 0b1000) {
            return KANJI;
        } else if (i == 0b111) {
            return ECI;
        } else return INVALID;
    }

    public int bits() {
        int bits;

        if (this == NUMERIC) {
            bits = 0b1;
        } else if (this == ALPHANUMERIC) {
            bits = 0b10;
        } else if (this == BYTE) {
            bits = 0b100;
        } else if (this == KANJI) {
            bits = 0b1000;
        } else if (this == ECI) {
            bits = 0b111;
        } else bits = -1;

        return bits;
    }
}
