/*
 *
 * min: human, max: AI
 *
 * player red -> Human/first 
 * player blue-> AI
 * move(1,2,3,4,6,7,8,9）-> 左上，上，右上，左，右，左下，下，右下
 * alpha sets to positive infinity, and beta sets to negative infinity
 */

import java.util.*;
//import java.lang.*;


public class MinMax{

	//private Queue<Board> pqueue = new LinkedList<Board>();
	private int alpha = Integer.MIN_VALUE;
	private int beta = Integer.MAX_VALUE;
	//private Boolean maximizingPlayer;
	private int bestChoice;
	//ArrayList<Board> bestBoard = new ArrayList<Board>();
	Board bestBoard;
	
	
	public MinMax(Board board) {
		//输入一个root board， 找到该root的最优选择
		//maximizingPlayer = board.getIsRed();  
		//this.bestChoice = minMax(board, depth, alpha, beta,maximizingPlayer);
	}
	
	public int minMax(Board board, int depth, int alpha, int beta, Boolean maximizingPlayer){

		//to check is the position is 0 or the game is over 游戏初始状态
	//000`	boolean test = !maximizingPlayer;

		if(depth == 0 || board.getCountB()==0 || board.getCountR()==0){  
			//Board temp = board.duplicate();
			//bestBoard.add(board);
			if(bestBoard==null)
				bestBoard = board;

			if(board!=null && bestBoard!=null && board.getValue() >bestBoard.getValue() ){
				bestBoard = board;
			}
			return board.getValue();
		}

		//if maximizing player, isRed=False
		if(maximizingPlayer){

			Queue<Board> pqueue = new LinkedList<Board>();
			int maxEval = Integer.MIN_VALUE;
			board.setValue(maxEval);
			int numChildren = board.getChildren().size(); 

			//do descending sort, with defined comparator: value
			ArrayList<Board> tempList = board.getChildren();
			Collections.sort(tempList, Collections.reverseOrder());

			//numChildren = size of TempList
			for(int i = 0; i<numChildren; i++){
				pqueue.add(tempList.get(i)); 
			}
			

			// k cannot exceed the total number of children board 
			while(pqueue.peek()!=null){ 
				Board tempBoard = pqueue.poll();
				int eval = minMax(tempBoard, depth - 1, alpha, beta, false);
				maxEval = Math.max(maxEval, eval);
				alpha = Math.max(alpha, eval);
				if( beta <= alpha ){
					break;
				}
				
		
			}
			//bestBoard = board;
			return maxEval;

		}else{

			Queue<Board> pqueue = new LinkedList<Board>();
			int minEval = Integer.MAX_VALUE;
			board.setValue(minEval);
			int numChildren = board.getChildren().size();

			//do descending sort, with defined comparator: value
			ArrayList<Board> tempList = board.getChildren();
			Collections.sort(tempList, Collections.reverseOrder());
			

			for(int i=0; i<tempList.size(); i++){
				pqueue.add(tempList.get(i)); 
			}


			//k cannot exceed the total number of children board
			while(pqueue.peek()!=null ){
				Board tempBoard = pqueue.poll();
				int eval = minMax(tempBoard, depth-1, alpha, beta, true); 
				minEval = Math.min(minEval, eval);
				beta = Math.min(beta, eval);
				if( beta <= alpha){
					break;
				}
	
			}
				

			
			//bestBoard = board;
			return minEval;
		}

		
	}

	public void setAlpha(int c) {
		this.alpha = c;
	}
	
	public void setBeta(int c) {
		this.beta = c;
	}
	
	public int getAlpha() {
		return alpha;
	}
	
	public int getBeta() {
		return beta;
	}
	
	public int getBestChoice() {
		return bestChoice;
	}

	public Board getBestBoard() {
		return bestBoard;
	}
	

}