package com.climbjava.miniproject_qq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.climbjava.miniproject_qq.domain.Admin;
import com.climbjava.miniproject_qq.domain.Customer;
import com.climbjava.miniproject_qq.domain.User;
import org.springframework.stereotype.Service;

import static com.climbjava.miniproject_qq.utils.QqUtils.*;

@Service
public class UserService {
	// 유저 리스트 생성
	List<User> users = new ArrayList<>();

	{ // 유저 리스트 초기화 블럭
		users.add(new Admin(1, "고양이관리자", "admin", "1234"));
		users.add(new Customer(2, "새똥이", "guest1", "1234"));
		users.add(new Customer(3, "개똥이", "guest2", "1234"));
		users.add(new Admin(4, "멍멍이관리자", "admin2", "1234"));
		users.add(new Admin(5, "테스트용 관리자", "1", "1"));
		users.add(new Customer(6, "테스트용 고객", "a", "a"));
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

	// findBy (아이디, 클래스(Admin || Customer))
	public <T extends User> T findBy(String id, Class<T> clazz) {
		return users.stream().filter(u -> clazz.isInstance(u) && u.getId().equals(id)).map(clazz::cast).findFirst().orElse(null);
	}

	// findByID (아이디)
	public User findByID(String id) {
		for(User u : users) {
			if(u.getId().equals(id)) {
				return u;
			}
		}
		return null;
	}
	// findByNo (회원번호)
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
		if(!name.matches("[가-힣a-zA-z]{1,10}")) {
			throw new IllegalArgumentException("[(!)이름은 한글 또는 영어 1~10글자로 입력하세요]");
		}
		return name;
	}

	// inputId -- 입력제한_ID
	public String inputId(String id) {
		id = nextLine("[ID를 입력해주세요] > ");
		if(!id.matches("^[A-Za-z][A-Za-z0-9_+&*-]*")) {
			throw new IllegalArgumentException("[(!)ID의 첫글자는 알파벳으로 시작해야 합니다.]\n[(!)영어와 숫자 조합으로 입력하세요]");
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
		List<User> users = getUsers(User.class);
		users.forEach(System.out::println);
	}
	public void printAdmin() {
		List<Admin> admins = getUsers(Admin.class);
		admins.forEach(System.out::println);
	}
	public void printCustomer() {
		List<Customer> customers = getUsers(Customer.class);
		customers.forEach(System.out::println);
	}

	//====================================================
	//----------------- 회원가입
	public void register() {
		System.out.println("=======[회원가입 정보 입력]=======");
		//----이름
		String name = inputName();

		//----ID
		String id = "0";
		id = inputId(id);
		id = duplId(id);

		//----PW
		String pw = nextLine("[비밀번호를 입력해주세요] > ");

		//----회원번호(자동증가)
		int no = users.isEmpty() ? 1 : users.get(users.size()-1).getUserNo()+1;

		//----회원리스트에 저장 (최초 회원가입시 Customer)
		User users = new Customer(no, name, id, pw);
		this.users.add(users);

		System.out.println("[회원가입 완료. 로그인해주세요.]");
	}

	//----------------- 회원 정보 수정 (수정가능요소 : ID, PW, name)
	public void modify() {
		System.out.println("=======[내 회원정보 수정]=======");
		System.out.println("[수정할 정보를 입력하세요]");
		String id = "0";
		id = inputId(id);
		User t = findBy(id, User.class);
		if(t == null) {
			System.out.printf("[기존 아이디 :\"%s\"가 \"%s\"로 수정됩니다.]\n", getLoginUser().getId(), id);
		} else {
			System.out.println("[(!)중복된 아이디입니다.]");
			return;
		}

		String name = inputName();

		String pw = nextLine("[ PW 를 입력해주세요] > ");
		System.out.printf("[PW가 \"%s\"로 변경됩니다.]", pw);

		loginUser.setId(id);
		loginUser.setName(name);
		loginUser.setPw(pw);
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
		if(nextConfirm("[정말 탈퇴하시겠습니까?](y/yes)")) {
			users.remove(loginUser);
			System.out.println("[정상적으로 탈퇴 되었습니다.]");
			logout();
			return;
		} else {
			System.out.println("[탈퇴 취소되었습니다.]");
			return;
		}
	}

} //UserService 닫기