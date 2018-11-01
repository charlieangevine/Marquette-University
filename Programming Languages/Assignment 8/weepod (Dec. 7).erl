-module(weepod).
-export([start/0, send_wait/2, shop/2]).

%%
% WeePod
%
% written by Jack Pfeiffer, dec 2017
% 
% Added receipt display for checkout.
% Added some error checking: 
% - If adding an item to a shelf that already exists, telling the user that it is being overwritten.
% - If choosing an item that is not on shelves, log error message that it is not available.
% - If trying to remove an item that is not in your cart, send an error message to the user.
% - If the user tries to checkout or show cart, tells the user there is nothing in cart.
%%

% Start up the server
start() -> spawn(fun init/0).

% Basic Request
shop(Pid, Request) ->
    send_wait(Pid, Request).

init() ->
    put({item, lettuce}, 1.50),
    put({item, cupcake}, 0.75),
    put({item, soup}, 2.35),
    put(cart, []),
    loop().

f(N) -> % truncates floating point numbers to 2 decimal places
    lists:concat([ trunc(N), ".", (trunc(N*100) - (trunc(N) * 100)) ]).

add_items(Name, Count, []) -> [{Name, Count}];
add_items(Name, Count, [{Name, C}|L]) -> [{Name, C+Count}|L];
add_items(Name, Count, [H|L]) -> [H|add_items(Name, Count, L)].

rem_item(_, []) -> [];
rem_item(Name, [{Name, _}]) -> [];
rem_item(Name, [{Name, _}|L]) -> [L];
rem_item(Name, [H|L]) -> [H|rem_item(Name, L)].

% adds up the cart by multiplying the cost and quantity of each item in the cart
checkout([]) -> 0;
checkout([{Name, C}]) -> 
    io:fwrite(lists:concat(["  ", Name, "  (", C, " x ", f(get({item, Name})), ")\t\t $", f(get({item, Name}) * C), "\n"])),  % Writes out the checkout like a recipt
    (get({item, Name}) * C);
checkout([{Name, C}|L]) -> (get({item, Name}) * C) + checkout(L).

shelf_list() ->
    K = get_keys(),
    [{N, get(E)} || {item, N}=E <- K].

in_cart(_,[]) -> false; % returns wether item is in cart
in_cart(Name,[{Name, _}|_]) -> true;
in_cart(Name,[_|L]) -> in_cart(Name, L).

is_member(_, []) -> false;
is_member(A, [A|_]) -> true;
is_member(A, [_|L]) -> is_member(A, L).

% core server function
loop() ->
    receive
        {From, show_shelves} ->
            From ! {self(), shelf_list()}, 
            loop();
        
        {From, {add_item, Name, Price}} ->
            E = is_member({item, Name}, get_keys()),
            put({item, Name}, Price),
            if
                E ->
                    From ! {self(), lists:concat(["Overwrote ", Name, " with new price ", f(Price)])};
                true ->
                    From ! {self(), ok}
            end,
            loop();
        
        {From, {choose, Name, Count}} ->
            E = is_member({item, Name}, get_keys()),
            if
                E ->
                    put(cart, add_items(Name, Count, get(cart))),
                    From ! {self(), ok};
                true ->
                    From ! {self(), {error, lists:concat([Name, " is not for sale!"])}}
            end,
            loop();
        
        {From, {remove, Name}} ->
            E = in_cart(Name, get(cart)), % define the if condition before the test case, otherwise there would be illegal guard error
            if
                E ->
                    put(cart, rem_item(Name, get(cart))),
                    From ! {self(), ok};
                true ->
                    From ! {self(), {error, lists:concat([Name, " is not in your cart"])}}
            end,
            loop();
        {From, show_cart} ->
            E = get(cart) == [],
            if
                E ->
                    io:fwrite("Your list is empty! \n"),
                    From ! {self(), ok};
                true ->
                    From ! {self(), get(cart)}
            end,
            loop();
        
        {From, checkout} ->
            E = get(cart) == [],
            if
                E ->
                    From ! {self(), {error, "There are no items in your cart"}};
                true ->
                    io:fwrite(lists:concat(["  TOTAL: \t\t\t $", f(checkout(get(cart))), "\n"])),
                    From ! {self(), ok}
            end,
            loop();
        
        {From, Other} ->
            From ! {self(), {error, Other}}, 
            loop()
    end.

% core client function
send_wait(Pid, Request) ->
    Pid ! {self(), Request},
    receive
        {Pid, Response} -> Response
    end.