package com.climbjava.miniproject_qq.ui;

import com.climbjava.miniproject_qq.domain.Customer;
import com.climbjava.miniproject_qq.service.CustomerService;
import com.climbjava.miniproject_qq.service.MenuService;
import com.climbjava.miniproject_qq.service.OrderService;
import com.climbjava.miniproject_qq.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.climbjava.miniproject_qq.utils.QqUtils.nextInt;

@Component
@AllArgsConstructor
public class CustomerUI {
  private CustomerService cs;
  private UserService us;
  private OrderService os;
  private MenuService ms;

  public void init() {
    System.out.println("===============손님 로그인 상태");
    Customer customer = us.findBy(us.getLoginUser().getId(), Customer.class);
    int input = nextInt("[1.메뉴보기 2. 장바구니 담기 3. 장바구니 빼기 4. 결제하기 5.내정보 6. 로그아웃]");
    // 메뉴보기 - 메뉴클래스 , 장바구니 담기, 장바구니 빼기, 결제하기 - order 클래스
    switch (input) {
      case 1:
        ms.read();
        while (true) {
          int menuInput = nextInt("[1.가격 범위로 메뉴 검색] [2.선택한메뉴 가격 합계] [0.뒤로가기]");
          switch (menuInput) {
            case 1:
              ms.PriceRange();
              break;
            case 2:
              ms.totalMenuPrice();
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
        cs.modify();
        break;
      case 3:
        us.remove();
        break;
      case 4:
        return;
    }
  }
}
