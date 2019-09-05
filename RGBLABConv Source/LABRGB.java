import java.util.Random;

/**
 *
 * @author Blake Lee and Kumar Prasad Pandit
 */

public class LABRGB {

    private static final int ADOBE_RGB = 0;
    private static final int APPLE_RGB = 1;
    private static final int BEST_RGB = 2;
    private static final int BETA_RGB = 3;
    private static final int BRUCE_RGB = 4;
    private static final int CIE_RGB = 5;
    private static final int COLORMATCH_RGB = 6;
    private static final int DON_RGB_4 = 7;
    private static final int ECI_RGB_V2 = 8;
    private static final int EKTA_SPACE_PS5 = 9;
    private static final int NTSC_RGB = 10;
    private static final int PAL_SECAM_RGB = 11;
    private static final int PROPHOTO_RGB = 12;
    private static final int SMPTE_C_RGB = 13;
    private static final int SRGB = 14;
    private static final int WIDE_GAMUT_RGB = 15;

    public static void main(String[] args){
        Random rand = new Random();
        int index = SRGB;
        double max = 255;
        double avg = 0;
        int rounds = 10000;
        for (int x = 0;x<rounds;x++) {
            double R = rand.nextDouble() * max;
            double G = rand.nextDouble() * max;
            double B = rand.nextDouble() * max;
            double[][] lab = rgb2lab(R,G,B, max, index);
            double[][] rgb2 = lab2rgb(lab[0][0], lab[1][0], lab[2][0], max, index);
            //printMtr(lab);
            double error1 = (Math.abs(R - rgb2[0][0]) / max);
            double error2 = (Math.abs(G - rgb2[1][0]) / max);
            double error3 = (Math.abs(B - rgb2[2][0]) / max);
            double acc = 100.0 - (error1 / 3 + error2 / 3 + error3 / 3) *100.0;
            avg += acc;
            if (acc < 99.999 || Double.isNaN(acc)) {
                System.out.println("Accuracy test " + x + ":");
                System.out.println("                  R = " + R + ", G = " + G + ", B = " + B);
                System.out.println("                  L = " + lab[0][0] + ", A = " + lab[1][0] + ", B = " + lab[2][0]);
                System.out.println("                 R2 = " + rgb2[0][0] + ", G2 = " + rgb2[1][0] + ", B2 = " + rgb2[2][0]);
                System.out.println("                 Accuracy = %" + acc);
            }
            if (Double.isNaN(avg)){
                break;
            }
        }
        System.out.println("Average: "+avg / rounds);
    }

    public static double[][] lab2rgb(double l, double a, double b, double max, int index){
        double[][] lab = crtMtr(l,a,b);
        double[][] xyz = lab2xyz(lab, index);
        double[][] xyz2 = mtrXdub(xyz, 1/100.0);
        double[][] rgb = Compand(mtrXmtr(getConversionMatrix2(index), xyz2), index);
        double[][] rgb2 = mtrXdub(rgb, max);
        return rgb2;
    }

    public static double[][] rgb2lab(double r, double g, double b, double max, int index){
        r /= max;
        g /= max;
        b /= max;
        double[][] rgb = crtMtr(r,g,b);
        double[][] xyz = mtrXdub(mtrXmtr(getConversionMatrix(index), InverseCompand(rgb, index)), 100);
        double[][] lab = xyx2lab(xyz,index);
        return lab;
    }

