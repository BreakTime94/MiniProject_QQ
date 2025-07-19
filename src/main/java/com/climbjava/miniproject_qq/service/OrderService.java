package com.climbjava.miniproject_qq.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.climbjava.miniproject_qq.domain.*;
import com.climbjava.miniproject_qq.utils.QqUtils;

public class OrderService {
	
	private static final OrderService ORDER_SERVICE = new OrderService();
	private UserService cu = UserService.getInstance(); // 이후에 getloginCustmoer 메서드를 통해 호출하여야 한다.
	private MenuService mu = MenuService.getInstance();
	User loginCustomer = cu.getLoginUser();
	private OrderService() {	}
	public static OrderService getInstance() {
		return ORDER_SERVICE;
	}
	private List<Order> orders = new ArrayList<Order>(); // 주문 내역 집합
	private List<Cart> carts = new ArrayList<>(); // 장바구니
	private List<Menu> menus = new ArrayList<>(); //메뉴판 목록
	
	int num ;
	{	
		List<Cart> l = new ArrayList<>();
		l.add(new Cart(mu.findBy(1), 2));
		Calendar cal = Calendar.getInstance();
		cal.set(2025, 4, 1, 18, 30);
		Date d = cal.getTime();
		System.out.println(d);
		orders.add(new Order(++num, (Customer)cu.findByID("guest1"), l, mu.findBy(1).getPrice() * 2, d));
		l.clear();
		l.add(new Cart(mu.findBy(4), 1));
		l.add(new Cart(mu.findBy(11), 2));
		cal.set(2025, 4, 2, 20, 20);
		d = cal.getTime();
		System.out.println(d);
		orders.add(new Order(++num, (Customer)cu.findByID("guest2"), l, (mu.findBy(4).getPrice() * 1 + mu.findBy(11).getPrice() *  2) , d));
	}
	// CRUD
	
	//유효값 범위 체크
	public int checkRangeMenu(int no) {
		if(no > 0 || no <= 15 ) {
			return no;	
		}
		throw new IllegalArgumentException("메뉴판에 존재하는 메뉴번호를 입력하여 주십시오.");
	}
	
	public int checkRangeAmount(int no) {
		if(no > 0 || no <= 10 ) {
			return no;	
		}
		throw new IllegalArgumentException("주문하실 수량은 1 ~ 10까지 숫자로 입력하여 주십시오.");
	}
	
	// 주문하기 (장바구니 담기)
	public void getItem() {
		while(true) {
			MenuService.getInstance().read();
			// 상품 번호를 입력받고
			int no = QqUtils.nextInt("주문하실 메뉴 번호를 입력하세요 > ");
			checkRangeMenu(no);
			Menu m = MenuService.getInstance().findBy(no); // 숫자 번호는 1번부터
			if(m == null) {
				System.out.println("올바른 메뉴를 입력하여주세요.");
				break;
			}
			// 수량 입력
			int amount = QqUtils.nextInt("담을 수량을 입력하세요 > ");
			checkRangeAmount(amount);
			Cart cart = new Cart(m, amount);
			carts.add(cart);
			System.out.println(carts);
			if(QqUtils.nextConfirm("추가로 담으시려면 y를 눌러주시고 아니면 아무키나 눌러주세요.> ")) {
				continue;
			} 
			System.out.println("주문화면으로 돌아갑니다.");
			break;	
		}
	}
	
	public void read() {//장바구니 조회 기능
		System.out.println(carts);
	}
	
	public void deleteItem() { //장바구니에 담은 상품을 결제 전에 뺄 수 있는 기능
		while(true) {
			if(carts.isEmpty()) {
				System.out.println("장바구니가 비었습니다.");
				break;
			}
			System.out.println(carts);
			int menu = QqUtils.nextInt("결제를 취소하실 메뉴 번호를 선택하여 주십시오. > ");
			checkRangeMenu(menu);
			Cart car = new Cart();
			for(Cart c : carts) {
				if(c.getNo() == menu) {
					car = c;
					break;
				}
			 System.out.println("담으신 메뉴중에서 선택하여 주십시오.");
			 return;
			}	
			
			int amount = QqUtils.nextInt("취소하실 수량을 선택하여 주십시오. > ");
			boolean res = false;
			if(car.getAmount() >= amount) {	
				res = true;
				car.setAmount(car.getAmount() - amount);
				if(car.getAmount() == 0) {
					carts.remove(car);
				}
				if(QqUtils.nextConfirm("상품을 계속 빼시려면 y를 눌러주시고 아니면 아무키나 눌러주세요. > ")) {
					continue;
				} 
				System.out.println("주문화면으로 돌아갑니다.");
				break;
			}
			if(!res) {
				System.out.println("올바른 수량을 입력하여주세요.");
				break;
			}
		}
	}
	
