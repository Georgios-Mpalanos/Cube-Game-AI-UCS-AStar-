# UCS-A* Cube Game
 This project highlights how UCS and A* algorithms differ in speed and the number of expansions they need.

The point of the game is to reach a final state in which all the given cubes are placed correctly.

PrioQueue.java is used to support the implementation of UCS(you can find the general algorithm on the internet).
PrioQueueA.java is used to support the implementation of A*.

A* : The heuristic used takes into account whether a given cube is in its final position and if the cubes 
below it are also placed correctly. In that case, the "score" of the current state increases.
