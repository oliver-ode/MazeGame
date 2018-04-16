import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazeGeneration{
    //Prims generation code

    /**
     * Generates a random grid. I am never going to comment the class so yeah...
     * @param grid
     * @return
     */
    public Grid prims(Grid grid) {
        int upMoves = 0;
        int downMoves = 0;
        int leftMoves = 0;
        int rightMoves = 0;
        boolean debug = false;
        Grid newGrid = grid;
        Random rand = new Random();
        int[] baseIndex;
        int baseCellObjectIndex;
        ArrayList<Cell> baseCells = new ArrayList<>();
        ArrayList<Cell> travelCells = new ArrayList<>();
        //Randomizes starting cell
        /**
        int y = rand.nextInt(grid.getGrid().length);
        int x = rand.nextInt(grid.getGrid()[0].length);
        baseCells.add(newGrid.getGrid()[y][x]);
        newGrid.getGrid()[y][x].makeVisited();
         newGrid.getGrid()[0][0].wallDownRemove();
         newGrid.getGrid()[1][0].wallUpRemove();
         newGrid.getGrid()[0][0].wallRightRemove();
         newGrid.getGrid()[0][1].wallLeftRemove();
         **/
        //Makes starting cell in middle
        /**
        int y = newGrid.getHeight()/2;
        int x = newGrid.getWidth()/2;
        baseCells.add(newGrid.getGrid()[y][x]);
        newGrid.getGrid()[y][x].makeVisited();
         **/
        //Makes starting cell 0,0
        ///**
        baseCells.add(newGrid.getGrid()[grid.getHeight()-1][grid.getWidth()-1]);
        newGrid.getGrid()[grid.getHeight()-1][grid.getWidth()-1].makeVisited();
        // **/
        //While there still are cells that we can travel from
        while(baseCells.size() > 0){
            Collections.shuffle(baseCells);
            baseCellObjectIndex = rand.nextInt(baseCells.size());
            baseIndex = newGrid.getIndex(baseCells.get(baseCellObjectIndex));
            try{
                //Check right
                if(!newGrid.getGrid()[baseIndex[0]][baseIndex[1]+1].getVisited() && baseIndex[1] < newGrid.getGrid()[0].length-1){
                    travelCells.add(newGrid.getGrid()[baseIndex[0]][baseIndex[1]+1]);
                    if(debug)System.out.print("Right good, ");
                }
            }
            catch (Exception e){}
            try{
                //Check left
                if(!newGrid.getGrid()[baseIndex[0]][baseIndex[1]-1].getVisited() && baseIndex[1] > 0){
                    travelCells.add(newGrid.getGrid()[baseIndex[0]][baseIndex[1]-1]);
                    if(debug)System.out.print("Left good, ");
                }
            }
            catch (Exception e){}
            try{
                //Check up
                if(!newGrid.getGrid()[baseIndex[0]-1][baseIndex[1]].getVisited() && baseIndex[0] > 0){
                    travelCells.add(newGrid.getGrid()[baseIndex[0]-1][baseIndex[1]]);
                    if(debug)System.out.print("Up good, ");
                }
            }
            catch (Exception e){}
            try{
                //Check down
                if(!newGrid.getGrid()[baseIndex[0]+1][baseIndex[1]].getVisited() && baseIndex[1] < newGrid.getGrid().length-1){
                    travelCells.add(newGrid.getGrid()[baseIndex[0]+1][baseIndex[1]]);
                    if(debug)System.out.print("Down good, ");
                }
            }
            catch (Exception e){}
            if(debug)System.out.print("\n");
            if(debug)System.out.println(travelCells.size() + " is the size of travelCells!!!");
            Cell directionCell;
            int[] travelCellIndex;

            if(travelCells.size() > 0){
                directionCell = travelCells.get(rand.nextInt(travelCells.size()));
                travelCellIndex = newGrid.getIndex(directionCell);

                if(debug)System.out.println(travelCellIndex[1] + " : " + travelCellIndex[0] + " --> Printed in [x, y]");
                //diff = {yDiff, xDiff} Positive means right and down
                int[] diff = {baseIndex[0] - travelCellIndex[0], baseIndex[1] - travelCellIndex[1]};
                newGrid.getGrid()[travelCellIndex[0]][travelCellIndex[1]].makeVisited();
                baseCells.add(newGrid.getGrid()[travelCellIndex[0]][travelCellIndex[1]]);
                //Y movement
                if(diff[0] != 0){
                    //Moved down
                    if(diff[0] > 0){
                        if(debug)System.out.println("Moved up");
                        try{
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]].wallUpRemove();
                            newGrid.getGrid()[baseIndex[0]-1][baseIndex[1]].wallDownRemove();
                            downMoves ++;
                        }
                        catch (Exception e){
                            if(debug)System.out.println("NullPointer - You would have been out of bounds!!!");
                        }
                    }
                    //Moved up
                    else {
                        if(debug)System.out.println("Moved down");
                        try{
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]].wallDownRemove();
                            newGrid.getGrid()[baseIndex[0]+1][baseIndex[1]].wallUpRemove();
                            upMoves ++;
                        }
                        catch (Exception e){
                            if(debug)System.out.println("NullPointer - You would have been out of bounds!!!");
                        }
                    }
                }
                //X movement
                else if (diff[1] != 0){
                    //Moved left
                    if(diff[1] > 0){
                        if(debug)System.out.println("Moved left");
                        try{
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]].wallLeftRemove();
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]-1].wallRightRemove();
                            leftMoves ++;
                        }
                        catch (Exception e){
                            if(debug)System.out.println("NullPointer - You would have been out of bounds!!!");
                        }
                    }
                    //Moved right
                    else {
                        if(debug)System.out.println("Moved right");
                        try {
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]].wallRightRemove();
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]+1].wallLeftRemove();
                            rightMoves ++;
                        }
                        catch (Exception e){
                            if(debug)System.out.println("NullPointer - You would have been out of bounds!!!");
                        }
                    }
                }
                //Resets
                boolean aOk = false;
                travelCells.clear();
                for(int i = 0; i < baseCells.size(); i++){
                    baseIndex = newGrid.getIndex(baseCells.get(i));
                    try{
                        if(!newGrid.getGrid()[baseIndex[0]][baseIndex[1]+1].getVisited()) aOk = true;
                    }
                    catch (Exception e){
                        //Do nothing
                    }
                    try{
                        if(!newGrid.getGrid()[baseIndex[0]][baseIndex[1]-1].getVisited()) aOk = true;
                    }
                    catch (Exception e){
                        //Do nothing
                    }
                    try{
                        if(!newGrid.getGrid()[baseIndex[0]+1][baseIndex[1]].getVisited()) aOk = true;
                    }
                    catch (Exception e){
                        //Do nothing
                    }
                    try{
                        if(!newGrid.getGrid()[baseIndex[0]-1][baseIndex[1]].getVisited()) aOk = true;
                    }
                    catch (Exception e){
                        //Do nothing
                    }
                    if(!aOk) baseCells.remove(baseCells.get(i));
                    aOk = false;
                }
                if(debug)System.out.println("----------------------------------------");
            }
            else{
                baseCells.remove(newGrid.getGrid()[baseIndex[0]][baseIndex[1]]);
            }

        }
        if(debug) {
            System.out.println(upMoves);
            System.out.println(rightMoves);
            System.out.println(downMoves);
            System.out.println(leftMoves);
        }
        return newGrid;
    }
}
