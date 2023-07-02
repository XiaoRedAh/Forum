<template>
  <div>
    <el-form
        label-position="top"
        label-width="100px"
        :model="securityForm"
        style="max-width: 460px"
    >
      <el-form-item>
        <el-checkbox v-model="privacyForm.email" label="是否公开展示我的邮箱" size="large" />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="privacyForm.sex" label="是否公开展示我的性别" size="large" />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="privacyForm.phone" label="是否公开展示我的电话号码" size="large" />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="privacyForm.wx" label="是否公开展示我的微信" size="large" />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="privacyForm.blog" label="是否公开展示我的博客" size="large" />
      </el-form-item>
    </el-form>
    <el-button type="success" :icon="Select">保存隐私设置</el-button>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import {Select} from "@element-plus/icons-vue";

const privacyForm = reactive({
  email: false,
  phone: false,
  qq: false,
  wx: false,
  blog: false,
  sex: false
})
</script>

<style scoped>

</style>