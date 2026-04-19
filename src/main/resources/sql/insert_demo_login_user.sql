DECLARE @userId UNIQUEIDENTIFIER = NEWID();

INSERT INTO dbo.users (
    id,
    username,
    password,
    full_name,
    email,
    phone,
    avatar_url,
    last_login_at,
    created_at,
    updated_at,
    created_by,
    updated_by,
    deleted_at,
    deleted_by,
    is_active
)
VALUES (
    @userId,
    N'admin',
    N'Admin@123',
    N'Administrator',
    N'admin@demo.local',
    NULL,
    NULL,
    NULL,
    SYSUTCDATETIME(),
    SYSUTCDATETIME(),
    NULL,
    NULL,
    NULL,
    NULL,
    1
);

-- Username: admin
-- Email: admin@demo.local
-- Password: Admin@123