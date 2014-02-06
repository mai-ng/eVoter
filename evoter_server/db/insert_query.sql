
-- table question_type
insert into question_type (question_type_value) values ('Multiple Choice');
insert into question_type (question_type_value) values ('Yes/No');
insert into question_type (question_type_value) values ('Agree/Disagree');
insert into question_type (question_type_value) values ('Slider');
insert into question_type (question_type_value) values ('Match');
-- table user_type
insert into user_type (user_type_value) values ('Secretary');
insert into user_type (user_type_value) values ('Student');
insert into user_type (user_type_value) values ('Teacher');
-- table subject
insert into subject (title, creation_date) values ('Object Oriented Programming', now());
insert into subject (title, creation_date) values ('Testing Metrics', now());
insert into subject (title, creation_date) values ('Software Engineering', now());
-- table user
insert into user (user_type_id, username, email_address, passwd) values (2, "paul", "paul.gibson@telecom-sudparis.eu", "12345678");
insert into user (user_type_id, username, email_address, passwd) values (2, "jean", "jean.luc@telecom-sudparis.eu", "12345678");
insert into user (user_type_id, username, email_address, passwd) values (3, "nvluong", "nguyen.van@telecom-sudparis.eu", "12345678");
insert into user (user_type_id, username, email_address, passwd) values (3, "ntmai", "nguyen.mai@telecom-sudparis.eu", "12345678");
-- table user_subject
insert into user_subject (subject_id, user_id) values (1, 1);
insert into user_subject (subject_id, user_id) values (1, 3);
insert into user_subject (subject_id, user_id) values (1, 4);
insert into user_subject (subject_id, user_id) values (2, 2);
insert into user_subject (subject_id, user_id) values (2, 3);
insert into user_subject (subject_id, user_id) values (2, 4);
insert into user_subject (subject_id, user_id) values (3, 1);
insert into user_subject (subject_id, user_id) values (3, 3);
insert into user_subject (subject_id, user_id) values (3, 4);
-- table session
insert into session (subject_id, name, creation_date) values (1, "subject_1_session_1", now());
insert into session (subject_id, name, creation_date) values (1, "subject_1_session_2", now());
insert into session (subject_id, name, creation_date) values (1, "subject_1_session_3", now());

insert into session (subject_id, name, creation_date) values (2, "subject_2_session_1", now());
insert into session (subject_id, name, creation_date) values (2, "subject_2_session_2", now());
insert into session (subject_id, name, creation_date) values (2, "subject_2_session_3", now());

insert into session (subject_id, name, creation_date) values (3, "subject_3_session_1", now());
insert into session (subject_id, name, creation_date) values (3, "subject_3_session_2", now());
insert into session (subject_id, name, creation_date) values (3, "subject_3_session_3", now());
-- table session_user
insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (1, 1, false, true);
insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (3, 1, false, false);
insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (4, 1, false, true);

insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (2, 2, false, true);
insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (3, 2, true, true);
insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (4, 2, false, true);

insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (2, 2, false, true);
insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (3, 2, true, true);
insert into session_user (user_id, session_id, delete_indicator, accept_stt) values (4, 2, false, true);
