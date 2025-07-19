package com.climbjava.miniproject_qq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.climbjava.miniproject_qq.domain.Admin;
import com.climbjava.miniproject_qq.domain.Customer;
import com.climbjava.miniproject_qq.domain.User;

import static com.climbjava.miniproject_qq.utils.QqUtils.*;


public class UserService {
	// 싱글톤
	private static final UserService USER_SERVICE = new UserService();
	private UserService() {	}
	public static UserService getInstance() {
		return USER_SERVICE;
	}
	
	
	// 유저 리스트 생성
	List<User> users = new ArrayList<>();

	// 유저 리스트 초기화 블럭
	{
		users.add(new Admin(1, "고양이관리자", "admin", "1234"));
		users.add(new Customer(2, "새똥이", "guest1", "1234"));
		users.add(new Customer(3, "개똥이", "guest2", "1234"));
		users.add(new Admin(4, "멍멍이관리자", "admin2", "1234"));
	}
	
	
//============================= 메소드 ======================
	// loginUser -- 로그인 상태 저장
	private User loginUser;
	public User getLoginUser() {
		return loginUser;
	}
	
	// getUsers -- 유저 리스트 서치 
	public <T extends User> List<T> getUsers(Class<T> clazz) {
		return users.stream().
		filter(clazz::isInstance).
		map(clazz::cast).
		collect(Collectors.toList());
	}
	
	// findBy -- (입력 : id, 클래스 | 출력 : 해당하는 클래스 users 스트림)
	public <T extends User> T findBy(String id, Class<T> clazz) {
		return users.stream().filter(u -> clazz.isInstance(u) && u.getId().equals(id)).map(clazz::cast).findFirst().orElse(null);
	}
	
	// findByID 
	public User findByID(String id) {
		for(User u : users) {
			if(u.getId().equals(id)) {
				return u;
			}
		}
		return null;
	}
	// findByNo 
	public User findByNo(int no) {
		for(User u : users) {
			if(u.getUserNo() == no) {
				return u;
			}
		}
		return null;
	}
	

	// inputName -- 입력제한_이름
	public String inputName() {
		String name =  nextLine("[이름을 입력해주세요] > ");		
		if(!name.matches("[가-힣]{1,4}")) {
			throw new IllegalArgumentException("[(!)이름은 한글 1~4글자로 입력하세요]");
		}
		return name;
	}
	
	// inputId -- 입력제한_ID
	public String inputId(String id) {
		id = nextLine("[ID를 입력해주세요] > ");	
		if(!id.matches("[A-Za-z0-9_+&*-]")) {			
			throw new IllegalArgumentException("[(!)ID는 알파벳, 숫자 조합으로 입력하세요]");
		}
		return id;
	}
	
	// duplId -- 중복체크_ID
	public String duplId(String id) {
		User u = findBy(id, User.class);
		if(u != null) {
			throw new IllegalArgumentException("[(!)이미 존재하는 ID 입니다]");
		}
		return id;
	}
	
	// print -- 출력용 회원 리스트
	public void printUser() {
		List<User> users = getInstance().getUsers(User.class);
		users.forEach(System.out::println);
	}
	public void printAdmin() {
		List<Admin> admins = getInstance().getUsers(Admin.class);
		admins.forEach(System.out::println);
	}
	public void printCustomer() {
		List<Customer> customers = getInstance().getUsers(Customer.class);
		customers.forEach(System.out::println);
	}
	
//====================================================
	//----------------- 회원가입
	public void register() {
		System.out.println("=======[회원가입 정보 입력]=======");
		//----이름
		String name = inputName();
		
		//----ID
		String id = "id";
		inputId(id);
		duplId(id);

		//----PW
		String pw = nextLine("[비밀번호를 입력해주세요] > ");
		
		//----회원번호(자동증가)
		int no = users.isEmpty() ? 1 : users.get(users.size()-1).getUserNo()+1;
		//----회원리스트에 저장 
		//최초 회원가입시 Customer
		User users = new Customer(no, name , id, pw);
		this.users.add(users);
		
		System.out.println("[회원가입 완료. 로그인해주세요.]");
		
		}
	
	//----------------- 회원 정보 수정 (수정가능요소 : ID, PW, name)
	public void modify(User user) {
		System.out.println("=======[내 회원정보 수정]=======");
		String id = nextLine("[수정] ID  입력 > ");
//			users = findBy(id, Class<T>);
		
		if(id == null) {
			System.out.printf("[아이디 :\"%s\"가 \"%s\"로 수정됩니다.]", getLoginUser().getId(), id);
			loginUser.setId(id);
		} else {
			System.out.println("[(!)이미 존재하는 ID 입니다.]");
			return;
		}
		
		String name = nextLine("[수정]이름 입력 > ");
		String pw = nextLine("[수정] PW  입력 > ");
		System.out.printf("[PW가 \"%s\"로 변경됩니다.]", pw);
	
		loginUser.setName(name);
		loginUser.setPw(pw); 
		//이걸 loginUser에 저장하는게 아니라 users에 저장해야할것같은디
		//회원목록을 파일로 익스포트, 파일에 loginUser 정보도 저장해야함.

		System.out.println("[회원 정보가 수정되었습니다.]");
	}
	
	//----------------- 로그인
	public void login() { 
		System.out.println("=======[로그인 정보 입력]=======");
		
		String id = nextLine("[아이디를 입력해주세요] > ");
		String pw = nextLine("[비밀번호를 입력해주세요] > ");
		
		boolean flag = false; 
		for(User c : users) {
			if(c.getId().equals(id) && c.getPw().equals(pw)) {
				flag = true;
				loginUser = c;
				System.out.println("[" + loginUser.getName() + "님 환영합니다!]");
				return;
			}
		}
		if(!flag) {
			System.out.println("[(!) 아이디 또는 비밀번호가 틀렸습니다]");
		}
	}
	
	//-----------------로그아웃
	public void logout() {
	    loginUser = null;
	    System.out.println("=======[로그아웃 되었습니다]=======");
	}
	
	//-----------------탈퇴/계정삭제
	public void remove() {
		System.out.println("=======[탈퇴 서비스]=======");
		if(!nextConfirm("[정말 탈퇴하시겠습니까?]")) {
			return;
		}
		users.remove(loginUser);
		logout();
	}
	
// =============================== 테스트용 메인
	public static void main(String[] args) throws Exception {
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
				} else if(UserService.getInstance().getLoginUser().getClass() == Admin.class){
					System.out.println("===============관리자 로그인 상태");
					int input = nextInt("[1.회원목록 조회] [2.관리자 등급 관리] [3.회원삭제] [4.메뉴관리] [5.매출조회] [0.로그아웃]");	
					switch (input) {
						case 1 : 
							AdminService.getInstance().read();
							break;
						case 2 : 
							AdminService.getInstance().isSeller();
							break;
						case 3 : 
							AdminService.getInstance().userRemove();
							break;
						case 4 : 
							MenuService.getInstance().register();
							break;
						case 5 : 
							System.out.println("* 임시 * 매출관리"); 
							OrderService.getInstance().findBySalesDate();
							break;
						case 0 :
							UserService.getInstance().logout();
							break;
					}
				} else {
					System.out.println("===============손님 로그인 상태"); 
					int input = nextInt("[0.로그아웃]");	
					switch (input) {
						case 0 : 
							UserService.getInstance().logout();
							break;
					}
				}
			}	catch (NumberFormatException e) {
				System.out.println("정확한 숫자를 입력하세요");
			}	catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		} //while(true) 닫기
	}//테스트용 main 닫기

} //com.climbjava.miniproject_qq.service.UserService 닫기