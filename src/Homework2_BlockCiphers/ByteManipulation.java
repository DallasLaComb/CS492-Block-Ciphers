package Homework2_BlockCiphers;


public class ByteManipulation {
    public static byte isolateRight4Bits(byte message) {
        // Convert the signed byte to an unsigned int
     byte right = (byte)(message>>4);
      right= (byte)(right&0x0F);
      return right;
    }

    public static byte isolateLeft4Bits(byte message){
  byte left = (byte)(message & 0x0F);
  return left;
    }

    public static byte combineLeft4BitsandRight4Bits(byte leftBits, byte rightBits){
        byte leftBitsPutBackIntoPlace = (byte)(leftBits<<4);
        return (byte)(leftBitsPutBackIntoPlace | rightBits);
    }
    public static void main(String[] args) {

        byte b1 = (byte)197; //1100 0101
//=====================================================================================
//      Isolates right 4 bits.
        byte temp = (byte)(b1<<4); //0101 0000
        byte rightBits = (byte)(temp>>>4); //0000 0101
//=====================================================================================
//      Isolates left 4 bits.
        int unsignedByte = b1 & 0xFF; //1100 0101
        int temp2 = (unsignedByte>>>4)&0xFF; //1100
        byte leftBits= (byte)temp2; //0000 1100
//=====================================================================================
//      Combines the 2 - 4 bits back together
        byte leftBitsMovedLeft = (byte)(leftBits<<4); //1100 0000
        byte together =(byte) (leftBitsMovedLeft | rightBits); //1100 0101
//=====================================================================================



    }
}
