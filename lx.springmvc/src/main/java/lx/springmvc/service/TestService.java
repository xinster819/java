package lx.springmvc.service;

import org.springframework.beans.factory.InitializingBean;

public class TestService implements InitializingBean {

    String name;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("alchemist" + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
