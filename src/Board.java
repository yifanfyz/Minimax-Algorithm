/**
 *
 * Board class is the class of the game interface.
 * @param board 棋盘本身
 * @param countRed 红色棋子计数，  默认: 人
 * @param countBlue 蓝色棋子计数， 默认: AI
 *
 */

import java.util.*;



public class Board implements Comparable<Board> {

	private Piece[][] board;
	private int countRed;
	private int countBlue;
	private boolean isRed;
	private ArrayList<Board> subBoard;
	private Board father;
	private int value = 0;
	private int depth = 3;
	//public static final int SUGGESTIVE_MAX_DEPTH = 10;

	//这里的constructor的d限制了输入的数值 它本身是input的1/3 这样可以保证board的大小一定是3的倍数
	public Board(int d){
		this.board = new Piece[d*3][d*3];
		for (int i = 1; i < this.board.length - 1; i ++) {
			for (int j = 0; j < this.board[0].length; j ++) {
				this.board[i][j] = null;
			}
		}
		for (int i = 0; i < d; i ++) {
			for (int j = 0; j < 3; j ++) {
				//红方
				this.board[0][i*3+j] = new Piece(0, j, 0, i*3+j);
				//蓝方
				this.board[this.board.length-1][i*3+j] = new Piece(1, j,this.board.length-1, i*3+j);
			}
		}
		//随机生成pit
		for(int j = 1; j < this.board.length - 1; j ++){
			for (int i = 0; i < d-1; i ++) {
				int y = (int)(Math.random()*d*3);
				this.board[j][y] = new Piece(j,y);
			}
		}
		this.countRed = d*3;
		this.countBlue = d*3;
		this.isRed = true;
		this.subBoard=new ArrayList<Board>();
	}

	//计算当前的局势，每个棋子值2分，如果可以杀死对方一个棋子，额外加3分，如果没有可以杀死的，额外加1分。

