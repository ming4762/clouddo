--创建模块表
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[cloud_module]') AND type IN ('U'))
	DROP TABLE [dbo].[cloud_model]
GO

CREATE TABLE [dbo].[cloud_module] (
  [MODULE_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [MODULE_NAME] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [REMARK] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[cloud_model] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'模块ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_module',
'COLUMN', N'MODULE_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模块名称',
'SCHEMA', N'dbo',
'TABLE', N'cloud_module',
'COLUMN', N'MODULE_NAME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模块备注',
'SCHEMA', N'dbo',
'TABLE', N'cloud_module',
'COLUMN', N'REMARK'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模块表',
'SCHEMA', N'dbo',
'TABLE', N'cloud_module'
GO

ALTER TABLE [dbo].[cloud_module] ADD CONSTRAINT [PK__cloud_mo__238A8246173876EA] PRIMARY KEY CLUSTERED ([MODULE_ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
GO

--创建栏目表
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[cloud_column]') AND type IN ('U'))
	DROP TABLE [dbo].[cloud_column]
GO

CREATE TABLE [dbo].[cloud_column] (
  [COLUMN_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [COLUMN_NAME] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [REMARK] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [MODULE_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[cloud_column] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'栏目ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_column',
'COLUMN', N'COLUMN_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'栏目名称',
'SCHEMA', N'dbo',
'TABLE', N'cloud_column',
'COLUMN', N'COLUMN_NAME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'备注',
'SCHEMA', N'dbo',
'TABLE', N'cloud_column',
'COLUMN', N'REMARK'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模块ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_column',
'COLUMN', N'MODULE_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'栏目表',
'SCHEMA', N'dbo',
'TABLE', N'cloud_column'
GO

ALTER TABLE [dbo].[cloud_column] ADD CONSTRAINT [PK__cloud_co__D6F57493060DEAE8] PRIMARY KEY CLUSTERED ([COLUMN_ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
GO

--创建子栏目表
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[cloud_subsection]') AND type IN ('U'))
	DROP TABLE [dbo].[cloud_subsection]
GO

CREATE TABLE [dbo].[cloud_subsection] (
  [SUBSECTION_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [SUBSECTION_NAME] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [REMARK] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [COLUMN_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[cloud_subsection] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'子栏目ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_subsection',
'COLUMN', N'SUBSECTION_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'子栏目名称',
'SCHEMA', N'dbo',
'TABLE', N'cloud_subsection',
'COLUMN', N'SUBSECTION_NAME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'备注',
'SCHEMA', N'dbo',
'TABLE', N'cloud_subsection',
'COLUMN', N'REMARK'
GO

EXEC sp_addextendedproperty
'MS_Description', N'栏目ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_subsection',
'COLUMN', N'COLUMN_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'子栏目表',
'SCHEMA', N'dbo',
'TABLE', N'cloud_subsection'
GO

ALTER TABLE [dbo].[cloud_subsection] ADD CONSTRAINT [PK__cloud_su__F68BD7FE1B0907CE] PRIMARY KEY CLUSTERED ([SUBSECTION_ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
GO

--创建新闻表
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[cloud_news]') AND type IN ('U'))
	DROP TABLE [dbo].[cloud_news]
GO

CREATE TABLE [dbo].[cloud_news] (
  [NEWS_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [TITLE] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [SUBTITLE] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [TITLE_PIC] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [AUTHOR_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CREATE_TIME] datetime  NOT NULL,
  [MODIFIER_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [UPDATE_TIME] datetime  NULL,
  [CONTENT] varchar(max) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [COLUMN_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [PRAISE_NUM] int DEFAULT ((0)) NOT NULL,
  [READ_NUM] int DEFAULT ((0)) NOT NULL,
  [COMMENT_NUM] int DEFAULT ((0)) NOT NULL,
  [SUBSECTION_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[cloud_news] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'新闻ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'NEWS_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'新闻标题',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'TITLE'
GO

EXEC sp_addextendedproperty
'MS_Description', N'副标题',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'SUBTITLE'
GO

EXEC sp_addextendedproperty
'MS_Description', N'标题图片',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'TITLE_PIC'
GO

EXEC sp_addextendedproperty
'MS_Description', N'作者ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'AUTHOR_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'CREATE_TIME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改人ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'MODIFIER_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'UPDATE_TIME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'内容',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'CONTENT'
GO

EXEC sp_addextendedproperty
'MS_Description', N'栏目ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'COLUMN_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'点赞数',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'PRAISE_NUM'
GO

EXEC sp_addextendedproperty
'MS_Description', N'阅读数',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'READ_NUM'
GO

EXEC sp_addextendedproperty
'MS_Description', N'评论数',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'COMMENT_NUM'
GO

EXEC sp_addextendedproperty
'MS_Description', N'子栏目ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_news',
'COLUMN', N'SUBSECTION_ID'
GO

ALTER TABLE [dbo].[cloud_news] ADD CONSTRAINT [PK__cloud_ne__D55D99087F60ED59] PRIMARY KEY CLUSTERED ([NEWS_ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
GO

--创建评论表
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[cloud_comment]') AND type IN ('U'))
	DROP TABLE [dbo].[cloud_comment]
GO

CREATE TABLE [dbo].[cloud_comment] (
  [COMMENT_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CONTENT] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CREATE_TIME] datetime  NOT NULL,
  [USER_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [PRAISE_NUM] int DEFAULT ((0)) NOT NULL,
  [NEWS_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[cloud_comment] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'评论ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_comment',
'COLUMN', N'COMMENT_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'评论内容',
'SCHEMA', N'dbo',
'TABLE', N'cloud_comment',
'COLUMN', N'CONTENT'
GO

EXEC sp_addextendedproperty
'MS_Description', N'评论时间',
'SCHEMA', N'dbo',
'TABLE', N'cloud_comment',
'COLUMN', N'CREATE_TIME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'评论人ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_comment',
'COLUMN', N'USER_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'点赞数',
'SCHEMA', N'dbo',
'TABLE', N'cloud_comment',
'COLUMN', N'PRAISE_NUM'
GO

EXEC sp_addextendedproperty
'MS_Description', N'新闻ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_comment',
'COLUMN', N'NEWS_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'评论表',
'SCHEMA', N'dbo',
'TABLE', N'cloud_comment'
GO

ALTER TABLE [dbo].[cloud_comment] ADD CONSTRAINT [PK__cloud_co__C55F98D009DE7BCC] PRIMARY KEY CLUSTERED ([COMMENT_ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
GO

--创建跟帖表
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[cloud_follow]') AND type IN ('U'))
	DROP TABLE [dbo].[cloud_follow]
GO

CREATE TABLE [dbo].[cloud_follow] (
  [FOLLOW_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CONTENT] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CREATE_TIME] datetime  NOT NULL,
  [COMMENT_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [USER_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[cloud_follow] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'跟帖ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_follow',
'COLUMN', N'FOLLOW_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'内容',
'SCHEMA', N'dbo',
'TABLE', N'cloud_follow',
'COLUMN', N'CONTENT'
GO

EXEC sp_addextendedproperty
'MS_Description', N'跟帖时间',
'SCHEMA', N'dbo',
'TABLE', N'cloud_follow',
'COLUMN', N'CREATE_TIME'
GO

EXEC sp_addextendedproperty
'MS_Description', N'评论ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_follow',
'COLUMN', N'COMMENT_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_follow',
'COLUMN', N'USER_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'跟帖表',
'SCHEMA', N'dbo',
'TABLE', N'cloud_follow'
GO

ALTER TABLE [dbo].[cloud_follow] ADD CONSTRAINT [PK__cloud_fo__5E2515370EA330E9] PRIMARY KEY CLUSTERED ([FOLLOW_ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
GO


--人员点赞中间表
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[cloud_user_praise]') AND type IN ('U'))
	DROP TABLE [dbo].[cloud_user_praise]
GO

CREATE TABLE [dbo].[cloud_user_praise] (
  [NEWS_COMMENT_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [USER_ID] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [TYPE] varchar(10) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[cloud_user_praise] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'新闻/评论ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_user_praise',
'COLUMN', N'NEWS_COMMENT_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人员ID',
'SCHEMA', N'dbo',
'TABLE', N'cloud_user_praise',
'COLUMN', N'USER_ID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型（新闻/评论）',
'SCHEMA', N'dbo',
'TABLE', N'cloud_user_praise',
'COLUMN', N'TYPE'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人员点赞表',
'SCHEMA', N'dbo',
'TABLE', N'cloud_user_praise'
GO

ALTER TABLE [dbo].[cloud_user_praise] ADD CONSTRAINT [PK__cloud_us__F7C4F02E1367E606] PRIMARY KEY CLUSTERED ([NEWS_COMMENT_ID], [USER_ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
ON [PRIMARY]
GO




