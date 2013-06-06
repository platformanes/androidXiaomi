androidXiaomi
=============

小米android平台ANE
* 此ANE可提供aser直接用，无需涉及java端。
* 保留ANE原件 和ANE源码  
* AS端源码和SWC文件
* 把涉及SDK的jar 文档等去掉（官方未找到下载路径）
* 若需要自己写  接SDK的时候 把官方提供的SDK相应文件导入库即可

## 资源

* 官方网站 [Xiaomi](http://app.xiaomi.com/)

## 编译方法
* 复制 `build/example.build.config` 为 `build/build.config`
* 修改其中的 `flex.sdk` 和 `android.sdk` 变量为正确的路径
* 运行 `ant android-ane`


## 作者

* [platformANEs](https://github.com/platformanes)由 [zrong](http://zengrong.net) 和 [rect](http://www.shadowkong.com/) 共同发起并完成。
