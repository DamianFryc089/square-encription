package pl.edu.pwr.student.damian_fryc.lab1;

public class Main {
	public static void main(String[] args) {
		Encryptor encryptor = new Encryptor(new char[][]{{'a', 'b'},{'c','d'}});
		System.out.println(encryptor.encrypt("ad"));
	}
}
