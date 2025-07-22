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

@AllArgsConstructor
@Service
public class CustomerService {
	private UserService us;
//    private Customer loginCustomer = (Customer)(UserService.getInstance().getLoginUser());

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