#### 2.1 上传文件

**请求地址/方法**   
- ` ~/api/usr/file/upload `  POST multipart/form-data, name=file
- 需要令牌
  
**示例**  
```

```

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|file |是  |multipart/form-data |文件   |


 **返回示例**  

``` json
{
    "msg": "",
    "code": 200,
    "data": {
        "fileId": 105,
        "userId": 10026,
        "md5": "24b7123763ab9c3d181394a8af195d2e",
        "name": "微信图片_20170822225654.jpg",
        "size": 35013,
        "status": 0,
        "createTime": "2018-01-06 03:02:50",
        "lastTime": "2018-01-06 03:02:50"
    }
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|fileId |long   |文件id  |
|userId |long   |用户id  |
|md5 |string   |文件MD5  |
|name |string   |文件名  |
|size |long   |文件大小  |
|status |int   |文件状态  |
|createTime|string|创建时间|
|lastTime|string|最后修改时间|


 **备注** 

- none


---  

#### 2.2 获取用户上传的文件

**请求地址/方法**   
- GET ` ~/api/usr/file/list ` 
  
**示例**  
- GET ` ~/api/usr/file/list?pageNum=1&pageSize=10 `
- 需要令牌


**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|pageNum |否  |int |页码，默认1   |
|pageSize |否  |int |每页数量，默认10   |


 **返回示例**  

``` json
{
    "msg": "",
    "code": 200,
    "data": {
        "count": 2,
        "pageSize": 10,
        "pageNum": 1, 
        "list": [
            {
                "fileId": 105,
                "userId": 10026,
                "md5": "24b7123763ab9c3d181394a8af195d2e",
                "name": "微信图片_20170822225654.jpg",
                "size": 35013,
                "status": 0,
                "createTime": "2018-01-06 03:02:50",
                "lastTime": "2018-01-06 03:02:50"
            },
            {
                "fileId": 106,
                "userId": 10026,
                "md5": "ec266feab06040fd0db4a5d519d3e00c",
                "name": "周杰伦 - 我很忙.cue",
                "size": 1019,
                "status": 0,
                "createTime": "2018-01-06 03:16:06",
                "lastTime": "2018-01-06 03:16:06"
            }
        ],
        "userId": 10026
    }
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|list |array   |文件详情数组  |
|count |int   |实际返回的数量  |
|pageNum |int   |当前页码  |
|pageSize |int   |当前每页数量  |
|- |-   |-  |
|fileId |long   |文件id  |
|userId |long   |用户id  |
|md5 |string   |文件MD5  |
|name |string   |文件名  |
|size |long   |文件大小  |
|status |int   |文件状态  |
|createTime|string|创建时间|
|lastTime|string|最后修改时间|


 **备注** 

- none


---  

#### 2.3 修改文件名

**请求地址/方法**   
- ` ~/api/usr/file/rename  `  POST PARAM
- 需要令牌
  
**示例**   
- POST ` ~/api/usr/file/rename?fileId=106&filename=新文件名  `

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|fileId |是  |long |文件id   |
|filename |是  |string |文件名  |


 **返回示例**  

``` json
  {
    "code": 200,
    "data": "",
    "msg": null
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   |返回码  |

 **备注** 

- none

---  

#### 2.4 删除文件

**请求地址/方法**   
- ` ~/api/usr/file/delete  `  POST PARAM
- 需要令牌
  
**示例**   
- POST ` ~/api/usr/file/rename?fileId=106  `

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|fileId |是  |long |文件id   |


 **返回示例**  

``` json
  {
    "code": 200,
    "data": "",
    "msg": null
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   |返回码  |

 **备注** 

- none

---  


#### 2.5 分享文件

**请求地址/方法**   
- ` ~/api/usr/file/share  `  POST PARAM
- 需要令牌
  
**示例**   
- POST ` ~/api/usr/file/rename?fileId=106  `

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|fileId |是  |long |文件id   |


 **返回示例**  

``` json
{
    "msg": "",
    "code": 200,
    "data": {
        "shareId": 102233,
        "userId": 10026,
        "fileId": 106,
        "status": 1,
        "createTime": "2018-01-06 03:39:25",
        "lastTime": "2018-01-06 03:39:25",
        "fileInfo": null
    }
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   |返回码  |
|status |int   |分享状态，0取消分享，1正常  |
|fileId|long|对应分享的文件id|
|createTime|string|创建时间|
|lastTime|string|最后修改时间|


 **备注** 

- none

---  


#### 2.6 取消分享

**请求地址/方法**   
- ` ~/api/usr/file/cancelShare  `  POST PARAM
- 需要令牌
  
**示例**   
- POST ` ~/api/usr/file/rename?shareId=106  `

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|shareId |是  |long |分享id   |


 **返回示例**  

``` json
{
    "msg": "",
    "code": 200,
    "data": ""
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   |返回码  |


 **备注** 

- none

---  


#### 2.7 获取个人分享列表

**请求地址/方法**   
- ` ~/api/usr/file/shareList  `  
- 需要令牌
  
**示例**   
- GET ` ~/api/usr/file/shareList?pageNum=1  `

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|pageNum |否  |int |页码，默认1   |
|pageSize |否  |int |每页数量，默认10   |


 **返回示例**  

``` json
{
    "msg": "",
    "code": 200,
    "data": {
        "count": 1,
        "pageSize": 10,
        "list": [
            {
                "shareId": 102233,
                "userId": 10026,
                "fileId": 106,
                "status": 1,
                "createTime": "2018-01-06 03:39:25",
                "lastTime": "2018-01-06 03:39:25",
                "fileInfo": {
                    "fileId": 106,
                    "userId": 10026,
                    "md5": "ec266feab06040fd0db4a5d519d3e00c",
                    "name": "新文件名",
                    "size": null,
                    "status": 0,
                    "createTime": "2018-01-06 03:16:06",
                    "lastTime": "2018-01-06 03:31:42"
                }
            }
        ],
        "pageNum": 1
    }
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   |返回码  |


 **备注** 

- none

---  

#### 3.1 获取某个分享详情

**请求地址/方法**   
- ` ~/api/pub/share/{shortLink}  `  
- 不需要令牌
  
**示例**   
- GET ` ~/api/pub/share/ALt  `

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|shortlink |是  |string |短链接   |


 **返回示例**  

``` json
{
    "msg": "",
    "code": 200,
    "data": {
        "shareId": 102234,
        "userId": 10026,
        "fileId": 106,
        "status": 1,
        "createTime": "2018-01-06 06:16:59",
        "lastTime": "2018-01-06 06:16:59",
        "fileInfo": {
            "fileId": 106,
            "userId": 10026,
            "md5": "ec266feab06040fd0db4a5d519d3e00c",
            "name": "新文件名",
            "size": null,
            "status": 0,
            "createTime": "2018-01-06 03:16:06",
            "lastTime": "2018-01-06 03:31:42"
        }
    }
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   |返回码  |
|status |int   |分享状态，0取消分享，1正常  |
|fileId|long|对应分享的文件id|
|createTime|string|创建时间|
|lastTime|string|最后修改时间|



 **备注** 

- none

---  



#### 3.2 下载文件

**请求地址/方法**   
- ` ~/api/pub/download   `  
- 非公开文件需要令牌
  
**示例**   
- GET ` ~/api/pub/download?fileId=123   `
- GET ` ~/api/pub/download?shareId=10023   `

**参数**  

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|fileId |否  |long |下载的文件id   |
|shareId |否  |long |分享id（通过分享页下载）   |


 **返回示例**  

``` 
文件流
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   |返回码  |


 **备注** 

- none

---  