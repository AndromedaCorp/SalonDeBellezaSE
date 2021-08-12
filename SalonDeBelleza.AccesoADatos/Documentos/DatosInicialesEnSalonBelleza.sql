USE [eliqsv_salonbelleza]
GO

INSERT INTO [dbo].[Rol]
			([Nombre])
		VALUES
			('ADMINISTRADOR DEL SISTEMA')
GO

--Encriptar la contraseña Admin2021 en MD5 https://www.infranetworking.com/md5
INSERT INTO [dbo].[Usuario]
			([IdRol]
			,[DUI]
			,[Nombre]
			,[Apellido]
			,[Numero]
			,[Login]
			,[Password]
			,[Estado]
			,[FechaRegistro])
		VALUES
			((SELECT TOP 1 Id FROM Rol WHERE Nombre='ADMINISTRADOR DEL SISTEMA'),
			'21783290-9',
			 'Administrador',
			 'Del Sistema',
			 '1256-3212',
			 'SysAdmin',
			 '1fe57b020cf7bcd8ef4cc23354b214a9',
			 1,
			 SYSDATETIME())