drop schema if exists adproject;
create database adproject;

delete from adproject.user;

insert into adproject.role
(type)
values
('USER'),
('ADMIN');

insert into adproject.user
(date_of_birth, email, enabled, gender, height, name, password, profile_pic, username, weight)
values
(str_to_date('2022-01-01', '%Y-%m-%d'), 'james@gmail.com', true,'', 160, 'James', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '','james', 50),
(str_to_date('2022-01-01', '%Y-%m-%d'), 'jill@gmail.com', true,'', 160, 'Jill', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '', 'jill', 50),
(str_to_date('2022-01-01', '%Y-%m-%d'), 'jake@gmail.com', true,'', 160, 'Jake', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '', 'jake', 50),
(str_to_date('2022-01-01', '%Y-%m-%d'), 'jane@gmail.com', true,'', 160, 'Jane', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '', 'jane', 50),
(str_to_date('2022-01-01', '%Y-%m-%d'), 'appfooddiary@gmail.com', true,'', 160, 'CY', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '', 'cy', 50),
(null, 'admin@gmail.com', true,'', 0, 'admin', '$2a$12$HY72DB4KJuPZpJSjAdWJ8OKvsCRlm.gvA91BXKHf.xaG/Gx602CVO', '','admin', 0);


insert into adproject.users_roles
(user_id, role_id)
values
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(6,2);


INSERT INTO adproject.goal 
(id, end_date, goal_description, start_date, status, target_count, total_meal_count, author_user_id)
VALUES
	(1, '2021-12-06', 'Eat vegetables', '2021-10-06', 'COMPLETED', 60, 60, 3),
	(2, '2022-01-06', 'Eat fruits', '2021-12-07', 'COMPLETED', 30, 30, 3),
	(3, NULL, 'Lose weight', '2022-01-07', 'IN_PROGRESS', 40, 40, 3);


INSERT INTO adproject.meal_entry
(id, description, feeling, flagged, imageurl, time_stamp, title, track_score, visibility, author_user_id, goal_id)
VALUES
	(1, 'happy', 'JOYFUL', 0, NULL, NULL, NULL, 1, 1, 3, 3),
	(2, 'yummy', 'JOYFUL', 0, NULL, NULL, NULL, 1, 1, 3, 3),
	(3, 'sinful', 'PENSIVE', 0, NULL, NULL, NULL, 0, 1, 3, 3),
	(4, 'simple', 'HAPPY', 0, NULL, NULL, NULL, 1, 1, 3, 3);

INSERT INTO adproject.friend_request
(sender_user_id, recipient_user_id, status)
VALUES
    (2, 1, 'ACCEPTED'),
    (2, 3, 'ACCEPTED'),
    (2, 4, 'ACCEPTED'),
    (2, 5, 'PENDING');

INSERT INTO adproject.friend_request
(sender_user_id, recipient_user_id, status)
VALUES
    (2, 7, 'ACCEPTED');