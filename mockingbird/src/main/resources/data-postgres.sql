INSERT INTO mediq_group (id, name)
VALUES ('ce15c087-48b5-47f9-ba06-2ed7965c1179', 'Administrator')
ON CONFLICT (id) DO NOTHING;

insert into name_info_entity (id, first_name, last_name, middle_name)
VALUES ('0590af1f-26a5-4b3c-850e-a9f31071411b', 'Aydan', 'Gaite', null)
ON CONFLICT (id) DO NOTHING;

insert into mediq_user (unique_id, group_id, name_info_entity_id)
VALUES ('lf2fnZU6eEVHmeA9f2J68cmZrl72', 'ce15c087-48b5-47f9-ba06-2ed7965c1179',
        '0590af1f-26a5-4b3c-850e-a9f31071411b')
ON CONFLICT (unique_id) DO NOTHING;
