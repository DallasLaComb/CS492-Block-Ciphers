//import java.util.Scanner;
package Homework2_BlockCiphers;

public class henry {

    public static void main(String[] args) {

        byte[] plaintext16 = {(byte)0,(byte) 255};
        byte[] plaintext32 = {(byte)152,(byte)55,(byte)0,(byte)255};


        //iv
        byte iv = 0;
        //messages with bytes split appropriatle
        //byte[] bitMessageSplit16 = new byte[plaintext16.length];
        //byte[] bitMessageSplit32 = new byte[plaintext32.length];

        //sending 13 & 32 but messages to be split byt splitter

        byte[] bitMessageSplit16 = splitter(plaintext16);
        byte[] bitMessageSplit32 = splitter(plaintext32);

        //ECB Main Driver Calls
        byte[] SR1 = ECBDriver(bitMessageSplit16);
        byte[] SR2 = ECBDriver(bitMessageSplit32);
        //CTR Main Driver Calls
        byte[] SR3 = CTRDriver(bitMessageSplit16, iv);
        byte[] SR4 = CTRDriver(bitMessageSplit32, iv);
        //System.out.print(R1);;
        byte[] SR5 = CBCDriver(bitMessageSplit16, iv);
        byte[] SR6 = CBCDriver(bitMessageSplit32, iv);
        //String message1 = "1000101011001110";

        byte[] R1 = crusher(SR1);
        byte[] R2 = crusher(bitMessageSplit32);
        byte[] R3 = crusher(SR3);
        byte[] R4 = crusher(SR4);
        byte[] R5 = crusher(SR5);
        byte[] R6 = crusher(SR6);

        //Original Message

        System.out.println(bytesToBlocksString(plaintext32));
        System.out.println(bytesToBlocksString(bitMessageSplit32 ));
        System.out.println(bytesToBlocksString(R2 ));

        System.out.println("16bit Plaintext: \n" +bytesToBlocksString(plaintext16));
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("32bit Plaintext: \n" +bytesToBlocksString(plaintext32));
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("IV in Decimal: \n" + iv);
        //Divider
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        //ECB Printer
        System.out.println("16bit ECB-Cypher: \n" +bytesToBlocksString(R1));
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("32bit ECB-Cypher: \n" +bytesToBlocksString(R2));
        // Divider
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        //CTR Printer
        System.out.println("16bit CTR-Cypher: \n" +bytesToBlocksString(R3));
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("32bit CTR-Cypher: \n" +bytesToBlocksString(R4));
        // Divider
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        //CBC Printer
        System.out.println("16bit CBC-Cypher: \n" +bytesToBlocksString(R5));
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("32bit CBC-Cypher: \n" +bytesToBlocksString(R6));



    }
    // byteToBinaryString by: Chad W.
    public static String byteToBinaryString(byte passedByte) {
        String binaryString = Integer.toBinaryString(Byte.toUnsignedInt(passedByte));
        String prependZeros = "0".repeat(8 - binaryString.length());
        return prependZeros + binaryString;
    }

    // byteToBinaryString by: Chad W
    public static String bytesToBlocksString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (byte curByte : bytes) {
            String byteString = byteToBinaryString(curByte);
            buffer.append(byteString.substring(0, 4) + " " + byteString.substring(4) + " ");
        }
        return buffer.toString();
        //
    }
    public static byte[] splitter(byte[] message) {

        int holder = 0;
        byte[] result = new byte[message.length * 2];
        for(int index = 0; index < message.length; index++) {
            byte p1 = message[index];
            result[holder]= (byte)(p1 >> 4);
            result[holder] = (byte)(result[holder] & 0x0F);
            result[holder + 1] = (byte)(p1 & 0x0F);
            holder++;
            holder++;
        }
        return result;
    }

    public static byte[] crusher(byte[] messageSplit) {
        byte[] result = new byte[messageSplit.length /2];

        int holder = 0;

        for(int index = 0; index < result.length; index++) {
            byte p1 = (byte)((messageSplit[holder])<< 4);
            byte p2 = messageSplit[holder + 1];
            result[index] = (byte)(p1 | p2);
            holder++;
            holder++;
        }

        return result;
    }

    public static byte[] ECBDriver(byte[] message ) {

        byte[] result = new byte[message.length];

        for(int index = 0; index < result.length; index++) {
            result[index] = substitutionAssist(message[index]);
        }

        return result;

    }

    public static byte[] CTRDriver(byte[] message, byte ivpass ) {
        byte tempbyte;
        byte iv = ivpass;
        byte[] result = new byte[message.length];
        byte[] temporary = new byte[message.length];
        for(int index = 0; index <message.length; index++) {
            temporary[index] = iv;
            if(iv ==15) {
                iv = 0;
            }else {
                iv++;
            }
        }
        for(int index2 = 0; index2 < message.length; index2++) {
            result[index2] = (byte)(temporary[index2] ^ message[index2]);

        }



        return result;
    }

    public static byte[] CBCDriver(byte[] message, byte ivpass) {
        byte[] result = new byte[message.length];
        byte[] temp = new byte[message.length];
        byte[] xorR = new byte[message.length];
        //First part will be manual
        temp[0] = ivpass;
        xorR[0] = (byte)(temp[0] ^ message[0]);
        result[0] = substitutionAssist(xorR[0]);

        for(int index = 1; index <message.length; index++) {
            temp[index] = result[index-1];
            xorR[index] = (byte)(message[index] ^ temp[index] );
            result[index] = substitutionAssist(xorR[index]);
        }



        return result;
    }


    static byte substitutionAssist(byte key) {
        switch (key) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 8;
            case 8:
                return 9;
            case 9:
                return 10;
            case 10:
                return 11;
            case 11:
                return 12;
            case 12:
                return 13;
            case 13:
                return 14;
            case 14:
                return 15;
            case 15:
                return 0;



        }
        return -100;
    }

}



