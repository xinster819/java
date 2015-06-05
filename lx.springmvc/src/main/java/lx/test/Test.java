package lx.test;

//import junit.framework.TestCase;

public class Test {

    public static void main(String[] args) {
        String s = "abc";
        int[] value = { 1, 3, 4 };
        // char[] value = s.toCharArray();
        int n = s.length() - 1;
        for (int j = (n - 1) / 2; j >= 0; --j) {
            int temp = value[j];
//            int temp2 = value[n - j];
            value[j] = value[n - j];
            value[n - j] = temp;
        }
        for (Integer v : value) {
            System.out.println(v);
        }
    }
}
