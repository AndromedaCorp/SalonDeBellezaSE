--USE [master]
--GO


CREATE DATABASE [eliqsv_salonbelleza]
GO

USE [eliqsv_salonbelleza]
Go

--Crear la tabla Rol
CREATE TABLE [dbo].[Rol](
[Id] [int] PRIMARY KEY IDENTITY(1,1) NOT NULL,
[Nombre] [varchar] (50) NOT NULL
)
GO

--Crear la tabla Usuario
CREATE TABLE [dbo].[Usuario] (
	[Id] [int] Primary key Identity(1,1) NOT NULL,
	[IdRol] [int] NOT NULL,
	[Dui] [varchar](25) NOT NULL,
	[Nombre] [varchar](50) NOT NULL,
	[Apellido] [varchar](50) NOT NULL,
	[Numero] [varchar](10) NOT NULL,
	[Login] [varchar](25) NOT NULL,
	[Password] [char](32) NOT NULL,
	[Estado] [TinyInt] NOT NULL,
	[FechaRegistro] [DateTime] NOT NULL,
	CONSTRAINT FK1_Rol_Usuario FOREIGN KEY (IdRol) REFERENCES Rol (Id)
)
GO

--Crear la tabla Cliente
CREATE TABLE [dbo].[Cliente](
[Id] [int] PRIMARY KEY IDENTITY(1,1) NOT NULL,
[Dui] [varchar](50) NOT NULL,
[Nombre] [varchar](50) NOT NULL,
[Apellido] [varchar](50) NOT NULL,
[Numero] [int] NOT NULL
)
GO

--Crear la tabla Cita
CREATE TABLE [dbo].[Cita](
	[Id] [int] PRIMARY KEY IDENTITY(1,1)NOT NULL,
	[IdUsuario] [int] NOT NULL,
	[IdCliente] [int] NOT NULL,
	[FechaRegistrada] [datetime] NOT NULL,
	[FechaCita] [datetime] NOT NULL,
	[FechaCitaRealizada] [datetime] NULL,
	[Total] [decimal](12,2)NOT NULL,
	[Estado] [TinyInt] NOT NULL,
	CONSTRAINT FK1_Usuario_Cita FOREIGN KEY (IdUsuario) REFERENCES Usuario (Id),
	CONSTRAINT FK2_Cliente_Cita FOREIGN KEY (IdCliente) REFERENCES Cliente (Id)
)
GO


--Crear la tabla Servicio
CREATE TABLE  [dbo].[Servicio](
[Id] [int] PRIMARY	KEY IDENTITY(1,1) NOT NULL,
[Nombre] [varchar] (100) NOT NULL ,
[Descripcion]  [varchar] (250) NOT NULL,
[PrecioServicio] [decimal] (12, 2) NOT NULL

)
GO


--Crear la tabla DetalleCita
CREATE TABLE  [dbo].[DetalleCita](
[Id] [int] PRIMARY	KEY IDENTITY(1,1) NOT NULL,
[IdCita] [int] NOT NULL,
[IdServicio] [int] NOT NULL,
[PrecioServicio] [decimal] (12,2) NOT NULL,
CONSTRAINT FK1_Cita_DetalleCita FOREIGN KEY (IdCita) REFERENCES Cita (Id),
CONSTRAINT FK2_Servicio_DetalleCita FOREIGN KEY (IdServicio) REFERENCES Servicio (Id),
)
GO