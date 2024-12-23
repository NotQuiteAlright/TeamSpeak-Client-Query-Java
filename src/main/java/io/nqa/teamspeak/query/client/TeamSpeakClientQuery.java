package io.nqa.teamspeak.query.client;

import io.nqa.teamspeak.query.client.event.*;
import io.nqa.teamspeak.query.client.exception.TeamSpeakClientQueryException;
import io.nqa.teamspeak.query.client.exception.TeamSpeakError;
import io.nqa.teamspeak.query.client.model.*;
import io.nqa.teamspeak.query.client.model.inbound.*;
import io.nqa.teamspeak.query.client.model.inbound.ClientPoke;
import io.nqa.teamspeak.query.client.model.outbound.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.*;

/**
 * TeamSpeak Client Query<p>
 * Cast events coming from TeamSpeak client.<p>
 * Requires {@link org.springframework.context.event.ApplicationEventMulticaster}
 * in constructor.
 */
@EnableAsync
public class TeamSpeakClientQuery implements Runnable {
    private final ApplicationEventMulticaster eventMulticaster;
//    private final String address;
//    private final int port;
//    private final String apiKey;

    private final Socket client;
    private final PrintWriter input;
    private final BufferedReader output;
    private String message;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Getter
    private int handlerId = 0;  // Server connection handler ID

    public void setHandlerId(int handlerId) {
        this.handlerId = handlerId;
        use(handlerId);
    }

