package io.nqa.teamspeak.query.client.model.inbound;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class FileListFinished extends TeamSpeakNotifyModel {
    public Integer cid;
    public String path;
}
