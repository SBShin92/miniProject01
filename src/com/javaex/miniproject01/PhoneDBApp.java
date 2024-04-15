package com.javaex.miniproject01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhoneDBApp {
	private static String rootPath = System.getProperty("user.dir") + "\\PhoneDB\\";
	private static String fileName = rootPath + "PhoneDB.txt";
	public static void main(String[] args) {
		File file = new File(fileName);
		List<Person> lst = null;
		
		displayTitle();
		try {
			checkFile(file);
			lst = readFile(file);
			
			executeMenu(lst, file);
		} catch (FileNotFoundException e) {
			System.err.println("파일을 찾을 수 없습니다.");
		} catch (Exception e) {
			System.err.println("에러");
		}	
	}		
	
	private static void executeMenu(List<Person> lst, File file) {
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		
		while (flag) {
			System.out.println();
			displayMenu();
			System.out.print(">메뉴번호: ");
			String inputLine = sc.nextLine().trim();
			switch (inputLine) {
			case "1": {
				displayList(lst);
				break ;
			}
			case "2": {
				addList(lst, sc);
				flushFile(lst, file);
				break ;
			}
			case "3": {
				deleteList(lst, sc);
				flushFile(lst, file);
				break ;
			}
			case "4": {
				searchList(lst, sc);
				break ;
			}
			case "5": {
				flag = false;
				System.out.println("[종료]");
				break ;
			}
			default:
				System.out.println("정확한 값을 입력하세요.");
				break ;
			}
		}
		sc.close();
	}
	
	private static void flushFile(List<Person> lst, File file) {
		try (
				Writer fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
			) {
			for (Person node: lst) {
				bw.append(node.getName());
				bw.append(',');
				bw.append(node.getMp());
				bw.append(',');
				bw.append(node.getHp());
				bw.newLine();
			}
			
		} catch (IOException e) {
			System.err.println("파일에 접근할 수 없습니다.");
		}
	}
	
	
	private static void displayList(List<Person> lst) {
		Iterator<Person> iter = lst.iterator();
		
		for (int i = 1; iter.hasNext(); i++) {
			System.out.println(i + ". " + iter.next());
		}
	}

	private static void addList(List<Person> lst, Scanner sc) {
		
		Person tmpInfo = new Person();
		
		
		System.out.print("이름 : ");
		tmpInfo.setName(sc.nextLine().trim());
		System.out.print("휴대폰번호 : ");
		tmpInfo.setMp(sc.nextLine().trim());
		System.out.print("집전화번호 : ");
		tmpInfo.setHp(sc.nextLine().trim());
	
		lst.add(tmpInfo);	
	}

	private static void deleteList(List<Person> lst, Scanner sc) {
		int idx;
		
		System.out.print("지울 번호? : ");
		
		try {
			idx = Integer.parseInt(sc.nextLine().trim()) - 1;
			lst.remove(idx);
		} catch (NumberFormatException e) {
			System.err.println("숫자를 입력하세요.");
		} catch (IndexOutOfBoundsException e) {
			System.err.println("리스트에 없는 숫자입니다.");
		}
	}

	private static void searchList(List<Person> lst, Scanner sc) {
		System.out.print("찾을 이름? : ");
		String searchName = sc.nextLine().trim();
		
		for (Person node: lst) {
			if (node.getName().contains(searchName))
				System.out.println(node);
		}
	}
	
	private static List<Person> readFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		String tmp;
		String[] splitTmp;
		List<Person> lst = new LinkedList<>();
		
		while (sc.hasNext()) {
			Person parsedString = new Person();
			tmp = sc.nextLine().trim();
			splitTmp = regex(tmp);
			if (splitTmp == null)
				continue;
			parsedString.setName(splitTmp[0].trim());
			parsedString.setMp(splitTmp[1].trim());
			parsedString.setHp(splitTmp[2].trim());
			lst.add(parsedString);
		}
		sc.close();
		
		return lst;
	}

	private static void checkFile(File file) {
		try {
			if (!file.exists()) {
				System.out.printf("새로운 파일을 생성합니다.(%s)%n", fileName);
				file.createNewFile();
			}
		} catch (IOException e) {
			System.err.println("파일을 생성할 수 없습니다.");
		}
	}

	private static void displayTitle() {
		System.out.println("**********************************");
		System.out.println("*        전화번호 관리 프로그램        *");
		System.out.println("**********************************");
	}
	
	private static void displayMenu() {
		System.out.println("1.리스트  2.등록  3.삭제  4.검색  5.종료");
		System.out.println("----------------------------------");
	}
	
	private static String[] regex(String inputString) {
		Pattern pattern = Pattern.compile("\\s*([가-힣]+)\\s*,\\s*([0-9]+-[0-9]+-[0-9]+)\\s*,\\s*([0-9]+-[0-9]+-[0-9]+)\\s*");
		Matcher matcher = pattern.matcher(inputString);
		if (matcher.matches()) {
			return new String[] {matcher.group(1),
					matcher.group(2),
					matcher.group(3)};
		}
		return null;
	}
}




