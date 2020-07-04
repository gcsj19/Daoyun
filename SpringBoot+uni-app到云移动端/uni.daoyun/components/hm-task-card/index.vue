<template>
  <div class="hm-task-card">
    <div class="box">
      <text class="title">{{ options.title }}</text>
      <div class="bd">
        <text class="info">{{ options.info }}</text>
      </div>
	  
      <div class="submain">
        <div class="block">
          <div class="shiliangzhinengduixiangWrap" @click="toSign">
            <image
              class="shiliangzhinengduixiang"
              :src="options.shiliangzhinengduixiang"
            />
          </div>
          <text class="tag_1">{{ options.name_1 }}</text>    
        </div>
		
        <div class="priceWrap">
          <div class="youhuiquanWrap" @click="toDetail">
            <image class="youhuiquan" :src="options.youhuiquan" />
          </div>
          <text class="price">{{ options.name_2 }}</text>
        </div>
		
      </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'HmTaskCard',
  props: {
    dataId: {
      type: String,
      default: 'hm-task-card'
    },
    options: {
      type: Object,
      default: function() {
        return {
          title: '',
          info: '',
          shiliangzhinengduixiang:
            '/static/hm-task-card/images/img_25838_0_0.png',
          name_1: '签到',
          youhuiquan:
            '/static/hm-task-card/images/img_25838_0_1.png',
          name_2: '成员'
        };
      }
    }
  },
  data() {
    return {};
  },
  methods: {
		  
	  toSign(){
		  console.log("签到");
		  uni.navigateTo({
			  url: '/pages/index/index'
		  	
		  });
	  },
	  toDetail(){
		  console.log("成员");
	  }
	  
  }
};
</script>
<style>
@import './index.response.css';

</style>
