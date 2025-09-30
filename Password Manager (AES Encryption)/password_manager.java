import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class PasswordManager {
    private static final String ALGO = "AES";
    private static SecretKeySpec secretKey;

    static {
        try {
            // Generate fixed key (for demo)
            byte[] keyBytes = "MySuperSecretKey".getBytes(); // 16 chars = 128-bit
            secretKey = new SecretKeySpec(keyBytes, ALGO);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("1. Save Password 2. Retrieve Password 3. Exit");
            int c = sc.nextInt(); sc.nextLine();
            if(c==1) {
                System.out.print("Enter password: ");
                String pass = sc.nextLine();
                String enc = encrypt(pass);
                System.out.println("Encrypted & Saved: " + enc);
            } else if(c==2) {
                System.out.print("Enter encrypted string: ");
                String enc = sc.nextLine();
                System.out.println("Decrypted password: " + decrypt(enc));
            } else break;
        }
    }
}
