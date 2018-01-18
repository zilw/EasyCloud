<template>
  <div class="myfile">
    <el-container direction="vertical" style="height: 880px; border: 1px solid #eee">

      <head-item></head-item>

      <el-container direction="horizontal">

        <aside-item activeIndex="1"></aside-item>

        <el-container direction="vertical">
          <el-main>
            <el-row>
              <el-col :span="2" :offset="0">
                <el-button @click="dialogUploadVisible = true" size="medium" type="primary">上传文件</el-button>
              </el-col>
              
            </el-row>
            <el-table :data="tableData" v-loading="loading" height="680">
              <el-table-column prop="name" label="文件名" align="left" min-width="180" :render-header="renderHeader">
                <template slot-scope="scope">
                  <template v-if="editIndex === scope.$index">
                    <el-input :value="scope.row.name" v-model="inputFileName">
                      <el-button size="mini" slot="append" type="primary" @click="comformEdit(scope.$index, scope.row)">
                        <span id="update-span">重命名</span>
                      </el-button>
                    </el-input>
                  </template>
                  <template v-else>
                    <span @click="clickFileName(scope.$index, scope.row)">{{scope.row.name}}</span>
                  </template>
                </template>
              </el-table-column>
              <el-table-column prop="size" label="大小" align="left">
              </el-table-column>
              <el-table-column prop="lastTime" label="修改日期" align="left">
              </el-table-column>
              <el-table-column label="操作" align="left" min-width="120">
                <template slot-scope="scope">
                  <el-button-group>
                    <el-button size="medium" @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit"></el-button>
                    <el-button size="medium" @click="handleShare(scope.$index, scope.row)" icon="el-icon-share"></el-button>
                    <el-button size="medium" type="primary" @click="handleDownload(scope.$index, scope.row)" icon="el-icon-download"></el-button>
                    <el-button size="medium" type="danger" @click="handleDelete(scope.$index, scope.row)" icon="el-icon-delete"></el-button>

                  </el-button-group>
                </template>
              </el-table-column>
            </el-table>

          <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPageNum"
                :page-sizes="[5, 10, 20, 50]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next"
                :total="totalNumber">
              </el-pagination>

          </el-main>

          <!-- 上传文件弹出框 -->
          <el-dialog title="上传文件" :visible.sync="dialogUploadVisible">
            <el-upload ref="upload" name="file" action="/api/usr/file/upload" :auto-upload="false" :multiple="false" :with-credentials="true" :file-list="fileList" 
               :on-success="uploadSuccess" :headers="headers" :on-error="uploadFail">
              <el-button slot="trigger" size="small" type="primary">选择文件</el-button>
              <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">确认上传</el-button>

            </el-upload>

          </el-dialog>

        </el-container>

      </el-container>

    </el-container>

  </div>

</template>



<script>
import AsideItem from '@/components/AsideItem'
import HeadItem from '@/components/HeadItem'

