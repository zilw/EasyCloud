# EasyCloud  

[![Build Status](https://travis-ci.org/pdwu/EasyCloud.svg?branch=master)](https://travis-ci.org/pdwu/EasyCloud)
[![codecov](https://codecov.io/gh/pdwu/EasyCloud/branch/master/graph/badge.svg)](https://codecov.io/gh/pdwu/EasyCloud) 

### 简介
EasyCloud是一个简易的文件分享平台，后端正在完善中。  

### 技术栈
- Java, Maven  
- Spring, SpringMVC, Mybatis, MySQL  
- Unit Test: JUnit4, Mockito, DbSetup, H2Database  
- Travis自动构建 [EasyCloud - Travis CI](https://travis-ci.org/pdwu/EasyCloud)  
- Codecov测试覆盖率预览 [EasyCloud - Coverage](https://codecov.io/gh/pdwu/EasyCloud)  


### 设计
- 使用拦截器统一验证token
- 分享服务中，短链接使用长整形转62进制的方式实现 

### 接口概览  

|  模块  |          描述          |        请求路径       | 方法 | 参数 | 返回 | 备注 |
|:------:|:----------------------:|:---------------------:|:----:|:----:|:----:|:----:|
|  用户  |          登录          | /login                | POST |      |      |      |
|        |          注册          | /register             | POST |      |      |      |
|  文件  |        上传文件        | /api/file/upload      | POST |      |      |      |
|        |   获取用户的文件列表   | /api/file/list        |  GET |      |      |      |
|        |       修改文件名       | /api/file/rename      | POST |      |      |      |
|        |        删除文件        | /api/file/delete      | POST |      |      |      |
|        |        分享文件        | /api/file/share       | POST |      |      |      |
|        |        取消分享        | /api/file/cancelShare | POST |      |      |      |
|  下载  |        下载文件        | /download             |  GET |      |      |      |
| 分享页 | 文件分享页面（短链接） | /share/{shortlink}    |  GET |      |      |      |
|        |                        |                       |      |      |      |      |