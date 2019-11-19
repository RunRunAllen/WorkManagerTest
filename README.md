# WorkManager初探

## 一.项目基本信息
------------------
   * 官方文档地址：https://developer.android.com/topic/libraries/architecture/workmanager
   * 项目基于的编程语言 ：Java\
   * 项目所需的环境： studio  java7以上\
   * 项目支持的平台： Android 
   
##  二. 快速开始
----------------------
    添加依赖:
    dependencies{
        implementation 'androidx.work:work-runtime:2.2.0'
    }
    ```
    public class MyWorker extends Worker{
        public Result doWork(){
        }
    }
    
-------------------
##  三. Thanks 
     感谢Jepack团队及相关支持的码友们
