<template>
  <div style="display: flex;">
    <router-link
        :to="{
      name: this.redirectTo,
      params: { redirectedKeyword: this.redirectedKeyword }
    }"
        class="alarm-btn">
      {{content}}
    </router-link>
  </div>
</template>

<script>
import {DateTime} from 'luxon';
export default {
  name: 'EmergencyAlarm',
  props: {
    redirectedKeyword: {
      type: String
    },
    title: {
      type: String,
    },
    date: {
      type: Number,
    },
    redirectTo: {
      type: String,
    },
  },
  data() {
    return {
      content: '',
      timer: null,
      delay: 1000,
    };
  },
  methods: {
    startTimer() {
      this.timer = setInterval(() => {
        this.countDown();
      }, this.delay);
    },
    clearTimer() {
      clearInterval(this.timer);
      this.timer = null;
    },
    parseUnixDateTime(dateTime) {
      if (this.$i18n.locale === 'zhHans') {
        return DateTime.fromMillis(dateTime).toRelative({ locale: 'zh' });
      } else {
        return DateTime.fromMillis(dateTime).toRelative({ locale: 'en' });
      }
    },
    countDown() {
      this.content = `${this.title} - ${this.parseUnixDateTime(this.date)}`;
    },
  },
  mounted() {
    this.startTimer();
  },
  beforeDestroy() {
    this.clearTimer();
  },
};
</script>

<style scoped>
.chart-container div {
  position: relative;
  margin: auto;
  height: inherit;
}
.alarm-btn {
  margin-left: auto;
  margin-right: 0;
  margin-top: 4px;
  width: auto;
  padding: 1px 5px;
  border: 0.025rem solid #b6b6b6;
  border-radius: 10px;
  color: #161616;
  text-decoration: none;
}
</style>
