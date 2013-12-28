-- select user, user type and subject
select subject.title, user.username, user.email_address, user.passwd, user_type.user_type_value
from subject, user, user_subject, user_type 
where subject.id=3 and subject.id=user_subject.subject_id 
			and user_subject.user_id=user.id and user.user_type_id=user_type.id ;

-- select user and session
select user.username, session.name, session.creation_date, 
				session_user.delete_indicator as isDeletedSession
from user, session, session_user
where user.id=session_user.user_id and session.id=session_user.session_id
group by user.username;

-- select question and answer
select question.question_text, answer.answer_text
from question, answer
where question.id=3 and question.id=answer.question_id;