_Maze_
----

_The application generates a maze and opens a new thread to solve it, it calls the `runAlgorithm` function and if it finds a solution it returns true, and if not it returns flase.
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

```

<h1 align="center">Here is an example for DFS algorithm !!</h1>

<img src="https://user-images.githubusercontent.com/51932344/176753442-423826ab-5176-49a4-9531-f7104048ba79.png">

