<template>
  <v-card
      v-bind="{...$props, ...$attrs}"
      outlined
      class="text-center py-8"
  >
    <v-card-text>
      <div v-if="title" class="text-h6 font-weight-regular"> {{title}}</div>
      <div class="text-h3 mt-5">
        <span :class="getValueClasses" class="text-h3 font-weight-black">
          <slot name="value">
            {{value | numberFormatter}}
          </slot>
        </span>
        <span :class="getUnitClasses" class="subtitle-1 font-weight-black"> {{unit}}</span>
      </div>
    </v-card-text>
  </v-card>
</template>
<script>
export default {
  name: "CBoard",
  props: {
    title: {
      type: String,
      default: '',
    },
    value: {
      type: [String, Number],
    },
    unit: {
      type: String,
      default: '',
    },
    valueClasses: {
      type: Array,
      default() {
        return [];
      },
    },
    unitClasses: {
      type: Array,
      default() {
        return [];
      },
    }
  },
  computed:{
    getValueClasses() {
      return this.valueClasses ? this.valueClasses : '';
    },
    getUnitClasses() {
      return this.unitClasses ? this.unitClasses : '';
    }
  },
}
</script>

<style scoped>

</style>