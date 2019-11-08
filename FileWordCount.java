import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/**
 * This is a class that reads in a text file and outputs
 * the top ten most used word, along with the must used
 * word's last sentence.
 * Please put the wanted read-in text file in the same folder
 * as this java program.
 */
class WordCounter {
    public static void main(String[] args) throws Exception 
    {
        String path = System.getProperty("user.dir") + File.separator + "passage.txt";
        File file = new File(path);
        Scanner read_file = new Scanner(file); 
        Map<String, Integer> stringTracker = new HashMap<>();
        String input = "";
        String current_sentence = "";
        Integer mostTracked = 0;
        Boolean change_sentence = true;
        String last_sentence = "";
        Integer word_count = 0;
        Integer total_count = 0;
        while (read_file.hasNext()){
            input = read_file.next();
            total_count++;
            if (current_sentence.length() == 0){
                current_sentence = input;
            }
            else{
                current_sentence = current_sentence + " " + input;
            }
            input = input.toLowerCase();
            // When there is a . that marks the end of a sentence.
            if (input.charAt(input.length() - 1)== '.'){
                input = input.substring(0, input.length() - 1);
                if (change_sentence){
                    last_sentence = current_sentence;
                }
                current_sentence = "";
                change_sentence = false;
            }
            input = input.replaceAll("[^a-zA-Z]", "");
            word_count = stringTracker.get(input);
            if (word_count == null){
                stringTracker.put(input, 1);
            }
            else {
                // Checks if the top word has changed, which means the sentence
                // must change as well.
                if (word_count > mostTracked){
                    change_sentence = true;
                    mostTracked = word_count;
                }
                stringTracker.put(input, word_count + 1);
            }
        }
        List<Map.Entry<String, Integer>> words = new ArrayList<Map.Entry<String, 
        Integer>>(stringTracker.entrySet());
        // Must change the comparator because there isn't a comparator for map entries
        Collections.sort(words, new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> entry1,  
                               Map.Entry<String, Integer> entry2){
                                   return entry2.getValue().compareTo(entry1.getValue());
                               }
        });

        System.out.println("Total Word Count:");
        System.out.println("------------------------");
        System.out.println(total_count);
        System.out.println("The Top Ten Used Words:");
        System.out.println("------------------------");
        for (int i = 0; i < 10; i++){
            System.out.println(words.get(i).getKey() + ": " + words.get(i).getValue());
        }
        System.out.println("The most used word's last sentence:");
        System.out.println("------------------------");
        System.out.println(last_sentence);
        read_file.close();
    }
}