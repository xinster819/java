package lx.reflect;

import java.lang.reflect.Field;

public class FieldAccessible {
    public MyClass one = new MyClass("4444");

    public static class MyClass {
        public MyClass(String name) {
            this.theField = name;
        }

        public  String theField;
    }

    public static void main(String[] args) throws Exception {
        MyClass myClass = new MyClass("444");
        Field field1 = myClass.getClass().getDeclaredField("theField");
        field1.setAccessible(true);
        field1.set(myClass, "5555");
        System.out.println(field1.get(myClass));
        Field field2 = myClass.getClass().getDeclaredField("theField");
        field2.setAccessible(true);
        System.out.println(field2.get(myClass));
    }

}