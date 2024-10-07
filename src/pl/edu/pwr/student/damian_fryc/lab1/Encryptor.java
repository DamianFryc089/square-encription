package pl.edu.pwr.student.damian_fryc.lab1;

public class Encryptor {
	private char[][] key;
	public Encryptor(char[][] key) {
		this.key = key;
	}
	public void printKey()
	{
		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key[i].length; j++) {
				System.out.print(key[i][j] + " ");
			}
			System.out.print('\n');
		}
	}
	private char[] getColumn(int col)
	{
		char[] column = new char[key[0].length];
		for (int i = 0; i < key[0].length; i++) {
			column[i] = key[i][col];
		}
		return column;
	}

	public String encrypt(String text) {

		if(text.length() % 2 == 1)
			text += 'X';

		String encrypted = "";
		printKey();
		System.out.println("Text to encrypt: " + text);
		for (int i = 0; i < text.length(); i+=2) {
			int[] firstLetterPosition = new int[]{-1,-1};
			int[] secondLetterPosition = new int[]{-1,-1};

			for (int j = 0; j < key.length; j++) {
				for (int k = 0; k < key[j].length; k++) {
					if (key[j][k] == text.charAt(i))
						firstLetterPosition = new int[]{j, k};
					if (key[j][k] == text.charAt(i+1))
						secondLetterPosition = new int[]{j, k};
				}
			}
			System.out.println("First letter: " + firstLetterPosition[0] + ", " + firstLetterPosition[1]);
			System.out.println("Second letter: " + secondLetterPosition[0] + ", " + secondLetterPosition[1]);

				// p and q are the same letter
			if(firstLetterPosition[0] == secondLetterPosition[0] && firstLetterPosition[1] == secondLetterPosition[1]){
				encrypted = encrypted + text.charAt(i) + "X" + text.charAt(i+1);
			}
				// p and q are in the same row
			else if(firstLetterPosition[0] == secondLetterPosition[0]){
				char[] row = key[firstLetterPosition[0]];
				encrypted = encrypted + row[((firstLetterPosition[1]) + 1) % row.length];
				encrypted = encrypted + row[((secondLetterPosition[1]) + 1) % row.length];
			}
				// p and q are in the same column
			else if(firstLetterPosition[1] == secondLetterPosition[1]){
				char[] column = getColumn(firstLetterPosition[1]);
				encrypted = encrypted + column[((firstLetterPosition[1]) + 1) % column.length];
				encrypted = encrypted + column[((secondLetterPosition[1]) + 1) % column.length];

			}
				// p and q are in the corners
			else{
				encrypted = encrypted + key[secondLetterPosition[0]][firstLetterPosition[1]];
				encrypted = encrypted + key[firstLetterPosition[0]][secondLetterPosition[1]];

			}
		}

		return encrypted;
	}
}
