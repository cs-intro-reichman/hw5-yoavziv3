public class Wordle {

    public static String[] readDictionary(String filename) {
        In in = new In("dictionary.txt");
        String[] words = in.readAllLines();
        return words;
    }


    public static String chooseSecretWord(String[] dict) {
        int i = (int) (Math.random() * dict.length);
        return dict[i].toLowerCase();
    }


    public static boolean containsChar(String secret, char c) {
        for(int i=0; i<secret.length(); i++)
        {
            if(secret.charAt(i) == c)
            {
                return true;
            }
        }
        return false;
    }


    public static void computeFeedback(String secret, String guess, char[] resultRow) {
        for(int i=0; i<guess.length(); i++)
        {
            if(containsChar(secret, guess.charAt(i)))
            {
                if(secret.charAt(i) == guess.charAt(i))
                {
                    resultRow[i] = 'G';
                }
                else
                {
                    resultRow[i] = 'Y';
                }
            }
            else
            {
                resultRow[i] = '_';
            }
        }
    }

    
    public static void storeGuess(String guess, char[][] guesses, int row) {
		for(int i=0; i<guess.length(); i++)
        {
            guesses[row][i] = guess.charAt(i);
        }
    }

    
    public static void printBoard(char[][] guesses, char[][] results, int currentRow) {
        System.out.println("Current board:");
        for (int row = 0; row <= currentRow; row++) {
            System.out.print("Guess " + (row + 1) + ": ");
            for (int col = 0; col < guesses[row].length; col++) {
                System.out.print(guesses[row][col]);
            }
            System.out.print("   Result: ");
            for (int col = 0; col < results[row].length; col++) {
                System.out.print(results[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    
    public static boolean isAllGreen(char[] resultRow) {
		for(int i=0; i<resultRow.length; i++)
        {
            if(resultRow[i] != 'G')
            {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        int WORD_LENGTH = 5;
        int MAX_ATTEMPTS = 6;
        
        
        String[] dict = readDictionary("dictionary.txt");

        
        String secret = chooseSecretWord(dict);

        
        char[][] guesses = new char[MAX_ATTEMPTS][WORD_LENGTH];
        char[][] results = new char[MAX_ATTEMPTS][WORD_LENGTH];
 
        In inp = new In();

        int attempt = 0;
        boolean won = false;

        while (attempt < MAX_ATTEMPTS && !won) {

            String guess = "";
            boolean valid = false;

            
            while (!valid) {
                System.out.print("Enter your guess (5-letter word): ");
                guess = inp.readLine();
                
                if (guess.length() != 5) {
                    System.out.println("Invalid word. Please try again.");
                } else {
                    valid = true;
                }
            }

            storeGuess(guess, guesses, attempt);
            computeFeedback(secret, guess, results[attempt]);

            
            printBoard(guesses, results, attempt);

            
            if (isAllGreen(results[attempt])) {
                System.out.println("Congratulations! You guessed the word in " + (attempt + 1) + " attempts.");
                won = true;
            }

            attempt++;
        }

        if (!won) {
            System.out.println("Sorry, you did not guess the word.\r\n" + 
                                "The secret word was: " + secret);
        }

        inp.close();
    }
}
