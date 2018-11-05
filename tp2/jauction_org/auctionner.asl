!do_auction(katanaArtifact,"katana", 30).

+!do_auction(Id,Item, Price)
   <- // creates a scheme to coordinate the auction
      .concat("sch_",Id,SchName);
      makeArtifact(SchName, "ora4mas.nopl.SchemeBoard",["src/org/jauction-os.xml", doAuction],SchArtId);
      debug(inspector_gui(on))[artifact_id(SchArtId)];
      setArgumentValue(jauction,"Id",Id)[artifact_id(SchArtId)];
      setArgumentValue(jauction,"Item",Item)[artifact_id(SchArtId)];
      setArgumentValue(jauction,"InitValue",Price)[artifact_id(SchArtId)];
      .my_name(Me); setOwner(Me)[artifact_id(SchArtId)];  // I am the owner of this scheme!
      focus(SchArtId);
      addScheme(SchName);  // set the group as responsible for the scheme
      commitMission(mAuctioneer)[artifact_id(SchArtId)].

+!start_auction[scheme(Sch)]
    <-  ?goalArgument(Sch,jauction,"Id",Id);
        ?goalArgument(Sch,jauction,"Item",Item);
        ?goalArgument(Sch,jauction,"InitValue",Price);
        .print("Scheme ",Sch, " created for ", Item);
        makeArtifact(Id, "jauction_env.JAuctionArtifact", [], ArtId);
        .print("Auction artifact created for ",Item);
        Sch::focus(ArtId);  // place observable properties of this auction in a particular name space
        Sch::start(Item, Price);
        .

+!conduct_auction[scheme(Sch)]
    <-  ?Sch::item(Item);
        ?Sch::price(Price);
        .print("Auctioning ",Item ," for ", Price, " moneys");
        Sch::check_room;
        !detect_winner(Sch).

+!detect_winner(Sch): Sch::winner(W) // Ver se da pra tirar o argumento Sch
    <- .print(W," won the auction").

+!detect_winner(Sch): Sch::draw
    <- .print("Everyone left, nobody won the auction!").

+!detect_winner(Sch)
    <-  Sch::update_price;
        .wait(2000);
        !conduct_auction[scheme(Sch)].

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$jacamoJar/templates/org-obedient.asl") }
