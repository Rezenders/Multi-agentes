angent_type(participant).

//Items avaiable
// item(book, 10).

+!kqml_received(Sender, cfp, Content, MsgId) : .nth(1, Content, Item) & item(Item, Qt) & Qt > 0
    <-  .random(R);
        Price =  R*100;
        .nth(0, Content, Id);
        +propose(Sender, Id, Item, Price);
        .send(Sender, propose, propose(Id,Price), MsgId ).

+!kqml_received(Sender, cfp, Content, MsgId)
    <-
        .send(Sender, refuse, Content, MsgId ).

+!kqml_received(Sender, accepted_proposal, Content, MsgId) : propose(Sender, Content, Item, Price) & item(Item,Qt) & Qt >0
    <-
        -+item(Item,Qt-1);
        .print("When negotiating with ",Sender, " I won cnp with id ", Content, " sold ", Item, " for ", Price).

+!kqml_received(Sender, accepted_proposal, Content, MsgId)
    <-  ?propose(Sender, Content, Item, Price);
        .print("When negotiating with ",Sender, " I won cnp with id ", Content, " but I no longer have ", Item, " in stock!").

+!kqml_received(Sender, refused_proposal, Content, MsgId)
    <-  .print("When negotiating with ",Sender," my proposal for cnp ",Content, " was refused.").
