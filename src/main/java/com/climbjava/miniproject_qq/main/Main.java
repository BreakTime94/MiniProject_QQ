package com.climbjava.miniproject_qq.main;

import static com.climbjava.miniproject_qq.utils.QqUtils.nextInt;

import com.climbjava.miniproject_qq.domain.Admin;
import com.climbjava.miniproject_qq.service.CustomerService;
import com.climbjava.miniproject_qq.service.UserService;

public class Main {
	public static void main(String[] args) {
		while(true) {
			try {	
				if(UserService.getInstance().getLoginUser() == null) { //null :비로그인 상태, 그외 : 로그인 상태
					int input = nextInt("[1.회원가입] [2.로그인]");
					switch (input) {
						case 1 : 
							UserService.getInstance().register();
							break;
						
						case 2 : 
							UserService.getInstance().login();
							break;
					}
				} else if(UserService.getInstance().getLoginUser().getClass() == Admin.class){ //관리자 서비스에서 만든 메뉴 호출
					System.out.println("===============관리자 로그인 상태");
				} else { //커스토머 서비스에서 만든 메뉴 호출
					System.out.println("===============손님 로그인 상태"); 
					CustomerService.getInstance().init();
				}
			}	catch (NumberFormatException e) {
				System.out.println("정확한 숫자를 입력하세요");
			}	catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		} //while(true) 닫기
	}
}
