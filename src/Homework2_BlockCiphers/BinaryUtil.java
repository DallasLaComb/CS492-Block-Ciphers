package Homework2_BlockCiphers;

public class BinaryUtil {
    public static String byteToBinaryString(byte passedByte) {
        String binaryString = Integer.toBinaryString(Byte.toUnsignedInt(passedByte));
        String prependZeros = "0".repeat(8 - binaryString.length());
        return prependZeros + binaryString;
    }

    // Assignment block is 4 bits
    public static String bytesToBlocksString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (byte curByte : bytes) {
            String byteString = byteToBinaryString(curByte);
            buffer.append(byteString.substring(0, 4) + " " + byteString.substring(4) + " ");
        }
        return buffer.toString();
    }

    public static void main(String args[]) {
        byte[] plaintext = {(byte) 0x2F, (byte) 0xE3, (byte) 0x84};
        System.out.println(bytesToBlocksString(plaintext));
    }
}