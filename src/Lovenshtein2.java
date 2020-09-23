public class Lovenshtein2 {


    public static boolean lov(String f, String s) {
        if (f.length() == s.length()) {
            return distinctLetters(f, s) == 1;
        }

        if (s.length() - f.length() == 1) {
            if(s.contains(f))
                return true;
            else return equalsWithoutLetter(s,f);
        }
        if (f.length() - s.length() == 1) {
            if(f.contains(s))
                return true;
            else return equalsWithoutLetter(f,s);
        }

        return false;
    }

    public static boolean equalsWithoutLetter(String big, String small){
        for(int i=0;i<big.length(); i++){
            if(wordWithoutLetter(big,i).equals(small)){
                return true;
            }
        }
        return false;
    }

    public static String wordWithoutLetter(String word, int index){
        if(index==0) return word.substring(1);

        return word.substring(0,index)+word.substring(index+1);
    }

    public static int distinctLetters(String f, String s) {
        int cnt = 0;
        for (int i = 0; i < f.length(); i++) {
            if (f.charAt(i) != s.charAt(i)) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String ... args) {
        //    если отличается на одну букву true при одинаковой длинне
        //    если разницы в длине 1 и одно слово входит в другое
        //    если отличается в длине на 1 и убрав в длинном слове букву получм короткое

        System.out.println(lov("cat", "dog"));
        System.out.println("false\n");

        System.out.println(lov("cat", "cats"));
        System.out.println("true\n");

        System.out.println(lov("cat", "cut"));
        System.out.println("true\n");

        System.out.println(lov("cat", "tac"));
        System.out.println("false\n");

        System.out.println(lov("cat", "cast"));
        System.out.println("true\n");

        System.out.println(lov("cat", "at"));
        System.out.println("true\n");

        System.out.println(lov("cat", "acts"));
        System.out.println("false\n");
    }
}