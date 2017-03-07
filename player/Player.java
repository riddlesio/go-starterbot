package player;

public class Player {
    
    private String name;
    private double points;
    private String lastMove;

    public Player(String name) {
        this.name = name;
    }

    public void setPoints(double points) {
        this.points = points;
    }
    
    public double getPoints() {
        return this.points;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setLastMove(String lastMove) {
    	this.lastMove = lastMove;
    }
    
    public String getLastMove() {
    	return this.lastMove;
    }
}
