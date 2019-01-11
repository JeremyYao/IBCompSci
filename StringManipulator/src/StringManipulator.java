import java.util.LinkedList;
import java.util.*;
public class StringManipulator
{
    public static void main(String[] args)
    {
        String a = "sample";
        String b = "This is pretty cool";
        String c = "elephant";
        String d = "fireplace";

        System.out.println(removeVowels(a));
        System.out.println(removeVowels(b));
        System.out.println(scrambleWord(c));
        System.out.println(scrambleWord(d));
    }

    //  precondition:
    // postcondition: returns a new String which contains only
    //    all of the characters in the original except the vowels
    //    (EX: sample is returned as smpl)
    public static String removeVowels(String str)
    {
        LinkedList<Character> list = new LinkedList<>();
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        for (int i = 0; i < str.length(); i++)
        {
            boolean isNotVowel = true;
            for(char temp : vowels)
            {
                if(temp == str.toLowerCase().charAt(i)) {
                    isNotVowel = false;
                    break;
                }
            }

            if (isNotVowel)
            {
                list.add(str.charAt(i));
            }
        }

        str = "";
        for (char i : list)
        {
            str += i;
        }

        return str;

    }

    //  precondition: word is a single word
    // postcondition: returns a new string which is has the same
    //   first and last character as word and the rest of the
    //   characters have been scrambled randomly
    //   (EX: sample could be returned as smpale or splame)
    public static String scrambleWord(String word)
    {
        char[] wordArry = word.toCharArray();
        int pos1, pos2;

        for (int i = 0; i < 69; i++)
        {
            pos1 = (int)((word.length()-2) * Math.random() + 1);
            pos2 = (int)((word.length()-2) * Math.random() + 1);
            char temp = wordArry[pos1];
            wordArry[pos1] = wordArry[pos2];
            wordArry[pos2] = temp;
        }

        word = "";                                          
        for (char i : wordArry)
        {
            word += i;
        }
        return word;
    }

}