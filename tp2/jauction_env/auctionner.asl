!start_auction(katanaArtifact,"katana", 30).

+!start_auction(Id,Item, Price)
   <-   makeArtifact(Id, "jauction_env.JAuctionArtifact", [], ArtId);
        .print("Auction artifact created for ",Item);
        Id::focus(ArtId);  // place observable properties of this auction in a particular name space
        Id::start(Item, Price);
        .broadcast(achieve,focus(Id));
        .at("now + 2 seconds",{+!conduct_auction(Id)}).

+!conduct_auction(Id)
    <-  ?Id::item(Item);
        ?Id::price(Price);
        .print("Auctioning ",Item ," for ", Price, " moneys");
        Id::check_room;
        !detect_winner(Id).

+!detect_winner(Id): Id::winner(W)
    <- .print(W," won the auction").

+!detect_winner(Id): Id::draw
    <- .print("Everyone left, nobody won the auction!").

+!detect_winner(Id)
    <-  Id::update_price;
        .wait(2000);
        !conduct_auction(Id).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$jacamoJar/templates/org-obedient.asl") }
