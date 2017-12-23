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

| 模块 |          描述          | 请求路径                   | 方法 | 参数 | 返回 | 备注 |
|:----:|:----------------------:|----------------------------|:----:|:----:|:----:|:----:|
| 用户 |          登录          | /api/pub/login             | POST |      |      |      |
|      |          注册          | /api/pub/register          | POST |      |      |      |
|      |          注销          | /api/pub/logout            | GET  |      |      |      |
| 文件 |        上传文件        | /api/usr/file/upload       | POST |      |      |      |
|      |   获取用户的文件列表   | /api/usr/file/list         |  GET |      |      |      |
|      |       修改文件名       | /api/usr/file/rename       | POST |      |      |      |
|      |        删除文件        | /api/usr/file/delete       | POST |      |      |      |
|      |        分享文件        | /api/usr/file/share        | POST |      |      |      |
|      |        取消分享        | /api/usr/file/cancelShare  | POST |      |      |      |
| 下载 |        下载文件        | /api/pub/download          |  GET |      |      |      |
| 分享 | 文件分享页面（短链接） | /api/pub/share/{shortlink} |  GET |      |      |      |
|      |                        |                            |      |      |      |      |


（待完善）