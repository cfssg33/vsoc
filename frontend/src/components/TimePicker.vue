<template>
<v-card width="300">
  <v-card-subtitle>
    Pick a Time
  </v-card-subtitle>
  <v-card-text class="pb-0">
    <v-container fill-height>
      <v-row align="center" justify="center">
        <v-text-field class="time-box" hide-details solo filled v-model="hour" @keyup="checkHour" @focusout="checkTimeFormat"></v-text-field>
        <div class="mx-2" style="font-size: 30px;">:</div>
        <v-text-field class="time-box" hide-details solo filled v-model="minutes" @keyup="checkMinute" @focusout="checkTimeFormat"></v-text-field>
      </v-row>
    </v-container>
  </v-card-text>
  <v-card-actions>
    <v-spacer></v-spacer>
    <v-btn text color="mainBlue" @click="submitChange">OK</v-btn>
  </v-card-actions>
</v-card>
</template>

<script>
export default {
  name: "TimePicker",
  data() {
    return {
      hour: this.value.split(":")[0],
      minutes: this.value.split(":")[1],
    }
  },
  computed: {
    time() {
      return `${this.hour}:${this.minutes}`;
    }
  },
  props: {
    value: {
      type: String,
      default: '00:00'
    }
  },
  methods: {
    checkTimeFormat() {
      if(this.hour === '') {
        this.hour = '00';
      }
      if(this.minutes === '') {
        this.minutes = '00';
      }
      if(this.hour.length === 1) {
        this.hour = '0'+this.hour;
      }
      if(this.minutes.length === 1) {
        this.minutes = '0'+this.minutes;
      }
    },
    checkHour($event) {
      if($event.target.value === '') {
        return;
      }
      if(isNaN($event.target.value)) {
        this.hour = '';
        return;
      }

      if($event.target.value.length > 2) {
        this.hour = $event.target.value.substring(0,2);
        return;
      }

      const hourValue = parseInt($event.target.value);
      if(hourValue < 0 || hourValue > 23) {
        this.hour = '00'
      }
    },
    checkMinute($event) {
      if($event.target.value === '') {
        return;
      }

      if(isNaN($event.target.value)) {
        this.minutes = '';
        return;
      }

      if($event.target.value.length > 2) {
        this.minutes = $event.target.value.substring(0,2);
        return;
      }

      const minuteValue = parseInt($event.target.value);
      if(minuteValue < 0 || minuteValue > 59) {
        this.minutes = '00'
      }
    },
    submitChange() {
      this.$emit('input', this.time);
      this.$emit('close');
    }
  }
}
</script>

<style scoped>
.time-box {
  max-width: 80px;
  font-size: 25px;
}
.time-box >>> input {
  text-align: center;
}
</style>