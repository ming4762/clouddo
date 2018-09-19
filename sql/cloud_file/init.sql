--创建文件信息表
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[cloud_file]') AND type IN ('U'))
	DROP TABLE [dbo].[cloud_file]
GO

CREATE TABLE [dbo].[cloud_file] (
  [FILE_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [FILE_NAME] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CREATE_TIME] datetime  NOT NULL,
  [TYPE] varchar(10) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CONTENT_TYPE] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [SIZE] bigint  NOT NULL,
  [DB_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [MD5] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [SEQ] int  NULL
)
GO

ALTER TABLE [dbo].[cloud_file] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'FILE_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件名',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'FILE_NAME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'CREATE_TIME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'TYPE'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件类型',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'CONTENT_TYPE'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件大小',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'SIZE'
GO

EXEC sp_addextendedproperty
'MS_Description', N'存储在文件服务器的ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'DB_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件的MD5',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'MD5'
GO

EXEC sp_addextendedproperty
'MS_Description', N'序号',
'SCHEMA', N'dbo',
'TABLE', N'cloud_file',
'COLUMN', N'SEQ'
GO
-- Primary Key structure for table cloud_file
ALTER TABLE [dbo].[cloud_file] ADD CONSTRAINT [PK__cloud_fi__49C04C7A7F60ED59] PRIMARY KEY CLUSTERED ([FILE_ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
GO