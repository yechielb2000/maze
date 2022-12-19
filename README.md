_Maze_
----

_The application generates a maze and opens a new thread to solve it, it calls the `runAlgorithm` function and it returns true or false depends if it found a solution or not.
and this is how we know if there is a solution to the maze._

_Here is the algorithms function :_
```js

private boolean runAlgorithm(Collection<Point> collection) {

   while (!collection.isEmpty()) {

            Point point;
            if(collection instanceof Queue) {
                point = ((Queue<Point>) collection).remove();
            }else {
                point = ((Stack<Point>) collection).pop();
            }
            
            int x = point.x, y = point.y;

            if(!this.visited[x][y]) {
                setSquareAsVisited(x, y, true);
            }             

            collection.addAll(findNeighbors(x, y));

            if(x == this.values.length - 1 && y == this.values.length - 1)
                return true;
        }

    return false;     
}
