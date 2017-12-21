# EasyCloud  

[![Build Status](https://travis-ci.org/pdwu/EasyCloud.svg?branch=master)](https://travis-ci.org/pdwu/EasyCloud)
[![codecov](https://codecov.io/gh/pdwu/EasyCloud/branch/master/graph/badge.svg)](https://codecov.io/gh/pdwu/EasyCloud) 

### 简介
EasyCloud是一个简易的文件分享平台后端，正在完善中。  

### 技术栈
- Spring, SpringMVC, Mybatis
- MySQL
- Unit Test: JUnit4, Mockito, DbSetup, H2Database

### 设计
- 使用拦截器统一验证token
- 分享服务中，短链接使用整形转62进制的方式实现 
- 接入Travis持续集成，Codecov测试覆盖率预览


### 接口概览  

|  模块  |          描述          |        请求路径       | 方法 | 参数 | 返回 | 备注 |
|:------:|:----------------------:|:---------------------:|:----:|:----:|:----:|:----:|
|  用户  |          登录          | /login                | post |      |      |      |
|        |          注册          | /register             | post |      |      |      |
|  文件  |        上传文件        | /api/file/upload      | post |      |      |      |
|        |   获取用户的文件列表   | /api/file/list        |  get |      |      |      |
|        |       修改文件名       | /api/file/rename      | post |      |      |      |
|        |        删除文件        | /api/file/delete      | post |      |      |      |
|        |        分享文件        | /api/file/share       | post |      |      |      |
|        |        取消分享        | /api/file/cancelShare | post |      |      |      |
|  下载  |        下载文件        | /download             |  get |      |      |      |
| 分享页 | 文件分享页面（短链接） | /share/{shortlink}    |  get |      |      |      |
|        |                        |                       |      |      |      |      |