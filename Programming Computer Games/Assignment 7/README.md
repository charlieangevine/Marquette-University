# COSC 3550 - Computer Games
### Assignment #7 - Grab - A Networked Game

This assignment involves the Grab client and server discussed in class (and available in the Grab folder of the demos page). I would suggest that you work in teams of two, with each team handing in one assignment with both names. This will make it much easier to test the networking features. However, if you wish to work alone, that is OK.

**GOAL**: Modify the Grab game to add some game features. In particular, you should add a "grab" command, issued by pressing "G" on the keyboard, which takes a coin which you are standing next to and facing (and does nothing if there is no such coin) and a "blast" command, issued by pressing "B" on the keyboard, which takes a block which you are standing next to and facing and removes the block from the board. Each player starts the game with 4 sticks of dynamite and uses one for each blast, so there should be a counter for each player making sure they don't blast more than 4 blocks. In each case these moves need to be sent to the server to check for legality and then reported back to both clients (so the displays stay synchronized). You should also display the scores (number of coins collected) for each player.

You should also add sound effects which are played by the client whenever a coin is grabbed or a block is blasted. If you have the time, you could put in different sounds depending on whether you or your opponent grabbed a coin.

**METHOD**: The main thing you need to do here is understand how the demo game works and add in some new messages between the clients and the server. You'll need to be able to tell the server that a player is trying to grab or blast something and the server will need to be able to tell the players that a coin is gone, score has changed, or a block is gone. Of course, each of the server and client will need to be changed to correctly react to the new messages.

If you're having trouble understanding the basic networking, remember that the demos folder NetDemo contains the stripped down client/server demo that we discussed in class.

Remember that to run the demos (either Grab or NetDemo), you need to start the server (with the command "java GameServer"), and then start two clients ("java Grab"). Currently, the server will stop when the clients are closed, so you need to run the server every time.

**EXTRA**: Anything to improve the game. Things that occurred to me include checking for game over when all coins are picked up, announcing a winner, allowing more than two players.