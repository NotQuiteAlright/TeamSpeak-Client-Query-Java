package io.nqa.teamspeak.query.client.model.inbound;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class ClientChatClosed extends TeamSpeakNotifyModel {
    /** Client ID */
    public Integer clid;
    /** Client Unique ID */
    public String cluid;
}
