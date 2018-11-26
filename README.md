# Breakout_Mini_Game_NC

1)Game has buttom for toggling sound, but no sound present in game.
2)Collision detection for bricks and Paddle a bit rusty, didn't had time to implement robust capsule-circle collision for pedle, and special type of recursion for bricks.
3)Game possesses 3 types of bricks: green=1hit to destroy ,  yellow=2, red=3.
4)Instead of drawing everything on 1 Bitmap to scale everytinh on canvas, I decided to draw everyting on canvas immediatly for a bit better visual result. To save resolution, percentages of space were used instead of pixels. This was VERY bad idea, this took a lot more work and result is minimal or almost invisible.
5)PowerUps for paddle were in progress, but time demanded to cut this feature.
6)Game possesses asynchronization with 2Threads(before start of level, player can see how bricks are generated), in practise this doesn't effect gameplay, because most of the phones have computational power to proccess bricks before first collision  of Brick-Ball is made.
7)Game wasn't tested a lot, so there will be some bugs.
8)All "assests" are loaded together. Result= no loading time. My prediction were true that final game will consume not so much RAM.
9)That are all notes. The biggest personal problem was lack of time. It was impossible to make all game modules perfect, so some parts are "rusty".
