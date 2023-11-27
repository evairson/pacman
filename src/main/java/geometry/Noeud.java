package geometry;
import config.*;
import java.util.ArrayList;

public class Noeud { //TODO : mettre des commentaires
    private final IntCoordinates coordinates;
    private final Noeud parent;
    private int cout;
    private int heuristique;

    public Noeud(IntCoordinates coordinates, Noeud parent){
        this.coordinates = coordinates;
        this.parent = parent;
    }

    public ArrayList<Noeud> getVoisins(MazeConfig config){
        ArrayList<Noeud> arr = new ArrayList<>();
        Cell currCell = config.getCell(this.coordinates);
        if(!currCell.northWall()){
            if(this.coordinates.y() - 1 >= 0){
                arr.add(new Noeud(new IntCoordinates(this.coordinates.x(), this.coordinates.y() - 1), this));
            }
        }
        if(!currCell.southWall()){
            if(this.coordinates.y() + 1 < config.getHeight()){
                arr.add(new Noeud(new IntCoordinates(this.coordinates.x(), this.coordinates.y() + 1), this));
            }
        }
        if(!currCell.westWall()){
            if(this.coordinates.x() - 1 >= 0){
                arr.add(new Noeud(new IntCoordinates(this.coordinates.x() - 1, this.coordinates.y()), this));
            }
        }
        if(!currCell.eastWall()){
            if(this.coordinates.x() + 1 < config.getWidth()){
                arr.add(new Noeud(new IntCoordinates(this.coordinates.x() + 1, this.coordinates.y()), this));
            }
        }

        return arr;
    }

    public IntCoordinates getCoordinates(){
        return this.coordinates;
    }

    public Noeud getParent() {
        return parent;
    }

    public int getCout(){
        return this.cout;
    }

    public void setCout(int cout){
        this.cout = cout;
    }

    public int getHeuristique(){
        return this.heuristique;
    }

    public void setHeuristique(int heuristique){
        this.heuristique = heuristique;
    }

    public int compareParHeuristique(Noeud n){
        if(n.heuristique == this.heuristique){
            return 0;
        } else {
            return n.heuristique < this.heuristique ? -1 : 1;
        }
    }

    public int manhattanDistance(Noeud n){
        return Math.abs(this.coordinates.x() - n.coordinates.x()) + Math.abs(this.coordinates.y() - n.coordinates.y());
    }

    public String toString(){
        return "" + this.coordinates.x() + ", " + this.coordinates.y();
    }

}

