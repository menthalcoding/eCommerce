
------------
INSTALLATION
------------

DATABASE SETUP
--------------
Open Microsoft SQL Server Management Studio.
Create the new database name exactly the same ([PROJECT_NAME]) as in the editor.
Open [PROJECT_NAME]_Database.sql file in the main directory of the zip file produced by the editor and run it.

WEBSITE SETUP
-------------
Open Microsoft Visual Studio. (version 2022 recommended)
Click Create a New Project.
Click ASP.NET Core Web Application.
Enter the name of your project exactly the same ([PROJECT_NAME]) as in the editor.
Select the Authentication Type field as individual accounts and click Create.

Open the package manager console and run the commands below.

Install-Package System.Data.SqlClient -Version 4.8.5
Install-Package Microsoft.AspNetCore.Authentication -Version 2.2.0
Install-Package Microsoft.AspNetCore.Authentication.JwtBearer -Version 6.0.13
Install-Package Newtonsoft.Json.Bson -Version 1.0.2

Copy all the files in the AspNet Core MVC folder in the zip file created by the editor to the main directory of your project.
Edit database connection string in appsetting.json file in root directory.
Run your project.