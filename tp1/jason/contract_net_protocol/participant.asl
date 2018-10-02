
//Items avaiable
item(book, 33, 3).

+happy(X) : true <- .print("Happy",X).

+want(Qt, Srv) <- .print("Want ", Qt, " ", Srv, "").


+!kqml_received(Sender, cfp, Content, MsgId) : item(Content, Price, Qt) & Qt > 0
    <-  .send(Sender, propose, Price, MsgId );
        .print("Sender: ", Sender, " Content: ", Content, " MsgId ", MsgId).
