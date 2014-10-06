import java.util.ArrayList;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		System.out.println("Input your expression: ");
		Scanner scan = new Scanner(System.in);
		
		String expressionString = scan.nextLine();
		
		//Take string and convert it into an arraylist of strings
		ArrayList<String> exp = convertToArrayList(expressionString);
		System.out.println(exp);
	
		//while expression !solve
		while (exp.size() > 1) {
			//Look for what index and operation to solve
			int indexToPerformAt = chooseIndexToPerformOperation(exp);

			//operate
			exp = operate(exp, indexToPerformAt);
			System.out.println(exp);
		}
	}
	
	public static boolean isNumber(char c) {
		char[] listOfChars = {'+' , '-', '*', '/', '^', '(', ')'};
		for(char cha : listOfChars) {
			if(c == cha) {
				return false;
			}
		}
		return true;
	}
	
	public static ArrayList<String> convertToArrayList(String exp) {
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0 ; i < exp.length() ; i++) {
			if (exp.charAt(i) != ' ') {
				list.add("" + exp.charAt(i));
			}
		}
			
		int ctr = 0;
		while (ctr < list.size()-1) {
			if(ctr < list.size()-1) {
				if(isNumber(list.get(ctr).charAt(0)) && isNumber(list.get(ctr+1).charAt(0))) {
					//Combine the two
					list.set(ctr, list.get(ctr) + list.get(ctr+1));
					list.remove(ctr+1);
					ctr--;
				}
				ctr++;
			}
		}

		return list;
	}
	
	/* Returns index of first num in expression*/
	public static int chooseIndexToPerformOperation(ArrayList<String> exp) {
		if (contains("(", exp) || contains(")", exp)) {
			//Find index of operation that we'll perform
			int indexToPerformAtAfterRemoval = -1;
			for(int i = exp.lastIndexOf("(") ; i < exp.size() ; i++) {
				if(exp.get(i).equals("+") || exp.get(i).equals("-") || exp.get(i).equals("*") || exp.get(i).equals("/") || exp.get(i).equals("^")) {
					indexToPerformAtAfterRemoval = i-1;
					break;
				}
			}
			//Remove ()'s
			exp.remove(exp.indexOf(")"));
			exp.remove(exp.lastIndexOf("("));

			return indexToPerformAtAfterRemoval;
			
			
		} else if (contains("^", exp)) {
			return exp.indexOf("^");
		} else if ((contains("*", exp) || contains("/", exp))) {
			String firstOperator = "";
			for(String s : exp) {
				if(s.equals("*")) {
					firstOperator = s;
					break;
				} else if (s.equals("/")) {
					firstOperator = s;
					break;
				}
			}
			return exp.indexOf(firstOperator);
		} else {
			String firstOperator = "";
			for(String s : exp) {
				if(s.equals("+")) {
					firstOperator = s;
					break;
				} else if (s.equals("-")) {
					firstOperator = s;
					break;
				}
			}
			return exp.indexOf(firstOperator);
		}
	}
	
	public static ArrayList<String> operate (ArrayList<String> exp, int index) {
		if (exp.get(index).equals("^")) {
			//Solve power
			double ans = Math.pow(Double.parseDouble(exp.get(index-1)), Double.parseDouble(exp.get(index+1)));
			
			//replace 1st input with ans
			exp.set(index-1, "" + ans);
			
			//remove operator and 2nd input
			exp.remove(index+1);
			exp.remove(exp.lastIndexOf("^"));
		} else if (exp.get(index).equals("*")) {
			//Solve Mult
			double ans = Double.parseDouble(exp.get(index-1)) * Double.parseDouble(exp.get(index+1));
			
			//replace 1st input with ans
			exp.set(index-1, "" + ans);
			
			//remove operator and 2nd input
			exp.remove(index+1);
			exp.remove(index);
		} else if (exp.get(index).equals("/")) {
			//Solve power
			double ans = Double.parseDouble(exp.get(index-1)) / Double.parseDouble(exp.get(index+1));
			
			//replace 1st input with ans
			exp.set(index-1, "" + ans);
			
			//remove operator and 2nd input
			exp.remove(index+1);
			exp.remove(index);
		} else if (exp.get(index).equals("+")) {
			//Solve power
			double ans = Double.parseDouble(exp.get(index-1)) + Double.parseDouble(exp.get(index+1));
			
			//replace 1st input with ans
			exp.set(index-1, "" + ans);
			
			//remove operator and 2nd input
			exp.remove(index+1);
			exp.remove(index);
		} else if (exp.get(index).equals("-")) {
			//Solve power
			double ans = Double.parseDouble(exp.get(index-1)) - Double.parseDouble(exp.get(index+1));
			
			//replace 1st input with ans
			exp.set(index-1, "" + ans);
			
			//remove operator and 2nd input
			exp.remove(index+1);
			exp.remove(index);
		}
		return exp;
	}
	
	public static boolean contains(String checkForString, ArrayList<String> exp) {
		for(String s : exp) {
			if (s.equals(checkForString)) {
				return true;
			}
		}
		return false;
	}
	
}
