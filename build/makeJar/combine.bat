@echo off
::转到当前盘符
%~d0
::打开当前目录
cd %~dp0
::你做的主JAR包的路径
set MainJar=xiaomiane.jar
::第三方JAR包的路径
set ExternalJar=SDK_TY_2.0.0.jar
::第三方JAR包顶级包名称
set packageName=com
set packageName2=cn
set packageName3=assets
echo =========== start combin ==============
::解压第三方包
jar -xf %ExternalJar%
::合并主JAR包
jar -uf %MainJar% %packageName% 
::如果还有别的顶级包可以接着合并，例如：
::jar -uf %MainJar% %packageName2%
::jar -uf %MainJar% %packageName3%
echo =========== over ==============
echo 再点一下就结束了--小Q
pause