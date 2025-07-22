package com.climbjava.miniproject_qq;

import com.climbjava.miniproject_qq.ui.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
       ApplicationContext context = SpringApplication.run(Application.class, args);

       Main m = context.getBean(Main.class);

       m.init();
    }

}
