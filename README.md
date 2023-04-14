# HomeworkThree
In Homework3, there are 500,000 gifts which are taken at random by one of the 4 threads and added to the list. The list is a linked list which uses the LazyList refrenced from the notes and the textbook. Then after adding the item to the list, the thread will always remove an element to write a thank you letter so it is always swapping between adding and removing  elements. The remove operation always removes the value next to the head, unless that value is the tail then nothing will be removed.

In Homework32, each thread generates 60 random numbers as each thread then generates 1 random number per minute then. Then each number is stored in a treeset and then a low and high list that shows the 5 lowest and 5 highest numbers. Then every ten minutes the highest number and the lowest number in the low and high list are taken and the diffrence is shown. 

Output:
Lowest: -99 -98 -97 -96 -95 
Highest: 65 66 67 69 70 
Ten Minute Interval: 169
