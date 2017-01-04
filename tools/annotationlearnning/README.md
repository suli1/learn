# 使用注解器在编译时生成代码
* 编译  
```$xslt
mvn clean compile // 在sample模块下面的generated-sources下生成Java代码
```
* part1  
使用@Factory生成IMealFactory工程类

* part2  
annotation 和 processor 模块分离

> 参考资料  
1. [AnnotationProcessor](https://github.com/qinxiandiqi/AnnotationProcessorDemo)
2. [实现一个简单的Java编译时注解处理器](https://yq.aliyun.com/articles/59493)
3. [ANNOTATION PROCESSING 101](http://hannesdorfmann.com/annotation-processing/annotationprocessing101)