	public void calculateValue(){
		int red_Wumpuses = 0;
		int red_heroes = 0;
		int red_mages = 0;

		int blue_Wumpuses = 0;
		int blue_heroes = 0;
		int blue_mages = 0;
		int redpoints = 0;
		int bluepoints = 0;

		for (int i = 0; i < this.board.length; i ++) {
			for (int j = 0; j < this.board.length; j ++) {
				if(this.board[i][j]!= null && this.board[i][j].getTeam() == 0 && this.board[i][j].getType() == 0)
					red_Wumpuses++;
				if(this.board[i][j]!= null && this.board[i][j].getTeam() == 1 && this.board[i][j].getType() == 0)
					blue_Wumpuses++;

				if(this.board[i][j]!= null && this.board[i][j].getTeam() == 0 && this.board[i][j].getType() == 1)
					red_heroes++;
				if(this.board[i][j]!= null && this.board[i][j].getTeam() == 1 && this.board[i][j].getType() == 1)
					blue_heroes++;

				if(this.board[i][j]!= null && this.board[i][j].getTeam() == 0 && this.board[i][j].getType() == 2)
					red_mages++;
				if(this.board[i][j]!= null && this.board[i][j].getTeam() == 1 && this.board[i][j].getType() == 2)
					blue_mages++;
			}
		}

		//red win means positive points

		redpoints = 4*(red_Wumpuses+red_heroes+red_mages);

		bluepoints = 4*(blue_Wumpuses+blue_mages+blue_heroes);
		//System.out.println("redpoints:"+redpoints);
		//System.out.println("bluepoints:"+bluepoints);

		if(blue_mages>0 && red_Wumpuses > 0)
			redpoints = redpoints + blue_mages*3;
		else
			redpoints = redpoints + red_Wumpuses;
		//System.out.println("redpoints:"+redpoints);


		if(blue_heroes>0 && red_mages>0 )
			redpoints = redpoints + blue_heroes*3;
		else
			redpoints = redpoints + red_mages;
		//System.out.println("redpoints:"+redpoints);

		if(blue_Wumpuses>0 && red_heroes>0 )
			redpoints = redpoints + blue_Wumpuses*3;
		else
			redpoints = redpoints + red_heroes;
		//System.out.println("redpoints:"+redpoints);



		if(red_mages>0 && blue_Wumpuses > 0)
			bluepoints = bluepoints + red_mages*3;
		else
			bluepoints = bluepoints + blue_Wumpuses;
		//System.out.println("bluepoints:"+bluepoints);

		if(red_heroes>0 && blue_mages > 0)
			bluepoints = bluepoints + red_heroes*3;
		else
			bluepoints = bluepoints + blue_mages;
		//System.out.println("bluepoints:"+bluepoints);

		if(red_Wumpuses>0 && blue_heroes >0)
			bluepoints = bluepoints + red_Wumpuses*3;
		else
			bluepoints = bluepoints + blue_heroes;
		//System.out.println("bluepoints:"+bluepoints);

		bluepoints = bluepoints*10;
		redpoints = redpoints*10;

		//自杀
		if(red_mages != blue_mages){
			if(red_mages>blue_mages)
			redpoints = redpoints + (red_mages - blue_mages) * 5;
			else
			bluepoints = bluepoints + (blue_mages - red_mages) * 5;
		}

		if(red_Wumpuses != blue_Wumpuses){
			if(red_Wumpuses>blue_Wumpuses)
			redpoints = redpoints + (red_Wumpuses - blue_Wumpuses) * 5;
			else
			bluepoints = bluepoints + (blue_Wumpuses - red_Wumpuses) * 5;
		}

		if(red_heroes != blue_heroes){
			if(red_heroes>blue_heroes)
			redpoints = redpoints + (red_heroes - blue_heroes) * 5;
			else
			bluepoints = bluepoints + (blue_heroes - red_mages) * 5;
		}

		if(red_mages < blue_heroes){
			redpoints = redpoints - (blue_heroes - red_mages) * 2;
		}
		if(red_heroes < blue_Wumpuses){
			redpoints = redpoints - (blue_Wumpuses - red_heroes) * 2;
		}
		if(red_Wumpuses < blue_mages){
			redpoints = redpoints - (blue_mages - red_Wumpuses) * 2;
		}

		if(blue_mages < red_heroes){
			bluepoints = bluepoints - (red_heroes - blue_mages) * 2;
		}
		if(blue_heroes < red_Wumpuses){
			bluepoints = bluepoints - (red_Wumpuses - blue_heroes) * 2;
		}
		if(blue_Wumpuses < red_mages){
			bluepoints = bluepoints - (red_mages - blue_Wumpuses) * 2;
		}

		value = bluepoints - redpoints;
		//System.out.print("value:" + value);

	}


	public int compareTo(Board m)
	{
		return this.value - m.value;
	}

	public int getBoardLength(){
		return this.board.length;
	}
	

	public int getCountR(){
		return countRed;
	}

	public boolean getIsRed(){
		return isRed;
	}

	public Board getFather(){
		return father;
	}
	
	public int getDepth() {
		return depth;
	}

	public ArrayList<Board> getChildren(){
		return subBoard;
	}

	public int getValue(){
		return value;
	}

	public int getCountB(){
		return countBlue;
	}

	public void setIsRed(boolean c){
		this.isRed = c;
	}
	
	public void setDepth(int c) {
		this.depth = c;
	}

	public void setCountR(int c){
		this.countRed = c;
	}
	public void setCountB(int c){
		this.countBlue = c;
	}

	public void setValue(int c){
		this.value = c;
	}

	public void setFather(Board father){
		this.father = father;
	}

	public void setChildren(Board children){
		this.subBoard.add(children);
	}

	public boolean havePiece(int x, int y){
		if (this.board[x][y] == null) {
			return false;
		}
		return true;
	}
	public Piece getPiece(int x, int y){
		return this.board[x][y];
	}

