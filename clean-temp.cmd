@echo off

rem LICENSE.txt
rem
rem Copyright (c) 2012-2022 Rafael Corchuelo.
rem
rem In keeping with the traditional purpose of furthering education and research, it is
rem the policy of the copyright owner to permit non-commercial use and redistribution of
rem this software. It has been tested carefully, but it is not guaranteed for any particular
rem purposes.  The copyright owner does not offer any warranties or representations, nor do
rem they accept any liabilities with respect to them.

set "RUST=%APPDATA%\..\Local\Temp\rust_*"
set "TOMCAT=%APPDATA%\..\Local\Temp\tomcat^.*"

echo Running this command will remove the following temp folders:
echo.
echo %RUST%
echo %TOMCAT%
echo.
choice /c yn /t 10 /d n /m "Press 'y' to proceed or 'n' to cancel"
echo.

if %ERRORLEVEL% == 1 goto :clean
if %ERRORLEVEL% == 2 goto :end
goto :end

:clean
	call :do_clean %RUST% 
	call :do_clean %TOMCAT% 
	goto :end
	
:do_clean
	SetLocal EnableDelayedExpansion
	set "FOLDERS=%1"	
	rem if not exist %FOLDERS% (
	rem	echo There aren't any %FOLDERS% folders ...		
	rem )	
	if exist %FOLDERS% (
		rem echo Cleaning %FOLDERS% ...
		for /f "tokens=*" %%i in ('dir /b /a:d %FOLDERS%') do (
			set "FOLDER=%APPDATA%\..\Local\Temp\%%i"
			echo rmdir /s /q !FOLDER!
			rmdir /s /q !FOLDER!
		)
	)
	rem echo.
	goto :EOF

:end
