# AndFixTest
AndFix 热修复
在我们发布的应用中都可能存在bug，而修复bug我们需要重新发包，但是重新发包会影响用户的体验，并且浪费用户的流量，重新发包用户需要下载整个包进行安装，这时我们可以考虑不需要重新发包，只需要向用户推送一个补丁进行修复就可以了。

在这里我将向大家推荐AndFix 热修复，AndFix是阿里开源的一个热修复框架，AndFix它的原理简单而纯粹。我们可以通过apkpatch工具生成一个差量文件(.apatch)文件。

AndFix原理


方法替换过程


# .apatch文件生成
在cmd下输入 apkpatch.bat -f new.apk -t old.apk -o xxx -k xxx -p xxx -a ckb -e xxx

-f ：新版本

-t : 旧版本

-o ： 输出目录

-k ： 打包所用的keystore的路径

-p ： keystore的密码

-a ： keystore 用户别名

-e ： keystore 用户别名密码



执行完之后会生成一个.apatch 文件



在本实例中可以将文件重命名out.apatch,拷贝至sd卡根目录下进行测试

在android studio 中build.gradle 需要添加 compile 'com.alipay.euler:andfix:0.3.1@aar'

混淆需要添加下列混淆语句

-keep class * extends java.lang.annotation.Annotation

-keepclasseswithmembernames class * { native ; }

-keep class com.alipay.euler.andfix.** { *; }

注意：AndFix只支持修复代码部分，资源文件不支持

框架源码地址：https://github.com/alibaba/AndFix
