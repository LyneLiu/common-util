

1. Eclipse启动异常：

	---------------------------

	Java Virtual Machine Launcher

	Error Could not create the Java Virtual Machine.
	Error A fatal exception has occurred. Program will exit.

	---------------------------

2. 启动参数信息

	---------------------------

		Java was started but returned exit code=1
		C:\ProgramData\Oracle\Java\javapath\javaw.exe
		-Dosgi.requiredJavaVersion=1.7
		-Xms512m
		-Xmx512m
		-Djava.net.preferIPv4Stack=true
		-javaagent:lombok.jar
		-Xverify:none
		-XX:+PrintGCDetails
		-XX:+PrintGCDateStamps
		-Xloggc:gc.log
		-jar D:\Program Files\eclipse\\plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar
		-os win32
		-ws win32
		-arch x86_64
		-showsplash D:\Program Files\eclipse\\plugins\org.eclipse.platform_4.5.1.v20150904-0015\splash.bmp
		-launcher D:\Program Files\eclipse\eclipse.exe
		-name Eclipse
		--launcher.library D:\Program Files\eclipse\\plugins/org.eclipse.equinox.launcher.win32.win32.x86_64_1.1.300.v20150602-1417\eclipse_1611.dll
		-startup D:\Program Files\eclipse\\plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar
		--launcher.appendVmargs
		-exitdata 475c_60
		-product org.eclipse.epp.package.jee.product
		-vm C:\ProgramData\Oracle\Java\javapath\javaw.exe
		-vmargs
		-Dosgi.requiredJavaVersion=1.7
		-Xms512m
		-Xmx512m
		-Djava.net.preferIPv4Stack=true
		-javaagent:lombok.jar
		-Xverify:none
		-XX:+PrintGCDetails
		-XX:+PrintGCDateStamps
		-Xloggc:gc.log
		-jar D:\Program Files\eclipse\\plugins/org.eclipse.equinox.launcher_1.3.100.v20150511-1540.jar 

	---------------------------

解决办法：
Eclipse应用启动默认使用的是系统路径下的C:\ProgramData\Oracle\Java\javapath\javaw.exe，需要指定用户安装的java组件。
在快捷方式的目标栏中添加启动参数，"D:\Program Files\eclipse\eclipse.exe" -vm $JAVA_HOME$\bin\javaw.exe