    public TeamSpeakClientQuery(String address, int port, String apiKey, ApplicationEventMulticaster multicaster) throws IOException {
        try {
            this.eventMulticaster = multicaster;
            this.client = new Socket(address, port);
            this.input = new PrintWriter(client.getOutputStream(), true);
            this.output = new BufferedReader(new InputStreamReader(client.getInputStream()));
            auth(apiKey);
            clientNotifyRegister();
        } catch (ConnectException e) {
            throw new TeamSpeakClientQueryException(TeamSpeakError.CONNECTION_REFUSED);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                message = output.readLine();
//                System.out.println(message);
                if (message == null) {
                    // given when closing client
                    throw new TeamSpeakClientQueryException(TeamSpeakError.CONNECTION_LOST);
                }
                if (!message.isBlank()) processMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process incoming message.
     *
     * @throws TeamSpeakClientQueryException sda
     */
    private void processMessage() throws TeamSpeakClientQueryException {
        // Ignore
        if (message.isBlank() ||
                message.equalsIgnoreCase("TS3 Client") ||
                message.startsWith("Welcome") ||
                message.startsWith("Use the")) return;
        if (next().equalsIgnoreCase("selected"))
            return; // schandler was selected
        logger.info("Received: {}", message);
        if (next().startsWith("notify") ||
                next().equalsIgnoreCase("channellist") ||
                next().equalsIgnoreCase("channellistfinished")) {
            NotifyEvent notifyEvent = NotifyEvent.from(next());
            if (notifyEvent == null) {
                logger.error("Event {} not found", next());
                return;
            }
            updateMessage();
            switch (notifyEvent) {
                case NOTIFY_TALK_STATUS_CHANGE -> {
                    TalkStatusChange talkStatus = new TalkStatusChange();
                    setObjectVariables(talkStatus);
                    eventMulticaster.multicastEvent(new OnTalkStatusChange(talkStatus));
                }
                case NOTIFY_MESSAGE -> {
                    Message msg = new Message();
                    setObjectVariables(msg);
                    eventMulticaster.multicastEvent(new OnMessage(msg));
                }
                case NOTIFY_MESSAGE_LIST -> {
                    MessageList messageList = new MessageList();
                    setObjectVariables(messageList);
                    eventMulticaster.multicastEvent(new OnMessageList(messageList));
                }
                case NOTIFY_COMPLAIN_LIST -> System.out.println("unimplemented " + notifyEvent);
                case NOTIFY_BAN_LIST -> {
                    BanList banList = new BanList();
                    setObjectVariables(banList);
                    eventMulticaster.multicastEvent(new OnBanList(banList));
                }
                case NOTIFY_CLIENT_MOVED -> {
                    ClientMoved clientMoved = new ClientMoved();
                    setObjectVariables(clientMoved);
                    eventMulticaster.multicastEvent(new OnClientMoved(clientMoved));
                }
                case NOTIFY_CLIENT_LEFT_VIEW -> {
                    ClientLeftView clientLeftView = new ClientLeftView();
                    setObjectVariables(clientLeftView);
                    eventMulticaster.multicastEvent(new OnClientLeftView(clientLeftView));
                }
                case NOTIFY_CLIENT_ENTER_VIEW -> {
                    ClientEnterView clientEnterView = new ClientEnterView();
                    setObjectVariables(clientEnterView);
                    eventMulticaster.multicastEvent(new OnClientEnterView(clientEnterView));
                }
                case NOTIFY_CLIENT_POKE -> {
                    ClientPoke clientPoke = new ClientPoke();
                    setObjectVariables(clientPoke);
                    eventMulticaster.multicastEvent(new OnClientPoke(clientPoke));
                }
                case NOTIFY_CLIENT_CHAT_CLOSED -> {
                    ClientChatClosed clientChatClosed = new ClientChatClosed();
                    setObjectVariables(clientChatClosed);
                    eventMulticaster.multicastEvent(new OnClientChatClosed(clientChatClosed));
                }
                case NOTIFY_CLIENT_CHAT_COMPOSING -> {
                    ClientChatComposing clientChatComposing = new ClientChatComposing();
                    setObjectVariables(clientChatComposing);
                    eventMulticaster.multicastEvent(new OnClientChatComposing(clientChatComposing));
                }
                case NOTIFY_CLIENT_UPDATED -> {
                    ClientUpdated clientUpdated = new ClientUpdated();
                    setObjectVariables(clientUpdated);
                    eventMulticaster.multicastEvent(new OnClientUpdated(clientUpdated));
                }
                case NOTIFY_CLIENT_IDS -> System.out.println("unimplemented " + notifyEvent);
                case NOTIFY_CLIENT_DBID_FROM_UID -> System.out.println("unimplemented " + notifyEvent);
                case NOTIFY_CLIENT_NAME_FROM_UID -> System.out.println("unimplemented " + notifyEvent);
                case NOTIFY_CLIENT_NAME_FROM_DBID -> System.out.println("unimplemented " + notifyEvent);
                case NOTIFY_CLIENT_UID_FROM_CLID -> System.out.println("unimplemented " + notifyEvent);
                case NOTIFY_CONNECTION_INFO -> {
                    ConnectionInfo connectionInfo = new ConnectionInfo();
                    setObjectVariables(connectionInfo);
                    eventMulticaster.multicastEvent(new OnConnectionInfo(connectionInfo));
                }
                case NOTIFY_CHANNEL_CREATED -> {
                    ChannelCreated channelCreated = new ChannelCreated();
                    setObjectVariables(channelCreated);
                    eventMulticaster.multicastEvent(new OnChannelCreated(channelCreated));
                }
                case NOTIFY_CHANNEL_EDITED -> {
                    ChannelEdited channelEdited = new ChannelEdited();
                    setObjectVariables(channelEdited);
                    eventMulticaster.multicastEvent(new OnChannelEdited(channelEdited));
                }
                case NOTIFY_CHANNEL_DESCRIPTION_CHANGED -> {
                    ChannelDescriptionChanged channelDescriptionChanged = new ChannelDescriptionChanged();
                    setObjectVariables(channelDescriptionChanged);
                    eventMulticaster.multicastEvent(new OnChannelDescriptionChanged(channelDescriptionChanged));
                }
                case NOTIFY_CHANNEL_PASSWORD_CHANGED -> {
                    ChannelPasswordChanged channelPasswordChanged = new ChannelPasswordChanged();
                    setObjectVariables(channelPasswordChanged);
                    eventMulticaster.multicastEvent(new OnChannelPasswordChanged(channelPasswordChanged));
                }
                case NOTIFY_CHANNEL_DELETED -> {
                    ChannelDeleted channelDeleted = new ChannelDeleted();
                    setObjectVariables(channelDeleted);
                    eventMulticaster.multicastEvent(new OnChannelDeleted(channelDeleted));
                }
                case NOTIFY_CHANNEL_MOVED -> {
                    ChannelMoved channelMoved = new ChannelMoved();
                    setObjectVariables(channelMoved);
                    eventMulticaster.multicastEvent(new OnChannelMoved(channelMoved));
                }
                case NOTIFY_SERVER_EDITED -> {
                    ServerEdited serverEdited = new ServerEdited();
                    setObjectVariables(serverEdited);
                    eventMulticaster.multicastEvent(new OnServerEdited(serverEdited));
                }
                case NOTIFY_SERVER_UPDATED -> {
                    ServerUpdated serverUpdated = new ServerUpdated();
                    setObjectVariables(serverUpdated);
                    eventMulticaster.multicastEvent(new OnServerUpdated(serverUpdated));
                }
                case NOTIFY_CHANNEL_LIST -> {
                    ChannelList channelList = new ChannelList();
                    setObjectVariables(channelList);
                    eventMulticaster.multicastEvent(new OnChannelList(channelList));
                }
                case NOTIFY_CHANNEL_LIST_FINISHED -> {
                    ChannelListFinished channelListFinished = new ChannelListFinished();
                    setObjectVariables(channelListFinished);
                    eventMulticaster.multicastEvent(new OnChannelListFinished(channelListFinished));
                }
                case NOTIFY_TEXT_MESSAGE -> {
                    TextMessage textMessage = new TextMessage();
                    setObjectVariables(textMessage);
                    eventMulticaster.multicastEvent(new OnTextMessage(textMessage));
                }
                case NOTIFY_CURRENT_SERVER_CONNECTION_CHANGED -> {
                    CurrentServerConnectionChanged currentServerConnectionChanged = new CurrentServerConnectionChanged();
                    setObjectVariables(currentServerConnectionChanged);
                    eventMulticaster.multicastEvent(new OnCurrentServerConnectionChanged(currentServerConnectionChanged));
                }
                case NOTIFY_CONNECT_STATUS_CHANGE -> {
                    ConnectStatusChange connectStatusChange = new ConnectStatusChange();
                    setObjectVariables(connectStatusChange);
                    eventMulticaster.multicastEvent(new OnConnectStatusChange(connectStatusChange));
                }
                case NOTIFY_CHANNEL_GROUP_LIST -> {
                    ChannelGroupList channelGroupList = new ChannelGroupList();
                    setObjectVariables(channelGroupList);
                    eventMulticaster.multicastEvent(new OnChannelGroupList(channelGroupList));
                }
                case NOTIFY_CHANNEL_GROUP_PERM_LIST -> {
                    ChannelGroupPermList channelGroupPermList = new ChannelGroupPermList();
                    setObjectVariables(channelGroupPermList);
                    eventMulticaster.multicastEvent(new OnChannelGroupPermList(channelGroupPermList));
                }
                case NOTIFY_CHANNEL_PERM_LIST -> {
                    ChannelPermList channelPermList = new ChannelPermList();
                    setObjectVariables(channelPermList);
                    eventMulticaster.multicastEvent(new OnChannelPermList(channelPermList));
                }
                case NOTIFY_CLIENT_CHANNEL_GROUP_CHANGED -> {
                    ClientChannelGroupChanged channelGroupChanged = new ClientChannelGroupChanged();
                    setObjectVariables(channelGroupChanged);
                    eventMulticaster.multicastEvent(new OnClientChannelGroupChanged(channelGroupChanged));
                }
                case NOTIFY_CHANNEL_SUBSCRIBED -> {
                    ChannelSubscribed channelSubscribed = new ChannelSubscribed();
                    setObjectVariables(channelSubscribed);
                    eventMulticaster.multicastEvent(new OnChannelSubscribed(channelSubscribed));
                }
                case NOTIFY_CHANNEL_UNSUBSCRIBED -> {
                    ChannelUnsubscribed channelUnsubscribed = new ChannelUnsubscribed();
                    setObjectVariables(channelUnsubscribed);
                    eventMulticaster.multicastEvent(new OnChannelUnsubscribed(channelUnsubscribed));
                }
                case NOTIFY_CLIENT_NEEDED_PERMISSIONS -> {
                    ClientNeededPermissions clientNeededPermissions = new ClientNeededPermissions();
                    setObjectVariables(clientNeededPermissions);
                    eventMulticaster.multicastEvent(new OnClientNeededPermissions(clientNeededPermissions));
                }
                case NOTIFY_SERVER_GROUP_LIST -> {
                    ServerGroupList serverGroupList = new ServerGroupList();
                    setObjectVariables(serverGroupList);
                    eventMulticaster.multicastEvent(new OnServerGroupList(serverGroupList));
                }
                case NOTIFY_SERVER_GROUP_PERM_LIST -> {
                    ServerGroupPermList serverGroupPermList = new ServerGroupPermList();
                    setObjectVariables(serverGroupPermList);
                    eventMulticaster.multicastEvent(new OnServerGroupPermList(serverGroupPermList));
                }
                case NOTIFY_SERVER_GROUP_CLIENT_LIST -> {
                    ServerGroupClientList serverGroupClientList = new ServerGroupClientList();
                    setObjectVariables(serverGroupClientList);
                    eventMulticaster.multicastEvent(new OnServerGroupClientList(serverGroupClientList));
                }
                case NOTIFY_SERVER_GROUP_CLIENT_ADDED -> {
                    ServerGroupClient serverGroupClient = new ServerGroupClient();
                    setObjectVariables(serverGroupClient);
                    eventMulticaster.multicastEvent(new OnServerGroupClientAdded(serverGroupClient));
                }
                case NOTIFY_SERVER_GROUP_CLIENT_DELETED -> {
                    ServerGroupClient serverGroupClient = new ServerGroupClient();
                    setObjectVariables(serverGroupClient);
                    eventMulticaster.multicastEvent(new OnServerGroupClientDeleted(serverGroupClient));
                }
                case NOTIFY_FILE_LIST -> {
                    FileList fileList = new FileList();
                    setObjectVariables(fileList);
                    eventMulticaster.multicastEvent(new OnFileList(fileList));
                }
                case NOTIFY_FILE_LIST_FINISHED -> {
                    FileListFinished fileListFinished = new FileListFinished();
                    setObjectVariables(fileListFinished);
                    eventMulticaster.multicastEvent(new OnFileListFinished(fileListFinished));
                }
                case NOTIFY_START_UPLOAD -> {
                    StartUpload startUpload = new StartUpload();
                    setObjectVariables(startUpload);
                    eventMulticaster.multicastEvent(new OnStartUpload(startUpload));
                }
                case NOTIFY_START_DOWNLOAD -> {
                    StartDownload startDownload = new StartDownload();
                    setObjectVariables(startDownload);
                    eventMulticaster.multicastEvent(new OnStartDownload(startDownload));
                }
            }
        }
    }

    // V1

    private boolean multipleVariables = false;

    private String next() {
//        if (message.contains(" "))
//            return message.substring(0, message.indexOf(" "));
//        return message;
        int spcIndex = message.indexOf(" ");
        int sepIndex = message.indexOf("|");
        if (spcIndex > -1 && sepIndex > -1) {
            if (spcIndex < sepIndex)
                return message.substring(0, spcIndex);
            return message.substring(0, sepIndex);
        }
        if (spcIndex > -1)
            return message.substring(0, spcIndex);
        if (sepIndex > -1)
            return message.substring(0, sepIndex);
        return message;
    }

    private void updateMessage() {
        if (!message.contains(" ") && !message.contains("|")) {
            message = "";
            return;
        }
        if (multipleVariables) message = message.substring(message.indexOf("|") + 1);
        else message = message.substring(message.indexOf(" ") + 1);
    }

    private String messageVariable() throws TeamSpeakClientQueryException {
        int spaceIndex = message.indexOf(" ");
        int equalsIndex = message.indexOf("=");
        if (spaceIndex == -1) spaceIndex = message.length();
        if (equalsIndex == -1 || equalsIndex > spaceIndex)
            throw new TeamSpeakClientQueryException(TeamSpeakError.VARIABLE_WITHOUT_EQUALS);
        if (message.substring(equalsIndex + 1, spaceIndex).contains("|") &&
                message.substring(equalsIndex + 1, spaceIndex).contains("=")) {
            multipleVariables = true;
            return message.substring(message.indexOf("=") + 1, message.indexOf("|"));
        } else multipleVariables = false;
        return message.substring(message.indexOf("=") + 1, spaceIndex);
    }

    private String messageVariable_string() throws TeamSpeakClientQueryException {
        String str = "";
        try {
            str = messageVariable();
        } catch (TeamSpeakClientQueryException e) {
            e.printStackTrace();
        }
        updateMessage();
        return str;
    }

    private int messageVariable_int() throws TeamSpeakClientQueryException {
        int i = 0;
        try {
            i = Integer.parseInt(messageVariable());
        } catch (TeamSpeakClientQueryException e) {
            e.printStackTrace();
        }
        updateMessage();
        return i;
    }

    private long messageVariable_long() throws TeamSpeakClientQueryException {
        long l = 0L;
        try {
            l = Long.parseLong(messageVariable());
        } catch (TeamSpeakClientQueryException e) {
            e.printStackTrace();
        }
        updateMessage();
        return l;
    }

    // V2

    private TeamSpeakVariables getVariablesFromMessage() {
        if (message == null) throw new TeamSpeakClientQueryException(TeamSpeakError.MESSAGE_NULL);
        List<String> repeating = findRepeatingVariables();
        TeamSpeakVariables variables = new TeamSpeakVariables();
        Map<String, String> map = new HashMap<>();
        String buffer;
        String key;
        String value;
        Map<String, String> rep = new HashMap<>();
        while (!message.isBlank()) {
            buffer = next();
//            System.out.println("Buffer... " + buffer);
            if (buffer.contains("=")) {
                key = buffer.substring(0, buffer.indexOf("="));
                value = buffer.substring(buffer.indexOf("=") + 1);
                if (repeating.contains(key)) {
                    if (rep.containsKey(key)) {
                        List<Map<String, String>> repVarList = variables.getRepeating();
                        repVarList.add(rep);
                        variables.setRepeating(repVarList);
                        rep = new HashMap<>();
                        // rep.clear() will not work here.
                    }
                    rep.put(key, value);
                } else map.put(key, value);
            }
            if (!message.contains(" ")) message = "";
//            message = message.substring(message.indexOf(" ") + 1);
            int spcIndex = message.indexOf(" ");
            int sepIndex = message.indexOf("|");
            if (spcIndex > -1 && sepIndex > -1) {
                if (spcIndex < sepIndex)
                    message = message.substring(spcIndex + 1);
                else message = message.substring(sepIndex + 1);
            }
            else if (spcIndex > -1)
                message = message.substring(spcIndex + 1);
            else if (sepIndex > -1)
                message = message.substring(sepIndex + 1);
        }
//        System.out.println("START PARAMETERS");
//        map.forEach((k, v) -> System.out.println(k + " = " + v));
//        System.out.println("END PARAMETERS");

        // Save
        variables.setVariables(map);
        List<Map<String, String>> repVarList = variables.getRepeating();
        if (!rep.isEmpty()) repVarList.add(rep);
        variables.setRepeating(repVarList);
//        System.out.println(variables);
        return variables;
    }

    private void setObjectVariables(Object object) {
        List<String> repeating = findRepeatingVariables();
//        System.out.println("Repeating... " + repeating);
        TeamSpeakVariables variables = getVariablesFromMessage();
//        System.out.println("Classes... " + Arrays.toString(object.getClass().getClasses()));
//        System.out.println("Declared classes... " + Arrays.toString(object.getClass().getDeclaredClasses()));
        variables.getVariables().forEach((key, value) -> {
            try {
                if (objectContainsField(object, key)) {
                    Field field = object.getClass().getField(key);
                    setField(field, object, value);
                } else {
                    // subclass field
                    Map<String, String> map = new HashMap<>();
                    map.put(key, value);
                    List<Map<String, String>> list = variables.getRepeating();
                    list.add(map);
                    variables.setRepeating(list);
                }
            } catch (NoSuchFieldException e) {
                logger.error("Field {} not found in {}", key, object.getClass());
            }
        });
        if (!variables.getRepeating().isEmpty()) {
            try {
                // get first subclass
                Class<?> clazz = object.getClass().getDeclaredClasses()[0];
                Field[] fields = object.getClass().getFields();
                Field listField = null;
                // find the List<?> field
                for (Field field : fields) {
                    if (field.getType().equals(List.class)) {
                        listField = field;
                        break;
                    }
                }
                if (listField == null)
                    throw new TeamSpeakClientQueryException(TeamSpeakError.MULTIPLE_VARIABLES_NO_LIST);
                List<Object> list = new ArrayList<>();
                variables.getRepeating().forEach(map -> {
                    try {
                        Object item = clazz.getDeclaredConstructor().newInstance();
                        map.forEach((key, value) -> {
                            try {
                                Field field = clazz.getField(key);
                                setField(field, item, value);
                            } catch (NoSuchFieldException e) {
                                logger.error("Field {} not found in {}", key, clazz);
                                e.printStackTrace();
                            }
                        });
                        list.add(item);
                    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
                listField.set(object, list);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                // Could be thrown when some parameters from message are not define in specified object
                logger.error("No subclass on: {}", object);
                logger.error("Repeating variables: {}", variables.getRepeating());
            }
        }
    }

    private void setField(Field field, Object object, String value) {
        try {
//            System.out.println("Field... " + field.getName() + " ...type... " + field.getType() + " ...generic... " + field.getGenericType());
            if (field.getType().equals(String.class)) field.set(object, value);
            if (field.getType().equals(Integer.class)) field.set(object, Integer.parseInt(value));
            if (field.getType().equals(Long.class)) field.set(object, Long.parseLong(value));
            if (field.getType().equals(Boolean.class)) field.set(object, intToBoolean(Integer.parseInt(value)));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> findRepeatingVariables() {
        List<String> duplicates = new ArrayList<>();
        List<String> words = new ArrayList<>();
        // Split message with " " and "|"
        for (String word : message.split(" |\\|")) {
            if (!word.contains("=")) break;
            word = word.substring(0, word.indexOf("="));
            if (!duplicates.contains(word)) {
                if (words.contains(word)) {
                    duplicates.add(word);
                    words.remove(word);
                } else words.add(word);
            }
        }
        return duplicates;
    }

    /**
     * Check if object contains field with given name.
     *
     * @param object object to check
     * @param field field name to check
     * @return true if field exists in object
     */
    private boolean objectContainsField(Object object, String field) {
        try {
            object.getClass().getField(field);
        } catch (NoSuchFieldException e) {
            return false;
        }
        return true;
    }

    /**
     * Replaces all spaces with "\s", so it can be sent to client without problems.
     *
     * @param str String to modify
     * @return String with spaces removed
     */
    private String replaceSpaces(String str) {
        if (str.contains(" "))
            str = str.replaceAll(" ", Character.toString((char) 92) + Character.toString((char) 92) + "s");
        return str;
    }

    /**
     * Opposite of {@link #replaceSpaces(String)}
     *
     * @param str String to modify
     * @return String with spaces
     */
    private String insertSpaces(String str) {
        if (str == null) return null;
        if (str.contains(Character.toString((char) 92) + Character.toString((char) 92) + "s"))
            str = str.replaceAll(Character.toString((char) 92) + Character.toString((char) 92) + "s", " ");
        return str;
    }

    /**
     * Convert integer to boolean.
     *
     * @param i integer to convert
     * @return true if i == 1
     */
    private boolean intToBoolean(int i) {
        return i == 1;
    }

    /**
     * Convert boolean to integer.
     *
     * @param b boolean to convert
     * @return 1 if boolean = true
     */
    private int booleanToInt(boolean b) {
        if (b) return 1;
        return 0;
    }

    /**
     * Generate and send command from object.
     *
     * @param command Command to send
     * @param object Object to get parameters from
     */
    public void sendCommand(String command, Object object) {
        StringBuilder sb = new StringBuilder(command);
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            try {
                Object value = field.get(object);
                // Skip field if value is null
                if (value == null) continue;
                if (field.getType().equals(Boolean.class)) {
                    sb.append(" -").append(field.getName());
                    continue;
                }
                if (field.getType().equals(List.class)) {
                    ArrayList<?> list = (ArrayList<?>) value;
                    for (int i = 0; i < list.size(); i++) {
                        if (i > 0) sb.append("|");
                        Object listObj = list.get(i);
                        for (Field subField : listObj.getClass().getFields()) {
                            Object subValue = subField.get(listObj);
                            if (subValue == null) continue;
                            if (!sb.toString().endsWith("|")) sb.append(" ");
                            if (subField.getType().equals(Boolean.class)) {
                                sb.append("-").append(subField.getName());
                                continue;
                            }
                            if (subField.getType().equals(String.class)) subValue = replaceSpaces((String) subValue);
                            sb.append(subField.getName()).append("=").append(subValue);
                        }
                    }
                    continue;
                }
                if (field.getType().equals(String.class)) value = replaceSpaces((String) value);
                sb.append(" ").append(field.getName()).append("=").append(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        this.send(sb.toString());
    }

    /**
     * Send command for TeamSpeak client to execute.
     *
     * @param str Command to execute
     */
    private void send(String str) {
        logger.info("Sending: {}", str);
        input.println(str);
    }

    /* client actions */

    public void help() {
        send("help");
    }

    public void quit() {
        send("quit");
    }

    /**
     * Use specific server connection handler
     *
     * @param handlerId Server connection handler ID
     */
    public void use(int handlerId) {
        send("use " + handlerId);
    }

    /**
     * Authenticated with TeamSpeak client plugin
     *
     * @param apiKey API key
     */
    public void auth(String apiKey) {
        send("auth apikey=" + apiKey);
    }

    /**
     * Add a new ban rule to the server
     *
     * @param rule Details on new ban rule
     */
    public void banAdd(BanRule rule) {
        if (rule.getIpAddress().isBlank() &&
                rule.getName().isBlank() &&
                rule.getClientUid().isBlank())
            throw new TeamSpeakClientQueryException();
        // at least on of the three must be set
        StringBuilder sb = new StringBuilder("banadd");
        if (!rule.getIpAddress().isBlank())
            sb.append(" ip=").append(rule.getIpAddress());
        if (!rule.getName().isBlank())
            sb.append(" name=").append(replaceSpaces(rule.getName()));
        if (!rule.getClientUid().isBlank())
            sb.append(" uid=").append(rule.getClientUid());
        sb.append(" time=").append(rule.getTime());
        if (!rule.getReason().isBlank())
            sb.append(" banreason=").append(replaceSpaces(rule.getReason()));
        send(sb.toString());
    }

    /**
     * Delete specified channel from TeamSpeak server.
     *
     * @param channelDelete Object containing information on which channel to delete
     */
    public void channelDelete(ChannelDelete channelDelete) {
        sendCommand("channeldelete", channelDelete);
    }

    /**
     * Register for all possible events.
     */
    public void clientNotifyRegister() {
        clientNotifyRegister("any");
    }

    /**
     * Possible values for event:<br>
     *   notifytalkstatuschange<br>
     *   notifymessage<br>
     *   notifymessagelist<br>
     *   notifycomplainlist<br>
     *   notifybanlist<br>
     *   notifyclientmoved<br>
     *   notifyclientleftview<br>
     *   notifycliententerview<br>
     *   notifyclientpoke<br>
     *   notifyclientchatclosed<br>
     *   notifyclientchatcomposing<br>
     *   notifyclientupdated<br>
     *   notifyclientids<br>
     *   notifyclientdbidfromuid<br>
     *   notifyclientnamefromuid<br>
     *   notifyclientnamefromdbid<br>
     *   notifyclientuidfromclid<br>
     *   notifyconnectioninfo<br>
     *   notifychannelcreated<br>
     *   notifychanneledited<br>
     *   notifychanneldeleted<br>
     *   notifychannelmoved<br>
     *   notifyserveredited<br>
     *   notifyserverupdated<br>
     *   channellist<br>
     *   channellistfinished<br>
     *   notifytextmessage<br>
     *   notifycurrentserverconnectionchanged<br>
     *   notifyconnectstatuschange
     * <br>
     * Use {@link #clientNotifyRegister} to register for all possible events.
     *
     * @param event event to register for
     */
    public void clientNotifyRegister(String event) {
        send("clientnotifyregister schandlerid=" + handlerId + " event=" + event);
    }

    /**
     * Unregister from all previously registered events and register new list of events to listen for.
     *
     * @param events List of events to listen for
     */
    public void clientNotifyRegister(NotifyEvent ... events) {
        clientNotifyUnregister();
        for (NotifyEvent event : events)
            clientNotifyRegister(event.value);
    }

    /**
     * Unregisters from all previously registered client notifications.
     */
    public void clientNotifyUnregister() {
        send("clientnotifyunregister");
    }

    /**
     * Poke a client.
     *
     * @param message Message to poke with
     * @param target Target Client ID
     */
    public void clientPoke(String message, int target) {
        send("clientpoke msg=" + replaceSpaces(message) + " clid=" + target);
    }

    /**
     * Poke a client.
     *
     * @param clientPoke Object containing poke details
     */
    public void clientPoke(io.nqa.teamspeak.query.client.model.outbound.ClientPoke clientPoke) {
        sendCommand("clientpoke", clientPoke);
    }

    /**
     * Connect to TeamSpeak server.
     *
     * @param connect Object containing connection details
     */
    public void connect(Connect connect) {
        sendCommand("connect", connect);
    }

    /**
     * Get current server connection handler ID.
     */
    public void currentScHandlerId() {
        send("currentschandlerid");
    }

    /**
     * Disconnect from TeamSpeak server.
     */
    public void disconnect() {
        send("disconnect");
    }

    /**
     * Disconnect from TeamSpeak server with message.
     *
     * @param message Message to server
     */
    public void disconnect(String message) {
        send("disconnect msg=" + replaceSpaces(message));
    }

    /**
     * Check if we know the current password of a channel.
     *
     * @param channelId Channel to check on
     * @param password Password to verify
     */
    public void verifyChannelPassword(int channelId, String password) {
        send("verifychannelpassword cid=" + channelId + " password=" + password);
    }

    /**
     * Check if we know the current password of a channel.
     *
     * @param verifyChannelPassword Object containing channel and password details
     */
    public void verifyChannelPassword(VerifyChannelPassword verifyChannelPassword) {
        sendCommand("verifychannelpassword", verifyChannelPassword);
    }

    /**
     * Check if we know the current server password.
     *
     * @param password Password to verify
     */
    public void verifyServerPassword(String password) {
        send("verifyserverpassword password=" + password);
    }

    /**
     * Get information on current client.
     */
    public void whoAmI() {
        send("whoami");
    }
}
