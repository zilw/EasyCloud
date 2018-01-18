<template>
  <div class="myfile">
    <el-container direction="vertical" style="height: 880px; border: 1px solid #eee">

      <head-item></head-item>

      <el-container direction="horizontal">

        <aside-item activeIndex="2"></aside-item>

        <el-container direction="vertical">

          <el-main>
            <el-table :data="tableData" v-loading="loading" height="700">
              <el-table-column prop="fileInfo.name" label="文件名" align="left" min-width="180">
              </el-table-column>
              <el-table-column prop="fileInfo.size" label="大小" align="left">
              </el-table-column>
              <el-table-column prop="createTime" label="分享时间" align="left">
              </el-table-column>
              <el-table-column label="操作" align="left" min-width="100">
                <template slot-scope="scope">
                  <el-button-group>
                    <el-button size="small" type="info" @click="showShortLink(scope.$index, scope.row)">链接</el-button>
                    <el-button size="small" type="danger" @click="handleCancleShare(scope.$index, scope.row)">取消分享</el-button>
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

        </el-container>

      </el-container>

    </el-container>

  </div>

</template>



<script>
import AsideItem from '@/components/AsideItem'
import HeadItem from '@/components/HeadItem'

export default {
  name: "my-share-view",
  components: {
    AsideItem,
    HeadItem
  },
  data() {
    return {
      loading: true,
      tableData: [],
      pageSize: 10,
      currentPageNum: 1,
      totalNumber: 0
    }
  },
  created() {
    this.requestListApi();
  },
  methods: {
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
      this.$http.get("/api/usr/file/shareList", {
        params: {
          status: 1,
          pageNum: truePageNum,
          pageSize: truePageSize
        }
      }).then(function (response) {
        if (response.data.code != 200) {
          thiz.showErrorMsg(response.data.msg);
        };
        thiz.loading = false;
        thiz.tableData = response.data.data.list;
        thiz.totalNumber = response.data.data.totalNumber;
        thiz.pageSize = response.data.data.pageSize;
        thiz.currentPageNum = response.data.data.pageNum;

      });
    },
    showShortLink(index, row){

       this.$confirm(''+row.shortlink, row.fileInfo.name, {
          confirmButtonText: '复制',
          cancelButtonText: '取消',
          type: 'info'
        }).then(() => {
          this.copyToClipboard(''+row.shortlink);
        }).catch(() => {
        });

    },
    copyToClipboard (text) {
    
      var textArea = document.createElement("textarea");
        textArea.style.position = 'fixed';
        textArea.style.top = '0';
        textArea.style.left = '0';
        textArea.style.width = '2em';
        textArea.style.height = '2em';
        textArea.style.padding = '0';
        textArea.style.border = 'none';
        textArea.style.outline = 'none';
        textArea.style.boxShadow = 'none';
        textArea.style.background = 'transparent';
        textArea.value = text;
        document.body.appendChild(textArea);
        textArea.select();

        var errMsg = '该浏览器不支持点击复制到剪贴板';
        try {
          var successful = document.execCommand('copy');
          if(successful){
            this.showSuccessMsg('复制成功');
          }else{
            this.showErrorMsg(errMsg);
          }
        } catch (err) {
          this.showErrorMsg(errMsg);
        }
        document.body.removeChild(textArea);
    },
    handleCancleShare(index, row) {
      var param = '?shareId=' + row.shareId;
      var thiz = this;
      this.$http.post("/api/usr/file/cancelShare" + param).then(function (response) {
        if (response.data.code === 200) {
          thiz.showSuccessMsg("取消分享成功");
          thiz.requestListApi();
        } else {
          thiz.showErrorMsg(response.data.msg);
        }
      });
    },
    handleSizeChange(val) {
        this.requestListApi(1,val);
      },
    handleCurrentChange(val) {
        this.requestListApi(val,this.pageSize);
    }
  }

}

</script>

<style scoped>

</style>

