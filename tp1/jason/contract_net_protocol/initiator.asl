// Agent sample_agent in project contract_net_protocol

/* Initial beliefs and rules */

/* Initial goals */

!cnp(book).

/* Plans */

+!cnp(Item) <- .broadcast(cfp,Item).

+!kqml_received(Sender, propose, Content, MsgId)
    <- .print("Sender: ", Sender, " Content: ", Content, " MsgId ", MsgId).
