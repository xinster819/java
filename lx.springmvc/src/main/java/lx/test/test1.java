package lx.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class test1 {

    public int Cal(int a, int b)
    {
        return a+b;
    }
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        int a =2;
        int b =3;
        int sum = Cal(a,b);
        assertEquals(sum,5);
        
    }

}
