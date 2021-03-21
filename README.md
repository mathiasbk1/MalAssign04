# MalAssign04
We have used a simple augmentation of the regex provided in assignment 2, which is 

Example: A(B|C|D)*E

* A Login
* B List items
* C Edit item 
* D Delete item
* E Logout

We store all active sessions with an "ID" concatenation of system and instance integers. 
The program runs through the entire log and performs validation on every action. Upon reaching the end of the file it prints whatever sessions are still "open". 
