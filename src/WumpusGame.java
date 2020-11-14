import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;




public class WumpusGame{


	public static void main(String[] args) throws InterruptedException{
		int num = Integer.parseInt(args[0]);
		if (num%3 != 0) {
			System.out.println("input is not a multiple of 3, cannot build board.");
			return;
		}else if (num <= 0){
			System.out.println("input is smaller or equal to 0, cannot build board.");
			return;
		}
		System.out.println("build a "+num+"*"+num+"board.");
		Board b = new Board(num/3);
		b.printBoard();

		Scanner in;
		String userInput;
		int x = -1;
		int y = -1;
	//	boolean isRed = true; 该功能挪到了board中

		while(b.getCountB() != 0 && b.getCountR() != 0){

			in = new Scanner(System.in);
			if (b.getIsRed()) {
				System.out.println("Red trun!");
			}else{
				System.out.println("Blue trun!");
			}
			System.out.println("choose your piece:");
			int x1, y1;

			while(true) {
				userInput = in.nextLine();
				if (userInput.indexOf(' ')  == -1 ) {
					System.out.println("wrong format! Please enter your piece again.");
					continue;
				}
				x1 = Integer.parseInt(userInput.substring(0, userInput.indexOf(' ')));
				y1 = Integer.parseInt(userInput.substring(userInput.indexOf(' ')+1));
				if (x1 >= num || y1 >= num) {
					System.out.println("x or y out of range! Please enter your piece again.");
					continue;
				}else if(b.getPiece(x1,y1) == null){
					System.out.println("You have to choose a piece! Please enter your piece again.");
					continue;
				}else if((b.getPiece(x1,y1).getTeam() == 0 && b.getIsRed()) || (b.getPiece(x1,y1).getTeam() == 1 && (!b.getIsRed()))){
					break;
				}else if (b.getPiece(x1,y1).getTeam() == -1) {
					System.out.println("you choosed a pit! Please enter your piece again.");
					continue;
				}else{
					System.out.println("Invalid input! Please enter your piece again.");
					continue;
				}
			}

			int x2, y2;

			System.out.println("move to:");
			while(true) {
				userInput = in.nextLine();
				if (userInput.indexOf(' ')  == -1 ) {
					System.out.println("wrong format! Please enter your destination again.");
					continue;
				}
				x2 = Integer.parseInt(userInput.substring(0, userInput.indexOf(' ')));
				y2 = Integer.parseInt(userInput.substring(userInput.indexOf(' ')+1));
				if (x2 >= num || y2 >= num) {
					System.out.println("x or y out of range! Please enter your destination again.");
					continue;
				}else{
					if(!b.movePiece(x1,y1,x2,y2)){
					System.out.println("you cannot put your piece here! Please enter your destination again.");
					}else{
						break;
					}
				}
			}
		//	Board c = new Board(num/3);
		//	c = b.duplicate();
		//	System.out.println("previous: ");
		//	c.printBoard();
			System.out.println("now: ");  // b is the current board we have , and we need to use Board b to calculate player 2 optimized step. 
			b.printBoard();
			b.setIsRed(!b.getIsRed());//如果是1v1人人对战， 这里的隐藏要打开， 并且将minmax部分隐藏
			if(b.getIsRed()) {
				System.out.println("Board player: huamn(red)");
			}else {
				System.out.println("Board player: AI(blue)");
			}
			
			b.calculateValue();
			
			System.out.println("Board value: "+ b.getValue());		
			
			buildTree tree = new buildTree();
			
			tree.buildthreelayer(b);
			//b.printBoard();
			MinMax optimumTest = new MinMax(b);
			int min = Integer.MIN_VALUE;
			int max = Integer.MAX_VALUE;
		//	b.setIsRed(!b.getIsRed());
			optimumTest.minMax(b, 3, min, max, b.getIsRed());
			System.out.println("...");
			//Thread.sleep(1000);
			System.out.println("AI ... calculating ... ");
			//Thread.sleep(1000);
			System.out.println();
			if(optimumTest.getBestBoard().getFather().getFather() == null && (optimumTest.getBestBoard().getCountB()==0 || optimumTest.getBestBoard().getCountR()==0)){
				optimumTest.getBestBoard().printBoard();
				break;
			}
			Board AI = optimumTest.getBestBoard().getFather().getFather();
			
			AI.printBoard();
			//System.out.print("value:" + AI.getValue());
			b = AI.duplicate();   
			//ArrayList<Board> AI = optimumTest.getBestBoard();
			//System.out.println("AI list length: "+ AI.size());
			//b.setIsRed(!b.getIsRed());
			

		}


		if (b.getCountR() == 0 && b.getCountB() == 0) {
			System.out.println("Draw!");
		}else if (b.getCountR() == 0) {
			System.out.println("Red wins!");
		}else{
			System.out.println("AI wins!");
		}

		
	}
	
	
}

