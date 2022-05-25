import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ScrabbleHelper {

    private ArrayList<String> wordList = new ArrayList<String>();

    public ScrabbleHelper() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("files/enable.txt"));
        int i =0;
        while (scan.hasNext()){
            String word1 = scan.nextLine();
            wordList.add(word1);
            i++;
        }
        scan.close();
    }

    public ArrayList getWordList() {
        return wordList;
    }

    public boolean foundWord(String word) {
        word = word.toLowerCase();
        int min = 0;
        int max = wordList.size()-1;
        int mid = wordList.size()/2;
        while (min<=max) {
            if (wordList.get(mid).compareTo(word)==0) {
                return true;
            }
            else if (wordList.get(mid).compareTo(word)>0) {
                max =mid-1;
                mid = ((min+max)/2);

            }
            else if (wordList.get(mid).compareTo(word)<0) {
                min = mid+1;
                mid = ((min+max)/2);
            }

        }
        return false;

    }
}
