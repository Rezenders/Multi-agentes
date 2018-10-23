+!focus(A) // goal sent by the auctioneer
   <- lookupArtifact(A,ToolId);
      focus(ToolId).

+price(Price)[artifact_id(AId)] : running("yes")[artifact_id(AId)]
	<- 	?item(Item)[artifact_id(AId)];
		!japanese_auction(Item, Price, AId).

+!japanese_auction(Item, Price, AId): not price_limit(Item, Limit)
    <-  .random(R);
        Limit=math.floor(R*11)+ 27;
		.print("My Limit is ", Limit);
        +price_limit(Item, Limit);
        !japanese_auction(Item, Price, AId).

+!japanese_auction(Item, Price, AId): price_limit(Item, Limit) & Price < Limit
    <-	.my_name(N);
		add_participant(N)[artifact_id(AId)].

-!japanese_auction(Item, Price, AId)
    <-	.my_name(N);
		remove_participant(N)[artifact_id(AId)].

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$jacamoJar/templates/org-obedient.asl") }
