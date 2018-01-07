<template>
  <div class="myfile">
    <el-container direction="vertical" style="height: 600px; border: 1px solid #eee">

      <head-item></head-item>

      <el-container direction="horizontal">

        <aside-item activeIndex="2"></aside-item>

        <el-container direction="vertical">

          <el-main>
            <el-table :data="tableData" v-loading="loading">
              <el-table-column prop="fileInfo.name" label="文件名" align="left">
              </el-table-column>
              <el-table-column prop="fileInfo.size" label="大小" align="left">
              </el-table-column>
              <el-table-column prop="fileInfo.lastTime" label="修改日期" align="left">
              </el-table-column>
              <el-table-column prop="createTime" label="分享时间" align="left">
              </el-table-column>
              <el-table-column label="操作" align="left" min-width="120">
                <template slot-scope="scope">
                  <el-button-group>
                    <el-button size="small" type="danger" @click="handleCancleShare(scope.$index, scope.row)">取消分享</el-button>
                  </el-button-group>
                </template>
              </el-table-column>

            </el-table>
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
      tableData: []
    }
  },
  created() {
    this.requestListApi();
  },
  methods: {
    requestListApi() {
      var thiz = this;
      this.$http.get("/api/usr/file/shareList", {
        params: {
          status: 1,
          pageNum: 1,
          pageSize: 10
        }
      }).then(function (response) {
        if (response.data.code != 200) {
          thiz.showErrorMsg(response.data.msg);
        };
        thiz.loading = false;
        thiz.tableData = response.data.data.list;

      });
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
    }
  }

}

</script>

<style scoped>

</style>

