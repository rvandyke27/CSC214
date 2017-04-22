
Rebecca Van Dyke
rvandyke@u.rochester.edu
CSC 214 Assignment 9
TA: Julian Weiss

The purpose of this assignment was to practice using Asynctasks, Handlers, HandlerThreads, and Loopers.

1. All AsyncTasks run in the same background thread, so the task to calculate the square root is not executed until the task to calculate the largest prime is done. At first nothing happens, but then after the largest prime is calculated and the toast fades, the square root toast immediately appears.

2. In contrast to part 1, each HandlerThread represents its own thread so when calculating the largest prime for a very large number, the square root can be calculated more quickly in its own parallel thread. While the largest prime is being calculated, the square root will display in a toast (almost immediately) and in the textview until the largest prime is calculated.

I would like my implementation of hierarchical navigation to be considered for extra credit.