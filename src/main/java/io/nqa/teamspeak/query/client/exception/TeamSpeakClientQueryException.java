package io.nqa.teamspeak.query.client.exception;

public class TeamSpeakClientQueryException extends RuntimeException {
    public TeamSpeakClientQueryException(String message) {
        super(message);
    }
    public TeamSpeakClientQueryException(TeamSpeakError error) {
        super(String.valueOf(error));
    }
}
