-- =============================================================================
-- Tạo bảng user_roles theo cấu trúc thầy chốt (SQL Server)
-- Lưu ý: project đang có @ManyToMany(User.roles) dùng join table user_roles,
-- nên cột user_id + role_id vẫn được giữ để Hibernate join được.
-- =============================================================================

IF OBJECT_ID(N'dbo.user_roles', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.user_roles (
        id UNIQUEIDENTIFIER NOT NULL
            CONSTRAINT DF_user_roles_id DEFAULT NEWID(),

        user_id UNIQUEIDENTIFIER NOT NULL,
        role_id UNIQUEIDENTIFIER NOT NULL,

        created_at DATETIME2 NULL
            CONSTRAINT DF_user_roles_created_at DEFAULT SYSUTCDATETIME(),
        updated_at DATETIME2 NULL,

        created_by UNIQUEIDENTIFIER NULL,
        updated_by UNIQUEIDENTIFIER NULL,

        deleted_at DATETIME2 NULL,
        deleted_by UNIQUEIDENTIFIER NULL,

        is_active BIT NOT NULL
            CONSTRAINT DF_user_roles_is_active DEFAULT (1),

        CONSTRAINT PK_user_roles PRIMARY KEY (id),
        CONSTRAINT FK_user_roles_users FOREIGN KEY (user_id) REFERENCES dbo.users(id),
        CONSTRAINT FK_user_roles_roles FOREIGN KEY (role_id) REFERENCES dbo.roles(id)
    );

    -- Tránh gán trùng (user_id, role_id) khi chưa bị xóa mềm
    CREATE UNIQUE INDEX UX_user_roles_user_role_active
    ON dbo.user_roles(user_id, role_id)
    WHERE deleted_at IS NULL;

    CREATE INDEX IX_user_roles_user_id ON dbo.user_roles(user_id);
    CREATE INDEX IX_user_roles_role_id ON dbo.user_roles(role_id);
END
GO

