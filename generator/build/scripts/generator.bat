@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  generator startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and GENERATOR_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\generator-1.0-SNAPSHOT.jar;%APP_HOME%\lib\directions-api-client-0.10.1-4.jar;%APP_HOME%\lib\core-1.0-SNAPSHOT.jar;%APP_HOME%\lib\jsprit-analysis-1.7.2.jar;%APP_HOME%\lib\jsprit-core-1.7.2.jar;%APP_HOME%\lib\commons-csv-1.1.jar;%APP_HOME%\lib\log4j-core-2.11.1.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.25.jar;%APP_HOME%\lib\nominatim-api-3.3.jar;%APP_HOME%\lib\graphhopper-reader-osm-0.11.0.jar;%APP_HOME%\lib\graphhopper-core-0.11.0.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\httpclient-4.5.6.jar;%APP_HOME%\lib\gs-ui-1.3.jar;%APP_HOME%\lib\gs-algo-1.3.jar;%APP_HOME%\lib\commons-math3-3.6.1.jar;%APP_HOME%\lib\commons-cli-1.4.jar;%APP_HOME%\lib\swagger-annotations-1.5.15.jar;%APP_HOME%\lib\logging-interceptor-2.7.5.jar;%APP_HOME%\lib\okhttp-2.7.5.jar;%APP_HOME%\lib\gson-fire-1.8.0.jar;%APP_HOME%\lib\geogson-jts-1.1.100.jar;%APP_HOME%\lib\geogson-core-1.1.100.jar;%APP_HOME%\lib\gson-2.8.5.jar;%APP_HOME%\lib\threetenbp-1.3.5.jar;%APP_HOME%\lib\jfreechart-1.0.19.jar;%APP_HOME%\lib\gs-core-1.3.jar;%APP_HOME%\lib\log4j-api-2.11.1.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\httpcore-4.4.10.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\commons-codec-1.10.jar;%APP_HOME%\lib\graphhopper-api-0.11.0.jar;%APP_HOME%\lib\guava-27.0-jre.jar;%APP_HOME%\lib\commons-lang3-3.8.1.jar;%APP_HOME%\lib\jts-1.13.jar;%APP_HOME%\lib\jackson-annotations-2.9.8.jar;%APP_HOME%\lib\okio-1.6.0.jar;%APP_HOME%\lib\jcommon-1.0.23.jar;%APP_HOME%\lib\junit-4.12.jar;%APP_HOME%\lib\pherd-1.0.jar;%APP_HOME%\lib\mbox2-1.0.jar;%APP_HOME%\lib\scala-library-2.10.1.jar;%APP_HOME%\lib\jts-core-1.14.0.jar;%APP_HOME%\lib\hppc-0.8.1.jar;%APP_HOME%\lib\xmlgraphics-commons-2.3.jar;%APP_HOME%\lib\osmosis-osm-binary-0.46.jar;%APP_HOME%\lib\failureaccess-1.0.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-qual-2.5.2.jar;%APP_HOME%\lib\error_prone_annotations-2.2.0.jar;%APP_HOME%\lib\j2objc-annotations-1.1.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.17.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\commons-math-2.1.jar;%APP_HOME%\lib\commons-io-1.3.1.jar;%APP_HOME%\lib\protobuf-java-3.4.0.jar

@rem Execute generator
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GENERATOR_OPTS%  -classpath "%CLASSPATH%" pl.grajekf.servicearea.generator.GeneratorMain %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GENERATOR_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%GENERATOR_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
