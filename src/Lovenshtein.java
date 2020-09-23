public class Lovenshtein {
    /*
    OneEditApart("cat", "dog") -> false
    OneEditApart("cat", "cats") -> true
    OneEditApart("cat", "cut") -> true
    OneEditApart("cat", "tac") -> false
    OneEditApart("cat", "cast") -> true
    OneEditApart("cat", "at") -> true
    OneEditApart("cat", "acts") -> false
    */

    public static void main(String[] args) {

        System.out.println(lov("cat", "dog"));
        System.out.println("false");

        System.out.println(lov("cat", "cats"));
        System.out.println("true");

        System.out.println(lov("cat", "cut"));
        System.out.println("true");

        System.out.println(lov("cat", "tac"));
        System.out.println("false");

        System.out.println(lov("cat", "cast"));
        System.out.println("true");

        System.out.println(lov("cat", "at"));
        System.out.println("true");

        System.out.println(lov("cat", "acts"));
        System.out.println("false");

        System.out.println(lov("123456789", "234567891"));
        System.out.println("false");

    }

    private static boolean lov(String a, String b){

        int al=0;
        int bl=0;
        int wrong=0;

        while (al!=a.length()&&bl!=b.length()){


            if (a.charAt(al)==b.charAt(bl)){
                ++al; ++bl;
            }else if (a.length()>b.length()){
                ++al;
                ++wrong;
            }else if (a.length()<b.length()){
                ++bl;
                ++wrong;
            }else {
                ++al; ++bl;
                ++wrong;
            }

        }

        System.out.println(wrong);

        return wrong<=1;

    }
}
