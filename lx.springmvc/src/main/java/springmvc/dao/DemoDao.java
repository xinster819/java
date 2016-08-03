package springmvc.dao;

import org.springframework.stereotype.Component;

@Component
public class DemoDao {

    public String byId(int id) {
        return "demo";
    }

}
