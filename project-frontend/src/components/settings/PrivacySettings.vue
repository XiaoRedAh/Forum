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
        <el-checkbox v-model="privacyForm.qq" label="是否公开展示我的qq" size="large" />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="privacyForm.wx" label="是否公开展示我的微信" size="large" />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="privacyForm.blog" label="是否公开展示我的博客" size="large" />
      </el-form-item>
    </el-form>
    <el-button type="success" :icon="Select" @click="save">保存隐私设置</el-button>
  </div>
</template>

<script setup>
import {onMounted, reactive} from "vue";
import {Select} from "@element-plus/icons-vue";
import {post,get} from "@/net";
import {ElMessage} from "element-plus";

const privacyForm = reactive({
  email: false,
  phone: false,
  qq: false,
  wx: false,
  blog: false,
  sex: false
})

const save = ()=>{
  post('/api/user/save-privacy', privacyForm,()=>ElMessage.success("隐私设置保存成功！"),'json')
}
//展示现在有的隐私
onMounted(()=>{
  get('/api/user/privacy', message=>{
    privacyForm.email = message.email
    privacyForm.phone = message.phone
    privacyForm.qq = message.qq
    privacyForm.wx = message.wx
    privacyForm.blog = message.blog
    privacyForm.sex = message.sex
  })
})
</script>

<style scoped>

</style>