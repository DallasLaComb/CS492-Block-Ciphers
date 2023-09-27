package Homework2_BlockCiphers;

public class testing {
    public static void main(String[] args) {
        ByteManipulation manipulation = new ByteManipulation();
        BinaryUtil format = new BinaryUtil();
        byte a = (byte) 255;
        System.out.println(format.byteToBinaryString(a));

        byte left = manipulation.isolateLeft4Bits((a));
        System.out.println("Left Isolation: "+format.byteToBinaryString(left));

        byte right = manipulation.isolateRight4Bits((a));
        System.out.println("Right Isolation: "+format.byteToBinaryString(right));

        byte combined = manipulation.combineLeft4BitsandRight4Bits(left,right);
        System.out.println("Combonation: "+format.byteToBinaryString(combined));

    }
}
