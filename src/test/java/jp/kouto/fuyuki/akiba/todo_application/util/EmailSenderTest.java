package jp.kouto.fuyuki.akiba.todo_application.util;

import org.junit.jupiter.api.Test;

class EmailSenderTest {

	@Test
	void test() {
		try {
			EmailSender sender = new EmailSender();
			sender.sendMail("hiroyaforwork@gmail.com", "アプリからのテスト", "開発環境から自宅サーバーを使用してます。");
			System.out.println("送信成功");
		} catch(Exception e) {
			System.out.println("エラー発生");
			e.printStackTrace();
		}
	}

}
