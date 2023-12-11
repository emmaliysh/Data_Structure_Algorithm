import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of the Boyer Moore string searching algorithm.
 */
public class PatternMatching {
    /**
     * Boyer Moore algorithm that relies on a last occurrence table.
     * This algorithm Works better with large alphabets.
     *
     * Make sure to implement the buildLastTable() method, which will be
     * used in this boyerMoore() method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class useful.
     *
     * You may assume that the passed in pattern, text, and comparator will not be null.
     *
     * @param pattern    The pattern you are searching for in a body of text.
     * @param text       The body of text where you search for the pattern.
     * @param comparator You MUST use this to check if characters are equal.
     * @return           List containing the starting index for each match found.
     */
    public static List<Integer> boyerMoore(CharSequence pattern, CharSequence text, CharacterComparator comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!

        Map<Character, Integer> table = buildLastTable(pattern);

        int i = 0; // the comparison marker to show where the comparison is happening
        int m = pattern.length();
        int n = text.length();

        List<Integer> result = new ArrayList<>();
        if (m > n){
            return result;
        }

    
        while(i <= (n - m)){
            int j = m - 1; // index within the pattern starting from the end 

            while(j >= 0 && comparator.compare(text.charAt(i+j), pattern.charAt(j)) == 0){
                j--;

            }

            //case 1: all characters match 
            if (j == -1){
                result.add(i);// pattern found
                i++;
            
                
            } else{
                // check whether the mismatched character is in the table or not
                int shift = table.getOrDefault(text.charAt(i+j), -1);

            //case 2: no character matches --> all shift 
            //        some characters match but no need to go backwards
                if (shift < j){ 
                    i = i + (j - shift); // (j - shift) is the real shift (this is why it should not be <= 0, which means shift < j)

                } else { 
                    //case 3: some characters match but need to go backwards --> shift to right by 1
                    i++;

                }
            } 

        }

        return result;

    }

    /**
     * Builds the last occurrence table that will be used to run the Boyer Moore algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     * You may assume that the passed in pattern will not be null.
     *
     * @param pattern A pattern you are building last table for.
     * @return A Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern.
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        
        int m = pattern.length();

        HashMap<Character, Integer> lastTable = new HashMap<>(); 
    

        for (int i = 0; i < m; i++){
            lastTable.put(pattern.charAt(i), i);

        }


        return lastTable;

    }

    public static void main(String[] args) {
        

        CharacterComparator c = new CharacterComparator();
        CharSequence pattern = "COM";

        CharSequence text = "COMPUTERSCIENCE";

        

        
        System.out.println(boyerMoore(pattern, text, c));
        System.out.println(c.getComparisonCount());
        
        
    }
}