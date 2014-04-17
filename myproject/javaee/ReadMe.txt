itext：可以打印pdf和doc
遇到的问题：
JUnit-4.11使用报java.lang.NoClassDefFoundError: org/hamcrest/SelfDescribing错误
解决：
	换一个低一点的版本就好了。还有人说，是缺少hamcrest的包。去官网又看了一下，结果发现这样一段话：
 
junit.jar: Includes the Hamcrest classes. The simple all-in-one solution to get started quickly.Starting with version 4.11, Hamcrest is no longer included in this jar.
junit-dep.jar: Only includes the JUnit classes but not Hamcrest. Lets you use a different Hamcrest version.
注意黑色加下划线的部分。说明4.1.1中没有hamcrest包了


