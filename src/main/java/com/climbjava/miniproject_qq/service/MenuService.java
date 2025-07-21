package com.climbjava.miniproject_qq.service;

import com.climbjava.miniproject_qq.domain.Menu;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.*;
@Service
public class MenuService {
		

	// 싱글톤 (MENU_SERVICE 라는 이름의 객체를 클래스 안에서 딱 한 번 만들고, 나중에 getInstance()로 꺼내쓰기위해 사용함)
	private static final MenuService MENU_SERVICE = new MenuService();
	private Scanner sc = new Scanner(System.in);
	
	//메뉴리스트생성
//	private List<Menu> sortedMenus;
	
	// MenuService생성자
	private MenuService() {	}
	public static MenuService getInstance() {
		return MENU_SERVICE;//이 메서드를 통해 다른 클래스에서도 MenuService를 사용할 수있다
		 
	}
	//메뉴를 저장할 ArrayList
//메뉴는 Menu 객체로 구성돼 있음(메뉴 번호, 이름, 카테고리, 가격)
	private List<Menu> menus = new ArrayList<Menu>();
	{
		menus.add(new Menu(1, "차돌볶음", 0, 20_000));
		menus.add(new Menu(2, "두부조림", 0, 12_000));
		menus.add(new Menu(3, "술찜", 0, 18_000));
		menus.add(new Menu(4, "도가니수육", 0, 50_000));
		menus.add(new Menu(5, "탕수육", 0, 15_000));
		menus.add(new Menu(6, "감자전", 0, 10_000));
		menus.add(new Menu(7, "김치전", 0, 10_000));
		menus.add(new Menu(8, "타코와사비", 1, 8_000));
		menus.add(new Menu(9, "파인샤베트", 1, 8_000));
		menus.add(new Menu(10, "타코야끼", 1, 8_000));
		menus.add(new Menu(11, "소주", 2, 5_000));
		menus.add(new Menu(12, "맥주", 2, 6_000));
		menus.add(new Menu(13, "청하", 2, 7_000));
		menus.add(new Menu(14, "하이볼", 2, 8_000));
		menus.add(new Menu(15, "고량주", 2, 10_000));
	}

	
	// CRUD
	
//	void rank1() {	
//		sortedMenus = new ArrayList<Menu>(menus);
//	}
	
	// 메뉴 등록
//	메뉴 번호, 이름, 카테고리, 가격을 사용자 입력으로 받아서
//	중복 번호 확인 후, 새로 만든 Menu 객체를 menus 리스트에 추가
		public void register() {
			System.out.println(" 메뉴 등록");

			System.out.print("메뉴 번호 > ");
		
			int no = Integer.parseInt(sc.nextLine());
           
			Menu m = findBy(no);
			if (m != null) {
				System.out.println("중복된 메뉴 번호가 존재합니다.");
				return;
			}
			System.out.print("메뉴 이름 > ");
			String name = sc.nextLine();

			System.out.print("카테고리 (0:메인, 1:사이드, 2:주류) > ");
			int category = Integer.parseInt(sc.nextLine());
			
			System.out.print("가격 >");
			int price = Integer.parseInt(sc.nextLine());
			
			Menu m1 = new Menu(no, name, category, price);
			menus.add(m1);
			System.out.println("메뉴가 등록되었습니다.");
		}
	// 메뉴수정
//사용자에게 수정할 메뉴 번호를 입력받고, 해당 메뉴가 있으면
//이름, 카테고리, 가격을 다시 입력받아 값을 변경
		public void modify() {
			System.out.println("메뉴 수정");

			System.out.print("수정할 메뉴 번호 > ");
			int no = Integer.parseInt(sc.nextLine());

			Menu m = findBy(no);
			if (m == null) {
				System.out.println("해당 번호의 메뉴가 존재하지 않습니다.");
				return;
			}

			System.out.print("새 이름 > ");
			m.setName(sc.nextLine());

			System.out.print("새 카테고리 (0:메인, 1:사이드, 2:주류) > ");
			m.setCategory(Integer.parseInt(sc.nextLine()));

			System.out.print("새 가격 > ");
			m.setPrice(Integer.parseInt(sc.nextLine()));
			
			System.out.println("메뉴가 수정되었습니다.");
		}


// 메뉴삭제
		public void remove() {
		    System.out.println("메뉴 삭제");
		    print(menus); 

		    System.out.print("삭제할 메뉴 번호 > ");
		    int no = Integer.parseInt(sc.nextLine());

		    Menu m = findBy(no);
		    if (m == null) {
		        System.out.println("해당 번호의 메뉴가 존재하지 않습니다.");
		        return;
		    }

		   
		    System.out.printf("'%s' 메뉴를 정말 삭제하시겠습니까? (Y/N) > ", m.getName());
		    String confirm = sc.nextLine().trim();

		    if (confirm.equalsIgnoreCase("Y")) {
		        menus.remove(m);
		        System.out.println("메뉴가 삭제되었습니다.");
		    } else {
		        System.out.println("삭제가 취소되었습니다.");
		    }
		}

//	// 메뉴조회
	public void read() {
		System.out.println("메뉴조회");
		       	print(menus);
		
		
	}
//메뉴 번호를 입력받아, menus 리스트 안에서 번호가 일치하는 메뉴를 찾아 반환 없으면 null 반환
	public Menu findBy(int no) {
		for(Menu m : menus) {
			if(no == m.getNo()) {
				return m;
				
			}
		}
		return null;
	}
	
	
	public void readOrder() {
	System.out.println("메뉴순서조회기능");
	menus.sort(Comparator.comparing(Menu::getNo));
	print(menus);
	}
	
//메뉴 리스트를 화면에 하나씩 출력 System.out.println(s)는 Menu 클래스의 toString()을 자동으로 사용
	public void print(List<Menu> menus) {
		menus.forEach(s -> System.out.println(s));
	}
	