    private static double[][] crtMtr(double r, double g, double b){
        return new double[][]{
            {r},
            {g},
            {b}
        };
    }
    private static double[][] mtrXmtr(double[][] A, double[][] B){
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < aColumns; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
    private static double[][] mtrXdub(double[][] inp, double mul){
        int row = inp.length;
        int col = inp[0].length;
        double[][] ret = new double[row][col];
        for (int x =0;x<row;x++){
            for (int y=0;y<col;y++){
                ret[x][y] = inp[x][y] * mul;
            }
        }
        return ret;
    }

    private static void printMtr(double[][] mtr){
        for (double[] da : mtr){
            for (double d : da){
                System.out.print(d);
            }
            System.out.print("\n");
        }
    }

    private static double[][] xyx2lab(double[][] xyz, int index){
        int row = xyz.length;
        int col = xyz[0].length;
        double[][] white = getRefWhite(index);
        double[][] fXYZ = new double[row][col];
        for (int x =0;x<row;x++) {
            for (int y = 0; y < col; y++) {
                double r = xyz[x][y] / white[x][y];
                if (r > 0.008856) fXYZ[x][y] = Math.cbrt(r);
                else fXYZ[x][y] = (903.3 * r + 16) / 116;
            }
        }
        double[][] ret = new double[row][col];
        ret[0][0] = 116 * fXYZ[1][0] - 16;
        ret[1][0] = 500 * (fXYZ[0][0] - fXYZ[1][0]);
        ret[2][0] = 200 * (fXYZ[1][0] - fXYZ[2][0]);
        return ret;
    }

    private static double[][] lab2xyz(double[][] lab, int index){
        double[][] white = getRefWhite(index);
        double fY = (lab[0][0] + 16) / 116;
        double fX = (lab[1][0] / 500) + fY;
        double fZ = fY - (lab[2][0] / 200);
        double e = 0.008856;
        double k = 903.3;
        double Xr;
        double Yr;
        double Zr;
        if(Math.pow(fX, 3) > e) Xr = Math.pow(fX, 3);
        else Xr = (116 * fX - 16) / k;
        if (lab[0][0] > k * e) Yr = Math.pow((lab[0][0] + 16)/116, 3);
        else Yr = lab[0][0] / k;
        if (Math.pow(fZ, 3) > e) Zr = Math.pow(fZ, 3);
        else Zr = (116 * fZ - 16) / k;
        double X = Xr * white[0][0];
        double Y = Yr * white[1][0];
        double Z = Zr * white[2][0];
        return crtMtr(X,Y,Z);
    }

    private static double[][] getRefWhite(int index){
        switch(index){
            case 0:return new double[][]{
                    {95.047},
                    {100},
                    {108.883}
            };
            case 1:return new double[][]{
                    {95.047},
                    {100},
                    {108.883}
            };
            case 2:return new double[][]{
                    {96.422},
                    {100},
                    {82.521}
            };
            case 3:return new double[][]{
                    {96.422},
                    {100},
                    {82.521}
            };
            case 4:return new double[][]{
                    {95.047},
                    {100},
                    {108.883}
            };
            case 5:return new double[][]{
                    {100},
                    {100},
                    {100}
            };
            case 6:return new double[][]{
                    {96.422},
                    {100},
                    {82.521}
            };
            case 7:return new double[][]{
                    {96.422},
                    {100},
                    {82.521}
            };
            case 8:return new double[][]{
                    {96.422},
                    {100},
                    {82.521}
            };
            case 9:return new double[][]{
                    {96.422},
                    {100},
                    {82.521}
            };
            case 10:return new double[][]{
                    {98.074},
                    {100},
                    {118.232}
            };
            case 11:return new double[][]{
                    {95.047},
                    {100},
                    {108.883}
            };
            case 12:return new double[][]{
                    {96.422},
                    {100},
                    {82.521}
            };
            case 13:return new double[][]{
                    {95.047},
                    {100},
                    {108.883}
            };
            case 14:return new double[][]{
                    {95.047},
                    {100},
                    {108.883}
            };
            case 15:return new double[][]{
                    {96.422},
                    {100},
                    {82.521}
            };
            default:return new double[][]{
                    {100},
                    {100},
                    {100}
            };
        }
    }

    private static double[][] Compand(double[][] xyz, int index){
        int row = xyz.length;
        int col = xyz[0].length;
        double[][] ret = new double[row][col];
        for (int x =0;x<row;x++){
            for (int y=0;y<col;y++){
                double tp = xyz[x][y];
                switch(index){
                    case 0:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 1:ret[x][y] = Math.pow(tp, 1/1.8);break;
                    case 2:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 3:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 4:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 5:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 6:ret[x][y] = Math.pow(tp, 1/1.8);break;
                    case 7:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 8:{
                        if(tp <= 0.008856) ret[x][y] = (tp * 903.3) / 100;
                        else ret[x][y] = 1.16 * Math.cbrt(tp) - 0.16;
                    }break;
                    case 9:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 10:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 11:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 12:ret[x][y] = Math.pow(tp, 1/1.8);break;
                    case 13:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    case 14:{
                        //System.out.println("V = "+tp);
                        if (tp <= 0.0031308) ret[x][y] = tp * 12.92;
                        else ret[x][y] = 1.055 * Math.pow(tp, 1/2.4) - 0.055;
                    }break;
                    case 15:ret[x][y] = Math.pow(tp, 1/2.2);break;
                    default:ret[x][y] = tp;
                }
            }
        }
        return ret;
    }

    private static double[][] InverseCompand(double[][] rgb, int index){
        int row = rgb.length;
        int col = rgb[0].length;
        double[][] ret = new double[row][col];
        for (int x =0;x<row;x++){
            for (int y=0;y<col;y++){
                double tp = rgb[x][y];
                switch(index){
                    case 0:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 1:ret[x][y] = Math.pow(tp, 1.8);break;
                    case 2:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 3:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 4:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 5:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 6:ret[x][y] = Math.pow(tp, 1.8);break;
                    case 7:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 8:{
                        if(tp > 0.08) ret[x][y] = Math.pow((tp + 0.16) / 1.16, 3);
                        else ret[x][y] = 100 * tp / 903.3;
                    }break;
                    case 9:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 10:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 11:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 12:ret[x][y] = Math.pow(tp, 1.8);break;
                    case 13:ret[x][y] = Math.pow(tp, 2.2);break;
                    case 14:{
                        //System.out.println("V2 = "+tp);
                        if (tp <= 0.04045) ret[x][y] = tp / 12.92;
                        else ret[x][y] = Math.pow((tp + 0.055) / 1.055, 2.4);
                    }break;
                    case 15:ret[x][y] = Math.pow(tp, 2.2);break;
                    default:ret[x][y] = tp;
                }
            }
        }
        return ret;
    }

    private static double[][] getConversionMatrix(int index){
        switch(index){
            case 0:return new double[][]{
                    {0.5767309, 0.1855540, 0.1881852},      //Adobe RGB
                    {0.2973769, 0.6273491, 0.0752741},
                    {0.0270343, 0.0706872, 0.9911085}
                };
            case 1:return new double[][]{
                    {0.4497288, 0.3162486, 0.1844926},      //Apple RGB
                    {0.2446525, 0.6720283, 0.0833192},
                    {0.0251848, 0.1411824, 0.9224628}
                };
            case 2:return new double[][]{
                    {0.6326696, 0.2045558, 0.1269946},      //Best RGB
                    {0.2284569, 0.7373523, 0.0341908},
                    {0.0000000, 0.0095142, 0.8156958}
                };
            case 3:return new double[][]{
                    {0.6712537, 0.1745834, 0.1183829},      //Beta RGB
                    {0.3032726, 0.6637861, 0.0329413},
                    {0.0000000, 0.0407010, 0.7845090}
                };
            case 4:return new double[][]{
                    {0.4674162, 0.2944512, 0.1886026},       //Bruce RGB
                    {0.2410115, 0.6835475, 0.0754410},
                    {0.0219101, 0.0736128, 0.9933071}
                };
            case 5:return new double[][]{
                    {0.4887180, 0.3106803, 0.2006017},      //CIE RGB
                    {0.1762044, 0.8129847, 0.0108109},
                    {0.0000000, 0.0102048, 0.9897952}
                };
            case 6:return new double[][]{
                    {0.5093439, 0.3209071, 0.1339691},      //ColorMatch RGB
                    {0.2748840, 0.6581315, 0.0669845},
                    {0.0242545, 0.1087821, 0.6921735}
                };
            case 7:return new double[][]{
                    {0.6457711, 0.3209071, 0.1339691},      //Don RGB 4
                    {0.2748840, 0.6581315, 0.0669845},
                    {0.0242545, 0.1087821, 0.6921735}
                };
            case 8:return new double[][]{
                    {0.6502043, 0.1780774, 0.1359384},      //ECI RGB
                    {0.3202499, 0.6020711, 0.0776791},
                    {0.0000000, 0.0678390, 0.7573710}
                };
            case 9:return new double[][]{
                    {0.5938914, 0.2729801, 0.0973485},      //Ekta Space PS5
                    {0.2606286, 0.7349465, 0.0044249},
                    {0.0000000, 0.0419969, 0.7832131}
                };
            case 10:return new double[][]{
                    {0.6068909, 0.1735011, 0.2003480},      //NTSC RGB
                    {0.2989164, 0.5865990, 0.1144845},
                    {0.0000000, 0.0660957, 1.1162243}
                };
            case 11:return new double[][]{
                    {0.4306190, 0.3415419, 0.1783091},      //PAL / SECAM RGB
                    {0.2220379, 0.7066384, 0.0713236},
                    {0.0201853, 0.1295504, 0.9390944}
                };
            case 12:return new double[][]{
                    {0.7976749, 0.1351917, 0.0313534},      //ProPhoto RGB
                    {0.2880402, 0.7118741, 0.0000857},
                    {0.0000000, 0.0000000, 0.8252100}
                };
            case 13:return new double[][]{                  //SMPTE-C RGB
                    {0.3935891, 0.3652497, 0.1916313},
                    {0.2124132, 0.7010437, 0.0865432},
                    {0.0187423, 0.1119313, 0.9581563}
                };
            case 14:return new double[][]{                  //sRGB
                    {0.4124564, 0.3575761, 0.1804375},
                    {0.2126729, 0.7151522, 0.0721750},
                    {0.0193339, 0.1191920, 0.9503041}
                };
            case 15:return new double[][]{                  //Wide Gamut RGB
                    {0.7161046, 0.1009296, 0.1471858},
                    {0.2581874, 0.7249378, 0.0168748},
                    {0.0000000, 0.0517813, 0.7734287}
                };
            default :return new double[][]{
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}
            };
        }
    }

