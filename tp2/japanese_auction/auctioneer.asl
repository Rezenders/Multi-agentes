/* Initial beliefs and rules */

/* Initial goals */

!conduct_auction(katana, 30).

/* Plans */

+!conduct_auction(Item, Price)
	<- 	.print("Auctioning ",Item," for ", Price, " moneys")
		.broadcast(achieve, japanese_auction(Item, Price))
		.wait(2000);
		!check_participants(Item, P);
		!detect_winner(Item, Price, P).

@plan[atomic]
+!check_participants(Item, P)
	<- .findall(Name,participating(Item)[source(Name)],P).

+!detect_winner(Item, Price, P): .length(P,L) & L==0
	<- .print("Everyone left, nobody won the ",Item," auction!").

+!detect_winner(Item, Price, P): .length(P,L) & L==1
	<- 	.nth(0, P, Winner);
		.print(Winner," won the ", Item ," auction for ", Price, " moneys!").

+!detect_winner(Item, Price, P)
	<-	!conduct_auction(Item, Price+1).
