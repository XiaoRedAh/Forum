<template>
  <el-form
      label-position="top"
      label-width="100px"
      :model="infoFrom"
      style="max-width: 460px"
  >
    <el-form-item label="用户名">
      <el-input v-model="infoFrom.username" />
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="infoFrom.sex" class="ml-4">
        <el-radio label="1" size="large">男</el-radio>
        <el-radio label="2" size="large">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="手机号">
      <el-input v-model="infoFrom.phone" />
    </el-form-item>
    <el-form-item label="QQ">
      <el-input v-model="infoFrom.qq" />
    </el-form-item>
    <el-form-item label="微信">
      <el-input v-model="infoFrom.wx" />
    </el-form-item>
    <el-form-item label="博客">
      <el-input v-model="infoFrom.blog" />
    </el-form-item>
    <el-form-item label="个人简介">
      <el-input type="textarea" v-model="infoFrom.desc" :rows="6"/>
    </el-form-item>
  </el-form>
  <el-button type="success" :icon="Select">保存个人信息设置</el-button>
</template>

<script setup>
import {useStore} from "@/stores";
const store = useStore()//用户信息存储在这个全局变量中
import {Select} from "@element-plus/icons-vue";

import {reactive} from "vue";
/*"个人信息设置"的各个参数*/
const infoFrom =reactive({
  username: '',
  desc: '',
  qq: '',
  phone: '',
  wx: '',
  blog: '',
  sex: ''
})
</script>

<style scoped>

</style>