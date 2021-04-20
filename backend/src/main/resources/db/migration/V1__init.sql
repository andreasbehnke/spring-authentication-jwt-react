CREATE TABLE "user" (
    id uuid not null,
    email varchar not null,
    hashedPassword varchar not null,
    enabled bool not null,

    constraint pk_user primary key (id),
    constraint unique_user_email unique (email)
);
