package jpp.qrcode;

public class DataPositions {

    private ReservedModulesMask mask;
    private int i;
    private int j;


    public DataPositions(ReservedModulesMask mask) {
        this.mask = mask;
        i = mask.size() - 1;
        j = mask.size() - 1;
        //i = 24;
        //j = 5;
    }

    public int i() {
        return i;
    }

    public int j() {
        return j;
    }

    public boolean next() {

        if (i == mask.size() - 9 && j == 0) {
            return false;
        } else if (i == mask.size() - 1 && j == 9) {
            i = mask.size() - 9;
            j = j - 1;
        } else if (i == 9 && j == 7) {
            j = j - 2;
        } else if ((j > 6 && (j % 2 == 0)) || j==5|| j == 3 || j == 1) {
            j = j - 1;
        } else if ((j > 6 && (j + 1) % 4 == 0) || j == 2) {
            if (i == 0) {
                j = j - 1;
            } else {
                i = i - 1;
                j = j + 1;
            }
        } else {
            if (i == mask.size() - 1) {
                j = j - 1;
            } else {
                i = i + 1;
                j = j + 1;
            }
        }

        while (mask.isReserved(i, j)) {
            if (i == mask.size() - 9 && j == 0) {
                return false;}
            if ((j > 6 && (j % 2 == 0)) ||j==5|| j == 3 || j == 1) {
                j = j - 1;
            } else if ((j > 6 && (j + 1) % 4 == 0) || j == 2) {
                if (i == 0) {
                    j = j - 1;
                } else {
                    i = i - 1;
                    j = j + 1;
                }
            } else {
                if (i == mask.size() - 1) {
                    j = j - 1;
                } else {
                    i = i + 1;
                    j = j + 1;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        DataPositions data = new DataPositions(ReservedModulesMask.forVersion(Version.fromNumber(2)));
        while (data.next()) {
            System.out.println(data.i() + ", " + data.j());


        }
    }

}





        /*if (i == mask.size() - 9 && j == 0) {
            return false;
        } else if (i == mask.size() - 1 && j == 9) {
            i = mask.size() - 9;
            j = j - 1;
        } else if (i == 9 && j == 7) {
            j = j - 2;
        } else if (j > 6) {
            if (j % 2 == 0) {
                j = j - 1;
            } else if ((j + 1) % 4 == 0) {
                if (i == 0) {
                    j = j - 1;
                } else if (i == 7) {
                    i = i - 2;
                    j = j + 1;
                } else {
                    i = i - 1;
                    j = j + 1;
                }
            } else {
                if (i == mask.size() - 1) {
                    j = j - 1;
                } else if (i == 5) {
                    i = i + 2;
                    j = j + 1;
                } else {
                    i = i + 1;
                    j = j + 1;
                }
            }
        } else {
            if (j == 5 || j == 1) {
                j = j - 1;
            }
            else if (j == 4 || j == 0) {
                if (i == mask.size() - 9) {
                    j = j - 1;
                } else {
                    i = i + 1;
                    j = j + 1;
                }
            } else if (j == 3) {
                j = j - 1;
            } else {
                if (i == 9) {
                    j = j - 1;
                } else {
                    i = i - 1;
                    j = j + 1;
                }
            }
        }


        while (mask.isReserved(i, j)) {
            if (j > 6) {
                if (j % 2 == 0) {
                    j = j - 1;
                } else if ((j + 1) % 4 == 0) {
                    if (i == 0) {
                        j = j - 1;
                    } else if (i == 7) {
                        i = i - 2;
                        j = j + 1;
                    } else {
                        i = i - 1;
                        j = j + 1;
                    }
                } else {
                    if (i == mask.size() - 1) {
                        j = j - 1;
                    } else if (i == 5) {
                        i = i + 2;
                        j = j + 1;
                    } else {
                        i = i + 1;
                        j = j + 1;
                    }
                }
            } else {
                if (j == 5 || j == 1) {
                    j = j - 1;
                } else if (j == 4 || j == 0) {
                    if (i == mask.size() - 9) {
                        j = j - 1;
                    } else {
                        i = i + 1;
                        j = j + 1;
                    }
                } else if (j == 3) {
                    if (i == mask.size() - 9) {
                        j = j - 1;
                    } else {
                        i = i - 1;
                        j = j + 1;
                    }
                } else {
                    if (i == 9) {
                        j = j - 1;
                    } else {
                        i = i + 1;
                        j = j + 1;
                    }
                }
            }

        }
        return true;
    }


    public static void main(String[] args) {

        DataPositions data = new DataPositions(ReservedModulesMask.forVersion(Version.fromNumber(2)));
        while (data.next()) {
            System.out.println(data.i() + ", " + data.j());


        }*/