	//결제하기 
	public void pay() {
		System.out.println("주문하신 메뉴는 " + carts + " 입니다.");
		int sales = 0;
		for(Cart c : carts) {
			sales += c.getPrice() * c.getAmount();
		}
		System.out.println("결제하실 금액은 " + sales + "원 입니다."); 
		if(sales != QqUtils.nextInt(sales + "원을 입력하여주세요. > ")) {
			System.out.println("올바른 값을 입력하여 주십시오.");
			System.out.println("주문화면으로 돌아갑니다.");
			return;
		}
		List<Cart> tmp = new ArrayList<>();
		tmp.addAll(carts);
		
		Order order = new Order(++num, (Customer)loginCustomer, tmp, sales, new Date()); // kim 대신 로그인한 손님을 대입해야 함
		orders.add(order);
		order.setPay(true);
		System.out.println("결제가 완료되었습니다.");
		System.out.println(orders);  
		carts.clear();
	}
	// 결제 취소
//	public void cancle() { // 취소 했을 때의 시간도 반영되어야 한다.
//		List<Order> tmp = findByPayment(loginCustomer);// loginCustomer를 집어넣어야 한다.
//		
//	}
	
	// 결제 조회, 관리자/손님 페이지에서 조회 관리자 -> 매출 조회, 손님 -> 누적 소비금액 및 쿠폰 관련
	public List<Order> findByPayment(Customer c) { //loginCustomer를 집어 넣는다 개인의 주문금액 조회, 쿠폰도 여기서 호출?/ 관리자페이지에서 손님 리스트에 손님객체 대입
		//손님 한 명당 가지고 있는 주문 수는 여러개 일 수 있으므로 리스트 타입으로 반환
		List<Order> tmp = new ArrayList<Order>();
		for (Order o : orders) {
			if(c == o.getCustomer()) {
				tmp.add(o);
				System.out.println(tmp);
				return tmp;
			}
		}
		System.out.println("주문 내역이 없습니다.");
		return null;
	}
	
	public void findBySalesDate () throws ParseException { // 일별 매출 조회 날짜/메뉴/수량/금액
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date d = date.parse(QqUtils.nextLine("조회하실 날짜를 yyyy-MM-dd 순으로 입력하여 주십시오. >"));
		String f = date.format(d);
		List<Order> sales = new ArrayList<Order>();
			for(Order o : orders) {
				if(o.getDate().getMonth() == d.getMonth() && o.getDate().getDate() == d.getDate()) {					
					sales.add(o);
				}
				else {					
					System.out.println("선택한 일자의 매출 내역이 없습니다.");
					return;
				}
			}
			int sum = 0;
			for(Order o : sales) {
				sum += o.getSales();
			}
			System.out.println(sales);
			System.out.println(f + "일 매출 총액 : " + sum);
	}
	
	public void findBySalesMonth () throws ParseException { // 월별 매출 조회 날짜/메뉴/수량/금액
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM");
		Date d = date.parse(QqUtils.nextLine("조회하실 월을 yyyy-MM 순으로 입력하여 주십시오. >"));
		String f = date.format(d);
		List<Order> sales = new ArrayList<Order>();
			for(Order o : orders) {
				if(o.getDate().getMonth() == d.getMonth()) {					
					sales.add(o);
				}
				else {					
					System.out.println("선택한 월의 매출 내역이 없습니다.");
					return;
				}
			}
			int sum = 0;
			for(Order o : sales) {
				sum += o.getSales();
			}
			System.out.println(sales);
			System.out.println(d + "월 매출 총액 : " + sum);
	}

	
	
	public static void main(String[] args) throws ParseException {// 구동 연습 메서드
		System.out.println(new Date());
		OrderService order = ORDER_SERVICE.getInstance();
		Date d = new Date();
		while(true) {
			int no = QqUtils.nextInt(" 1. 장바구니담기,  2. 장바구니 빼기 3. 결제하기  4. 일별매출점검  5. 월별매출점검 6. 주문내역 점검(손님기준) 7. 종료");
			switch (no) {
			case 1 :  order.getItem();
			break;
			
			case 2 : order.deleteItem();
			break;
			
			case 3 : order.pay();;
			break;
				
			case 4 : order.findBySalesDate(); 
			break;
			
			case 5 : order.findBySalesMonth();
			break;
			
//			case 6 : order.findByPayment(kim);
//			break;
			
			default : System.out.println("프로그램을 종료합니다");
			return;
			}
		}
	}
}
