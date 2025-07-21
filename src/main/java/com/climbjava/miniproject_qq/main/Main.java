package com.climbjava.miniproject_qq.main;

import static com.climbjava.miniproject_qq.utils.QqUtils.nextInt;

import com.climbjava.miniproject_qq.domain.Admin;
import com.climbjava.miniproject_qq.service.AdminService;
import com.climbjava.miniproject_qq.service.CustomerService;
import com.climbjava.miniproject_qq.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Main {
  UserService us;
  CustomerService cs;
  AdminService as;

  public void init() throws Exception {
    while(true) {
      try {
        if(us.getLoginUser() == null) { // 비로그인 상태
          System.out.println("===============비로그인 상태");
          int input = nextInt("[1.회원가입] [2.로그인]");
          switch (input) {
            case 1 :
              us.register();
              break;
            case 2 :
              us.login();
              break;
          }
        } else if(us.getLoginUser() instanceof Admin){
          as.adminInit(); // Login Admin
        } else {
          cs.init(); // Login Customer
        }
      }	catch (NumberFormatException e) {
        System.out.println("정확한 숫자를 입력하세요");
      }	catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    } //while(true) 닫기
  }
}
