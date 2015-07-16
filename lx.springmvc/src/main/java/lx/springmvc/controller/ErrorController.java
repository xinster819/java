package lx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class ErrorController {
    @RequestMapping("dd")
    public String dd() {
        System.out.println("123");
        return "123";
    }

    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4, 5 };
        System.out.println(addOne(a));
    }

    public static int addOne(int[] arr) {
        int total = 0;
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                total += ++arr[i];
            }
        }
        return total;
    }
}