export default {
  name: "my-file-view",
  components: {
    AsideItem,
    HeadItem
  },
  data() {
    return {
      loading: true,
      tableData: [],
      fileList: [],
      dialogUploadVisible: false,
      headers: {},
      editIndex: -1,
      inputFileName: "",
      pageSize: 10,
      currentPageNum: 1,
      totalNumber: 0
    }
  },
  created: function () {
    this.requestListApi();
  },
  methods: {
    //获取文件列表
    requestListApi(pageNum,pageSize) {
      var truePageNum = 1;
      var truePageSize = 10;
      if( pageNum != undefined){
        truePageNum = pageNum;
      }
      if ( pageSize != undefined ){
        truePageSize = pageSize;
      }

      var thiz = this;
      this.$http.get("/api/usr/file/list", {
        params: {
          pageNum: truePageNum,
          pageSize: truePageSize
        }
      }).then(function (response) {
        if (response.data.code === 200) {
          thiz.loading = false;
          thiz.tableData = response.data.data.list;
          thiz.totalNumber = response.data.data.totalNumber;
          thiz.pageSize = response.data.data.pageSize;
          thiz.currentPageNum = response.data.data.pageNum;
        }else{
          thiz.showErrorMsg(response.data.msg);
        }

      });
    },
    handleEdit(index, row) {
      if (this.editIndex == index) {
        this.editIndex = -1;
        this.inputFileName = "";
      } else {
        this.inputFileName = row.name;
        this.editIndex = index;
      }
    },
    //确认重命名文件
    comformEdit(index, row) {
      if (this.inputFileName === row.name) {
        this.editIndex = -1;
        return;
      }

      var param = '?fileId=' + row.fileId + '&filename=' + this.inputFileName;
      var thiz = this;
      this.$http.post("/api/usr/file/rename" + param).then(function (response) {
        if (response.data.code === 200) {
          thiz.showSuccessMsg("修改文件名成功");
        } else {
          thiz.showErrorMsg(response.data.msg);
        }
        row.name = thiz.inputFileName;
        thiz.editIndex = -1;
      });

    },
    //删除文件请求
    requestDeleteApi(fileId) {
      var param = '?fileId=' + fileId;
      var thiz = this;
      this.$http.post("/api/usr/file/delete" + param).then(function (response) {
        if (response.data.code === 200) {
          thiz.showSuccessMsg("删除文件成功");
          thiz.requestListApi();
        } else {
          thiz.showErrorMsg(response.data.msg);
        }
      });
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除文件, 是否继续?', '删除文件', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.requestDeleteApi(row.fileId);
      }).catch(() => {
        this.showInfoMsg('已取消删除');
      });
    },
    //分享文件请求
    requestShareApi(fileId) {
      var param = '?fileId=' + fileId;
      var thiz = this;
      this.$http.post("/api/usr/file/share" + param).then(function (response) {
        if (response.data.code === 200) {
          thiz.showSuccessMsg("分享文件成功");
          thiz.$router.push({
            name: "route-my-share"
          });
        } else {
          thiz.showErrorMsg(response.data.msg);
        }
      });
    },
    handleShare(index, row) {
      this.$confirm('分享后任何人可查看或下载, 是否继续?', '分享文件', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.requestShareApi(row.fileId);
      }).catch(() => {
        this.showInfoMsg('已取消分享');
      });

    },
    //开始上传
    submitUpload() {
      this.$refs.upload.submit();
    },
    uploadSuccess(response, file, fileList) {
      this.dialogUploadVisible = false;
      this.requestListApi();
      this.showSuccessMsg('上传文件成功');
      this.$refs.upload.clearFiles();
    },
    uploadFail(err) {
      var obj = JSON.parse(err.message);
      this.showErrorMsg(obj.msg);
    },
    getFileNameFromHeader(disposition) {
      var result = disposition.split(';')[1].trim().split('=')[1];
      return result.replace(/"/g, '');
    },
    //下载文件请求
    handleDownload(index, row) {
      var thiz = this;
      this.$http.get("/api/pub/download", {
        params: {
          fileId: row.fileId
        },
        responseType: 'arraybuffer'
      }).then(function (response) {
        if (response.status === 200) {
          console.log(response.headers);
          console.log(response.headers["content-disposition"]);
          thiz.showSuccessMsg("正在下载，请稍等...");
          var blob = new Blob([response.data], {})
          var link = document.createElement('a');
          link.href = window.URL.createObjectURL(blob);
          link.download = thiz.getFileNameFromHeader(response.headers["content-disposition"]);
          link.click()

        } else {
          thiz.showErrorMsg(response.data.msg);
        }

      });
    },
    handleSizeChange(val) {
        //console.log(`每页 ${val} 条`);
        this.requestListApi(1,val);
      },
    handleCurrentChange(val) {
        //console.log(`当前页: ${val}`);
        this.requestListApi(val,this.pageSize);
    },
    clickFileName(index, row){
      var strRegex = "(.jpg|.png|.gif|.jpeg|.pdf)$"; 
      var re = new RegExp(strRegex);
      if (!re.test(row.name.toLowerCase())){
        return;
      } 
      window.open("/api/pub/preview?fileId=" + row.fileId);
    },
    renderHeader(createElement, { column }) {
        var createItemI = createElement('i',{'class':'el-icon-question'});

        var createTooltip = createElement('el-tooltip',
          {attrs:{content:'点击图片、PDF文件名直接跳转预览',placement:'top'}},
          [createItemI]);

        return createElement('span',{},[column.label+' ',createTooltip]);
      }
  }

}

</script>

<style scoped>

#update-span {
  color: #409EFF
}

</style>

