insert into name_info (id, first_name, last_name)
values ('dfb7da5e-4e7d-4793-8f45-5e15e80a4a3e', 'ADMIN', 'ADMIN');
insert into mediq_group (id, name)
values ('250c93f3-2952-42cb-91ae-73bf06eca11d', 'Administrator');
insert into mediq_user (id, unique_id, group_id, name_info_id)
values ('57232ddd-3c99-4d46-ac6e-aac7c2dd066e', 'FIRE_BASE_TOKEN', '250c93f3-2952-42cb-91ae-73bf06eca11d', 'dfb7da5e-4e7d-4793-8f45-5e15e80a4a3e');
