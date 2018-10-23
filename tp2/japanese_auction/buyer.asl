
+!japanese_auction(Item, Price): not price_limit(Item, Limit)
    <-  .random(R);
        Limit=math.floor(R*11)+ 27;
        .print("My Limit is ", Limit);
        +price_limit(Item, Limit);
        !japanese_auction(Item, Price).


+!japanese_auction(Item, Price): price_limit(Item, Limit) & Price < Limit
    <-  .send(auctioneer, tell, participating(Item)).

-!japanese_auction(Item, Price)
    <-  .send(auctioneer, untell, participating(Item)).
        // .print("I won't join the auction for ", Item ).
