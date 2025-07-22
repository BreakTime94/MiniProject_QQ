package com.climbjava.miniproject_qq.ui;

import com.climbjava.miniproject_qq.domain.Order;
import com.climbjava.miniproject_qq.service.AdminService;
import com.climbjava.miniproject_qq.service.MenuService;
import com.climbjava.miniproject_qq.service.OrderService;
import com.climbjava.miniproject_qq.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.climbjava.miniproject_qq.utils.QqUtils.*;
import static com.climbjava.miniproject_qq.utils.QqUtils.DATE_FORMAT_DATE;
import static com.climbjava.miniproject_qq.utils.QqUtils.DATE_FORMAT_MONTH;
import static com.climbjava.miniproject_qq.utils.QqUtils.nextLine;

@Component
@AllArgsConstructor
public class AdminUI {
  UserService us;
  AdminService as;
  OrderService os;
  MenuService ms;

  // =============================== 메인 호출용 -- 관리자 로그인 상태
  public void adminInit() throws Exception {
    System.out.println("===============관리자 로그인 상태");
    int input = nextInt("[1.회원목록 조회] [2.관리자 권한 관리] [3.회원삭제] [4.메뉴관리] [5.매출조회] | [9.내정보수정] [0.로그아웃]");
    switch (input) {
      case 1 : //회원목록 조회
        read();
        break;
      case 2 : //관리자 권한 관리
        as.isSeller();
        break;
      case 3 : //회원삭제
        as.userRemove();
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

  //----------------- 메뉴관리
  public void menu() {
    System.out.println("=======[메뉴 관리]=======");
    int input = nextInt("[1.메뉴등록] [2.메뉴조회] [3.메뉴수정] [4.메뉴삭제] | [0.뒤로가기]");
    switch (input) {
      case 1: //메뉴등록
        ms.register();
        break;
      case 2: //메뉴조회
        ms.read();
        break;
      case 3: //메뉴수정
        ms.modify();
        break;
      case 4: //메뉴삭제
        ms.remove();
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

}
