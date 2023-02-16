<template>
  <v-date-picker
    v-bind="{...$props, ...$attrs}"
    :picker-date.sync="picker"
    :first-day-of-week="firstDayOfWeek"

    @input="dateInput($event)"
    v-model="date"

  >
  </v-date-picker>
</template>

<script>
  export default {
    props: {
      /* Sets the first day of the week, starting with 0 for Sunday. */
      firstDayOfWeek: {
        type: Number,
      },
      /* displayed month/year */
      pickerDate: {
        type: String,
      },
      selectedDate: {
        type: String,
      },
    },
    data() {
      return {
        date: this.selectedDate,
        picker: this.picker
      }
    },
    methods: {
        setColor() {
          const allDates = document.querySelectorAll(".v-date-picker-table tr");
          const sat = parseInt(6 - this.firstDayOfWeek);
          const sun = parseInt((7 - this.firstDayOfWeek)  % 7);

          allDates.forEach((x) => {
            if(x.children) {
              if(x.children[sat]) {
                if(!x.children[sat].children[0])
                  x.children[sat].style.color = 'blue';
                const targetEle = x.children[sat].children[0];
                if(targetEle && targetEle.style)
                  targetEle.style.color = 'blue';
              }
              if(x.children[sun]) {
                if(!x.children[sun].children[0])
                  x.children[sun].style.color = 'red';
                const targetEle = x.children[sun].children[0];
                if(targetEle && targetEle.style)
                  targetEle.style.color = 'red';
              }
            }
          });
        },
        dateInput: function(e) {
            this.$emit('input', e)
        },
    },
    updated() {
      this.setColor();
    },
  }
</script>

