USE eCommerce
GO

--Drop all table constraints
DECLARE @sql NVARCHAR(MAX) = N'';

SELECT @sql += N'
ALTER TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME(parent_object_id))
    + '.' + QUOTENAME(OBJECT_NAME(parent_object_id)) + 
    ' DROP CONSTRAINT ' + QUOTENAME(name) + ';'
FROM sys.foreign_keys;

PRINT @sql;
EXEC sp_executesql @sql;
--Drop table
IF OBJECT_ID('dbo.mcs_Category','U') IS NOT NULL
    DROP TABLE dbo.[mcs_Category];

CREATE TABLE [mcs_Category] (
	[CategoryId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[CategoryParentId] [int] NULL,
	[Name] [nvarchar](255) NOT NULL,
	[Description] [nvarchar](255) NULL
);

--Drop table
IF OBJECT_ID('dbo.mcs_Cart','U') IS NOT NULL
    DROP TABLE dbo.[mcs_Cart];

CREATE TABLE [mcs_Cart] (
	[CartId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[UserId] [nvarchar](450) NOT NULL,
	[Status] [nvarchar](255) NOT NULL,
	[Created_at] [datetime2] NOT NULL,
	[Updated_at] [datetime2] NOT NULL
);

--Drop table
IF OBJECT_ID('dbo.mcs_CartItem','U') IS NOT NULL
    DROP TABLE dbo.[mcs_CartItem];

CREATE TABLE [mcs_CartItem] (
	[CartItemId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[CartId] [int] NOT NULL,
	[ProductId] [int] NOT NULL,
	[Quantity] [int] NULL,
	[Created_at] [datetime2] NULL,
	[Updated_at] [datetime2] NULL
);

--Drop table
IF OBJECT_ID('dbo.mcs_Product','U') IS NOT NULL
    DROP TABLE dbo.[mcs_Product];

CREATE TABLE [mcs_Product] (
	[ProductId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
	[Title] [nvarchar](500) NULL,
	[Description] [nvarchar](500) NULL,
	[CategoryId] [int] NOT NULL,
	[Price] [decimal] NOT NULL,
	[DefaultPhoto] [nvarchar](500) NULL,
	[Status] [nvarchar](255) NULL,
	[Created_at] [datetime2] NOT NULL,
	[Updated_at] [datetime2] NULL
);

--Drop table
IF OBJECT_ID('dbo.mcs_Order','U') IS NOT NULL
    DROP TABLE dbo.[mcs_Order];

CREATE TABLE [mcs_Order] (
	[OrderId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[UserId] [nvarchar](450) NOT NULL,
	[CartId] [int] NOT NULL,
	[Created_at] [datetime2] NULL,
	[Cost] [decimal] NULL,
	[Tax] [decimal] NULL,
	[Total] [decimal] NULL,
	[Paid] [bit] DEFAULT 0,
	[Currency] [nvarchar](50) NULL
);

--Drop table
IF OBJECT_ID('dbo.mcs_Payment','U') IS NOT NULL
    DROP TABLE dbo.[mcs_Payment];

CREATE TABLE [mcs_Payment] (
	[PaymentId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[UserId] [nvarchar](450) NOT NULL,
	[PaymentType] [nvarchar](50) NULL,
	[Created_at] [datetime2] NULL,
	[Amount] [decimal] NULL,
	[Currency] [nvarchar](50) NULL,
	[OrderId] [int] NOT NULL,
	[AddressId] [int] NOT NULL
);

--Drop table
IF OBJECT_ID('dbo.mcs_Shipping','U') IS NOT NULL
    DROP TABLE dbo.[mcs_Shipping];

CREATE TABLE [mcs_Shipping] (
	[ShippingId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[OrderId] [int] NOT NULL,
	[AddressId] [int] NOT NULL,
	[ShippingMethod] [nvarchar](50) NULL,
	[Status] [nvarchar](50) NULL,
	[ShippingProvider] [nvarchar](50) NULL,
	[Cost] [decimal] NULL
);

--Drop table
IF OBJECT_ID('dbo.mcs_Address','U') IS NOT NULL
    DROP TABLE dbo.[mcs_Address];

CREATE TABLE [mcs_Address] (
	[AddressId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[UserId] [nvarchar](450) NOT NULL,
	[Name] [nvarchar](50) NULL,
	[GPS] [nvarchar](255) NULL,
	[Country] [nvarchar](50) NULL,
	[City] [nvarchar](50) NULL,
	[ZipCode] [nvarchar](50) NULL,
	[CountryCode] [nvarchar](50) NULL,
	[Detail] [nvarchar](255) NULL,
	[Primary] [bit] NULL,
	[Active] [bit] DEFAULT 1
);

--Drop table
IF OBJECT_ID('dbo.mcs_Stock','U') IS NOT NULL
    DROP TABLE dbo.[mcs_Stock];

CREATE TABLE [mcs_Stock] (
	[StockId] [int] PRIMARY KEY IDENTITY (1,1) NOT NULL,
	[ProductId] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[EntryPrice] [decimal] NULL,
	[EntryDate] [datetime2] NULL
);

--Drop table
IF OBJECT_ID('dbo.mcs_ProductPhoto','U') IS NOT NULL
    DROP TABLE dbo.[mcs_ProductPhoto];

CREATE TABLE [mcs_ProductPhoto] (
	[ProductPhotoId] [uniqueidentifier] PRIMARY KEY DEFAULT NEWID() NOT NULL,
	[ProductId] [int] NOT NULL,
	[Url] [nvarchar](500) NOT NULL
);
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Product_mcs_Category]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Product]'))
ALTER TABLE [mcs_Product] ADD CONSTRAINT FK_mcs_Product_mcs_Category FOREIGN KEY ([CategoryId]) REFERENCES [mcs_Category] ([CategoryId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Category_mcs_Product_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Category_mcs_Product_List;
GO
CREATE PROCEDURE sp_mcs_Category_mcs_Product_List(
@CategoryId int
)
AS
	SELECT * FROM [mcs_Product] WHERE [CategoryId] = @CategoryId 
RETURN
GO
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Category_mcs_Category]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Category]'))
ALTER TABLE [mcs_Category] ADD CONSTRAINT FK_mcs_Category_mcs_Category FOREIGN KEY ([CategoryParentId]) REFERENCES [mcs_Category] ([CategoryId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Category_mcs_Category_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Category_mcs_Category_List;
GO
CREATE PROCEDURE sp_mcs_Category_mcs_Category_List(
@CategoryParentId int
)
AS
	SELECT * FROM [mcs_Category] WHERE [CategoryParentId] = @CategoryParentId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_Category_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Category_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_Category_Insert 
	@CategoryId int,
	@CategoryParentId int,
	@Name nvarchar(255),
	@Description nvarchar(255)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Category]
		([CategoryParentId], 
		[Name], 
		[Description])
	VALUES (@CategoryParentId, 
		@Name, 
		@Description)
END
GO

IF OBJECT_ID ('sp_mcs_Category_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Category_Update;  
GO

CREATE PROCEDURE sp_mcs_Category_Update
		@CategoryId int,
	@CategoryParentId int,
	@Name nvarchar(255),
	@Description nvarchar(255)
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_Category] SET 
		[CategoryParentId] = @CategoryParentId,
		[Name] = @Name,
		[Description] = @Description
	WHERE CategoryId = @CategoryId
END
GO

IF OBJECT_ID ('sp_mcs_Category_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Category_Delete;  
GO

CREATE PROCEDURE sp_mcs_Category_Delete
	@CategoryId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Category] WHERE CategoryId = @CategoryId
END
GO


IF OBJECT_ID('sp_mcs_Category_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Category_Select;  
GO  

CREATE PROCEDURE sp_mcs_Category_Select(
	@CategoryId int
)
AS    
   SELECT * FROM [mcs_Category] WHERE CategoryId = @CategoryId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_Category_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Category_List;  
GO  

CREATE PROCEDURE sp_mcs_Category_List(
	@pageNumber int = 0, 
	@pageSize int = 100
)
AS    
   SELECT * FROM [mcs_Category]
	
	 ORDER BY CategoryId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_CartItem_mcs_Cart]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_CartItem]'))
ALTER TABLE [mcs_CartItem] ADD CONSTRAINT FK_mcs_CartItem_mcs_Cart FOREIGN KEY ([CartId]) REFERENCES [mcs_Cart] ([CartId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Cart_mcs_CartItem_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Cart_mcs_CartItem_List;
GO
CREATE PROCEDURE sp_mcs_Cart_mcs_CartItem_List(
@CartId int
)
AS
	SELECT * FROM [mcs_CartItem] WHERE [CartId] = @CartId 
RETURN
GO
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Order_mcs_Cart]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Order]'))
ALTER TABLE [mcs_Order] ADD CONSTRAINT FK_mcs_Order_mcs_Cart FOREIGN KEY ([CartId]) REFERENCES [mcs_Cart] ([CartId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Cart_mcs_Order_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Cart_mcs_Order_List;
GO
CREATE PROCEDURE sp_mcs_Cart_mcs_Order_List(
@CartId int
)
AS
	SELECT * FROM [mcs_Order] WHERE [CartId] = @CartId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_Cart_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Cart_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_Cart_Insert 
	@CartId int,
	@UserId nvarchar(450),
	@Status nvarchar(255),
	@Created_at datetime2,
	@Updated_at datetime2
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Cart]
		([UserId], 
		[Status], 
		[Created_at], 
		[Updated_at])
	VALUES (@UserId, 
		@Status, 
		@Created_at, 
		@Updated_at)
END
GO

IF OBJECT_ID ('sp_mcs_Cart_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Cart_Update;  
GO

CREATE PROCEDURE sp_mcs_Cart_Update
		@CartId int,
	@UserId nvarchar(450),
	@Status nvarchar(255),
	@Created_at datetime2,
	@Updated_at datetime2
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_Cart] SET 
		[UserId] = @UserId,
		[Status] = @Status,
		[Created_at] = @Created_at,
		[Updated_at] = @Updated_at
	WHERE CartId = @CartId
END
GO

IF OBJECT_ID ('sp_mcs_Cart_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Cart_Delete;  
GO

CREATE PROCEDURE sp_mcs_Cart_Delete
	@UserId nvarchar(450) = NULL,
	@CartId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Cart] WHERE CartId = @CartId and UserId = @UserId
END
GO


IF OBJECT_ID('sp_mcs_Cart_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Cart_Select;  
GO  

CREATE PROCEDURE sp_mcs_Cart_Select(
	@CartId int
)
AS    
   SELECT * FROM [mcs_Cart] WHERE CartId = @CartId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_Cart_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Cart_List;  
GO  

CREATE PROCEDURE sp_mcs_Cart_List(
	@pageNumber int = 0, 
	@pageSize int = 100,
	@UserId nvarchar(450) = NULL
)
AS    
   SELECT * FROM [mcs_Cart]
	WHERE @UserId Is Null OR UserId = @UserId
	 ORDER BY CartId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_CartItem_mcs_Cart]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_CartItem]'))
ALTER TABLE [mcs_CartItem] ADD CONSTRAINT FK_mcs_CartItem_mcs_Cart FOREIGN KEY ([CartId]) REFERENCES [mcs_Cart] ([CartId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Cart_mcs_CartItem_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Cart_mcs_CartItem_List;
GO
CREATE PROCEDURE sp_mcs_Cart_mcs_CartItem_List(
@CartId int
)
AS
	SELECT * FROM [mcs_CartItem] WHERE [CartId] = @CartId 
RETURN
GO
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_CartItem_mcs_Product]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_CartItem]'))
ALTER TABLE [mcs_CartItem] ADD CONSTRAINT FK_mcs_CartItem_mcs_Product FOREIGN KEY ([ProductId]) REFERENCES [mcs_Product] ([ProductId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Product_mcs_CartItem_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Product_mcs_CartItem_List;
GO
CREATE PROCEDURE sp_mcs_Product_mcs_CartItem_List(
@ProductId int
)
AS
	SELECT * FROM [mcs_CartItem] WHERE [ProductId] = @ProductId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_CartItem_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_CartItem_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_CartItem_Insert 
	@CartItemId int,
	@CartId int,
	@ProductId int,
	@Quantity int,
	@Created_at datetime2,
	@Updated_at datetime2
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_CartItem]
		([CartId], 
		[ProductId], 
		[Quantity], 
		[Created_at], 
		[Updated_at])
	VALUES (@CartId, 
		@ProductId, 
		@Quantity, 
		@Created_at, 
		@Updated_at)
END
GO

IF OBJECT_ID ('sp_mcs_CartItem_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_CartItem_Update;  
GO

CREATE PROCEDURE sp_mcs_CartItem_Update
		@CartItemId int,
	@CartId int,
	@ProductId int,
	@Quantity int,
	@Created_at datetime2,
	@Updated_at datetime2
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_CartItem] SET 
		[CartId] = @CartId,
		[ProductId] = @ProductId,
		[Quantity] = @Quantity,
		[Created_at] = @Created_at,
		[Updated_at] = @Updated_at
	WHERE CartItemId = @CartItemId
END
GO

IF OBJECT_ID ('sp_mcs_CartItem_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_CartItem_Delete;  
GO

CREATE PROCEDURE sp_mcs_CartItem_Delete
	@CartItemId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_CartItem] WHERE CartItemId = @CartItemId
END
GO


IF OBJECT_ID('sp_mcs_CartItem_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_CartItem_Select;  
GO  

CREATE PROCEDURE sp_mcs_CartItem_Select(
	@CartItemId int
)
AS    
   SELECT * FROM [mcs_CartItem] WHERE CartItemId = @CartItemId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_CartItem_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_CartItem_List;  
GO  

CREATE PROCEDURE sp_mcs_CartItem_List(
	@pageNumber int = 0, 
	@pageSize int = 100
)
AS    
   SELECT * FROM [mcs_CartItem]
	
	 ORDER BY CartItemId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Product_mcs_Category]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Product]'))
ALTER TABLE [mcs_Product] ADD CONSTRAINT FK_mcs_Product_mcs_Category FOREIGN KEY ([CategoryId]) REFERENCES [mcs_Category] ([CategoryId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Category_mcs_Product_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Category_mcs_Product_List;
GO
CREATE PROCEDURE sp_mcs_Category_mcs_Product_List(
@CategoryId int
)
AS
	SELECT * FROM [mcs_Product] WHERE [CategoryId] = @CategoryId 
RETURN
GO
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Stock_mcs_Product]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Stock]'))
ALTER TABLE [mcs_Stock] ADD CONSTRAINT FK_mcs_Stock_mcs_Product FOREIGN KEY ([ProductId]) REFERENCES [mcs_Product] ([ProductId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Product_mcs_Stock_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Product_mcs_Stock_List;
GO
CREATE PROCEDURE sp_mcs_Product_mcs_Stock_List(
@ProductId int
)
AS
	SELECT * FROM [mcs_Stock] WHERE [ProductId] = @ProductId 
RETURN
GO
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_CartItem_mcs_Product]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_CartItem]'))
ALTER TABLE [mcs_CartItem] ADD CONSTRAINT FK_mcs_CartItem_mcs_Product FOREIGN KEY ([ProductId]) REFERENCES [mcs_Product] ([ProductId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Product_mcs_CartItem_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Product_mcs_CartItem_List;
GO
CREATE PROCEDURE sp_mcs_Product_mcs_CartItem_List(
@ProductId int
)
AS
	SELECT * FROM [mcs_CartItem] WHERE [ProductId] = @ProductId 
RETURN
GO
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_ProductPhoto_mcs_Product]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_ProductPhoto]'))
ALTER TABLE [mcs_ProductPhoto] ADD CONSTRAINT FK_mcs_ProductPhoto_mcs_Product FOREIGN KEY ([ProductId]) REFERENCES [mcs_Product] ([ProductId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Product_mcs_ProductPhoto_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Product_mcs_ProductPhoto_List;
GO
CREATE PROCEDURE sp_mcs_Product_mcs_ProductPhoto_List(
@ProductId int
)
AS
	SELECT * FROM [mcs_ProductPhoto] WHERE [ProductId] = @ProductId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_Product_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Product_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_Product_Insert 
	@ProductId int,
	@Name nvarchar(255),
	@Title nvarchar(500),
	@Description nvarchar(500),
	@CategoryId int,
	@Price decimal,
	@DefaultPhoto nvarchar(500),
	@Status nvarchar(255),
	@Created_at datetime2,
	@Updated_at datetime2
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Product]
		([Name], 
		[Title], 
		[Description], 
		[CategoryId], 
		[Price], 
		[DefaultPhoto], 
		[Status], 
		[Created_at], 
		[Updated_at])
	VALUES (@Name, 
		@Title, 
		@Description, 
		@CategoryId, 
		@Price, 
		@DefaultPhoto, 
		@Status, 
		@Created_at, 
		@Updated_at)
END
GO

IF OBJECT_ID ('sp_mcs_Product_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Product_Update;  
GO

CREATE PROCEDURE sp_mcs_Product_Update
		@ProductId int,
	@Name nvarchar(255),
	@Title nvarchar(500),
	@Description nvarchar(500),
	@CategoryId int,
	@Price decimal,
	@DefaultPhoto nvarchar(500),
	@Status nvarchar(255),
	@Created_at datetime2,
	@Updated_at datetime2
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_Product] SET 
		[Name] = @Name,
		[Title] = @Title,
		[Description] = @Description,
		[CategoryId] = @CategoryId,
		[Price] = @Price,
		[DefaultPhoto] = @DefaultPhoto,
		[Status] = @Status,
		[Created_at] = @Created_at,
		[Updated_at] = @Updated_at
	WHERE ProductId = @ProductId
END
GO

IF OBJECT_ID ('sp_mcs_Product_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Product_Delete;  
GO

CREATE PROCEDURE sp_mcs_Product_Delete
	@ProductId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Product] WHERE ProductId = @ProductId
END
GO


IF OBJECT_ID('sp_mcs_Product_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Product_Select;  
GO  

CREATE PROCEDURE sp_mcs_Product_Select(
	@ProductId int
)
AS    
   SELECT * FROM [mcs_Product] WHERE ProductId = @ProductId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_Product_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Product_List;  
GO  

CREATE PROCEDURE sp_mcs_Product_List(
	@pageNumber int = 0, 
	@pageSize int = 100
)
AS    
   SELECT * FROM [mcs_Product]
	
	 ORDER BY ProductId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Shipping_mcs_Order]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Shipping]'))
ALTER TABLE [mcs_Shipping] ADD CONSTRAINT FK_mcs_Shipping_mcs_Order FOREIGN KEY ([OrderId]) REFERENCES [mcs_Order] ([OrderId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Order_mcs_Shipping_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Order_mcs_Shipping_List;
GO
CREATE PROCEDURE sp_mcs_Order_mcs_Shipping_List(
@OrderId int
)
AS
	SELECT * FROM [mcs_Shipping] WHERE [OrderId] = @OrderId 
RETURN
GO
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Order_mcs_Cart]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Order]'))
ALTER TABLE [mcs_Order] ADD CONSTRAINT FK_mcs_Order_mcs_Cart FOREIGN KEY ([CartId]) REFERENCES [mcs_Cart] ([CartId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Cart_mcs_Order_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Cart_mcs_Order_List;
GO
CREATE PROCEDURE sp_mcs_Cart_mcs_Order_List(
@CartId int
)
AS
	SELECT * FROM [mcs_Order] WHERE [CartId] = @CartId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_Order_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Order_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_Order_Insert 
	@OrderId int,
	@UserId nvarchar(450),
	@CartId int,
	@Created_at datetime2,
	@Cost decimal,
	@Tax decimal,
	@Total decimal,
	@Paid bit = 0,
	@Currency nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Order]
		([UserId], 
		[CartId], 
		[Created_at], 
		[Cost], 
		[Tax], 
		[Total], 
		[Paid], 
		[Currency])
	VALUES (@UserId, 
		@CartId, 
		@Created_at, 
		@Cost, 
		@Tax, 
		@Total, 
		@Paid, 
		@Currency)
END
GO

IF OBJECT_ID ('sp_mcs_Order_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Order_Update;  
GO

CREATE PROCEDURE sp_mcs_Order_Update
		@OrderId int,
	@UserId nvarchar(450),
	@CartId int,
	@Created_at datetime2,
	@Cost decimal,
	@Tax decimal,
	@Total decimal,
	@Paid bit = 0,
	@Currency nvarchar(50)
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_Order] SET 
		[UserId] = @UserId,
		[CartId] = @CartId,
		[Created_at] = @Created_at,
		[Cost] = @Cost,
		[Tax] = @Tax,
		[Total] = @Total,
		[Paid] = @Paid,
		[Currency] = @Currency
	WHERE OrderId = @OrderId
END
GO

IF OBJECT_ID ('sp_mcs_Order_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Order_Delete;  
GO

CREATE PROCEDURE sp_mcs_Order_Delete
	@UserId nvarchar(450) = NULL,
	@OrderId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Order] WHERE OrderId = @OrderId and UserId = @UserId
END
GO


IF OBJECT_ID('sp_mcs_Order_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Order_Select;  
GO  

CREATE PROCEDURE sp_mcs_Order_Select(
	@OrderId int
)
AS    
   SELECT * FROM [mcs_Order] WHERE OrderId = @OrderId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_Order_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Order_List;  
GO  

CREATE PROCEDURE sp_mcs_Order_List(
	@pageNumber int = 0, 
	@pageSize int = 100,
	@UserId nvarchar(450) = NULL
)
AS    
   SELECT * FROM [mcs_Order]
	WHERE @UserId Is Null OR UserId = @UserId
	 ORDER BY OrderId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  



IF OBJECT_ID ('sp_mcs_Payment_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Payment_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_Payment_Insert 
	@PaymentId int,
	@UserId nvarchar(450),
	@PaymentType nvarchar(50),
	@Created_at datetime2,
	@Amount decimal,
	@Currency nvarchar(50),
	@OrderId int,
	@AddressId int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Payment]
		([UserId], 
		[PaymentType], 
		[Created_at], 
		[Amount], 
		[Currency], 
		[OrderId], 
		[AddressId])
	VALUES (@UserId, 
		@PaymentType, 
		@Created_at, 
		@Amount, 
		@Currency, 
		@OrderId, 
		@AddressId)
END
GO

IF OBJECT_ID ('sp_mcs_Payment_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Payment_Update;  
GO

CREATE PROCEDURE sp_mcs_Payment_Update
		@PaymentId int,
	@UserId nvarchar(450),
	@PaymentType nvarchar(50),
	@Created_at datetime2,
	@Amount decimal,
	@Currency nvarchar(50),
	@OrderId int,
	@AddressId int
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_Payment] SET 
		[UserId] = @UserId,
		[PaymentType] = @PaymentType,
		[Created_at] = @Created_at,
		[Amount] = @Amount,
		[Currency] = @Currency,
		[OrderId] = @OrderId,
		[AddressId] = @AddressId
	WHERE PaymentId = @PaymentId
END
GO

IF OBJECT_ID ('sp_mcs_Payment_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Payment_Delete;  
GO

CREATE PROCEDURE sp_mcs_Payment_Delete
	@UserId nvarchar(450) = NULL,
	@PaymentId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Payment] WHERE PaymentId = @PaymentId and UserId = @UserId
END
GO


IF OBJECT_ID('sp_mcs_Payment_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Payment_Select;  
GO  

CREATE PROCEDURE sp_mcs_Payment_Select(
	@PaymentId int
)
AS    
   SELECT * FROM [mcs_Payment] WHERE PaymentId = @PaymentId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_Payment_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Payment_List;  
GO  

CREATE PROCEDURE sp_mcs_Payment_List(
	@pageNumber int = 0, 
	@pageSize int = 100,
	@UserId nvarchar(450) = NULL
)
AS    
   SELECT * FROM [mcs_Payment]
	WHERE @UserId Is Null OR UserId = @UserId
	 ORDER BY PaymentId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Shipping_mcs_Order]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Shipping]'))
ALTER TABLE [mcs_Shipping] ADD CONSTRAINT FK_mcs_Shipping_mcs_Order FOREIGN KEY ([OrderId]) REFERENCES [mcs_Order] ([OrderId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Order_mcs_Shipping_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Order_mcs_Shipping_List;
GO
CREATE PROCEDURE sp_mcs_Order_mcs_Shipping_List(
@OrderId int
)
AS
	SELECT * FROM [mcs_Shipping] WHERE [OrderId] = @OrderId 
RETURN
GO
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Shipping_mcs_Address]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Shipping]'))
ALTER TABLE [mcs_Shipping] ADD CONSTRAINT FK_mcs_Shipping_mcs_Address FOREIGN KEY ([AddressId]) REFERENCES [mcs_Address] ([AddressId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Address_mcs_Shipping_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Address_mcs_Shipping_List;
GO
CREATE PROCEDURE sp_mcs_Address_mcs_Shipping_List(
@AddressId int
)
AS
	SELECT * FROM [mcs_Shipping] WHERE [AddressId] = @AddressId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_Shipping_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Shipping_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_Shipping_Insert 
	@ShippingId int,
	@OrderId int,
	@AddressId int,
	@ShippingMethod nvarchar(50),
	@Status nvarchar(50),
	@ShippingProvider nvarchar(50),
	@Cost decimal
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Shipping]
		([OrderId], 
		[AddressId], 
		[ShippingMethod], 
		[Status], 
		[ShippingProvider], 
		[Cost])
	VALUES (@OrderId, 
		@AddressId, 
		@ShippingMethod, 
		@Status, 
		@ShippingProvider, 
		@Cost)
END
GO

IF OBJECT_ID ('sp_mcs_Shipping_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Shipping_Update;  
GO

CREATE PROCEDURE sp_mcs_Shipping_Update
		@ShippingId int,
	@OrderId int,
	@AddressId int,
	@ShippingMethod nvarchar(50),
	@Status nvarchar(50),
	@ShippingProvider nvarchar(50),
	@Cost decimal
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_Shipping] SET 
		[OrderId] = @OrderId,
		[AddressId] = @AddressId,
		[ShippingMethod] = @ShippingMethod,
		[Status] = @Status,
		[ShippingProvider] = @ShippingProvider,
		[Cost] = @Cost
	WHERE ShippingId = @ShippingId
END
GO

IF OBJECT_ID ('sp_mcs_Shipping_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Shipping_Delete;  
GO

CREATE PROCEDURE sp_mcs_Shipping_Delete
	@ShippingId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Shipping] WHERE ShippingId = @ShippingId
END
GO


IF OBJECT_ID('sp_mcs_Shipping_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Shipping_Select;  
GO  

CREATE PROCEDURE sp_mcs_Shipping_Select(
	@ShippingId int
)
AS    
   SELECT * FROM [mcs_Shipping] WHERE ShippingId = @ShippingId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_Shipping_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Shipping_List;  
GO  

CREATE PROCEDURE sp_mcs_Shipping_List(
	@pageNumber int = 0, 
	@pageSize int = 100
)
AS    
   SELECT * FROM [mcs_Shipping]
	
	 ORDER BY ShippingId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Shipping_mcs_Address]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Shipping]'))
ALTER TABLE [mcs_Shipping] ADD CONSTRAINT FK_mcs_Shipping_mcs_Address FOREIGN KEY ([AddressId]) REFERENCES [mcs_Address] ([AddressId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Address_mcs_Shipping_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Address_mcs_Shipping_List;
GO
CREATE PROCEDURE sp_mcs_Address_mcs_Shipping_List(
@AddressId int
)
AS
	SELECT * FROM [mcs_Shipping] WHERE [AddressId] = @AddressId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_Address_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Address_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_Address_Insert 
	@AddressId int,
	@UserId nvarchar(450),
	@Name nvarchar(50),
	@GPS nvarchar(255),
	@Country nvarchar(50),
	@City nvarchar(50),
	@ZipCode nvarchar(50),
	@CountryCode nvarchar(50),
	@Detail nvarchar(255),
	@Primary bit,
	@Active bit = 1
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Address]
		([UserId], 
		[Name], 
		[GPS], 
		[Country], 
		[City], 
		[ZipCode], 
		[CountryCode], 
		[Detail], 
		[Primary], 
		[Active])
	VALUES (@UserId, 
		@Name, 
		@GPS, 
		@Country, 
		@City, 
		@ZipCode, 
		@CountryCode, 
		@Detail, 
		@Primary, 
		@Active)
END
GO

IF OBJECT_ID ('sp_mcs_Address_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Address_Update;  
GO

CREATE PROCEDURE sp_mcs_Address_Update
		@AddressId int,
	@UserId nvarchar(450),
	@Name nvarchar(50),
	@GPS nvarchar(255),
	@Country nvarchar(50),
	@City nvarchar(50),
	@ZipCode nvarchar(50),
	@CountryCode nvarchar(50),
	@Detail nvarchar(255),
	@Primary bit,
	@Active bit = 1
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_Address] SET 
		[UserId] = @UserId,
		[Name] = @Name,
		[GPS] = @GPS,
		[Country] = @Country,
		[City] = @City,
		[ZipCode] = @ZipCode,
		[CountryCode] = @CountryCode,
		[Detail] = @Detail,
		[Primary] = @Primary,
		[Active] = @Active
	WHERE AddressId = @AddressId
END
GO

IF OBJECT_ID ('sp_mcs_Address_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Address_Delete;  
GO

CREATE PROCEDURE sp_mcs_Address_Delete
	@UserId nvarchar(450) = NULL,
	@AddressId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Address] WHERE AddressId = @AddressId and UserId = @UserId
END
GO


IF OBJECT_ID('sp_mcs_Address_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Address_Select;  
GO  

CREATE PROCEDURE sp_mcs_Address_Select(
	@AddressId int
)
AS    
   SELECT * FROM [mcs_Address] WHERE AddressId = @AddressId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_Address_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Address_List;  
GO  

CREATE PROCEDURE sp_mcs_Address_List(
	@pageNumber int = 0, 
	@pageSize int = 100,
	@UserId nvarchar(450) = NULL
)
AS    
   SELECT * FROM [mcs_Address]
	WHERE @UserId Is Null OR UserId = @UserId
	 ORDER BY AddressId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_Stock_mcs_Product]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_Stock]'))
ALTER TABLE [mcs_Stock] ADD CONSTRAINT FK_mcs_Stock_mcs_Product FOREIGN KEY ([ProductId]) REFERENCES [mcs_Product] ([ProductId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Product_mcs_Stock_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Product_mcs_Stock_List;
GO
CREATE PROCEDURE sp_mcs_Product_mcs_Stock_List(
@ProductId int
)
AS
	SELECT * FROM [mcs_Stock] WHERE [ProductId] = @ProductId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_Stock_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Stock_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_Stock_Insert 
	@StockId int,
	@ProductId int,
	@Quantity int,
	@EntryPrice decimal,
	@EntryDate datetime2
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Stock]
		([ProductId], 
		[Quantity], 
		[EntryPrice], 
		[EntryDate])
	VALUES (@ProductId, 
		@Quantity, 
		@EntryPrice, 
		@EntryDate)
END
GO

IF OBJECT_ID ('sp_mcs_Stock_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Stock_Update;  
GO

CREATE PROCEDURE sp_mcs_Stock_Update
		@StockId int,
	@ProductId int,
	@Quantity int,
	@EntryPrice decimal,
	@EntryDate datetime2
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_Stock] SET 
		[ProductId] = @ProductId,
		[Quantity] = @Quantity,
		[EntryPrice] = @EntryPrice,
		[EntryDate] = @EntryDate
	WHERE StockId = @StockId
END
GO

IF OBJECT_ID ('sp_mcs_Stock_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_Stock_Delete;  
GO

CREATE PROCEDURE sp_mcs_Stock_Delete
	@StockId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Stock] WHERE StockId = @StockId
END
GO


IF OBJECT_ID('sp_mcs_Stock_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Stock_Select;  
GO  

CREATE PROCEDURE sp_mcs_Stock_Select(
	@StockId int
)
AS    
   SELECT * FROM [mcs_Stock] WHERE StockId = @StockId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_Stock_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_Stock_List;  
GO  

CREATE PROCEDURE sp_mcs_Stock_List(
	@pageNumber int = 0, 
	@pageSize int = 100
)
AS    
   SELECT * FROM [mcs_Stock]
	
	 ORDER BY StockId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  
IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_mcs_ProductPhoto_mcs_Product]') AND parent_object_id = OBJECT_ID(N'[dbo].[mcs_ProductPhoto]'))
ALTER TABLE [mcs_ProductPhoto] ADD CONSTRAINT FK_mcs_ProductPhoto_mcs_Product FOREIGN KEY ([ProductId]) REFERENCES [mcs_Product] ([ProductId])
	ON DELETE CASCADE
	ON UPDATE CASCADE
GO
IF OBJECT_ID('sp_mcs_Product_mcs_ProductPhoto_List', 'P') IS NOT NULL
	DROP PROCEDURE sp_mcs_Product_mcs_ProductPhoto_List;
GO
CREATE PROCEDURE sp_mcs_Product_mcs_ProductPhoto_List(
@ProductId int
)
AS
	SELECT * FROM [mcs_ProductPhoto] WHERE [ProductId] = @ProductId 
RETURN
GO



IF OBJECT_ID ('sp_mcs_ProductPhoto_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_ProductPhoto_Insert;  
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE sp_mcs_ProductPhoto_Insert 
	@ProductPhotoId uniqueidentifier,
	@ProductId int,
	@Url nvarchar(500)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_ProductPhoto]
		([ProductId], 
		[Url])
	VALUES (@ProductId, 
		@Url)
END
GO

IF OBJECT_ID ('sp_mcs_ProductPhoto_Update', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_ProductPhoto_Update;  
GO

CREATE PROCEDURE sp_mcs_ProductPhoto_Update
		@ProductPhotoId uniqueidentifier,
	@ProductId int,
	@Url nvarchar(500)
AS
BEGIN
	SET NOCOUNT ON;
	UPDATE [mcs_ProductPhoto] SET 
		[ProductId] = @ProductId,
		[Url] = @Url
	WHERE ProductPhotoId = @ProductPhotoId
END
GO

IF OBJECT_ID ('sp_mcs_ProductPhoto_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE sp_mcs_ProductPhoto_Delete;  
GO

CREATE PROCEDURE sp_mcs_ProductPhoto_Delete
	@ProductPhotoId uniqueidentifier
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_ProductPhoto] WHERE ProductPhotoId = @ProductPhotoId
END
GO


IF OBJECT_ID('sp_mcs_ProductPhoto_Select', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_ProductPhoto_Select;  
GO  

CREATE PROCEDURE sp_mcs_ProductPhoto_Select(
	@ProductPhotoId uniqueidentifier
)
AS    
   SELECT * FROM [mcs_ProductPhoto] WHERE ProductPhotoId = @ProductPhotoId
RETURN  
GO  

IF OBJECT_ID('sp_mcs_ProductPhoto_List', 'P') IS NOT NULL  
   DROP PROCEDURE sp_mcs_ProductPhoto_List;  
GO  

CREATE PROCEDURE sp_mcs_ProductPhoto_List(
	@pageNumber int = 0, 
	@pageSize int = 100
)
AS    
   SELECT * FROM [mcs_ProductPhoto]
	
	 ORDER BY ProductPhotoId DESC
	 OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
RETURN  
GO  

/****** Object:  Table [dbo].[AspNetRoleClaims]    Script Date: 6/4/2018 10:18:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AspNetRoleClaims]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AspNetRoleClaims](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ClaimType] [nvarchar](max) NULL,
	[ClaimValue] [nvarchar](max) NULL,
	[RoleId] [nvarchar](450) NOT NULL,
 CONSTRAINT [PK_AspNetRoleClaims] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
/****** Object:  Table [dbo].[AspNetRoles]    Script Date: 6/4/2018 10:18:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AspNetRoles]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AspNetRoles](
	[Id] [nvarchar](450) NOT NULL,
	[ConcurrencyStamp] [nvarchar](max) NULL,
	[Name] [nvarchar](256) NULL,
	[NormalizedName] [nvarchar](256) NULL,
 CONSTRAINT [PK_AspNetRoles] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
/****** Object:  Table [dbo].[AspNetUserClaims]    Script Date: 6/4/2018 10:18:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AspNetUserClaims]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AspNetUserClaims](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ClaimType] [nvarchar](max) NULL,
	[ClaimValue] [nvarchar](max) NULL,
	[UserId] [nvarchar](450) NOT NULL,
 CONSTRAINT [PK_AspNetUserClaims] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
/****** Object:  Table [dbo].[AspNetUserLogins]    Script Date: 6/4/2018 10:18:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AspNetUserLogins]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AspNetUserLogins](
	[LoginProvider] [nvarchar](450) NOT NULL,
	[ProviderKey] [nvarchar](450) NOT NULL,
	[ProviderDisplayName] [nvarchar](max) NULL,
	[UserId] [nvarchar](450) NOT NULL,
 CONSTRAINT [PK_AspNetUserLogins] PRIMARY KEY CLUSTERED 
(
	[LoginProvider] ASC,
	[ProviderKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
/****** Object:  Table [dbo].[AspNetUserRoles]    Script Date: 6/4/2018 10:18:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AspNetUserRoles]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AspNetUserRoles](
	[UserId] [nvarchar](450) NOT NULL,
	[RoleId] [nvarchar](450) NOT NULL,
 CONSTRAINT [PK_AspNetUserRoles] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC,
	[RoleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
/****** Object:  Table [dbo].[AspNetUsers]    Script Date: 6/4/2018 10:18:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AspNetUsers]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AspNetUsers](
	[Id] [nvarchar](450) NOT NULL,
	[AccessFailedCount] [int] NOT NULL,
	[ConcurrencyStamp] [nvarchar](max) NULL,
	[Email] [nvarchar](256) NULL,
	[EmailConfirmed] [bit] NOT NULL,
	[LockoutEnabled] [bit] NOT NULL,
	[LockoutEnd] [datetimeoffset](7) NULL,
	[NormalizedEmail] [nvarchar](256) NULL,
	[NormalizedUserName] [nvarchar](256) NULL,
	[PasswordHash] [nvarchar](max) NULL,
	[PhoneNumber] [nvarchar](max) NULL,
	[PhoneNumberConfirmed] [bit] NOT NULL,
	[SecurityStamp] [nvarchar](max) NULL,
	[TwoFactorEnabled] [bit] NOT NULL,
	[UserName] [nvarchar](256) NULL,
 CONSTRAINT [PK_AspNetUsers] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
/****** Object:  Table [dbo].[AspNetUserTokens]    Script Date: 6/4/2018 10:18:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AspNetUserTokens]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AspNetUserTokens](
	[UserId] [nvarchar](450) NOT NULL,
	[LoginProvider] [nvarchar](450) NOT NULL,
	[Name] [nvarchar](450) NOT NULL,
	[Value] [nvarchar](max) NULL,
 CONSTRAINT [PK_AspNetUserTokens] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC,
	[LoginProvider] ASC,
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetRoleClaims_AspNetRoles_RoleId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetRoleClaims]'))
ALTER TABLE [dbo].[AspNetRoleClaims]  WITH CHECK ADD  CONSTRAINT [FK_AspNetRoleClaims_AspNetRoles_RoleId] FOREIGN KEY([RoleId])
REFERENCES [dbo].[AspNetRoles] ([Id])
ON DELETE CASCADE
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetRoleClaims_AspNetRoles_RoleId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetRoleClaims]'))
ALTER TABLE [dbo].[AspNetRoleClaims] CHECK CONSTRAINT [FK_AspNetRoleClaims_AspNetRoles_RoleId]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserClaims_AspNetUsers_UserId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserClaims]'))
ALTER TABLE [dbo].[AspNetUserClaims]  WITH CHECK ADD  CONSTRAINT [FK_AspNetUserClaims_AspNetUsers_UserId] FOREIGN KEY([UserId])
REFERENCES [dbo].[AspNetUsers] ([Id])
ON DELETE CASCADE
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserClaims_AspNetUsers_UserId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserClaims]'))
ALTER TABLE [dbo].[AspNetUserClaims] CHECK CONSTRAINT [FK_AspNetUserClaims_AspNetUsers_UserId]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserLogins_AspNetUsers_UserId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserLogins]'))
ALTER TABLE [dbo].[AspNetUserLogins]  WITH CHECK ADD  CONSTRAINT [FK_AspNetUserLogins_AspNetUsers_UserId] FOREIGN KEY([UserId])
REFERENCES [dbo].[AspNetUsers] ([Id])
ON DELETE CASCADE
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserLogins_AspNetUsers_UserId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserLogins]'))
ALTER TABLE [dbo].[AspNetUserLogins] CHECK CONSTRAINT [FK_AspNetUserLogins_AspNetUsers_UserId]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserRoles_AspNetRoles_RoleId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserRoles]'))
ALTER TABLE [dbo].[AspNetUserRoles]  WITH CHECK ADD  CONSTRAINT [FK_AspNetUserRoles_AspNetRoles_RoleId] FOREIGN KEY([RoleId])
REFERENCES [dbo].[AspNetRoles] ([Id])
ON DELETE CASCADE
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserRoles_AspNetRoles_RoleId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserRoles]'))
ALTER TABLE [dbo].[AspNetUserRoles] CHECK CONSTRAINT [FK_AspNetUserRoles_AspNetRoles_RoleId]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserRoles_AspNetUsers_UserId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserRoles]'))
ALTER TABLE [dbo].[AspNetUserRoles]  WITH CHECK ADD  CONSTRAINT [FK_AspNetUserRoles_AspNetUsers_UserId] FOREIGN KEY([UserId])
REFERENCES [dbo].[AspNetUsers] ([Id])
ON DELETE CASCADE
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserRoles_AspNetUsers_UserId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserRoles]'))
ALTER TABLE [dbo].[AspNetUserRoles] CHECK CONSTRAINT [FK_AspNetUserRoles_AspNetUsers_UserId]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserTokens_AspNetUsers_UserId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserTokens]'))
ALTER TABLE [dbo].[AspNetUserTokens]  WITH CHECK ADD  CONSTRAINT [FK_AspNetUserTokens_AspNetUsers_UserId] FOREIGN KEY([UserId])
REFERENCES [dbo].[AspNetUsers] ([Id])
ON DELETE CASCADE
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AspNetUserTokens_AspNetUsers_UserId]') AND parent_object_id = OBJECT_ID(N'[dbo].[AspNetUserTokens]'))
ALTER TABLE [dbo].[AspNetUserTokens] CHECK CONSTRAINT [FK_AspNetUserTokens_AspNetUsers_UserId]
GO
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AspNetRoles]') AND type in (N'U'))
BEGIN
INSERT INTO [dbo].[AspNetRoles] ([Id], [Name], [NormalizedName], [ConcurrencyStamp]) VALUES (NewId(), 'Admin', 'ADMİN', NewId())
INSERT INTO [dbo].[AspNetRoles] ([Id], [Name], [NormalizedName], [ConcurrencyStamp]) VALUES (NewId(), 'Manager', 'MANAGER', NewId())
INSERT INTO [dbo].[AspNetRoles] ([Id], [Name], [NormalizedName], [ConcurrencyStamp]) VALUES (NewId(), 'Editor', 'EDİTOR', NewId())
INSERT INTO [dbo].[AspNetRoles] ([Id], [Name], [NormalizedName], [ConcurrencyStamp]) VALUES (NewId(), 'WebUser', 'WEBUSER', NewId())
INSERT INTO [dbo].[AspNetRoles] ([Id], [Name], [NormalizedName], [ConcurrencyStamp]) VALUES (NewId(), 'WebApiUser', 'WEBAPİUSER', NewId())
END
GO

IF OBJECT_ID ( 'sp_mcs_Address_Delete', 'P' ) IS NOT NULL   
    DROP PROCEDURE [dbo].[sp_mcs_Address_Delete];  
GO  

CREATE PROCEDURE [dbo].[sp_mcs_Address_Delete]
	@UserId varchar(450),
	@AddressId int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    DELETE [mcs_Address] WHERE UserId = @UserId AND AddressId = @AddressId
END
GO

CREATE PROCEDURE [dbo].[sp_mcs_ProductByCategory_List]
	@CategoryId int Null,
	@Sort char(50) Null,
	@OrderDirection nvarchar(10) = 'Desc',
	@Status nvarchar(50) Null,
	@pageNumber int = 0, 
	@pageSize int = 100
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
   SELECT * FROM [mcs_Product]
	WHERE (@CategoryId is null or [CategoryId] = @CategoryId) AND (@Status is null or [Status] IN (SELECT * FROM STRING_SPLIT(@Status, ',')))
	ORDER by CASE WHEN (@Sort = '1' and UPPER(@OrderDirection) = 'DESC') THEN [Price] END DESC,
			CASE WHEN @Sort = '1' and UPPER(@OrderDirection) = 'ASC' THEN [Price] END ASC,
			CASE WHEN @Sort = '2' and UPPER(@OrderDirection) = 'DESC' THEN [Created_at] END DESC,
			CASE WHEN @Sort = '2' and UPPER(@OrderDirection) = 'ASC' THEN [Created_at] END ASC
		OFFSET @pageNumber ROWS 
		FETCH NEXT @pageSize ROWS ONLY;
END
GO

IF OBJECT_ID ( 'sp_mcs_Cart_Insert', 'P' ) IS NOT NULL   
    DROP PROCEDURE [dbo].[sp_mcs_Cart_Insert];  
GO  

CREATE PROCEDURE [dbo].[sp_mcs_Cart_Insert] 
	@CartId int,
	@UserId nvarchar(450),
	@Status nvarchar(255),
	@Created_at datetime2,
	@Updated_at datetime2
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    INSERT INTO [mcs_Cart]
		([UserId], 
		[Status], 
		[Created_at], 
		[Updated_at])
		OUTPUT Inserted.CartId
	VALUES (@UserId, 
		@Status, 
		@Created_at, 
		@Updated_at)
END
GO

SET IDENTITY_INSERT [dbo].[mcs_Address] ON 
GO
INSERT [dbo].[mcs_Address] ([AddressId], [UserId], [Name], [GPS], [Country], [City], [ZipCode], [CountryCode], [Detail], [Primary], [Active]) VALUES (11, N'348d4f8f-2a81-41e4-b6d4-ff6642d4dc32', N'Göstepe', NULL, N'Türkiye', N'İstanbul', N'81300', NULL, N'Sample address destination 1.', 1, 0)
GO
INSERT [dbo].[mcs_Address] ([AddressId], [UserId], [Name], [GPS], [Country], [City], [ZipCode], [CountryCode], [Detail], [Primary], [Active]) VALUES (12, N'348d4f8f-2a81-41e4-b6d4-ff6642d4dc32', N'Ereğli', NULL, N'Türkiye', N'Kocaeli', N'94513', NULL, N'Sample address destination 2.', 0, 0)
GO
SET IDENTITY_INSERT [dbo].[mcs_Address] OFF
GO
SET IDENTITY_INSERT [dbo].[mcs_Category] ON
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (1, 4, N'Phone', N'Mobile Phone')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (2, 4, N'Computer Laptop', N'Computer')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (3, NULL, N'ROOT', N'Main Root')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (4, 3, N'Electronics', N'Electronics')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (5, 4, N'Headphones', N'Headphones')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (6, 4, N'Portable speakers', N'Portable speakers')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (7, 3, N'Fashion', N'Fashion')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (8, 7, N'T-Shirts', N'T-Shirts')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (9, 7, N'Dresses', N'Dresses')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (10, 7, N'Sunglasses', N'Sunglasses')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (11, 3, N'Home', N'Home')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (12, 11, N'Bathroom Furniture', N'Bathroom Furniture')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (13, 11, N'Kids'' Furniture', N'Kids'' Furniture')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (14, 11, N'Living Room Furniture', N'Living Room Furniture')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (15, 11, N'Bedroom Furniture', N'Bedroom Furniture')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (16, 11, N'Kitchen Furniture', N'Kitchen Furniture')
GO
INSERT [dbo].[mcs_Category] ([CategoryId], [CategoryParentId], [Name], [Description]) VALUES (17, 11, N'Home Office Furniture', N'Home Office Furniture')
GO
SET IDENTITY_INSERT [dbo].[mcs_Category] OFF
GO
SET IDENTITY_INSERT [dbo].[mcs_Product] ON 
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (1, N'IPhone 14 Pro', N'  Designed for durability.  With Ceramic Shield, tougher than any smartphone glass. Water resistance.1 Surgical-grade stainless steel. 6.1″ and 6.7″ display sizes.2 All in four Pro colors.', N'A magical new way to interact with iPhone. Groundbreaking safety features designed to save lives. An innovative 48MP camera for mind-blowing detail. All powered by the ultimate smartphone chip. ', 1, CAST(1000 AS Decimal(18, 0)), N'110000271459507.jpg', NULL, CAST(N'2023-08-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-08-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (2, N'IPhone 14 Pro Max', N'256 GB - 6,7 inç', N'A magical new way to interact with iPhone. Groundbreaking safety features designed to save lives.', 1, CAST(1200 AS Decimal(18, 0)), N'110000271461057.jpg', N'New', CAST(N'2023-09-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-09-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (3, N'Samsung Galaxy S22', NULL, N'Ultra 512 GB 5G Bordo (Samsung Türkiye Garantili)', 1, CAST(990 AS Decimal(18, 0)), N'110000143038313.jpg', NULL, CAST(N'2023-11-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-11-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (4, N'Xiaomi Redmi Note 11 Pro', N'5G + 4G Volte 128GB + 6GB', N'Factory Unlocked 6.67" 108MP Camera Night Mode (Not Verizon Sprint Boost Cricket Metro At&T) + (w/Fast Car Charger Bundle) (Graphite Gray)', 1, CAST(350 AS Decimal(18, 0)), N'110000168630735.jpg', N'Sale', CAST(N'2023-11-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-11-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (5, N'Samsung Galaxy A23', NULL, N'128 GB 4 GB Ram (Samsung Türkiye Garantili)', 1, CAST(250 AS Decimal(18, 0)), N'110000153625124.jpg', NULL, CAST(N'2023-09-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-10-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (6, N'Modern Home Office Chair', N'Leather Desk Chair with Rattan Back, Vanity Chairs for Girl Women, Upholstered Tufted Swivel Armchair for Bedroom Living Room, Height Adjustable Computer Task Chair', N'Leather Desk Chair with Rattan Back, Vanity Chairs for Girl Women, Upholstered Tufted Swivel Armchair for Bedroom Living Room, Height Adjustable Computer Task Chair', 17, CAST(150 AS Decimal(18, 0)), N'61L-KEItaoL._AC_SL1500_.jpg', N'New', CAST(N'2001-01-09T00:00:00.0000000' AS DateTime2), CAST(N'2001-01-08T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (7, N'Office Chair in Black Faux Leather and Chrome Finish', N'Office Chair in Black Faux Leather and Chrome Finish', N'Office Chair in Black Faux Leather and Chrome Finish', 17, CAST(95 AS Decimal(18, 0)), N'61ecveeR6pL.jpg', N'Sale', CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (8, N'L Shaped Desk, Computer Corner Desk', NULL, N'Home Gaming Desk, Office Writing Workstation with Large Monitor Stand, Space-Saving, Easy to Assemble...', 17, CAST(108 AS Decimal(18, 0)), N'71yAMPt6fSL._AC_SL1500_.jpg', NULL, CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (9, N'Optimum', NULL, N'Optimum is a slick sunglasses frame exuding vintage chic.', 10, CAST(77 AS Decimal(18, 0)), N'32-001748_f_2.jpg', NULL, CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (10, N'Heather', NULL, N'Heather is a glamorous frame with an exciting color mix.', 10, CAST(62 AS Decimal(18, 0)), N'7301_f.jpg', NULL, CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (11, N'SHIRT DRESS', NULL, N'Lapel collar dress with long cuffed sleeves. Box pleat detail at hem. Front button and side hidden in-seam zip closure.', 9, CAST(59 AS Decimal(18, 0)), N'2180314401_6_1_1.jpg', NULL, CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (12, N'DENIM MINI DRESS', N'Mini dress with lapel collar and long cuffed sleeves. Patch pockets with flaps and buttons at chest. Tied self belt. Front metal button closure.', N'Mini dress with lapel collar and long cuffed sleeves. Patch pockets with flaps and buttons at chest. Tied self belt. Front metal button closure.', 9, CAST(59 AS Decimal(18, 0)), N'2449042407_6_1_1.jpg', N'New', CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Product] ([ProductId], [Name], [Title], [Description], [CategoryId], [Price], [DefaultPhoto], [Status], [Created_at], [Updated_at]) VALUES (13, N'CROPPED T-SHIRT', NULL, N'Fitted cropped T-shirt with round neck and short sleeves.', 8, CAST(25 AS Decimal(18, 0)), N'5584161803_6_1_1.jpg', NULL, CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2001-01-01T00:00:00.0000000' AS DateTime2))
GO
SET IDENTITY_INSERT [dbo].[mcs_Product] OFF
GO
SET IDENTITY_INSERT [dbo].[mcs_Stock] ON 
GO
INSERT [dbo].[mcs_Stock] ([StockId], [ProductId], [Quantity], [EntryPrice], [EntryDate]) VALUES (1, 1, 150, CAST(850 AS Decimal(18, 0)), CAST(N'2023-08-01T00:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[mcs_Stock] ([StockId], [ProductId], [Quantity], [EntryPrice], [EntryDate]) VALUES (2, 2, 50, CAST(1000 AS Decimal(18, 0)), CAST(N'2023-09-01T00:00:00.0000000' AS DateTime2))
GO
SET IDENTITY_INSERT [dbo].[mcs_Stock] OFF
GO
INSERT [dbo].[AspNetUsers] ([Id], [AccessFailedCount], [ConcurrencyStamp], [Email], [EmailConfirmed], [LockoutEnabled], [LockoutEnd], [NormalizedEmail], [NormalizedUserName], [PasswordHash], [PhoneNumber], [PhoneNumberConfirmed], [SecurityStamp], [TwoFactorEnabled], [UserName]) VALUES (N'348d4f8f-2a81-41e4-b6d4-ff6642d4dc32', 0, N'1c3dd53a-e176-4797-b1e6-f24c3900ade5', N'aoe@gmail.com', 1, 1, NULL, N'AOE@GMAIL.COM', N'AOE@GMAIL.COM', N'AQAAAAEAACcQAAAAEP6t1gnfAN998cWpUVo7VOC1xUqJ1A4Cq0ljGx4scwVLA301WmUYvJ4d0l7vdldHmw==', NULL, 0, N'H5GQWLS24M5RALZVUXKUKKEGUXPK3B4M', 0, N'aoe@gmail.com')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'007DBDF2-CB18-40F4-9A50-7560D3209D8F', 11, N'2180314401_6_3_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'06474A73-A0F2-49C5-B9D8-7705267B11B6', 2, N'110000271458061.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'0C452346-5722-4142-8ABC-00B5FBB7DC42', 12, N'2449042407_6_3_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'21FC902C-AF99-43A3-B811-2815718725D9', 12, N'2449042407_2_1_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'37430980-7E9F-444D-8FD1-BACA0CF1504D', 2, N'110000271463556.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'41ACA875-6B06-4151-85C0-F09C946320B4', 6, N'71+N-QFznWL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'42ABE63D-7B17-4BA9-97A3-AE57898029CA', 10, N'7301_u.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'455FFEFC-65EF-4B0E-AB8E-B3FCE1B22B61', 4, N'110000168856560.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'4874671C-6FBA-4321-A351-A6ACC70E67EE', 1, N'110000271395231.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'4C48787C-E095-49E6-9F8C-30DEBBC948ED', 6, N'81ANQbsRkVL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'4D7D60AC-DC36-49F6-8FAB-4A7C54DA9624', 3, N'110000143038713.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'4D93EEB1-8333-4434-AC7B-E6F1438BA9C0', 3, N'110000143039515.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'51E2114B-37BD-4DA4-9590-9C4CDA62BE73', 11, N'2180314401_6_1_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'594784E7-E00E-45B7-B9BB-B4ED8FDE48BE', 6, N'71joqDdLJHL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'5CB3EB0C-0D9D-4CE1-8CF7-E4797C25FA43', 1, N'110000271459507.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'5CE95A09-5EC1-48A3-8FEF-57B41C58FD45', 1, N'110000271395232.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'5EC0DB09-9B60-4963-B984-73FB978C4B9A', 13, N'5584161803_6_3_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'5FA4EEC0-D403-4A5E-8577-0A1B54C31166', 11, N'2180314401_6_4_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'62FEE7C2-3A4F-4D4F-A2DE-C766090C678F', 8, N'714sSBdBGQL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'64178097-FF0C-41A5-80AA-5351E051F8F8', 7, N'717sQ18ZkYL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'697578C0-12F6-4301-930D-C74C066FEAAF', 2, N'110000271461057.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'6B57C2AF-AEA2-4BFC-97CF-B0CA23028F33', 3, N'110000142716177.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'70C86392-B440-4174-BBF5-C0B14994FAF3', 8, N'71BarkUB1ML._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'719C21B5-AF76-493C-9AA9-39DB5AFDECB2', 4, N'110000168805965.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'721FB1E0-32AB-4F41-B0A5-3D8DFC95E3DF', 11, N'2180314401_6_2_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'750120FF-EA98-4943-9843-4611150F1D6B', 7, N'61sdl0AHwaL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'87BD3B7A-BED0-4712-93DA-187BF6EB67F5', 13, N'5584161803_6_1_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'8C06D0A1-8162-4469-ADE7-7032567A612E', 9, N'32-001748_f_2.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'984CA052-C68A-4577-9648-78BE4EC32AA1', 4, N'110000168630735.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'9AADF0EE-3CC2-4A02-8E7D-827628B6FCCA', 1, N'110000271395233.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'9BDA79C8-BA83-4C27-875F-D337DCF1A3F1', 9, N'32-001748_s_2.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'BA6180C0-B166-486A-9E8E-7B568AAF88C8', 3, N'110000143038313.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'C76786A3-F352-46E4-BFA9-2799B7CF6EC9', 3, N'110000143040658.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'C7745C63-3F2D-4BE2-8C05-69666B1A4CAD', 2, N'110000271461258.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'CF02FD2F-9205-49F1-8C9A-CD29A186C7CA', 7, N'615EtAn+SNL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'D4A2630C-E815-42A5-937E-E846D6821866', 8, N'71yAMPt6fSL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'D8367AC6-30F5-47BC-9240-85F70188C45A', 4, N'110000168797385.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'DB5425A8-8415-4D5B-93E5-F1DCF9F164EE', 6, N'612r-+BASXL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'DE7E3623-59AC-4D6C-B0CE-34064A99EEBA', 6, N'71aDA3ElEXL._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'E3833893-BA01-4E51-93DE-DFF1BF26F50A', 10, N'7301_f.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'E3CCD776-1F9D-4332-B9BD-0E1246F013F6', 12, N'2449042407_6_1_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'E586987F-B431-4BAF-BDC7-DBE199165E7A', 6, N'81RcsqS3v1L._AC_SL1500_.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'E66F8B85-7346-4BED-96B6-862E58C98B78', 12, N'2449042407_6_2_1.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'EDB6BC2F-AD78-4A39-9CC0-195C3F8A23D6', 9, N'32-001748_u_2.jpg')
GO
INSERT [dbo].[mcs_ProductPhoto] ([ProductPhotoId], [ProductId], [Url]) VALUES (N'FA9F134F-4A82-473C-AE60-5E8BF3E132D4', 8, N'81+YbFbv8PL._AC_SL1500_.jpg')
GO