# COSC 3550 - Computer Games
### Assignment #3

**GOAL**: Write a game which allows your Roomba to sweep up the Great Dust Bunny Uprising. The goal is to collect all the dust bunnies in the shortest time possible.

[](http://www.mscs.mu.edu/~mikes/cosc3550/GDBU-pic.png)

There should be an array of twelve round "bunnies" moving about the screen with [randomly chosen] fixed velocity vectors. Bunnies that move off one edge of the screen should come back in on the opposite edge [wrap-around effect].

Your roomba moves forward at a fixed speed. You can control turning the machine left or right [with two keyboard keys]. The [round] roomba should have a small round "headlight" on its leading edge so you can see which way it's headed. The roomba does *not* wrap around edges, but rather runs into the edge of the screen. It will get stuck at an edge until you turn enough to be facing away from the edge.

The vacuum is powerful enough that as soon as you touch the edge of a bunny it gets sucked up and disappears from the screen. A timer should display how many seconds have elapsed from the beginning of the game until all the bunnies are gone.

You'll need to check for collisions between the roomba and the bunnies, but you don't need to worry about the bunnies running into each other [it's OK if they cross over each other].

Here's some idea what the game might look like while being played. The yellow arrow is just to show the direction of the roomba, it wouldn't appear on the screen.


**METHOD**: Start small. Maybe just have one dust bunny moving across the screen and work on getting the wrap-around correct.

Then, you might add in the roomba, but don't worry about the collisions with the dust bunnies. Focus on getting the controls to behave correctly. The keys should *turn* the direction of the roomba, *not* move it in that direction. Holding down the "left" key should make the roomba circle to the left, *not* move left across the screen. There's no forward or backward key - the roomba just always moves in the direction it's facing [unless it has run into an edge of the applet]. You'll need to keep track of the x and y coordinates of the roomba, but also which direction it's facing. The easiest way to handle the direction is to keep an angle which you add to or subtract from to turn. If your currect direction is dir_angle and your speed is speed then you can compute a velocity with:
```
  dx = speed * Math.cos(dir_angle);
  dy = speed * Math.sin(dir_angle);
  ```
Once all that's working, then you can work on more bunnies, collision testing, the timer, etc.

**EXTRAS**: Here are a few ideas I had for extra effort:

* have the dust bunnies bounce off of each other [i.e. can't overlap]
* do a fancy wrap-around where the bunny appears partially on one edge and partially on the opposite edge as it's wrapping [rather than just disappearing from one side and reappearing on the other]
* add in new bunnies after a certain amount of time [if the player hasn't won by then]
* have "super vacuum" power-ups that allow the roomba to suck in bunnies that are "close" even if they aren't touching.
* have the bunnies [or some of them] try to avoid the roomba