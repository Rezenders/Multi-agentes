
//Items avaiable
item(book, 3).

+!kqml_received(Sender, cfp, Content, MsgId) : .nth(1, Content, Item) & item(Item, Qt) & Qt > 0
    <-  .random(R);
        Price =  R*100;
        .nth(0, Content, Id);
        +propose(Id, Item, Price);
        .send(Sender, propose, propose(Id,Price), MsgId ).

+!kqml_received(Sender, cfp, Content, MsgId)
    <-  .nth(0, Content, Id);
        .nth(1, Content, Item);
        .send(Sender, refuse, Id, MsgId ).

+!kqml_received(Sender, accepted_proposal, Content, MsgId)
    <-  ?propose(Content, Item, Price);
        ?item(Item,Qt);
        -+item(Item,Qt-1);
        .print("Won cnp with id ", Content, " sold ", Item, " for ", Price).

+!kqml_received(Sender, refused_proposal, Content, MsgId)
    <-  .print("My proposal for cnp ",Content, " was refused.").
