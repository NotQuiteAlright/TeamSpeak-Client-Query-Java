# List of permissions
| Permission                                                 | Description                                                          |
|------------------------------------------------------------|----------------------------------------------------------------------|
| unknown                                                    | May occur on error returns with no associated permission             |
| b_serverinstance_help_view                                 | Retrieve information about ServerQuery commands                      |
| b_serverinstance_version_view                              | Retrieve global server version (including platform and build number) |
| b_serverinstance_info_view                                 | Retrieve global server information                                   |
| b_serverinstance_virtualserver_list                        | List virtual servers stored in the database                          |
| b_serverinstance_binding_list                              | List active IP bindings on multi-homed machines                      |
| b_serverinstance_permission_list                           | List permissions available available on the server instance          |
| b_serverinstance_permission_find                           | Search permission assignments by name or ID                          |
| b_virtualserver_create                                     | Create virtual servers                                               |
| b_virtualserver_delete                                     | Delete virtual servers                                               |
| b_virtualserver_start_any                                  | Start any virtual server in the server instance                      |
| b_virtualserver_stop_any                                   | Stop any virtual server in the server instance                       |
| b_virtualserver_change_machine_id                          | Change a virtual servers machine ID                                  |
| b_virtualserver_change_template                            | Edit virtual server default template values                          |
| b_serverquery_login                                        | Login to ServerQuery                                                 |
| b_serverinstance_textmessage_send                          | Send text messages to all virtual servers at once                    |
| b_serverinstance_log_view                                  | Retrieve global server log                                           |
| b_serverinstance_log_add                                   | Write to global server log                                           |
| b_serverinstance_stop                                      | Shutdown the server process                                          |
| b_serverinstance_modify_settings                           | Edit global settings                                                 |
| b_serverinstance_modify_querygroup                         | Edit global ServerQuery groups                                       |
| b_serverinstance_modify_templates                          | Edit global template groups                                          |
| b_virtualserver_select                                     | Select a virtual server                                              |
| b_virtualserver_info_view                                  | Retrieve virtual server information                                  |
| b_virtualserver_connectioninfo_view                        | Retrieve virtual server connection information                       |
| b_virtualserver_channel_list                               | List channels on a virtual server                                    |
| b_virtualserver_channel_search                             | Search for channels on a virtual server                              |
| b_virtualserver_client_list                                | List clients online on a virtual server                              |
| b_virtualserver_client_search                              | Search for clients online on a virtual server                        |
| b_virtualserver_client_dblist                              | List client identities known by the virtual server                   |
| b_virtualserver_client_dbsearch                            | Search for client identities known by the virtual server             |
| b_virtualserver_client_dbinfo                              | Retrieve client information                                          |
| b_virtualserver_permission_find                            | Find permissions                                                     |
| b_virtualserver_custom_search                              | Find custom fields                                                   |
| b_virtualserver_start                                      | Start own virtual server                                             |
| b_virtualserver_stop                                       | Stop own virtual server                                              |
| b_virtualserver_token_list                                 | List privilege keys available                                        |
| b_virtualserver_token_add                                  | Create new privilege keys                                            |
| b_virtualserver_token_use                                  | Use a privilege keys to gain access to groups                        |
| b_virtualserver_token_delete                               | Delete a privilege key                                               |
| b_virtualserver_log_view                                   | Retrieve virtual server log                                          |
| b_virtualserver_log_add                                    | Write to virtual server log                                          |
| b_virtualserver_join_ignore_password                       | Join virtual server ignoring its password                            |
| b_virtualserver_notify_register                            | Register for server notifications                                    |
| b_virtualserver_notify_unregister                          | Unregister from server notifications                                 |
| b_virtualserver_snapshot_create                            | Create server snapshots                                              |
| b_virtualserver_snapshot_deploy                            | Deploy server snapshots                                              |
| b_virtualserver_permission_reset                           | Reset the server permission settings to default values               |
| b_virtualserver_modify_name                                | Modify server name                                                   |
| b_virtualserver_modify_welcomemessage                      | Modify welcome message                                               |
| b_virtualserver_modify_maxclients                          | Modify servers max clients                                           |
| b_virtualserver_modify_reserved_slots                      | Modify reserved slots                                                |
| b_virtualserver_modify_password                            | Modify server password                                               |
| b_virtualserver_modify_default_servergroup                 | Modify default Server Group                                          |
| b_virtualserver_modify_default_channelgroup                | Modify default Channel Group                                         |
| b_virtualserver_modify_default_channeladmingroup           | Modify default Channel Admin Group                                   |
| b_virtualserver_modify_channel_forced_silence              | Modify channel force silence value                                   |
| b_virtualserver_modify_complain                            | Modify individual complain settings                                  |
| b_virtualserver_modify_antiflood                           | Modify individual antiflood settings                                 |
| b_virtualserver_modify_ft_settings                         | Modify file transfer settings                                        |
| b_virtualserver_modify_ft_quotas                           | Modify file transfer quotas                                          |
| b_virtualserver_modify_hostmessage                         | Modify individual hostmessage settings                               |
| b_virtualserver_modify_hostbanner                          | Modify individual hostbanner settings                                |
| b_virtualserver_modify_hostbutton                          | Modify individual hostbutton settings                                |
| b_virtualserver_modify_port                                | Modify server port                                                   |
| b_virtualserver_modify_autostart                           | Modify server autostart                                              |
| b_virtualserver_modify_needed_identity_security_level      | Modify required identity security level                              |
| b_virtualserver_modify_priority_speaker_dimm_modificator   | Modify priority speaker dimm modificator                             |
| b_virtualserver_modify_log_settings                        | Modify log settings                                                  |
| b_virtualserver_modify_min_client_version                  | Modify min client version                                            |
| b_virtualserver_modify_icon_id                             | Modify server icon                                                   |
| b_virtualserver_modify_weblist                             | Modify web server list reporting settings                            |
| b_virtualserver_modify_codec_encryption_mode               | Modify codec encryption mode                                         |
| b_virtualserver_modify_temporary_passwords                 | Modify temporary serverpasswords                                     |
| b_virtualserver_modify_temporary_passwords_own             | Modify own temporary serverpasswords                                 |
| b_virtualserver_modify_channel_temp_delete_delay_default   | Modify default temporary channel delete delay                        |
| b_virtualserver_modify_nickname                            | Modify server nicknames                                              |
| b_virtualserver_modify_integrations                        | Modify integrations                                                  |
| i_channel_min_depth                                        | Min channel creation depth in hierarchy                              |
| i_channel_max_depth                                        | Max channel creation depth in hierarchy                              |
| b_channel_group_inheritance_end                            | Stop inheritance of channel group permissions                        |
| i_channel_permission_modify_power                          | Modify channel permission power                                      |
| i_channel_needed_permission_modify_power                   | Needed modify channel permission power                               |
| b_channel_info_view                                        | Retrieve channel information                                         |
| b_channel_create_child                                     | Create sub-channels                                                  |
| b_channel_create_permanent                                 | Create permanent channels                                            |
| b_channel_create_semi_permanent                            | Create semi-permanent channels                                       |
| b_channel_create_temporary                                 | Create temporary channels                                            |
| b_channel_create_private                                   | Create private channel                                               |
| b_channel_create_with_topic                                | Create channels with a topic                                         |
| b_channel_create_with_description                          | Create channels with a description                                   |
| b_channel_create_with_password                             | Create password protected channels                                   |
| b_channel_create_modify_with_codec_speex8                  | Create channels using Speex Narrowband (8 kHz) codecs                |
| b_channel_create_modify_with_codec_speex16                 | Create channels using Speex Wideband (16 kHz) codecs                 |
| b_channel_create_modify_with_codec_speex32                 | Create channels using Speex Ultra-Wideband (32 kHz) codecs           |
| b_channel_create_modify_with_codec_celtmono48              | Create channels using the CELT Mono (48 kHz) codec                   |
| b_channel_create_modify_with_codec_opusvoice               | Create channels using OPUS (voice) codec                             |
| b_channel_create_modify_with_codec_opusmusic               | Create channels using OPUS (music) codec                             |
| i_channel_create_modify_with_codec_maxquality              | Create channels with custom codec quality                            |
| i_channel_create_modify_with_codec_latency_factor_min      | Create channels with minimal custom codec latency factor             |
| b_channel_create_with_maxclients                           | Create channels with custom max clients                              |
| b_channel_create_with_maxfamilyclients                     | Create channels with custom max family clients                       |
| b_channel_create_with_sortorder                            | Create channels with custom sort order                               |
| b_channel_create_with_default                              | Create default channels                                              |
| b_channel_create_with_needed_talk_power                    | Create channels with needed talk power                               |
| b_channel_create_modify_with_force_password                | Create new channels only with password                               |
| i_channel_create_modify_with_temp_delete_delay             | Max delete delay for temporary channels                              |
| b_channel_modify_parent                                    | Move channels                                                        |
| b_channel_modify_make_default                              | Make channel default                                                 |
| b_channel_modify_make_permanent                            | Make channel permanent                                               |
| b_channel_modify_make_semi_permanent                       | Make channel semi-permanent                                          |
| b_channel_modify_make_temporary                            | Make channel temporary                                               |
| b_channel_modify_name                                      | Modify channel name                                                  |
| b_channel_modify_topic                                     | Modify channel topic                                                 |
| b_channel_modify_description                               | Modify channel description                                           |
| b_channel_modify_password                                  | Modify channel password                                              |
| b_channel_modify_codec                                     | Modify channel codec                                                 |
| b_channel_modify_codec_quality                             | Modify channel codec quality                                         |
| b_channel_modify_codec_latency_factor                      | Modify channel codec latency factor                                  |
| b_channel_modify_maxclients                                | Modify channels max clients                                          |
| b_channel_modify_maxfamilyclients                          | Modify channels max family clients                                   |
| b_channel_modify_sortorder                                 | Modify channel sort order                                            |
| b_channel_modify_needed_talk_power                         | Change needed channel talk power                                     |
| i_channel_modify_power                                     | Channel modify power                                                 |
| i_channel_needed_modify_power                              | Needed channel modify power                                          |
| b_channel_modify_make_codec_encrypted                      | Make channel codec encrypted                                         |
| b_channel_modify_temp_delete_delay                         | Modify temporary channel delete delay                                |
| b_channel_delete_permanent                                 | Delete permanent channels                                            |
| b_channel_delete_semi_permanent                            | Delete semi-permanent channels                                       |
| b_channel_delete_temporary                                 | Delete temporary channels                                            |
| b_channel_delete_flag_force                                | Force channel delete                                                 |
| i_channel_delete_power                                     | Delete channel power                                                 |
| i_channel_needed_delete_power                              | Needed delete channel power                                          |
| b_channel_join_permanent                                   | Join permanent channels                                              |
| b_channel_join_semi_permanent                              | Join semi-permanent channels                                         |
| b_channel_join_temporary                                   | Join temporary channels                                              |
| b_channel_join_ignore_password                             | Join channel ignoring its password                                   |
| b_channel_join_ignore_maxclients                           | Ignore channels max clients limit                                    |
| i_channel_join_power                                       | Channel join power                                                   |
| i_channel_needed_join_power                                | Needed channel join power                                            |
| i_channel_subscribe_power                                  | Channel subscribe power                                              |
| i_channel_needed_subscribe_power                           | Needed channel subscribe power                                       |
| i_channel_description_view_power                           | Channel description view power                                       |
| i_channel_needed_description_view_power                    | Needed channel needed description view power                         |
| i_icon_id                                                  | Group icon identifier                                                |
| i_max_icon_filesize                                        | Max icon filesize in bytes                                           |
| b_icon_manage                                              | Enables icon management                                              |
| b_group_is_permanent                                       | Group is permanent                                                   |
| i_group_auto_update_type                                   | Group auto-update type                                               |
| i_group_auto_update_max_value                              | Group auto-update max value                                          |
| i_group_sort_id                                            | Group sort id                                                        |
| i_group_show_name_in_tree                                  | Show group name in tree depending on selected mode                   |
| b_virtualserver_servergroup_list                           | List server groups                                                   |
| b_virtualserver_servergroup_permission_list                | List server group permissions                                        |
| b_virtualserver_servergroup_client_list                    | List clients from a server group                                     |
| b_virtualserver_channelgroup_list                          | List channel groups                                                  |
| b_virtualserver_channelgroup_permission_list               | List channel group permissions                                       |
| b_virtualserver_channelgroup_client_list                   | List clients from a channel group                                    |
| b_virtualserver_client_permission_list                     | List client permissions                                              |
| b_virtualserver_channel_permission_list                    | List channel permissions                                             |
| b_virtualserver_channelclient_permission_list              | List channel client permissions                                      |
| b_virtualserver_servergroup_create                         | Create server groups                                                 |
| b_virtualserver_channelgroup_create                        | Create channel groups                                                |
| i_group_modify_power                                       | Group modify power                                                   |
| i_group_needed_modify_power                                | Needed group modify power                                            |
| i_group_member_add_power                                   | Group member add power                                               |
| i_group_needed_member_add_power                            | Needed group member add power                                        |
| i_group_member_remove_power                                | Group member delete power                                            |
| i_group_needed_member_remove_power                         | Needed group member delete power                                     |
| i_permission_modify_power                                  | Permission modify power                                              |
| b_permission_modify_power_ignore                           | Ignore needed permission modify power                                |
| b_virtualserver_servergroup_delete                         | Delete server groups                                                 |
| b_virtualserver_channelgroup_delete                        | Delete channel groups                                                |
| i_client_permission_modify_power                           | Client permission modify power                                       |
| i_client_needed_permission_modify_power                    | Needed client permission modify power                                |
| i_client_max_clones_uid                                    | Max additional connections per client identity                       |
| i_client_max_idletime                                      | Max idle time in seconds                                             |
| i_client_max_avatar_filesize                               | Max avatar filesize in bytes                                         |
| i_client_max_channel_subscriptions                         | Max channel subscriptions                                            |
| b_client_is_priority_speaker                               | Client is priority speaker                                           |
| b_client_skip_channelgroup_permissions                     | Ignore channel group permissions                                     |
| b_client_force_push_to_talk                                | Force Push-To-Talk capture mode                                      |
| b_client_ignore_bans                                       | Ignore bans                                                          |
| b_client_ignore_antiflood                                  | Ignore antiflood measurements                                        |
| b_client_issue_client_query_command                        | Issue query commands from client                                     |
| b_client_use_reserved_slot                                 | Use an reserved slot                                                 |
| b_client_use_channel_commander                             | Use channel commander                                                |
| b_client_request_talker                                    | Allow to request talk power                                          |
| b_client_avatar_delete_other                               | Allow deletion of avatars from other clients                         |
| b_client_is_sticky                                         | Client will be sticked to current channel                            |
| b_client_ignore_sticky                                     | Client ignores sticky flag                                           |
| b_client_info_view                                         | Retrieve client information                                          |
| b_client_permissionoverview_view                           | Retrieve client permissions overview                                 |
| b_client_permissionoverview_own                            | Retrieve clients own permissions overview                            |
| b_client_remoteaddress_view                                | View client IP address and port                                      |
| i_client_serverquery_view_power                            | ServerQuery view power                                               |
| i_client_needed_serverquery_view_power                     | Needed ServerQuery view power                                        |
| b_client_custom_info_view                                  | View custom fields                                                   |
| i_client_kick_from_server_power                            | Client kick power from server                                        |
| i_client_needed_kick_from_server_power                     | Needed client kick power from server                                 |
| i_client_kick_from_channel_power                           | Client kick power from channel                                       |
| i_client_needed_kick_from_channel_power                    | Needed client kick power from channel                                |
| i_client_ban_power                                         | Client ban power                                                     |
| i_client_needed_ban_power                                  | Needed client ban power                                              |
| i_client_move_power                                        | Client move power                                                    |
| i_client_needed_move_power                                 | Needed client move power                                             |
| i_client_complain_power                                    | Complain power                                                       |
| i_client_needed_complain_power                             | Needed complain power                                                |
| b_client_complain_list                                     | Show complain list                                                   |
| b_client_complain_delete_own                               | Delete own complains                                                 |
| b_client_complain_delete                                   | Delete complains                                                     |
| b_client_ban_list                                          | Show banlist                                                         |
| b_client_ban_create                                        | Add a ban                                                            |
| b_client_ban_delete_own                                    | Delete own bans                                                      |
| b_client_ban_delete                                        | Delete bans                                                          |
| i_client_ban_max_bantime                                   | Max bantime                                                          |
| i_client_private_textmessage_power                         | Client private message power                                         |
| i_client_needed_private_textmessage_power                  | Needed client private message power                                  |
| b_client_server_textmessage_send                           | Send text messages to virtual server                                 |
| b_client_channel_textmessage_send                          | Send text messages to channel                                        |
| b_client_offline_textmessage_send                          | Send offline messages to clients                                     |
| i_client_talk_power                                        | Client talk power                                                    |
| i_client_needed_talk_power                                 | Needed client talk power                                             |
| i_client_poke_power                                        | Client poke power                                                    |
| i_client_needed_poke_power                                 | Needed client poke power                                             |
| b_client_set_flag_talker                                   | Set the talker flag for clients and allow them to speak              |
| i_client_whisper_power                                     | Client whisper power                                                 |
| i_client_needed_whisper_power                              | Client needed whisper power                                          |
| b_client_modify_description                                | Edit a clients description                                           |
| b_client_modify_own_description                            | Allow client to edit own description                                 |
| b_client_modify_dbproperties                               | Edit a clients properties in the database                            |
| b_client_delete_dbproperties                               | Delete a clients properties in the database                          |
| b_client_create_modify_serverquery_login                   | Create or modify own ServerQuery account                             |
| b_ft_ignore_password                                       | Browse files without channel password                                |
| b_ft_transfer_list                                         | Retrieve list of running filetransfers                               |
| i_ft_file_upload_power                                     | File upload power                                                    |
| i_ft_needed_file_upload_power                              | Needed file upload power                                             |
| i_ft_file_download_power                                   | File download power                                                  |
| i_ft_needed_file_download_power                            | Needed file download power                                           |
| i_ft_file_delete_power                                     | File delete power                                                    |
| i_ft_needed_file_delete_power                              | Needed file delete power                                             |
| i_ft_file_rename_power                                     | File rename power                                                    |
| i_ft_needed_file_rename_power                              | Needed file rename power                                             |
| i_ft_file_browse_power                                     | File browse power                                                    |
| i_ft_needed_file_browse_power                              | Needed file browse power                                             |
| i_ft_directory_create_power                                | Create directory power                                               |
| i_ft_needed_directory_create_power                         | Needed create directory power                                        |
| i_ft_quota_mb_download_per_client                          | Download quota per client in MByte                                   |
| i_ft_quota_mb_upload_per_client                            | Upload quota per client in MByte                                     |