	//移动棋子 x1 y1是棋子坐标 x2 y2是目标点坐标
	public boolean movePiece(int x1, int y1, int x2, int y2){
		int num=this.board.length;
		if(x1<0||y1<0||x2<0||y2<0)return false;
		if(x1>=num||y1>=num||x2>=num||y2>=num)return false;
		if (this.board[x1][y1] == null) {
			System.out.println("("+x1+","+y1+") is empty");
			return false;
		}
		if (this.board[x1][y1].getTeam() == -1) {
			return false;
		}
		int team1 = this.board[x1][y1].getTeam();
		if (this.board[x2][y2] == null) {
			if (this.board[x1][y1].move(x2,y2)) {
				this.board[x2][y2] = this.board[x1][y1];
				this.board[x1][y1] = null;
				return true;
			}
			System.out.println("Cannot reach ("+x2+","+y2+")");
			return false;
		}else
		{
			if (this.board[x2][y2].getTeam() == -1){
				if (team1 == 0) {
					this.countRed --;
				}else{
					this.countBlue --;
				}
				this.board[x1][y1] = null;
				this.board[x2][y2] = null;
				//System.out.println("you fell into the pit!");

				return true;
			}
			if (this.board[x1][y1].getTeam() == this.board[x2][y2].getTeam()) {
				return false;
			}
			if (this.board[x1][y1].move(x2,y2)){
				int type1 = this.board[x1][y1].getType();
				int type2 = this.board[x2][y2].getType();
				if (type1 == type2) {
					this.board[x1][y1] = null;
					this.board[x2][y2] = null;
					this.countRed --;
					this.countBlue --;
					return true;
				}
				boolean t1wins = false;
				if (type1 + type2 == 1){
					if (type1 == 1) {
						//1吃2
						t1wins = true;
					}
				}else if(type1 + type2 == 2){
					if (type1 == 0) {
						//1吃2
						t1wins = true;
					}
				}else if (type1 + type2 == 3) {
					if (type1 == 2) {
						//1吃2
						t1wins = true;
					}
				}
				if (t1wins) {
					if (team1 == 1) {
						this.countRed --;
					}else{
						this.countBlue --;
					}
					//System.out.println("you have eaten ("+x2+","+y2+")!");
					this.board[x2][y2] = this.board[x1][y1];
					this.board[x1][y1] = null;
				}else{
					if (team1 == 0) {
						this.countRed --;
					}else{
						this.countBlue --;
					}
					//System.out.println("you have been eaten by ("+x2+","+y2+")!");
					this.board[x1][y1] = null;
				}
				return true;
			}
			System.out.println("Cannot reach ("+x2+","+y2+")");
		}
		System.out.println("default.");
		return false;
	}

	public void printBoard(){
		for (int i = 0; i < this.board.length; i ++) {
			System.out.print("\t"+i);
		}
		System.out.println();
		for (int i = 0; i < this.board.length; i ++) {
			System.out.print(i + "\t");
			for (int j = 0; j < this.board[0].length; j ++) {
				if (this.board[i][j] == null) {
					System.out.print( "*\t");
				}else{
					System.out.print(this.board[i][j] + "\t");
				}
			}
			System.out.println();System.out.println();
		}
	}

	//这个method可以完全新建一个一模一样的board object 这样修改这个复制过的board的话不会影响之前的数据
	public Board duplicate(){
		Board b = new Board(this.board.length/3);
		for (int i = 0; i < this.board.length; i ++) {
			for (int j = 0; j < this.board[0].length; j ++) {
				if (this.board[i][j] == null) {
					b.board[i][j] = null;
				}else{
					b.board[i][j] = this.board[i][j].duplicate();
				}
			}
		}
		b.setCountR(this.getCountR());
		b.setCountB(this.getCountB());
		return b;
	}


}