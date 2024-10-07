package pl.edu.pwr.student.damian_fryc.lab1;

public class Main {
	public static void main(String[] args) {
		Encryptor encryptor = new Encryptor(new char[][]
				{
//					{'a', 'b'},
//					{'c', 'd'},

//					{'a', 'b', 'c'},
//					{'d', 'e', 'f'},
//					{'g', 'h', 'i'},

					{'a', 'b', 'c', 'd', 'e'},
					{'f', 'g', 'h', 'i', 'j'},
					{'k', 'l', 'm', 'n', 'o'},
					{'p', 'q', 'r', 's', 't'},
					{'u', 'v', 'w', 'x', ' '}
				});

		encryptor.printKey();
		String toEncrypt = "the quick brown fox jumps over the lazy dog";
		toEncrypt = toEncrypt.toLowerCase();

		System.out.println("Text to encrypt: " + toEncrypt + " (" + toEncrypt.length() + ")");

		String encrypted = encryptor.encrypt(toEncrypt);
		System.out.println("Encrypted: " + encrypted + " (" + encrypted.length() + ")");

		String decrypted = encryptor.decrypt(encrypted);
		System.out.println("Decrypted: " + decrypted + " (" + decrypted.length() + ")");

		System.out.println("Are the same - " + toEncrypt.equals(decrypted));
	}
}
