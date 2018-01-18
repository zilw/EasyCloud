<template>

  <div class="pubshare">

    <el-row id="bd" type="flex" justify="center">
      <el-col :span="8">
        <el-card :body-style="{ padding: '0px' }">

            <template v-if="showImg">
            <el-row>
          <img :src="showImgSrc" class="image">
            </el-row>
            </template>
          <el-row type="flex" style="padding-top: 6px;"><span style="margin-left: 5px;">{{fileName}}</span> </el-row>
            <el-row type="flex" align="middle" style="padding: 4px 4px 4px 6px;">
              <el-col :span="20"  ><span class="time">分享者: {{shareUser}}</span></el-col>
              <el-col :span="4"><el-button size="medium" type="primary" :disabled="disableBtn" @click="handleDownload">下载</el-button></el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

  </div>

</template>



<script>
export default {
  name: 'pub-share',
  data() {
    return {
      showImg: false,
      showImgSrc: '',
      fileName: '文件',
      shareUser: '***',
      disableBtn: false,
      downloadURL: ''
    }
  },
  created() {
    var shortLinkCode = this.$route.params.code;
    this.getShareInfo(shortLinkCode);

  },
  methods: {
    getShareInfo(code) {
      var thiz = this;
      this.$http.get('/api/pub/share/' + code).then(function (response) {
        if (response.data.code === 200) {
          var fileName = response.data.data.fileInfo.name;
          thiz.fileName = fileName + '';
          thiz.shareUser = response.data.data.userId;
          var param = '?fileId=' + response.data.data.fileId + '&shareId=' + response.data.data.shareId;
          thiz.downloadURL = '/api/pub/download' + param;

          if (thiz.isImage(fileName)) {
            thiz.showImg = true;
            thiz.showImgSrc = '/api/pub/preview' + param;
          }

        } else {
          thiz.fileName = response.data.msg;
          thiz.disableBtn = true;
        }
      })
    },
    isImage(fileName) {
      var strRegex = "(.jpg|.png|.gif|.jpeg)$";
      var re = new RegExp(strRegex);
      if (re.test(fileName.toLowerCase())) {
        return true;
      } else {
        return false;
      }
    },
    getFileNameFromHeader(disposition) {
      var result = disposition.split(';')[1].trim().split('=')[1];
      return result.replace(/"/g, '');
    },
    handleDownload() {
      window.open(this.downloadURL);
    }
  }
}

</script>

<style scoped>

#bd {
  margin-top: 6%;
}

.time {
  font-size: 13px;
  color: #999;
  float: left;
}

.image {
  width: 100%;
  display: block;
}

</style>

