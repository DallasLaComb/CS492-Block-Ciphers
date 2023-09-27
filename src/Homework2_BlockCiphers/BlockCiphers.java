package Homework2_BlockCiphers;



public class BlockCiphers {
//    The subsititution method is a Switch case statement that can take input of all 4bit combos, and maps it to a new unique 4 bit combo.
//    I made mine very simple in order to test that the code is working properly... Each input just maps to it's +1, and
//    15 maps back to 0.
    public static byte substitute(byte fourbit){
        switch (fourbit){
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
            default:
                return -100;
        }
    }
//  ecbEncrypt() Iterates through each index/byte in the message array and substitutes the message with what is mapped on my
//  substitute method. Each itteration it stores this substituted / cipher text in a temp array which is returned in the end.
//  I only created a temp array that it returns, as I didn't want to change my actual message array, as I'll be using the same
//  plain text messages in the other encryption methods too.

    public static byte[] isolate4bits(byte[] message){
        byte [] fourBitArray = new byte[message.length*2];
        for(int i=0;i<message.length;i+=2){
            byte currentByte = message[i];
            byte left4Bits=(byte)(currentByte >> 4);
            left4Bits = (byte)(left4Bits&0x0F);

            byte right4Bits=(byte)(currentByte&0x0F);
            fourBitArray[i]=left4Bits;
            fourBitArray[i+1]=right4Bits;
        }
        return fourBitArray;
    }

    public static byte[] combineEncryptedBits(byte[] fourBitArray){
        byte[] eightBitArray = new byte[fourBitArray.length/2];

        for(int i=0;i<eightBitArray.length;i+=2){
            byte left4Bits = (byte)((fourBitArray[i])<<4);
            byte right4Bits = (byte)(fourBitArray[i+1]);
            eightBitArray[i]=(byte)(left4Bits | right4Bits);
        }
        return eightBitArray;
    }


    public static byte[] ecbEncrypt(byte [] message){
        ByteManipulation manipulation = new ByteManipulation();
        byte [] byteArray = new byte[message.length];
        byte[] input = message;

        for (int i =0;i<message.length;i++){
            byte right4Bits = manipulation.isolateRight4Bits(input[i]);
            byte left4Bits = manipulation.isolateLeft4Bits(input[i]);

            byte substitutedRight4Bits = substitute(right4Bits);
            byte substitutedLeft4Bits = substitute(left4Bits);

            byteArray[i] = manipulation.combineLeft4BitsandRight4Bits(substitutedLeft4Bits,substitutedRight4Bits);
        }
        return byteArray;
    }
//  ctrEncrypt() takes a byte of any 4 bit combo and iterates through your whole plain text message.
//  Each iteration it is mapping (substituting) your IV to my corresponding substitute method. In my case, each 4 bit maps to it's +1, and 15 maps back to 0.
//  Each iteration after it is done mapping your IV, it XORs the newly substituted IV with the corresponding byte(4 bits in our case, with 4 0's prefixed) in our plain text message.
//  Each iteration after it is done XORing, it increments the IV 1 decimal (0000 0001 binary) unit to create IV+1,IV+2,IV+3,IV.n...
    public static byte[] ctrEncrypt(byte iv, byte[] message){
        ByteManipulation manipulation = new ByteManipulation();

        byte ivTemp = iv;
        byte[] cipher = new byte[message.length];
        for (int i=0;i<message.length;i++) {
            cipher[i] = (byte)(substitute(ivTemp)^message[i]);
            if (ivTemp!=15){
                ivTemp++;
            }
            else{
                ivTemp=0;
            }
        }
        return cipher;
    }
//    IV XOR Message
//    XOR'd IV/Message Substituted.
//    IV becomes your substituted XOR message from previous iteration
    public static byte[] cbcEncrypt(byte iv, byte[] message){
        ByteManipulation manipulation = new ByteManipulation();

        byte ivTemp = iv;
        byte[] cipher = new byte[message.length];
        for (int i=0;i<message.length;i++){
            cipher[i] = (byte)(ivTemp ^ message[i]);
            cipher[i] = substitute(cipher[i]);
            ivTemp = cipher[i];
        }
        return cipher;
    }
//    ==================================================================================
    public static void main(String[] args) {
//Initializing Object for Homework2.BinaryUtil Class - Recommended class to format bytes by professor Chad Williams
        BinaryUtil formatter = new BinaryUtil();
//These will be the same 16 / 32 Bit Plain Text Inputs for all encryption testing.
        byte[] plainText16Bit= {(byte)100,(byte)100};
        byte[] plainText32Bit = {(byte)200,(byte)130,(byte)170,(byte)80};
//My IV for ctrEncrypt
        byte iv = 14;
//====================================
        plainText16Bit = isolate4bits(plainText16Bit);
//Giving a variable byte array to hold my ECB Encryptions.
        byte[] ecbEncryption16 = ecbEncrypt(plainText16Bit);
        byte[] ecbEncrypiton32 = ecbEncrypt(plainText32Bit);
//Giving a variable byte array to hold my CTR Encryptions.
        byte[] ctrEncryption16 = ctrEncrypt(iv,plainText16Bit);
        byte[] ctrEncryption32 = ctrEncrypt(iv,plainText32Bit);
//Giving a variable byte array to hold my CBC Encryptions.
        byte[] cbcEncryption16 = cbcEncrypt(iv,plainText16Bit);
        byte[] cbcEncryption32 = cbcEncrypt(iv,plainText32Bit);



//Giving a variable to my formatted ECB encryption. It is formatted via the Homework2.BinaryUtil Class that Professor Chad Williams recommended to use in black board.
        String cipherText16ECB = formatter.bytesToBlocksString(ecbEncryption16);
        String cipherText32ECB = formatter.bytesToBlocksString(ecbEncrypiton32);
//Giving a variable to my formatted CTR encryption. It is formatted via the Homework2.BinaryUtil Class that Professor Chad Williams recommended to use in black board.
//        String cipherText16CTR = (ctrEncryption16);
//        String cipherText32CTR = (ctrEncryption32);
//Giving a variable to my formatted CTR encryption. It is formatted via the Homework2.BinaryUtil Class that Professor Chad Williams recommended to use in black board.
        String cipherText16CBC = formatter.bytesToBlocksString(cbcEncryption16);
        String cipherText32CBC = formatter.bytesToBlocksString(cbcEncryption32);


        System.out.println("16-bit plaintext input: "+formatter.bytesToBlocksString(plainText16Bit));
        System.out.println("32-bit plaintext input: " +formatter.bytesToBlocksString(plainText32Bit));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("ECB 16-bit Ciphertext: "+cipherText16ECB);
        System.out.println("ECB 32-bit Ciphertext: "+cipherText32ECB);
        System.out.println("--------------------------------------------------------------------------------");
//        System.out.println("CTR 16-bit Ciphertext: "+cipherText16CTR);
//        System.out.println("CTR 32-bit Ciphertext: "+cipherText32CTR);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("CBC 16-bit Ciphertext: "+cipherText16CBC);
        System.out.println("CBC 32-bit Ciphertext: "+cipherText32CBC);
    }
}