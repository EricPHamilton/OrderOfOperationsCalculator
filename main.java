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
	
	/**
	 * Returns whether or not the given char is a number (true), or if it is an
	 * operation char (false)
	 *
	 * @param  c char that will be checked
	 * @return      true if is number, false if operator
	 */
	public static boolean isNumber(char c) {
		char[] listOfChars = {'+' , '-', '*', '/', '^', '(', ')'};
		for(char cha : listOfChars) {
			if(c == cha) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isOperator(char c) {
		return !isNumber(c);
	}
	
	/**
	 * Converts a param expression String to an ordered arrayList
	 * Each element is a number or an operator
	 *
	 * @param  Expression String - Ex. "1+(4*74)"
	 * @return      ArrayList<String> that represents the Expression String - Ex. {1,+,(,4,*,74,)}
	 */
	public static ArrayList<String> convertToArrayList(String exp) {
		//Adds each different char to its own element in the ArrayList
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0 ; i < exp.length() ; i++) {
			if (exp.charAt(i) != ' ') {
				list.add("" + exp.charAt(i));
			}
		}
			
		//Parses through list to make sure there are no double-digit numbers
		//Combines two indices with numbers together and shrinks the ArrayList
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

		//Converts -'s that imply negative numbers from subtraction operators to negative numbers.
		for(int i = 0 ; i < list.size() ; i++) {		
			/*if (list.get(i).charAt(0) == '-') {
				if ((i == 0 && list.get(i).length() > 1) || (isOperator(list.get(i-1).charAt(0)))) {
					System.out.println(list.get(i+1));
					if (!list.get(i+1).contains("(") || !list.get(i+1).contains(")")) {
						System.out.println("Changing");
						list.set(i, "-" + list.get(i+1));
						list.remove(i+1);
					}
				}
			}*/
			if (list.get(i).contains("-")) {
				if (isNumber(list.get(i+1).charAt(0))) {
					if (isOperator(list.get(i-1).charAt(0)) || i == 0) { //If the one before '-' is an operator...
						//Then its a negative
						System.out.println("Changing");
						list.set(i, "-" + list.get(i+1));
						list.remove(i+1);
					}
				}
			}

		}
		return list;
	}
	
	/**
	 * Returns the index of the operator we wish to operate at
	 *
	 * @param  ArrayList representation of the expression
	 * @return      int of index of the operator we wish to operate at
	 */
	public static int chooseIndexToPerformOperation(ArrayList<String> exp) {
		if (contains("(", exp) || contains(")", exp)) {
			//Find index of operation that we'll perform
			int indexToPerformAtAfterRemoval = -1;
			for(int i = exp.lastIndexOf("(") ; i < exp.size() ; i++) {
				if(exp.get(i).equals("+") || exp.get(i).equals("-") || exp.get(i).equals("*") || exp.get(i).equals("/") || exp.get(i).equals("^")) {
					indexToPerformAtAfterRemoval = i;
					break;
				}
			}

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
	
	/**
	 * Performs the mathematical expression at the given index.
	 *
	 * @param  exp - ArrayList representation of the expression string
	 * @param index - index of operation to be performed
	 * @return ArrayList<String> of shortened arrayList that represents the state of the expression after operation.
	 */
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
		
		removeParenthesis(exp);
		
		return exp;
	}
	
	/**
	 * Checks to see if a String is contained in ArrayList
	 *
	 * @param  checkForString, the thing we are checking for
	 * @param exp, the arrayList representation of the expression
	 * @return true if checkForString is an element of exp, false if otherwise.
	 */
	public static boolean contains(String checkForString, ArrayList<String> exp) {
		for(String s : exp) {
			if (s.equals(checkForString)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Only deletes Parenthesis if there is a single element between them (expression is inoperable)
	 *
	 * @param exp, the arrayList representation of the expression
	 * @return ArrayList with removed parenthesis if needed
	 */
	public static ArrayList<String> removeParenthesis(ArrayList<String> exp) {
		if(exp.contains("(") || exp.contains(")")) {
			int lastStartParen = exp.lastIndexOf("(");
			int matchingEndParen = -1;
			for (int i = exp.size()-1 ; i > lastStartParen; i--) {
				if (exp.get(i).equals(")")) {
					matchingEndParen = i;
				}
			}
			if (exp.get(lastStartParen + 2).equals(")")) {
				exp.remove(lastStartParen);
				exp.remove(matchingEndParen-1);
			}
		}
			
		return exp;
	}
	
}
