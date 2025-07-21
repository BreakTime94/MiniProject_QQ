package com.climbjava.miniproject_qq.service;

import com.climbjava.miniproject_qq.domain.Admin;
import com.climbjava.miniproject_qq.domain.Customer;
import com.climbjava.miniproject_qq.domain.Order;
import com.climbjava.miniproject_qq.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.climbjava.miniproject_qq.utils.QqUtils.*;

@Service
public class AdminService {
	private OrderService os;
	private UserService us;
	@Autowired
	public AdminService(OrderService os, UserService us) {
		this.os = os;
		this.us = us;
	}

	// =============================== 메인 호출용 -- 관리자 로그인 상태
	public void adminInit() throws Exception {
		System.out.println("===============관리자 로그인 상태");
		int input = nextInt("[1.회원목록 조회] [2.관리자 권한 관리] [3.회원삭제] [4.메뉴관리] [5.매출조회] | [9.내정보수정] [0.로그아웃]");
		switch (input) {
			case 1 : //회원목록 조회
				read();
				break;
			case 2 : //관리자 권한 관리
				isSeller();
				break;
			case 3 : //회원삭제
				userRemove();
				break;
			case 4 : //메뉴관리
				menu();
				break;
			case 5 : //매출조회
				salesRecord();
				break;
			case 9 ://내정보수정
				us.modify();
				break;
			case 0 : //로그아웃
				us.logout();
				break;
		}
	}

	//-----------------회원 목록 조회
	public void read() {
		System.out.println("=======[회원 목록 조회]=======");
		int input = nextInt("[1.전체회원 보기] [2.관리자회원 보기] [3.일반회원 보기] | [0.뒤로가기]");
		switch (input) {
			case 1 : //전체회원보기
				us.printUser();
				break;
			case 2 : //관리자회원보기
				us.printAdmin();
				break;
			case 3 : //일반회원보기
				us.printCustomer();
				break;
			case 0 : return;
		}
	}

	//-----------------관리자 권한 관리
	//계정 존재유무 확인 >> 관리자 권한 소지유무 확인 >> 관리자 권한 부여 및 박탈
	public void isSeller() {
		System.out.println("=======[관리자 권한 관리]=======");

		String input = nextLine("[관리자 권한을 부여할 ID 또는 회원번호를 입력하세요] > ");

		boolean def = true;
		User t = us.findBy(input, User.class);
		if(t == us.getLoginUser()) {
			System.out.println("[(!)본인의 관리자 권한을 직접 삭제할 수 없습니다.]");
			def = false;
		}

		if (t instanceof Admin && def == true) { //해당 ID가 이미 Admin일때 권한 취소
			System.out.print("[(!)이미 관리자 등급인 계정입니다.]\n");
			if(nextConfirm("[이 계정의 관리자 권한을 취소하시겠습니까?](y/yes) > ")) {
				us.users.add(new Customer(t.getUserNo(), t.getName(), t.getId(), t.getPw()));
				us.users.remove(t);
				System.out.printf("[id : \"%s\"의 관리자 권한이 취소되었습니다.]", input);
			}
			return;
		}

		if (t instanceof Customer) { //해당 ID가 Customer일 때 Admin 권한 부여
			us.users.add(new Admin(t.getUserNo(), t.getName(), t.getId(), t.getPw() ));
			us.users.remove(t);
			System.out.printf("[id : \"%s\"에게 관리자 권한이 부여되었습니다.]\n", input);
		}
		if (t == null) { //해당 ID가 UserList에 존재하지 않을때
			System.out.println("[(!)존재하지 않는 계정입니다.]");
			return;
		}
	}

	//-----------------회원 삭제 //관리자 권한이 있을경우 삭제 불가능
	//계정 존재유무 확인 >> 관리자 권한 소지유무 확인 >> 정말 삭제할건지 확인
	public void userRemove() {
		System.out.println("=======[회원 정보 삭제]=======");
		String input = nextLine("[삭제할 회원의 ID를 입력하세요] > ");
		User t = us.findBy(input, User.class);
		if(t == us.getLoginUser()) {
			System.out.println("[(!)본인은 삭제할 수 없습니다.]");
		}
		if(t instanceof User) {
			if (t instanceof Admin){
				System.out.println("[(!)해당 회원은 관리자 권한을 갖고 있습니다.]\n[관리자 권한 해지 후 회원 삭제가 가능합니다.]");
				return;
			} else {
				if(nextConfirm("[이 계정을 정말 삭제하시겠습니까? (Y,YES)] > ")) {
					us.users.remove(t);
					System.out.printf("[id : \"%s\"가 삭제되었습니다.]", input);
				} else {
					System.out.println("[취소되었습니다.]");
					return;
				}
			}
		} else {
			System.out.println("[(!)존재하지 않는 계정입니다.]");
			return;
		}
	}

	//----------------- 메뉴관리
	public void menu() {
		System.out.println("=======[메뉴 관리]=======");
		int input = nextInt("[1.메뉴등록] [2.메뉴조회] [3.메뉴수정] [4.메뉴삭제] | [0.뒤로가기]");
		switch (input) {
			case 1: //메뉴등록
				MenuService.getInstance().register();
				break;
			case 2: //메뉴조회
				MenuService.getInstance().read();
				break;
			case 3: //메뉴수정
				MenuService.getInstance().modify();
				break;
			case 4: //메뉴삭제
				MenuService.getInstance().remove();
				break;
			case 0 : return;
		}
	}

	//----------------- 매출조회
	public void salesRecord() {
		System.out.println("=======[매출조회]=======");
		int input = nextInt("[1.당일 매출] [2.당월매출] [3.특정일 매출] [4.특정월 매출] | [0.뒤로가기]");
		switch (input) {
			case 1 : //오늘 매출
				System.out.println("당일매출");
				List<Order> od = os.findOrderBy(DATE_FORMAT_DATE, null);
				System.out.println(od);
				break;
			case 2 : //이번달 매출
				System.out.println("당월매출");
				List<Order> om = os.findOrderBy(DATE_FORMAT_MONTH, null);
				System.out.println(om);
				break;
			case 3 : //일매출
				String d = nextLine("[일 매출 확인할 날짜를 입력](yyyy-mm-dd) > ");
				List<Order> old = os.findOrderBy(DATE_FORMAT_DATE, d);
				System.out.println(old);
				break;
			case 4 : //특정월
				String m = nextLine("[월 매출 확인할 날짜를 입력](yyyy-mm) > ");
				List<Order> olm = os.findOrderBy(DATE_FORMAT_MONTH, m);
				System.out.println(olm);
				break;
			case 0 : return;
		}
	}


} //============================ AdminService 닫기