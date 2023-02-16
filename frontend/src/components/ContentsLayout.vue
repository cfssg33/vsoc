<template>
<v-container class="contents-layout">
  <v-row class="header-box">
    <v-col class="title-area d-flex justify-start">
      <slot name="title">
        <div class="page-title">{{ $t("routeName."+$route.name) }}</div>
      </slot>
    </v-col>
    <v-col class="control-area d-flex justify-end">
      <slot name="control"></slot>
    </v-col>
  </v-row>
  <slot name="content">
    Contents will be here.
  </slot>
</v-container>
</template>
<script>
export default {
  data() {
    return {};
  },
  props: {},
  methods: {}
};
</script>
<style>
.page-title {
  padding-top: 1vh;
  padding-bottom: 3vh;
  font-size: 21px;
  font-weight: bold;
  color: #515461;
}
</style>
