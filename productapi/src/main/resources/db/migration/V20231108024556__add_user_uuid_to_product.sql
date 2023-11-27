alter table if exists product
    add column if not exists user_id uuid not null default '00000000-0000-0000-0000-000000000000';