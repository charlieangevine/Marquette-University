# COSC 3410 - Erlang
#### Assignment #8

**GOAL:** A new company with an innovative concept is planning to allow customers to order groceries online and have them delivered by a truck. They decide to call the service "WeePod". Your goal is write an Erlang server to help run this service.

The server should maintain a list of products for sale, and a customer shopping cart. Commands should include:
```
  show_shelves
      Returns a list of {=item name=, =price=} pairs.

  {add_item, =item name=, =price=}
      Puts new item on shelve (or changes price for
      existing item).

  {choose, =item name=, =count=}
      Places =count= many =item name='s in your shopping cart.

  {remove, =item name=}
      Removes all =item name='s (if any) from your shopping cart.

  show_basket
      Returns a list of {=item name=, =count=} pairs.

  checkout
      Returns total price of items in your shopping cart.
```      
A server that just handles show_shelves and choose can be found in weepod_alpha.erl.

In addition to the code needed for the server, you should provide a simple function to send messages to the server (like "volume" in the solids servers).

**HAND-IN:** Your Erlang source code (the .erl files). Be sure to mention any special features you'd like me to notice in comments or a separate file. Remember, each .erl file should include a comment saying who wrote it and why.