    private static double[][] getConversionMatrix2(int index){
        switch(index){
            case 0:return new double[][]{
                    {2.0413690 , -0.5649464 , -0.3446944},      //Adobe RGB
                    {-0.9692660, 1.8760108  , 0.0415560},
                    {0.0134474 , -0.1183897, 1.0154096}
            };
            case 1:return new double[][]{
                    {2.9515373 , -1.2894116, -0.4738445},      //Apple RGB
                    {-1.0851093, 1.9908566, 0.0372026},
                    {0.0854934, -0.2694964, 1.0912975}
            };
            case 2:return new double[][]{
                    {1.7552599 , -0.4836786, -0.2530000},      //Best RGB
                    {-0.5441336, 1.5068789, 0.0215528},
                    {0.0063467 , -0.0175761, 1.2256959}
            };
            case 3:return new double[][]{
                    {1.6832270, -0.4282363, -0.2360185},      //Beta RGB
                    {-0.7710229, 1.7065571  , 0.0446900},
                    {0.0400013 , -0.0885376, 1.2723640}
            };
            case 4:return new double[][]{
                    {2.7454669 , -1.1358136, -0.4350269},       //Bruce RGB
                    {-0.9692660, 1.8760108  , 0.0415560},
                    {0.0112723 , -0.1139754, 1.0132541}
            };
            case 5:return new double[][]{
                    {2.3706743 , -0.9000405, -0.4706338},      //CIE RGB
                    {-0.5138850, 1.4253036  , 0.0885814},
                    {0.0052982 , -0.0146949, 1.0093968}
            };
            case 6:return new double[][]{
                    {2.6422874 , -1.2234270, -0.3930143},      //ColorMatch RGB
                    {-1.1119763, 2.0590183  , 0.0159614},
                    {0.0821699 , -0.2807254, 1.4559877}
            };
            case 7:return new double[][]{
                    {1.7603902 , -0.4881198, -0.2536126},      //Don RGB 4
                    {-0.7126288, 1.6527432  , 0.0416715},
                    {0.0078207 , -0.0347411, 1.2447743}
            };
            case 8:return new double[][]{
                    {1.7827618 , -0.4969847, -0.2690101},      //ECI RGB
                    {-0.9593623, 1.9477962 , -0.0275807},
                    {0.0859317, -0.1744674, 1.3228273}
            };
            case 9:return new double[][]{
                    {2.0043819 , -0.7304844, -0.2450052},      //Ekta Space PS5
                    {-0.7110285, 1.6202126  , 0.0792227},
                    {0.0381263 , -0.0868780, 1.2725438}
            };
            case 10:return new double[][]{
                    {1.9099961 , -0.5324542, -0.2882091},      //NTSC RGB
                    {-0.9846663, 1.9991710 , -0.0283082},
                    {0.0583056 , -0.1183781, 0.8975535}
            };
            case 11:return new double[][]{
                    {3.0628971 , -1.3931791, -0.4757517},      //PAL / SECAM RGB
                    {-0.9692660, 1.8760108  , 0.0415560},
                    {0.0678775 , -0.2288548, 1.0693490}
            };
            case 12:return new double[][]{
                    {1.3459433 , -0.2556075, -0.0511118},      //ProPhoto RGB
                    {-0.5445989, 1.5081673  , 0.0205351},
                    {0.0000000, 0.0000000, 1.2118128}
            };
            case 13:return new double[][]{                  //SMPTE-C RGB
                    {3.5053960 , -1.7394894, -0.5439640},
                    {-1.0690722, 1.9778245  , 0.0351722},
                    {0.0563200 , -0.1970226, 1.0502026}
            };
            case 14:return new double[][]{                  //sRGB
                    {3.2404542 , -1.5371385, -0.4985314},
                    {-0.9692660, 1.8760108  , 0.0415560},
                    {0.0556434 , -0.2040259, 1.0572252}
            };
            case 15:return new double[][]{                  //Wide Gamut RGB
                    {1.4628067 , -0.1840623, -0.2743606},
                    {-0.5217933, 1.4472381  , 0.0677227},
                    {0.0349342 , -0.0968930, 1.2884099}
            };
            default :return new double[][]{
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}
            };
        }
    }
}
