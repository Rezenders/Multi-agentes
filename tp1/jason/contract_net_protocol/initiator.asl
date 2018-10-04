// Agent sample_agent in project contract_net_protocol

/* Initial beliefs and rules */

/* Initial goals */

!cnp(1, book).
// !cnp(2, car).

/* Plans */

+!cnp(Id, Item) <-
    .broadcast(cfp, [Id, Item]);
    .print("Requesting ",Item," cnp id is ", Id);
    .wait(2000);
    .findall([O,A],propose(Id,O)[source(A)],L);
    L \== [];
    .min(L, [WO, WA]);
    !inform_results(Id, L, WA).

-!cnp(Id, Item)
    <- .print("CNP ",Id," has failed!").

+!inform_results(_,[],_).

+!inform_results(Id, [[O,A]|T], WA): A=WA
    <-  .send(A, accepted_proposal, Id);
        !inform_results(Id, T, WA).

+!inform_results(Id, [[O,A]|T], WA)
    <-  .send(A, refused_proposal, Id);
        !inform_results(Id, T, WA).

+!kqml_received(Sender, propose, Content, MsgId)
    <- +Content[source(Sender)].

 //ARRUMAR ISSO
+!kqml_received(Sender, refuse, Content, MsgId)
    <- .print("Request refused").
