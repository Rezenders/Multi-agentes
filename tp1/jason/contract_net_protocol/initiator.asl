// Agent sample_agent in project contract_net_protocol

/* Initial beliefs and rules */
angent_type(initiator).

/* Initial goals */
!cnp(1, book).
!cnp(2, car).
!cnp(3, bike).
!cnp(4, paper).
!cnp(5, mustard).


/* Plans */
+!cnp(Id, Item) <-
    .broadcast(cfp, [Id, Item]);
    .print("Requesting ",Item," cnp id is ", Id);
    .wait(2000);
    !choose_proposal(Id,L);
    .min(L, [WO, WA]);
    !inform_results(Id, L, WA).

-!cnp(Id, Item)
    <- .print("CNP ", Id, " for ", Item, " has failed!").

@choose_proposal[atomic]
+!choose_proposal(Id,L)
    <-
      .findall([O,A],propose(Id,O)[source(A)],L);
      L \== [].

+!inform_results(_,[],_).

+!inform_results(Id, [[O,A]|T], WA): A=WA
    <-  .send(A, accepted_proposal, Id);
        !inform_results(Id, T, WA).

+!inform_results(Id, [[O,A]|T], WA)
    <-  .send(A, refused_proposal, Id);
        !inform_results(Id, T, WA).

+!kqml_received(Sender, propose, Content, MsgId)
    <- +Content[source(Sender)].

+!kqml_received(Sender, refuse, Content, MsgId)
    <-
      .nth(0, Content, Id);
      .nth(1, Content, Item);
      .print("Agent ", Sender, " refused request for ", Item).

+!kqml_received(Sender, cfp, Content, MsgId).
