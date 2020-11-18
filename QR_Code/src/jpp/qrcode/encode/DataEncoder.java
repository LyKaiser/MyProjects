package jpp.qrcode.encode;

import jpp.qrcode.ErrorCorrection;
import jpp.qrcode.ErrorCorrectionInformation;
import jpp.qrcode.Version;

public final class DataEncoder {
    public static DataEncoderResult encodeForCorrectionLevel(String str, ErrorCorrection level) {

        byte[] bystr = str.getBytes();


        int laengeDaten = (4 + 8 + 8 * str.length() + 4) / 8;
        if (laengeDaten > Version.fromNumber(40).totalByteCount()) {
            throw new IllegalArgumentException();
        }
        Version versio = Version.forDataBytesCount(laengeDaten, level);
        if(versio.number()>9){
            versio = Version.forDataBytesCount(laengeDaten+1, level);
        }


        int versioNr = versio.number();


        int laenge = versio.correctionInformationFor(level).totalDataByteCount();
        int bla = str.length();
        byte[] by = new byte[laenge];

        if (versioNr < 10) {
            int pos = 0;
            byte start = 0;
            int i = 3;
            start |= (1 << 6);
            for (int j = 7; j > 3; j--) {
                if ((bla & (1 << j)) != 0) {
                    start |= (1 << i);

                }
                i--;
            }
            by[pos] = start;
            pos++;

            i = 7;
            start = 0;
            for (int j = 3; j >= 0; j--) {
                if ((bla & (1 << j)) != 0) {
                    start |= (1 << i);

                }
                i--;
            }
            if (bystr.length != 0) {
                for (int j = 7; j > 3; j--) {
                    if ((bystr[0] & (1 << j)) != 0) {
                        start |= (1 << i);

                    }
                    i--;
                }

                by[pos] = start;
                pos++;

                //for (byte b : bystr) {
                for (int c = 0; c < bystr.length; c++) {
                    start = 0;
                    i = 7;
                    for (int j = 3; j >= 0; j--) {
                        if ((bystr[c] & (1 << j)) != 0) {
                            start |= (1 << i);

                        }
                        i--;

                    }
                    if (c < bystr.length - 1) {
                        for (int j = 7; j > 3; j--) {
                            if ((bystr[c + 1] & (1 << j)) != 0) {
                                start |= (1 << i);

                            }
                            i--;
                        }
                        by[pos] = start;
                        pos++;
                    }

                }
            }
            by[pos] = start;
            pos++;
            start = 0;
            int fu = 0;
            for (int a = pos; a < by.length; a++) {
                if (fu % 2 == 0) {
                    by[a] = (byte) 236;

                } else {
                    by[a] = (byte) 17;
                }
                fu++;
            }

        } else {
            int pos = 0;
            byte start = 0;
            int i = 3;
            start |= (1 << 6);
            for (int j = 15; j > 11; j--) {
                if ((bla & (1 << j)) != 0) {
                    start |= (1 << i);

                }
                i--;
            }
            by[pos] = start;
            pos++;

            i = 7;
            start = 0;
            for (int j = 11; j > 3; j--) {
                if ((bla & (1 << j)) != 0) {
                    start |= (1 << i);

                }
                i--;
            }
            by[pos] = start;
            pos++;
            i=7;
            start=0;
            for (int j = 3; j >= 0; j--) {
                if ((bla & (1 << j)) != 0) {
                    start |= (1 << i);

                }
                i--;
            }
            if (bystr.length != 0) {
                for (int j = 7; j > 3; j--) {
                    if ((bystr[0] & (1 << j)) != 0) {
                        start |= (1 << i);

                    }
                    i--;
                }

                by[pos] = start;
                pos++;

                //for (byte b : bystr) {
                for (int c = 0; c < bystr.length; c++) {
                    start = 0;
                    i = 7;
                    for (int j = 3; j >= 0; j--) {
                        if ((bystr[c] & (1 << j)) != 0) {
                            start |= (1 << i);

                        }
                        i--;

                    }
                    if (c < bystr.length - 1) {
                        for (int j = 7; j > 3; j--) {
                            if ((bystr[c + 1] & (1 << j)) != 0) {
                                start |= (1 << i);

                            }
                            i--;
                        }
                        by[pos] = start;
                        pos++;
                    }
                }
            }
            by[pos] = start;
            pos++;
            start = 0;
            int fu = 0;
            for (int a = pos; a < by.length; a++) {
                if (fu % 2 == 0) {
                    by[a] = (byte) 236;

                } else {
                    by[a] = (byte) 17;
                }
                fu++;
            }

        }


        DataEncoderResult result = new DataEncoderResult(by, versio);
        return result;
    }
}











        /*for (int i = 0; i < 9; i++) {
            if (i  1) {
                by[i] = 1;
            } else by[i] = 0;
        }
        int pos = 4;
        if (versioNr > 9) {
            for (int i = 14; i >= 0; i--) {
                boolean bit = ((str.length() >> i) & 1) != 0;
                if (bit) {
                    by[pos] = 1;
                    pos++;
                } else {
                    by[pos] = 0;
                    pos++;
                }
            }
        } else {
            for (int i = 7; i >= 0; i--) {
                boolean bit = ((str.length() >> i) & 1) != 0;
                if (bit) {
                    by[pos] = 1;
                    pos++;
                } else {
                    by[pos] = 0;
                    pos++;
                }
            }
        }

        for (byte b : bystr) {
            for (int i = 7; i >= 0; i--) {
                boolean bit = ((b >> i) & 1) != 0;
                if (bit) {
                    by[pos] = 1;
                    pos++;
                } else {
                    by[pos] = 0;
                    pos++;
                }

            }
        }
        int j = pos;
        for (int i = j; i < j + 5; i++) {
            by[pos] = 0;
            pos++;
        }
        for (int i = paddingBytes; i > 0; i--) {
            if (i % 2 == 0) {
                for (int a = 0; a < 8; a++) {
                    if (a == 3 || a == 7) {
                        by[pos] = 1;
                        pos++;
                    } else {
                        by[pos] = 0;
                        pos++;
                    }
                }
            } else {
                for (int a = 0; a < 8; a++) {
                    if (a == 3 || a == 6 || a == 7) {
                        by[pos] = 0;
                        pos++;
                    } else {
                        by[pos] = 1;
                        pos++;
                    }
                }
            }

        }
    }

    DataEncoderResult result = new DataEncoderResult(by, versio);
        return result;

}
}*/
