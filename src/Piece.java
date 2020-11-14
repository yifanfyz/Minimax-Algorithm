/**
 *
 *
 * Piece class is the class of all Pieces.
 * @param team 红方棋子/蓝方棋子,0 = 红方, 1 = 蓝方, -1 = pit.
 * @param type 棋子类型,0 = Wumpus, 1 = Hero, 2 = Mage, -1 = pit.
 * @param x 棋子x坐标
 * @param y 棋子y坐标
 *
 */

class Piece{
	private int team;
	private int type;
	private int x;
	private int y;

	public Piece(int team, int t, int x, int y){
		this.team = team;
		this.type = t;
		this.x = x;
		this.y = y;
	}

	//generate pit
	public Piece(int x, int y){
		this.team = -1;
		this.type = -1;
		this.x = x;
		this.y = y;
	}

	public int getType(){
		return type;
	}
	public int getTeam(){
		return team;
	}

	public boolean move(int x, int y){
		if (this.type == -1) {
			return false;
		}
		if (1 >= Math.abs(x - this.x) && 1 >= Math.abs(y-this.y)) {
			this.x = x;
			this.y = y;
			return true;
		}
		return false;
	}

	public Piece duplicate(){
		return new Piece(this.team,this.type, this.x, this.y);
	}

	@Override
	public String toString(){
		String name = "";
		if (team == 0) {
			name += "r";
		}else if (team == 1){
			name += "b";
		}
		if (type == 0) {
			name += "W";
		}else if (type == 1) {
			name += "H";
		}else if (type == 2) {
			name += "M";
		}
		if (team == -1) {
			name = "pit";
		}
		return name;
	}

}



