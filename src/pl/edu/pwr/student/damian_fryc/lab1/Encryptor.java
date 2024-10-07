package pl.edu.pwr.student.damian_fryc.lab1;

public class Encryptor {
	private char[][] key;
	public Encryptor(char[][] newKey) {
		setKey(newKey);
	}
	public void setKey(char[][] key) {

			// check if the key is a square
		for (int i = 0; i < key.length; i++) {
			if(key[i].length != key.length) {
				this.key = null;
				return;
			}
		}
			// key to lower case
		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key[i].length; j++) {
				key[i][j] = Character.toLowerCase(key[i][j]);
			}
		}

		this.key = key;
	}

	public void printKey() {
		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key[i].length; j++) {
				System.out.print(key[i][j] + " ");
			}
			System.out.print('\n');
		}
	}
	private char[] getColumn(int col) {
		char[] column = new char[key[0].length];
		for (int i = 0; i < key[0].length; i++) {
			column[i] = key[i][col];
		}
		return column;
	}
	private int[][] locatePair(char firstLetter, char secondLetter) {
		int[][] pairPositions = new int[][]{{-1,-1},{-1,-1}};

		for (int j = 0; j < key.length; j++) {
			for (int k = 0; k < key[j].length; k++) {
				if (key[j][k] == firstLetter)
					pairPositions[0] = new int[]{j, k};
				if (key[j][k] == secondLetter)
					pairPositions[1] = new int[]{j, k};
			}
		}
		return pairPositions;
	}
	public String encrypt(String text) {
		if(key == null) return "Null key";
		text = text.toLowerCase();
		if(text.length() % 2 == 1) text += 'X';

		String encrypted = "";
		for (int i = 0; i < text.length()-1; i+=2) {
			int[][] pairPositions = locatePair(text.charAt(i), text.charAt(i+1));

			if(pairPositions[1][0] == -1) {
				if(text.charAt(i + 1) == 'X'){
					encrypted = encrypted + text.charAt(i);
					i++;
					continue;
				}
				else{
					System.out.println(text.charAt(i + 1) + " not found in the key");
					encrypted = encrypted + text.charAt(i) + text.charAt(i+1);
					continue;
				}
			}
			if(pairPositions[0][0] == -1) {
				System.out.println(text.charAt(i) + " not found in the key");
				encrypted = encrypted + text.charAt(i) + text.charAt(i+1);
				continue;
			}

				// p and q are the same letter
			if(pairPositions[0][0] == pairPositions[1][0] && pairPositions[0][1] == pairPositions[1][1]){
				encrypted = encrypted + text.charAt(i) + "X" + text.charAt(i+1);
			}
				// p and q are in the same row
			else if(pairPositions[0][0] == pairPositions[1][0]){
				char[] row = key[pairPositions[0][0]];
				encrypted = encrypted + row[(pairPositions[0][1] + 1) % row.length];
				encrypted = encrypted + row[(pairPositions[1][1] + 1) % row.length];
			}
				// p and q are in the same column
			else if(pairPositions[0][1] == pairPositions[1][1]){
				char[] column = getColumn(pairPositions[0][1]);
				encrypted = encrypted + column[(pairPositions[0][0] + 1) % column.length];
				encrypted = encrypted + column[(pairPositions[1][0] + 1) % column.length];

			}
				// p and q are in the corners
			else{
				encrypted = encrypted + key[pairPositions[1][0]][pairPositions[0][1]];
				encrypted = encrypted + key[pairPositions[0][0]][pairPositions[1][1]];
			}

		}
		if(encrypted.length() % 2 == 1) encrypted = encrypted + 'X';
		return encrypted;
	}

	public String decrypt(String text){
		if(key == null) return "Null key";

			// Count Xs to add extra if not even
		int XCount = 0;
			// text.length() - 1, because the last X shouldn't be counted
		for (int i = 0; i < text.length() - 1; i++)
			if(text.charAt(i) == 'X') XCount++;
		if((text.length() + XCount) % 2 == 1) text += 'X';

		String decrypted = "";

		for (int i = 0; i < text.length()-1; i+=2) {
			int[][] pairPositions = locatePair(text.charAt(i), text.charAt(i+1));


			if(pairPositions[1][0] == -1) {
					// check if the pair has a X on second place, which means that's a repeated letters pair (p and q are the same)
					// or an end of the string
				if(text.charAt(i + 1) == 'X'){
						// odd number of repeated - makes 2 Xs on the end
					if(text.charAt(i) == 'X')
						continue;

					decrypted = decrypted + text.charAt(i);
					if(i + 2 < text.length())
						decrypted = decrypted + text.charAt(i);
					i++;
					continue;
				}
				else{
					System.out.println(text.charAt(i + 1) + " not found in the key");
					decrypted = decrypted + text.charAt(i) + text.charAt(i+1);
					continue;
				}
			}
			if(pairPositions[0][0] == -1) {
				System.out.println(text.charAt(i) + " not found in the key");
				decrypted = decrypted + text.charAt(i) + text.charAt(i+1);
				continue;
			}


				// p and q are in the same row
			if(pairPositions[0][0] == pairPositions[1][0]){
				char[] row = key[pairPositions[0][0]];
				decrypted = decrypted + row[(pairPositions[0][1] + row.length- 1) % row.length];
				decrypted = decrypted + row[(pairPositions[1][1] + row.length- 1) % row.length];
			}
				// p and q are in the same column
			else if(pairPositions[0][1] == pairPositions[1][1]){
				char[] column = getColumn(pairPositions[0][1]);
				decrypted = decrypted + column[(pairPositions[0][0] + column.length- 1) % column.length];
				decrypted = decrypted + column[(pairPositions[1][0] + column.length- 1) % column.length];

			}
				// p and q are in the corners
			else{
				decrypted = decrypted + key[pairPositions[1][0]][pairPositions[0][1]];
				decrypted = decrypted + key[pairPositions[0][0]][pairPositions[1][1]];
			}

		}

		return decrypted;
	}
}
