package model;

public enum Direction { //choix de la direction
    NONE, NORTH, EAST, SOUTH, WEST;

    public String toString(){ 
        return switch (this) {
            case EAST -> "east";
            case WEST -> "west";
            case NORTH -> "north";
            case SOUTH -> "south";
            default -> "none";
        };
    }
}

