import javax.swing.*;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.awt.GridLayout;   
import java.lang.Math;
import java.lang.Character;
public class calculator extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JLabel label;
	JTextField text;
	JButton mode;
	JButton sq;
	JButton pow;
	JButton oneOverX;
	JPanel row2;
	JButton sin;
	JButton cos;
	JButton tan;
	JButton dot;
	JButton ce;
	JButton c;
	JButton slash;
	JButton del;
	JButton[] numbers;
	JButton mul,minus,plus;
	JButton plusMinus;
	JButton zero;
	JButton cross;
	JButton equal,m1,m2,m3,m4;
	calculator() throws IOException{
		setTitle("Scientific Calculator");
		addPanels();
  		setLayout(null);
  		setBounds(400,100,400, 600);    
		setVisible(true);  
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	//---------------------------------------Panel Controller-------------------------------------------------------//
	private void addPanels(){
		headingLabel();
		typingBox();
		scientificOperation();
		textingModifiers();
		numbers();
		evaluators();
	}	
    
    //--------------------------------------------Panels------------------------------------------------------------//
    private void headingLabel(){
		JPanel standard = new JPanel();
        label = new JLabel("<html><span style='font-size:20px; font-family:times new roman, san-serif;' >"+" &nbsp = Scientific"+"</span></html>",SwingConstants.LEFT);
   		standard.setLayout(new GridLayout(1,1));
   		standard.add(label);
   		standard.setBounds(0,0,400,50);
   		//resize
   		add(standard);
	}

	private void typingBox(){
		// TEXT FIELD
		JPanel pText = new JPanel();
		Font font1 = new Font("SansSerif", Font.BOLD, 30);
        text = new JTextField(30);
        text.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
              		 	String expression= text.getText();
              			if(expression.contains("sin(")){
            				int index = expression.indexOf('s');
            				index += 4;
            				int close = expression.indexOf(')');
            				String s2 = "";
            				for(int i=index;i<close;i++) {
            					s2 += expression.charAt(i);
            				}
            				double num = Double.parseDouble(s2);
            	 			double res = Math.sin(num);
            	 			res = Math.round(res * 100.0) / 100.0;
            	 			if(expression.charAt(index-5) == '-' && res < 0) {
            	 				res *= -1;
            	 				expression = expression.substring(0,index-5)+"+"+ String.valueOf(res) + expression.substring(close+1,expression.length());
            	 			}if(expression.charAt(index-5) == '+' && res < 0) {
            	 				expression = expression.substring(0,index-5) + String.valueOf(res) + expression.substring(close+1,expression.length());
            	 			}
            			}if(expression.contains("tan(")){
            				int index = expression.indexOf('t');
            				index += 4;
            				int close = expression.indexOf(')');
            				String s2 = "";
            				for(int i=index;i<close;i++) {
            					s2 += expression.charAt(i);
            				}
            	 			double num = Double.parseDouble(s2);
            	 			double res = Math.tan(num);
            	 			res = Math.round(res * 100.0) / 100.0;
            	 			if(expression.charAt(index-5) == '-' && res < 0) {
            	 				res *= -1;
            	 				expression = expression.substring(0,index-5)+"+"+ String.valueOf(res) + expression.substring(close+1,expression.length());
            	 	 			System.out.print(expression);
            	 			}else if(expression.charAt(index-5) == '+' && res < 0) {
            	 				expression = expression.substring(0,index-5) + String.valueOf(res) + expression.substring(close+1,expression.length());
            	 			}
            	 			else {
            	 				expression = expression.substring(0,index-4)+ String.valueOf(res) + expression.substring(close+1,expression.length());
            	 			}
            			}
            			if(expression.contains("cos(")){
            				int index = expression.indexOf('c');
            				index += 4;
            				int close = expression.indexOf(')');
            				String s2 = "";
            				for(int i=index;i<close;i++) {
            					s2 += expression.charAt(i);
            				}
            	 			double num = Double.parseDouble(s2);
            	 			double res = Math.cos(num);
            	 			res = Math.round(res * 100.0) / 100.0;
            	 			if(expression.charAt(index-5) == '-' && res < 0) {
            	 				res *= -1;
            	 				expression = expression.substring(0,index-5)+"+"+ String.valueOf(res) + expression.substring(close+1,expression.length());
            	 	 			System.out.print(expression);
            	 			}else if(expression.charAt(index-5) == '+' && res < 0) {
            	 				expression = expression.substring(0,index-5) + String.valueOf(res) + expression.substring(close+1,expression.length());
            	 	 			System.out.print(expression);
            	 			}
            	 			else {
            	 				expression = expression.substring(0,index-4)+ String.valueOf(res) + expression.substring(close+1,expression.length());
            	 				System.out.print(expression);
            	 			}
            			}
						expression = infix_to_postfix(expression);
						expression =  PostfixEvaluation(expression);
						text.setText(expression);
						FileWriter myWriter;
						try {
							myWriter = new FileWriter("memory.txt");
							myWriter.write(expression+"\n");
						    myWriter.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					    
          		}
        }
        public void keyReleased(KeyEvent e){
        	char ch = e.getKeyChar();
          		if((ch>='a' && ch <='z' )|| (ch>='A' && ch <= 'Z')){
          			String getText = text.getText();
          			if(getText.length()>0) {
	          			char chh = getText.charAt(getText.length()-1);
						getText = getText.replace(chh,' ');
						getText = getText.trim();
						text.setText(getText);
					}
          		}
        }
    	});
        text.setFont(font1);
       	text.setHorizontalAlignment(SwingConstants.RIGHT);
   		pText.setLayout(new GridLayout(1,1));
   		pText.add(text);
   		pText.setBounds(0,50,400,100);
   		add(pText);
	}

	private void scientificOperation(){
		//row 0
   		JPanel row0 = new JPanel();
   		m1 = new JButton("M1");
   		m2 = new JButton("M2");
   		m3 = new JButton("M3");
   		m4 = new JButton("M4");
   		
   		m1.addActionListener(this);
   		m2.addActionListener(this);
   		m3.addActionListener(this);
   		m4.addActionListener(this);
   
		row0.add(m1);
		row0.add(m2);
		row0.add(m3);
		row0.add(m4);
  		row0.setLayout(new GridLayout(1,4));
  		row0.setBounds(0,150,400,45);

   		
   		// ROW 1
   		JPanel row1 = new JPanel();
   		mode = new JButton("mode");
		sq = new JButton("sq");
		pow = new JButton("x^2");
		oneOverX = new JButton("1/x");
		
		mode.addActionListener(this);
		sq.addActionListener(this);
		pow.addActionListener(this);
		oneOverX.addActionListener(this);


  		// ROW 2
   		row2 = new JPanel();
		sin = new JButton("sin");
		cos = new JButton("cos");
		tan = new JButton("tan");
		del = new JButton("del");
		
		sin.addActionListener(this);
		cos.addActionListener(this);
		tan.addActionListener(this);
		del.addActionListener(this);
		
		//adding to panel row1
		row1.add(mode);
		row1.add(sq);
		row1.add(pow);
		row1.add(oneOverX);
  		row1.setLayout(new GridLayout(1,4));
  		row1.setBounds(0,195,400,45);

  		//adding to panel row2
		row2.add(sin);
		row2.add(cos);
		row2.add(tan);
		row2.add(del);
  		row2.setLayout(new GridLayout(1,4));
  		row2.setBounds(0,240,400,45);

		add(row0);
  		add(row1);   
  		add(row2); 
	}

	private void textingModifiers(){
		JPanel row3 = new JPanel();
  		ce = new JButton(")");
  		c = new JButton("(");
  		cross = new JButton("AC");
		slash  = new JButton("/");
		
		ce.addActionListener(this);
		c.addActionListener(this);
		cross.addActionListener(this);
		slash.addActionListener(this);
		
		row3.add(c);
		row3.add(ce);
		row3.add(slash);
		row3.add(cross);
  		row3.setLayout(new GridLayout(1,4));
  		row3.setBounds(0,285,400,45);
  		add(row3);
	}

	private void numbers(){
		//NUMBERS ROW 4
  		JPanel numbersPanel = new JPanel();
  		numbers = new JButton[9];
  		for(int i=1;i<10;i++){
  			numbers[i-1] = new JButton(String.valueOf(i));
  			numbers[i-1].addActionListener(this);
			numbersPanel.add(numbers[i-1]);
			if(i==3){
				mul = new JButton("*");
				mul.addActionListener(this);
				numbersPanel.add(mul);
			}
			if(i==6){
				minus = new JButton("-");
				minus.addActionListener(this);
				numbersPanel.add(minus);
			}
			if(i==9){
				plus = new JButton("+");
				plus.addActionListener(this);
				numbersPanel.add(plus);
			}
  		}
  		numbersPanel.setLayout(new GridLayout(3,4));
  		numbersPanel.setBounds(0,330,400,190);
  		add(numbersPanel);
	}

	private void evaluators(){
		JPanel row5 = new JPanel();
		plusMinus = new JButton("+-");
		zero = new JButton("0");
		dot = new JButton(".");
		equal = new JButton("=");
		equal.setForeground(Color.RED);
		
		plusMinus.addActionListener(this);
		zero.addActionListener(this);
		dot.addActionListener(this);
		equal.addActionListener(this);
		
		row5.add(plusMinus);
		row5.add(zero);
		row5.add(dot);
		row5.add(equal);
  		row5.setLayout(new GridLayout(1,4));
  		row5.setBounds(0,520,400,55);
  		add(row5);
	}
	//--------------------------------------------PostFix Evaluation--------------------------------------------------------//
	private String PostfixEvaluation(String str) {
		boolean found=false;
	    double v1,v2;
	    Stack<Double> s = new Stack<Double>();
	    for (int i=0; i<str.length(); i++) {
	        if((str.charAt(i) >= 48) && (str.charAt(i) <= 57) ){
	        	if(str.charAt(i+1) == ' ') {
	        		s.push((double)str.charAt(i)-'0');}
	        	else {
	        		String numb ="";
	        		do{
	        			numb += str.charAt(i);
	        			i++;
	        		}while(str.charAt(i)!=' ');
	        		double num = Double.parseDouble(numb);
	        		s.push(num);
	        	}
	        }
	        else if((str.charAt(i) == ' ')) {
	        	continue;
	        }
	        else{
	        	 	found=true;
	                if(s.isEmpty())
	                	return "Error 1";
	                v1=s.peek();
	                s.pop();
	                if(s.isEmpty())
	                	return "Error 2";
	                v2=s.peek();
	                s.pop();
	                switch (str.charAt(i)) {
	                case '*':
	                	s.push(v2*v1);
	                	break;
	                case '%':
	                	s.push(v2%v1);
	                	break;
	                case '-':
	                	s.push(v2-v1);
	                	break;
	                case '+':
	                	s.push(v2+v1);
	                	break;
	                case '/':
	                	s.push(v2/v1);
	                	break;
	                }
	        	}
	    
	    	}
	    double result=s.peek();
	    result = Math.round(result * 100.0) / 100.0;
	    s.pop();
	    if(!s.isEmpty() && found==false){
	        return "Error 3";
	    }
	    else
	    	return String.valueOf(result);
	}
	//--------------------------------------------infix_to_postfix--------------------------------------------------------//
	static int Prec(char ch)
    {
        switch (ch)
        {
        case '+':
        case '-':
            return 1;
      
        case '%':
        case '*':
        case '/':
            return 2;
			
        }
        return -1;
    }
	private String infix_to_postfix(String exp){
		String result = new String("");
        Stack<Character> stack = new Stack<Character>();
         
        for (int i = 0; i<exp.length(); ++i)
        {
            char c = exp.charAt(i);
            if (Character.isLetterOrDigit(c) || c == '.'){
                result += c;
			}
            else if (c == '('){
				result += ' ';
                stack.push(c);
			}
            else if (c == ')')
            {
				result += ' ';
                while (!stack.isEmpty() &&
                        stack.peek() != '(')
                    result += stack.pop();
                 
                    stack.pop();
            }
            else
            {
				result += ' ';
                while (!stack.isEmpty() && Prec(c)
                         <= Prec(stack.peek())){
                   result += stack.pop();
				   result += ' ';
             }
                stack.push(c);
            }
      
        }
		result += ' ';
        while (!stack.isEmpty()){
            if(stack.peek() == '(')
                return "Invalid Expression";
            result += stack.pop();
			result += ' ';
         }
        return result;
	}

	//--------------------------------------------EventHandler------------------------------------------------------------//
	@Override
    public void actionPerformed(ActionEvent e) {
		if (e.getSource() == m1){
			Scanner sc;
			try {
				sc = new Scanner(new File("memory.txt"));
				String s = text.getText();
				while(sc.hasNext()) {
					s = sc.next();
				}
				text.setText(s);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if (e.getSource() == m2){
			Scanner sc;
			try {
				sc = new Scanner(new File("memory.txt"));
				String s = text.getText();
				String array[] = new String[1000];
				int i=0;
				while(sc.hasNext()) {
					array[i] = sc.next();
					i++;
				}
				if(i > 1) {
					text.setText(array[i-2]);
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if (e.getSource() == m3){
			Scanner sc;
			try {
				sc = new Scanner(new File("memory.txt"));
				String s = text.getText();
				String array[] = new String[1000];
				int i=0;
				while(sc.hasNext()) {
					array[i] = sc.next();
					i++;
				}
				if(i > 2) {
					text.setText(array[i-3]);
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if (e.getSource() == m4){
			Scanner sc;
			try {
				sc = new Scanner(new File("memory.txt"));
				String s = text.getText();
				String array[] = new String[1000];
				int i=0;
				while(sc.hasNext()) {
					array[i] = sc.next();
					i++;
				}
				if(i > 3) {
					text.setText(array[i-4]);
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		if (e.getSource() == sq){
			String expression= text.getText();
			expression = infix_to_postfix(expression);
			expression =  PostfixEvaluation(expression);
			double exp = Double.parseDouble(expression);
			double num = Math.sqrt(exp);
			text.setText(String.valueOf(num));
		}
		if (e.getSource() == pow){
			String expression= text.getText();
			expression = infix_to_postfix(expression);
			expression =  PostfixEvaluation(expression);
			double exp = Double.parseDouble(expression);
			double num = Math.pow(exp,2);
			text.setText(String.valueOf(num));
		}
		if (e.getSource() == oneOverX){
			String expression= text.getText();
			expression = infix_to_postfix(expression);
			expression =  PostfixEvaluation(expression);
			double exp = Double.parseDouble(expression);
			double num = 1/(double)exp;
			text.setText(String.valueOf(num));
		}
		if (e.getSource() == cos){
			String expression= text.getText();
			text.setText(expression+"cos(");
		}
		if (e.getSource() == tan){
			String expression= text.getText();
			text.setText(expression+"tan(");
		}
		if (e.getSource() == dot){
			text.setText(text.getText()+".");
//			String expression= text.getText();
//			if(expression.charAt(0) == '-') {
//				double exp = Double.parseDouble(expression);
//				double num = Math.abs((double)exp);
//				text.setText(String.valueOf(num));
//			}else {
//				expression = infix_to_postfix(expression);
//				expression =  PostfixEvaluation(expression);
//				double exp = Double.parseDouble(expression);
//				double num = Math.abs((double)exp);
//				text.setText(String.valueOf(num));
//			}
		}
		if (e.getSource() == sin){
			String expression= text.getText();
			text.setText(expression+"sin(");
		}
    	if (e.getSource() == zero){
			text.setText(text.getText()+"0");
		}
		if (e.getSource() == mul){
			text.setText(text.getText()+"*");
		}
		if (e.getSource() == minus){
			text.setText(text.getText()+"-");
		}
		if (e.getSource() == plus){
			text.setText(text.getText()+"+");
		}
		if (e.getSource() == plusMinus){
			String txt = text.getText();
			int i = Integer.parseInt(txt);
			i = i*-1;
			text.setText(String.valueOf(i));
		}
		if (e.getSource() == c){
			text.setText(text.getText()+"(");
		}
		if (e.getSource() == ce){
			text.setText(text.getText()+")");
		}
		if (e.getSource() == slash){
			text.setText(text.getText()+"/");
		}
		if (e.getSource() == mode){
			text.setText(text.getText()+"%");
		}
		if (e.getSource() == del){
			String getText = text.getText();
			if(getText.length()>0) {
				StringBuilder string = new StringBuilder(getText); 
		        string.setCharAt(getText.length()-1,' ');
		        String s= string.toString();
		        s  = s.replaceAll(" ", "");
				text.setText(s);
			}
		}
		if (e.getSource() == cross){
			text.setText("");
		}
		for(int i=1;i<10;i++){
			if(numbers[i-1] == e.getSource()){
				text.setText(text.getText()+String.valueOf(i));
			}
		}
		if(e.getSource() == equal ) {
			String expression= text.getText();
			if(expression.contains("sin(")){
				int index = expression.indexOf('s');
				index += 4;
				int close = expression.indexOf(')');
				String s2 = "";
				for(int i=index;i<close;i++) {
					s2 += expression.charAt(i);
				}
				double num = Double.parseDouble(s2);
	 			double res = Math.sin(num);
	 			res = Math.round(res * 100.0) / 100.0;
	 			if(expression.charAt(index-5) == '-' && res < 0) {
	 				res *= -1;
	 				expression = expression.substring(0,index-5)+"+"+ String.valueOf(res) + expression.substring(close+1,expression.length());
	 			}if(expression.charAt(index-5) == '+' && res < 0) {
	 				expression = expression.substring(0,index-5) + String.valueOf(res) + expression.substring(close+1,expression.length());
	 			}
			}if(expression.contains("tan(")){
				int index = expression.indexOf('t');
				index += 4;
				int close = expression.indexOf(')');
				String s2 = "";
				for(int i=index;i<close;i++) {
					s2 += expression.charAt(i);
				}
	 			double num = Double.parseDouble(s2);
	 			double res = Math.tan(num);
	 			res = Math.round(res * 100.0) / 100.0;
	 			if(expression.charAt(index-5) == '-' && res < 0) {
	 				res *= -1;
	 				expression = expression.substring(0,index-5)+"+"+ String.valueOf(res) + expression.substring(close+1,expression.length());
	 	 			System.out.print(expression);
	 			}else if(expression.charAt(index-5) == '+' && res < 0) {
	 				expression = expression.substring(0,index-5) + String.valueOf(res) + expression.substring(close+1,expression.length());
	 			}
	 			else {
	 				expression = expression.substring(0,index-4)+ String.valueOf(res) + expression.substring(close+1,expression.length());
	 			}
			}
			if(expression.contains("cos(")){
				int index = expression.indexOf('c');
				index += 4;
				int close = expression.indexOf(')');
				String s2 = "";
				for(int i=index;i<close;i++) {
					s2 += expression.charAt(i);
				}
	 			double num = Double.parseDouble(s2);
	 			double res = Math.cos(num);
	 			res = Math.round(res * 100.0) / 100.0;
	 			if(expression.charAt(index-5) == '-' && res < 0) {
	 				res *= -1;
	 				expression = expression.substring(0,index-5)+"+"+ String.valueOf(res) + expression.substring(close+1,expression.length());
	 	 			System.out.print(expression);
	 			}else if(expression.charAt(index-5) == '+' && res < 0) {
	 				expression = expression.substring(0,index-5) + String.valueOf(res) + expression.substring(close+1,expression.length());
	 	 			System.out.print(expression);
	 			}
	 			else {
	 				expression = expression.substring(0,index-4)+ String.valueOf(res) + expression.substring(close+1,expression.length());
	 				System.out.print(expression);
	 			}
			}
			expression = infix_to_postfix(expression);
			expression =  PostfixEvaluation(expression);
			text.setText(expression);
			FileWriter myWriter;
			try {
				myWriter = new FileWriter("memory.txt",true);
				myWriter.write(expression+"\n");
			    myWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
 
	//--------------------------------------------Main Method------------------------------------------------------------//
	public static void main(String[] args) throws IOException{
		new calculator();
		
	}
}
