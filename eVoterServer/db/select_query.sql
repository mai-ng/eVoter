-- select user, user type and subject
select subject.title, user.username, user_type.user_type_value
from subject, user, evoter.user_subject, user_type 
where subject.id=3 and subject.id=user_subject.subject_id 
			and user_subject.user_id=user.id and user.user_type_id=user_type.id ;

