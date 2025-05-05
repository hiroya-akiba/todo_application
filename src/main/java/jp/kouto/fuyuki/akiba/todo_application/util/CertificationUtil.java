package jp.kouto.fuyuki.akiba.todo_application.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class CertificationUtil {
	/**
	 * パスワードのハッシュ化関数
	 * @param password
	 * @return
	 */
	public static String hashPassword(String password) {
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            
            // バイト列を16進数文字列に変換
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found");
        }
	}
	
	/**
	 * 6桁の認証用整数を生成する
	 * @return
	 */
	public static int createVerifyCode() {
		Random random = new Random();
        // 100000から999999までのランダムな6桁の数字を生成
        return 100000 + random.nextInt(900000);
	}
	
	/**
	 * 8桁の受付番号を生成する
	 * @return
	 */
	public static String createReceiptCode() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder receiptNumber = new StringBuilder();
		
		// 8文字のランダムな受付番号を生成
		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(characters.length());
			receiptNumber.append(characters.charAt(index));
		}
		return receiptNumber.toString();
	}
}