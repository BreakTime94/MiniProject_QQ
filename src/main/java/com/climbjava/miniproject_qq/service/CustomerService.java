package com.climbjava.miniproject_qq.service;

import com.climbjava.miniproject_qq.domain.Customer;
import com.climbjava.miniproject_qq.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.climbjava.miniproject_qq.utils.QqUtils.*;

@NoArgsConstructor
@Service
public class CustomerService {
	private UserService us;
	private OrderService os;
	@Autowired
	public CustomerService(UserService us, OrderService os) {
		this.us = us;
		this.os = os;
	}



//    private Customer loginCustomer = (Customer)(UserService.getInstance().getLoginUser());

	public void init() {
		System.out.println("===============손님 로그인 상태");
		Customer customer = us.findBy(us.getLoginUser().getId(), Customer.class);
		int input = nextInt("[1.메뉴보기 2. 장바구니 담기 3. 장바구니 빼기 4. 결제하기 5.내정보 6. 로그아웃]");
		// 메뉴보기 - 메뉴클래스 , 장바구니 담기, 장바구니 빼기, 결제하기 - order 클래스
		switch (input) {
			case 1:MenuService.getInstance().read();
				while (true) {
					int menuInput = nextInt("[1.가격 범위로 메뉴 검색] [2.선택한메뉴 가격 합계] [0.뒤로가기]");
					switch (menuInput) {
						case 1:
							MenuService.getInstance().PriceRange();
							break;
						case 2:
							MenuService.getInstance().totalMenuPrice();
							break;
					}
					if (menuInput == 0)
						break;
				}
				break;

			case 2:
				os.getItem();
				break;
			case 3:
				os.deleteItem();
				break;
			case 4:
				os.pay();
				break;
			case 5:
				mypage(customer);
				break;
			case 6:
				us.logout();
				break;
		}
	}
	// 소비금액조회, 정보 수정

	public void mypage(Customer customer) {
		int input = nextInt("[1.소비금액 조회 2. 정보수정 3. 정보삭제 4. 뒤로가기]");
		// 메뉴보기 - 메뉴클래스 , 장바구니 담기, 장바구니 빼기, 결제하기 - order 클래스
		switch (input) {
			case 1:
				os.findByPayment(customer);
				break;
			case 2:
				modify();
				break;
			case 3:
				us.remove();
				break;
			case 4:
				return;
		}
	}

	public void modify() { // 비밀번호 입력, 정보 조회, 현재 비밀번호 일치하지 않음, 새로운 비밀번호 입력 , 탈퇴하기
		String pw = nextLine("비밀번호 입력");
		User user = us.getLoginUser();
		if(!user.getPw().equals(pw)) {
			System.out.println("비밀번호가 일치하지 않습니다");
			return;
		}

		String newPw = nextLine("새 비밀번호 입력");
		String name = nextLine("새 이름 입력");

		user.setName(name);
		user.setPw(newPw);

		System.out.println("수정 완료 되었습니다");
	}

	public void remove() {
		System.out.println("탈퇴");
		if (!nextConfirm("[정말 탈퇴하시겠습니까?]")) {
			us.remove();
			System.out.println("[정상적으로 탈퇴 되었습니다.]");
			remove();
			return;
		} else {
			System.out.println("[탈퇴 취소되었습니다.]");
			return;

		}
	}
}