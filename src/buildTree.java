import java.util.ArrayList;

public class buildTree {
	public void buildthreelayer(Board board){
		buildTree(board);
		ArrayList<Board>firstSetOfChildren=board.getChildren();
		int i=0;
		while(i<firstSetOfChildren.size()){
			firstSetOfChildren.get(i).setIsRed(!firstSetOfChildren.get(i).getIsRed());
			buildTree(firstSetOfChildren.get(i));
			ArrayList<Board>secondSetOfChildren=firstSetOfChildren.get(i).getChildren();
			int j=0;
			while(j<secondSetOfChildren.size()){
				buildTree(secondSetOfChildren.get(j));
				j++;
			}
			i++;
		}




	}
	public void buildTree(Board board) {  //only going to build one layer
		board.calculateValue();
		int depth = 0;
		//depth of the tree
			int count = 0;
			int[][] getredlocation = new int[board.getBoardLength()][2];
			if (board.getIsRed()) {
				int num = board.getBoardLength();
				//finding all possible pieces
				for (int i = 0; i < board.getBoardLength(); i++) {
					for (int j = 0; j < board.getBoardLength(); j++) {
						if (board.getPiece(i, j)!=null&&board.getPiece(i, j).getTeam() == 0) {
							getredlocation[count][0] = i;
							getredlocation[count][1] = j;
							count++;
						}
					}
				}
				//get all possible next step

			} else {
				int num = board.getBoardLength();
				//finding all possible pieces
				for (int i = 0; i < board.getBoardLength(); i++) {
					for (int j = 0; j < board.getBoardLength(); j++) {
						if (board.getPiece(i, j)!=null&&board.getPiece(i, j).getTeam() == 1) {
							getredlocation[count][0] = i;
							getredlocation[count][1] = j;
							count++;
						}
					}
				}
			}
			for (int i = 0; i < count; i++) {
				Board possiblechild = board.duplicate();
				int x1 = getredlocation[i][0];
				int y1 = getredlocation[i][1];
				if (possiblechild.movePiece(x1, y1, x1, y1 - 1)) {
					possiblechild.setFather(board);
					possiblechild.calculateValue();
					board.setChildren(possiblechild);
				}
				Board possiblechild2 = board.duplicate();
				if (possiblechild2.movePiece(x1, y1, x1, y1 + 1)) {
					possiblechild2.setFather(board);
					possiblechild2.calculateValue();
					board.setChildren(possiblechild2);
				}
				Board possiblechild3 = board.duplicate();
				if (possiblechild3.movePiece(x1, y1, x1 - 1, y1)) {
					possiblechild3.setFather(board);
					possiblechild3.calculateValue();
					board.setChildren(possiblechild3);
				}
				Board possiblechild4 = board.duplicate();
				if (possiblechild4.movePiece(x1, y1, x1 + 1, y1)) {
					possiblechild4.setFather(board);
					possiblechild4.calculateValue();
					board.setChildren(possiblechild4);
				}
				Board possiblechild5 = board.duplicate();
				if (possiblechild5.movePiece(x1, y1, x1 - 1, y1 - 1)) {
					possiblechild5.setFather(board);
					possiblechild5.calculateValue();
					board.setChildren(possiblechild5);
				}
				Board possiblechild6 = board.duplicate();
				if (possiblechild6.movePiece(x1, y1, x1 - 1, y1 + 1)) {
					possiblechild6.setFather(board);
					possiblechild6.calculateValue();
					board.setChildren(possiblechild6);
				}
				Board possiblechild7 = board.duplicate();
				if (possiblechild7.movePiece(x1, y1, x1 + 1, y1 + 1)) {
					possiblechild7.setFather(board);
					possiblechild7.calculateValue();
					board.setChildren(possiblechild7);
				}
				Board possiblechild8 = board.duplicate();
				if (possiblechild8.movePiece(x1, y1, x1 + 1, y1 - 1)) {
					possiblechild8.setFather(board);
					possiblechild8.calculateValue();
					board.setChildren(possiblechild8);
				}


				//	board.setFather = null; // set up the original board


			}





	}
	public static void main(String[] args){
		Board board=new Board(1);
		buildTree buildTree=new buildTree();
		board.printBoard();
		buildTree.buildthreelayer(board);

	}

	}