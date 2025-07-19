package com.climbjava.miniproject_qq.service;

import com.climbjava.miniproject_qq.domain.Customer;

import static com.climbjava.miniproject_qq.utils.QqUtils.nextInt;
import static com.climbjava.miniproject_qq.utils.QqUtils.nextLine;

import java.util.List;


public class CustomerService {
	private static final CustomerService CUSTOMER_SERVICE = new CustomerService();
	public CustomerService () {
		
	}
	public static CustomerService getInstance() {
		return CUSTOMER_SERVICE;
	}
	
	List<Customer> customers;
	private Customer Customer;
	private char[] loginCustomer;

	
	public  void init () {
		System.out.println("===============손님 로그인 상태"); 
		int input = nextInt("[1.메뉴보기 2. 장바구니 담기 3. 장바구니 빼기 4. 결제하기 5.내정보 6. 로그아웃]");	
		//메뉴보기 - 메뉴클래스 , 장바구니 담기, 장바구니 빼기, 결제하기 - order 클래스
		switch (input) {
			case 1 : 
				MenuService.getInstance().read();
				break;
			case 2: 
				OrderService.getInstance().getItem();
				break;
			case 3 : 
				OrderService.getInstance().deleteItem();
				break;
			case 4 :
				OrderService.getInstance().pay();
				break;
			case 5 :
				UserService.getInstance().modify(Customer);
				
				break;
			case 6 :
				UserService.getInstance().logout();
				break;
		}
	}
	// 소비금액조회, 정보 수정
	public void  Mypage () {
		System.out.println("내정보보기");
		System.out.println(loginCustomer);
		System.out.println("소비금액 조회");
		//com.climbjava.miniproject_qq.service.OrderService.getInstance().print(com.climbjava.miniproject_qq.service.OrderService.getInstance().findByPayment(Customer c));
	}
	public void modify() {
		System.out.println("정보수정");
		String pw = nextLine("비밀번호 > ");
//		Customer c = findBy(pw, null);
		}
	
	}
	
	

