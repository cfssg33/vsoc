<template>
  <v-btn
    v-bind="{...$props, ...$attrs}"
    class="font-weight-regular"
    :class="getClasses"
    :color="color"
    :outlined="outlined"
    :text="text"
    :icon="icon"

    v-on="$listeners"
  >
    <v-icon v-if="iconName&&!(this.$slots.default)" :color="iconColor" :size="iconSize">{{iconName}}</v-icon>
    <v-icon v-else-if="iconName&&this.$slots.default" left :color="iconColor" :size="iconSize">{{iconName}}</v-icon>
    <slot></slot>
  </v-btn>
</template>

<script>
  export default {
    props: {
      classes: {
        type: String,
        default: undefined
      },
      color: { // button color
        type: String,
        default: undefined
      },
      iconName: { // icon name
        type: String,
        default: undefined
      },
      iconColor: {
        type: String,
        default: undefined
      },
      iconSize: {
        type: String,
        default: undefined
      },


      text: { // Removes the button background color
        type: Boolean,
        default: false
      },
      outlined: { // Button will have an outline
        type: Boolean,
        default: false
      },
      icon: { // Designates the button as icon - round and flat(text)
        type: Boolean,
        default: false
      },
    },
    methods: {},
    computed: {
      getClasses() {
        if (this.text || this.icon || this.outlined) {
          return `${this.color}--text ${this.classes}`
        } else {
          return `white--text  ${this.classes}`
        }
      },
    },
  }
</script>