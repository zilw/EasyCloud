# EasyCloud  

[![Build Status](https://travis-ci.org/pdwu/EasyCloud.svg?branch=master)](https://travis-ci.org/pdwu/EasyCloud)
[![codecov](https://codecov.io/gh/pdwu/EasyCloud/branch/master/graph/badge.svg)](https://codecov.io/gh/pdwu/EasyCloud) 

### 简介
EasyCloud是一个简易的文件分享平台。  

### 技术栈
- Java, Maven  
- Spring, SpringMVC, Mybatis, MySQL  
- Unit Test: JUnit4, Mockito, DbSetup, H2Database  
- Travis自动构建 [EasyCloud - Travis CI](https://travis-ci.org/pdwu/EasyCloud)  
- Codecov测试覆盖率预览 [EasyCloud - Coverage](https://codecov.io/gh/pdwu/EasyCloud)  


### 设计
- 使用拦截器统一验证token
- 分享服务中，短链接使用长整形转62进制的方式实现 
- 统一返回的数据格式
  1. 成功则返回200和需要的数据
  ```json
  {
    "code":200, 
    "data":{},
    "msg":null 
  }
  ```
  2. 失败则返回对应错误码和错误信息
    ```json
    {
      "code":400, 
      "data":null,
      "msg":"参数错误" 
    }
    ```

### 接口概览 （已实现） 

| 模块 |        描述        | 请求路径                   | 方法 | 令牌 |        备注        |
|:----:|:------------------:|----------------------------|:----:|------|:------------------:|
| 用户 |        登录        | /api/pub/login             | POST |      |                    |
|      |        注册        | /api/pub/register          | POST |      |                    |
|      |        注销        | /api/pub/logout            |  GET | 需要 |                    |
| 文件 |      上传文件      | /api/usr/file/upload       | POST | 需要 |                    |
|      | 获取用户的文件列表 | /api/usr/file/list         |  GET | 需要 |                    |
|      |     修改文件名     | /api/usr/file/rename       | POST | 需要 |                    |
|      |      删除文件      | /api/usr/file/delete       | POST | 需要 |                    |
|      |      分享文件      | /api/usr/file/share        | POST | 需要 |                    |
|      |      取消分享      | /api/usr/file/cancelShare  | POST | 需要 |                    |
|      |  获取个人分享列表  | /api/usr/file/shareList    |  GET | 需要 |                    |
| 下载 |      下载文件      | /api/pub/download          |  GET |      | 非公开文件需要令牌 |
| 分享 |  获取文件分享详情  | /api/pub/share/{shortlink} |  GET |      |                    |
|      |                    |                            |      |      |                    |

### 详细接口文档 
路径： /doc/api_xxx.md  
- 通用规范 [api_common.md](./doc/api_common.md)
- 文件模块 [api_file.md](./doc/api_file.md)
- 用户模块 [api_user.md](./doc/api_user.md)

