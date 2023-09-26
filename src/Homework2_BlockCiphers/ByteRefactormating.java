package Homework2_BlockCiphers;

import static Homework2_BlockCiphers.BinaryUtil.byteToBinaryString;


public class ByteRefactormating {
//    This is going to be each indice array[i]
    public static byte left4Bits(byte message){
        int unsignedByte = message &0xFF;
        int temp = (unsignedByte>>4)&0xFF;
        return (byte)temp;
    }
    //    This is going to be each indice array[i]
    public static byte right4Bits(byte message){

    }
//
//    public static byte recombine{
//
//    }

    public static void main(String[] args) {

        byte b1 = (byte)197;

        byte temp = (byte)(b1<<4);
        byte rightBits = (byte)(temp>>4);

        int unsignedByte = b1 & 0xFF;
        int temp2 = (unsignedByte>>4)&0xFF;
        byte leftBits= (byte)temp2;
//        String leftBits2 = Integer.toBinaryString(temp2);

        System.out.println("Whole Byte: "+byteToBinaryString(b1));
        System.out.println("Right Bits: "+byteToBinaryString(rightBits));
        System.out.println("Left Bits: "+byteToBinaryString(leftBits));

    }
}
