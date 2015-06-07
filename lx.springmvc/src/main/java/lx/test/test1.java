package lx.test;

import static org.junit.Assert.assertEquals;

public class test1 {

    public int Cal(int a, int b)
    {
        return a+b;
    }
    public void setUp() throws Exception {
    }

    public void tearDown() throws Exception {
    }

    public void test() {
        int a =2;
        int b =3;
        int sum = Cal(a,b);
        assertEquals(sum,5);
        
    }

}
