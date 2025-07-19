package com.climbjava.miniproject_qq.service;

import com.climbjava.miniproject_qq.domain.Admin;
import com.climbjava.miniproject_qq.domain.Customer;
import com.climbjava.miniproject_qq.domain.User;


import static com.climbjava.miniproject_qq.utils.QqUtils.*;

public class AdminService {
	// 싱글톤
	private static final AdminService ADMIN_SERVICE = new AdminService();
	public AdminService() {}
	public static AdminService getInstance() {
		return ADMIN_SERVICE;
	}
	
	public void adminInit() throws Exception {

		System.out.println("===============관리자 로그인 상태");
		int input = nextInt("[1.회원목록 조회] [2.관리자 등급 관리] [3.회원삭제] [4.메뉴관리] [5.매출조회] [0.로그아웃]");	
		switch (input) {
			case 1 : 
				read();
				break;
			case 2 : 
				isSeller();
				break;
			case 3 : 
				userRemove();
				break;
			case 4 : 
				MenuService.getInstance().register();
				break;
			case 5 : 
				System.out.println("* 임시 * 매출조회"); 
				OrderService.getInstance().findBySalesDate();
				break;
			case 0 :
				UserService.getInstance().logout();
				break;
		}
	}

	//-----------------회원 목록 조회
	public void read() {
		System.out.println("=======[회원 목록 조회]=======");
		int input = nextInt("[1.전체회원 보기] [2.관리자회원 보기] [3.일반회원 보기]");
		switch (input) {
		case 1 : 
			UserService.getInstance().printUser();
			break;
		case 2 : 
			UserService.getInstance().printAdmin();
			break;
		case 3 : 
			UserService.getInstance().printCustomer();
			return;
		} 
	}
	
	
	//-----------------관리자 권한 관리
	//User customer에 있던 정보를 User Admin으로 이동시키기
	public void isSeller() {
		System.out.println("=======[관리자 권한 관리]=======");
		
		String input = nextLine("[관리자 권한을 부여할 ID 또는 회원번호를 입력하세요] > ");
//		if (input == ) { //ID의 시작이 숫자일 수 없으니까 숫자로 시작하면 무조건 회원번호로 취급
//			User t = com.climbjava.miniproject_qq.service.UserService.getInstance().findBy(id, User.class);
//		}
		User t = UserService.getInstance().findBy(input, User.class);
		
	    if (t instanceof Admin) {
	        System.out.print("[(!)이미 관리자 등급인 계정입니다.]\n");
	        if(nextConfirm("[이 계정의 관리자 권한을 취소하시겠습니까?] > ")) {
		        UserService.getInstance().users.add(new Customer(t.getUserNo(), t.getName(), t.getId(), t.getPw() ));
		        UserService.getInstance().users.remove(t);
		        System.out.printf("[id : \"%s\"의 관리자 권한이 취소되었습니다.]", input);
	        }
	        return;
	    }
	    if (t instanceof Customer) {
	        UserService.getInstance().users.add(new Admin(t.getUserNo(), t.getName(), t.getId(), t.getPw() ));
	        UserService.getInstance().users.remove(t);
	        System.out.printf("[id : \"%s\"에게 관리자 권한이 부여되었습니다.]", input);
	    }
	    if (t == null) {
	        System.out.println("[(!)존재하지 않는 계정입니다.]");
	        return;
	    }
	}
	
	//-----------------회원 삭제 //관리자 권한이 있을경우 삭제 불가능
	public void userRemove() {
	//계정 존재유무 확인 >> 관리자 권한 소지유무 확인 >> 정말 삭제할건지 확인
		
		System.out.println("임시 * 회원삭제");
		String id = nextLine("[삭제할 회원의 ID를 입력하세요] > ");
		User t = UserService.getInstance().findBy(id, User.class);
		
		if(UserService.getInstance().getUsers(User.class).contains(t)) {
			if (UserService.getInstance().getUsers(Admin.class).contains(t)){
				System.out.println("[(!)해당 회원은 관리자 권한을 갖고 있습니다.]\n[관리자 권한 해지 후 회원 삭제가 가능합니다.]");
				return;
			} else {
				if(nextConfirm("[이 계정을 정말 삭제하시겠습니까?] > ")) {
				UserService.getInstance().users.remove(t);
				System.out.printf("[id : \"%s\"가 삭제되었습니다.]");
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
	
} //============================ com.climbjava.miniproject_qq.service.AdminService 닫기