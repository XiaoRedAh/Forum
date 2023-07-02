<template>
  <div style="display: flex;padding: 20px 30px">
    <!--左侧标签页展示设置细节-->
    <div style="flex: 1">
      <el-tabs v-model="activeName">
        <el-tab-pane label="个人信息设置" name="info">
          <info-settings/>
        </el-tab-pane>
        <el-tab-pane label="账号安全设置" name="security">
          <security-settings/>
        </el-tab-pane>
        <el-tab-pane label="隐私信息" name="privacy">
          <privacy-settings/>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!--右侧用一个类似卡片的形式显示一些个人信息-->
    <div style="width: 300px">
      <div class="user-card">
        <div style="text-align: right;padding: 5px 15px 0 15px">
          <div style="width: 70px;display: inline-block">
            <el-avatar :size="70" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
            <div style="text-align: right;font-weight: bold;padding: 0 25px">{{store.auth.user.username}}</div>
          </div>
        </div>
        <div style="padding: 10px;font-size: 20px">
          撒据北京asFffffffffffffff
          发放rgdgdfafasfaf
          存储fafaefafa
          gaaggggggggewaaaaaaaaa
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import {reactive, ref} from "vue";
import {useStore} from "@/stores";
import InfoSettings from "@/components/settings/infoSettings.vue";
import SecuritySettings from "@/components/settings/securitySettings.vue";
import PrivacySettings from "@/components/settings/PrivacySettings.vue";
const store = useStore()//用户信息存储在这个全局变量中

const activeName =ref('info')/*默认在“个人信息设置”标签页*/

</script>

<style scoped>
/*右侧个人明信片样式*/
.user-card{
  height: 300px;
  width: 260px;
  margin: 15px 0 0 30px;
  border-radius: 5px;
  border: solid 1px lightgrey;
}
/*右侧个人明信片样式用灰色线上下分隔*/
.user-card > div{
  border-bottom: solid 1px lightgrey;
}

</style>