	public void rank() {
		Collections.sort(menus, (o1, o2) -> o2.getCategory() - o1.getCategory());
		print(menus);
	}

	
	
	
	
	// 가격 범위로 메뉴 검색할수있는기능
	public void PriceRange() {
	    System.out.print("최소 가격 > ");
	    int min = Integer.parseInt(sc.nextLine());
	    //sc.nextLine()사용자가 입력한 값을 문자열로 받아와서Integer.parseInt 그 문자열을 숫자로 바꿈

	    System.out.print("최대 가격 > ");
	    int max = Integer.parseInt(sc.nextLine());

	    for (Menu m : menus) {
	        if (m.getPrice() >= min && m.getPrice() <= max) {
	            System.out.println(m);
	        }
	    }
	}

	
	
	
	
	//선택한메뉴 가격 합계할수있는것
	public void totalMenuPrice() {
	    System.out.println("선택된 메뉴의 가격 합산");
	    System.out.println("메뉴 번호를 쉼표로 구분해서 입력하세요");
	    String input = sc.nextLine();//사용자로부터 입력을받고 input 변수에는 문자열이 저장
	    
	    String[] parts = input.split(",");//입력된 문자열을 쉼표 , 기준으로 잘라서 배열로 만듬 1,3,5 > [1,3,5]
	    int totalPrice = 0;//선택된 메뉴들의 가격을 더해서 저장할 변수 처음엔 0으로 시작

	    for (String part : parts) {  //위에서 나눈 메뉴 번호들 1,3,5을 하나씩 꺼내서 반복 part는 각 반복에서 1 > 3 > 5식으로 순서대로 바뀜
	        try {
	            int no = Integer.parseInt(part.trim());//trim()은 1처럼 공백이 있을 경우 없애줌 > 1
	                                                   //Integer.parseInt()로 1을 숫자 1로 바꿈
                                              //만약 숫자로 바꿀 수 없는 글자면 예외가 발생 아래에서 catch 처리
	            
	            Menu m = findBy(no);  //findBy(int no) 메서드를 사용해서 입력한 메뉴 번호와 같은 번호를 가진 Menu 객체를 찾아옴
                                  //no가 3이면 메뉴 리스트 중 번호가 3인 Menu 객체를 찾아오는데 없다면 null이됨
	            if (m != null) {
	                totalPrice += m.getPrice();  //메뉴가 존재하면 getPrice로 해당 메뉴의 가격을 가져와서 totalPrice에 누적합산
	            } else {
	                System.out.println("메뉴 번호 " + no + "는 존재하지 않습니다.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("잘못된 입력: " + part);
	        }
	    }

	    System.out.println("선택한 메뉴들의 총합: " + totalPrice + "원");
	}

	
	// 메서드기능 테스트
    public static void main(String[] args) {
        MenuService menuService = MenuService.getInstance();
        
        Scanner scanner = new Scanner(System.in); 
        while (true) {
            System.out.println("===== 메뉴 서비스 =====");
            System.out.println("1. 메뉴 등록");
            System.out.println("2. 메뉴 조회");
            System.out.println("3. 메뉴 수정");
            System.out.println("4. 메뉴 삭제");
            System.out.println("5. 메뉴 가격합산");
            System.out.println("6. 메뉴 가격범위로 검색");
            System.out.println("7. 종료");
            System.out.print("선택 > ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1:
                    menuService.register(); // 메뉴 등록
                    break;
                case 2:
                    menuService.read(); // 메뉴 조회
                    break;
                case 3:
                	menuService.modify();//메뉴수정
                case 4:
                	menuService.remove(); //메뉴삭제
                case 5:
                	menuService.totalMenuPrice();
                case 6:
                	menuService.PriceRange();
                case 7:
                    System.out.println("프로그램 종료.");
                    return; 
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}

   


