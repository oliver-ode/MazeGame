import java.util.ArrayList;
import java.util.Random;

public class MazeGeneration{
    //Prims generation code
    public Grid prims(Grid grid) {
        Grid newGrid = grid;
        Random rand = new Random();
        int[] baseIndex;
        int baseCellObjectIndex;
        ArrayList<Cell> baseCells = new ArrayList<>();
        ArrayList<Cell> travelCells = new ArrayList<>();
        baseCells.add(newGrid.getGrid()[0][0]);
        newGrid.getGrid()[0][0].makeVisited();
        //While there still are cells that we can travel from
        while(baseCells.size() > 0){
            baseCellObjectIndex = rand.nextInt(baseCells.size());
            baseIndex = newGrid.getIndex(baseCells.get(baseCellObjectIndex));
            try{
                //Check right
                if(!newGrid.getGrid()[baseIndex[0]][baseIndex[1]+1].getVisited() && baseIndex[1] < newGrid.getGrid()[0].length-1){
                    travelCells.add(newGrid.getGrid()[baseIndex[0]][baseIndex[1]+1]);
                    System.out.print("Right good, ");
                }
            }
            catch (Exception e){}
            try{
                //Check left
                if(!newGrid.getGrid()[baseIndex[0]][baseIndex[1]-1].getVisited() && baseIndex[1] > 0){
                    travelCells.add(newGrid.getGrid()[baseIndex[0]][baseIndex[1]-1]);
                    System.out.print("Left good, ");
                }
            }
            catch (Exception e){}
            try{
                //Check up
                if(!newGrid.getGrid()[baseIndex[0]-1][baseIndex[1]].getVisited() && baseIndex[0] > 0){
                    travelCells.add(newGrid.getGrid()[baseIndex[0]-1][baseIndex[1]]);
                    System.out.print("Up good, ");
                }
            }
            catch (Exception e){}
            try{
                //Check down
                if(!newGrid.getGrid()[baseIndex[0]+1][baseIndex[1]].getVisited() && baseIndex[1] < newGrid.getGrid().length-1){
                    travelCells.add(newGrid.getGrid()[baseIndex[0]+1][baseIndex[1]]);
                    System.out.print("Down good, ");
                }
            }
            catch (Exception e){}
            System.out.print("\n");
            System.out.println(travelCells.size() + " is the size of travelCells!!!");
            Cell directionCell;
            int[] travelCellIndex;

            if(travelCells.size() > 0){
                directionCell = travelCells.get(rand.nextInt(travelCells.size()));
                travelCellIndex = newGrid.getIndex(directionCell);

                System.out.println(travelCellIndex[1] + " : " + travelCellIndex[0] + " --> Printed in [x, y]");
                //diff = {yDiff, xDiff} Positive means right and down
                int[] diff = {baseIndex[0] - travelCellIndex[0], baseIndex[1] - travelCellIndex[1]};
                newGrid.getGrid()[travelCellIndex[0]][travelCellIndex[1]].makeVisited();
                baseCells.add(newGrid.getGrid()[travelCellIndex[0]][travelCellIndex[1]]);
                //Y movement
                if(diff[0] != 0){
                    //Moved down
                    if(diff[0] > 0){
                        System.out.println("Moved up");
                        try{
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]].wallUpRemove();
                            newGrid.getGrid()[baseIndex[0]-1][baseIndex[1]].wallDownRemove();
                        }
                        catch (Exception e){
                            System.out.println("NullPointer - You would have been out of bounds!!!");
                        }
                    }
                    //Moved up
                    else {
                        System.out.println("Moved down");
                        try{
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]].wallDownRemove();
                            newGrid.getGrid()[baseIndex[0]+1][baseIndex[1]].wallUpRemove();
                        }
                        catch (Exception e){
                            System.out.println("NullPointer - You would have been out of bounds!!!");
                        }
                    }
                }
                //X movement
                else if (diff[1] != 0){
                    //Moved left
                    if(diff[1] > 0){
                        System.out.println("Moved left");
                        try{
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]].wallLeftRemove();
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]-1].wallRightRemove();
                        }
                        catch (Exception e){
                            System.out.println("NullPointer - You would have been out of bounds!!!");
                        }
                    }
                    //Moved right
                    else {
                        System.out.println("Moved right");
                        try {
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]].wallRightRemove();
                            newGrid.getGrid()[baseIndex[0]][baseIndex[1]+1].wallLeftRemove();
                        }
                        catch (Exception e){
                            System.out.println("NullPointer - You would have been out of bounds!!!");
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
                System.out.println("----------------------------------------");
            }
            else{
                baseCells.remove(newGrid.getGrid()[baseIndex[0]][baseIndex[1]]);
            }

        }
        return newGrid;
    }
}
